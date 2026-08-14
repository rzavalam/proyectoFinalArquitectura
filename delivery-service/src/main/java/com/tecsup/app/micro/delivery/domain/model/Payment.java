package com.tecsup.app.micro.delivery.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    private Long id;

    /**
     * ID de la orden asociada al pago.
     */
    private Long orderId;

    /**
     * Número único del pago.
     * Ejemplo: PAY-20260813-A1B2C3
     */
    private String paymentNumber;

    private BigDecimal amount;

    private String paymentMethod;

    private PaymentStatus status;

    private String transactionId;

    private LocalDateTime paidAt;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    /**
     * Crea un nuevo pago en estado PENDING.
     */
    public static Payment create(
            Long orderId,
            BigDecimal amount,
            String paymentMethod
    ) {

        if (orderId == null) {
            throw new IllegalArgumentException(
                    "Order ID is required."
            );
        }

        if (amount == null
                || amount.compareTo(BigDecimal.ZERO) <= 0) {

            throw new IllegalArgumentException(
                    "Payment amount must be greater than zero."
            );
        }

        if (paymentMethod == null
                || paymentMethod.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Payment method is required."
            );
        }

        LocalDateTime now = LocalDateTime.now();

        return Payment.builder()
                .orderId(orderId)
                .paymentNumber(generatePaymentNumber())
                .amount(amount)
                .paymentMethod(paymentMethod)
                .status(PaymentStatus.PENDING)
                .transactionId(null)
                .paidAt(null)
                .createdAt(now)
                .updatedAt(now)
                .build();
    }

    /**
     * Aprueba el pago.
     */
    public void approve(String transactionId) {

        if (status != PaymentStatus.PENDING) {
            throw new IllegalStateException(
                    "Only pending payments can be approved."
            );
        }

        if (transactionId == null
                || transactionId.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Transaction ID is required."
            );
        }

        LocalDateTime now = LocalDateTime.now();

        this.status = PaymentStatus.APPROVED;
        this.transactionId = transactionId;
        this.paidAt = now;
        this.updatedAt = now;
    }

    /**
     * Rechaza el pago.
     */
    public void reject() {

        if (status != PaymentStatus.PENDING) {
            throw new IllegalStateException(
                    "Only pending payments can be rejected."
            );
        }

        this.status = PaymentStatus.REJECTED;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Genera el número único del pago.
     */
    private static String generatePaymentNumber() {

        return "PAY-"
                + LocalDateTime.now()
                .format(
                        DateTimeFormatter.ofPattern("yyyyMMdd")
                )
                + "-"
                + UUID.randomUUID()
                .toString()
                .substring(0, 6)
                .toUpperCase();
    }
}


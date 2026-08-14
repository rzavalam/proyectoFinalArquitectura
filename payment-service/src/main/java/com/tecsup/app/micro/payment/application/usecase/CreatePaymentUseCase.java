package com.tecsup.app.micro.payment.application.usecase;

import com.tecsup.app.micro.events.PaymentApprovedEvent;
import com.tecsup.app.micro.events.PaymentRejectedEvent;
import com.tecsup.app.micro.payment.domain.model.Order;
import com.tecsup.app.micro.payment.domain.model.Payment;
import com.tecsup.app.micro.payment.domain.repository.PaymentRepository;
import com.tecsup.app.micro.payment.infrastructure.client.OrderClient;
import com.tecsup.app.micro.payment.infrastructure.event.KafkaEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class CreatePaymentUseCase {

    private final PaymentRepository paymentRepository;
    private final KafkaEventPublisher eventPublisher;
    private final OrderClient orderClient;
    private final Random random = new Random();

    /**
     * Crea y procesa un pago.
     */
    public Payment execute( Long orderId, BigDecimal amount,  String paymentMethod, String jwtToken) {

        log.info( "[PAYMENT] Creating payment. orderId={}, amount={}, method={}", orderId, amount, paymentMethod);

        // ==========================================
        // 1. Validar datos
        // ==========================================

        validatePaymentData( orderId, amount,paymentMethod);

        // ==========================================
        // 2. VALIDAR QUE LA ORDEN EXISTA
        // ==========================================

        Order order =   orderClient.getOrderById(orderId, jwtToken );

        if (order == null) {

            log.error(
                    "[PAYMENT] No se puede crear el pago. " +
                            "La orden {} no existe.",
                    orderId
            );

            throw new IllegalArgumentException(
                    "Orden " + orderId + " NO se encuentra registrada"
            );
        }

        log.info(
                "[PAYMENT] Orden {} validada correctamente.",
                orderId
        );

        // ==========================================
        // 2. Verificar pago existente
        // ==========================================

        if (paymentRepository.existsByOrderId(orderId)) {
            throw new IllegalArgumentException(
                    "Ya existe un pago para la orden: " + orderId
            );
        }
        // ==========================================
        // 3. Crear Payment en estado PENDING
        // ==========================================

        Payment payment = Payment.create( orderId, amount, paymentMethod);

        // ==========================================
        // 4. Guardar Payment
        // ==========================================

        Payment savedPayment = paymentRepository.save(payment);
        log.info(
                "[PAYMENT] Payment created. id={}, orderId={}, status={}",
                savedPayment.getId(),
                savedPayment.getOrderId(),
                savedPayment.getStatus()
        );

        // ==========================================
        // 5. Procesar pago
        // ==========================================

        processPayment(savedPayment);

        // ==========================================
        // 6. Retornar pago actualizado
        // ==========================================

        return paymentRepository.findById(savedPayment.getId()).orElse(savedPayment);
    }

    /**
     * Valida los datos necesarios para crear el pago.
     */
    private void validatePaymentData( Long orderId, BigDecimal amount, String paymentMethod) {
        if (orderId == null) {
            throw new IllegalArgumentException( "Order ID is required.");
        }

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException( "Payment amount must be greater than zero.");
        }

        if (paymentMethod == null || paymentMethod.trim().isEmpty()) {
            throw new IllegalArgumentException( "Payment method is required.");
        }
    }

    /**
     * Simula el procesamiento del pago.
     */
    private void processPayment(Payment payment) {
        try {
            log.info( "[PAYMENT] Processing payment. orderId={}", payment.getOrderId() );
            // Simulación del proveedor de pagos: 1-3 segundos
            Thread.sleep(1000 + random.nextInt(2000));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error( "[PAYMENT] Payment processing interrupted. orderId={}", payment.getOrderId(), e);
            throw new RuntimeException( "Payment processing interrupted.",e);
        }

        // 60% de probabilidad de aprobación
        boolean approved =  random.nextInt(100) < 99;
        if (approved) {
            approvePayment(payment);
        } else {
            rejectPayment(payment);
        }
    }

    /**
     * Aprueba el pago y publica el evento correspondiente.
     */
    private void approvePayment(Payment payment) {
        String transactionId = "tx-" + UUID.randomUUID();
        payment.approve(transactionId);
        Payment savedPayment = paymentRepository.save(payment);
        log.info( "[PAYMENT] Payment APPROVED. orderId={}, transactionId={}", savedPayment.getOrderId(),transactionId);
        publishPaymentApprovedEvent(savedPayment);
    }

    /**
     * Rechaza el pago y publica el evento correspondiente.
     */
    private void rejectPayment(Payment payment) {
        payment.reject();
        Payment savedPayment = paymentRepository.save(payment);
        log.warn( "[PAYMENT] Payment REJECTED. orderId={}", savedPayment.getOrderId());
        publishPaymentRejectedEvent(savedPayment);
    }

    /**
     * Publica el evento de pago aprobado.
     */
    private void publishPaymentApprovedEvent( Payment payment ) {
        PaymentApprovedEvent event =
                new PaymentApprovedEvent(
                        payment.getOrderId(),
                        payment.getTransactionId(),
                        payment.getAmount(),
                        LocalDateTime.now()
                );

        eventPublisher.publish(event);
        log.info( "[PAYMENT] PaymentApprovedEvent published. orderId={}", payment.getOrderId());
    }

    /**
     * Publica el evento de pago rechazado.
     */
    private void publishPaymentRejectedEvent( Payment payment ) {
        PaymentRejectedEvent event =
                new PaymentRejectedEvent(
                        payment.getOrderId(),
                        "PAYMENT_DECLINED",
                        "El pago fue rechazado por el proveedor, saldo insuficiente.",
                        LocalDateTime.now()
                );

        eventPublisher.publish(event);

        log.warn( "[PAYMENT] PaymentRejectedEvent published. orderId={}",payment.getOrderId() );
    }




}
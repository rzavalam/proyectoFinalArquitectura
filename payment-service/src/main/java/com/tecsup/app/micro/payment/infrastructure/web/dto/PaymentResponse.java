package com.tecsup.app.micro.payment.infrastructure.web.dto;
import com.tecsup.app.micro.payment.domain.model.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {

    private Long id;

    private Long orderId;

    private String paymentNumber;

    private BigDecimal amount;

    private String paymentMethod;

    private PaymentStatus status;

    private String transactionId;

    private LocalDateTime paidAt;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}


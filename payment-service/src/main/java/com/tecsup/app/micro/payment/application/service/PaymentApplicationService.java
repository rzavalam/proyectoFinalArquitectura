package com.tecsup.app.micro.payment.application.service;

import com.tecsup.app.micro.payment.application.usecase.CreatePaymentUseCase;
import com.tecsup.app.micro.payment.application.usecase.GetPaymentByIdUseCase;
import com.tecsup.app.micro.payment.application.usecase.GetPaymentByOrderIdUseCase;
import com.tecsup.app.micro.payment.domain.model.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentApplicationService {

    private final CreatePaymentUseCase createPaymentUseCase;
    private final GetPaymentByIdUseCase getPaymentByIdUseCase;
    private final GetPaymentByOrderIdUseCase getPaymentByOrderIdUseCase;

    @Transactional
    public Payment createPayment(
            Long orderId,
            BigDecimal amount,
            String paymentMethod,
            String jwtToken
    ) {

        return createPaymentUseCase.execute(
                orderId,
                amount,
                paymentMethod,
                jwtToken
        );
    }

    @Transactional(readOnly = true)
    public Payment getPaymentById(Long id) {

        return getPaymentByIdUseCase.execute(id);
    }

    @Transactional(readOnly = true)
    public Payment getPaymentByOrderId(Long orderId) {

        return getPaymentByOrderIdUseCase.execute(orderId);
    }
}
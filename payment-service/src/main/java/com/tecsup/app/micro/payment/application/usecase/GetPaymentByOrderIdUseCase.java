package com.tecsup.app.micro.payment.application.usecase;

import com.tecsup.app.micro.payment.domain.exception.PaymentNotFoundException;
import com.tecsup.app.micro.payment.domain.model.Payment;
import com.tecsup.app.micro.payment.domain.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class GetPaymentByOrderIdUseCase {

    private final PaymentRepository paymentRepository;

    public Payment execute(Long orderId) {

        log.debug(
                "Searching payment by orderId: {}",
                orderId
        );

        return paymentRepository.findByOrderId(orderId)
                .orElseThrow(() ->
                        new PaymentNotFoundException(
                                "No se encontró un pago para la orden: "
                                        + orderId
                        )
                );
    }
}
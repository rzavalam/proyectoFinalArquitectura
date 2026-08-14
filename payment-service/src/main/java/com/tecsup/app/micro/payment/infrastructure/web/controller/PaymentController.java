package com.tecsup.app.micro.payment.infrastructure.web.controller;

import com.tecsup.app.micro.payment.application.service.PaymentApplicationService;
import com.tecsup.app.micro.payment.domain.model.Payment;
import com.tecsup.app.micro.payment.infrastructure.web.dto.CreatePaymentRequest;
import com.tecsup.app.micro.payment.infrastructure.web.dto.PaymentResponse;
import com.tecsup.app.micro.payment.infrastructure.web.mapper.PaymentDtoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@Slf4j
public class PaymentController {

    private final PaymentApplicationService paymentApplicationService;

    private final PaymentDtoMapper paymentDtoMapper;

    @PostMapping
    public ResponseEntity<PaymentResponse> createPayment( @RequestBody CreatePaymentRequest request,
            @RequestHeader(    value = "Authorization", required = false) String authorization
    ) {
        String jwtToken = null;
        if (authorization != null &&  authorization.startsWith("Bearer ")) {
            jwtToken =authorization.substring(7);
        }

        Payment payment =
                paymentApplicationService.createPayment(
                        request.getOrderId(),
                        request.getAmount(),
                        request.getPaymentMethod(),
                        jwtToken
                );

        return ResponseEntity.ok(
                paymentDtoMapper.toResponse(payment)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponse> getPaymentById(
            @PathVariable Long id
    ) {

        Payment payment =
                paymentApplicationService.getPaymentById(id);

        return ResponseEntity.ok(
                paymentDtoMapper.toResponse(payment)
        );
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<PaymentResponse> getPaymentByOrderId(
            @PathVariable Long orderId
    ) {

        Payment payment =
                paymentApplicationService.getPaymentByOrderId(orderId);

        return ResponseEntity.ok(
                paymentDtoMapper.toResponse(payment)
        );
    }
}
package com.tecsup.app.micro.order.application.service;

import com.tecsup.app.micro.events.DomainEvent;
import com.tecsup.app.micro.events.PaymentApprovedEvent;
import com.tecsup.app.micro.events.PaymentRejectedEvent;

import com.tecsup.app.micro.order.infrastructure.config.KafkaConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentEventHandler {

    private final OrderApplicationService orderApplicationService;

    /**
     * Escucha los eventos publicados por payment-service.
     */
    @KafkaListener(
            topics = KafkaConfig.PAYMENT_PROCESSED_TOPIC,
            groupId = "order-group"
    )
    public void handlePaymentEvent(DomainEvent event) {

        log.info("[Kafka] Payment event received: {}", event.getClass().getSimpleName());

        if (event instanceof PaymentApprovedEvent) {
            handlePaymentApproved( (PaymentApprovedEvent) event);
        } else if (event instanceof PaymentRejectedEvent) {
            handlePaymentRejected( (PaymentRejectedEvent) event);
        } else {
            log.warn( "[Kafka] Unsupported payment event: {}", event.getClass().getName());
        }
    }

    /**
     * Procesa un pago aprobado.
     */
    private void handlePaymentApproved(PaymentApprovedEvent event) {
        Long orderId = event.getOrderId();
        log.info("[Kafka] Payment approved. orderId={}, transactionId={}", orderId, event.getTransactionId());
        orderApplicationService.confirmOrder( orderId);
        log.info("[Kafka] Order {} confirmed after payment approval", orderId);
    }

    /**
     * Procesa un pago rechazado.
     */
    private void handlePaymentRejected(PaymentRejectedEvent event) {

        Long orderId = event.getOrderId();
        log.warn( "[Kafka] Payment rejected. orderId={}, errorCode={}, reason={}",
                orderId,
                event.getErrorCode(),
                event.getReason()
        );

        orderApplicationService.cancelOrder(orderId);

        log.info("[Kafka] Order {} cancelled after payment rejection",orderId);
    }
}

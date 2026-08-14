package com.tecsup.app.micro.delivery.application.service;

import com.tecsup.app.micro.delivery.infrastructure.config.KafkaConfig;
import com.tecsup.app.micro.events.OrderConfirmedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventHandler {

    private final DeliveryApplicationService deliveryApplicationService;

    /**
     * Escucha los eventos publicados por order-service
     * cuando una orden ha sido confirmada.
     */
    @KafkaListener(
            topics = KafkaConfig.ORDER_CONFIRMED_TOPIC,
            groupId = "delivery-group"
    )
    public void handleOrderEvent(OrderConfirmedEvent event) {

        log.info(
                "[Kafka] Order confirmed event received. orderId={}, orderNumber={}",
                event.getOrderId(),
                event.getOrderNumber()
        );

        handleOrderConfirmed(event);
    }

    /**
     * Procesa la orden confirmada y crea automáticamente
     * el Delivery en estado PENDING.
     */
    private void handleOrderConfirmed(OrderConfirmedEvent event) {

        log.info(
                "[Kafka] Creating delivery for orderId={}",
                event.getOrderId()
        );

        deliveryApplicationService.createDelivery(
                event.getOrderId(),
                event.getUserId(),
                event.getDeliveryAddress(),
                null,
                null
        );

        log.info(
                "[Kafka] Delivery created successfully. orderId={}",
                event.getOrderId()
        );
    }
}

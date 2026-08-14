package com.tecsup.app.micro.order.application.usecase;

import com.tecsup.app.micro.events.OrderConfirmedEvent;
import com.tecsup.app.micro.order.domain.model.Order;
import com.tecsup.app.micro.order.domain.repository.OrderRepository;
import com.tecsup.app.micro.order.shared.infrastructure.event.KafkaEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ConfirmOrderUseCase {

    private final OrderRepository orderRepository;
    private final KafkaEventPublisher eventPublisher;
    public Order execute(Long orderId) {

        log.info(
                "Confirming order after payment approval. orderId={}",
                orderId
        );

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Order not found with id: " + orderId
                        )
                );

        order.confirm();

        Order savedOrder =
                orderRepository.save(order);

        log.info(
                "Order {} confirmed successfully",
                orderId
        );

        //Crear el evento kafka
        OrderConfirmedEvent event = new OrderConfirmedEvent(
                savedOrder.getId(),
                savedOrder.getOrderNumber(),
                savedOrder.getUserId(),
                savedOrder.getRestaurantId(),
                savedOrder.getStatus().name(),
                savedOrder.getSubtotal(),
                savedOrder.getDeliveryFee(),
                savedOrder.getTotalAmount(),
                savedOrder.getDeliveryAddress(),
                savedOrder.getNotes()
        );

        this.eventPublisher.publish(event);
        return savedOrder;
    }
}
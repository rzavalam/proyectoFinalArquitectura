package com.tecsup.app.micro.order.application.usecase;

import com.tecsup.app.micro.order.domain.model.Order;
import com.tecsup.app.micro.order.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CancelOrderUseCase {

    private final OrderRepository orderRepository;

    public Order execute(Long orderId) {

        log.info(
                "Cancelling order after payment rejection. orderId={}",
                orderId
        );

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Order not found with id: " + orderId
                        )
                );

        order.cancel();

        Order savedOrder =
                orderRepository.save(order);

        log.info(
                "Order {} cancelled successfully",
                orderId
        );

        return savedOrder;
    }
}
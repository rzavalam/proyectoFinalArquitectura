package com.tecsup.app.micro.order.application.usecase;

import com.tecsup.app.micro.order.domain.exception.UserNotFoundException;
import com.tecsup.app.micro.order.domain.model.Order;
import com.tecsup.app.micro.order.domain.model.User;
import com.tecsup.app.micro.order.domain.repository.OrderRepository;
import com.tecsup.app.micro.order.infrastructure.client.UserClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class GetOrdersByUserUseCase {

    private final OrderRepository orderRepository;
    private final UserClient userClient;

    public List<Order> execute(Long userId, String jwtToken) {

        User user = userClient.getUserById(userId, jwtToken);

        if (user == null) {
            throw new UserNotFoundException(userId);
        }

        log.info("Fetching orders for user from user-service: {}", user.getName());

        log.debug("Executing GetOrdersByUserUseCase for userId: {}", userId);

        List<Order> orders = orderRepository.findByUserId(userId);

        orders.forEach(order -> order.setUser(user));

        return orders;
    }
}
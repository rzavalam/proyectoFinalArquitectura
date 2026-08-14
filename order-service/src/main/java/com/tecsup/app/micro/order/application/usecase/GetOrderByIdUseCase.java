package com.tecsup.app.micro.order.application.usecase;

import com.tecsup.app.micro.order.domain.exception.OrderNotFoundException;
import com.tecsup.app.micro.order.domain.exception.UserNotFoundException;
import com.tecsup.app.micro.order.domain.model.Order;
import com.tecsup.app.micro.order.domain.model.User;
import com.tecsup.app.micro.order.domain.repository.OrderRepository;
import com.tecsup.app.micro.order.infrastructure.client.UserClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class GetOrderByIdUseCase {

    private final OrderRepository orderRepository;

    private final UserClient userClient;

    public Order execute(Long id, String jwtToken) {

        log.debug("Executing GetOrderByIdUseCase for id: {}", id);

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));

        // --------------------------------------------------------
        // Obtener información del usuario desde user-service
        // --------------------------------------------------------
        User user = userClient.getUserById(order.getUserId(), jwtToken);

        log.info("Fetching user from user-service: {}", user);

        if (user == null) {
            log.warn("User with id {} not found", order.getUserId());
            throw new UserNotFoundException(order.getUserId());
        }

        order.setUser(user);

        return order;
    }
}

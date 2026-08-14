package com.tecsup.app.micro.order.application.usecase;

import com.tecsup.app.micro.order.domain.exception.InvalidOrderDataException;
import com.tecsup.app.micro.order.domain.exception.UserNotFoundException;
import com.tecsup.app.micro.events.OrderCreatedEvent;
import com.tecsup.app.micro.order.domain.model.Order;
import com.tecsup.app.micro.order.domain.model.OrderItem;
import com.tecsup.app.micro.order.domain.model.OrderStatus;
import com.tecsup.app.micro.order.domain.model.Product;
import com.tecsup.app.micro.order.domain.model.User;
import com.tecsup.app.micro.order.domain.repository.OrderRepository;
import com.tecsup.app.micro.order.infrastructure.client.ProductClient;
import com.tecsup.app.micro.order.infrastructure.client.UserClient;
import com.tecsup.app.micro.order.shared.infrastructure.event.KafkaEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class CreateOrderUseCase {

    private final OrderRepository orderRepository;
    private final ProductClient productClient;
    private final UserClient userClient;

    public Order execute(Order order, String jwtToken) {

        if (order == null || !order.isValid()) {
            throw new InvalidOrderDataException(
                    "Invalid order data. User and items are required.");
        }

        if (order.getItems() == null || order.getItems().isEmpty()) {
            throw new InvalidOrderDataException(
                    "An order must contain at least one product.");
        }

        // ==========================
        // Obtener usuario
        // ==========================
        User user = userClient.getUserById(order.getUserId(), jwtToken);

        if (user == null) {
            throw new UserNotFoundException(order.getUserId());
        }

        order.setUser(user);

        // Guardar productos en memoria
        Map<Long, Product> products = new HashMap<>();

        // ==========================
        // Validar productos
        // ==========================
        for (OrderItem item : order.getItems()) {

            if (item == null || item.getProductId() == null || item.getQuantity() == null) {
                throw new InvalidOrderDataException("Invalid order item.");
            }

            Product product =
                    productClient.getProductById(item.getProductId(), jwtToken);

            if (product == null) {
                throw new InvalidOrderDataException(
                        "Product not found: " + item.getProductId());
            }

            if (!product.isAvailable()) {
                throw new InvalidOrderDataException(
                        "Product without stock: " + product.getName());
            }


            products.put(product.getId(), product);

            item.setProductName(product.getName());
            item.setUnitPrice(product.getPrice());
            item.calculateSubtotal();
        }

        order.calculateAmounts();

        order.setStatus(OrderStatus.PENDING);
        order.setOrderNumber(generateOrderNumber());
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());

        // ==========================
        // Guardar
        // ==========================
        Order savedOrder = orderRepository.save(order);

        // ==========================
        // Reconstruir respuesta
        // ==========================
        savedOrder.setUser(user);

        for (OrderItem item : savedOrder.getItems()) {
            item.setProduct(products.get(item.getProductId()));
        }

        //Crear el evento kafka
        OrderCreatedEvent event = new OrderCreatedEvent(
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
        //this.eventPublisher.publish(event);

        log.info("Order {} created successfully with total {}", savedOrder.getOrderNumber(), savedOrder.getTotalAmount());

        return savedOrder;
    }

    private String generateOrderNumber() {

        return "ORD-"
                + LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd"))
                + "-"
                + UUID.randomUUID()
                .toString()
                .substring(0, 6)
                .toUpperCase();
    }


}
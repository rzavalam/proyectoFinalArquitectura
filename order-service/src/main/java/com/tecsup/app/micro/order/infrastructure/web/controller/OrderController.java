package com.tecsup.app.micro.order.infrastructure.web.controller;

import com.tecsup.app.micro.order.application.service.OrderApplicationService;
import com.tecsup.app.micro.order.domain.model.Order;
import com.tecsup.app.micro.order.domain.model.OrderStatus;
import com.tecsup.app.micro.order.infrastructure.web.dto.CreateOrderRequest;
import com.tecsup.app.micro.order.infrastructure.web.dto.OrderResponse;


import com.tecsup.app.micro.order.infrastructure.web.mapper.OrderDtoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Controller REST para gestión de órdenes.
 */
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderApplicationService orderApplicationService;
    private final OrderDtoMapper orderMapper;


    /**
     * Obtener todas las órdenes.
     *
     * GET /api/orders
     */
    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        List<Order> orders =orderApplicationService.getAllOrders();
        return ResponseEntity.ok(orders.stream().map(orderMapper::toResponse).toList());
    }



    /**
     * Obtener orden por ID.
     *
     * GET /api/orders/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id,@RequestHeader("Authorization") String authorization) {
        String jwtToken = extractToken(authorization);
        Order order =  orderApplicationService.getOrderById(id, jwtToken);
        return ResponseEntity.ok( orderMapper.toResponse(order)
        );
    }

    /**
     * Obtener órdenes de un usuario.
     *
     * GET /api/orders/user/{userId}
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponse>> getOrdersByUser( @PathVariable Long userId, @RequestHeader("Authorization") String authorization) {
        String jwtToken = extractToken(authorization);
        List<Order> orders =  orderApplicationService.getOrdersByUser( userId, jwtToken);

        return ResponseEntity.ok( orders.stream().map(orderMapper::toResponse).toList() );
    }

    /**
     * Registrar una nueva orden.
     *
     * POST /api/orders
     *
     * Ejemplo:
     *
     * {
     *    "userId": 1,
     *    "items": [
     *       {
     *          "productId": 10,
     *          "quantity": 2
     *       }
     *    ]
     * }
     *
     */
    @PostMapping
    public ResponseEntity<OrderResponse> createOrder( @Valid @RequestBody CreateOrderRequest request,
            @RequestHeader("Authorization") String authorization) {

        String jwtToken = extractToken(authorization);

        Order order = orderMapper.toDomain(request);
        Order created =  orderApplicationService.createOrder( order, jwtToken );

        return ResponseEntity.status(HttpStatus.CREATED).body( orderMapper.toResponse(created));
    }


    /**
     * Actualizar estado de una orden.
     *
     * PATCH /api/orders/{id}/status
     *
     * Body:
     *
     * {
     *    "status":"CONFIRMED"
     * }
     */
    @PutMapping("/{id}/status")
    public ResponseEntity<OrderResponse> updateStatus( @PathVariable Long id, @RequestBody String status,
            @RequestHeader("Authorization") String authorization) {


        String jwtToken = extractToken(authorization);
        OrderStatus orderStatus = OrderStatus.valueOf(status.trim().toUpperCase());

        Order updated = orderApplicationService.updateOrderStatus( id, orderStatus, jwtToken);

        return ResponseEntity.ok( orderMapper.toResponse(updated) );
    }





    /**
     * Eliminar una orden.
     *
     * DELETE /api/orders/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderApplicationService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }


    /**
     * Extrae JWT eliminando Bearer.
     */
    private String extractToken(String authorization) {
        if (authorization != null && authorization.startsWith("Bearer ")) {
            return authorization.substring(7);
        }

        return authorization;
    }

}

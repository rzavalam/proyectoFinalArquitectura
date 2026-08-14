package com.tecsup.app.micro.payment.infrastructure.client.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO utilizado para consumir el order-service.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    private Long id;

    private String orderNumber;

    private Long userId;

    private Long restaurantId;

    private String status;

    private BigDecimal subtotal;

    private BigDecimal deliveryFee;

    private BigDecimal totalAmount;

    private String deliveryAddress;

    private String notes;

    private List<OrderItemDto> items;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
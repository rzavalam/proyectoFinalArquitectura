package com.tecsup.app.micro.delivery.infrastructure.client.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO utilizado para consumir el product-service
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private Long id;

    private Long restaurantId;

    private String name;

    private String description;

    private BigDecimal price;

    private String imageUrl;

    private boolean available;

    private Long createdBy;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
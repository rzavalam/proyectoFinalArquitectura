package com.tecsup.app.micro.payment.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Product Domain Model (Core Business Entity)
 * Esta es la entidad de dominio pura, sin dependencias de frameworks
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

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

    /**
     * Valida los datos mínimos requeridos del producto.
     */
    public boolean isValid() {
        return restaurantId != null
                && name != null
                && !name.trim().isEmpty()
                && price != null
                && price.compareTo(BigDecimal.ZERO) >= 0
                && createdBy != null;
    }
}

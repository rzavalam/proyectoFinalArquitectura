package com.tecsup.app.micro.product.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Modelo de dominio de restaurante.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {

    private Long id;

    private String name;

    private String description;

    private String address;

    private String phone;

    private boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public boolean isValid() {

        return name != null
                && !name.trim().isEmpty();
    }
}

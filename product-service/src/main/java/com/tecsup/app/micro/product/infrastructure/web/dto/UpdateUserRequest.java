package com.tecsup.app.micro.product.infrastructure.web.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
/**
 * DTO para actualizar un usuario
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {

    @NotNull(message = "Restaurant ID is required")
    private Long restaurantId;

    @NotBlank(message = "Name is required")
    @Size(
            max = 150,
            message = "Name must not exceed 150 characters"
    )
    private String name;

    @Size(
            max = 500,
            message = "Description must not exceed 500 characters"
    )
    private String description;

    @NotNull(message = "Price is required")
    @DecimalMin(
            value = "0.0",
            message = "Price must be positive or zero"
    )
    private BigDecimal price;

    @Size(
            max = 500,
            message = "Image URL must not exceed 500 characters"
    )
    private String imageUrl;

    private boolean available;
}

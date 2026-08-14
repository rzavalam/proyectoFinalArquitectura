package com.tecsup.app.micro.order.infrastructure.web.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequest  {

    @NotNull(message = "El ID del usuario es obligatorio")
    private Long userId;

    @NotNull(message = "El ID del restaurante es obligatorio")
    private Long restaurantId;

    @NotNull(message = "El costo de envío es obligatorio")
    @DecimalMin(value = "0.00", message = "El costo de envío no puede ser negativo")
    private BigDecimal deliveryFee;

    @NotBlank(message = "La dirección de entrega es obligatoria")
    @Size(max = 255, message = "La dirección de entrega no debe superar los 255 caracteres")
    private String deliveryAddress;

    @Size(max = 1000, message = "Las notas no deben superar los 1000 caracteres")
    private String notes;

    @NotEmpty(message = "El pedido debe contener al menos un producto")
    @Valid
    private List<CreateOrderItemRequest> items;

}
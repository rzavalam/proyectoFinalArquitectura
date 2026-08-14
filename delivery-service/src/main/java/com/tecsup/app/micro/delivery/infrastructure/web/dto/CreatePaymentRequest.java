package com.tecsup.app.micro.delivery.infrastructure.web.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePaymentRequest {

    @NotNull(message = "El ID de la orden es obligatorio")
    private Long orderId;

    @NotNull(message = "El monto es obligatorio")
    @DecimalMin(
            value = "0.01",
            message = "El monto debe ser mayor que 0"
    )
    private BigDecimal amount;

    @NotBlank(message = "El método de pago es obligatorio")
    @Size(
            max = 30,
            message = "El método de pago no debe superar los 30 caracteres"
    )
    private String paymentMethod;
}

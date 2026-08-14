package com.tecsup.app.micro.order.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    private Long id;

    private Long productId;
    /**
     * Información del producto obtenida desde product-service
     */
    private Product product;
    /**
     * Nombre del producto guardado como snapshot al momento
     * de crear la orden.
     */
    private String productName;

    private BigDecimal unitPrice;

    private Integer quantity;

    private BigDecimal subtotal;

    public boolean isValid() {
        return productId != null
                && productName != null
                && !productName.trim().isEmpty()
                && quantity != null
                && quantity > 0
                && unitPrice != null
                && unitPrice.compareTo(BigDecimal.ZERO) >= 0;
    }

    public void calculateSubtotal() {
        if (unitPrice == null) {
            throw new IllegalStateException("Unit price cannot be null.");
        }

        if (quantity == null || quantity <= 0) {
            throw new IllegalStateException(
                    "Quantity must be greater than zero."
            );
        }

        this.subtotal = unitPrice.multiply(
                BigDecimal.valueOf(quantity)
        );
    }
}


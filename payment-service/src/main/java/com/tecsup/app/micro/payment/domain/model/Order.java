package com.tecsup.app.micro.payment.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private Long id;
    private String orderNumber;

    private Long userId;
    private Long restaurantId;

    private OrderStatus status;

    private BigDecimal subtotal;
    private BigDecimal deliveryFee;
    private BigDecimal totalAmount;

    private String deliveryAddress;
    private String notes;

    private List<OrderItem> items;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Usuario obtenido desde user-service.
     * No se persiste en orders; se usa únicamente para enriquecer la respuesta.
     */
    private User user;

    public boolean isValid() {
        return userId != null
                && restaurantId != null
                && deliveryAddress != null
                && !deliveryAddress.trim().isEmpty()
                && items != null
                && !items.isEmpty();
    }

    /**
     * Calcula el subtotal sumando los subtotales de los items.
     */
    public void calculateSubtotal() {
        this.subtotal = items.stream()
                .map(OrderItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Calcula el total: subtotal + deliveryFee.
     */
    public void calculateTotal() {
        BigDecimal orderSubtotal =
                subtotal != null ? subtotal : BigDecimal.ZERO;

        BigDecimal fee =
                deliveryFee != null ? deliveryFee : BigDecimal.ZERO;

        this.totalAmount = orderSubtotal.add(fee);
    }

    public void calculateAmounts() {
        calculateSubtotal();
        calculateTotal();
    }

    public boolean canBeCancelled() {
        return status == OrderStatus.PENDING
                || status == OrderStatus.CONFIRMED;
    }

    public void cancel() {
        if (!canBeCancelled()) {
            throw new IllegalStateException("The order cannot be cancelled.");
        }

        this.status = OrderStatus.CANCELLED;
        this.updatedAt = LocalDateTime.now();
    }

    public void confirm() {
        if (status != OrderStatus.PENDING) {
            throw new IllegalStateException(
                    "Only pending orders can be confirmed."
            );
        }

        this.status = OrderStatus.CONFIRMED;
        this.updatedAt = LocalDateTime.now();
    }

    public void ship() {
        if (status != OrderStatus.CONFIRMED) {
            throw new IllegalStateException(
                    "Only confirmed orders can be shipped."
            );
        }

        this.status = OrderStatus.SHIPPED;
        this.updatedAt = LocalDateTime.now();
    }

    public void deliver() {
        if (status != OrderStatus.SHIPPED) {
            throw new IllegalStateException(
                    "Only shipped orders can be delivered."
            );
        }

        this.status = OrderStatus.DELIVERED;
        this.updatedAt = LocalDateTime.now();
    }
}


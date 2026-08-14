package com.tecsup.app.micro.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
@AllArgsConstructor
@Getter
@ToString
@NoArgsConstructor(force = true)
public class OrderConfirmedEvent extends DomainEvent {

    private final Long orderId;
    private final String orderNumber;
    private final Long userId;
    private final Long restaurantId;
    private final String status;
    private final BigDecimal subtotal;
    private final BigDecimal deliveryFee;
    private final BigDecimal totalAmount;
    private final String deliveryAddress;
    private final String notes;

    @Override
    public String getKey() {
        return String.valueOf(this.orderId);
    }
}
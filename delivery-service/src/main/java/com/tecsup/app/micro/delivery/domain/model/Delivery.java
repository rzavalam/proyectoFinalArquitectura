package com.tecsup.app.micro.delivery.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Delivery {

    private Long id;

    private Long orderId;

    private Long userId;

    private String deliveryAddress;

    private String deliveryPersonName;

    private String deliveryPersonPhone;

    private String status;

    private LocalDateTime estimatedDeliveryAt;

    private LocalDateTime pickedUpAt;

    private LocalDateTime deliveredAt;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    /**
     * Crea un nuevo delivery en estado PENDING.
     */
    public static Delivery create(
            Long orderId,
            Long userId,
            String deliveryAddress,
            String deliveryPersonName,
            String deliveryPersonPhone
    ) {

        LocalDateTime now = LocalDateTime.now();

        return Delivery.builder()
                .orderId(orderId)
                .userId(userId)
                .deliveryAddress(deliveryAddress)
                .deliveryPersonName(deliveryPersonName)
                .deliveryPersonPhone(deliveryPersonPhone)
                .status("PENDING")
                .estimatedDeliveryAt(now.plusMinutes(45))
                .createdAt(now)
                .updatedAt(now)
                .build();
    }

    /**
     * Marca el delivery como recogido.
     */
    public void pickUp() {

        this.status = "PICKED_UP";
        this.pickedUpAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Marca el delivery como entregado.
     */
    public void deliver() {

        this.status = "DELIVERED";
        this.deliveredAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Cancela el delivery.
     */
    public void cancel() {

        this.status = "CANCELLED";
        this.updatedAt = LocalDateTime.now();
    }
}
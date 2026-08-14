package com.tecsup.app.micro.delivery.infrastructure.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryResponse {

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
}

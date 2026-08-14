package com.tecsup.app.micro.delivery.infrastructure.web.controller;


import com.tecsup.app.micro.delivery.application.service.DeliveryApplicationService;
import com.tecsup.app.micro.delivery.domain.model.Delivery;

import com.tecsup.app.micro.delivery.infrastructure.web.dto.CreateDeliveryRequest;
import com.tecsup.app.micro.delivery.infrastructure.web.dto.DeliveryResponse;
import com.tecsup.app.micro.delivery.infrastructure.web.mapper.DeliveryDtoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/deliveries")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryApplicationService deliveryApplicationService;
    private final DeliveryDtoMapper deliveryDtoMapper;

    /**
     * Crear delivery.
     */
    @PostMapping
    public ResponseEntity<DeliveryResponse> createDelivery(
            @Valid @RequestBody CreateDeliveryRequest request
    ) {

        Delivery delivery =
                deliveryApplicationService.createDelivery(
                        request.getOrderId(),
                        request.getUserId(),
                        request.getDeliveryAddress(),
                        request.getDeliveryPersonName(),
                        request.getDeliveryPersonPhone()
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(deliveryDtoMapper.toResponse(delivery));
    }

    /**
     * Obtener delivery por ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DeliveryResponse> getDeliveryById(
            @PathVariable Long id
    ) {

        Delivery delivery =
                deliveryApplicationService.getDeliveryById(id);

        return ResponseEntity.ok(
                deliveryDtoMapper.toResponse(delivery)
        );
    }

    /**
     * Obtener delivery por orderId.
     */
    @GetMapping("/order/{orderId}")
    public ResponseEntity<DeliveryResponse> getDeliveryByOrderId(
            @PathVariable Long orderId
    ) {

        Delivery delivery =
                deliveryApplicationService.getDeliveryByOrderId(orderId);

        return ResponseEntity.ok(
                deliveryDtoMapper.toResponse(delivery)
        );
    }

    /**
     * Actualizar estado del delivery.
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<DeliveryResponse> updateDeliveryStatus(
            @PathVariable Long id,
            @RequestParam String status
    ) {

        Delivery delivery =
                deliveryApplicationService.updateDeliveryStatus(
                        id,
                        status
                );

        return ResponseEntity.ok(
                deliveryDtoMapper.toResponse(delivery)
        );
    }
}
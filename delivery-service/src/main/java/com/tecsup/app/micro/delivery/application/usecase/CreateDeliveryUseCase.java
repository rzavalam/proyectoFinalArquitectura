package com.tecsup.app.micro.delivery.application.usecase;

import com.tecsup.app.micro.delivery.domain.model.Delivery;
import com.tecsup.app.micro.delivery.domain.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CreateDeliveryUseCase {

    private final DeliveryRepository deliveryRepository;

    /**
     * Crea un nuevo delivery para una orden confirmada.
     */
    public Delivery execute(
            Long orderId,
            Long userId,
            String deliveryAddress,
            String deliveryPersonName,
            String deliveryPersonPhone
    ) {

        log.info(
                "[DELIVERY] Creating delivery. orderId={}, userId={}",
                orderId,
                userId
        );

        // ==========================================
        // 1. Validar datos
        // ==========================================

        validateDeliveryData(
                orderId,
                userId,
                deliveryAddress
        );

        // ==========================================
        // 2. Verificar que no exista delivery
        // ==========================================

        if (deliveryRepository.existsByOrderId(orderId)) {

            log.warn(
                    "[DELIVERY] Ya existe un delivery para orderId={}",
                    orderId
            );

            throw new IllegalArgumentException(
                    "Ya existe un delivery para la orden: " + orderId
            );
        }

        // ==========================================
        // 3. Crear Delivery en estado PENDING
        // ==========================================

        Delivery delivery = Delivery.create(
                orderId,
                userId,
                deliveryAddress,
                deliveryPersonName,
                deliveryPersonPhone
        );

        // ==========================================
        // 4. Guardar Delivery
        // ==========================================

        Delivery savedDelivery =
                deliveryRepository.save(delivery);

        log.info(
                "[DELIVERY] Delivery created. id={}, orderId={}, status={}",
                savedDelivery.getId(),
                savedDelivery.getOrderId(),
                savedDelivery.getStatus()
        );

        return savedDelivery;
    }

    /**
     * Valida los datos necesarios para crear el delivery.
     */
    private void validateDeliveryData(
            Long orderId,
            Long userId,
            String deliveryAddress
    ) {

        if (orderId == null) {
            throw new IllegalArgumentException(
                    "Order ID is required."
            );
        }

        if (userId == null) {
            throw new IllegalArgumentException(
                    "User ID is required."
            );
        }

        if (deliveryAddress == null ||
                deliveryAddress.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Delivery address is required."
            );
        }
    }
}

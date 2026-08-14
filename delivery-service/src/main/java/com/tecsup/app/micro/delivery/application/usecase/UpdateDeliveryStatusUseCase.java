package com.tecsup.app.micro.delivery.application.usecase;

import com.tecsup.app.micro.delivery.domain.exception.DeliveryNotFoundException;
import com.tecsup.app.micro.delivery.domain.model.Delivery;
import com.tecsup.app.micro.delivery.domain.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UpdateDeliveryStatusUseCase {

    private final DeliveryRepository deliveryRepository;

    public Delivery execute(
            Long id,
            String status
    ) {

        log.info(
                "[DELIVERY] Updating delivery status. id={}, status={}",
                id,
                status
        );

        // ==========================================
        // 1. Validar datos
        // ==========================================

        if (id == null) {
            throw new IllegalArgumentException(
                    "Delivery ID is required."
            );
        }

        if (status == null ||
                status.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Delivery status is required."
            );
        }

        // ==========================================
        // 2. Buscar Delivery
        // ==========================================

        Delivery delivery =
                deliveryRepository.findById(id)
                        .orElseThrow(() ->
                                new DeliveryNotFoundException(id)
                        );

        // ==========================================
        // 3. Actualizar estado
        // ==========================================

        switch (status.toUpperCase()) {

            case "PENDING":

                throw new IllegalArgumentException(
                        "No se puede actualizar manualmente a PENDING."
                );

            case "PICKED_UP":

                delivery.pickUp();
                break;

            case "DELIVERED":

                delivery.deliver();
                break;

            case "CANCELLED":

                delivery.cancel();
                break;

            default:

                throw new IllegalArgumentException(
                        "Estado de delivery no válido: " + status
                );
        }

        // ==========================================
        // 4. Guardar cambios
        // ==========================================

        Delivery updatedDelivery =
                deliveryRepository.save(delivery);

        log.info(
                "[DELIVERY] Delivery status updated. id={}, status={}",
                updatedDelivery.getId(),
                updatedDelivery.getStatus()
        );

        return updatedDelivery;
    }
}
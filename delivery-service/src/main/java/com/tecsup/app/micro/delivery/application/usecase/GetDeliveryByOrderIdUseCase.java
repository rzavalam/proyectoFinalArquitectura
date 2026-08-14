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
public class GetDeliveryByOrderIdUseCase {

    private final DeliveryRepository deliveryRepository;

    public Delivery execute(Long orderId) {

        log.debug(
                "[DELIVERY] Searching delivery by orderId: {}",
                orderId
        );

        return deliveryRepository.findByOrderId(orderId)
                .orElseThrow(() ->
                        new DeliveryNotFoundException(
                                "No se encontró un delivery para la orden: "
                                        + orderId
                        )
                );
    }
}
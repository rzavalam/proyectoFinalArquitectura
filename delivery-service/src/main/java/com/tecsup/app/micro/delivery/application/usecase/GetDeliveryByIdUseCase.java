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
public class GetDeliveryByIdUseCase {

    private final DeliveryRepository deliveryRepository;

    public Delivery execute(Long id) {

        log.debug(
                "[DELIVERY] Searching delivery by id: {}",
                id
        );

        return deliveryRepository.findById(id)
                .orElseThrow(() ->
                        new DeliveryNotFoundException(id)
                );
    }
}

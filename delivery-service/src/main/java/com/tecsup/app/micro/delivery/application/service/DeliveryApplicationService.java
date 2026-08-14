package com.tecsup.app.micro.delivery.application.service;

import com.tecsup.app.micro.delivery.application.usecase.CreateDeliveryUseCase;
import com.tecsup.app.micro.delivery.application.usecase.GetDeliveryByIdUseCase;
import com.tecsup.app.micro.delivery.application.usecase.GetDeliveryByOrderIdUseCase;
import com.tecsup.app.micro.delivery.application.usecase.UpdateDeliveryStatusUseCase;
import com.tecsup.app.micro.delivery.domain.model.Delivery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeliveryApplicationService {

    private final CreateDeliveryUseCase createDeliveryUseCase;
    private final GetDeliveryByIdUseCase getDeliveryByIdUseCase;
    private final GetDeliveryByOrderIdUseCase getDeliveryByOrderIdUseCase;
    private final UpdateDeliveryStatusUseCase updateDeliveryStatusUseCase;

    @Transactional
    public Delivery createDelivery(
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

        return createDeliveryUseCase.execute(
                orderId,
                userId,
                deliveryAddress,
                deliveryPersonName,
                deliveryPersonPhone
        );
    }

    @Transactional(readOnly = true)
    public Delivery getDeliveryById(Long id) {

        return getDeliveryByIdUseCase.execute(id);
    }

    @Transactional(readOnly = true)
    public Delivery getDeliveryByOrderId(Long orderId) {

        return getDeliveryByOrderIdUseCase.execute(orderId);
    }

    @Transactional
    public Delivery updateDeliveryStatus(
            Long id,
            String status
    ) {

        return updateDeliveryStatusUseCase.execute(
                id,
                status
        );
    }
}
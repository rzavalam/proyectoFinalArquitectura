package com.tecsup.app.micro.delivery.infrastructure.web.mapper;

import com.tecsup.app.micro.delivery.domain.model.Delivery;

import com.tecsup.app.micro.delivery.infrastructure.web.dto.DeliveryResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DeliveryDtoMapper {

    DeliveryResponse toResponse(Delivery delivery);
}

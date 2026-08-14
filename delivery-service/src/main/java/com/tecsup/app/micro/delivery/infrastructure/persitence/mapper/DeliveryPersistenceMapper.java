package com.tecsup.app.micro.delivery.infrastructure.persitence.mapper;

import com.tecsup.app.micro.delivery.domain.model.Delivery;

import com.tecsup.app.micro.delivery.infrastructure.persitence.entity.DeliveryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DeliveryPersistenceMapper {

    DeliveryEntity toEntity(Delivery delivery);

    Delivery toDomain(DeliveryEntity entity);
}
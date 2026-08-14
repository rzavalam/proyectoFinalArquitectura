package com.tecsup.app.micro.product.infrastructure.persistence.mapper;

import com.tecsup.app.micro.product.domain.model.Restaurant;
import com.tecsup.app.micro.product.infrastructure.persistence.entity.RestaurantEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RestaurantPersistenceMapper {

    Restaurant toDomain(RestaurantEntity entity);

    RestaurantEntity toEntity(Restaurant restaurant);

    List<Restaurant> toDomainList(
            List<RestaurantEntity> entities
    );
}
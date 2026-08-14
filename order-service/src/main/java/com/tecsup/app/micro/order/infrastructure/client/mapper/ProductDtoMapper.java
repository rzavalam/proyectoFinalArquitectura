package com.tecsup.app.micro.order.infrastructure.client.mapper;

import com.tecsup.app.micro.order.domain.model.Product;

import com.tecsup.app.micro.order.infrastructure.client.dto.ProductDto;
import org.mapstruct.Mapper;

/**
 * Mapper entre DTOs de presentación y modelo de dominio usando MapStruct
 */
@Mapper(componentModel = "spring")
public interface ProductDtoMapper {

    Product toDomain(ProductDto dto);

    ProductDto toDto(Product product);

}
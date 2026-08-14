package com.tecsup.app.micro.payment.infrastructure.client.mapper;


import com.tecsup.app.micro.payment.domain.model.Product;
import com.tecsup.app.micro.payment.infrastructure.client.dto.ProductDto;
import org.mapstruct.Mapper;

/**
 * Mapper entre DTOs de presentación y modelo de dominio usando MapStruct
 */
@Mapper(componentModel = "spring")
public interface ProductDtoMapper {

    Product toDomain(ProductDto dto);

    ProductDto toDto(Product product);

}
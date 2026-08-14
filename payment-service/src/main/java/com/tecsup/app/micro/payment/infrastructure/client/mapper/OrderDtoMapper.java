package com.tecsup.app.micro.payment.infrastructure.client.mapper;

import com.tecsup.app.micro.payment.domain.model.Order;
import com.tecsup.app.micro.payment.domain.model.OrderItem;
import com.tecsup.app.micro.payment.infrastructure.client.dto.OrderDto;
import com.tecsup.app.micro.payment.infrastructure.client.dto.OrderItemDto;
import org.mapstruct.Mapper;

/**
 * Mapper entre el DTO de order-service
 * y el modelo de dominio de payment-service.
 */
@Mapper(componentModel = "spring")
public interface OrderDtoMapper {

    Order toDomain(OrderDto dto);

    OrderDto toDto(Order order);

    OrderItem toDomain(OrderItemDto dto);

    OrderItemDto toDto(OrderItem item);
}

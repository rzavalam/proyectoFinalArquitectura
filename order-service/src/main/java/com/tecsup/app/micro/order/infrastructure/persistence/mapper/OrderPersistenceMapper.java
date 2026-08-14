package com.tecsup.app.micro.order.infrastructure.persistence.mapper;

import com.tecsup.app.micro.order.domain.model.Order;
import com.tecsup.app.micro.order.domain.model.OrderItem;
import com.tecsup.app.micro.order.infrastructure.persistence.entity.OrderEntity;
import com.tecsup.app.micro.order.infrastructure.persistence.entity.OrderItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface OrderPersistenceMapper {

    OrderEntity toEntity(Order order);

    Order toDomain(OrderEntity entity);

    List<Order> toDomainList(List<OrderEntity> entities);

    List<OrderEntity> toEntityList(List<Order> orders);

    OrderItemEntity toEntity(OrderItem item);

    OrderItem toDomain(OrderItemEntity entity);

    List<OrderItem> toDomainItems(List<OrderItemEntity> entities);

    List<OrderItemEntity> toEntityItems(List<OrderItem> items);

}
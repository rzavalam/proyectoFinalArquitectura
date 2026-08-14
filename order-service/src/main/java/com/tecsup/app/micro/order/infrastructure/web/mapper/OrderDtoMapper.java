package com.tecsup.app.micro.order.infrastructure.web.mapper;

import com.tecsup.app.micro.order.domain.model.Order;
import com.tecsup.app.micro.order.domain.model.OrderItem;
import com.tecsup.app.micro.order.domain.model.Product;
import com.tecsup.app.micro.order.domain.model.User;
import com.tecsup.app.micro.order.infrastructure.web.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface OrderDtoMapper {

    // ==========================================
    // Request DTO -> Domain
    // ==========================================

    Order toDomain(CreateOrderRequest dto);

    OrderItem toDomain(CreateOrderItemRequest dto);

    List<OrderItem> toDomainItems(List<CreateOrderItemRequest> dtos);

    // ==========================================
    // Domain -> Response DTO
    // ==========================================

    OrderResponse toResponse(Order order);

    List<OrderResponse> toResponseList(List<Order> orders);

    OrderItemResponse toResponse(OrderItem item);

    List<OrderItemResponse> toResponseItems(List<OrderItem> items);

    ProductResponse toResponse(Product product);

    UserResponse toResponse(User user);

}

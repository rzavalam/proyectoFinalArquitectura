package com.tecsup.app.micro.order.infrastructure.persistence.repository;

import com.tecsup.app.micro.order.infrastructure.persistence.entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositorio JPA de OrderItem
 * Interface de Spring Data JPA para operaciones de persistencia
 */
public interface JpaOrderItemRepository extends JpaRepository<OrderItemEntity, Long> {

    /**
     * Obtiene todos los items de una orden.
     */
    List<OrderItemEntity> findByOrderId(Long orderId);

    /**
     * Obtiene todos los items de un producto.
     */
    List<OrderItemEntity> findByProductId(Long productId);

}

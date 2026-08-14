package com.tecsup.app.micro.order.infrastructure.persistence.repository;

import com.tecsup.app.micro.order.domain.model.OrderStatus;
import com.tecsup.app.micro.order.infrastructure.persistence.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio JPA de Order
 * Interface de Spring Data JPA para operaciones de persistencia
 */
public interface JpaOrderRepository extends JpaRepository<OrderEntity, Long> {

    /**
     * Busca una orden por su número.
     */
    Optional<OrderEntity> findByOrderNumber(String orderNumber);

    /**
     * Obtiene todas las órdenes de un usuario.
     */
    List<OrderEntity> findByUserId(Long userId);

    /**
     * Obtiene las órdenes por estado.
     */
    List<OrderEntity> findByStatus(OrderStatus status);

    /**
     * Verifica si ya existe un número de orden.
     */
    boolean existsByOrderNumber(String orderNumber);

}

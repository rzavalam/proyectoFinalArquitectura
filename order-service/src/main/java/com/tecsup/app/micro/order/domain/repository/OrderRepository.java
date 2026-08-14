package com.tecsup.app.micro.order.domain.repository;

import com.tecsup.app.micro.order.domain.model.Order;

import java.util.List;
import java.util.Optional;

/**
 * Puerto del Repositorio de Orden (Interface)
 * Define el contrato para la persistencia sin depender de la implementación.
 * Esta interfaz pertenece al dominio y será implementada en la capa de infraestructura.
 */
public interface OrderRepository {

    /**
     * Obtiene todas las órdenes
     */
    List<Order> findAll();

    /**
     * Busca una orden por ID
     */
    Optional<Order> findById(Long id);

    /**
     * Busca una orden por su número
     */
    Optional<Order> findByOrderNumber(String orderNumber);

    /**
     * Obtiene todas las órdenes de un usuario
     */
    List<Order> findByUserId(Long userId);

    /**
     * Obtiene las órdenes por estado
     */
    List<Order> findByStatus(String status);

    /**
     * Guarda una nueva orden o actualiza una existente
     */
    Order save(Order order);

    /**
     * Elimina una orden por ID
     */
    void deleteById(Long id);

    /**
     * Verifica si existe una orden con el ID dado
     */
    boolean existsById(Long id);

    /**
     * Verifica si existe una orden con el número dado
     */
    boolean existsByOrderNumber(String orderNumber);
}

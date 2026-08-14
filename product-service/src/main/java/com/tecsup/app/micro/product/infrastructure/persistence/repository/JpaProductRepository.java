package com.tecsup.app.micro.product.infrastructure.persistence.repository;

import com.tecsup.app.micro.product.infrastructure.persistence.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Repositorio JPA de Producto
 * Interface de Spring Data JPA para operaciones de persistencia
 */
public interface JpaProductRepository extends JpaRepository<ProductEntity, Long> {

    List<ProductEntity> findByCreatedBy(Long userId);

    List<ProductEntity> findByRestaurantId(Long restaurantId);

    List<ProductEntity> findByAvailableTrue();
}

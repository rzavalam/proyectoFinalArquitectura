package com.tecsup.app.micro.product.domain.repository;

import com.tecsup.app.micro.product.domain.model.Product;

import java.util.List;
import java.util.Optional;

/**
 * Puerto del Repositorio de Producto (Interface)
 * Define el contrato para la persistencia sin depender de la implementación
 * Esta interfaz pertenece al dominio y será implementada en la capa de infraestructura
 */
public interface ProductRepository {

    List<Product> findAll();

    Optional<Product> findById(Long id);

    List<Product> findByCreatedBy(Long userId);

    List<Product> findByRestaurantId(Long restaurantId);

    List<Product> findAvailableProducts();

    Product save(Product product);

    void deleteById(Long id);

    boolean existsById(Long id);
}

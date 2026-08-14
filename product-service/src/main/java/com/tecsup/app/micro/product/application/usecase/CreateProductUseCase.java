package com.tecsup.app.micro.product.application.usecase;

import com.tecsup.app.micro.product.domain.exception.InvalidProductDataException;
import com.tecsup.app.micro.product.domain.model.Product;
import com.tecsup.app.micro.product.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Caso de uso: Crear un nuevo producto
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class CreateProductUseCase {
    
    private final ProductRepository productRepository;

    public Product execute(Product product) {

        log.debug("Ejecutando CreateProductUseCase para el producto: {}", product.getName());

        if (!product.isValid()) {
            throw new InvalidProductDataException(
                    "Los datos del producto no son válidos. El restaurante, el nombre y un precio válido son obligatorios."
            );
        }

        Product savedProduct = productRepository.save(product);

        log.info(
                "Producto creado correctamente con ID: {}",
                savedProduct.getId()
        );

        return savedProduct;
    }
}

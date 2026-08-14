package com.tecsup.app.micro.product.application.usecase;

import com.tecsup.app.micro.product.domain.exception.InvalidProductDataException;
import com.tecsup.app.micro.product.domain.exception.ProductNotFoundException;
import com.tecsup.app.micro.product.domain.model.Product;
import com.tecsup.app.micro.product.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Caso de uso: Actualizar un producto existente
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class UpdateProductUseCase {

    private final ProductRepository productRepository;

    public Product execute(Long id, Product productDetails) {

        log.debug(
                "Executing UpdateProductUseCase for id: {}",
                id
        );

        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException(id)
                );

        if (!productDetails.isValid()) {
            throw new InvalidProductDataException(
                    "Invalid product data. Restaurant, name and valid price are required."
            );
        }

        existingProduct.setRestaurantId(
                productDetails.getRestaurantId()
        );

        existingProduct.setName(
                productDetails.getName()
        );

        existingProduct.setDescription(
                productDetails.getDescription()
        );

        existingProduct.setPrice(
                productDetails.getPrice()
        );

        existingProduct.setImageUrl(
                productDetails.getImageUrl()
        );

        existingProduct.setAvailable(
                productDetails.isAvailable()
        );

        Product updatedProduct =
                productRepository.save(existingProduct);

        log.info(
                "Product updated successfully with id: {}",
                updatedProduct.getId()
        );

        return updatedProduct;
    }
}


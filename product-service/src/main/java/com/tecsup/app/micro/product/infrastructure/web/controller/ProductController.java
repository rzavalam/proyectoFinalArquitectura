package com.tecsup.app.micro.product.infrastructure.web.controller;


import com.tecsup.app.micro.product.application.service.ProductApplicationService;
import com.tecsup.app.micro.product.domain.model.Product;
import com.tecsup.app.micro.product.infrastructure.web.dto.CreateProductRequest;
import com.tecsup.app.micro.product.infrastructure.web.dto.ProductResponse;
import com.tecsup.app.micro.product.infrastructure.web.dto.UpdateProductRequest;
import com.tecsup.app.micro.product.infrastructure.web.mapper.ProductDtoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST de Productos
 */
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductApplicationService productApplicationService;
    private final ProductDtoMapper productDtoMapper;

    /**
     * Obtiene todos los productos.
     */
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        log.info("REST request to get all products");
        List<Product> products =  productApplicationService.getAllProducts();
        return ResponseEntity.ok(productDtoMapper.toResponseList(products));
    }

    /**
     * Obtiene productos disponibles.
     */
    @GetMapping("/available")
    public ResponseEntity<List<ProductResponse>> getAvailableProducts() {
        log.info("REST request to get available products");
        List<Product> products = productApplicationService.getAvailableProducts();
        return ResponseEntity.ok(productDtoMapper.toResponseList(products)
        );
    }

    /**
     * Obtiene productos de un restaurante.
     */
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<ProductResponse>> getProductsByRestaurant(@PathVariable Long restaurantId) {

        log.info( "REST request to get products for restaurant: {}",   restaurantId );

        List<Product> products = productApplicationService.getProductsByRestaurant(restaurantId);

        return ResponseEntity.ok(productDtoMapper.toResponseList(products));
    }

    /**
     * Crea un producto.
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody CreateProductRequest request) {

        log.info("REST request to create product: {}",request.getName());
        Product product = productDtoMapper.toDomain(request);
        Product createdProduct = productApplicationService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(productDtoMapper.toResponse(createdProduct));
    }

    /**
     * Actualiza un producto.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id, @Valid @RequestBody UpdateProductRequest request) {
        log.info( "REST request to update product: {}",id);
        Product product = productDtoMapper.toDomain(request);
        Product updatedProduct =  productApplicationService.updateProduct(id, product);
        return ResponseEntity.ok(productDtoMapper.toResponse(updatedProduct));
    }

    /**
     * Elimina un producto.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        log.info("REST request to delete product: {}",id);
        productApplicationService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Health check.
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok(
                "Product Service running with Clean Architecture!"
        );
    }

    /**
     * Obtiene un producto por ID.
     */
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ProductResponse> getProductById(
            @PathVariable Long id,
            @RequestHeader(value = "Authorization",required = false) String authHeader) {

        log.info("REST request to get product by id: {}",id);
        log.info("Entró al controlador ProductController");
        // Extraer JWT del header
        String jwtToken = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwtToken = authHeader.substring(7);
        } else {
            log.warn("No Authorization header with Bearer token found for product retrieval");
        }

        log.info("jwtToken extracted for product retrieval: {}", jwtToken != null);

        Product product =productApplicationService.getProductById(id, jwtToken);

        return ResponseEntity.ok(productDtoMapper.toResponse(product));
    }
}

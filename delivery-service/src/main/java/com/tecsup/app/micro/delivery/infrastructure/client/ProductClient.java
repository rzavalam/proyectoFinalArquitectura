package com.tecsup.app.micro.delivery.infrastructure.client;

import com.tecsup.app.micro.delivery.domain.model.Product;
import com.tecsup.app.micro.delivery.infrastructure.client.dto.ProductDto;
import com.tecsup.app.micro.delivery.infrastructure.client.mapper.ProductDtoMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductClient {

    private final RestTemplate restTemplate;
    private final ProductDtoMapper productDtoMapper;
    //    @Value("${product.service.url:http://localhost:8082}")
    @Value("${product.service.url}")
    private String productServiceUrl;

    /**
     * Obtiene un producto por ID desde product-service
     *
     * @param productId ID del producto
     * @param jwtToken JWT para autenticación
     * @return Producto del dominio
     */
    @CircuitBreaker(name = "productService", fallbackMethod = "getProductFallback")
    @Retry(name = "productService")
    public Product getProductById(Long productId, String jwtToken) {

        log.info("Calling Product Service to get product with id: {}", productId);
        log.info("PRODUCT SERVICE URL CONFIGURADA = [{}]", productServiceUrl);
        log.info("Calling Product Service to get product with id: {}", productId);
        String url = this.productServiceUrl + "/api/products/" + productId;

        // Propagación del JWT
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        if (jwtToken != null && !jwtToken.isEmpty()) {
            headers.setBearerAuth(jwtToken);
        } else {
            log.warn("No JWT token provided for Product Service call");
        }

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {

            ResponseEntity<ProductDto> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    ProductDto.class
            );

            log.info("Product retrieved successfully from product-service: {}", response.getBody());

            if (response.getBody() == null) {
                throw new RuntimeException("Product Service returned an empty response.");
            }

            return productDtoMapper.toDomain(response.getBody());

        } catch (Exception e) {

            log.error("Error calling Product Service: {}", e.getMessage());

            throw new RuntimeException("Error calling Product Service: " + e.getMessage());
        }
    }

    /**
     * Método de versión anterior (sin JWT)
     */
    public Product getProductById(Long productId) {
        return getProductById(productId, null);
    }

    /**
     * Fallback cuando product-service no está disponible.
     */
    public Product getProductFallback(Long productId,
                                      String jwtToken,
                                      Throwable throwable) {

        log.warn(
                "FALLBACK: Product Service no disponible para productId: {}. Razón: {}",
                productId,
                throwable.getMessage()
        );

        return Product.builder()
                .id(productId)
                .name("Producto no disponible")
                .description("Servicio no disponible")
                .price(java.math.BigDecimal.ZERO)
                .build();
    }
}
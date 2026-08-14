package com.tecsup.app.micro.product.application.usecase;

import com.tecsup.app.micro.product.domain.model.Product;
import com.tecsup.app.micro.product.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class GetProductsByRestaurantUseCase {

    private final ProductRepository productRepository;

    public List<Product> execute(Long restaurantId) {

        log.debug(
                "Executing GetProductsByRestaurantUseCase for restaurantId: {}",
                restaurantId
        );

        return productRepository.findByRestaurantId(restaurantId);
    }
}
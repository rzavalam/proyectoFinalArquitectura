package com.tecsup.app.micro.product.domain.repository;

import com.tecsup.app.micro.product.domain.model.Restaurant;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository {

    List<Restaurant> findAll();

    Optional<Restaurant> findById(Long id);

    List<Restaurant> findActiveRestaurants();

    Restaurant save(Restaurant restaurant);

    void deleteById(Long id);

    boolean existsById(Long id);
}

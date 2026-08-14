package com.tecsup.app.micro.product.infrastructure.persistence.repository;

import com.tecsup.app.micro.product.domain.model.Restaurant;
import com.tecsup.app.micro.product.domain.repository.RestaurantRepository;
import com.tecsup.app.micro.product.infrastructure.persistence.entity.RestaurantEntity;
import com.tecsup.app.micro.product.infrastructure.persistence.mapper.RestaurantPersistenceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RestaurantRepositoryImpl
        implements RestaurantRepository {

    private final JpaRestaurantRepository jpaRepository;

    private final RestaurantPersistenceMapper mapper;

    @Override
    public List<Restaurant> findAll() {
        return mapper.toDomainList(jpaRepository.findAll());
    }

    @Override
    public Optional<Restaurant> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Restaurant> findActiveRestaurants() {
        return mapper.toDomainList(jpaRepository.findByActiveTrue());
    }

    @Override
    public Restaurant save(Restaurant restaurant) {

        RestaurantEntity entity = mapper.toEntity(restaurant);
        return mapper.toDomain(jpaRepository.save(entity)
        );
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepository.existsById(id);
    }
}

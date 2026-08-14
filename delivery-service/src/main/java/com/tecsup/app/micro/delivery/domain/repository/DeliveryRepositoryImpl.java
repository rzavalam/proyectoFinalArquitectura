package com.tecsup.app.micro.delivery.domain.repository;

import com.tecsup.app.micro.delivery.domain.model.Delivery;
import com.tecsup.app.micro.delivery.domain.repository.DeliveryRepository;
import com.tecsup.app.micro.delivery.infrastructure.persitence.entity.DeliveryEntity;
import com.tecsup.app.micro.delivery.infrastructure.persitence.mapper.DeliveryPersistenceMapper;
import com.tecsup.app.micro.delivery.infrastructure.persitence.repository.JpaDeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DeliveryRepositoryImpl implements DeliveryRepository {

    private final JpaDeliveryRepository jpaDeliveryRepository;
    private final DeliveryPersistenceMapper deliveryPersistenceMapper;

    @Override
    public Delivery save(Delivery delivery) {

        DeliveryEntity entity =
                deliveryPersistenceMapper.toEntity(delivery);

        DeliveryEntity savedEntity =
                jpaDeliveryRepository.save(entity);

        return deliveryPersistenceMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Delivery> findById(Long id) {

        return jpaDeliveryRepository.findById(id)
                .map(deliveryPersistenceMapper::toDomain);
    }

    @Override
    public Optional<Delivery> findByOrderId(Long orderId) {

        return jpaDeliveryRepository.findByOrderId(orderId)
                .map(deliveryPersistenceMapper::toDomain);
    }

    @Override
    public List<Delivery> findAll() {

        return jpaDeliveryRepository.findAll()
                .stream()
                .map(deliveryPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public boolean existsByOrderId(Long orderId) {

        return jpaDeliveryRepository.existsByOrderId(orderId);
    }

    @Override
    public boolean existsById(Long id) {

        return jpaDeliveryRepository.existsById(id);
    }

    @Override
    public void deleteById(Long id) {

        jpaDeliveryRepository.deleteById(id);
    }
}

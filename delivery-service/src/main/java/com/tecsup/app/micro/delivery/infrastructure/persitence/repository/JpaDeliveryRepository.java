package com.tecsup.app.micro.delivery.infrastructure.persitence.repository;

import com.tecsup.app.micro.delivery.infrastructure.persitence.entity.DeliveryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface   JpaDeliveryRepository         extends JpaRepository<DeliveryEntity, Long> {

    Optional<DeliveryEntity> findByOrderId(Long orderId);

    boolean existsByOrderId(Long orderId);
}
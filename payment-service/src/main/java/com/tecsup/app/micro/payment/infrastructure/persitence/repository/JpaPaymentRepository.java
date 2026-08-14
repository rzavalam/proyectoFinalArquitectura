package com.tecsup.app.micro.payment.infrastructure.persitence.repository;


import com.tecsup.app.micro.payment.infrastructure.persitence.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaPaymentRepository
        extends JpaRepository<PaymentEntity, Long> {

    Optional<PaymentEntity> findByOrderId(Long orderId);

    Optional<PaymentEntity> findByPaymentNumber(String paymentNumber);

    List<PaymentEntity> findByStatus(String status);

    boolean existsByOrderId(Long orderId);

    boolean existsByPaymentNumber(String paymentNumber);
}

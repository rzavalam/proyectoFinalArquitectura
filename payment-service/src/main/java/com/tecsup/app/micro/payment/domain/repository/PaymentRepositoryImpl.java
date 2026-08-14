package com.tecsup.app.micro.payment.domain.repository;

import com.tecsup.app.micro.payment.domain.model.Payment;
import com.tecsup.app.micro.payment.infrastructure.persitence.entity.PaymentEntity;
import com.tecsup.app.micro.payment.infrastructure.persitence.mapper.PaymentPersistenceMapper;
import com.tecsup.app.micro.payment.infrastructure.persitence.repository.JpaPaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class PaymentRepositoryImpl implements PaymentRepository {

    private final JpaPaymentRepository jpaPaymentRepository;
    private final PaymentPersistenceMapper mapper;

    @Override
    public List<Payment> findAll() {

        return mapper.toDomainList(
                jpaPaymentRepository.findAll()
        );
    }

    @Override
    public Optional<Payment> findById(Long id) {

        return jpaPaymentRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Payment> findByPaymentNumber(String paymentNumber) {

        return jpaPaymentRepository
                .findByPaymentNumber(paymentNumber)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Payment> findByOrderId(Long orderId) {

        return jpaPaymentRepository
                .findByOrderId(orderId)
                .map(mapper::toDomain);
    }

    @Override
    public List<Payment> findByStatus(String status) {

        return mapper.toDomainList(
                jpaPaymentRepository.findByStatus(status)
        );
    }

    @Override
    public Payment save(Payment payment) {

        PaymentEntity entity = mapper.toEntity(payment);

        PaymentEntity savedEntity =
                jpaPaymentRepository.save(entity);

        return mapper.toDomain(savedEntity);
    }

    @Override
    public void deleteById(Long id) {

        jpaPaymentRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {

        return jpaPaymentRepository.existsById(id);
    }

    @Override
    public boolean existsByOrderId(Long orderId) {

        return jpaPaymentRepository.existsByOrderId(orderId);
    }

    @Override
    public boolean existsByPaymentNumber(String paymentNumber) {

        return jpaPaymentRepository
                .existsByPaymentNumber(paymentNumber);
    }
}

package com.tecsup.app.micro.payment.infrastructure.persitence.mapper;

import com.tecsup.app.micro.payment.domain.model.Payment;
import com.tecsup.app.micro.payment.infrastructure.persitence.entity.PaymentEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(
        componentModel = "spring"
)
public interface PaymentPersistenceMapper {

    PaymentEntity toEntity(Payment payment);

    Payment toDomain(PaymentEntity entity);

    List<Payment> toDomainList(List<PaymentEntity> entities);
}
package com.tecsup.app.micro.payment.infrastructure.web.mapper;

import com.tecsup.app.micro.payment.domain.model.Payment;
import com.tecsup.app.micro.payment.infrastructure.web.dto.PaymentResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentDtoMapper {

    PaymentResponse toResponse(Payment payment);
}

package com.tecsup.app.micro.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRejectedEvent extends DomainEvent {

    /**
     * ID de la orden asociada al pago.
     */
    private Long orderId;

    /**
     * Código del error producido durante el pago.
     */
    private String errorCode;

    /**
     * Descripción del motivo del rechazo.
     */
    private String reason;

    /**
     * Fecha y hora del rechazo.
     */
    private LocalDateTime timestamp;

    /**
     * La clave del evento Kafka será el orderId.
     */
    @Override
    public String getKey() { return orderId != null ? orderId.toString() : null; }
}

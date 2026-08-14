package com.tecsup.app.micro.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentApprovedEvent extends DomainEvent {

    /**
     * ID de la orden asociada al pago.
     */
    private Long orderId;

    /**
     * ID de la transacción generada por el proveedor de pagos.
     */
    private String transactionId;

    /**
     * Monto del pago aprobado.
     */
    private BigDecimal amount;

    /**
     * Fecha y hora de aprobación.
     */
    private LocalDateTime timestamp;

    /**
     * La clave del evento Kafka será el orderId.
     */
    @Override
    public String getKey() {
        return orderId != null ? orderId.toString() : null;
    }
}

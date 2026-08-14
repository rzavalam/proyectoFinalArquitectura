package com.tecsup.app.micro.delivery.domain.exception;

public class PaymentNotFoundException extends RuntimeException {

    public PaymentNotFoundException(Long id) {
        super("No se encontró el pago con ID: " + id);
    }

    public PaymentNotFoundException(String message) {
        super(message);
    }
}

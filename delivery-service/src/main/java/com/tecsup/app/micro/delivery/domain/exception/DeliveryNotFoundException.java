package com.tecsup.app.micro.delivery.domain.exception;

public class DeliveryNotFoundException extends RuntimeException {

    public DeliveryNotFoundException(Long id) {
        super("Delivery no encontrado con ID: " + id);
    }

    public DeliveryNotFoundException(String message) {
        super(message);
    }
}

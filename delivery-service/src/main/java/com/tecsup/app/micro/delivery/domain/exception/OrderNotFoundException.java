package com.tecsup.app.micro.delivery.domain.exception;

public class OrderNotFoundException extends RuntimeException {


    public OrderNotFoundException(Long id) {
        super("Product not found with id: " + id);
    }
}

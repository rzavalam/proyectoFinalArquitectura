package com.tecsup.app.micro.order.domain.exception;

public class UserServiceUnavailableException extends RuntimeException {

    public UserServiceUnavailableException(Throwable cause) {
        super("User Service unavailable", cause);
    }
}

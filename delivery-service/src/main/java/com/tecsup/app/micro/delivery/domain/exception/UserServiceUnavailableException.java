package com.tecsup.app.micro.delivery.domain.exception;

public class UserServiceUnavailableException extends RuntimeException {

    public UserServiceUnavailableException(Throwable cause) {
        super("User Service unavailable", cause);
    }
}

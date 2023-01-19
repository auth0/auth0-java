package com.auth0.exception;

public class ClientAssertionSigningException extends RuntimeException {

    public ClientAssertionSigningException(String message) {
        super(message);
    }

    public ClientAssertionSigningException(String message, Throwable cause) {
        super(message, cause);
    }
}

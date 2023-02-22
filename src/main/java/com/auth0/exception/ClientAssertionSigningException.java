package com.auth0.exception;

/**
 * Reqpresents an exception when creating the signed client assertion.
 */
public class ClientAssertionSigningException extends RuntimeException {

    /**
     * Create a new instance.
     *
     * @param message the message of the exception.
     */
    public ClientAssertionSigningException(String message) {
        super(message);
    }

    /**
     * Create a new instance.
     *
     * @param message the message of the exception.
     * @param cause the cause of the exception.
     */
    public ClientAssertionSigningException(String message, Throwable cause) {
        super(message, cause);
    }
}

package com.auth0.exception;


/**
 * Represents an error when attempting to retrieve a public key.
 * @see com.auth0.utils.tokens.PublicKeyProvider
 */
@SuppressWarnings("unused")
public class PublicKeyException extends Exception {

    /**
     * Creates a new {@code PublicKeyException}
     *
     * @param message the exception message
     */
    public PublicKeyException(String message) {
        super(message);
    }

    /**
     * Creates a new {@code PublicKeyException}
     *
     * @param cause the cause of the exception
     */
    public PublicKeyException(Throwable cause) {
        super(cause);
    }

    /**
     * Creates a new {@code PublicKeyException}
     *
     * @param message the exception message
     * @param cause the cause of the exception
     */
    public PublicKeyException(String message, Throwable cause) {
        super(message, cause);
    }

}

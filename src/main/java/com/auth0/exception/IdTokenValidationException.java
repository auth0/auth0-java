package com.auth0.exception;

/**
 * Represents an error during the validation of an OIDC-compliant token.
 */
public class IdTokenValidationException extends RuntimeException {

    /**
     * Creates a new {@code IdTokenValidationException}.
     *
     * @param message the exception message.
     */
    public IdTokenValidationException(String message) {
        super(message);
    }

    /**
     * Creates a new {@code IdTokenValidationException}.
     *
     * @param message the exception message.
     * @param cause the cause of the exception.
     */
    public IdTokenValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}

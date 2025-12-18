package com.auth0.client.mgmt.core;

/**
 * Exception thrown when OAuth token fetching fails.
 *
 * <p>This exception is thrown when there are issues obtaining an access token
 * using the OAuth 2.0 client credentials flow, such as:
 * <ul>
 *   <li>Invalid client credentials</li>
 *   <li>Network connectivity issues</li>
 *   <li>Auth0 service unavailability</li>
 *   <li>Invalid audience or base URL</li>
 * </ul>
 */
public class OAuthTokenException extends RuntimeException {

    /**
     * Creates a new OAuth token exception with the specified message.
     *
     * @param message The error message
     */
    public OAuthTokenException(String message) {
        super(message);
    }

    /**
     * Creates a new OAuth token exception with the specified message and cause.
     *
     * @param message The error message
     * @param cause The underlying cause
     */
    public OAuthTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new OAuth token exception with the specified cause.
     *
     * @param cause The underlying cause
     */
    public OAuthTokenException(Throwable cause) {
        super(cause);
    }
}

package com.auth0.api;

/**
 * Exception that wraps errors when creating a body for a request
 */
public class RequestBodyBuildException extends RuntimeException {

    public RequestBodyBuildException(String message, Throwable cause) {
        super(message, cause);
    }

}

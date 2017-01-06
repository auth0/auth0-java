package com.auth0.exception;

public class RequestFailedException extends Auth0Exception {

    public RequestFailedException(String message, Throwable cause) {
        super("Request failed: " + message, cause);
    }

    public RequestFailedException(String message) {
        super("Request failed: " + message);
    }
}

package com.auth0;

public class RequestFailedException extends Exception {

    public RequestFailedException(String message, Throwable cause) {
        super("Request failed: " + message, cause);
    }

    public RequestFailedException(String message) {
        super("Request failed: " + message);
    }
}

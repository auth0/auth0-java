package com.auth0.net;

import java.io.IOException;

public class RequestFailedException extends IOException {

    public RequestFailedException(String message, Throwable cause) {
        super("Request failed: " + message, cause);
    }

    public RequestFailedException(String message) {
        super("Request failed: " + message);
    }
}

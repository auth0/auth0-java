package com.auth0.exception;

import java.io.IOException;

public abstract class Auth0Exception extends IOException {

    public Auth0Exception(String message) {
        super(message);
    }

    public Auth0Exception(String message, Throwable cause) {
        super(message, cause);
    }
}

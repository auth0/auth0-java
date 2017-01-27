package com.auth0.exception;

import java.io.IOException;

/**
 * Class that represents an error captured when executing an http request to the Auth0 Server.
 */
@SuppressWarnings("WeakerAccess")
public class Auth0Exception extends IOException {

    public Auth0Exception(String message) {
        super(message);
    }

    public Auth0Exception(String message, Throwable cause) {
        super(message, cause);
    }
}

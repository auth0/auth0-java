package com.auth0.exception;

import java.util.Map;

public class AuthenticationException extends Auth0Exception {

    private String code;
    private String error;
    private String description;
    private int statusCode;

    public AuthenticationException(String payload, int statusCode, Throwable cause) {
        super(createMessage(payload, statusCode), cause);
        this.description = payload;
        this.statusCode = statusCode;
    }

    public AuthenticationException(Map<String, Object> values, int statusCode) {
        super(createMessage((String) values.get("error_description"), statusCode));
        this.code = (String) values.get("code");
        this.error = (String) values.get("error");
        this.description = (String) values.get("error_description");
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getCode() {
        return code;
    }

    public String getError() {
        return error;
    }

    public String getDescription() {
        return description;
    }

    private static String createMessage(String description, int statusCode) {
        return String.format("Authentication failed with status code %d: %s", statusCode, description);
    }
}

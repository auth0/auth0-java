package com.auth0.exception;

import java.util.Map;

public class APIException extends Auth0Exception {

    private String error;
    private String description;
    private int statusCode;

    public APIException(String payload, int statusCode, Throwable cause) {
        super(createMessage(payload, statusCode), cause);
        this.description = payload;
        this.statusCode = statusCode;
    }

    public APIException(Map<String, Object> values, int statusCode) {
        super(createMessage(obtainExceptionMessage(values), statusCode));
        this.error = obtainExceptionError(values);
        this.description = obtainExceptionMessage(values);
        this.statusCode = statusCode;
    }

    private static String obtainExceptionMessage(Map<String, Object> values) {
        if (values.containsKey("error_description")) {
            return (String) values.get("error_description");
        }
        if (values.containsKey("description")) {
            return (String) values.get("description");
        }
        if (values.containsKey("message")) {
            return (String) values.get("message");
        }
        if (values.containsKey("error")) {
            return (String) values.get("error");
        }
        return "Unknown exception";
    }

    private static String obtainExceptionError(Map<String, Object> values) {
        if (values.containsKey("errorCode")) {
            return (String) values.get("errorCode");
        }
        if (values.containsKey("error")) {
            return (String) values.get("error");
        }
        if (values.containsKey("code")) {
            return (String) values.get("code");
        }
        return "Unknown error";
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getError() {
        return error;
    }

    public String getDescription() {
        return description;
    }

    private static String createMessage(String description, int statusCode) {
        return String.format("Request failed with status code %d: %s", statusCode, description);
    }
}

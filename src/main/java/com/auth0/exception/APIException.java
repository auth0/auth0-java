package com.auth0.exception;

import java.util.Map;

/**
 * Class that represents an Auth0 Server error captured from a http response. Provides different methods to get a clue of why the request failed.
 * i.e.:
 * <pre>
 * {@code
 * {
 *      statusCode: 400,
 *      description: "Query validation error: 'String 'users' does not match pattern. Must be a comma separated list of the following values: name,strategy,options,enabled_clients,id,provisioning_ticket_url' on property fields (A comma separated list of fields to include or exclude (depending on include_fields) from the result, empty to retrieve all fields).",
 *      error: "invalid_query_string"
 * }
 * }
 * </pre>
 */
@SuppressWarnings("WeakerAccess")
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

    /**
     * Getter for the Http Status Code received in the response.
     * i.e. a {@code status_code=403} would mean that the token has an insufficient scope.
     *
     * @return the status code.
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * Getter for the exception error code.
     * i.e. a {@code error=invalid_query_string} would mean that the query parameters sent in the request were invalid.
     *
     * @return the error code.
     */
    public String getError() {
        return error;
    }

    /**
     * Getter for the exception user friendly description of why the request failed.
     * i.e. the description may say which query parameters are valid for that endpoint.
     *
     * @return the description.
     */
    public String getDescription() {
        return description;
    }

    private static String createMessage(String description, int statusCode) {
        return String.format("Request failed with status code %d: %s", statusCode, description);
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
}

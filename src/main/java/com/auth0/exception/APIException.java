package com.auth0.exception;

import java.util.Collections;
import java.util.Map;

/**
 * Represents an Auth0 Server error captured from an HTTP response. Provides different methods to determine why the request failed.
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

    private static final String ERROR_DESCRIPTION = "error_description";
    private static final String DESCRIPTION = "description";
    private static final String MESSAGE = "message";
    private static final String ERROR = "error";
    private static final String ERROR_CODE = "errorCode";
    private static final String CODE = "code";

    private String error;
    private final String description;
    private final int statusCode;
    private Map<String, Object> values;

    public APIException(String payload, int statusCode, Throwable cause) {
        super(createMessage(payload, statusCode), cause);
        this.description = payload;
        this.statusCode = statusCode;
    }

    public APIException(Map<String, Object> values, int statusCode) {
        super(createMessage(obtainExceptionMessage(values), statusCode));
        this.values = Collections.unmodifiableMap(values);
        this.error = obtainExceptionError(this.values);
        this.description = obtainExceptionMessage(this.values);
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
     * Returns a value from the error map, if any.
     *
     * @param key key of the value to return
     * @return the value if found or null
     */
    public Object getValue(String key) {
        if (values == null) {
            return null;
        }
        return values.get(key);
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

    /**
     * @return {@code true} when an MFA code is required to authenticate, {@code false} otherwise.
     */
    public boolean isMultifactorRequired() {
        return "mfa_required".equals(error);
    }

    /**
     * @return {@code true} when the username and/or password used for authentication are invalid, {@code false} otherwise.
     */
    public boolean isInvalidCredentials() {
        return "invalid_user_password".equals(error)
                || "invalid_grant".equals(error) && "Wrong email or password.".equals(description);
    }

    /**
     * @return {@code true} when MFA is required and the user is not enrolled, {@code false} otherwise.
     */
    public boolean isMultifactorEnrollRequired() {
        return "unsupported_challenge_type".equals(error);
    }

    /**
     * @return {@code true} when Bot Protection flags the request as suspicious, {@code false} otherwise.
     */
    public boolean isVerificationRequired() {
        return "requires_verification".equals(error);
    }

    /**
     * @return {@code true} when the MFA Token used on the login request is malformed or has expired, {@code false} otherwise.
     */
    public boolean isMultifactorTokenInvalid() {
        return "expired_token".equals(error) && "mfa_token is expired".equals(description)
                || "invalid_grant".equals(error) && "Malformed mfa_token".equals(description);
    }

    /**
     * @return {@code true} when authenticating with web-based authentiction and the resource server denied access per the OAuth 2 spec, {@code false} otherwise.
     */
    public boolean isAccessDenied() {
        return "access_denied".equals(error);
    }

    private static String createMessage(String description, int statusCode) {
        return String.format("Request failed with status code %d: %s", statusCode, description);
    }

    private static String obtainExceptionMessage(Map<String, Object> values) {
        if (values.containsKey(ERROR_DESCRIPTION)) {
            return toStringOrNull(values.get(ERROR_DESCRIPTION));
        }
        if (values.containsKey(DESCRIPTION)) {
            Object description = values.get(DESCRIPTION);
            if (description instanceof String) {
                return (String) description;
            } else if (description instanceof Map) {
                @SuppressWarnings("unchecked")
                PasswordStrengthErrorParser policy = new PasswordStrengthErrorParser((Map<String, Object>) description);
                return policy.getDescription();
            }
        }
        if (values.containsKey(MESSAGE)) {
            return toStringOrNull(values.get(MESSAGE));
        }
        if (values.containsKey(ERROR)) {
            return toStringOrNull(values.get(ERROR));
        }
        return "Unknown exception";
    }

    private static String obtainExceptionError(Map<String, Object> values) {
        if (values.containsKey(ERROR_CODE)) {
            return toStringOrNull(values.get(ERROR_CODE));
        }
        if (values.containsKey(ERROR)) {
            return toStringOrNull(values.get(ERROR));
        }
        if (values.containsKey(CODE)) {
            return toStringOrNull(values.get(CODE));
        }
        return "Unknown error";
    }

    private static String toStringOrNull(Object obj) {
        return obj != null ? obj.toString() : null;
    }
}

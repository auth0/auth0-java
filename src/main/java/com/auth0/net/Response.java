package com.auth0.net;

import java.util.Map;

/**
 * Represents the response of an HTTP request executed by {@link Request}.
 *
 * @param <T> the type of the parsed response body.
 */
public interface Response<T> {

    /**
     * @return the HTTP response headers.
     */
    Map<String, String> getHeaders();

    /**
     * @return the response body.
     */
    T getBody();

    /**
     * @return the HTTP status code.
     */
    int getStatusCode();

    TokenQuotaBucket getClientQuotaLimit();

    TokenQuotaBucket getOrganizationQuotaLimit();
}

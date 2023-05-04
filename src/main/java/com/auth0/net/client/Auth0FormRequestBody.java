package com.auth0.net.client;

import java.util.Map;

/**
 * Represents the body of a application/x-www-form-urlencoded request
 */
public class Auth0FormRequestBody {

    private final Map<String, Object> params;

    public Auth0FormRequestBody(Map<String, Object> params) {
        this.params = params;
    }

    public Map<String, Object> getParams() {
        return params;
    }
}

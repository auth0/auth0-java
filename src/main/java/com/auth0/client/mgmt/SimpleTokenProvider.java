package com.auth0.client.mgmt;

import com.auth0.exception.Auth0Exception;
import com.auth0.utils.Asserts;

public class SimpleTokenProvider implements TokenProvider {
    private final String apiToken;

    public static SimpleTokenProvider create(String apiToken) {
        return new SimpleTokenProvider(apiToken);
    }

    private SimpleTokenProvider(String apiToken) {
        Asserts.assertNotNull(apiToken, "apiToken");
        this.apiToken = apiToken;
    }

    @Override
    public String getToken() {
        return apiToken;
    }
}

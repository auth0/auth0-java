package com.auth0.client.mgmt;

import com.auth0.utils.Asserts;

/**
 * An implementation of {@link TokenProvider} that simply returns the token it is configured with. This is used
 * when creating the {@link ManagementAPI} with an API token directly. Tokens will not be renewed; consumers are
 * responsible for renewing the token when needed and then calling {@link ManagementAPI#setApiToken(String)} with the
 * new token.
 */
public class SimpleTokenProvider implements TokenProvider {
    private final String apiToken;

    public static SimpleTokenProvider create(String apiToken) {
        return new SimpleTokenProvider(apiToken);
    }

    @Override
    public String getToken() {
        return apiToken;
    }

    private SimpleTokenProvider(String apiToken) {
        Asserts.assertNotNull(apiToken, "api token");
        this.apiToken = apiToken;
    }
}

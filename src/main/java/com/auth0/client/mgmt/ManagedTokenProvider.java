package com.auth0.client.mgmt;

import com.auth0.client.auth.AuthAPI;
import com.auth0.exception.Auth0Exception;
import com.auth0.json.auth.TokenHolder;
import com.auth0.utils.Asserts;
import org.jetbrains.annotations.TestOnly;

import java.time.Instant;
import java.util.Objects;

/**
 * An implementation of {@link TokenProvider} that fetches, stores (in-memory), and renews Auth0 Management API tokens.
 */
public class ManagedTokenProvider implements TokenProvider {

    private final AuthAPI authAPI;
    private TokenHolder tokenHolder;
    final static int LEEWAY = 10;

    public static ManagedTokenProvider create(AuthAPI authAPI) {
        return new ManagedTokenProvider(authAPI);
    }

    @Override
    public synchronized String getToken() throws Auth0Exception {
        // get tokens on first request if not set yet
        if (Objects.isNull(tokenHolder)) {
            tokenHolder = getTokenHolder();
            return tokenHolder.getAccessToken();
        }

        // if expired (or about to expire), renew the token, store it, and return it
        if (isExpired()) {
            this.tokenHolder = getTokenHolder();
            return tokenHolder.getAccessToken();
        }

        // token still valid
        return tokenHolder.getAccessToken();
    }

    ManagedTokenProvider(AuthAPI authAPI) {
        Asserts.assertNotNull(authAPI, "Auth API");
        this.authAPI = authAPI;
    }

    @TestOnly
    boolean isExpired() {
        return Instant.now().plusSeconds(LEEWAY).isAfter(tokenHolder.getExpiresAt().toInstant());
    }

    private TokenHolder getTokenHolder() throws Auth0Exception {
        return this.authAPI.requestToken(this.authAPI.getManagementAPIAudience())
            .execute()
            .getBody();
    }
}

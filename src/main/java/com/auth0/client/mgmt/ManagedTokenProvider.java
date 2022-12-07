package com.auth0.client.mgmt;

import com.auth0.client.auth.AuthAPI;
import com.auth0.exception.Auth0Exception;
import com.auth0.json.auth.TokenHolder;
import com.auth0.net.client.Auth0HttpClient;
import com.auth0.net.client.DefaultHttpClient;
import org.jetbrains.annotations.TestOnly;

import java.time.Instant;
import java.util.Objects;

/**
 * An implementation of {@link TokenProvider} that fetches, stores (in-memory), and renews Auth0 Management API tokens.
 */
public class ManagedTokenProvider implements TokenProvider {

    private final AuthAPI authAPI;
    private TokenHolder tokenHolder;
    private final int leeway;
    private final Auth0HttpClient httpClient;

    /**
     * Initializes a new {@link Builder} with details about an
     * <a href="https://auth0.com/docs/secure/tokens/access-tokens/get-management-api-access-tokens-for-production">
     *     Auth0 application authorized for the Management API.
     * </a>
     * @param domain the tenant's domain. Must be a non-null valid HTTPS URL.
     * @param clientId the client ID of the application.
     * @param clientSecret the client secret of the application.
     * @return a Builder for further configuration.
     */
    public static ManagedTokenProvider.Builder newBuilder(String domain, String clientId, String clientSecret) {
        return new ManagedTokenProvider.Builder(domain, clientId, clientSecret);
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

    private ManagedTokenProvider(Builder builder) {
        if (builder.leeway < 0) {
            throw new IllegalArgumentException("leeway must be a positive number of seconds");
        }

        this.leeway = builder.leeway;
        this.httpClient = Objects.nonNull(builder.httpClient) ? builder.httpClient : DefaultHttpClient.newBuilder().build();

        // allow injection of AuthAPI client for testing purposes
        if (Objects.nonNull(builder.authAPI)) {
            this.authAPI = builder.authAPI;
        } else {
            this.authAPI = AuthAPI.newBuilder(builder.domain, builder.clientId, builder.clientSecret)
                .withHttpClient(this.httpClient)
                .build();
        }
    }

    @TestOnly
    Auth0HttpClient getHttpClient() {
        return this.httpClient;
    }

    @TestOnly
    boolean isExpired() {
        return Instant.now().plusSeconds(this.leeway).isAfter(tokenHolder.getExpiresAt().toInstant());
    }

    private TokenHolder getTokenHolder() throws Auth0Exception {
        return this.authAPI.requestToken(this.authAPI.getManagementAPIAudience())
            .execute()
            .getBody();
    }

    /**
     * A Builder to configure and construct a {@link ManagedTokenProvider}.
     */
    public static class Builder {
        private final String domain;
        private final String clientId;
        private final String clientSecret;
        private Auth0HttpClient httpClient;
        private int leeway = 10;
        private AuthAPI authAPI;

        /**
         * Create a new Builder instance.
         * @param domain the tenant's domain. Must be a non-null valid HTTPS URL.
         * @param clientId the client ID of the application.
         * @param clientSecret the client secret of the application.
         */
        public Builder(String domain, String clientId, String clientSecret) {
            this.domain = domain;
            this.clientId = clientId;
            this.clientSecret = clientSecret;
        }

        /**
         * Specify a leeway, in seconds, used to determine of the token is expired. By default, a value of 10 seconds
         * is used.
         * <p>
         * The calculation used to determine if the token is expired is {@code isExpired = timeNow + leeway > tokenExpiry}
         * </p>
         * @param leewaySeconds the number of seconds to add
         * @return the builder instance.
         */
        public Builder withLeeway(int leewaySeconds) {
            this.leeway = leewaySeconds;
            return this;
        }

        /**
         * Specify an {@link Auth0HttpClient} to use when fetching the API token.
         * @param httpClient the Http client to use.
         * @return the builder instance.
         */
        public Builder withHttpClient(Auth0HttpClient httpClient) {
            this.httpClient = httpClient;
            return this;
        }

        // allow tests to inject an AuthAPI
        @TestOnly
        Builder withAuthAPI(AuthAPI authAPI) {
            this.authAPI = authAPI;
            return this;
        }

        /**
         * Constructs a {@link ManagedTokenProvider} from this builder.
         * @return the constructed instance.
         */
        public ManagedTokenProvider build() {
            return new ManagedTokenProvider(this);
        }
    }
}

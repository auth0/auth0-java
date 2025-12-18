/**
 * OAuth token supplier for fetching and caching access tokens using client credentials.
 *
 * This class handles the OAuth 2.0 client credentials flow to obtain access tokens
 * from Auth0. Tokens are cached and automatically refreshed when they expire.
 */
package com.auth0.client.mgmt.core;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.*;

public class OAuthTokenSupplier implements java.util.function.Supplier<String> {
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private static final long BUFFER_SECONDS = 120;

    // Shared HTTP client for all OAuth token requests to avoid resource leaks
    private static final OkHttpClient SHARED_HTTP_CLIENT =
            new OkHttpClient.Builder().callTimeout(30, TimeUnit.SECONDS).build();

    private final String clientId;
    private final String clientSecret;
    private final String tokenUrl;
    private final String audience;

    private volatile String accessToken;
    private volatile Instant expiresAt;

    /**
     * Creates a new OAuth token supplier.
     *
     * @param clientId The OAuth client ID
     * @param clientSecret The OAuth client secret
     * @param baseUrl The Auth0 base URL (e.g., "https://your-domain.auth0.com")
     * @param audience The API audience. If null, defaults to baseUrl + "/api/v2/"
     * @throws IllegalArgumentException if baseUrl is null or empty
     */
    public OAuthTokenSupplier(String clientId, String clientSecret, String baseUrl, String audience) {
        if (baseUrl == null || baseUrl.trim().isEmpty()) {
            throw new IllegalArgumentException("baseUrl cannot be null or empty");
        }

        this.clientId = clientId;
        this.clientSecret = clientSecret;

        String normalizedBaseUrl = baseUrl.replaceAll("/+$", "");
        this.tokenUrl = normalizedBaseUrl + "/oauth/token";
        this.audience = audience != null ? audience : normalizedBaseUrl + "/api/v2/";
        this.expiresAt = Instant.now();
    }

    /**
     * Gets an access token, fetching a new one if the cached token has expired.
     *
     * @return A valid Bearer token string (without "Bearer " prefix)
     * @throws OAuthTokenException if token fetching fails due to network issues,
     *                             invalid credentials, or Auth0 service problems
     */
    @Override
    public String get() {
        if (accessToken == null || Instant.now().isAfter(expiresAt)) {
            synchronized (this) {
                if (accessToken == null || Instant.now().isAfter(expiresAt)) {
                    fetchToken();
                }
            }
        }
        return accessToken;
    }

    /**
     * Fetches a new access token from Auth0.
     *
     * @throws OAuthTokenException if token fetching fails
     */
    private void fetchToken() {
        try {
            Map<String, String> requestData = new HashMap<>();
            requestData.put("client_id", clientId);
            requestData.put("client_secret", clientSecret);
            requestData.put("audience", audience);
            requestData.put("grant_type", "client_credentials");

            String requestBody = ObjectMappers.JSON_MAPPER.writeValueAsString(requestData);

            Request request = new Request.Builder()
                    .url(tokenUrl)
                    .post(RequestBody.create(requestBody, JSON))
                    .addHeader("Content-Type", "application/json")
                    .build();

            try (Response response = SHARED_HTTP_CLIENT.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    throw new OAuthTokenException(
                            "Failed to fetch OAuth token: HTTP " + response.code() + " - " + response.message());
                }

                String responseBody = response.body() != null ? response.body().string() : "{}";
                TokenResponse tokenResponse = ObjectMappers.JSON_MAPPER.readValue(responseBody, TokenResponse.class);

                if (tokenResponse.accessToken == null || tokenResponse.accessToken.isEmpty()) {
                    throw new OAuthTokenException("OAuth token response did not contain an access token");
                }

                this.accessToken = tokenResponse.accessToken;
                this.expiresAt = Instant.now()
                        .plusSeconds(tokenResponse.expiresIn != null ? tokenResponse.expiresIn : 86400)
                        .minusSeconds(BUFFER_SECONDS);
            }
        } catch (IOException e) {
            throw new OAuthTokenException("Failed to fetch OAuth token due to network error", e);
        }
    }

    /**
     * Response from Auth0 token endpoint.
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class TokenResponse {
        @JsonProperty("access_token")
        String accessToken;

        @JsonProperty("expires_in")
        Integer expiresIn;

        @JsonProperty("token_type")
        String tokenType;
    }
}

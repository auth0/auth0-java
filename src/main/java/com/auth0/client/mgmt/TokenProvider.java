package com.auth0.client.mgmt;

import com.auth0.exception.Auth0Exception;
import java.util.concurrent.CompletableFuture;

/**
 * A {@code TokenProvider} is responsible for providing the token used when making authorized requests to the Auth0
 * Management API.
 */
public interface TokenProvider {

    /**
     * Responsible for obtaining a token for use with the Management API client for synchronous requests.
     * @return the token required when making requests to the Auth0 Management API.
     * @throws Auth0Exception if the token cannot be retrieved.
     */
    String getToken() throws Auth0Exception;

    /**
     * Responsible for obtaining a token for use with the Management API client for asynchronous requests.
     * @return a {@link CompletableFuture} with the token required when making requests to the Auth0 Management API.
     */
    CompletableFuture<String> getTokenAsync();
}

package com.auth0.client.mgmt;

import com.auth0.exception.Auth0Exception;

/**
 * A {@code TokenProvider} is responsible for providing the token used when making authorized requests to the Auth0
 * Management API.
 */
public interface TokenProvider {

    /**
     * @return the token required when making requests to the Auth0 Management API.
     * @throws Auth0Exception if the token cannot be retrieved.
     */
    String getToken() throws Auth0Exception;
}

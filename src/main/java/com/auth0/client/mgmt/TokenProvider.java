package com.auth0.client.mgmt;

import com.auth0.exception.Auth0Exception;

public interface TokenProvider {
    String getToken() throws Auth0Exception;
}

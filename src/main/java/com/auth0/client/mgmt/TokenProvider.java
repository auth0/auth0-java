package com.auth0.client.mgmt;

import com.auth0.exception.Auth0Exception;

public interface TokenProvider {
    // TODO - if we fetch the token, can throw an Auth0Exception. Every API method now has to declare it throws that,
    //  and all client code needs to handle.
    //  Also, in the simple case, method would need to declare it throws when it never would
    String getToken() throws Auth0Exception;
}

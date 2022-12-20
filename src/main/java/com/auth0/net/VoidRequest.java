package com.auth0.net;

import com.auth0.client.mgmt.TokenProvider;
import com.auth0.exception.Auth0Exception;
import com.auth0.net.client.Auth0HttpClient;
import com.auth0.net.client.Auth0HttpResponse;
import com.auth0.net.client.HttpMethod;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.HashMap;

/**
 * Represents a request that doesn't return any value on its success.
 * <p>
 * This class is not thread-safe:
 * It makes use of {@link HashMap} for storing the parameters. Make sure to not modify headers or the parameters
 * from a different or un-synchronized thread.
 *
 * @see BaseRequest
 */
public class VoidRequest extends BaseRequest<Void> {

    public VoidRequest(Auth0HttpClient client, TokenProvider tokenProvider, String url, HttpMethod method) {
        super(client, tokenProvider, url, method, new TypeReference<Void>() {
        });
    }

    @Override
    protected Void parseResponseBody(Auth0HttpResponse response) throws Auth0Exception {
        if (!response.isSuccessful()) {
            throw super.createResponseException(response);
        }
        return null;
    }
}

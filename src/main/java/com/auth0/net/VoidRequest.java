package com.auth0.net;

import com.auth0.exception.Auth0Exception;
import com.auth0.net.client.HttpClient;
import com.auth0.net.client.HttpMethod;
import com.auth0.net.client.HttpResponse;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.Collections;
import java.util.HashMap;

/**
 * Represents a request that doesn't return any value on its success.
 * <p>
 * This class is not thread-safe:
 * It makes use of {@link HashMap} for storing the parameters. Make sure to not modify headers or the parameters
 * from a different or un-synchronized thread.
 *
 * @see CustomRequest
 */
public class VoidRequest extends CustomRequest<Void> {

    public VoidRequest(HttpClient client, String url, HttpMethod method) {
        super(client, url, method, new TypeReference<Void>() {
        });
    }

    @Override
    protected Void parseResponseBody(HttpResponse response) throws Auth0Exception {
        if (!response.isSuccessful()) {
            throw super.createResponseException(response);
        }
        // TODO who is responsible to close request?????
//        response.close();
        return null;
    }
}

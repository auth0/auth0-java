package com.auth0.net;

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
 * @see CustomRequest
 */
public class VoidRequest extends CustomRequest<Void> {

    public VoidRequest(Auth0HttpClient client, String url, HttpMethod method) {
        super(client, url, method, new TypeReference<Void>() {
        });
    }

    @Override
    protected Void parseResponseBody(Auth0HttpResponse response) throws Auth0Exception {
        if (!response.isSuccessful()) {
            throw super.createResponseException(response);
        }
        // TODO because a VoidRequest doesn't have a body, it won't be read and the response not automatically closed
        //  need to ensure that resposnes are *always* closed.
        //Poovam: Should we do this? We should just give a shoutout in the documentation. This feature will make it look like we are a full on Http library. We also have to properly close OkHttpReponse in DefaultHttpClient#buildResponse
//        response.close();
        return null;
    }
}

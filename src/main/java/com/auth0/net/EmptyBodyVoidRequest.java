package com.auth0.net;

import com.auth0.client.mgmt.TokenProvider;
import com.auth0.exception.Auth0Exception;
import com.auth0.net.client.Auth0HttpClient;
import com.auth0.net.client.Auth0HttpResponse;
import com.auth0.net.client.HttpMethod;
import com.auth0.net.client.HttpRequestBody;
import com.fasterxml.jackson.core.type.TypeReference;

/**
 * Request class that does not accept parameters to be sent as part of its body and request doesn't return any value on its success.
 * The content type of this request is "application/json".
 *
 * @param <T> The type expected to be received as part of the response.
 * @see BaseRequest
 */
public class EmptyBodyVoidRequest<T> extends BaseRequest<T>  {
    public EmptyBodyVoidRequest(Auth0HttpClient client, TokenProvider tokenProvider, String url, HttpMethod method, TypeReference<T> tType) {
        super(client, tokenProvider, url, method, tType);
    }

    @Override
    @SuppressWarnings("deprecation")
    protected HttpRequestBody createRequestBody() {
        return HttpRequestBody.create("application/json", new byte[0]);
    }

    @Override
    public EmptyBodyVoidRequest<T> addParameter(String name, Object value) {
        //do nothing
        return this;
    }
    @Override
    protected T parseResponseBody(Auth0HttpResponse response) throws Auth0Exception {
        if (!response.isSuccessful()) {
            throw super.createResponseException(response);
        }
        return null;
    }

}

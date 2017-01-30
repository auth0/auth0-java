package com.auth0.net;

import com.auth0.exception.Auth0Exception;
import okhttp3.OkHttpClient;
import okhttp3.Response;

import java.io.IOException;

public abstract class BaseRequest<T> implements Request<T> {

    private final OkHttpClient client;

    BaseRequest(OkHttpClient client) {
        this.client = client;
    }

    protected abstract okhttp3.Request createRequest() throws Auth0Exception;

    protected abstract T parseResponse(Response response) throws Auth0Exception;

    /**
     * Executes this request.
     *
     * @return the response body JSON decoded as T
     * @throws Auth0Exception if the request execution fails.
     */
    @Override
    public T execute() throws Auth0Exception {
        okhttp3.Request request = createRequest();
        Response response;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            throw new Auth0Exception("Failed to execute request", e);
        }

        return parseResponse(response);
    }

}

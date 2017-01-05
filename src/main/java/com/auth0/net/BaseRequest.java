package com.auth0.net;

import okhttp3.OkHttpClient;
import okhttp3.Response;

import java.io.IOException;

public abstract class BaseRequest<T> implements Request<T> {

    private final OkHttpClient client;

    public BaseRequest(OkHttpClient client) {
        this.client = client;
    }

    protected abstract okhttp3.Request createRequest() throws RequestFailedException;

    protected abstract T parseResponse(Response response) throws RequestFailedException;

    /**
     * Executes this request.
     *
     * @return the response body JSON decoded as T
     * @throws RequestFailedException if the request execution fails.
     */
    public T execute() throws RequestFailedException {
        okhttp3.Request request = createRequest();
        Response response;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            throw new RequestFailedException(String.format("Failed to execute request"), e);
        }

        return parseResponse(response);
    }

}

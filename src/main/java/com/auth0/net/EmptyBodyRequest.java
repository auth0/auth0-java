package com.auth0.net;

import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

import java.io.IOException;

public class EmptyBodyRequest<T> extends CustomRequest<T> {

    public EmptyBodyRequest(OkHttpClient client, String url, String method, TypeReference<T> tType) {
        super(client, url, method, tType);
    }

    @Override
    protected RequestBody createRequestBody() throws IOException {
        return RequestBody.create(null, new byte[0]);
    }

    @Override
    public EmptyBodyRequest<T> addParameter(String name, Object value) {
        //do nothing
        return this;
    }
}

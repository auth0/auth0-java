package com.auth0.net;

import com.auth0.exception.Auth0Exception;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class EmptyBodyRequest<T> extends CustomRequest<T> {

    public EmptyBodyRequest(OkHttpClient client, String url, String method, TypeReference<T> tType) {
        super(client, url, method, tType);
    }

    @Override
    protected RequestBody createBody() throws Auth0Exception {
        return RequestBody.create(null, new byte[0]);
    }

    @Override
    public EmptyBodyRequest<T> addParameter(String name, Object value) {
        //do nothing
        return this;
    }
}

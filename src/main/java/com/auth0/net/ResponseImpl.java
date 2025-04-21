package com.auth0.net;

import com.auth0.json.auth.TokenQuotaLimit;

import java.util.Collections;
import java.util.Map;

class ResponseImpl<T> implements Response<T> {

    private final Map<String, String> headers;
    private final T body;
    private final int statusCode;

    ResponseImpl(Map<String, String> headers, T body, int statusCode) {
        this.headers = Collections.unmodifiableMap(headers);
        this.body = body;
        this.statusCode = statusCode;
    }

    @Override
    public Map<String, String> getHeaders() {
        return headers;
    }

    @Override
    public T getBody() {
        return body;
    }

    @Override
    public int getStatusCode() {
        return statusCode;
    }
}

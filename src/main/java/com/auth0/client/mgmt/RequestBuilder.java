package com.auth0.client.mgmt;

import com.auth0.net.HttpClient;
import com.auth0.net.CustomRequest;
import com.auth0.net.Request;
import com.auth0.net.VoidRequest;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.HashMap;
import java.util.Map;

import okhttp3.HttpUrl;
import org.jetbrains.annotations.Nullable;

class RequestBuilder<T> {
    private final HttpClient client;
    private final String method;

    // TODO decouple from OkHttp
    private final HttpUrl.Builder url;
    private final TypeReference<T> target;

    @Nullable
    private Object body = null;
    private final Map<String, String> headers = new HashMap<>();
    private final Map<String, Object> parameters = new HashMap<>();

    public RequestBuilder(HttpClient client, String method, HttpUrl baseUrl, TypeReference<T> target) {
        this.client = client;
        this.method = method;
        this.url = baseUrl.newBuilder();
        this.target = target;
    }

    public RequestBuilder<T> withHeader(String name, String value) {
        this.headers.put(name, value);
        return this;
    }

    public RequestBuilder<T> withParameter(String name, Object value) {
        this.parameters.put(name, value);
        return this;
    }

    public RequestBuilder<T> withBody(Object body) {
        this.body = body;
        return this;
    }

    public RequestBuilder<T> withPathSegments(String path) {
        url.addPathSegments(path);
        return this;
    }

    @SuppressWarnings("unchecked")
    public Request<T> build() {
        CustomRequest<T> request;

        final String url = this.url.build().toString();
        if ("java.lang.Void".equals(target.getType().getTypeName())) {
            request = (CustomRequest<T>) new VoidRequest(client, url, method);
        } else {
            request = new CustomRequest<>(client, url, method, target);
        }

        if (body != null) {
            request.setBody(body);
        }

        headers.forEach(request::addHeader);
        parameters.forEach(request::addParameter);

        return request;
    }
}

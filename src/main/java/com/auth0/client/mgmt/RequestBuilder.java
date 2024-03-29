package com.auth0.client.mgmt;

import com.auth0.net.BaseRequest;
import com.auth0.net.Request;
import com.auth0.net.VoidRequest;
import com.auth0.net.client.Auth0HttpClient;
import com.auth0.net.client.HttpMethod;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.HttpUrl;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

class RequestBuilder<T> {
    private final Auth0HttpClient client;
    private final TokenProvider tokenProvider;
    private final HttpMethod method;
    private final HttpUrl.Builder url;
    private final TypeReference<T> target;

    @Nullable
    private Object body = null;
    private final Map<String, String> headers = new HashMap<>();
    private final Map<String, Object> parameters = new HashMap<>();

    public RequestBuilder(Auth0HttpClient client, TokenProvider tokenProvider, HttpMethod method, HttpUrl baseUrl, TypeReference<T> target) {
        this.client = client;
        this.tokenProvider = tokenProvider;
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
        BaseRequest<T> request;

        final String url = this.url.build().toString();
        if ("java.lang.Void".equals(target.getType().getTypeName())) {
            request = (BaseRequest<T>)  new VoidRequest(client, tokenProvider,  url, method);
        } else {
            request =  new BaseRequest<>(client, tokenProvider, url, method, target);
        }

        if (body != null) {
            request.setBody(body);
        }

        headers.forEach(request::addHeader);
        parameters.forEach(request::addParameter);

        return request;
    }
}

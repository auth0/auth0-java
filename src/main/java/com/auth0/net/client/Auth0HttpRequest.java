package com.auth0.net.client;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Auth0HttpRequest {

    private final String url;
    private final Map<String, String> headers;
    private final HttpRequestBody body;

    private final HttpMethod method;

    public static Builder newBuilder(String url, HttpMethod method) {
        return new Builder(url, method);
    }

    private Auth0HttpRequest(Builder builder) {
        this.url = builder.url;
        this.method = builder.method;
        this.body = builder.body;
        this.headers = Objects.nonNull(builder.headers) ? new HashMap<>(builder.headers) : new HashMap<>();
    }

    public String getUrl() {
        return url;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public HttpRequestBody getBody() {
        return body;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public static class Builder {
        private final String url;
        private Map<String, String> headers;
        private HttpRequestBody body;

        private final HttpMethod method;

        private Builder(String url, HttpMethod method) {
            this.url = url;
            this.method = method;
        }

        public Builder withHeaders(Map<String, String> headers) {
            this.headers = headers;
            return this;
        }

        public Builder withBody(HttpRequestBody body) {
            this.body = body;
            return this;
        }

        public Auth0HttpRequest build() {
            return new Auth0HttpRequest(this);
        }
    }
}

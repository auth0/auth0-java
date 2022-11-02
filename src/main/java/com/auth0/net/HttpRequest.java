package com.auth0.net;

import java.util.Map;

public class HttpRequest {

    private final String url;
    private final Map<String, String> headers;
    private final byte[] body;
    private final String method;

    private HttpRequest(Builder builder) {
        this.url = builder.url;
        this.method = builder.method;
        this.body = builder.body;
        this.headers = builder.headers;
    }

    public String getUrl() {
        return url;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public byte[] getBody() {
        return body;
    }

    public String getMethod() {
        return method;
    }

    public static class Builder {
        private String url;
        private Map<String, String> headers;
        private byte[] body;
        private String method;

        public Builder(String url, String method) {
            this.url = url;
            this.method = method;
        }

        public Builder headers(Map<String, String> headers) {
            this.headers =  headers;
            return this;
        }

        public Builder body(byte[] body) {
            this.body = body;
            return this;
        }

        public HttpRequest build() {
            return new HttpRequest(this);
        }
    }
}

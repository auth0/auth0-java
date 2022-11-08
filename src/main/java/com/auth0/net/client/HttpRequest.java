package com.auth0.net.client;

import java.io.File;
import java.util.Map;

public class HttpRequest {

    private final String url;
    private final Map<String, String> headers;
//    private final byte[] body;
    private final HttpRequestBody body;

    private final File file;

    private final HttpMethod method;

    private HttpRequest(Builder builder) {
        this.url = builder.url;
        this.method = builder.method;
        this.body = builder.body;
        this.file = builder.file;
        this.headers = builder.headers;
    }

    public String getUrl() {
        return url;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

//    public byte[] getBody() {
//        return body;
//    }
    public HttpRequestBody getBody() {
    return body;
}

    public HttpMethod getMethod() {
        return method;
    }

    public static class Builder {
        private final String url;
        private Map<String, String> headers;
//        private byte[] body;
        private HttpRequestBody body;

        private File file;

        private final HttpMethod method;

        public Builder(String url, HttpMethod method) {
            this.url = url;
            this.method = method;
        }

        public Builder headers(Map<String, String> headers) {
            this.headers =  headers;
            return this;
        }

//        public Builder body(byte[] body) {
//            this.body = body;
//            return this;
//        }

        public Builder body(HttpRequestBody body) {
            this.body = body;
            return this;
        }

        public Builder file(File file) {
            this.file = file;
            return this;
        }

        public HttpRequest build() {
            return new HttpRequest(this);
        }
    }
}

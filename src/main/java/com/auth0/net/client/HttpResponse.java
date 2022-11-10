package com.auth0.net.client;

import com.auth0.utils.Asserts;

import java.util.HashMap;
import java.util.Map;

public class HttpResponse {

    private final int code;
    private final String body;

    private final Map<String, String> headers;

    private HttpResponse(Builder  builder) {
        Asserts.assertNotNull(builder.code, "response code");
        Asserts.assertNotNull(builder.headers, "response headers");
        this.code = builder.code;
        this.body = builder.body;
        this.headers = new HashMap<>(builder.headers);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public boolean isSuccessful() {
        return  code >= 200 && code <= 299;
    }

    public String getBody() {
        return body;
    }

    public int getCode() {
        return code;
    }

    public Map<String, String> getHeaders() {
        return this.headers;
    }

    public String getHeader(String header) {
        // TODO null check?
        return headers.get(header);
    }

    public String getHeader(String header, String defaultValue) {
        // TODO null check?
        return headers.get(header) != null ? headers.get(header) : defaultValue;
    }
    public static class Builder {
        private int code;
        private String body;

        private Map<String, String> headers = new HashMap<>();

        private Builder() {}

        public Builder code(int code) {
            this.code = code;
            return this;
        }

        public Builder body(String body) {
            this.body = body;
            return this;
        }

        public Builder headers(Map<String, String> headers) {
            // TODO this safe?
            this.headers = headers;
            return this;
        }

        // TODO headers

        public HttpResponse build() {
            return new HttpResponse(this);
        }
    }
}

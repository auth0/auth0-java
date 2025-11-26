package com.auth0.net.client;

import com.auth0.utils.Asserts;
import java.util.HashMap;
import java.util.Map;

public class Auth0HttpResponse {

    private final int code;
    private final String body;

    private final Map<String, String> headers;

    private Auth0HttpResponse(Builder builder) {
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
        return code >= 200 && code <= 299;
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
        return headers.get(header);
    }

    public String getHeader(String header, String defaultValue) {
        return headers.get(header) != null ? headers.get(header) : defaultValue;
    }

    public static class Builder {
        private int code;
        private String body;

        private Map<String, String> headers = new HashMap<>();

        private Builder() {}

        public Builder withStatusCode(int code) {
            this.code = code;
            return this;
        }

        public Builder withBody(String body) {
            this.body = body;
            return this;
        }

        public Builder withHeaders(Map<String, String> headers) {
            this.headers = headers;
            return this;
        }

        public Auth0HttpResponse build() {
            return new Auth0HttpResponse(this);
        }
    }
}

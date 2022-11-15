package com.auth0.net.client;

public class HttpRequestBody {

    private byte[] content;

    HttpRequestBody(Builder builder) {
        this.content = builder.content;
        this.multipartRequestBody = builder.multipartRequestBody;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public byte[] getContent() {
        return this.content;
    }

    public Auth0MultipartRequestBody getMultipartRequestBody() {
        return this.multipartRequestBody;
    }

    private String contentType;
    private Auth0MultipartRequestBody multipartRequestBody;

    private HttpRequestBody(String contentType, byte[] content) {
        this.contentType = contentType;
        this.content = content;
    }

    private HttpRequestBody(String contentType, Auth0MultipartRequestBody multipartRequestBody) {
        this.contentType = contentType;
        this.multipartRequestBody = multipartRequestBody;
    }

    static HttpRequestBody create(String contentType, byte[] content) {
        return new HttpRequestBody(contentType, content);
    }

    static HttpRequestBody create(String contentType, Auth0MultipartRequestBody multipartRequestBody) {
        return new HttpRequestBody(contentType, multipartRequestBody);
    }

    //Poovam: Should we just use the static create methods and avoid the builder? Use builder if we have lot more parameters in future?
    public static class Builder {
        private byte[] content;
        private Auth0MultipartRequestBody multipartRequestBody;

        private Builder() {}

        public Builder withContent(byte[] content) {
            this.content = content;
            return this;
        }

        public Builder withMultipart(Auth0MultipartRequestBody multipartRequestBody) {
            this.multipartRequestBody = multipartRequestBody;
            return this;
        }

        public HttpRequestBody build() {
            return new HttpRequestBody(this);
        }
    }
}

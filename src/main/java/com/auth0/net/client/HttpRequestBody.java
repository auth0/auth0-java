package com.auth0.net.client;

public class HttpRequestBody {

    private byte[] content;
    private String contentType;
    private Auth0MultipartRequestBody multipartRequestBody;
    private Auth0FormRequestBody formRequestBody;

    public static HttpRequestBody create(String contentType, byte[] content) {
        return new HttpRequestBody(contentType, content);
    }

    public static HttpRequestBody create(String contentType, Auth0MultipartRequestBody multipartRequestBody) {
        return new HttpRequestBody(contentType, multipartRequestBody);
    }

    public static HttpRequestBody create(String contentType, Auth0FormRequestBody formRequestBody) {
        return new HttpRequestBody(contentType, formRequestBody);
    }

    public byte[] getContent() {
        return this.content;
    }

    public Auth0MultipartRequestBody getMultipartRequestBody() {
        return this.multipartRequestBody;
    }

    public Auth0FormRequestBody getFormRequestBody() {
        return this.formRequestBody;
    }

    public String getContentType() {
        return this.contentType;
    }

    private HttpRequestBody(String contentType, byte[] content) {
        this.contentType = contentType;
        this.content = content;
    }

    private HttpRequestBody(String contentType, Auth0MultipartRequestBody multipartRequestBody) {
        this.contentType = contentType;
        this.multipartRequestBody = multipartRequestBody;
    }

    private HttpRequestBody(String contentType, Auth0FormRequestBody formRequestBody) {
        this.contentType = contentType;
        this.formRequestBody = formRequestBody;
    }
}

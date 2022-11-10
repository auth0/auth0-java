package com.auth0.net.client;

public class HttpRequestBody {

    private byte[] content;
//    private final File file;

//    private final Map<String, String> params;

    HttpRequestBody(Builder builder) {
        this.content = builder.content;
        this.multipartRequestBody = builder.multipartRequestBody;
//        this.content = content;
//        this.file = builder.file;
//        this.params = builder.params; // TODO defensive copy here or in FileUploadRequest?
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

//    public File getFile() {
//        return this.file;
//    }

//    public Map<String, String> getParams() {
//        return this.params;
//    }

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

    public static class Builder {
        private byte[] content;
        private Auth0MultipartRequestBody multipartRequestBody;
//        private File file;
//        private Map<String, String> params;

        private Builder() {}

        public Builder withContent(byte[] content) {
            this.content = content;
            return this;
        }

        public Builder withMultipart(Auth0MultipartRequestBody multipartRequestBody) {
            this.multipartRequestBody = multipartRequestBody;
            return this;
        }
//        public Builder withFile(File file) {
//            this.file = file;
//            return this;
//        }
//
//        public Builder withParams(Map<String, String> params) {
//            this.params = params;
//            return this;
//        }

        public HttpRequestBody build() {
            return new HttpRequestBody(this);
        }
    }
}

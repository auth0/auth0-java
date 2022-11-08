package com.auth0.net.client;

import java.io.File;
import java.util.Map;

public class HttpRequestBody {

    private final byte[] content;
    private final File file;

    private final Map<String, String> params;

    private HttpRequestBody(Builder builder) {
        this.content = builder.content;
        this.file = builder.file;
        this.params = builder.params; // TODO defensive copy here or in FileUploadRequest?
    }

    public byte[] getContent() {
        return this.content;
    }

    public File getFile() {
        return this.file;
    }

    public Map<String, String> getParams() {
        return this.params;
    }

    public static class Builder {
        private byte[] content;
        private File file;
        private Map<String, String> params;

        public Builder withContent(byte[] content) {
            this.content = content;
            return this;
        }

        public Builder withFile(File file) {
            this.file = file;
            return this;
        }

        public Builder withParams(Map<String, String> params) {
            this.params = params;
            return this;
        }

        public HttpRequestBody build() {
            return new HttpRequestBody(this);
        }
    }
}

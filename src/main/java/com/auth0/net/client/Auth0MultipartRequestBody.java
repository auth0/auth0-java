package com.auth0.net.client;

import com.auth0.utils.Asserts;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Auth0MultipartRequestBody {

    private final FilePart filePart;
    private final Map<String, String> parts;

    public static Builder newBuilder() {
        return new Builder();
    }

    private Auth0MultipartRequestBody(Builder builder) {
        this.filePart = builder.filePart;
        this.parts = new HashMap<>(builder.parts);
    }

    public FilePart getFilePart() {
        return this.filePart;
    }

    public Map<String, String> getParts() {
        return this.parts;
    }

    public static class FilePart {
        private final String partName;
        private final File file;
        private final String mediaType;

        public FilePart(String partName, File file, String mediaType) {
            Asserts.assertNotNull(partName, "part name");
            Asserts.assertNotNull(file, "file");
            Asserts.assertNotNull(mediaType, "mediaType");

            this.partName = partName;
            this.file = file;
            this.mediaType = mediaType;
        }

        public String getPartName() {
            return partName;
        }

        public File getFile() {
            return file;
        }

        public String getMediaType() {
            return mediaType;
        }
    }

    public static class Builder {
        FilePart filePart;
        Map<String, String> parts = new HashMap<>();

        private Builder() {}

        public Builder withFilePart(FilePart filePart) {
            this.filePart = filePart;
            return this;
        }

        public Builder withPart(String name, String value) {
            parts.put(name, value);
            return this;
        }

        public Auth0MultipartRequestBody build() {
            return new Auth0MultipartRequestBody(this);
        }
    }
}

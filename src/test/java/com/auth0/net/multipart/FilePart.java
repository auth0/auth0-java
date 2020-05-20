package com.auth0.net.multipart;

public class FilePart extends KeyValuePart {

    private final String filename;
    private final String contentType;

    public FilePart(String key, String value, String filename, String contentType) {
        super(key, value);
        this.filename = filename;
        this.contentType = contentType;
    }

    public String getFilename() {
        return filename;
    }

    public String getContentType() {
        return contentType;
    }
}

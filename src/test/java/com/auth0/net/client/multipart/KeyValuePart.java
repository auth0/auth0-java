package com.auth0.net.client.multipart;

public class KeyValuePart {

    private final String key;
    private final String value;

    public KeyValuePart(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}

package com.auth0;

import okhttp3.HttpUrl;

abstract class Asserts {
    public static void assertNotNull(Object value, String name) throws IllegalArgumentException {
        if (value == null) {
            throw new IllegalArgumentException(String.format("'%s' cannot be null!", name));
        }
    }

    public static void assertValidUrl(String value, String name) throws IllegalArgumentException {
        if (value == null || HttpUrl.parse(value) == null) {
            throw new IllegalArgumentException(String.format("'%s' must be a valid URL!", name));
        }
    }
}

package com.auth0.utils;

import java.util.Collection;
import okhttp3.HttpUrl;

public abstract class Asserts {
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

    public static void assertNotEmpty(Collection value, String name) throws IllegalArgumentException {
        if (value == null) {
            throw new IllegalArgumentException(String.format("'%s' cannot be null!", name));
        }
        if (value.size() == 0) {
            throw new IllegalArgumentException(String.format("'%s' cannot be empty!", name));
        }
    }
}

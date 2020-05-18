package com.auth0.utils;

import java.util.Collection;
import okhttp3.HttpUrl;

public abstract class Asserts {

    /**
     * Asserts that an object is not null.
     *
     * @param value the value to check.
     * @param name the name of the parameter, used when creating the exception message.
     * @throws IllegalArgumentException if the value is null or has length of zero.
     */
    public static void assertNotNull(Object value, String name) {
        if (value == null) {
            throw new IllegalArgumentException(String.format("'%s' cannot be null!", name));
        }
    }

    /**
     * Asserts that a value is a valid URL.
     *
     * @param value the value to check.
     * @param name the name of the parameter, used when creating the exception message.
     * @throws IllegalArgumentException if the value is null or is not a valid URL.
     */
    public static void assertValidUrl(String value, String name) {
        if (value == null || HttpUrl.parse(value) == null) {
            throw new IllegalArgumentException(String.format("'%s' must be a valid URL!", name));
        }
    }

    /**
     * Asserts that a collection is not null and has at least one item.
     *
     * @param value the value to check.
     * @param name the name of the parameter, used when creating the exception message.
     * @throws IllegalArgumentException if the value is null or has length of zero.
     */
    public static void assertNotEmpty(Collection<?> value, String name) {
        if (value == null) {
            throw new IllegalArgumentException(String.format("'%s' cannot be null!", name));
        }
        if (value.size() == 0) {
            throw new IllegalArgumentException(String.format("'%s' cannot be empty!", name));
        }
    }

    /**
     * Asserts that an array is not null and has at least one item.
     * @param value the value to check.
     * @param name the name of the parameter, used when creating the exception message.
     * @throws IllegalArgumentException if the value is null or has length of zero.
     */
    public static <T> void assertNotEmpty(T[] value, String name) {
        if (value == null) {
            throw new IllegalArgumentException(String.format("'%s' cannot be null!", name));
        }
        if (value.length == 0) {
            throw new IllegalArgumentException(String.format("'%s' cannot be empty!", name));
        }
    }
}

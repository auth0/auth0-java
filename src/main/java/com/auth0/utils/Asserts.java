package com.auth0.utils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;

public abstract class Asserts {

    /**
     * Asserts that an object is not null.
     *
     * @param value the value to check.
     * @param name the name of the parameter, used when creating the exception message.
     * @throws IllegalArgumentException if the value is null
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
        try {
            new URI(value);
        } catch (URISyntaxException | NullPointerException e) {
            throw new IllegalArgumentException(String.format("'%s' must be a valid URL!", name), e);
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
}

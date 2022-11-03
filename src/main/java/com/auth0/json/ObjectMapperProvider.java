package com.auth0.json;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Provides access to a single {@link ObjectMapper} instance for the serialization and deserialization of JSON data.
 * This class is for internal use only and is subject to change without notice.
 */
public class ObjectMapperProvider {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    // prevent instantiation
    private ObjectMapperProvider() {}

    /**
     * @return the {@code ObjectMapper} instance to process JSON data
     */
    public static ObjectMapper getMapper() {
        return objectMapper;
    }
}

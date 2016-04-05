package com.auth0.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class Telemetry {
    public static final String HEADER_NAME = "Auth0-Client";

    private static final String NAME_KEY = "name";
    private static final String VERSION_KEY = "version";
    private static final String LIB_VERSION_KEY = "lib_version";

    private final String name;
    private final String version;
    private final String libraryVersion;

    public Telemetry(String name, String version) {
        this(name, version, null);
    }

    public Telemetry(String name, String version, String libraryVersion) {
        this.name = name;
        this.version = version;
        this.libraryVersion = libraryVersion;
    }

    public String getValue() {
        Map<String, String> values = new HashMap<>();
        if (name != null) {
            values.put(NAME_KEY, name);
        }
        if (version != null) {
            values.put(VERSION_KEY, version);
        }
        if (libraryVersion != null) {
            values.put(LIB_VERSION_KEY, libraryVersion);
        }
        if (values.isEmpty()) {
            return null;
        }
        try {
            String json = new ObjectMapper().writeValueAsString(values);
            return Base64.encodeUrlSafe(json);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}

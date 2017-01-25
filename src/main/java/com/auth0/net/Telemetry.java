package com.auth0.net;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.binary.Base64;

import java.util.HashMap;
import java.util.Map;

public class Telemetry {
    public static final String HEADER_NAME = "Auth0-Client";

    private static final String NAME_KEY = "name";
    private static final String VERSION_KEY = "version";

    private final String name;
    private final String version;

    public Telemetry(String name, String version) {
        this.name = name;
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public String getValue() {
        Map<String, String> values = new HashMap<>();
        if (name != null) {
            values.put(NAME_KEY, name);
        }
        if (version != null) {
            values.put(VERSION_KEY, version);
        }
        if (values.isEmpty()) {
            return null;
        }
        String urlSafe = null;
        try {
            String json = new ObjectMapper().writeValueAsString(values);
            urlSafe = Base64.encodeBase64URLSafeString(json.getBytes());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return urlSafe;
    }
}

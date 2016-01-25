package com.auth0.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class BaseMetrics implements Metrics {

    private final Map<String, String> values;

    public BaseMetrics() {
        this.values = new HashMap<>();
    }

    @Override
    public void usingLibrary(String name, String version) {
        values.put(NAME_KEY, name);
        values.put(VERSION_KEY, version);
    }

    @Override
    public String getValue() {
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

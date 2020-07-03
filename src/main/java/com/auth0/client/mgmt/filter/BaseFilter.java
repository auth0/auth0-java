package com.auth0.client.mgmt.filter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class BaseFilter {
    protected final Map<String, Object> parameters = new ConcurrentHashMap<>();

    public Map<String, Object> getAsMap() {
        return parameters;
    }
}

package com.auth0.client.mgmt.filter;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseFilter {
    protected Map<String, Object> parameters = new HashMap<>();

    public Map<String, Object> getAsMap() {
        return parameters;
    }
}

package com.auth0.json;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.HashMap;
import java.util.Map;

public class UserInfo {

    Map<String, Object> values;

    public UserInfo() {
        values = new HashMap<>();
    }

    @JsonAnySetter
    public void add(String key, Object value) {
        values.put(key, value);
    }

    @JsonAnyGetter
    public Map<String, Object> getValues() {
        return values;
    }
}

package com.auth0.json.auth;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserInfo {

    private Map<String, Object> values;

    public UserInfo() {
        values = new HashMap<>();
    }

    @JsonAnySetter
    public void setValue(String key, Object value) {
        values.put(key, value);
    }

    @JsonAnyGetter
    public Map<String, Object> getValues() {
        return values;
    }
}

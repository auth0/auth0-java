package com.auth0.json.mgmt.client;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Addon {

    @JsonUnwrapped
    private Map<String, Object> properties;

    @JsonAnySetter
    private void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    @JsonAnyGetter
    private Map<String, Object> getProperties() {
        return properties;
    }

    private Object getProperty(String name) {
        return properties.get(name);
    }

}

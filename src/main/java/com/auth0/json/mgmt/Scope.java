package com.auth0.json.mgmt;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Scope {
    @JsonProperty("description")
    private String description;
    @JsonProperty("value")
    private String value;

    @JsonCreator
    public Scope(@JsonProperty("value") String value,
                 @JsonProperty("description") String description) {
        this.description = description;
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}

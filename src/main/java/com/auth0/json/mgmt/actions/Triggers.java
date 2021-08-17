package com.auth0.json.mgmt.actions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Triggers {

    @JsonProperty("triggers")
    private List<Trigger> triggers;

    public List<Trigger> getTriggers() {
        return this.triggers;
    }
}

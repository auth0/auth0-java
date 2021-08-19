package com.auth0.json.mgmt.actions;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

// TODO consider better name?
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BindingUpdate {

    @JsonProperty("ref")
    private BindingReference bindingReference;
    @JsonProperty("display_name")
    private String displayName;

    @JsonCreator
    public BindingUpdate(@JsonProperty("ref") BindingReference bindingReference) {
        this.bindingReference = bindingReference;
    }

    public BindingReference getBindingReference() {
        return bindingReference;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

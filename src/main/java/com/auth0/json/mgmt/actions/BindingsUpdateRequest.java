package com.auth0.json.mgmt.actions;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

// TODO consider a better name?
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BindingsUpdateRequest {

    @JsonProperty("bindings")
    private List<BindingUpdate> bindingUpdates;

    @JsonCreator
    public BindingsUpdateRequest(@JsonProperty("bindings") List<BindingUpdate> bindingUpdates) {
        this.bindingUpdates = bindingUpdates;
    }

    public List<BindingUpdate> getBindingUpdates() {
        return bindingUpdates;
    }
}

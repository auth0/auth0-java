package com.auth0.json.mgmt.actions;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a trigger binding object when updating a trigger's action bindings.
 * @see BindingsUpdateRequest
 * @see com.auth0.client.mgmt.ActionsEntity
 */
// TODO consider better name?
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BindingUpdate {

    @JsonProperty("ref")
    private BindingActionReference bindingActionReference;
    @JsonProperty("display_name")
    private String displayName;

    /**
     * Creates a new instance.
     *
     * @param bindingActionReference the action bindings reference.
     */
    @JsonCreator
    public BindingUpdate(@JsonProperty("ref") BindingActionReference bindingActionReference) {
        this.bindingActionReference = bindingActionReference;
    }

    /**
     * @return the binding action references associated with this instance.
     */
    public BindingActionReference getBindingActionReference() {
        return bindingActionReference;
    }

    /**
     * Sets the display name of this binding reference.
     *
     * @param displayName the display name of this binding reference.
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * @return the display name of this binding reference.
     */
    public String getDisplayName() {
        return displayName;
    }
}

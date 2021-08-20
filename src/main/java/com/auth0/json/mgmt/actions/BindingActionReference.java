package com.auth0.json.mgmt.actions;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents an action to bind to a trigger when updating a trigger's binding.
 * @see BindingUpdate
 * @see BindingsUpdateRequest
 * @see com.auth0.client.mgmt.ActionsEntity
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BindingActionReference {

    @JsonProperty("type")
    private String type;
    @JsonProperty("value")
    private String value;

    /**
     * Create a new instance.
     *
     * @param type the type of action reference that will be used for the value, e.g., "action_name" or "action_id".
     * @param value the value of the action reference. This is used along with the {@type} parameter to associate an action.
     *              For example, {@code new BindingActionReference("action_id", "abc")} would create a binding reference
     *              to the action with ID "abc".
     */
    @JsonCreator
    public BindingActionReference(@JsonProperty("type") String type,
                                  @JsonProperty("value") String value) {
        this.type = type;
        this.value = value;
    }

    /**
     * @return the reference type.
     */
    public String getType() {
        return type;
    }

    /**
     * @return the reference value.
     */
    public String getValue() {
        return value;
    }
}

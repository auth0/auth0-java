package com.auth0.json.mgmt.actions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Represents the triggers of an action.
 * @see Action
 * @see com.auth0.client.mgmt.ActionsEntity
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Triggers {

    @JsonProperty("triggers")
    private List<Trigger> triggers;

    /**
     * @return the list of triggers
     */
    public List<Trigger> getTriggers() {
        return this.triggers;
    }
}

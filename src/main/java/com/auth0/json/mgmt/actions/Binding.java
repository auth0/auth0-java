package com.auth0.json.mgmt.actions;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Represents an action binding.
 * @see com.auth0.client.mgmt.ActionsEntity
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Binding {
    @JsonProperty("id")
    private String id;
    @JsonProperty("trigger_id")
    private String triggerId;
    @JsonProperty("display_name")
    private String displayName;
    @JsonProperty("action")
    private Action action;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("created_at")
    private Date createdAt;
    @JsonProperty("updated_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Date updatedAt;

    /**
     * @return the ID of this binding
     */
    public String getId() {
        return id;
    }

    /**
     * @return the ID the trigger associated with this binding
     */
    public String getTriggerId() {
        return triggerId;
    }

    /**
     * @return the name of this binding
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * @return the action associated with this binding
     */
    public Action getAction() {
        return action;
    }

    /**
     * @return the time when this binding was created
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * @return the time when this binding was updated
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }
}

package com.auth0.json.mgmt.actions;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

/**
 * Represents an action's execution.
 * @see com.auth0.client.mgmt.ActionsEntity
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Execution {

    @JsonProperty("id")
    private String id;
    @JsonProperty("trigger_id")
    private String triggerId;
    @JsonProperty("status")
    private String status;
    @JsonProperty("created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    Date createdAt;
    @JsonProperty("updated_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Date updatedAt;
    @JsonProperty("results")
    private List<ExecutionResult> results;

    /**
     * @return the ID that identifies this execution
     */
    public String getId() {
        return id;
    }

    /**
     * @return the trigger ID associated with this execution
     */
    public String getTriggerId() {
        return triggerId;
    }

    /**
     * @return the status associated with this execution
     */
    public String getStatus() {
        return status;
    }

    /**
     * @return the time this execution was started
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * @return the time this execution finished executing
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * @return the results of this execution
     */
    public List<ExecutionResult> getResults() {
        return results;
    }
}

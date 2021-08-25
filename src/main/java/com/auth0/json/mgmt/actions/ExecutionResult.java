package com.auth0.json.mgmt.actions;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Represents the result of an action execution.
 * @see Execution
 * @see com.auth0.client.mgmt.ActionsEntity
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExecutionResult {

    @JsonProperty("error")
    private Error error;
    @JsonProperty("action_name")
    private String actionName;
    @JsonProperty("started_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    Date startedAt;
    @JsonProperty("ended_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Date endedAt;

    /**
     * @return the error of this execution, if applicable
     */
    public Error getError() {
        return error;
    }

    /**
     * @return the name of the action that was executed
     */
    public String getActionName() {
        return actionName;
    }

    /**
     * @return the time when the execution started
     */
    public Date getStartedAt() {
        return startedAt;
    }

    /**
     * @return the time when the execution completed
     */
    public Date getEndedAt() {
        return endedAt;
    }
}

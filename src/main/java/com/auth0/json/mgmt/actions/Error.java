package com.auth0.json.mgmt.actions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents an error of an Action execution.
 * @see Execution
 * @see ExecutionResult
 * @see com.auth0.client.mgmt.ActionsEntity
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Error {

    @JsonProperty("id")
    private String id;
    @JsonProperty("msg")
    private String message;
    @JsonProperty("url")
    private String url;

    /**
     * @return the ID of this error
     */
    public String getId() {
        return id;
    }

    /**
     * @return the message of this error
     */
    public String getMessage() {
        return message;
    }

    /**
     * @return the URL of this error
     */
    public String getUrl() {
        return url;
    }
}

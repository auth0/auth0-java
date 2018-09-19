package com.auth0.json.mgmt.jobs;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class that represents an Auth0 Job Error object. Related to the {@link com.auth0.client.mgmt.JobsEntity} entity.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JobError {

    @JsonProperty("statusCode")
    private Integer statusCode;
    @JsonProperty("message")
    private String message;

    @JsonCreator
    public JobError(@JsonProperty("statusCode") Integer statusCode, @JsonProperty("message") String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    @JsonProperty("statusCode")
    public Integer getStatusCode() {
        return statusCode;
    }

    @JsonProperty("message")
    public String getMessage() {
        return message;
    }
}

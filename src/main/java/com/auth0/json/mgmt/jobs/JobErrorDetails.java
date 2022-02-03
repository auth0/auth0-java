package com.auth0.json.mgmt.jobs;

import com.auth0.json.mgmt.users.User;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Class that represents the error details for an Auth0 Job object. Related to the {@link com.auth0.client.mgmt.JobsEntity} entity.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JobErrorDetails {

    @JsonProperty("user")
    private final User user;

    @JsonProperty("errors")
    private final List<JobError> errors;

    @JsonCreator
    public JobErrorDetails(@JsonProperty("user") User user, @JsonProperty("errors") List<JobError> errors) {
        this.user = user;
        this.errors = errors;
    }

    @JsonProperty("user")
    public User getUser() {
        return user;
    }

    @JsonProperty("errors")
    public List<JobError> getErrors() {
        return errors;
    }
}

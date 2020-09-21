package com.auth0.json.mgmt;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Represents the identity object that can be sent on requests to create an email verification ticket or job.
 * Related to {@link com.auth0.client.mgmt.JobsEntity} and {@link com.auth0.client.mgmt.TicketsEntity}.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmailVerificationIdentity implements Serializable {

    @JsonProperty("provider")
    private String provider;
    @JsonProperty("user_id")
    private String userId;

    public EmailVerificationIdentity(String provider, String userId) {
        this.provider = provider;
        this.userId = userId;
    }

    @JsonProperty("provider")
    public String getProvider() {
        return this.provider;
    }

    @JsonProperty("user_id")
    public String getUserId() {
        return this.userId;
    }
}

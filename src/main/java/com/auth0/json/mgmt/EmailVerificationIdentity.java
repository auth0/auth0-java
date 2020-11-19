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
public class EmailVerificationIdentity {

    @JsonProperty("provider")
    private final String provider;
    @JsonProperty("user_id")
    private final String userId;

    /**
     * Create a new instance.
     *
     * @param provider The identity provider name (e.g. {@code "google-oauth2"}).
     * @param userId The user ID of the identity to be verified.
     */
    public EmailVerificationIdentity(String provider, String userId) {
        this.provider = provider;
        this.userId = userId;
    }

    /**
     * Get the identity provider name.
     *
     * @return The identity provider name.
     */
    @JsonProperty("provider")
    public String getProvider() {
        return this.provider;
    }

    /**
     * Get the user ID.
     *
     * @return the User ID.
     */
    @JsonProperty("user_id")
    public String getUserId() {
        return this.userId;
    }
}

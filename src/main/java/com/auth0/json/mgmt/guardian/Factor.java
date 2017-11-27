package com.auth0.json.mgmt.guardian;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class that represents an Auth0 Guardian Factor object. Related to the {@link com.auth0.client.mgmt.GuardianEntity} entity.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Factor {

    @JsonProperty("enabled")
    private Boolean enabled;
    @JsonProperty("trial_expired")
    private Boolean trialExpired;
    @JsonProperty("name")
    private String name;

    @JsonCreator
    public Factor(@JsonProperty("enabled") Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Whether this factor is enabled or not.
     *
     * @return true if this factor is enabled, false otherwise.
     */
    @JsonProperty("enabled")
    public Boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets whether this factor is enabled or not.
     *
     * @param enabled whether this factor is enabled or not.
     */
    @JsonProperty("enabled")
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Whether the trial has already expired or not.
     *
     * @return true if the trial has expired, false otherwise.
     */
    @JsonProperty("trial_expired")
    public Boolean isTrialExpired() {
        return trialExpired;
    }

    /**
     * Getter for the name of this factor.
     *
     * @return the factor name.
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }
}

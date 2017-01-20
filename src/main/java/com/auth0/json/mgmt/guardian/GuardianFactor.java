package com.auth0.json.mgmt.guardian;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("unused")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GuardianFactor {

    @JsonProperty("enabled")
    private Boolean enabled;
    @JsonProperty("trial_expired")
    private Boolean trialExpired;
    @JsonProperty("name")
    private String name;

    @JsonCreator
    public GuardianFactor(@JsonProperty("enabled") Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getTrialExpired() {
        return trialExpired;
    }

    public String getName() {
        return name;
    }
}

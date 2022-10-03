package com.auth0.json.mgmt.attackprotection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the BreachedPassword stage configuration options
 *
 * @see com.auth0.client.mgmt.AttackProtectionEntity
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BreachedPasswordStage {

    @JsonProperty("pre-user-registration")
    BreachedPasswordStageEntry preUserRegistrationStage;

    /**
     * Get the pre-user-registration stage entry.
     * @return the pre-user-registration stage entry.
     */
    public BreachedPasswordStageEntry getPreUserRegistrationStage() {
        return preUserRegistrationStage;
    }

    /**
     * Set the pre-user-registration stage entry.
     * @param preUserRegistrationStage the pre-user-registration stage entry.
     */
    public void setPreUserRegistrationStage(BreachedPasswordStageEntry preUserRegistrationStage) {
        this.preUserRegistrationStage = preUserRegistrationStage;
    }
}

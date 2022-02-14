package com.auth0.json.mgmt.attackprotection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the stage configuration options
 *
 * @see com.auth0.client.mgmt.AttackProtectionEntity
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Stage {

    @JsonProperty("pre-login")
    StageEntry preLoginStage;
    @JsonProperty("pre-user-registration")
    StageEntry preUserRegistrationStage;

    /**
     * Get the pre-login stage entry.
     * @return the pre-login stage entry.
     */
    public StageEntry getPreLoginStage() {
        return preLoginStage;
    }

    /**
     * Sets the pre-login stage entry.
     * @param preLoginStage the pre-login stage entry.
     */
    public void setPreLoginStage(StageEntry preLoginStage) {
        this.preLoginStage = preLoginStage;
    }

    /**
     * Get the pre-user-registration stage entry.
     * @return the pre-user-registration stage entry.
     */
    public StageEntry getPreUserRegistrationStage() {
        return preUserRegistrationStage;
    }

    /**
     * Set the pre-user-registration stage entry.
     * @param preUserRegistrationStage the pre-user-registration stage entry.
     */
    public void setPreUserRegistrationStage(StageEntry preUserRegistrationStage) {
        this.preUserRegistrationStage = preUserRegistrationStage;
    }
}

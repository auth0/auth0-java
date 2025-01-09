package com.auth0.json.mgmt.prompts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Prompt {
    @JsonProperty("universal_login_experience")
    private String universalLoginExperience;
    @JsonProperty("identifier_first")
    private boolean identifierFirst;
    @JsonProperty("webauthn_platform_first_factor")
    private boolean webauthnPlatformFirstFactor;

    /**
     * Getter for the universal login experience.
     * @return the universal login experience.
     */
    public String getUniversalLoginExperience() {
        return universalLoginExperience;
    }

    /**
     * Setter for the universal login experience.
     * @param universalLoginExperience the universal login experience to set.
     */
    public void setUniversalLoginExperience(String universalLoginExperience) {
        this.universalLoginExperience = universalLoginExperience;
    }

    /**
     * Getter for the identifier first.
     * @return the identifier first.
     */
    public boolean isIdentifierFirst() {
        return identifierFirst;
    }

    /**
     * Setter for the identifier first.
     * @param identifierFirst the identifier first to set.
     */
    public void setIdentifierFirst(boolean identifierFirst) {
        this.identifierFirst = identifierFirst;
    }

    /**
     * Getter for the webauthn platform first factor.
     * @return the webauthn platform first factor.
     */
    public boolean isWebauthnPlatformFirstFactor() {
        return webauthnPlatformFirstFactor;
    }

    /**
     * Setter for the webauthn platform first factor.
     * @param webauthnPlatformFirstFactor the webauthn platform first factor to set.
     */
    public void setWebauthnPlatformFirstFactor(boolean webauthnPlatformFirstFactor) {
        this.webauthnPlatformFirstFactor = webauthnPlatformFirstFactor;
    }
}

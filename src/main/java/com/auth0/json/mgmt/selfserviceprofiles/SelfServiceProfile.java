package com.auth0.json.mgmt.selfserviceprofiles;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SelfServiceProfile {
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("user_attributes")
    private List<UserAttribute> userAttributes;
    @JsonProperty("branding")
    private Branding branding;
    @JsonProperty("allowed_strategies")
    private List<String> allowedStrategies;
    @JsonProperty("user_attribute_profile_id")
    private String userAttributeProfileId;

    /**
     * Getter for the name of the self-service profile.
     * @return the name of the self-service profile.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the name of the self-service profile.
     * @param name the name of the self-service profile to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for the description of the self-service profile.
     * @return the description of the self-service profile.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter for the description of the self-service profile.
     * @param description the description of the self-service profile to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter for the user attributes of the self-service profile.
     * @return the user attributes of the self-service profile.
     */
    public List<UserAttribute> getUserAttributes() {
        return userAttributes;
    }

    /**
     * Setter for the user attributes of the self-service profile.
     * @param userAttributes the user attributes of the self-service profile to set.
     */
    public void setUserAttributes(List<UserAttribute> userAttributes) {
        this.userAttributes = userAttributes;
    }

    /**
     * Getter for the branding of the self-service profile.
     * @return the branding of the self-service profile.
     */
    public Branding getBranding() {
        return branding;
    }

    /**
     * Setter for the branding of the self-service profile.
     * @param branding the branding of the self-service profile to set.
     */
    public void setBranding(Branding branding) {
        this.branding = branding;
    }

    /**
     * Getter for the allowed strategies of the self-service profile.
     * @return the allowed strategies of the self-service profile.
     */
    public List<String> getAllowedStrategies() {
        return allowedStrategies;
    }

    /**
     * Setter for the allowed strategies of the self-service profile.
     * @param allowedStrategies the allowed strategies of the self-service profile to set.
     */
    public void setAllowedStrategies(List<String> allowedStrategies) {
        this.allowedStrategies = allowedStrategies;
    }

    /**
     * Getter for user attribute profile ID.
     * @return the user attribute profile ID.
     */
    public String getUserAttributeProfileId() {
        return userAttributeProfileId;
    }

    /**
     * Setter for user attribute profile ID.
     * @param userAttributeProfileId the user attribute profile ID to set.
     */
    public void setUserAttributeProfileId(String userAttributeProfileId) {
        this.userAttributeProfileId = userAttributeProfileId;
    }
}

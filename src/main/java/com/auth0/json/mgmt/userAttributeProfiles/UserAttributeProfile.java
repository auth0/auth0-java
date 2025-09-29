package com.auth0.json.mgmt.userAttributeProfiles;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserAttributeProfile {

    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("user_id")
    private UserId userId;
    @JsonProperty("user_attributes")
    private Map<String, UserAttributes> userAttributes;

    /**
     * Gets the user attribute profile ID.
     * @return the user attribute profile ID
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * Sets the user attribute profile ID.
     * @return the user attribute profile ID
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * Sets the user attribute profile name.
     * @param name the user attribute profile name
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the user ID configuration.
     * @return the user ID configuration
     */
    @JsonProperty("user_id")
    public UserId getUserId() {
        return userId;
    }

    /**
     * Sets the user ID configuration.
     * @param userId the user ID configuration
     */
    @JsonProperty("user_id")
    public void setUserId(UserId userId) {
        this.userId = userId;
    }

    /**
     * Gets the user attributes configuration.
     * @return the user attributes configuration
     */
    @JsonProperty("user_attributes")
    public Map<String, UserAttributes> getUserAttributes() {
        return userAttributes;
    }

    /**
     * Sets the user attributes configuration.
     * @param userAttributes the user attributes configuration
     */
    @JsonProperty("user_attributes")
    public void setUserAttributes(Map<String, UserAttributes> userAttributes) {
        this.userAttributes = userAttributes;
    }
}

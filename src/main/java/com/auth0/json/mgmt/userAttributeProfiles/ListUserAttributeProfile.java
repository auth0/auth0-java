package com.auth0.json.mgmt.userAttributeProfiles;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ListUserAttributeProfile {
    @JsonProperty("user_attribute_profiles")
    private List<UserAttributeProfile> userAttributeProfiles;

    /**
     * Gets the user attribute profiles.
     * @return the user attribute profiles
     */
    @JsonProperty("user_attribute_profiles")
    public List<UserAttributeProfile> getUserAttributeProfiles() {
        return userAttributeProfiles;
    }

    /**
     * Sets the user attribute profiles.
     * @param userAttributeProfiles the user attribute profiles
     */
    @JsonProperty("user_attribute_profiles")
    public void setUserAttributeProfiles(List<UserAttributeProfile> userAttributeProfiles) {
        this.userAttributeProfiles = userAttributeProfiles;
    }
}

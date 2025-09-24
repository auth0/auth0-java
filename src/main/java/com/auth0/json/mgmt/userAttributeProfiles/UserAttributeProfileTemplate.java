package com.auth0.json.mgmt.userAttributeProfiles;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserAttributeProfileTemplate {
    @JsonProperty("name")
    private String name;
    @JsonProperty("user_id")
    private UserId userId;
    @JsonProperty("user_attributes")
    private Map<String, UserAttributes> userAttributes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserId getUserId() {
        return userId;
    }

    public void setUserId(UserId userId) {
        this.userId = userId;
    }

    public Map<String, UserAttributes> getUserAttributes() {
        return userAttributes;
    }

    public void setUserAttributes(Map<String, UserAttributes> userAttributes) {
        this.userAttributes = userAttributes;
    }
}

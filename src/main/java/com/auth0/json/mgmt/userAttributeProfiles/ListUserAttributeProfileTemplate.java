package com.auth0.json.mgmt.userAttributeProfiles;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ListUserAttributeProfileTemplate {
    @JsonProperty("user_attribute_profile_templates")
    private List<UserAttributeProfileTemplateResponse> userAttributeProfileTemplateResponses;

    /**
     * Gets the user attribute profile templates
     * @return the user attribute profile templates
     */
    @JsonProperty("user_attribute_profile_templates")
    public List<UserAttributeProfileTemplateResponse> getUserAttributeProfileTemplates() {
        return userAttributeProfileTemplateResponses;
    }

    /**
     * Sets the user attribute profile templates
     * @param userAttributeProfileTemplateResponses the user attribute profile templates
     */
    @JsonProperty("user_attribute_profile_templates")
    public void setUserAttributeProfileTemplates(List<UserAttributeProfileTemplateResponse> userAttributeProfileTemplateResponses) {
        this.userAttributeProfileTemplateResponses = userAttributeProfileTemplateResponses;
    }
}

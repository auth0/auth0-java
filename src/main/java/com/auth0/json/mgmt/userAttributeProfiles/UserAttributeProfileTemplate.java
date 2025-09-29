package com.auth0.json.mgmt.userAttributeProfiles;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class that represents a User Attribute Profile Template object. Related to
 * the {@link com.auth0.client.mgmt.UserAttributeProfilesEntity} entity.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserAttributeProfileTemplate {

    @JsonProperty("id")
    private String id;
    @JsonProperty("display_name")
    private String displayName;
    @JsonProperty("template")
    private UserAttributeProfile template;

    /**
     * Getter for the template ID.
     *
     * @return the template ID.
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * Getter for the display name.
     *
     * @return the display name.
     */
    @JsonProperty("display_name")
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Setter for the display name.
     *
     * @param displayName the display name to set.
     */
    @JsonProperty("display_name")
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Getter for the template.
     *
     * @return the template.
     */
    @JsonProperty("template")
    public UserAttributeProfile getTemplate() {
        return template;
    }

    /**
     * Setter for the template.
     *
     * @param template the template to set.
     */
    @JsonProperty("template")
    public void setTemplate(UserAttributeProfile template) {
        this.template = template;
    }
}

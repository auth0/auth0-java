package com.auth0.json.mgmt.selfserviceprofiles;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserAttribute {
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("is_optional")
    private boolean isOptional;

    /**
     * Creates a new instance of the UserAttribute class.
     */
    public UserAttribute() { }

    /**
     * Creates a new instance of the UserAttribute class.
     * @param name the name of the user attribute.
     * @param description the description of the user attribute.
     * @param isOptional the isOptional of the user attribute.
     */
    @JsonCreator
    public UserAttribute(@JsonProperty("name") String name, @JsonProperty("description") String description, @JsonProperty("is_optional") boolean isOptional) {
        this.name = name;
        this.description = description;
        this.isOptional = isOptional;
    }

    /**
     * Getter for the name of the user attribute.
     * @return the name of the user attribute.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the name of the user attribute.
     * @param name the name of the user attribute to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for the description of the user attribute.
     * @return the description of the user attribute.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter for the description of the user attribute.
     * @param description the description of the user attribute to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter for the isOptional of the user attribute.
     * @return the isOptional of the user attribute.
     */
    public boolean getIsOptional() {
        return isOptional;
    }

    /**
     * Setter for the isOptional of the user attribute.
     * @param isOptional the isOptional of the user attribute to set.
     */
    public void setIsOptional(boolean isOptional) {
        this.isOptional = isOptional;
    }
}

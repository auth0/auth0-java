package com.auth0.json.mgmt.resourceserver;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class that represents the authorization details associated with a {@link ResourceServer}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthorizationDetails {

    @JsonProperty("type")
    private String type;

    public AuthorizationDetails() {}

    public AuthorizationDetails(String type) {
        this.type = type;
    }

    /**
     * @return the value of the {@code type} field
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the {@code type} field
     * @param type the value of the {@code type} field
     */
    public void setType(String type) {
        this.type = type;
    }
}

package com.auth0.json.mgmt.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Class that represents an Auth0 Application signed request object. Related to the {@link com.auth0.client.mgmt.ClientsEntity} entity.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SignedRequest {

    @JsonProperty("required")
    private Boolean required;
    @JsonProperty("credentials")
    private List<Credential> credentials;

    /**
     * @return the value of the {@code credentials} field
     */
    public List<Credential> getCredentials() {
        return credentials;
    }

    /**
     * Sets the value of the {@code credentials} field
     *
     * @param credentials the value of the {@code credentials} field
     */
    public void setCredentials(List<Credential> credentials) {
        this.credentials = credentials;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }
}

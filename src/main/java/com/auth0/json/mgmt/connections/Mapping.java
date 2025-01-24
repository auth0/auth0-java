package com.auth0.json.mgmt.connections;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Mapping {
    @JsonProperty("auth0")
    private String auth0;
    @JsonProperty("scim")
    private String scim;

    /**
     * Creates a new instance.
     */
    @JsonCreator
    public Mapping() {

    }

    /**
     * Creates a new instance with the given Auth0 and SCIM attributes.
     * @param auth0 the Auth0 attribute.
     * @param scim the SCIM attribute.
     */
    @JsonCreator
    public Mapping(@JsonProperty("auth0") String auth0, @JsonProperty("scim") String scim) {
        this.auth0 = auth0;
        this.scim = scim;
    }

    /**
     * Getter for the Auth0 attribute.
     * @return the Auth0 attribute.
     */
    public String getAuth0() {
        return auth0;
    }

    /**
     * Setter for the Auth0 attribute.
     * @param auth0 the Auth0 attribute to set.
     */
    public void setAuth0(String auth0) {
        this.auth0 = auth0;
    }

    /**
     * Getter for the SCIM attribute.
     * @return the SCIM attribute.
     */
    public String getScim() {
        return scim;
    }

    /**
     * Setter for the SCIM attribute.
     * @param scim the SCIM attribute to set.
     */
    public void setScim(String scim) {
        this.scim = scim;
    }
}

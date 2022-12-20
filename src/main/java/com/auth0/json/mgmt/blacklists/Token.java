package com.auth0.json.mgmt.blacklists;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class that represents an Auth0 Blacklisted Token object. Related to the {@link com.auth0.client.mgmt.BlacklistsEntity} entity.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Token {

    @JsonProperty("aud")
    private String aud;
    @JsonProperty("jti")
    private String jti;

    @JsonCreator
    public Token(@JsonProperty("jti") String jti) {
        this.jti = jti;
    }

    /**
     * Getter for the JWT's aud claim. This is the client id of the application for which this token was issued.
     *
     * @return the audience.
     */
    @JsonProperty("aud")
    public String getAud() {
        return aud;
    }

    /**
     * Setter for the JWT's aud claim. This is the client id of the application for which this token was issued.
     *
     * @param aud the audience to blacklist.
     */
    @JsonProperty("aud")
    public void setAud(String aud) {
        this.aud = aud;
    }

    /**
     * Getter for the JWT's jti claim.
     *
     * @return the audience.
     */
    @JsonProperty("jti")
    public String getJTI() {
        return jti;
    }

    /**
     * Setter for the JWT's jti to blacklist.
     *
     * @param jti the jwt identifier to blacklist.
     */
    @JsonProperty("jti")
    public void setJTI(String jti) {
        this.jti = jti;
    }
}

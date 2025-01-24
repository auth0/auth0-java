package com.auth0.json.mgmt.connections;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScimTokenCreateResponse extends ScimTokenBaseResponse {
    @JsonProperty("token")
    private String token;

    /**
     * Getter for the token.
     * @return the token.
     */
    public String getToken() {
        return token;
    }

    /**
     * Setter for the token.
     * @param token the token to set.
     */
    public void setToken(String token) {
        this.token = token;
    }
}

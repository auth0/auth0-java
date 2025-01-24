package com.auth0.json.mgmt.connections;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScimTokenRequest {
    @JsonProperty("scopes")
    private List<String> scopes;
    @JsonProperty("token_lifetime")
    private Integer tokenLifetime;

    /**
     * Getter for the scopes.
     * @return the scopes.
     */
    public List<String> getScopes() {
        return scopes;
    }

    /**
     * Setter for the scopes.
     * @param scopes the scopes to set.
     */
    public void setScopes(List<String> scopes) {
        this.scopes = scopes;
    }

    /**
     * Getter for the token lifetime.
     * @return the token lifetime.
     */
    public Integer getTokenLifetime() {
        return tokenLifetime;
    }

    /**
     * Setter for the token lifetime.
     * @param tokenLifetime the token lifetime to set.
     */
    public void setTokenLifetime(Integer tokenLifetime) {
        this.tokenLifetime = tokenLifetime;
    }
}

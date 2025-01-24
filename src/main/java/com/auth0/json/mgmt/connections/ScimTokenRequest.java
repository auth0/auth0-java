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
    private Integer token_lifetime;

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
    public Integer getToken_lifetime() {
        return token_lifetime;
    }

    /**
     * Setter for the token lifetime.
     * @param token_lifetime the token lifetime to set.
     */
    public void setToken_lifetime(Integer token_lifetime) {
        this.token_lifetime = token_lifetime;
    }
}

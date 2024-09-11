package com.auth0.json.mgmt.refreshtokens;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResourceServer {
    @JsonProperty("audience")
    private String audience;
    @JsonProperty("scopes")
    private List<String> scopes;

    /**
     * @return Resource server ID
     */
    public String getAudience() {
        return audience;
    }

    /**
     * @return List of scopes for the refresh token
     */
    public List<String> getScopes() {
        return scopes;
    }
}

package com.auth0.json.mgmt.selfserviceprofiles;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProvisioningConfig {
    @JsonProperty("scopes")
    private List<String> scopes;
    @JsonProperty("token_lifetime")
    private Integer tokenLifetime;
    @JsonProperty("google_workspace")
    private GoogleWorkspaceProvisioningConfig googleWorkspace;


    /**
     * Getter for the scopes.
     * @return the scopes.
     */
    @JsonProperty("scopes")
    public List<String> getScopes() {
        return scopes;
    }

    /**
     * Setter for the scopes.
     * @param scopes the scopes to set.
     */
    @JsonProperty("scopes")
    public void setScopes(List<String> scopes) {
        this.scopes = scopes;
    }

    /**
     * Getter for the Google Workspace provisioning config.
     * @return the Google Workspace provisioning config.
     */
    @JsonProperty("google_workspace")
    public GoogleWorkspaceProvisioningConfig getGoogleWorkspace() {
        return googleWorkspace;
    }

    /**
     * Getter for the token lifetime.
     * @return the token lifetime.
     */
    @JsonProperty("token_lifetime")
    public Integer getTokenLifetime() {
        return tokenLifetime;
    }

    /**
     * Setter for the token lifetime.
     * @param tokenLifetime the token lifetime to set.
     */
    @JsonProperty("token_lifetime")
    public void setTokenLifetime(Integer tokenLifetime) {
        this.tokenLifetime = tokenLifetime;
    }

    /**
     * Setter for the Google Workspace provisioning config.
     * @param googleWorkspace the Google Workspace provisioning config to set.
     */
    @JsonProperty("google_workspace")
    public void setGoogleWorkspace(GoogleWorkspaceProvisioningConfig googleWorkspace) {
        this.googleWorkspace = googleWorkspace;
    }
}

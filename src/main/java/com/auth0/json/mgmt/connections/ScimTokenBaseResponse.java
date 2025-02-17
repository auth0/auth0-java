package com.auth0.json.mgmt.connections;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScimTokenBaseResponse {
    @JsonProperty("token_id")
    private String tokenId;
    @JsonProperty("scopes")
    private List<String> scopes;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("valid_until")
    private String validUntil;

    /**
     * Getter for the token id.
     * @return the token id.
     */
    public String getTokenId() {
        return tokenId;
    }

    /**
     * Setter for the token id.
     * @param tokenId the token id to set.
     */
    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

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
     * Getter for the created at.
     * @return the created at.
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * Setter for the created at.
     * @param createdAt the created at to set.
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Getter for the valid until.
     * @return the valid until.
     */
    public String getValidUntil() {
        return validUntil;
    }

    /**
     * Setter for the valid until.
     * @param validUntil the valid until to set.
     */
    public void setValidUntil(String validUntil) {
        this.validUntil = validUntil;
    }
}

package com.auth0.json.mgmt.connections;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScimTokenResponse extends ScimTokenBaseResponse {
    @JsonProperty("last_used_at")
    private String lastUsedAt;

    /**
     * Getter for the last used at.
     * @return the last used at.
     */
    public String getLastUsedAt() {
        return lastUsedAt;
    }

    /**
     * Setter for the last used at.
     * @param lastUsedAt the last used at to set.
     */
    public void setLastUsedAt(String lastUsedAt) {
        this.lastUsedAt = lastUsedAt;
    }
}

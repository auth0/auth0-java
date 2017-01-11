package com.auth0.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ClientGrant {

    private String id;
    private String clientId;
    private String audience;
    private String[] scope;

    @JsonProperty(value = "id", access = JsonProperty.Access.WRITE_ONLY)
    public String getId() {
        return id;
    }

    @JsonProperty(value = "id", access = JsonProperty.Access.WRITE_ONLY)
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("client_id")
    public String getClientId() {
        return clientId;
    }

    @JsonProperty("client_id")
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @JsonProperty("audience")
    public String getAudience() {
        return audience;
    }

    @JsonProperty("audience")
    public void setAudience(String audience) {
        this.audience = audience;
    }

    @JsonProperty("scope")
    public String[] getScope() {
        return scope;
    }

    @JsonProperty("scope")
    public void setScope(String[] scope) {
        this.scope = scope;
    }
}

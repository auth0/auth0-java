package com.auth0.json.mgmt.connections;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EnabledClientRequest {
    @JsonProperty("client_id")
    private String clientId;

    @JsonProperty("status")
    private boolean status;

    /**
     * Constructor for the EnabledClientRequest.
     * @param clientId
     * @param status
     */
    public EnabledClientRequest(String clientId, boolean status) {
        this.clientId = clientId;
        this.status = status;
    }

    /**
     * Getter for the client ID.
     *
     * @return the client ID.
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * Getter for the status.
     *
     * @return the status.
     */
    @JsonProperty("status")
    public boolean isStatus() {
        return status;
    }
}

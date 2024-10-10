package com.auth0.json.mgmt.sessions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Client {
    @JsonProperty("client_id")
    private String clientId;

    /**
     * @return ID of client for the session
     */
    public String getClientId() {
        return clientId;
    }
}

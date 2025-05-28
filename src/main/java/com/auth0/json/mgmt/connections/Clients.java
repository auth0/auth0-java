package com.auth0.json.mgmt.connections;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Clients {
    @JsonProperty("client_id")
    private String clientId;

    /**
     * Default constructor for the Clients class.
     */
    public Clients() {
    }

    /**
     * Constructor for the Clients class.
     *
     * @param clientId the client ID.
     */
    public Clients(String clientId) {
        this.clientId = clientId;
    }

    /**
     * Getter for the client ID.
     *
     * @return the client ID.
     */
    public String getClientId() {
        return clientId;
    }
}

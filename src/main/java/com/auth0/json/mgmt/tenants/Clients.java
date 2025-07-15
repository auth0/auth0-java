package com.auth0.json.mgmt.tenants;

import com.auth0.json.mgmt.tokenquota.ClientCredentials;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Clients {
    @JsonProperty("client_credentials")
    private ClientCredentials clientCredentials;

    /**
     * Default constructor for Clients.
     */
    public Clients() {
    }

    /**
     * Constructor for Clients.
     *
     * @param clientCredentials the client credentials
     */
    public Clients(ClientCredentials clientCredentials) {
        this.clientCredentials = clientCredentials;
    }

    /**
     * @return the client credentials
     */
    public ClientCredentials getClientCredentials() {
        return clientCredentials;
    }
}

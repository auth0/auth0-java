package com.auth0.json.mgmt.tenants;

import com.auth0.json.mgmt.tokenquota.ClientCredentials;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Organizations {
    @JsonProperty("client_credentials")
    private ClientCredentials clientCredentials;

    /**
     * Default constructor for Organizations.
     */
    public Organizations() {}

    /**
     * Constructor for Organizations.
     *
     * @param clientCredentials the client credentials
     */
    public Organizations(ClientCredentials clientCredentials) {
        this.clientCredentials = clientCredentials;
    }

    /**
     * @return the client credentials
     */
    public ClientCredentials getClientCredentials() {
        return clientCredentials;
    }
}

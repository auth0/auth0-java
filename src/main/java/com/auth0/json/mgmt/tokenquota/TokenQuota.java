package com.auth0.json.mgmt.tokenquota;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TokenQuota {
    @JsonProperty("client_credentials")
    private ClientCredentials clientCredentials;

    /**
     * Default constructor for Clients.
     */
    public TokenQuota() {}
    /**
     * Constructor for Clients.
     *
     * @param clientCredentials the client credentials
     */
    public TokenQuota(ClientCredentials clientCredentials) {
        this.clientCredentials = clientCredentials;
    }

    /**
     * @return the client credentials
     */
    public ClientCredentials getClientCredentials() {
        return clientCredentials;
    }
}

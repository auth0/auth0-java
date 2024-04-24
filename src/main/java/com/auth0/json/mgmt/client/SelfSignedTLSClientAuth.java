package com.auth0.json.mgmt.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Class that represents an Auth0 Application self-signed TLS client authentication method. Related to the {@link com.auth0.client.mgmt.ClientsEntity} entity.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SelfSignedTLSClientAuth {

    @JsonProperty("credentials")
    private List<Credential> credentials;

    /**
     * Create a new instance
     * @param credentials the credentials to use
     */
    public SelfSignedTLSClientAuth(@JsonProperty("credentials") List<Credential> credentials) {
        this.credentials = credentials;
    }

    /**
     * @return the credentials
     */
    public List<Credential> getCredentials() {
        return credentials;
    }
}

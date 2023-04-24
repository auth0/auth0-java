package com.auth0.json.mgmt.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class that represents an Auth0 Application authentication methods. Related to the {@link com.auth0.client.mgmt.ClientsEntity} entity.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientAuthenticationMethods {

    @JsonProperty("private_key_jwt")
    private PrivateKeyJwt privateKeyJwt;

    public ClientAuthenticationMethods() {

    }

    public ClientAuthenticationMethods(PrivateKeyJwt privateKeyJwt) {
        this.privateKeyJwt = privateKeyJwt;
    }

    public PrivateKeyJwt getPrivateKeyJwt() {
        return privateKeyJwt;
    }
}

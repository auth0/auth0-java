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
    @JsonProperty("self_signed_tls_client_auth")
    private SelfSignedTLSClientAuth selfSignedTLSClientAuth;

    public ClientAuthenticationMethods() {

    }

    public ClientAuthenticationMethods(PrivateKeyJwt privateKeyJwt) {
        this(privateKeyJwt, null);
    }

    /**
     * Create a new instance.
     * @param selfSignedTLSClientAuth the value of the {@code self_signed_tls_client_auth} field.
     */
    public ClientAuthenticationMethods(PrivateKeyJwt privateKeyJwt, SelfSignedTLSClientAuth selfSignedTLSClientAuth) {
        this.privateKeyJwt = privateKeyJwt;
        this.selfSignedTLSClientAuth = selfSignedTLSClientAuth;
    }

    public PrivateKeyJwt getPrivateKeyJwt() {
        return privateKeyJwt;
    }

    /**
     * @return the value of the {@code self_signed_tls_client_auth} field
     */
    public SelfSignedTLSClientAuth getSelfSignedTLSClientAuth() {
        return selfSignedTLSClientAuth;
    }
}

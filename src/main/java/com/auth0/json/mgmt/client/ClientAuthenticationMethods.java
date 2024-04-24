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
    @JsonProperty("tls_client_auth")
    private TLSClientAuth tlsClientAuth;

    public ClientAuthenticationMethods() {

    }

    /**
     * Create a new instance.
     * @param privateKeyJwt the value of the {@code private_key_jwt} field.
     */
    public ClientAuthenticationMethods(PrivateKeyJwt privateKeyJwt) {
        this(privateKeyJwt, null, null);
    }

    /**
     * Create a new instance.
     * @param privateKeyJwt the value of the {@code private_key_jwt} field.
     * @param selfSignedTLSClientAuth the value of the {@code self_signed_tls_client_auth} field.
     */
    public ClientAuthenticationMethods(PrivateKeyJwt privateKeyJwt, SelfSignedTLSClientAuth selfSignedTLSClientAuth) {
        this(privateKeyJwt, selfSignedTLSClientAuth, null);
    }

    /**
     * Create a new instance.
     * @param privateKeyJwt the value of the {@code private_key_jwt} field.
     * @param selfSignedTLSClientAuth the value of the {@code self_signed_tls_client_auth} field.
     * @param tlsClientAuth the value of the {@code tls_client_auth} field.
     */
    public ClientAuthenticationMethods(PrivateKeyJwt privateKeyJwt, SelfSignedTLSClientAuth selfSignedTLSClientAuth, TLSClientAuth tlsClientAuth) {
        this.privateKeyJwt = privateKeyJwt;
        this.selfSignedTLSClientAuth = selfSignedTLSClientAuth;
        this.tlsClientAuth = tlsClientAuth;
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

    /**
     * @return the value of the {@code tls_client_auth} field
     */
    public TLSClientAuth getTlsClientAuth() {
        return tlsClientAuth;
    }
}

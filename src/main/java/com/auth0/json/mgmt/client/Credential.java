package com.auth0.json.mgmt.client;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Class that represents an Auth0 application credential object. Related to the {@link com.auth0.client.mgmt.ClientsEntity} entity.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Credential {

    @JsonProperty("credential_type")
    private String credentialType;
    @JsonProperty("name")
    private String name;
    @JsonProperty("pem")
    private String pem;

    @JsonProperty("id")
    private String id;
    @JsonProperty("kid")
    private String kid;
    @JsonProperty("thumbprint")
    private String thumbprint;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("created_at")
    private Date createdAt;

    /**
     * Create a new credential
     * @param credentialType the credential type
     * @param pem the PEM
     */
    public Credential(String credentialType, String pem) {
        this.credentialType = credentialType;
        this.pem = pem;
    }

    /**
     * Create a new credential
     * @param id the ID of the credential
     */
    public Credential(String id) {
        this.id = id;
    }

    /**
     * Create a new credential
     */
    public Credential() {}

    /**
     * @return the credential type
     */
    public String getCredentialType() {
        return credentialType;
    }

    /**
     * Sets the credential type
     * @param credentialType the credential type
     */
    public void setCredentialType(String credentialType) {
        this.credentialType = credentialType;
    }

    /**
     * @return the credential name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the credential name
     * @param name the name of the credential
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the credential's PEM
     */
    public String getPem() {
        return pem;
    }

    /**
     * Sets the credential's PEM
     * @param pem the PEM of the credential
     */
    public void setPem(String pem) {
        this.pem = pem;
    }

    /**
     * @return the ID of the credential
     */
    public String getId() {
        return id;
    }

    /**
     * @return the KID of the credential
     */
    public String getKid() {
        return kid;
    }

    /**
     * @return the thumbprint of the credential
     */
    public String getThumbprint() {
        return thumbprint;
    }

    /**
     * @return the date the credential was created at
     */
    public Date getCreatedAt() {
        return createdAt;
    }
}

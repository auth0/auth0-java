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
    @JsonProperty("alg")
    private String alg;
    @JsonProperty("parse_expiry_from_cert")
    private Boolean parseExpiryFromCert;
    @JsonProperty("subject_dn")
    private String subjectDn;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("created_at")
    private Date createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("updated_at")
    private Date updatedAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("expires_at")
    private Date expiresAt;

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

    /**
     * @return the algorithm of this credential
     */
    public String getAlg() {
        return alg;
    }

    /**
     * Set the algorithm
     * @param alg the algorithm
     */
    public void setAlg(String alg) {
        this.alg = alg;
    }

    /**
     * @return the time this credential was last updated
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * @return the expiration time of this credential
     */
    public Date getExpiresAt() {
        return expiresAt;
    }

    /**
     * Set the expires_at value for this credential
     * @param expiresAt the time this credential should expire
     */
    public void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
    }

    /**
     * @return whether the expiry will be parsed from the x509 certificate
     */
    public Boolean getParseExpiryFromCert() {
        return parseExpiryFromCert;
    }

    /**
     * Whether to parse expiry from x509 certificate
     * @param parseExpiryFromCert true to parse expiry; false otherwise.
     */
    public void setParseExpiryFromCert(Boolean parseExpiryFromCert) {
        this.parseExpiryFromCert = parseExpiryFromCert;
    }

    /**
     * @return the value of the {@code subject_dn} field
     */
    public String getSubjectDn() {
        return subjectDn;
    }

    /**
     * Sets the value of the {@code subject_dn} field
     * @param subjectDn the value of the {@code subject_dn} field
     */
    public void setSubjectDn(String subjectDn) {
        this.subjectDn = subjectDn;
    }
}

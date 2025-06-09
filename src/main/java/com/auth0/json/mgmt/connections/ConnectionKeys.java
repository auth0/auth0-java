package com.auth0.json.mgmt.connections;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConnectionKeys {
    @JsonProperty("kid")
    private String kid;

    @JsonProperty("algorithm")
    private String algorithm;

    @JsonProperty("key_use")
    private String keyUse;

    @JsonProperty("subject_dn")
    private String subjectDn;

    @JsonProperty("cert")
    private String cert;

    @JsonProperty("fingerprint")
    private String fingerprint;

    @JsonProperty("thumbprint")
    private String thumbprint;

    @JsonProperty("pkcs")
    private String pkcs;

    @JsonProperty("current")
    private Boolean current;

    @JsonProperty("current_since")
    private String currentSince;

    @JsonProperty("next")
    private Boolean next;

    /**
     * Getter for the Key ID (kid).
     * @return the Key ID (kid).
     */
    public String getKid() {
        return kid;
    }

    /**
     * Setter for the Key ID (kid).
     * @param kid the Key ID (kid).
     */
    public void setKid(String kid) {
        this.kid = kid;
    }

    /**
     * Getter for the algorithm used by the key.
     * @return the algorithm used by the key.
     */
    public String getAlgorithm() {
        return algorithm;
    }

    /**
     * Setter for the algorithm used by the key.
     * @param algorithm the algorithm used by the key.
     */
    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    /**
     * Getter for the key use (e.g., "sig" for signature).
     * @return the key use.
     */
    public String getKeyUse() {
        return keyUse;
    }

    /**
     * Setter for the key use.
     * @param keyUse the key use (e.g., "sig" for signature).
     */
    public void setKeyUse(String keyUse) {
        this.keyUse = keyUse;
    }

    /**
     * Getter for the subject distinguished name (DN).
     * @return the subject DN.
     */
    public String getSubjectDn() {
        return subjectDn;
    }

    /**
     * Setter for the subject distinguished name (DN).
     * @param subjectDn the subject DN.
     */
    public void setSubjectDn(String subjectDn) {
        this.subjectDn = subjectDn;
    }

    /**
     * Getter for the certificate associated with the key.
     * @return the certificate.
     */
    public String getCert() {
        return cert;
    }

    /**
     * Setter for the certificate associated with the key.
     * @param cert the certificate.
     */
    public void setCert(String cert) {
        this.cert = cert;
    }

    /**
     * Getter for the fingerprint of the key.
     * @return the fingerprint.
     */
    public String getFingerprint() {
        return fingerprint;
    }

    /**
     * Setter for the fingerprint of the key.
     * @param fingerprint the fingerprint.
     */
    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }

    /**
     * Getter for the thumbprint of the key.
     * @return the thumbprint.
     */
    public String getThumbprint() {
        return thumbprint;
    }

    /**
     * Setter for the thumbprint of the key.
     * @param thumbprint the thumbprint.
     */
    public void setThumbprint(String thumbprint) {
        this.thumbprint = thumbprint;
    }

    /**
     * Getter for the PKCS#8 representation of the key.
     * @return the PKCS#8 representation.
     */
    public String getPkcs() {
        return pkcs;
    }

    /**
     * Setter for the PKCS#8 representation of the key.
     * @param pkcs the PKCS#8 representation.
     */
    public void setPkcs(String pkcs) {
        this.pkcs = pkcs;
    }

    /**
     * Getter for whether the key is currently active.
     * @return true if the key is current, false otherwise.
     */
    public Boolean getCurrent() {
        return current;
    }

    /**
     * Setter for whether the key is currently active.
     * @param current true if the key is current, false otherwise.
     */
    public void setCurrent(Boolean current) {
        this.current = current;
    }

    /**
     * Getter for the timestamp when the key became current.
     * @return the timestamp in ISO 8601 format.
     */
    public String getCurrentSince() {
        return currentSince;
    }

    /**
     * Setter for the timestamp when the key became current.
     * @param currentSince the timestamp in ISO 8601 format.
     */
    public void setCurrentSince(String currentSince) {
        this.currentSince = currentSince;
    }

    /**
     * Getter for whether there is a next key available.
     * @return true if there is a next key, false otherwise.
     */
    public Boolean getNext() {
        return next;
    }

    /**
     * Setter for whether there is a next key available.
     * @param next true if there is a next key, false otherwise.
     */
    public void setNext(Boolean next) {
        this.next = next;
    }
}

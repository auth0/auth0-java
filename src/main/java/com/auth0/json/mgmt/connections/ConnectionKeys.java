package com.auth0.json.mgmt.connections;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

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

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("current_since")
    private Date currentSince;

    @JsonProperty("next")
    private Boolean next;

    public String getKid() {
        return kid;
    }

    public void setKid(String kid) {
        this.kid = kid;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getKeyUse() {
        return keyUse;
    }

    public void setKeyUse(String keyUse) {
        this.keyUse = keyUse;
    }

    public String getSubjectDn() {
        return subjectDn;
    }

    public void setSubjectDn(String subjectDn) {
        this.subjectDn = subjectDn;
    }

    public String getCert() {
        return cert;
    }

    public void setCert(String cert) {
        this.cert = cert;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }

    public String getThumbprint() {
        return thumbprint;
    }

    public void setThumbprint(String thumbprint) {
        this.thumbprint = thumbprint;
    }

    public String getPkcs() {
        return pkcs;
    }

    public void setPkcs(String pkcs) {
        this.pkcs = pkcs;
    }

    public Boolean getCurrent() {
        return current;
    }

    public void setCurrent(Boolean current) {
        this.current = current;
    }

    public Date getCurrentSince() {
        return currentSince;
    }

    public void setCurrentSince(Date currentSince) {
        this.currentSince = currentSince;
    }

    public Boolean getNext() {
        return next;
    }

    public void setNext(Boolean next) {
        this.next = next;
    }
}

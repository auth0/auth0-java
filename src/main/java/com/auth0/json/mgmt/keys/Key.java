package com.auth0.json.mgmt.keys;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Class that represents an Auth0 Key object. Related to the {@link com.auth0.client.mgmt.KeysEntity} entity.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Key {

    @JsonProperty("kid")
    private String kid;

    @JsonProperty("cert")
    private String cert;

    @JsonProperty("pkcs7")
    private String pkcs7;

    @JsonProperty("current")
    private Boolean current;

    @JsonProperty("next")
    private Boolean next;

    @JsonProperty("previous")
    private Boolean previous;

    @JsonProperty("fingerprint")
    private String fingerprint;

    @JsonProperty("thumbprint")
    private String thumbprint;

    @JsonProperty("revoked")
    private Boolean revoked;

    @JsonProperty("current_since")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Date currentSince;

    @JsonProperty("current_until")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Date currentUntil;

    @JsonProperty("revoked_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Date revokedAt;

    @JsonProperty("kid")
    public String getKid() {
        return kid;
    }

    @JsonProperty("kid")
    public void setKid(String kid) {
        this.kid = kid;
    }

    @JsonProperty("cert")
    public String getCert() {
        return cert;
    }

    @JsonProperty("cert")
    public void setCert(String cert) {
        this.cert = cert;
    }

    @JsonProperty("pkcs7")
    public String getPkcs7() {
        return pkcs7;
    }

    @JsonProperty("pkcs7")
    public void setPkcs7(String pkcs7) {
        this.pkcs7 = pkcs7;
    }

    @JsonProperty("current")
    public Boolean getCurrent() {
        return current;
    }

    @JsonProperty("current")
    public void setCurrent(Boolean current) {
        this.current = current;
    }

    @JsonProperty("next")
    public Boolean getNext() {
        return next;
    }

    @JsonProperty("next")
    public void setNext(Boolean next) {
        this.next = next;
    }

    @JsonProperty("previous")
    public Boolean getPrevious() {
        return previous;
    }

    @JsonProperty("previous")
    public void setPrevious(Boolean previous) {
        this.previous = previous;
    }

    @JsonProperty("fingerprint")
    public String getFingerprint() {
        return fingerprint;
    }

    @JsonProperty("fingerprint")
    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }

    @JsonProperty("thumbprint")
    public String getThumbprint() {
        return thumbprint;
    }

    @JsonProperty("thumbprint")
    public void setThumbprint(String thumbprint) {
        this.thumbprint = thumbprint;
    }

    @JsonProperty("revoked")
    public Boolean getRevoked() {
        return revoked;
    }

    @JsonProperty("revoked")
    public void setRevoked(Boolean revoked) {
        this.revoked = revoked;
    }

    @JsonProperty("current_since")
    public Date getCurrentSince() {
        return currentSince;
    }

    @JsonProperty("current_since")
    public void setCurrentSince(Date currentSince) {
        this.currentSince = currentSince;
    }

    @JsonProperty("current_until")
    public Date getCurrentUntil() {
        return currentUntil;
    }

    @JsonProperty("current_until")
    public void setCurrentUntil(Date currentUntil) {
        this.currentUntil = currentUntil;
    }

    @JsonProperty("revoked_at")
    public Date getRevokedAt() {
        return revokedAt;
    }

    @JsonProperty("revoked_at")
    public void setRevokedAt(Date revokedAt) {
        this.revokedAt = revokedAt;
    }
}

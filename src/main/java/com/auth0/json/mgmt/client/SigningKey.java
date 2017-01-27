package com.auth0.json.mgmt.client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings({"WeakerAccess", "unused"})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SigningKey {

    @JsonProperty("cert")
    private String cert;
    @JsonProperty("pkcs7")
    private String pkcs7;
    @JsonProperty("subject")
    private String subject;

    @JsonCreator
    public SigningKey(@JsonProperty("cert") String cert, @JsonProperty("pkcs7") String pkcs7, @JsonProperty("subject") String subject) {
        this.cert = cert;
        this.pkcs7 = pkcs7;
        this.subject = subject;
    }

    /**
     * Getter for the signing public key.
     *
     * @return the public key.
     */
    @JsonProperty("cert")
    public String getCert() {
        return cert;
    }

    /**
     * Setter for the signing public key.
     *
     * @return the public key to use.
     */
    @JsonProperty("cert")
    public void setCert(String cert) {
        this.cert = cert;
    }

    /**
     * Getter for the signing public key in PKCS#7 format.
     *
     * @return the public key in PKCS#7 format.
     */
    @JsonProperty("pkcs7")
    public String getPKCS7() {
        return pkcs7;
    }

    /**
     * Setter for the signing public key in PKCS#7 format.
     *
     * @param pkcs7 the public key in PKCS#7 format to use.
     */
    @JsonProperty("pkcs7")
    public void setPKCS7(String pkcs7) {
        this.pkcs7 = pkcs7;
    }

    /**
     * Getter for the subject.
     *
     * @return the subject.
     */
    @JsonProperty("subject")
    public String getSubject() {
        return subject;
    }

    /**
     * Setter for the subject.
     *
     * @param subject the subject to set.
     */
    @JsonProperty("subject")
    public void setSubject(String subject) {
        this.subject = subject;
    }
}

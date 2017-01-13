package com.auth0.json.mgmt.client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

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

    public String getCert() {
        return cert;
    }

    public void setCert(String cert) {
        this.cert = cert;
    }

    public String getPKCS7() {
        return pkcs7;
    }

    public void setPKCS7(String pkcs7) {
        this.pkcs7 = pkcs7;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}

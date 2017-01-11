package com.auth0.json.mgmt.client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SigningKey {

    private String cert;
    private String pkcs7;
    private String subject;

    @JsonCreator
    public SigningKey(@JsonProperty("cert") String cert, @JsonProperty("pkcs7") String pkcs7, @JsonProperty("subject") String subject) {
        this.cert = cert;
        this.pkcs7 = pkcs7;
        this.subject = subject;
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
    public String getPKCS7() {
        return pkcs7;
    }

    @JsonProperty("pkcs7")
    public void setPKCS7(String pkcs7) {
        this.pkcs7 = pkcs7;
    }

    @JsonProperty("subject")
    public String getSubject() {
        return subject;
    }

    @JsonProperty("subject")
    public void setSubject(String subject) {
        this.subject = subject;
    }
}

package com.auth0.json.mgmt.client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EncryptionKey {

    @JsonProperty("pub")
    private String pub;
    @JsonProperty("cert")
    private String cert;
    @JsonProperty("subject")
    private String subject;

    @JsonCreator
    public EncryptionKey(@JsonProperty("pub") String pub, @JsonProperty("cert") String cert) {
        this.pub = pub;
        this.cert = cert;
    }

    public String getPub() {
        return pub;
    }

    public void setPub(String pub) {
        this.pub = pub;
    }

    public String getCert() {
        return cert;
    }

    public void setCert(String cert) {
        this.cert = cert;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}

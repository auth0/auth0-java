package com.auth0.json.mgmt.connections;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RotateKey {
    @JsonProperty("cert")
    private String cert;
    @JsonProperty("kid")
    private String kid;

    /**
     * Returns the certificate used for the key rotation.
     * @return the certificate as a String.
     */
    public String getCert() {
        return cert;
    }

    /**
     * Sets the certificate used for the key rotation.
     * @param cert the certificate as a String.
     */
    public void setCert(String cert) {
        this.cert = cert;
    }

    /**
     * Returns the Key ID (kid) of the key being rotated.
     * @return the Key ID as a String.
     */
    public String getKid() {
        return kid;
    }

    /**
     * Sets the Key ID (kid) of the key being rotated.
     * @param kid the Key ID as a String.
     */
    public void setKid(String kid) {
        this.kid = kid;
    }
}

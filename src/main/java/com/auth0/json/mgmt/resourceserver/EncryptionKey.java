package com.auth0.json.mgmt.resourceserver;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class that represents the encryption key associated with a {@link TokenEncryption}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EncryptionKey {

    @JsonProperty("name")
    private String name;
    @JsonProperty("alg")
    private String alg;
    @JsonProperty("pem")
    private String pem;
    @JsonProperty("kid")
    private String kid;
    @JsonProperty("thumbprint_sha256")
    private String thumbprintSha256;

    /**
     * @return the value of the {@code name} field.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the {@code name} field.
     * @param name the value of the {@code name} field.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the value of the {@code alg} field.
     */
    public String getAlg() {
        return alg;
    }

    /**
     * Sets the value of the {@code alg} field.
     * @param alg the value of the {@code alg} field.
     */
    public void setAlg(String alg) {
        this.alg = alg;
    }

    /**
     * @return the value of the {@code pem} field.
     */
    public String getPem() {
        return pem;
    }

    /**
     * Sets the value of the {@code pem} field.
     * @param pem the value of the {@code pem} field.
     */
    public void setPem(String pem) {
        this.pem = pem;
    }

    /**
     * @return the value of the {@code kid} field.
     */
    public String getKid() {
        return kid;
    }

    /**
     * Sets the value of the {@code kid} field.
     * @param kid the value of the {@code kid} field.
     */
    public void setKid(String kid) {
        this.kid = kid;
    }

    /**
     * @return the value of the {@code thumbprint_sha256} field.
     */
    public String getThumbprintSha256() {
        return thumbprintSha256;
    }

    /**
     * Sets the value of the {@code thumbprint_sha256} field.
     * @param thumbprintSha256 the value of the {@code thumbprint_sha256} field.
     */
    public void setThumbprintSha256(String thumbprintSha256) {
        this.thumbprintSha256 = thumbprintSha256;
    }
}

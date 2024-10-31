package com.auth0.json.mgmt.keys;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EncryptionWrappingKeyResponse {
    @JsonProperty("public_key")
    private String publicKey;
    @JsonProperty("algorithm")
    private String algorithm;

    /**
     * Getter for the public key.
     * @return the public key.
     */
    public String getPublicKey() {
        return publicKey;
    }

    /**
     * Setter for the public key.
     * @param publicKey the public key.
     */
    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    /**
     * Getter for the algorithm.
     * @return the algorithm.
     */
    public String getAlgorithm() {
        return algorithm;
    }

    /**
     * Setter for the algorithm.
     * @param algorithm the algorithm.
     */
    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }
}

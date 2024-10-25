package com.auth0.json.mgmt.keys;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EncryptionKey {
    @JsonProperty("kid")
    private String kid;
    @JsonProperty("type")
    private String type;
    @JsonProperty("state")
    private String state;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("updated_at")
    private String updatedAt;
    @JsonProperty("parent_kid")
    private String parentKid;
    @JsonProperty("public_key")
    private String publicKey;

    /**
     * Getter for the key id.
     * @return
     */
    public String getKid() {
        return kid;
    }

    /**
     * Setter for the key id.
     * @param kid
     */
    public void setKid(String kid) {
        this.kid = kid;
    }

    /**
     * Getter for the key type.
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     * Setter for the key type.
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Getter for the key state.
     * @return
     */
    public String getState() {
        return state;
    }

    /**
     * Setter for the key state.
     * @param state
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Getter for the key creation date.
     * @return
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * Setter for the key creation date.
     * @param createdAt
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Getter for the key update date.
     * @return
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Setter for the key update date.
     * @param updatedAt
     */
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * Getter for the parent key id.
     * @return
     */
    public String getParentKid() {
        return parentKid;
    }

    /**
     * Setter for the parent key id.
     * @param parentKid
     */
    public void setParentKid(String parentKid) {
        this.parentKid = parentKid;
    }

    /**
     * Getter for the public key.
     * @return
     */
    public String getPublicKey() {
        return publicKey;
    }

    /**
     * Setter for the public key.
     * @param publicKey
     */
    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }
}

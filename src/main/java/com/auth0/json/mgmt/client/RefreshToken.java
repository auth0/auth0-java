package com.auth0.json.mgmt.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class that represents the configuration of refresh tokens for a client.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RefreshToken {

    @JsonProperty("rotation_type")
    private String rotationType;
    @JsonProperty("expiration_type")
    private String expirationType;
    @JsonProperty("leeway")
    private Integer leeway;
    @JsonProperty("token_lifetime")
    private Integer tokenLifetime;
    @JsonProperty("infinite_token_lifetime")
    private Boolean infiniteTokenLifetime;
    @JsonProperty("idle_token_lifetime")
    private Integer idleTokenLifetime;
    @JsonProperty("infinite_idle_token_lifetime")
    private Boolean infiniteIdleTokenLifetime;

    /**
     * Getter for the rotation type of the refresh token.
     *
     * @return the rotation type.
     */
    @JsonProperty("rotation_type")
    public String getRotationType() {
        return rotationType;
    }

    /**
     * Setter for the rotation type of the refresh token.
     *
     * @param rotationType the rotation type to set.
     */
    @JsonProperty("rotation_type")
    public void setRotationType(String rotationType) {
        this.rotationType = rotationType;
    }

    /**
     * Getter for the expiration type of the refresh token.
     *
     * @return the expiration type.
     */
    @JsonProperty("expiration_type")
    public String getExpirationType() {
        return expirationType;
    }

    /**
     * Setter for the expiration type of the refresh token.
     *
     * @param expirationType the expiration type to set.
     */
    @JsonProperty("expiration_type")
    public void setExpirationType(String expirationType) {
        this.expirationType = expirationType;
    }

    /**
     * Getter for the period in seconds where the previous refresh token can be exchanged without
     * triggering breach detection.
     *
     * @return the leeway in seconds.
     */
    @JsonProperty("leeway")
    public Integer getLeeway() {
        return leeway;
    }

    /**
     * Setter for the period in seconds where the previous refresh token can be exchanged without
     * triggering breach detection.
     *
     * @param leeway the leeway in seconds.
     */
    @JsonProperty("leeway")
    public void setLeeway(Integer leeway) {
        this.leeway = leeway;
    }

    /**
     * Getter for the period in seconds for which refresh tokens will remain valid.
     *
     * @return a token's lifetime in seconds.
     */
    @JsonProperty("token_lifetime")
    public Integer getTokenLifetime() {
        return tokenLifetime;
    }

    /**
     * Setter for the period in seconds for which refresh tokens will remain valid.
     *
     * @param tokenLifetime a token's lifetime in seconds.
     */
    @JsonProperty("token_lifetime")
    public void setTokenLifetime(Integer tokenLifetime) {
        this.tokenLifetime = tokenLifetime;
    }

    /**
     * Getter for determining whether tokens have a set lifetime or not. This takes precedence over
     * token_lifetime values.
     *
     * @return true if tokens do not have a set lifetime, false otherwise.
     */
    @JsonProperty("infinite_token_lifetime")
    public Boolean getInfiniteTokenLifetime() {
        return infiniteTokenLifetime;
    }

    /**
     * Setter for determining whether tokens have a set lifetime or not. This takes precedence over
     * token_lifetime values.
     *
     * @param infiniteTokenLifetime true if tokens do not have a set lifetime, false otherwise.
     */
    @JsonProperty("infinite_token_lifetime")
    public void setInfiniteTokenLifetime(Boolean infiniteTokenLifetime) {
        this.infiniteTokenLifetime = infiniteTokenLifetime;
    }

    /**
     * Getter for the period in seconds for which refresh tokens will remain valid without use.
     *
     * @return a token's lifetime without use in seconds.
     */
    @JsonProperty("idle_token_lifetime")
    public Integer getIdleTokenLifetime() {
        return idleTokenLifetime;
    }

    /**
     * Setter for the period in seconds for which refresh tokens will remain valid without use.
     *
     * @param idleTokenLifetime a token's lifetime without use in seconds.
     */
    @JsonProperty("idle_token_lifetime")
    public void setIdleTokenLifetime(Integer idleTokenLifetime) {
        this.idleTokenLifetime = idleTokenLifetime;
    }

    /**
     * Getter for determining whether tokens expire without use or not. This takes precedence over
     * idle_token_lifetime values.
     *
     * @return true if tokens do not expire from lack of use, false otherwise.
     */
    @JsonProperty("infinite_idle_token_lifetime")
    public Boolean getInfiniteIdleTokenLifetime() {
        return infiniteIdleTokenLifetime;
    }

    /**
     * Setter for determining whether tokens expire without use or not. This takes precedence over
     * idle_token_lifetime values.
     *
     * @param infiniteIdleTokenLifetime true if tokens do not expire from lack of use, false
     *                                  otherwise.
     */
    @JsonProperty("infinite_idle_token_lifetime")
    public void setInfiniteIdleTokenLifetime(Boolean infiniteIdleTokenLifetime) {
        this.infiniteIdleTokenLifetime = infiniteIdleTokenLifetime;
    }
}

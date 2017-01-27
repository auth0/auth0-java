package com.auth0.json.mgmt.client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings({"unused", "WeakerAccess"})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JWTConfiguration {

    @JsonProperty("lifetime_in_seconds")
    private Integer lifetimeInSeconds;
    @JsonProperty("secret_encoded")
    private Boolean secretEncoded;
    @JsonProperty("scopes")
    private Object scopes;
    @JsonProperty("alg")
    private String alg;

    @JsonCreator
    public JWTConfiguration(@JsonProperty("lifetime_in_seconds") Integer lifetimeInSeconds,
                            @JsonProperty("scopes") Object scopes, @JsonProperty("alg") String alg) {
        this.lifetimeInSeconds = lifetimeInSeconds;
        this.scopes = scopes;
        this.alg = alg;
    }

    /**
     * Getter for the amount of seconds the JWT will be valid. (Affects 'exp' claim)
     *
     * @return the lifetime in seconds.
     */
    @JsonProperty("lifetime_in_seconds")
    public Integer getLifetimeInSeconds() {
        return lifetimeInSeconds;
    }

    /**
     * Setter for the amount of seconds the JWT will be valid. (Affects 'exp' claim)
     *
     * @param lifetimeInSeconds the lifetime in seconds to set.
     */
    @JsonProperty("lifetime_in_seconds")
    public void setLifetimeInSeconds(Integer lifetimeInSeconds) {
        this.lifetimeInSeconds = lifetimeInSeconds;
    }

    /**
     * Whether the client secret is base64 encoded or not.
     *
     * @return true if the client secret is base64 encoded, false otherwise.
     */
    @JsonProperty("secret_encoded")
    public Boolean isSecretEncoded() {
        return secretEncoded;
    }

    /**
     * Getter for the scopes.
     *
     * @return the scopes.
     */
    @JsonProperty("scopes")
    public Object getScopes() {
        return scopes;
    }

    /**
     * Setter for the scopes.
     *
     * @param scopes the scopes value to set.
     */
    @JsonProperty("scopes")
    public void setScopes(Object scopes) {
        this.scopes = scopes;
    }

    /**
     * Getter for the algorithm used to sign JWTs.
     *
     * @return the algorithm used to sign JWTs.
     */
    @JsonProperty("alg")
    public String getAlgorithm() {
        return alg;
    }

    /**
     * Setter for the algorithm used to sign JWTs.
     *
     * @param alg the algorithm to use to sign JWTs.
     */
    @JsonProperty("alg")
    public void setAlgorithm(String alg) {
        this.alg = alg;
    }
}
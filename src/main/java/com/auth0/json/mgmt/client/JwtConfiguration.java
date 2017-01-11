package com.auth0.json.mgmt.client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class JwtConfiguration {

    private Integer lifetimeInSeconds;
    private Boolean secretEncoded;
    private Object scopes;
    private String alg;

    @JsonCreator
    public JwtConfiguration(@JsonProperty("lifetime_in_seconds") Integer lifetimeInSeconds, @JsonProperty("secret_encoded") Boolean secretEncoded,
                            @JsonProperty("scopes") Object scopes, @JsonProperty("alg") String alg) {
        this.lifetimeInSeconds = lifetimeInSeconds;
        this.secretEncoded = secretEncoded;
        this.scopes = scopes;
        this.alg = alg;
    }

    @JsonProperty("lifetime_in_seconds")
    public Integer getLifetimeInSeconds() {
        return lifetimeInSeconds;
    }

    @JsonProperty("lifetime_in_seconds")
    public void setLifetimeInSeconds(Integer lifetimeInSeconds) {
        this.lifetimeInSeconds = lifetimeInSeconds;
    }

    @JsonProperty(value = "secret_encoded", access = JsonProperty.Access.WRITE_ONLY)
    public Boolean getSecretEncoded() {
        return secretEncoded;
    }

    @JsonProperty(value = "secret_encoded", access = JsonProperty.Access.WRITE_ONLY)
    public void setSecretEncoded(Boolean secretEncoded) {
        this.secretEncoded = secretEncoded;
    }

    @JsonProperty("scopes")
    public Object getScopes() {
        return scopes;
    }

    @JsonProperty("scopes")
    public void setScopes(Object scopes) {
        this.scopes = scopes;
    }

    @JsonProperty("alg")
    public String getAlg() {
        return alg;
    }

    @JsonProperty("alg")
    public void setAlg(String alg) {
        this.alg = alg;
    }
}
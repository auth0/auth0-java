package com.auth0.json.mgmt.client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JwtConfiguration {

    @JsonProperty("lifetime_in_seconds")
    private Integer lifetimeInSeconds;
    @JsonProperty("secret_encoded")
    private Boolean secretEncoded;
    @JsonProperty("scopes")
    private Object scopes;
    @JsonProperty("alg")
    private String alg;

    @JsonCreator
    public JwtConfiguration(@JsonProperty("lifetime_in_seconds") Integer lifetimeInSeconds,
                            @JsonProperty("scopes") Object scopes, @JsonProperty("alg") String alg) {
        this.lifetimeInSeconds = lifetimeInSeconds;
        this.scopes = scopes;
        this.alg = alg;
    }

    public Integer getLifetimeInSeconds() {
        return lifetimeInSeconds;
    }

    public void setLifetimeInSeconds(Integer lifetimeInSeconds) {
        this.lifetimeInSeconds = lifetimeInSeconds;
    }

    public Boolean getSecretEncoded() {
        return secretEncoded;
    }

    public Object getScopes() {
        return scopes;
    }

    public void setScopes(Object scopes) {
        this.scopes = scopes;
    }

    public String getAlg() {
        return alg;
    }

    public void setAlg(String alg) {
        this.alg = alg;
    }
}
package com.auth0.json.mgmt.client;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResourceServer {

    @JsonProperty("name")
    private String name;
    @JsonProperty("identifier")
    private String identifier;
    @JsonProperty("scopes")
    private List<Object> scopes;
    @JsonProperty("signing_alg")
    private String signingAlg;
    @JsonProperty("signing_secret")
    private String signingSecret;
    @JsonProperty("token_lifetime")
    private String tokenLifetime;

    @JsonCreator
    public ResourceServer(@JsonProperty("identifier") String identifier) {
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentifier() {
        return identifier;
    }

    public List<Object> getScopes() {
        return scopes;
    }

    public void setScopes(List<Object> scopes) {
        this.scopes = scopes;
    }

    public String getSigningAlg() {
        return signingAlg;
    }

    public void setSigningAlg(String signingAlg) {
        this.signingAlg = signingAlg;
    }

    public String getSigningSecret() {
        return signingSecret;
    }

    public void setSigningSecret(String signingSecret) {
        this.signingSecret = signingSecret;
    }

    public String getTokenLifetime() {
        return tokenLifetime;
    }

    public void setTokenLifetime(String tokenLifetime) {
        this.tokenLifetime = tokenLifetime;
    }
}
package com.auth0.json.mgmt.client;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResourceServer {

    @JsonProperty("identifier")
    private String identifier;
    @JsonProperty("scopes")
    private List<String> scopes;

    @JsonCreator
    public ResourceServer(@JsonProperty("identifier") String identifier, @JsonProperty("scopes") List<String> scopes) {
        this.identifier = identifier;
        this.scopes = scopes;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public List<String> getScopes() {
        return scopes;
    }

    public void setScopes(List<String> scopes) {
        this.scopes = scopes;
    }
}
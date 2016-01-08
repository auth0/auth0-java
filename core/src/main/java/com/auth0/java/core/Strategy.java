package com.auth0.java.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Class with Auth0 authentication strategy info
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Strategy {

    private String name;
    private List<Connection> connections;
    private Strategies strategyMetadata;

    @JsonCreator
    public Strategy(@JsonProperty(value = "name", required = true) String name,
                    @JsonProperty(value = "connections", required = true) List<Connection> connections) {
        this.name = name;
        this.connections = connections;
        this.strategyMetadata = Strategies.fromName(name);
    }

    public String getName() {
        return name;
    }

    public List<Connection> getConnections() {
        return connections;
    }

    public Strategies.Type getType() {
        return this.strategyMetadata.getType();
    }

    public boolean isResourceOwnerEnabled() {
        return Strategies.ActiveDirectory.getName().equals(name)
                || Strategies.ADFS.getName().equals(name)
                || Strategies.Waad.getName().equals(name);
    }
}

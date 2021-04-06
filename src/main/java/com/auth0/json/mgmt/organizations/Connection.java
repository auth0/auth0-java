package com.auth0.json.mgmt.organizations;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the Connection object of an {@linkplain EnabledConnection}.
 * @see com.auth0.client.mgmt.OrganizationsEntity
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Connection {

    @JsonProperty("name")
    private String  name;
    @JsonProperty("strategy")
    private String strategy;

    /**
     * @return the name of the connection.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the name of the connection.
     *
     * @param name the name of the connection.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the strategy of the connection
     */
    public String getStrategy() {
        return this.strategy;
    }

    /**
     * Sets the strategy of this connection.
     *
     * @param strategy the strategy of the connection.
     */
    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }
}

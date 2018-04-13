package com.auth0.json.mgmt;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

/**
 * Class that represents an Auth0 Connection object. Related to the {@link com.auth0.client.mgmt.ConnectionsEntity} entity.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Connection {

    @JsonProperty("name")
    private String name;
    @JsonProperty("options")
    private Map<String, Object> options;
    @JsonProperty("id")
    private String id;
    @JsonProperty("strategy")
    private String strategy;
    @JsonProperty("enabled_clients")
    private List<String> enabledClients = null;
    @JsonProperty("provisioning_ticket_url")
    private String provisioningTicketUrl;

    public Connection() {
    }

    @JsonCreator
    public Connection(@JsonProperty("name") String name, @JsonProperty("strategy") String strategy) {
        this.name = name;
        this.strategy = strategy;
    }

    /**
     * Getter for the name of the connection.
     *
     * @return the name.
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * Getter for the connection options.
     *
     * @return the connection options.
     */
    @JsonProperty("options")
    public Map<String, Object> getOptions() {
        return options;
    }

    /**
     * Getter for the connection options.
     *
     * @param options the connection options.
     */
    @JsonProperty("options")
    public void setOptions(Map<String, Object> options) {
        this.options = options;
    }

    /**
     * Getter for the id of this connection.
     *
     * @return the id.
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * Getter for the type of the connection, related to the identity provider.
     *
     * @return the strategy.
     */
    @JsonProperty("strategy")
    public String getStrategy() {
        return strategy;
    }

    /**
     * Getter for the list of applications this connection is enabled for.
     *
     * @return the list of enabled applications.
     */
    @JsonProperty("enabled_clients")
    public List<String> getEnabledClients() {
        return enabledClients;
    }

    /**
     * Setter for the list of applications this connection is enabled for.
     *
     * @param enabledClients the list of enabled applications to set.
     */
    @JsonProperty("enabled_clients")
    public void setEnabledClients(List<String> enabledClients) {
        this.enabledClients = enabledClients;
    }
    
    /**
     * Getter for the ad/ldap connection's ticket url.
     *
     * @return the provisioning ticket url.
     */
    @JsonProperty("provisioning_ticket_url")
    public String getProvisioningTicketUrl() {
        return provisioningTicketUrl;
    }

}

package com.auth0.json.mgmt.connections;

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
    @JsonProperty("display_name")
    private String displayName;
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
    @JsonProperty("metadata")
    private Map<String, String> metadata;
    @JsonProperty("realms")
    private List<String> realms;
    @JsonProperty("show_as_button")
    private boolean showAsButton;
    @JsonProperty("is_domain_connection")
    private boolean isDomainConnection;

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
     * Setter for the connection options.
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
     * Getter for the connection display name, used in the new universal login experience.
     *
     * @return the connection display name.
     */
    @JsonProperty("display_name")
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Setter for the connection display name, used in the new universal login experience.
     *
     * @param displayName the connection display name to set.
     */
    @JsonProperty("display_name")
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
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

    /**
     * Getter for the metadata of this connection.
     *
     * @return the map of metadata key-values.
     */
    @JsonProperty("metadata")
    public Map<String, String> getMetadata() {
        return metadata;
    }

    /**
     * Setter for the metadata of this connection.
     *
     * @param metadata the map of metadata key-values.
     */
    @JsonProperty("metadata")
    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    /**
     * Getter for the realms of this connection.
     *
     * @return the list of realms.
     */
    @JsonProperty("realms")
    public List<String> getRealms() {
        return realms;
    }

    /**
     * Setter for the realms of this connection.
     *
     * @param realms the list of realms.
     */
    @JsonProperty("realms")
    public void setRealms(List<String> realms) {
        this.realms = realms;
    }

    /**
     * Getter for the show as button flag.
     *
     * @return the show as button flag.
     */
    public boolean isShowAsButton() {
        return showAsButton;
    }

    /**
     * Setter for the show as button flag.
     *
     * @param showAsButton the show as button flag to set.
     */
    public void setShowAsButton(boolean showAsButton) {
        this.showAsButton = showAsButton;
    }

    /**
     * Getter for the domain connection flag.
     * @return the domain connection flag.
     */
    public boolean isDomainConnection() {
        return isDomainConnection;
    }

    /**
     * Setter for the domain connection flag.
     * @param domainConnection the domain connection flag to set.
     */
    public void setDomainConnection(boolean domainConnection) {
        isDomainConnection = domainConnection;
    }
}

package com.auth0.json.mgmt.selfserviceprofiles;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SsoAccessTicketRequest {
    @JsonProperty("connection_id")
    private String connectionId;
    @JsonProperty("connection_config")
    private Map<String, Object> connectionConfig;
    @JsonProperty("enabled_clients")
    private List<String> enabledClients;
    @JsonProperty("enabled_organizations")
    private List<EnabledOrganizations> enabledOrganizations;
    @JsonProperty("ttl_sec")
    private int ttlSec;
    @JsonProperty("domain_aliases_config")
    private DomainAliasesConfig domainAliasesConfig;
    @JsonProperty("provisioning_config")
    private ProvisioningConfig provisioningConfig;

    /**
     * Creates a new instance.
     * @return the new instance.
     */
    public String getConnectionId() {
        return connectionId;
    }

    /**
     * Sets the connection ID.
     * @param connectionId the connection ID to set.
     */
    public void setConnectionId(String connectionId) {
        this.connectionId = connectionId;
    }

    /**
     * Getter for the connection configuration.
     * @return the connection configuration.
     */
    public Map<String, Object> getConnectionConfig() {
        return connectionConfig;
    }

    /**
     * Setter for the connection configuration.
     * @param connectionConfig the connection configuration to set.
     */
    public void setConnectionConfig(Map<String, Object> connectionConfig) {
        this.connectionConfig = connectionConfig;
    }

    /**
     * Getter for the enabled clients.
     * @return the enabled clients.
     */
    public List<String> getEnabledClients() {
        return enabledClients;
    }

    /**
     * Setter for the enabled clients.
     * @param enabledClients the enabled clients to set.
     */
    public void setEnabledClients(List<String> enabledClients) {
        this.enabledClients = enabledClients;
    }

    /**
     * Getter for the enabled organizations.
     * @return the enabled organizations.
     */
    public List<EnabledOrganizations> getEnabledOrganizations() {
        return enabledOrganizations;
    }

    /**
     * Setter for the enabled organizations.
     * @param enabledOrganizations the enabled organizations to set.
     */
    public void setEnabledOrganizations(List<EnabledOrganizations> enabledOrganizations) {
        this.enabledOrganizations = enabledOrganizations;
    }

    /**
     * Getter for the TTL in seconds.
     * @return the TTL in seconds.
     */
    public int getTtlSec() {
        return ttlSec;
    }

    /**
     * Setter for the TTL in seconds.
     * @param ttlSec the TTL in seconds to set.
     */
    public void setTtlSec(int ttlSec) {
        this.ttlSec = ttlSec;
    }

    /**
     * Getter for the domain aliases configuration.
     * @return the domain aliases configuration.
     */
    public DomainAliasesConfig getDomainAliasesConfig() {
        return domainAliasesConfig;
    }

    /**
     * Setter for the domain aliases configuration.
     * @param domainAliasesConfig the domain aliases configuration to set.
     */
    public void setDomainAliasesConfig(DomainAliasesConfig domainAliasesConfig) {
        this.domainAliasesConfig = domainAliasesConfig;
    }

    /**
     * Getter for the provisioning configuration.
     * @return the provisioning configuration.
     */
    public ProvisioningConfig getProvisioningConfig() {
        return provisioningConfig;
    }

    /**
     * Setter for the provisioning configuration.
     * @param provisioningConfig the provisioning configuration to set.
     */
    public void setProvisioningConfig(ProvisioningConfig provisioningConfig) {
        this.provisioningConfig = provisioningConfig;
    }
}

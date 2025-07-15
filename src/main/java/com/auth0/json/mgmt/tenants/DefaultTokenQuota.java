package com.auth0.json.mgmt.tenants;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DefaultTokenQuota {
    @JsonProperty("clients")
    private Clients clients;

    @JsonProperty("organizations")
    private Organizations organizations;

    /**
     * Default constructor for DefaultTokenQuota.
     */
    public DefaultTokenQuota() {}

    /**
     * Constructor for DefaultTokenQuota.
     *
     * @param clients the clients
     * @param organizations the organizations
     */
    public DefaultTokenQuota(Clients clients, Organizations organizations) {
        this.clients = clients;
        this.organizations = organizations;
    }

    /**
     * @return the clients
     */
    public Clients getClients() {
        return clients;
    }

    /**
     * @param clients the clients to set
     */
    public void setClients(Clients clients) {
        this.clients = clients;
    }

    /**
     * @return the organizations
     */
    public Organizations getOrganizations() {
        return organizations;
    }

    /**
     * @param organizations the organizations to set
     */
    public void setOrganizations(Organizations organizations) {
        this.organizations = organizations;
    }
}

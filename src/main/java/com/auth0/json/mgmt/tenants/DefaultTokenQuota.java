package com.auth0.json.mgmt.tenants;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DefaultTokenQuota {
    @JsonProperty("clients")
    private Clients client;

    @JsonProperty("organizations")
    private Organizations organizations;

    /**
     * Default constructor for DefaultTokenQuota.
     */
    public DefaultTokenQuota() {}

    /**
     * Constructor for DefaultTokenQuota.
     *
     * @param client the clients
     * @param organizations the organizations
     */
    public DefaultTokenQuota(Clients client, Organizations organizations) {
        this.client = client;
        this.organizations = organizations;
    }

    /**
     * @return the clients
     */
    public Clients getClient() {
        return client;
    }

    /**
     * @param client the clients to set
     */
    public void setClient(Clients client) {
        this.client = client;
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

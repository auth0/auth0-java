package com.auth0.json.mgmt.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientDefaultOrganization {
    @JsonProperty("flows")
    private List<String> flows;
    @JsonProperty("organization_id")
    private String organizationId;

    public ClientDefaultOrganization() {

    }

    public ClientDefaultOrganization(List<String> flows, String organizationId) {
        this.flows = flows;
        this.organizationId = organizationId;
    }

    /**
     * Getter for the supported flows.
     * @return the supported flows.
     */
    public List<String> getFlows() {
        return flows;
    }

    /**
     * Setter for the supported flows.
     * @param flows the supported flows to set.
     */
    public void setFlows(List<String> flows) {
        this.flows = flows;
    }

    /**
     * Getter for the organization_id.
     * @return the organization_id.
     */
    public String getOrganizationId() {
        return organizationId;
    }

    /**
     * Setter for the organization_id.
     * @param organizationId the organization_id to set.
     */
    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }
}

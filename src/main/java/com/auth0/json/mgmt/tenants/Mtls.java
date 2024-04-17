package com.auth0.json.mgmt.tenants;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the value of the {@code enable_endpoint_aliases} field of the {@link Tenant}.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Mtls {

    @JsonProperty("enable_endpoint_aliases")
    private Boolean enableEndpointAliases;

    /**
     * @return the value of the {@code enable_endpoint_aliases} field
     */
    public Boolean getEnableEndpointAliases() {
        return enableEndpointAliases;
    }

    /**
     * Sets the value of the {@code enable_endpoint_aliases} field
     *
     * @param enableEndpointAliases the value of the {@code enable_endpoint_aliases} field
     */
    public void setEnableEndpointAliases(Boolean enableEndpointAliases) {
        this.enableEndpointAliases = enableEndpointAliases;
    }

}

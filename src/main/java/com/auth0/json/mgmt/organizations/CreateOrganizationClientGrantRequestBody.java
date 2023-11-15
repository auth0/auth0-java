package com.auth0.json.mgmt.organizations;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the body of the request to send when associating a client grant with an organization.
 * @see com.auth0.client.mgmt.OrganizationsEntity#addClientGrant(String, CreateOrganizationClientGrantRequestBody)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateOrganizationClientGrantRequestBody {

    @JsonProperty("grant_id")
    private String grantId;

    /**
     * Create a new instance.
     * @param grantId the ID of the grant.
     */
    @JsonCreator
    public CreateOrganizationClientGrantRequestBody(String grantId) {
        this.grantId = grantId;
    }

}

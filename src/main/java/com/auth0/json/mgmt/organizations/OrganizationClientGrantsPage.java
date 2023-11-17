package com.auth0.json.mgmt.organizations;

import com.auth0.json.mgmt.Page;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

/**
 * Represents a page of a response when getting the client grants associated with an organization.
 * @see OrganizationClientGrant
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(using = OrganizationClientGrantsPageDeserializer.class)
public class OrganizationClientGrantsPage extends Page<OrganizationClientGrant> {

    public OrganizationClientGrantsPage(List<OrganizationClientGrant> items) {
        super(items);
    }

    public OrganizationClientGrantsPage(Integer start, Integer length, Integer total, Integer limit, List<OrganizationClientGrant> items) {
        super(start, length, total, limit, items);
    }

}

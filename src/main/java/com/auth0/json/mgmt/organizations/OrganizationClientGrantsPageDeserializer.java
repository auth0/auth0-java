package com.auth0.json.mgmt.organizations;

import com.auth0.json.mgmt.PageDeserializer;

import java.util.List;

/**
 * Parses a paged response into its {@linkplain OrganizationClientGrant} representation.
 */
public class OrganizationClientGrantsPageDeserializer extends PageDeserializer<OrganizationClientGrantsPage, OrganizationClientGrant> {

    OrganizationClientGrantsPageDeserializer() {
        super(OrganizationClientGrant.class, "client_grants");
    }

    @Override
    protected OrganizationClientGrantsPage createPage(List<OrganizationClientGrant> items) {
        return new OrganizationClientGrantsPage(items);
    }

    @Override
    protected OrganizationClientGrantsPage createPage(Integer start, Integer length, Integer total, Integer limit, List<OrganizationClientGrant> items) {
        return new OrganizationClientGrantsPage(start, length, total, limit, items);
    }
}

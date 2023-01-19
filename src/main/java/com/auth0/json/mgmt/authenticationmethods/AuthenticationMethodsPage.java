package com.auth0.json.mgmt.authenticationmethods;

import com.auth0.json.mgmt.Page;
import com.auth0.json.mgmt.organizations.Organization;
import com.auth0.json.mgmt.organizations.OrganizationsPageDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

/**
 * Represents a page of an Organization.
 * @see Organization
 * @see com.auth0.client.mgmt.OrganizationsEntity
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(using = OrganizationsPageDeserializer.class)
public class AuthenticationMethodsPage extends Page<AuthenticationMethod> {
    public AuthenticationMethodsPage(List<AuthenticationMethod> items) {
        super(items);
    }

    /**
     * @deprecated use {@linkplain com.auth0.json.mgmt.organizations.OrganizationsPage#OrganizationsPage(Integer, Integer, Integer, Integer, String, List)} instead.
     */
    @Deprecated
    public AuthenticationMethodsPage(Integer start, Integer length, Integer total, Integer limit, List<AuthenticationMethod> items) {
        super(start, length, total, limit, items);
    }

    public AuthenticationMethodsPage(Integer start, Integer length, Integer total, Integer limit, String next, List<AuthenticationMethod> items) {
        super(start, length, total, limit, next, items);
    }
}


package com.auth0.client.mgmt;

import com.auth0.client.mgmt.filter.ClientGrantsFilter;
import com.auth0.client.mgmt.filter.PageFilter;
import com.auth0.json.mgmt.clientgrants.ClientGrant;
import com.auth0.json.mgmt.clientgrants.ClientGrantsPage;
import com.auth0.json.mgmt.organizations.OrganizationsPage;
import com.auth0.net.BaseRequest;
import com.auth0.net.Request;
import com.auth0.net.VoidRequest;
import com.auth0.net.client.Auth0HttpClient;
import com.auth0.net.client.HttpMethod;
import com.auth0.utils.Asserts;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.HttpUrl;

import java.util.List;
import java.util.Map;

/**
 * Class that provides an implementation of the Client Grants methods of the Management API as defined in https://auth0.com/docs/api/management/v2#!/Client_Grants
 * <p>
 * This class is not thread-safe.
 *
 * @see ManagementAPI
 */
@SuppressWarnings("WeakerAccess")
public class ClientGrantsEntity extends BaseManagementEntity {

    ClientGrantsEntity(Auth0HttpClient client, HttpUrl baseUrl, TokenProvider tokenProvider) {
        super(client, baseUrl, tokenProvider);
    }

    /**
     * Request all the Client Grants. A token with scope read:client_grants is needed.
     * See https://auth0.com/docs/api/management/v2#!/Client_Grants/get_client_grants
     *
     * @param filter the filter to use. Can be null
     * @return a Request to execute.
     */
    public Request<ClientGrantsPage> list(ClientGrantsFilter filter) {
        HttpUrl.Builder builder = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/client-grants");
        if (filter != null) {
            for (Map.Entry<String, Object> e : filter.getAsMap().entrySet()) {
                builder.addQueryParameter(e.getKey(), String.valueOf(e.getValue()));
            }
        }

        String url = builder.build().toString();
        return new BaseRequest<>(client, tokenProvider, url, HttpMethod.GET, new TypeReference<ClientGrantsPage>() {
        });
    }

    /**
     * Create a Client Grant. A token with scope create:client_grants is needed.
     * See https://auth0.com/docs/api/management/v2#!/Client_Grants/post_client_grants
     *
     * @param clientId the application's client id to associate this grant with.
     * @param audience the audience of the grant.
     * @param scope    the scope to grant.
     * @return a Request to execute.
     */
    public Request<ClientGrant> create(String clientId, String audience, String[] scope) {
        return create(clientId, audience, scope, null, null);
    }

    /**
     * Create a Client Grant. A token with scope create:client_grants is needed.
     * See https://auth0.com/docs/api/management/v2#!/Client_Grants/post_client_grants
     *
     * @param clientId the application's client id to associate this grant with.
     * @param audience the audience of the grant.
     * @param scope    the scope to grant.
     * @param orgUsage      Defines whether organizations can be used with client credentials exchanges for this grant. (defaults to deny when not defined)
     * @param allowAnyOrg   If true, any organization can be used with this grant. If disabled (default), the grant must be explicitly assigned to the desired organizations.
     * @return a Request to execute.
     */
    public Request<ClientGrant> create(String clientId, String audience, String[] scope, String orgUsage, Boolean allowAnyOrg) {
        Asserts.assertNotNull(clientId, "client id");
        Asserts.assertNotNull(audience, "audience");
        Asserts.assertNotNull(scope, "scope");

        String url = baseUrl
            .newBuilder()
            .addPathSegments("api/v2/client-grants")
            .build()
            .toString();
        BaseRequest<ClientGrant> request =  new BaseRequest<>(client, tokenProvider, url, HttpMethod.POST, new TypeReference<ClientGrant>() {
        });
        request.addParameter("client_id", clientId);
        request.addParameter("audience", audience);
        request.addParameter("scope", scope);
        if (orgUsage != null && !orgUsage.trim().isEmpty()) {
            request.addParameter("organization_usage", orgUsage);
        }
        if (allowAnyOrg != null) {
            request.addParameter("allow_any_organization", allowAnyOrg);
        }
        return request;
    }

    /**
     * Delete an existing Client Grant. A token with scope delete:client_grants is needed.
     * See https://auth0.com/docs/api/management/v2#!/Client_Grants/delete_client_grants_by_id
     *
     * @param clientGrantId the client grant id.
     * @return a Request to execute.
     */
    public Request<Void> delete(String clientGrantId) {
        Asserts.assertNotNull(clientGrantId, "client grant id");

        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/client-grants")
                .addPathSegment(clientGrantId)
                .build()
                .toString();
        return new VoidRequest(client, tokenProvider,  url, HttpMethod.DELETE);
    }

    /**
     * Update an existing Client Grant. A token with scope update:client_grants is needed.
     * See https://auth0.com/docs/api/management/v2#!/Client_Grants/patch_client_grants_by_id
     *
     * @param clientGrantId the client grant id.
     * @param scope         the scope to grant.
     * @return a Request to execute.
     */
    public Request<ClientGrant> update(String clientGrantId, String[] scope) {
        return update(clientGrantId, scope, null, null);
    }

    /**
     * Update an existing Client Grant. A token with scope update:client_grants is needed.
     * See https://auth0.com/docs/api/management/v2#!/Client_Grants/patch_client_grants_by_id
     *
     * @param clientGrantId the client grant id.
     * @param scope         the scope to grant.
     * @param orgUsage      Defines whether organizations can be used with client credentials exchanges for this grant. (defaults to deny when not defined)
     * @param allowAnyOrg   If true, any organization can be used with this grant. If disabled (default), the grant must be explicitly assigned to the desired organizations.
     * @return a Request to execute.
     */
    public Request<ClientGrant> update(String clientGrantId, String[] scope, String orgUsage, Boolean allowAnyOrg) {
        Asserts.assertNotNull(clientGrantId, "client grant id");
        Asserts.assertNotNull(scope, "scope");

        String url = baseUrl
            .newBuilder()
            .addPathSegments("api/v2/client-grants")
            .addPathSegment(clientGrantId)
            .build()
            .toString();
        BaseRequest<ClientGrant> request =  new BaseRequest<>(client, tokenProvider, url, HttpMethod.PATCH, new TypeReference<ClientGrant>() {
        });
        request.addParameter("scope", scope);
        if (orgUsage != null && !orgUsage.trim().isEmpty()) {
            request.addParameter("organization_usage", orgUsage);
        }
        if (allowAnyOrg != null) {
            request.addParameter("allow_any_organization", allowAnyOrg);
        }
        return request;
    }

    /**
     * Returns the organizations associated with this client grant. A token with scope {@code read:organization_client_grants} is required.
     * @param clientGrantId the client grant ID.
     * @param filter an optional filter to limit results.
     * @return a request to execute.
     */
    public Request<OrganizationsPage> listOrganizations(String clientGrantId, PageFilter filter) {
        Asserts.assertNotNull(clientGrantId, "client grant ID");
        HttpUrl.Builder builder = baseUrl
            .newBuilder()
            .addPathSegments("api/v2/client-grants")
            .addPathSegment(clientGrantId)
            .addPathSegment("organizations");

        if (filter != null) {
            for (Map.Entry<String, Object> e : filter.getAsMap().entrySet()) {
                builder.addQueryParameter(e.getKey(), String.valueOf(e.getValue()));
            }
        }

        String url = builder.build().toString();
        return new BaseRequest<>(client, tokenProvider, url, HttpMethod.GET, new TypeReference<OrganizationsPage>() {
        });
    }
}

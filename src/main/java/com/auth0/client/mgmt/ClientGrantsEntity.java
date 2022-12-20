package com.auth0.client.mgmt;

import com.auth0.client.mgmt.filter.ClientGrantsFilter;
import com.auth0.json.mgmt.ClientGrant;
import com.auth0.json.mgmt.ClientGrantsPage;
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
        return new BaseRequest<>(client, tokenProvider, url,  HttpMethod.GET, new TypeReference<ClientGrantsPage>() {
        });
    }

    /**
     * Request all the Client Grants. A token with scope read:client_grants is needed.
     * See https://auth0.com/docs/api/management/v2#!/Client_Grants/get_client_grants
     *
     * @return a Request to execute.
     * @deprecated Calling this method will soon stop returning the complete list of client grants and instead, limit to the first page of results.
     * Please use {@link #list(ClientGrantsFilter)} instead as it provides pagination support.
     */
    @Deprecated
    public Request<List<ClientGrant>> list() {
        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/client-grants")
                .build()
                .toString();
        return new BaseRequest<>(client, tokenProvider, url,  HttpMethod.GET, new TypeReference<List<ClientGrant>>() {
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
        Asserts.assertNotNull(clientId, "client id");
        Asserts.assertNotNull(audience, "audience");
        Asserts.assertNotNull(scope, "scope");

        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/client-grants")
                .build()
                .toString();
        BaseRequest<ClientGrant> request =  new BaseRequest<>(client, tokenProvider, url,  HttpMethod.POST, new TypeReference<ClientGrant>() {
        });
        request.addParameter("client_id", clientId);
        request.addParameter("audience", audience);
        request.addParameter("scope", scope);
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
        Asserts.assertNotNull(clientGrantId, "client grant id");
        Asserts.assertNotNull(scope, "scope");

        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/client-grants")
                .addPathSegment(clientGrantId)
                .build()
                .toString();
        BaseRequest<ClientGrant> request =  new BaseRequest<>(client, tokenProvider, url,  HttpMethod.PATCH, new TypeReference<ClientGrant>() {
        });
        request.addParameter("scope", scope);
        return request;
    }
}

package com.auth0.client.mgmt;

import com.auth0.json.mgmt.ClientGrant;
import com.auth0.net.CustomRequest;
import com.auth0.net.Request;
import com.auth0.net.VoidRequest;
import com.auth0.utils.Asserts;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

import java.util.List;

/**
 * Class that provides an implementation of the Client Grants methods of the Management API as defined in https://auth0.com/docs/api/management/v2#!/Client_Grants
 */
@SuppressWarnings("WeakerAccess")
public class ClientGrantsEntity extends BaseManagementEntity {

    ClientGrantsEntity(OkHttpClient client, String baseUrl, String apiToken) {
        super(client, baseUrl, apiToken);
    }

    /**
     * Request all the Client Grants. A token with scope read:client_grants is needed.
     * See https://auth0.com/docs/api/management/v2#!/Client_Grants/get_client_grants
     *
     * @return a Request to execute.
     */
    public Request<List<ClientGrant>> list() {
        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/client-grants")
                .build()
                .toString();
        CustomRequest<List<ClientGrant>> request = new CustomRequest<>(client, url, "GET", new TypeReference<List<ClientGrant>>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
    }

    /**
     * Create a Client Grant. A token with scope create:client_grants is needed.
     * See https://auth0.com/docs/api/management/v2#!/Client_Grants/post_client_grants
     *
     * @param clientId the client id to associate this grant with.
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
        CustomRequest<ClientGrant> request = new CustomRequest<>(client, url, "POST", new TypeReference<ClientGrant>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
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
    public Request delete(String clientGrantId) {
        Asserts.assertNotNull(clientGrantId, "client grant id");

        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/client-grants")
                .addPathSegment(clientGrantId)
                .build()
                .toString();
        VoidRequest request = new VoidRequest(client, url, "DELETE");
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
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
        CustomRequest<ClientGrant> request = new CustomRequest<>(client, url, "PATCH", new TypeReference<ClientGrant>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        request.addParameter("scope", scope);
        return request;
    }
}

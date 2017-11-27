package com.auth0.client.mgmt;

import java.util.Arrays;
import java.util.List;

import com.auth0.client.mgmt.builder.RequestBuilder;
import com.auth0.json.mgmt.ClientGrant;
import com.auth0.net.Request;
import com.auth0.utils.Asserts;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

/**
 * Class that provides an implementation of the Client Grants methods of the Management API as defined in https://auth0.com/docs/api/management/v2#!/Client_Grants
 */
@SuppressWarnings("WeakerAccess")
public class ClientGrantsEntity {
    private final RequestBuilder requestBuilder;
    ClientGrantsEntity(OkHttpClient client, HttpUrl baseUrl, String apiToken) {
        requestBuilder = new RequestBuilder(client, baseUrl, apiToken);
    }

    /**
     * Request all the Client Grants. A token with scope read:client_grants is needed.
     * See https://auth0.com/docs/api/management/v2#!/Client_Grants/get_client_grants
     *
     * @return a Request to execute.
     */
    public Request<List<ClientGrant>> list() {
        return requestBuilder.get("api/v2/client-grants")
                             .request(new TypeReference<List<ClientGrant>>() {
                             });
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

        return requestBuilder.post("api/v2/client-grants")
                             .body(new ClientGrant(clientId, audience, Arrays.asList(scope)))
                             .request(new TypeReference<ClientGrant>() {
                             });
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

        return requestBuilder.delete("api/v2/client-grants", clientGrantId)
                             .request();
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

        return requestBuilder.patch("api/v2/client-grants", clientGrantId)
                             .body(new ClientGrant(null, null, Arrays.asList(scope)))
                             .request(new TypeReference<ClientGrant>() {
                             });
    }
}

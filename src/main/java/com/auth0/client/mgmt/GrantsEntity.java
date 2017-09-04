package com.auth0.client.mgmt;

import com.auth0.net.CustomRequest;
import com.auth0.net.Request;
import com.auth0.net.VoidRequest;
import com.auth0.utils.Asserts;
import com.fasterxml.jackson.core.type.TypeReference;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

import java.util.List;

import com.auth0.json.mgmt.Grant;

/**
 * Class that provides an implementation of the Grants methods of the Management API as defined in https://auth0.com/docs/api/management/v2#!/Grants/
 */
@SuppressWarnings("WeakerAccess")
public class GrantsEntity extends BaseManagementEntity {

    GrantsEntity(OkHttpClient client, HttpUrl baseUrl, String apiToken) {
        super(client, baseUrl, apiToken);
    }

    /**
     * Request all Grants. A token with scope read:client_grants is needed.
     * See https://auth0.com/docs/api/management/v2#!/Grants/get_grants
     *
     * @param userId The user id of the grants to retrieve 
     * @return a Request to execute.
     */
    public Request<List<Grant>> list(String userId) {
        Asserts.assertNotNull(userId, "user id");
        
        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/grants")
                .addQueryParameter("user_id", userId)
                .build()
                .toString();
        CustomRequest<List<Grant>> request = new CustomRequest<>(client, url, "GET", new TypeReference<List<Grant>>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
    }

    /**
     * Delete an existing Grant. A token with scope delete:client_grants is needed.
     * See https://auth0.com/docs/api/management/v2#!/Grants/delete_grants_by_id<br>
     *
     * @param grantId The id of the grant to delete.
     * @return a Request to execute.
     */
    public Request delete(String grantId) {
        Asserts.assertNotNull(grantId, "grant id");
        
        final String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/grants")
                .addPathSegment(grantId)
                .build()
                .toString();
        VoidRequest request = new VoidRequest(client, url, "DELETE");
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
    }
    
    /**
     * Deletes all Grants of a given user. A token with scope delete:client_grants is needed.
     * See https://auth0.com/docs/api/management/v2#!/Grants/delete_grants_by_id<br>
     *
     * @param userId The id of the user whose grants are deleted.
     * @return a Request to execute.
     */
    public Request deleteAll(String userId) {
        Asserts.assertNotNull(userId, "user id");
        
        final String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/grants")
                .addQueryParameter("user_id", userId)
                .build()
                .toString();
        VoidRequest request = new VoidRequest(client, url, "DELETE");
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
    }

}

package com.auth0.client.mgmt;

import java.util.List;

import com.auth0.client.mgmt.builder.RequestBuilder;
import com.auth0.json.mgmt.Grant;
import com.auth0.net.Request;
import com.auth0.utils.Asserts;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

/**
 * Class that provides an implementation of the Grants methods of the Management API as defined in https://auth0.com/docs/api/management/v2#!/Grants/
 */
@SuppressWarnings("WeakerAccess")
public class GrantsEntity {
    private final RequestBuilder requestBuilder;
    GrantsEntity(OkHttpClient client, HttpUrl baseUrl, String apiToken) {
        requestBuilder = new RequestBuilder(client, baseUrl, apiToken);
    }

    /**
     * Request all Grants. A token with scope read:grants is needed
     * See https://auth0.com/docs/api/management/v2#!/Grants/get_grants
     *
     * @param userId The user id of the grants to retrieve 
     * @return a Request to execute.
     */
    public Request<List<Grant>> list(String userId) {
        Asserts.assertNotNull(userId, "user id");
        return requestBuilder.get("api/v2/grants")
                             .queryParameter("user_id", userId)
                             .request(new TypeReference<List<Grant>>() {
                             });
    }

    /**
     * Delete an existing Grant. A token with scope delete:grants is needed.
     * See https://auth0.com/docs/api/management/v2#!/Grants/delete_grants_by_id<br>
     *
     * @param grantId The id of the grant to delete.
     * @return a Request to execute.
     */
    public Request delete(String grantId) {
        Asserts.assertNotNull(grantId, "grant id");

        return requestBuilder.delete("api/v2/grants", grantId)
                             .request();
    }
    
    /**
     * Deletes all Grants of a given user. A token with scope delete:grants is needed.
     * See https://auth0.com/docs/api/management/v2#!/Grants/delete_grants_by_id<br>
     *
     * @param userId The id of the user whose grants are deleted.
     * @return a Request to execute.
     */
    public Request deleteAll(String userId) {
        Asserts.assertNotNull(userId, "user id");

        return requestBuilder.delete("api/v2/grants")
                             .queryParameter("user_id", userId)
                             .request();
    }

}

package com.auth0.client.mgmt;

import com.auth0.client.mgmt.filter.GrantsFilter;
import com.auth0.json.mgmt.Grant;
import com.auth0.json.mgmt.GrantsPage;
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
 * Class that provides an implementation of the Grants methods of the Management API as defined in https://auth0.com/docs/api/management/v2#!/Grants/
 * <p>
 * This class is not thread-safe.
 *
 * @see ManagementAPI
 */
@SuppressWarnings("WeakerAccess")
public class GrantsEntity extends BaseManagementEntity {

    GrantsEntity(Auth0HttpClient client, HttpUrl baseUrl, TokenProvider tokenProvider) {
        super(client, baseUrl, tokenProvider);
    }

    /**
     * Request all Grants. A token with scope read:grants is needed
     * See https://auth0.com/docs/api/management/v2#!/Grants/get_grants
     *
     * @param userId The user id of the grants to retrieve
     * @param filter the filter to use. Can be null.
     * @return a Request to execute.
     */
    public Request<GrantsPage> list(String userId, GrantsFilter filter) {
        Asserts.assertNotNull(userId, "user id");

        HttpUrl.Builder builder = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/grants")
                .addQueryParameter("user_id", userId);
        if (filter != null) {
            for (Map.Entry<String, Object> e : filter.getAsMap().entrySet()) {
                builder.addQueryParameter(e.getKey(), String.valueOf(e.getValue()));
            }
        }

        String url = builder.build().toString();
        BaseRequest<GrantsPage> request =  new BaseRequest<>(client, tokenProvider, url,  HttpMethod.GET, new TypeReference<GrantsPage>() {
        });
        return request;
    }

    /**
     * Request all Grants. A token with scope read:grants is needed
     * See https://auth0.com/docs/api/management/v2#!/Grants/get_grants
     *
     * @param userId The user id of the grants to retrieve
     * @return a Request to execute.
     * @deprecated Calling this method will soon stop returning the complete list of grants and instead, limit to the first page of results.
     * Please use {@link #list(String, GrantsFilter)} instead as it provides pagination support.
     */
    @Deprecated
    public Request<List<Grant>> list(String userId) {
        Asserts.assertNotNull(userId, "user id");

        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/grants")
                .addQueryParameter("user_id", userId)
                .build()
                .toString();
        BaseRequest<List<Grant>> request =  new BaseRequest<>(client, tokenProvider, url,  HttpMethod.GET, new TypeReference<List<Grant>>() {
        });
        return request;
    }

    /**
     * Delete an existing Grant. A token with scope delete:grants is needed.
     * See https://auth0.com/docs/api/management/v2#!/Grants/delete_grants_by_id<br>
     *
     * @param grantId The id of the grant to delete.
     * @return a Request to execute.
     */
    public Request<Void> delete(String grantId) {
        Asserts.assertNotNull(grantId, "grant id");

        final String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/grants")
                .addPathSegment(grantId)
                .build()
                .toString();
        VoidRequest request =  new VoidRequest(client, tokenProvider,  url, HttpMethod.DELETE);
        return request;
    }

    /**
     * Deletes all Grants of a given user. A token with scope delete:grants is needed.
     * See https://auth0.com/docs/api/management/v2#!/Grants/delete_grants_by_id<br>
     *
     * @param userId The id of the user whose grants are deleted.
     * @return a Request to execute.
     */
    public Request<Void> deleteAll(String userId) {
        Asserts.assertNotNull(userId, "user id");

        final String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/grants")
                .addQueryParameter("user_id", userId)
                .build()
                .toString();
        VoidRequest request =  new VoidRequest(client, tokenProvider,  url, HttpMethod.DELETE);
        return request;
    }

}

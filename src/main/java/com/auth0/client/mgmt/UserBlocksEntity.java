package com.auth0.client.mgmt;

import com.auth0.utils.Asserts;
import com.auth0.json.mgmt.userblocks.UserBlocks;
import com.auth0.net.CustomRequest;
import com.auth0.net.Request;
import com.auth0.net.VoidRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

public class UserBlocksEntity extends BaseManagementEntity {

    UserBlocksEntity(OkHttpClient client, String baseUrl, String apiToken) {
        super(client, baseUrl, apiToken);
    }

    /**
     * Request all the User Blocks for a given identifier. A token with scope read:users is needed.
     *
     * @param identifier the identifier. Either a username, phone_number, or email.
     * @return a Request to execute.
     */
    public Request<UserBlocks> getByIdentifier(String identifier) {
        Asserts.assertNotNull(identifier, "identifier");

        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("user-blocks")
                .addQueryParameter("identifier", identifier)
                .build()
                .toString();
        CustomRequest<UserBlocks> request = new CustomRequest<>(client, url, "GET", new TypeReference<UserBlocks>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
    }

    /**
     * Delete any existing User Blocks for a given identifier. A token with scope update:users is needed.
     *
     * @param identifier the identifier. Either a username, phone_number, or email.
     * @return a Request to execute.
     */
    public Request deleteByIdentifier(String identifier) {
        Asserts.assertNotNull(identifier, "identifier");

        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("user-blocks")
                .addQueryParameter("identifier", identifier)
                .build()
                .toString();
        VoidRequest request = new VoidRequest(client, url, "DELETE");
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
    }

    /**
     * Request all the User Blocks. A token with scope read:users is needed.
     *
     * @param userId the user id.
     * @return a Request to execute.
     */
    public Request<UserBlocks> get(String userId) {
        Asserts.assertNotNull(userId, "user id");

        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("user-blocks")
                .addPathSegment(userId)
                .build()
                .toString();
        CustomRequest<UserBlocks> request = new CustomRequest<>(client, url, "GET", new TypeReference<UserBlocks>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
    }

    /**
     * Delete any existing User Blocks. A token with scope update:users is needed.
     *
     * @param userId the user id.
     * @return a Request to execute.
     */
    public Request delete(String userId) {
        Asserts.assertNotNull(userId, "user id");

        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("user-blocks")
                .addPathSegment(userId)
                .build()
                .toString();
        VoidRequest request = new VoidRequest(client, url, "DELETE");
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
    }


}

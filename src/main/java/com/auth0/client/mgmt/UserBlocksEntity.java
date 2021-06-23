package com.auth0.client.mgmt;

import com.auth0.client.mgmt.tokens.TokenProvider;
import com.auth0.json.mgmt.userblocks.UserBlocks;
import com.auth0.net.CustomRequest;
import com.auth0.net.Request;
import com.auth0.net.VoidRequest;
import com.auth0.utils.Asserts;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

/**
 * Class that provides an implementation of the User Blocks methods of the Management API as defined in https://auth0.com/docs/api/management/v2#!/User_Blocks
 * <p>
 * This class is not thread-safe.
 *
 * @see ManagementAPI
 */
@SuppressWarnings("WeakerAccess")
public class UserBlocksEntity extends BaseManagementEntity {

    UserBlocksEntity(OkHttpClient client, HttpUrl baseUrl, TokenProvider provider) {
        super(client, baseUrl, provider);
    }

    /**
     * Request all the User Blocks for a given identifier. A token with scope read:users is needed.
     * See https://auth0.com/docs/api/management/v2#!/User_Blocks/get_user_blocks
     *
     * @param identifier the identifier. Either a username, phone_number, or email.
     * @return a Request to execute.
     */
    public Request<UserBlocks> getByIdentifier(String identifier) {
        Asserts.assertNotNull(identifier, "identifier");

        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/user-blocks")
                .addQueryParameter("identifier", identifier)
                .build()
                .toString();
        CustomRequest<UserBlocks> request = new CustomRequest<>(client, url, "GET", new TypeReference<UserBlocks>() {
        });
        request.addHeader("Authorization", "Bearer " + tokenProvider.getToken());
        return request;
    }

    /**
     * Delete any existing User Blocks for a given identifier. A token with scope update:users is needed.
     * See https://auth0.com/docs/api/management/v2#!/User_Blocks/delete_user_blocks
     *
     * @param identifier the identifier. Either a username, phone_number, or email.
     * @return a Request to execute.
     */
    public Request deleteByIdentifier(String identifier) {
        Asserts.assertNotNull(identifier, "identifier");

        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/user-blocks")
                .addQueryParameter("identifier", identifier)
                .build()
                .toString();
        VoidRequest request = new VoidRequest(client, url, "DELETE");
        request.addHeader("Authorization", "Bearer " + tokenProvider.getToken());
        return request;
    }

    /**
     * Request all the User Blocks. A token with scope read:users is needed.
     * See https://auth0.com/docs/api/management/v2#!/User_Blocks/get_user_blocks_by_id
     *
     * @param userId the user id.
     * @return a Request to execute.
     */
    public Request<UserBlocks> get(String userId) {
        Asserts.assertNotNull(userId, "user id");

        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/user-blocks")
                .addPathSegment(userId)
                .build()
                .toString();
        CustomRequest<UserBlocks> request = new CustomRequest<>(client, url, "GET", new TypeReference<UserBlocks>() {
        });
        request.addHeader("Authorization", "Bearer " + tokenProvider.getToken());
        return request;
    }

    /**
     * Delete any existing User Blocks. A token with scope update:users is needed.
     * See https://auth0.com/docs/api/management/v2#!/User_Blocks/delete_user_blocks_by_id
     *
     * @param userId the user id.
     * @return a Request to execute.
     */
    public Request delete(String userId) {
        Asserts.assertNotNull(userId, "user id");

        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/user-blocks")
                .addPathSegment(userId)
                .build()
                .toString();
        VoidRequest request = new VoidRequest(client, url, "DELETE");
        request.addHeader("Authorization", "Bearer " + tokenProvider.getToken());
        return request;
    }
}

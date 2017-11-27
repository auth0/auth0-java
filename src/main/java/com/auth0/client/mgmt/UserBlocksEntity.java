package com.auth0.client.mgmt;

import com.auth0.client.mgmt.builder.RequestBuilder;
import com.auth0.json.mgmt.userblocks.UserBlocks;
import com.auth0.net.Request;
import com.auth0.utils.Asserts;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

/**
 * Class that provides an implementation of the User Blocks methods of the Management API as defined in https://auth0.com/docs/api/management/v2#!/User_Blocks
 */
@SuppressWarnings("WeakerAccess")
public class UserBlocksEntity {
    private final RequestBuilder requestBuilder;

    UserBlocksEntity(OkHttpClient client, HttpUrl baseUrl, String apiToken) {
        requestBuilder = new RequestBuilder(client, baseUrl, apiToken);
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

        return requestBuilder.get("api/v2/user-blocks")
                             .queryParameter("identifier", identifier)
                             .request(new TypeReference<UserBlocks>() {
                             });
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

        return requestBuilder.delete("api/v2/user-blocks")
                             .queryParameter("identifier", identifier)
                             .request();
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

        return requestBuilder.get("api/v2/user-blocks", userId)
                             .request(new TypeReference<UserBlocks>() {
                             });
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

        return requestBuilder.delete("api/v2/user-blocks", userId)
                             .request();
    }
}

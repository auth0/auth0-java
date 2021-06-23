package com.auth0.client.mgmt;

import com.auth0.client.mgmt.tokens.TokenProvider;
import com.auth0.json.mgmt.Token;
import com.auth0.net.CustomRequest;
import com.auth0.net.Request;
import com.auth0.net.VoidRequest;
import com.auth0.utils.Asserts;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

import java.util.List;

/**
 * Class that provides an implementation of the Blacklists methods of the Management API as defined in https://auth0.com/docs/api/management/v2#!/Blacklists
 * <p>
 * This class is not thread-safe.
 *
 * @see ManagementAPI
 */
@SuppressWarnings("WeakerAccess")
public class BlacklistsEntity extends BaseManagementEntity {

    BlacklistsEntity(OkHttpClient client, HttpUrl baseUrl, TokenProvider tokenProvider) {
        super(client, baseUrl, tokenProvider);
    }

    /**
     * Request all the Blacklisted Tokens with a given audience. A token with scope blacklist:tokens is needed.
     * See https://auth0.com/docs/api/management/v2#!/Blacklists/get_tokens.
     *
     * @param audience the token audience (aud).
     * @return a Request to execute.
     */
    public Request<List<Token>> getBlacklist(String audience) {
        Asserts.assertNotNull(audience, "audience");

        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/blacklists/tokens")
                .addQueryParameter("aud", audience)
                .build()
                .toString();
        CustomRequest<List<Token>> request = new CustomRequest<>(client, url, "GET", new TypeReference<List<Token>>() {
        });
        request.addHeader("Authorization", "Bearer " + tokenProvider.getToken());
        return request;
    }

    /**
     * Add a Token to the Blacklist. A token with scope blacklist:tokens is needed.
     * See https://auth0.com/docs/api/management/v2#!/Blacklists/post_tokens.
     *
     * @param token the token to blacklist.
     * @return a Request to execute.
     */
    public Request blacklistToken(Token token) {
        Asserts.assertNotNull(token, "token");

        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/blacklists/tokens")
                .build()
                .toString();
        VoidRequest request = new VoidRequest(client, url, "POST");
        request.addHeader("Authorization", "Bearer " + tokenProvider.getToken());
        request.setBody(token);
        return request;
    }
}

package com.auth0.client.mgmt;

import com.auth0.json.mgmt.Token;
import com.auth0.net.BaseRequest;
import com.auth0.net.Request;
import com.auth0.net.VoidRequest;
import com.auth0.net.client.Auth0HttpClient;
import com.auth0.net.client.HttpMethod;
import com.auth0.utils.Asserts;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.HttpUrl;

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

    BlacklistsEntity(Auth0HttpClient client, HttpUrl baseUrl, TokenProvider tokenProvider) {
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
        return new BaseRequest<>(client, tokenProvider, url, HttpMethod.GET, new TypeReference<List<Token>>() {
        });
    }

    /**
     * Add a Token to the Blacklist. A token with scope blacklist:tokens is needed.
     * See https://auth0.com/docs/api/management/v2#!/Blacklists/post_tokens.
     *
     * @param token the token to blacklist.
     * @return a Request to execute.
     */
    public Request<Void> blacklistToken(Token token) {
        Asserts.assertNotNull(token, "token");

        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/blacklists/tokens")
                .build()
                .toString();
        VoidRequest request =  new VoidRequest(client, tokenProvider,  url, HttpMethod.POST);
        request.setBody(token);
        return request;
    }
}

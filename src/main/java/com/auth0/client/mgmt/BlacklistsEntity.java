package com.auth0.client.mgmt;

import java.util.List;

import com.auth0.client.mgmt.builder.RequestBuilder;
import com.auth0.json.mgmt.Token;
import com.auth0.net.Request;
import com.auth0.utils.Asserts;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

/**
 * Class that provides an implementation of the Blacklists methods of the Management API as defined in https://auth0.com/docs/api/management/v2#!/Blacklists
 */
@SuppressWarnings("WeakerAccess")
public class BlacklistsEntity {
    private final RequestBuilder requestBuilder;

    BlacklistsEntity(OkHttpClient client, HttpUrl baseUrl, String apiToken) {
        requestBuilder = new RequestBuilder(client, baseUrl, apiToken);
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

        return requestBuilder.get("api/v2/blacklists/tokens")
                             .queryParameter("aud", audience)
                             .request(new TypeReference<List<Token>>() {
                             });
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

        return requestBuilder.post("api/v2/blacklists/tokens")
                             .body(token)
                             .request();
    }
}

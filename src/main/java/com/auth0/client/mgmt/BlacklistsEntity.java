package com.auth0.client.mgmt;

import com.auth0.Asserts;
import com.auth0.json.mgmt.Token;
import com.auth0.net.CustomRequest;
import com.auth0.net.Request;
import com.auth0.net.VoidRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

import java.util.List;

public class BlacklistsEntity extends BaseManagementEntity {

    BlacklistsEntity(OkHttpClient client, String baseUrl, String apiToken) {
        super(client, baseUrl, apiToken);
    }

    /**
     * Request all the Blacklisted Tokens with a given audience. A token with scope blacklist:tokens is needed.
     *
     * @param audience the token audience (aud).
     * @return a Request to execute.
     */
    public Request<List<Token>> getBlacklist(String audience) {
        Asserts.assertNotNull(audience, "audience");

        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("blacklists")
                .addPathSegment("tokens")
                .addQueryParameter("aud", audience)
                .build()
                .toString();
        CustomRequest<List<Token>> request = new CustomRequest<>(client, url, "GET", new TypeReference<List<Token>>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
    }

    /**
     * Add a Token to the Blacklist. A token with scope blacklist:tokens is needed.
     *
     * @param token the token to blacklist.
     * @return a Request to execute.
     */
    public Request blacklistToken(Token token) {
        Asserts.assertNotNull(token, "token");

        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("blacklists")
                .addPathSegment("tokens")
                .build()
                .toString();
        VoidRequest request = new VoidRequest(client, url, "POST");
        request.addHeader("Authorization", "Bearer " + apiToken);
        request.setBody(token);
        return request;
    }


}

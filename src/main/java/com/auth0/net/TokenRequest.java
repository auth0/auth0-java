package com.auth0.net;

import com.auth0.json.auth.TokenHolder;
import com.auth0.net.client.Auth0HttpClient;
import com.auth0.net.client.HttpMethod;
import com.fasterxml.jackson.core.type.TypeReference;

public class TokenRequest extends CustomRequest<TokenHolder> implements AuthRequest {

    public TokenRequest(Auth0HttpClient client, String url) {
        super(client, null, url, HttpMethod.POST, new TypeReference<TokenHolder>() {
        });
    }

    @Override
    public TokenRequest setRealm(String realm) {
        super.addParameter("realm", realm);
        return this;
    }

    @Override
    public TokenRequest setAudience(String audience) {
        super.addParameter("audience", audience);
        return this;
    }

    @Override
    public TokenRequest setScope(String scope) {
        super.addParameter("scope", scope);
        return this;
    }
}

package com.auth0.net;

import com.auth0.json.auth.TokenHolder;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.OkHttpClient;

public class TokenRequest extends CustomRequest<TokenHolder> implements AuthRequest {

    public TokenRequest(OkHttpClient client, String url) {
        super(client, url, "POST", new TypeReference<TokenHolder>() {});
    }

    @Override
    public void setRealm(String realm) {
        super.addParameter("realm", realm);
    }

    @Override
    public void setAudience(String audience) {
        super.addParameter("audience", audience);
    }

    @Override
    public void setScope(String scope) {
        super.addParameter("scope", scope);
    }
}

package com.auth0.net;

import com.auth0.json.TokenHolder;
import okhttp3.OkHttpClient;

public class TokenRequest extends CustomRequest<TokenHolder> implements AuthRequest {

    public TokenRequest(OkHttpClient client, String url) {
        super(client, url, "POST", TokenHolder.class);
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

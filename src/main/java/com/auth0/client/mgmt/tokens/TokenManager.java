package com.auth0.client.mgmt.tokens;

import okhttp3.OkHttpClient;

// TODO maybe not needed?
public class TokenManager {

    private final OkHttpClient client;
    private final CachedTokenProvider tokenProvider;

    public TokenManager(OkHttpClient client, String domain, String clientId, String clientSecret, TokenCache tokenCache) {
        this.client = client;
        this.tokenProvider = new CachedTokenProvider(domain, clientId, clientSecret, tokenCache);
    }

}

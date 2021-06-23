package com.auth0.client.mgmt;

import com.auth0.client.mgmt.tokens.TokenProvider;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

abstract class BaseManagementEntity {
    protected final OkHttpClient client;
    protected final HttpUrl baseUrl;
    protected final TokenProvider tokenProvider;

    BaseManagementEntity(OkHttpClient client, HttpUrl baseUrl, TokenProvider tokenProvider) {
        this.client = client;
        this.baseUrl = baseUrl;
        this.tokenProvider = tokenProvider;
    }
}

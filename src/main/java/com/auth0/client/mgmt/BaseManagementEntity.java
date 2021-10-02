package com.auth0.client.mgmt;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

abstract class BaseManagementEntity {
    protected final OkHttpClient client;
    protected final HttpUrl baseUrl;
    protected final String apiToken;

    BaseManagementEntity(OkHttpClient client, HttpUrl baseUrl, String apiToken) {
        this.client = client;
        this.baseUrl = baseUrl;
        this.apiToken = apiToken;
    }
}

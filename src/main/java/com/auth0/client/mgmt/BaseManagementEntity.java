package com.auth0.client.mgmt;

import okhttp3.OkHttpClient;

abstract class BaseManagementEntity {
    protected final OkHttpClient client;
    protected final String baseUrl;
    protected final String apiToken;

    BaseManagementEntity(OkHttpClient client, String baseUrl, String apiToken) {
        this.client = client;
        this.baseUrl = baseUrl;
        this.apiToken = apiToken;
    }
}

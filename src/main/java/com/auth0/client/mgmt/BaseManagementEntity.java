package com.auth0.client.mgmt;

import com.auth0.net.Request;
import com.auth0.net.client.Auth0HttpClient;
import com.auth0.net.client.HttpMethod;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.HttpUrl;

import java.util.function.Consumer;

abstract class BaseManagementEntity {
    protected final Auth0HttpClient client;
    // TODO decouple from OkHttp!!
    protected final HttpUrl baseUrl;
    protected final String apiToken;

    BaseManagementEntity(Auth0HttpClient client, HttpUrl baseUrl, String apiToken) {
        this.client = client;
        this.baseUrl = baseUrl;
        this.apiToken = apiToken;
    }

    protected Request<Void> voidRequest(HttpMethod method, Consumer<RequestBuilder<Void>> customizer) {
        return customizeRequest(
            new RequestBuilder<>(client, method, baseUrl, new TypeReference<Void>() {
            }),
            customizer
        );
    }

    protected <T> Request<T> request(HttpMethod method, TypeReference<T> target, Consumer<RequestBuilder<T>> customizer) {
        return customizeRequest(
            new RequestBuilder<>(client, method, baseUrl, target),
            customizer
        );
    }

    private <T> Request<T> customizeRequest(RequestBuilder<T> builder, Consumer<RequestBuilder<T>> customizer) {
        builder.withHeader("Authorization", "Bearer " + apiToken);
        customizer.accept(builder);
        return builder.build();
    }
}

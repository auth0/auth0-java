package com.auth0.client.mgmt;

import com.auth0.net.Request;
import com.auth0.net.client.Auth0HttpClient;
import com.auth0.net.client.HttpMethod;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.HttpUrl;

import java.util.function.Consumer;

abstract class BaseManagementEntity {
    protected final Auth0HttpClient client;
    protected final HttpUrl baseUrl;
    protected final TokenProvider tokenProvider;

    BaseManagementEntity(Auth0HttpClient client, HttpUrl baseUrl, TokenProvider tokenProvider) {
        this.client = client;
        this.baseUrl = baseUrl;
        this.tokenProvider = tokenProvider;
    }

    protected Request<Void> voidRequest(HttpMethod method, Consumer<RequestBuilder<Void>> customizer) {
        return customizeRequest(
            new RequestBuilder<>(client, tokenProvider, method, baseUrl, new TypeReference<Void>() {
            }),
            customizer
        );
    }

    protected <T> Request<T> request(HttpMethod method, TypeReference<T> target, Consumer<RequestBuilder<T>> customizer) {
        return customizeRequest(
            new RequestBuilder<>(client, tokenProvider, method, baseUrl, target),
            customizer
        );
    }

    private <T> Request<T> customizeRequest(RequestBuilder<T> builder, Consumer<RequestBuilder<T>> customizer) {
        customizer.accept(builder);
        return builder.build();
    }
}

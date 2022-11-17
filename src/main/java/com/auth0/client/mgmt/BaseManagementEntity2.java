package com.auth0.client.mgmt;

import com.auth0.exception.Auth0Exception;
import com.auth0.net.Request;
import com.auth0.net.client.Auth0HttpClient;
import com.auth0.net.client.HttpMethod;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.HttpUrl;

import java.util.function.Consumer;

abstract class BaseManagementEntity2 {
    protected final Auth0HttpClient client;
    // TODO decouple from OkHttp!!
    protected final HttpUrl baseUrl;
    protected final TokenProvider tokenProvider;

    BaseManagementEntity2(Auth0HttpClient client, HttpUrl baseUrl, TokenProvider tokenProvider) {
        this.client = client;
        this.baseUrl = baseUrl;
        this.tokenProvider = tokenProvider;
    }

    protected Request<Void> voidRequest(HttpMethod method, Consumer<RequestBuilder<Void>> customizer) throws Auth0Exception {
        return customizeRequest(
            new RequestBuilder<>(client, method, baseUrl, new TypeReference<Void>() {
            }),
            customizer
        );
    }

    protected <T> Request<T> request(HttpMethod method, TypeReference<T> target, Consumer<RequestBuilder<T>> customizer) throws Auth0Exception {
        return customizeRequest(
            new RequestBuilder<>(client, method, baseUrl, target),
            customizer
        );
    }

    private <T> Request<T> customizeRequest(RequestBuilder<T> builder, Consumer<RequestBuilder<T>> customizer) throws Auth0Exception {
//        builder.withHeader("Authorization", "Bearer " + apiToken);
        builder.withHeader("Authorization", "Bearer " + tokenProvider.getToken());
        customizer.accept(builder);
        return builder.build();
    }
}

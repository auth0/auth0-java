package com.auth0.client.mgmt;

import com.auth0.net.CustomRequest;
import com.auth0.net.VoidRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import java.util.function.Function;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.Nullable;

abstract class BaseManagementEntity {
    protected final OkHttpClient client;
    protected final HttpUrl baseUrl;
    protected final String apiToken;

    BaseManagementEntity(OkHttpClient client, HttpUrl baseUrl, String apiToken) {
        this.client = client;
        this.baseUrl = baseUrl;
        this.apiToken = apiToken;
    }

    protected VoidRequest createVoidRequest(
        Function<HttpUrl.Builder, HttpUrl.Builder> urlBuilder,
        String method
    ) {
        return createVoidRequest(urlBuilder, method, null);
    }

    protected VoidRequest createVoidRequest(
        Function<HttpUrl.Builder, HttpUrl.Builder> urlBuilder,
        String method,
        @Nullable Object body
    ) {
        VoidRequest request = new VoidRequest(client, buildUrl(urlBuilder), method);
        addAuthorizationAndBody(request, body);
        return request;
    }

    protected <T> CustomRequest<T> createRequest(
        Function<HttpUrl.Builder, HttpUrl.Builder> urlBuilder,
        String method,
        TypeReference<T> responseType
    ) {
        return createRequest(urlBuilder, method, responseType, null);
    }

    protected <T> CustomRequest<T> createRequest(
        Function<HttpUrl.Builder, HttpUrl.Builder> urlBuilder,
        String method,
        TypeReference<T> responseType,
        @Nullable Object body
    ) {
        CustomRequest<T> request = new CustomRequest<>(client, buildUrl(urlBuilder), method, responseType);
        addAuthorizationAndBody(request, body);
        return request;
    }

    private String buildUrl(Function<HttpUrl.Builder, HttpUrl.Builder> urlBuilder) {
        return urlBuilder
            .apply(baseUrl.newBuilder())
            .build()
            .toString();
    }

    private <T> void addAuthorizationAndBody(CustomRequest<T> request, @Nullable Object body) {
        request.addHeader("Authorization", "Bearer " + apiToken);

        if (body != null) {
            request.setBody(body);
        }
    }
}

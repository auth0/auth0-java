package com.auth0.net;

import com.auth0.exception.Auth0Exception;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

// TODO allow creation using existing OkHttp client (use client's builder!)!
public class Auth0OkHttpClient implements Auth0HttpClient {

    private final okhttp3.OkHttpClient client;

    public static Auth0OkHttpClient.Builder newBuilder() {
        return new Auth0OkHttpClient.Builder();
    }

    private Auth0OkHttpClient(Builder builder) {
        okhttp3.OkHttpClient.Builder clientBuilder = new okhttp3.OkHttpClient.Builder();
        clientBuilder.readTimeout(builder.readTimeout, TimeUnit.SECONDS);
        clientBuilder.connectTimeout(builder.connectTimeout, TimeUnit.SECONDS);
        for (Interceptor i : builder.interceptors) {
            clientBuilder.addInterceptor(i);
        }
        client = clientBuilder.build();
    }


    @Override
    @SuppressWarnings("deprecation")
    public Auth0HttpResponse makeRequest(Auth0HttpRequest request) throws IOException {
        // Build okHtp3 request

        // Need to create with or without body


        RequestBody okBody = request.getBody() != null ?
            RequestBody.create(MediaType.parse("application/json"), request.getBody())
            : null;

        okhttp3.Request.Builder builder = new okhttp3.Request.Builder()
            .url(request.getUrl())
            .method(request.getMethod(), okBody);
        for (Map.Entry<String, String> e : request.getHeaders().entrySet()) {
            builder.addHeader(e.getKey(), e.getValue());
        }

        // execute
        Response okResponse = client.newCall(builder.build()).execute();

        // TODO add response headers

        // return an Auth0 Auth0HttpResponse
        Headers okHeaders = okResponse.headers();
        Map<String, String> headers = new HashMap<>();
        for (int i = 0; i < okHeaders.size(); i++) {
            headers.put(okHeaders.name(i), okHeaders.value(i));
        }
        Auth0HttpResponse response = new Auth0HttpResponse.Builder()
            .code(okResponse.code())
            .body(okResponse.body().string())
            .headers(headers)
            .build();

        return response;
    }

    @Override
    @SuppressWarnings("deprecation")
    public CompletableFuture<Auth0HttpResponse> makeRequestAsync(Auth0HttpRequest request) {
        final CompletableFuture<Auth0HttpResponse> future = new CompletableFuture<>();

        // Need to create with or without body

        RequestBody okBody = request.getBody() != null ?
            RequestBody.create(MediaType.parse("application/json"), request.getBody())
            : null;


        okhttp3.Request.Builder builder = new okhttp3.Request.Builder()
            .url(request.getUrl())
            .method(request.getMethod(), okBody);
        for (Map.Entry<String, String> e : request.getHeaders().entrySet()) {
            builder.addHeader(e.getKey(), e.getValue());
        }

        // execute
        client.newCall(builder.build()).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                future.completeExceptionally(new Auth0Exception("Failed to execute request", e));
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try {
                    // build Auth0 response
                    Auth0HttpResponse aResponse = new Auth0HttpResponse.Builder()
                        .code(response.code())
                        .body(response.body().string())
                        .build();
                    future.complete(aResponse);
                } catch (Auth0Exception e) {
                    future.completeExceptionally(e);
                }
            }
        });

        return future;
    }

    public static class Builder {
        private int readTimeout;
        private int connectTimeout;
        // TODO proxy

        private final List<Interceptor> interceptors = new ArrayList<>();

        public Builder() {
        }

        public Builder readTimeout(int readTimeout) {
            this.readTimeout = readTimeout;
            return this;
        }

        public Builder connectTimeout(int connectTimeout) {
            this.connectTimeout = connectTimeout;
            return this;
        }

        // TODO just take a list? Order important??
        public Builder interceptor(Interceptor interceptor) {
            interceptors.add(interceptor);
            return this;
        }

        public Auth0OkHttpClient build() {
            return new Auth0OkHttpClient(this);
        }
    }

}

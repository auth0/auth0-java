package com.auth0.net.client;

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
public class DefaultHttpClient implements HttpClient {

    private final okhttp3.OkHttpClient client;

    public static DefaultHttpClient.Builder newBuilder() {
        return new DefaultHttpClient.Builder();
    }

    private DefaultHttpClient(Builder builder) {
        okhttp3.OkHttpClient.Builder clientBuilder = new okhttp3.OkHttpClient.Builder();
        clientBuilder.readTimeout(builder.readTimeout, TimeUnit.SECONDS);
        clientBuilder.connectTimeout(builder.connectTimeout, TimeUnit.SECONDS);
        for (Interceptor i : builder.interceptors) {
            clientBuilder.addInterceptor(i);
        }
        client = clientBuilder.build();
    }


    // TODO accept params?
    @Override
    @SuppressWarnings("deprecation")
    public HttpResponse makeRequest(HttpRequest request) throws IOException {
        // Build okHtp3 request

        // Need to create with or without body


        // TODO only use body on request methods that support it
        // TODO not use null?
        RequestBody okBody = addBody(request);

        okhttp3.Request.Builder builder = new okhttp3.Request.Builder()
            .url(request.getUrl())
            .method(request.getMethod().toString(), okBody);
        for (Map.Entry<String, String> e : request.getHeaders().entrySet()) {
            builder.addHeader(e.getKey(), e.getValue());
        }

        // execute
        Response okResponse = client.newCall(builder.build()).execute();

        // TODO add response headers

        // return an Auth0 HttpResponse
        Headers okHeaders = okResponse.headers();
        Map<String, String> headers = new HashMap<>();
        for (int i = 0; i < okHeaders.size(); i++) {
            headers.put(okHeaders.name(i), okHeaders.value(i));
        }
        HttpResponse response = new HttpResponse.Builder()
            .code(okResponse.code())
            .body(okResponse.body().string())
            .headers(headers)
            .build();

        return response;
    }

    @Override
    @SuppressWarnings("deprecation")
    public CompletableFuture<HttpResponse> makeRequestAsync(HttpRequest request) {
        final CompletableFuture<HttpResponse> future = new CompletableFuture<>();

        // Need to create with or without body

        RequestBody okBody = request.getBody() != null ?
            // TODO put media type on HttpRequestBody
            RequestBody.create(MediaType.parse("application/json"), request.getBody().getContent())
            : null;

//        RequestBody okBody = request.getBody() != null ?
//            RequestBody.create(MediaType.parse("application/json"), request.getBody())
//            : null;


        okhttp3.Request.Builder builder = new okhttp3.Request.Builder()
            .url(request.getUrl())
            .method(request.getMethod().toString(), okBody);
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
                    HttpResponse aResponse = new HttpResponse.Builder()
                        .code(response.code())
                        // TODO handle null string()
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

    @SuppressWarnings("deprecation")
    private RequestBody addBody(HttpRequest request) {
        // TODO not use null?
        RequestBody okBody = null;
        if (request.getBody() != null) {
            // TODO put media type on requestbody!
            if (request.getBody().getFile() != null) {
                // multipart request
                MultipartBody.Builder bodyBuilder = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM);
                // TODO need to pass name
                bodyBuilder.addFormDataPart("users", request.getBody().getFile().getName(),
                    // TODO need to pass media type
                    // Use deprecated method to ensure interop with okhttp 3
                    RequestBody.create(MediaType.parse( "text/json"), request.getBody().getFile()));
                request.getBody().getParams().forEach(bodyBuilder::addFormDataPart);
                okBody = bodyBuilder.build();
            } else {
                okBody = RequestBody.create(MediaType.parse("application/json"), request.getBody().getContent());
            }

        }
//        RequestBody okBody = request.getBody() != null ?
//            // TODO don't set content-type here? Should be on request?
//            RequestBody.create(MediaType.parse("application/json"), request.getBody())
//            : null;
        return okBody;
    }

    // TODO default headers?
    // TODO accept OkHttp client?
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
        // TODO expose interceptors or OkHttp impl details?
        // TODO maybe just add the logging/telemetry interceptors as part of build(), if not exposing OkHttp
        public Builder interceptor(Interceptor interceptor) {
            interceptors.add(interceptor);
            return this;
        }

        public DefaultHttpClient build() {
            return new DefaultHttpClient(this);
        }
    }

}

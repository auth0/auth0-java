package com.auth0.net;

import com.auth0.exception.Auth0Exception;
import okhttp3.*;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public abstract class BaseRequest<T> implements Request<T> {

    private final OkHttpClient client;

    BaseRequest(OkHttpClient client) {
        this.client = client;
    }

    protected abstract okhttp3.Request createRequest() throws Auth0Exception;

    protected abstract T parseResponseBody(okhttp3.Response response) throws Auth0Exception;

    /**
     * Executes this request.
     *
     * @return the response body JSON decoded as T
     * @throws Auth0Exception if the request execution fails.
     */
    @Override
    public com.auth0.net.Response<T> execute() throws Auth0Exception {
        okhttp3.Request request = createRequest();
        try (Response response = client.newCall(request).execute()) {
            T body = parseResponseBody(response);
            return new ResponseImpl<T>(fromOkHttpHeaders(response.headers()), body, response.code());
        } catch (Auth0Exception e) {
            throw e;
        } catch (IOException e) {
            throw new Auth0Exception("Failed to execute request", e);
        }
    }

    @Override
    public CompletableFuture<com.auth0.net.Response<T>> executeAsync() {
        final CompletableFuture<com.auth0.net.Response<T>> future = new CompletableFuture<>();

        okhttp3.Request request;
        try {
            request = createRequest();
        } catch (Auth0Exception e) {
            future.completeExceptionally(e);
            return future;
        }

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                future.completeExceptionally(new Auth0Exception("Failed to execute request", e));
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try {
                    T parsedResponse = parseResponseBody(response);
                    future.complete(new ResponseImpl<>(fromOkHttpHeaders(response.headers()), parsedResponse, response.code()));
                } catch (Auth0Exception e) {
                    future.completeExceptionally(e);
                }
            }
        });

        return future;
    }

    private static Map<String, String> fromOkHttpHeaders(Headers okHttpHeaders) {
        if (Objects.isNull(okHttpHeaders)) {
            return Collections.emptyMap();
        }

        Map<String, String> headers = new HashMap<>();
        okHttpHeaders.forEach(nameValuePair -> headers.put(nameValuePair.getFirst(), nameValuePair.getSecond()));
        return headers;
    }
}

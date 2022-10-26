package com.auth0.net;

import com.auth0.exception.Auth0Exception;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

public abstract class BaseRequest<T> implements Request<T> {

    private final OkHttpClient client;

    BaseRequest(OkHttpClient client) {
        this.client = client;
    }

    protected abstract okhttp3.Request createRequest() throws Auth0Exception;

    protected abstract T parseResponse(okhttp3.Response response) throws Auth0Exception;

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
            T parsed = parseResponse(response);
            return new ResponseImpl<T>(Collections.emptyMap(), parsed, response.code());
//            return parseResponse(response);
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
                    T parsedResponse = parseResponse(response);
                    future.complete(new ResponseImpl<>(Collections.emptyMap(), parsedResponse, response.code()));
                } catch (Auth0Exception e) {
                    future.completeExceptionally(e);
                }
            }
        });

        return future;
    }
}

package com.auth0.net;

import com.auth0.exception.Auth0Exception;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public abstract class BaseRequest<T> implements Request<T> {

//    private final OkHttpClient client;
//
//    BaseRequest(OkHttpClient client) {
//        this.client = client;
//    }
//
//    protected abstract okhttp3.Request createRequest() throws Auth0Exception;
//
//    protected abstract T parseResponse(Response response) throws Auth0Exception;

    private final Auth0HttpClient client;

    BaseRequest(Auth0HttpClient client) {
        this.client = client;
    }

    protected abstract Auth0HttpRequest createRequest() throws Auth0Exception;

    protected abstract T parseResponse(Auth0HttpResponse response) throws Auth0Exception;

    /**
     * Executes this request.
     *
     * @return the response body JSON decoded as T
     * @throws Auth0Exception if the request execution fails.
     */
    @Override
    public T execute() throws Auth0Exception {
//        okhttp3.Request request = createRequest();
        Auth0HttpRequest request = createRequest();
        try {
            Auth0HttpResponse response = client.makeRequest(request);
            return parseResponse(response);
        } catch (Auth0Exception e) {
            throw e;
        } catch (IOException ioe) {
            throw new Auth0Exception("Failed to execute the request", ioe);
        }

//        try (Response response = client.newCall(request).execute()) {
//            return parseResponse(response);
//        } catch (Auth0Exception e) {
//            throw e;
//        } catch (IOException e) {
//            throw new Auth0Exception("Failed to execute request", e);
//        }
    }

    @Override
    public CompletableFuture<T> executeAsync() {
//        Auth0HttpRequest request;
//        CompletableFuture<Auth0HttpResponse> httpResponseCompletableFuture
        final CompletableFuture<T> future = new CompletableFuture<T>();
        Auth0HttpRequest request;

        try {
            request = createRequest();
        } catch (Auth0Exception e) {
            future.completeExceptionally(e);
            return future;
        }

        // TODO error handling, verify this is good async practice
        // Can we be sure that the parseResponse will always be executed prior to customer-added "thenApply" / consumers?
        // Appears so? https://bugs.openjdk.java.net/browse/JDK-8144577
        return client.makeRequestAsync(request).thenApply(r -> {
            try {
                return parseResponse(r);
            } catch (Auth0Exception e) {
                e.printStackTrace(); // TODO
            }
            return null; // TODO
        });
        // TODO error handling, verify this is good async practice
        // Can we be sure that the parseResponse will always be executed prior to customer-added "thenApply" / consumers?
        // Appears so? https://bugs.openjdk.java.net/browse/JDK-8144577
//        CompletableFuture<Auth0HttpResponse> httpResponseCompletableFuture = httpClient.makeRequestAsync(request);
//        return httpResponseCompletableFuture.thenApply(r -> {
//            try {
//                return parseResponse(r);
//            } catch (Auth0Exception e) {
//                e.printStackTrace();
//            }
//            return null;
//        });

//        final CompletableFuture<T> future = new CompletableFuture<T>();
//
//        okhttp3.Request request;
//        try {
//            request = createRequest();
//        } catch (Auth0Exception e) {
//            future.completeExceptionally(e);
//            return future;
//        }
//
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(@NotNull Call call, @NotNull IOException e) {
//                future.completeExceptionally(new Auth0Exception("Failed to execute request", e));
//            }
//
//            @Override
//            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
//                try {
//                    T parsedResponse = parseResponse(response);
//                    future.complete(parsedResponse);
//                } catch (Auth0Exception e) {
//                    future.completeExceptionally(e);
//                }
//            }
//        });
//
//        return future;
    }
}

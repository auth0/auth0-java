package com.auth0.net;

import com.auth0.client.mgmt.TokenProvider;
import com.auth0.exception.Auth0Exception;
import com.auth0.net.client.Auth0HttpClient;
import com.auth0.net.client.Auth0HttpRequest;
import com.auth0.net.client.Auth0HttpResponse;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public abstract class BaseRequest<T> implements Request<T> {

    private final Auth0HttpClient client;
    private final TokenProvider tokenProvider;

    BaseRequest(Auth0HttpClient client, TokenProvider tokenProvider) {
        this.client = client;
        this.tokenProvider = tokenProvider;
    }

    TokenProvider getTokenProvider() {
        return this.tokenProvider;
    }

    protected abstract Auth0HttpRequest createRequest() throws Auth0Exception;

    protected abstract T parseResponseBody(Auth0HttpResponse response) throws Auth0Exception;

    /**
     * Executes this request.
     *
     * @return the response body JSON decoded as T
     * @throws Auth0Exception if the request execution fails.
     */
    @Override
    public com.auth0.net.Response<T> execute() throws Auth0Exception {
        Auth0HttpRequest request = createRequest();
        try {
            Auth0HttpResponse response = client.sendRequest(request);
            T body = parseResponseBody(response);
            return new ResponseImpl<T>(response.getHeaders(), body, response.getCode());
        } catch (Auth0Exception e) {
            throw e;
        } catch (IOException ioe) {
            throw new Auth0Exception("Failed to execute the request", ioe);
        }
    }

    @Override
    public CompletableFuture<com.auth0.net.Response<T>> executeAsync() {
        final CompletableFuture<com.auth0.net.Response<T>> future = new CompletableFuture<>();
        Auth0HttpRequest request;

        try {
            request = createRequest();
        } catch (Auth0Exception e) {
            future.completeExceptionally(e);
            return future;
        }

        return client.sendRequestAsync(request)
            .thenCompose(this::getResponseFuture);
    }

    private CompletableFuture<Response<T>> getResponseFuture(Auth0HttpResponse httpResponse) {
        CompletableFuture<Response<T>> future = new CompletableFuture<>();
        try {
            T body = parseResponseBody(httpResponse);
            future = CompletableFuture.completedFuture(new ResponseImpl<>(httpResponse.getHeaders(), body, httpResponse.getCode()));
        } catch (Auth0Exception e) {
            future.completeExceptionally(e);
            return future;
        }
        return future;
    }
}


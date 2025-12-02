package com.auth0.net.client;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

/**
 * The HttpClient interface defines how HTTP requests to the Auth0 APIs are made.
 */
public interface Auth0HttpClient {

    /**
     * Builds, executes, and returns the result of a synchronous HTTP request to an Auth0 API.
     * @param request the request to send.
     * @return the response returned by the executed request.
     * @throws IOException if the request can not be completed due to a network timeout or interruption.
     */
    Auth0HttpResponse sendRequest(Auth0HttpRequest request) throws IOException;

    /**
     * Builds and executes an asynchronous HTTP request to an Auth0 API.
     * @param request the request to send.
     * @return the {@link CompletableFuture} that represents the result of the asynchronous HTTP request.
     */
    CompletableFuture<Auth0HttpResponse> sendRequestAsync(Auth0HttpRequest request);
}

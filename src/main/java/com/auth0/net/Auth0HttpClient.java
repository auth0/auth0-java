package com.auth0.net;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public interface Auth0HttpClient {

    Auth0HttpResponse makeRequest(Auth0HttpRequest request) throws IOException;

    CompletableFuture<Auth0HttpResponse> makeRequestAsync(Auth0HttpRequest request);

}

package com.auth0.net;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public interface HttpClient {

    HttpResponse makeRequest(HttpRequest request) throws IOException;

    CompletableFuture<HttpResponse> makeRequestAsync(HttpRequest request);

}

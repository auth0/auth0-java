import com.auth0.net.Request;
import com.auth0.net.client.Auth0HttpClient;
import com.auth0.net.client.Auth0HttpResponse;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.CompletableFuture;

public class JavaNetClient implements Auth0HttpClient {
    java.net.http.HttpClient client = java.net.http.HttpClient.newHttpClient();


    @Override
    public <E> Auth0HttpResponse sendRequest(Request<E> request) throws IOException {
        // build request
        java.net.http.HttpClient javaClient = java.net.http.HttpClient.newHttpClient();
        java.net.http.HttpRequest.Builder builder = java.net.http.HttpRequest.newBuilder()
            .uri(URI.create(request.getUrl()));

        // add headers, etc.
        // ...
        request.getHeaders().forEach(builder::setHeader);

        java.net.http.HttpRequest req = builder.build();

        // execute
        java.net.http.HttpResponse<String> response;
        try {
            response = client.send(req, java.net.http.HttpResponse.BodyHandlers.ofString());

            // Transform response to Auth0 response type
            Auth0HttpResponse auth0Response = Auth0HttpResponse.newBuilder()
                .withStatusCode(response.statusCode())
                .withBody(response.body())
                .build();
            return auth0Response;
        } catch (InterruptedException e) {
            throw new IOException(e);
        }
    }
}

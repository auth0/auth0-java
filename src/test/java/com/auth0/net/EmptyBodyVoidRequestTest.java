package com.auth0.net;

import com.auth0.client.MockServer;
import com.auth0.client.mgmt.TokenProvider;
import com.auth0.net.client.Auth0HttpClient;
import com.auth0.net.client.DefaultHttpClient;
import com.auth0.net.client.HttpMethod;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

import static com.auth0.client.MockServer.AUTH_TOKENS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class EmptyBodyVoidRequestTest {

    private Auth0HttpClient client;
    private TokenProvider tokenProvider;
    private MockServer server;

    @BeforeEach
    public void setUp() throws Exception {
        client = new DefaultHttpClient.Builder().build();
        server = new MockServer();
        tokenProvider = new TokenProvider() {
            @Override
            public String getToken() {
                return "Bearer abc";
            }

            @Override
            public CompletableFuture<String> getTokenAsync() {
                return CompletableFuture.completedFuture("Bearer abc");
            }
        };
    }

    @Test
    public void shouldCreatePOSTRequest() throws Exception {
        EmptyBodyVoidRequest<Void> request = new EmptyBodyVoidRequest<>(client, tokenProvider, server.getBaseUrl(), HttpMethod.POST, new TypeReference<Void>() {});
        assertThat(request, is(notNullValue()));

        server.jsonResponse(AUTH_TOKENS, 200);
        Void execute = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();
        assertThat(recordedRequest.getMethod(), is(HttpMethod.POST.toString()));
        assertThat(execute, is(nullValue()));
    }
}

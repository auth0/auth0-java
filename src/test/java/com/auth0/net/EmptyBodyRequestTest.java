package com.auth0.net;

import com.auth0.client.MockServer;
import com.auth0.client.mgmt.SimpleTokenProvider;
import com.auth0.json.auth.TokenHolder;
import com.auth0.net.client.Auth0HttpClient;
import com.auth0.net.client.DefaultHttpClient;
import com.auth0.net.client.HttpMethod;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.auth0.client.MockServer.AUTH_TOKENS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.mock;

public class EmptyBodyRequestTest {

    private MockServer server;
    private Auth0HttpClient client;

    private TypeReference<TokenHolder> tokenHolderType;

    @BeforeEach
    public void setUp() throws Exception {
        server = new MockServer();
        client = new DefaultHttpClient.Builder().build();
        tokenHolderType = new TypeReference<TokenHolder>() {
        };
    }

    @AfterEach
    public void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void shouldCreateEmptyRequestBody() throws Exception {
        EmptyBodyRequest<TokenHolder> request = new EmptyBodyRequest<>(client, SimpleTokenProvider.create("apiToken"), server.getBaseUrl(), HttpMethod.POST, tokenHolderType);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(AUTH_TOKENS, 200);
        request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();
        assertThat(recordedRequest.getMethod(), is(HttpMethod.POST.toString()));
        assertThat(recordedRequest.getBodySize(), is(0L));
    }

    @Test
    public void shouldNotAddParameters() throws Exception {
        EmptyBodyRequest<TokenHolder> request = new EmptyBodyRequest<>(client, SimpleTokenProvider.create("apiToken"), server.getBaseUrl(), HttpMethod.POST, tokenHolderType);
        Map mapValue = mock(Map.class);
        request.addParameter("key", "value");
        request.addParameter("map", mapValue);

        server.jsonResponse(AUTH_TOKENS, 200);
        request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();
        assertThat(recordedRequest.getMethod(), is(HttpMethod.POST.toString()));
        assertThat(recordedRequest.getBodySize(), is(0L));
    }
}

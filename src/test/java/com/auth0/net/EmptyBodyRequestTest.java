package com.auth0.net;

import com.auth0.client.MockServer;
import com.auth0.json.auth.TokenHolder;
import com.auth0.net.client.DefaultHttpClient;
import com.auth0.net.client.HttpClient;
import com.auth0.net.client.HttpMethod;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static com.auth0.client.MockServer.AUTH_TOKENS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.mock;

public class EmptyBodyRequestTest {

    private MockServer server;
    private HttpClient client;

    private TypeReference<TokenHolder> tokenHolderType;

    @Before
    public void setUp() throws Exception {
        server = new MockServer();
        client = new DefaultHttpClient.Builder().build();
        tokenHolderType = new TypeReference<TokenHolder>() {
        };
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void shouldCreateEmptyRequestBody() throws Exception {
        EmptyBodyRequest<TokenHolder> request = new EmptyBodyRequest<>(client, server.getBaseUrl(), HttpMethod.POST, tokenHolderType);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(AUTH_TOKENS, 200);
        request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();
        assertThat(recordedRequest.getMethod(), is(HttpMethod.POST.toString()));
        assertThat(recordedRequest.getBodySize(), is(0L));
    }

    @Test
    public void shouldNotAddParameters() throws Exception {
        EmptyBodyRequest<TokenHolder> request = new EmptyBodyRequest<>(client, server.getBaseUrl(), HttpMethod.POST, tokenHolderType);
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

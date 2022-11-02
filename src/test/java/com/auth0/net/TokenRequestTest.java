package com.auth0.net;

import com.auth0.client.MockServer;
import com.auth0.json.auth.TokenHolder;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static com.auth0.client.MockServer.AUTH_TOKENS;
import static com.auth0.client.MockServer.bodyFromRequest;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class TokenRequestTest {

    private HttpClient client;
    private MockServer server;

    @Before
    public void setUp() throws Exception {
        client = new DefaultHttpClient.Builder().build();
        server = new MockServer();
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void shouldCreateRequest() throws Exception {
        TokenRequest request = new TokenRequest(client, server.getBaseUrl());
        request.addParameter("non_empty", "body");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(AUTH_TOKENS, 200);
        TokenHolder response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();
        assertThat(recordedRequest.getMethod(), is("POST"));
        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldSetAudience() throws Exception {
        TokenRequest request = new TokenRequest(client, server.getBaseUrl());
        assertThat(request, is(notNullValue()));
        request.setAudience("https://myapi.auth0.com/users");

        server.jsonResponse(AUTH_TOKENS, 200);
        request.execute();
        RecordedRequest recordedRequest = server.takeRequest();
        Map<String, Object> values = bodyFromRequest(recordedRequest);
        assertThat(values, hasEntry("audience", "https://myapi.auth0.com/users"));
    }

    @Test
    public void shouldSetScope() throws Exception {
        TokenRequest request = new TokenRequest(client, server.getBaseUrl());
        assertThat(request, is(notNullValue()));
        request.setScope("email profile photos");

        server.jsonResponse(AUTH_TOKENS, 200);
        request.execute();
        RecordedRequest recordedRequest = server.takeRequest();
        Map<String, Object> values = bodyFromRequest(recordedRequest);
        assertThat(values, hasEntry("scope", "email profile photos"));
    }

    @Test
    public void shouldSetRealm() throws Exception {
        TokenRequest request = new TokenRequest(client, server.getBaseUrl());
        assertThat(request, is(notNullValue()));
        request.setRealm("dbconnection");

        server.jsonResponse(AUTH_TOKENS, 200);
        request.execute();
        RecordedRequest recordedRequest = server.takeRequest();
        Map<String, Object> values = bodyFromRequest(recordedRequest);
        assertThat(values, hasEntry("realm", "dbconnection"));
    }

}

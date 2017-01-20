package com.auth0.net;

import com.auth0.client.MockServer;
import okhttp3.OkHttpClient;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.auth0.client.MockServer.AUTH_TOKENS;
import static com.auth0.client.MockServer.bodyFromRequest;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class VoidRequestTest {
    private OkHttpClient client;
    private MockServer server;

    @Before
    public void setUp() throws Exception {
        client = new OkHttpClient();
        server = new MockServer();
    }

    @Test
    public void shouldCreateGETRequest() throws Exception {
        VoidRequest request = new VoidRequest(client, server.getBaseUrl(), "GET");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(AUTH_TOKENS, 200);
        Void execute = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();
        assertThat(recordedRequest.getMethod(), is("GET"));
        assertThat(execute, is(nullValue()));
    }

    @Test
    public void shouldCreatePOSTRequest() throws Exception {
        VoidRequest request = new VoidRequest(client, server.getBaseUrl(), "POST");
        assertThat(request, is(notNullValue()));
        request.addParameter("non_empty", "body");

        server.jsonResponse(AUTH_TOKENS, 200);
        Void execute = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();
        assertThat(recordedRequest.getMethod(), is("POST"));
        assertThat(execute, is(nullValue()));
    }

    @Test
    public void shouldSetSignUpCustomFields() throws Exception {
        VoidRequest request = new VoidRequest(client, server.getBaseUrl(), "POST");
        assertThat(request, is(notNullValue()));
        Map<String, String> customFields = new HashMap<>();
        customFields.put("name", "john");
        customFields.put("age", "25");
        request.setCustomFields(customFields);

        server.jsonResponse(AUTH_TOKENS, 200);
        request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        Map<String, Object> values = bodyFromRequest(recordedRequest);
        assertThat(values, is(notNullValue()));
        assertThat(values, hasKey("user_metadata"));
        Map<String, String> fields = (Map<String, String>) values.get("user_metadata");
        assertThat(fields, hasEntry("name", "john"));
        assertThat(fields, hasEntry("age", "25"));
    }

}
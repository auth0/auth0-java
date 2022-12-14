package com.auth0.net;

import com.auth0.client.MockServer;
import com.auth0.json.auth.CreatedUser;
import com.auth0.net.client.DefaultHttpClient;
import com.auth0.net.client.Auth0HttpClient;
import com.auth0.net.client.HttpMethod;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.auth0.client.MockServer.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CreateUserRequestTest {

    private Auth0HttpClient client;
    private MockServer server;

    @Before
    public void setUp() throws Exception {
        client = new DefaultHttpClient.Builder().build();
        server = new MockServer();
    }

    @Test
    public void shouldCreatePOSTRequest() throws Exception {
        SignUpRequest request = new SignUpRequest(client, server.getBaseUrl());
        assertThat(request, is(notNullValue()));
        request.addParameter("non_empty", "body");

        server.jsonResponse(AUTH_SIGN_UP, 200);
        CreatedUser execute = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();
        assertThat(recordedRequest.getMethod(), is(HttpMethod.POST.toString()));
        assertThat(execute, is(notNullValue()));
    }

    @Test
    public void shouldSetSignUpCustomFields() throws Exception {
        SignUpRequest request = new SignUpRequest(client, server.getBaseUrl());
        assertThat(request, is(notNullValue()));
        Map<String, String> customFields = new HashMap<>();
        customFields.put("name", "john");
        customFields.put("age", "25");
        request.setCustomFields(customFields);

        server.jsonResponse(AUTH_TOKENS, 200);
        request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        Map<String, Object> values = bodyFromRequest(recordedRequest);
        assertThat(values, is(notNullValue()));
        assertThat(values, hasKey("user_metadata"));
        Map<String, String> fields = (Map<String, String>) values.get("user_metadata");
        assertThat(fields, hasEntry("name", "john"));
        assertThat(fields, hasEntry("age", "25"));
    }


}

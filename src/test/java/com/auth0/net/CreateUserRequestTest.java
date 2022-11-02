package com.auth0.net;

import com.auth0.client.MockServer;
import com.auth0.json.auth.CreatedUser;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.auth0.client.MockServer.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class CreateUserRequestTest {

    private HttpClient client;
    private MockServer server;

    @Before
    public void setUp() throws Exception {
        client = new DefaultHttpClient.Builder().build();
        server = new MockServer();
    }

    @Test
    public void shouldCreatePOSTRequest() throws Exception {
        CreateUserRequest request = new CreateUserRequest(client, server.getBaseUrl());
        assertThat(request, is(notNullValue()));
        request.addParameter("non_empty", "body");

        server.jsonResponse(AUTH_SIGN_UP, 200);
        CreatedUser execute = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();
        assertThat(recordedRequest.getMethod(), is("POST"));
        assertThat(execute, is(notNullValue()));
    }

    @Test
    public void shouldSetSignUpCustomFields() throws Exception {
        CreateUserRequest request = new CreateUserRequest(client, server.getBaseUrl());
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

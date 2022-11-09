package com.auth0.net;

import com.auth0.client.MockServer;
import com.auth0.net.client.DefaultHttpClient;
import com.auth0.net.client.HttpClient;
import com.auth0.net.client.HttpMethod;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.Before;
import org.junit.Test;

import static com.auth0.client.MockServer.AUTH_TOKENS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class VoidRequestTest {
    private HttpClient client;
    private MockServer server;

    @Before
    public void setUp() throws Exception {
        client = new DefaultHttpClient.Builder().build();
        server = new MockServer();
    }

    @Test
    public void shouldCreateGETRequest() throws Exception {
        VoidRequest request = new VoidRequest(client, server.getBaseUrl(), HttpMethod.GET);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(AUTH_TOKENS, 200);
        Void execute = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();
        assertThat(recordedRequest.getMethod(), is(HttpMethod.GET.toString()));
        assertThat(execute, is(nullValue()));
    }

    @Test
    public void shouldCreatePOSTRequest() throws Exception {
        VoidRequest request = new VoidRequest(client, server.getBaseUrl(), HttpMethod.POST);
        assertThat(request, is(notNullValue()));
        request.addParameter("non_empty", "body");

        server.jsonResponse(AUTH_TOKENS, 200);
        Void execute = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();
        assertThat(recordedRequest.getMethod(), is(HttpMethod.POST.toString()));
        assertThat(execute, is(nullValue()));
    }

}

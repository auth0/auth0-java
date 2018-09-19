package com.auth0.net;

import com.auth0.client.MockServer;
import com.auth0.exception.Auth0Exception;
import com.auth0.json.mgmt.jobs.JobError;
import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.mockwebserver.RecordedRequest;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;

import static com.auth0.client.MockServer.AUTH_TOKENS;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StatusCodeRequestTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();
    private OkHttpClient client;
    private MockServer server;

    @Before
    public void setUp() throws Exception {
        client = new OkHttpClient();
        server = new MockServer();
    }

    @Test
    public void shouldGenerateJobError() throws Exception {
        StatusCodeRequest request = new StatusCodeRequest(client, this.generateUrl());
        assertThat(request, is(notNullValue()));

        server.emptyResponse(204);

        JobError jobError = request.execute();
        assertThat(jobError, is(notNullValue()));
        assertThat(jobError.getStatusCode(), is(204));

        RecordedRequest recordedRequest = server.takeRequest();
        assertThat(recordedRequest.getMethod(), is("GET"));
    }

    @Test
    public void shouldGenerateExceptionOnError() throws Exception {
        exception.expect(Auth0Exception.class);
        StatusCodeRequest request = new StatusCodeRequest(client, this.generateUrl());
        assertThat(request, is(notNullValue()));

        server.emptyResponse(401);
        request.execute();
    }

    @Test
    public void shouldGenerateExceptionOnRequestFailure() throws Exception {
        exception.expect(Auth0Exception.class);
        exception.expectCause(Matchers.<Throwable>instanceOf(IOException.class));
        exception.expectMessage("Failed to execute request");

        OkHttpClient client = mock(OkHttpClient.class);
        Call call = mock(Call.class);
        when(client.newCall(any(okhttp3.Request.class))).thenReturn(call);
        when(call.execute()).thenThrow(IOException.class);
        StatusCodeRequest request = new StatusCodeRequest(client, server.getBaseUrl());
        request.execute();
    }

    private String generateUrl() {
        return HttpUrl.parse(server.getBaseUrl())
                .newBuilder()
                .addPathSegments("api/v2/jobs/job_0000000001/error")
                .build()
                .toString();

    }
}
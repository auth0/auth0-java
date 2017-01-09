package com.auth0.net;

import com.auth0.MockServer;
import com.auth0.exception.Auth0Exception;
import com.auth0.exception.AuthAPIException;
import com.auth0.json.TokenHolder;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.mockwebserver.RecordedRequest;
import org.hamcrest.Matchers;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.auth0.MockServer.bodyFromRequest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CustomRequestTest {
    private MockServer server;
    private OkHttpClient client;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        server = new MockServer();
        client = new OkHttpClient();
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void shouldCreateGETRequest() throws Exception {
        CustomRequest<TokenHolder> request = new CustomRequest<>(client, server.getBaseUrl(), "GET", TokenHolder.class);
        assertThat(request, is(notNullValue()));

        server.okResponse();
        TokenHolder execute = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();
        Assert.assertThat(recordedRequest.getMethod(), is("GET"));
        Assert.assertThat(execute, is(notNullValue()));
    }

    @Test
    public void shouldCreatePOSTRequest() throws Exception {
        CustomRequest<TokenHolder> request = new CustomRequest<>(client, server.getBaseUrl(), "POST", TokenHolder.class);
        assertThat(request, is(notNullValue()));
        request.addParameter("non_empty", "body");

        server.okResponse();
        TokenHolder execute = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();
        Assert.assertThat(recordedRequest.getMethod(), is("POST"));
        Assert.assertThat(execute, is(notNullValue()));
    }

    @Test
    public void shouldAddParameters() throws Exception {
        CustomRequest<TokenHolder> request = new CustomRequest<>(client, server.getBaseUrl(), "POST", TokenHolder.class);
        Map mapValue = mock(Map.class);
        request.addParameter("key", "value");
        request.addParameter("map", mapValue);

        server.okResponse();
        request.execute();
        RecordedRequest recordedRequest = server.takeRequest();
        Map<String, Object> values = bodyFromRequest(recordedRequest);
        assertThat(values, hasEntry("key", (Object) "value"));
        assertThat(values, hasEntry("map", (Object) mapValue));
    }

    @Test
    public void shouldAddHeaders() throws Exception {
        CustomRequest<TokenHolder> request = new CustomRequest<>(client, server.getBaseUrl(), "POST", TokenHolder.class);
        request.addParameter("non_empty", "body");
        request.addHeader("Content-Type", "application/json");
        request.addHeader("Authorization", "Bearer my_access_token");

        server.okResponse();
        request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest.getHeader("Content-Type"), is("application/json"));
        assertThat(recordedRequest.getHeader("Authorization"), is("Bearer my_access_token"));
    }

    @Test
    public void shouldThrowOnExecuteFailure() throws Exception {
        exception.expect(Auth0Exception.class);
        exception.expectCause(Matchers.<Throwable>instanceOf(IOException.class));
        exception.expectMessage("Failed to execute request");

        OkHttpClient client = mock(OkHttpClient.class);
        Call call = mock(Call.class);
        when(client.newCall(any(okhttp3.Request.class))).thenReturn(call);
        when(call.execute()).thenThrow(IOException.class);
        CustomRequest<Void> request = new CustomRequest<>(client, server.getBaseUrl(), "GET", Void.class);
        request.execute();
    }

    @Test
    public void shouldThrowOnBodyCreationFailure() throws Exception {
        ObjectMapper mapper = mock(ObjectMapper.class);
        when(mapper.writeValueAsBytes(any(Object.class))).thenThrow(JsonProcessingException.class);

        CustomRequest request = new CustomRequest<>(client, mapper, server.getBaseUrl(), "POST", Object.class);
        request.addParameter("name", "value");
        exception.expect(Auth0Exception.class);
        exception.expectCause(Matchers.<Throwable>instanceOf(JsonProcessingException.class));
        exception.expectMessage("Couldn't create the request body.");
        request.execute();
    }

    @Test
    public void shouldParseSuccessfulResponse() throws Exception {
        CustomRequest<TokenHolder> request = new CustomRequest<>(client, server.getBaseUrl(), "GET", TokenHolder.class);
        server.tokensResponse();
        TokenHolder response = request.execute();
        server.takeRequest();

        assertThat(response, is(notNullValue()));
        assertThat(response.getAccessToken(), not(isEmptyOrNullString()));
        assertThat(response.getIdToken(), not(isEmptyOrNullString()));
        assertThat(response.getRefreshToken(), not(isEmptyOrNullString()));
        assertThat(response.getTokenType(), not(isEmptyOrNullString()));
        assertThat(response.getExpiresIn(), is(notNullValue()));
    }

    @Test
    public void shouldThrowOnParseInvalidSuccessfulResponse() throws Exception {
        CustomRequest<List> request = new CustomRequest<>(client, server.getBaseUrl(), "GET", List.class);
        server.tokensResponse();
        Exception exception = null;
        try {
            request.execute();
            server.takeRequest();
        } catch (Exception e) {
            exception = e;
        }
        assertThat(exception, is(notNullValue()));
        assertThat(exception, is(instanceOf(AuthAPIException.class)));
        assertThat(exception.getCause(), is(instanceOf(JsonMappingException.class)));
        assertThat(exception.getMessage(), is("Authentication failed with status code 200: Failed to parse body as List"));
        AuthAPIException authException = (AuthAPIException) exception;
        assertThat(authException.getDescription(), is("Failed to parse body as List"));
        assertThat(authException.getError(), is(nullValue()));
        assertThat(authException.getStatusCode(), is(200));
    }

    @Test
    public void shouldParseJSONErrorResponseWithErrorDescription() throws Exception {
        CustomRequest<List> request = new CustomRequest<>(client, server.getBaseUrl(), "GET", List.class);
        server.JSONErrorResponseWithErrorDescription();
        Exception exception = null;
        try {
            request.execute();
            server.takeRequest();
        } catch (Exception e) {
            exception = e;
        }
        assertThat(exception, is(notNullValue()));
        assertThat(exception, is(instanceOf(AuthAPIException.class)));
        assertThat(exception.getCause(), is(nullValue()));
        assertThat(exception.getMessage(), is("Authentication failed with status code 400: the connection was not found"));
        AuthAPIException authException = (AuthAPIException) exception;
        assertThat(authException.getDescription(), is("the connection was not found"));
        assertThat(authException.getError(), is("invalid_request"));
        assertThat(authException.getStatusCode(), is(400));
    }

    @Test
    public void shouldParseJSONErrorResponseWithError() throws Exception {
        CustomRequest<List> request = new CustomRequest<>(client, server.getBaseUrl(), "GET", List.class);
        server.JSONErrorResponseWithError();
        Exception exception = null;
        try {
            request.execute();
            server.takeRequest();
        } catch (Exception e) {
            exception = e;
        }
        assertThat(exception, is(notNullValue()));
        assertThat(exception, is(instanceOf(AuthAPIException.class)));
        assertThat(exception.getCause(), is(nullValue()));
        assertThat(exception.getMessage(), is("Authentication failed with status code 400: missing username for Username-Password-Authentication connection with requires_username enabled"));
        AuthAPIException authException = (AuthAPIException) exception;
        assertThat(authException.getDescription(), is(nullValue()));
        assertThat(authException.getError(), is("missing username for Username-Password-Authentication connection with requires_username enabled"));
        assertThat(authException.getStatusCode(), is(400));
    }

    @Test
    public void shouldParseJSONErrorResponseWithDescription() throws Exception {
        CustomRequest<List> request = new CustomRequest<>(client, server.getBaseUrl(), "GET", List.class);
        server.JSONErrorResponseWithDescription();
        Exception exception = null;
        try {
            request.execute();
            server.takeRequest();
        } catch (Exception e) {
            exception = e;
        }
        assertThat(exception, is(notNullValue()));
        assertThat(exception, is(instanceOf(AuthAPIException.class)));
        assertThat(exception.getCause(), is(nullValue()));
        assertThat(exception.getMessage(), is("Authentication failed with status code 400: The user already exists."));
        AuthAPIException authException = (AuthAPIException) exception;
        assertThat(authException.getDescription(), is("The user already exists."));
        assertThat(authException.getError(), is("user_exists"));
        assertThat(authException.getStatusCode(), is(400));
    }

    @Test
    public void shouldParsePlainTextErrorResponse() throws Exception {
        CustomRequest<List> request = new CustomRequest<>(client, server.getBaseUrl(), "GET", List.class);
        server.plainTextErrorResponse();
        Exception exception = null;
        try {
            request.execute();
            server.takeRequest();
        } catch (Exception e) {
            exception = e;
        }
        assertThat(exception, is(notNullValue()));
        assertThat(exception, is(instanceOf(AuthAPIException.class)));
        assertThat(exception.getCause(), is(instanceOf(JsonParseException.class)));
        assertThat(exception.getMessage(), is("Authentication failed with status code 400: A plain-text error response"));
        AuthAPIException authException = (AuthAPIException) exception;
        assertThat(authException.getDescription(), is("A plain-text error response"));
        assertThat(authException.getError(), is(nullValue()));
        assertThat(authException.getStatusCode(), is(400));
    }

}
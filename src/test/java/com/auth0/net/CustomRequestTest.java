package com.auth0.net;

import com.auth0.client.MockServer;
import com.auth0.exception.APIException;
import com.auth0.exception.Auth0Exception;
import com.auth0.exception.RateLimitException;
import com.auth0.json.auth.TokenHolder;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
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

import static com.auth0.client.MockServer.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CustomRequestTest {
    private MockServer server;
    private OkHttpClient client;

    @SuppressWarnings("deprecation")
    @Rule
    public ExpectedException exception = ExpectedException.none();
    private TypeReference<TokenHolder> tokenHolderType;
    private TypeReference<List> listType;
    private TypeReference<Void> voidType;

    @Before
    public void setUp() throws Exception {
        server = new MockServer();
        client = new OkHttpClient();
        tokenHolderType = new TypeReference<TokenHolder>() {
        };
        listType = new TypeReference<List>() {
        };
        voidType = new TypeReference<Void>() {
        };
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void shouldCreateGETRequest() throws Exception {
        CustomRequest<TokenHolder> request = new CustomRequest<>(client, server.getBaseUrl(), "GET", tokenHolderType);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(AUTH_TOKENS, 200);
        TokenHolder execute = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();
        assertThat(recordedRequest.getMethod(), is("GET"));
        assertThat(execute, is(notNullValue()));
    }

    @Test
    public void shouldCreatePOSTRequest() throws Exception {
        CustomRequest<TokenHolder> request = new CustomRequest<>(client, server.getBaseUrl(), "POST", tokenHolderType);
        assertThat(request, is(notNullValue()));
        request.addParameter("non_empty", "body");

        server.jsonResponse(AUTH_TOKENS, 200);
        TokenHolder execute = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();
        assertThat(recordedRequest.getMethod(), is("POST"));
        assertThat(execute, is(notNullValue()));
    }

    @Test
    public void shouldAddParameters() throws Exception {
        CustomRequest<TokenHolder> request = new CustomRequest<>(client, server.getBaseUrl(), "POST", tokenHolderType);
        Map mapValue = mock(Map.class);
        request.addParameter("key", "value");
        request.addParameter("map", mapValue);

        server.jsonResponse(AUTH_TOKENS, 200);
        request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();
        Map<String, Object> values = bodyFromRequest(recordedRequest);
        assertThat(values, hasEntry("key", "value"));
        assertThat(values, hasEntry("map", mapValue));
    }

    @Test
    public void shouldAddHeaders() throws Exception {
        CustomRequest<TokenHolder> request = new CustomRequest<>(client, server.getBaseUrl(), "POST", tokenHolderType);
        request.addParameter("non_empty", "body");
        request.addHeader("Extra-Info", "this is a test");
        request.addHeader("Authorization", "Bearer my_access_token");

        server.jsonResponse(AUTH_TOKENS, 200);
        request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest.getHeader("Extra-Info"), is("this is a test"));
        assertThat(recordedRequest.getHeader("Authorization"), is("Bearer my_access_token"));
    }

    @Test
    public void shouldNotOverrideContentTypeHeader() throws Exception {
        CustomRequest<TokenHolder> request = new CustomRequest<>(client, server.getBaseUrl(), "POST", tokenHolderType);
        request.addParameter("non_empty", "body");
        request.addHeader("Content-Type", "plaintext");

        server.jsonResponse(AUTH_TOKENS, 200);
        request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest.getHeader("Content-Type"), is("application/json"));
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
        CustomRequest<Void> request = new CustomRequest<>(client, server.getBaseUrl(), "GET", voidType);
        request.execute().getBody();
    }

    @Test
    public void shouldThrowOnBodyCreationFailure() throws Exception {
        ObjectMapper mapper = mock(ObjectMapper.class);
        when(mapper.writeValueAsBytes(any(Object.class))).thenThrow(JsonProcessingException.class);

        CustomRequest request = new CustomRequest<>(client, server.getBaseUrl(), "POST", mapper, voidType);
        request.addParameter("name", "value");
        exception.expect(Auth0Exception.class);
        exception.expectCause(Matchers.<Throwable>instanceOf(JsonProcessingException.class));
        exception.expectMessage("Couldn't create the request body.");
        request.execute().getBody();
    }

    @Test
    public void shouldParseSuccessfulResponse() throws Exception {
        CustomRequest<TokenHolder> request = new CustomRequest<>(client, server.getBaseUrl(), "GET", tokenHolderType);
        server.jsonResponse(AUTH_TOKENS, 200);
        TokenHolder response = request.execute().getBody();
        server.takeRequest();

        assertThat(response, is(notNullValue()));
        assertThat(response.getAccessToken(), not(emptyOrNullString()));
        assertThat(response.getIdToken(), not(emptyOrNullString()));
        assertThat(response.getRefreshToken(), not(emptyOrNullString()));
        assertThat(response.getTokenType(), not(emptyOrNullString()));
        assertThat(response.getExpiresIn(), is(notNullValue()));
    }

    @Test
    public void shouldThrowOnParseInvalidSuccessfulResponse() throws Exception {
        CustomRequest<List> request = new CustomRequest<>(client, server.getBaseUrl(), "GET", listType);
        server.jsonResponse(AUTH_TOKENS, 200);
        Exception exception = null;
        try {
            request.execute().getBody();
            server.takeRequest();
        } catch (Exception e) {
            exception = e;
        }
        assertThat(exception, is(notNullValue()));
        assertThat(exception, is(instanceOf(APIException.class)));
        assertThat(exception.getCause(), is(instanceOf(JsonMappingException.class)));
        assertThat(exception.getMessage(), is("Request failed with status code 200: Failed to parse the response body."));
        APIException authException = (APIException) exception;
        assertThat(authException.getDescription(), is("Failed to parse the response body."));
        assertThat(authException.getError(), is(nullValue()));
        assertThat(authException.getStatusCode(), is(200));
    }

    @Test
    public void shouldParseJSONErrorResponseWithErrorDescription() throws Exception {
        CustomRequest<List> request = new CustomRequest<>(client, server.getBaseUrl(), "GET", listType);
        server.jsonResponse(AUTH_ERROR_WITH_ERROR_DESCRIPTION, 400);
        Exception exception = null;
        try {
            request.execute().getBody();
            server.takeRequest();
        } catch (Exception e) {
            exception = e;
        }
        assertThat(exception, is(notNullValue()));
        assertThat(exception, is(instanceOf(APIException.class)));
        assertThat(exception.getCause(), is(nullValue()));
        assertThat(exception.getMessage(), is("Request failed with status code 400: the connection was not found"));
        APIException authException = (APIException) exception;
        assertThat(authException.getDescription(), is("the connection was not found"));
        assertThat(authException.getError(), is("invalid_request"));
        assertThat(authException.getStatusCode(), is(400));
    }

    @Test
    public void shouldParseJSONErrorResponseWithError() throws Exception {
        CustomRequest<List> request = new CustomRequest<>(client, server.getBaseUrl(), "GET", listType);
        server.jsonResponse(AUTH_ERROR_WITH_ERROR, 400);
        Exception exception = null;
        try {
            request.execute().getBody();
            server.takeRequest();
        } catch (Exception e) {
            exception = e;
        }
        assertThat(exception, is(notNullValue()));
        assertThat(exception, is(instanceOf(APIException.class)));
        assertThat(exception.getCause(), is(nullValue()));
        assertThat(exception.getMessage(), is("Request failed with status code 400: missing username for Username-Password-Authentication connection with requires_username enabled"));
        APIException authException = (APIException) exception;
        assertThat(authException.getDescription(), is("missing username for Username-Password-Authentication connection with requires_username enabled"));
        assertThat(authException.getError(), is("missing username for Username-Password-Authentication connection with requires_username enabled"));
        assertThat(authException.getStatusCode(), is(400));
    }

    @SuppressWarnings("RedundantCast")
    @Test
    public void shouldParseJSONErrorResponseWithDescriptionAndExtraProperties() throws Exception {
        CustomRequest<List> request = new CustomRequest<>(client, server.getBaseUrl(), "GET", listType);
        server.jsonResponse(AUTH_ERROR_WITH_DESCRIPTION_AND_EXTRA_PROPERTIES, 400);
        Exception exception = null;
        try {
            request.execute().getBody();
            server.takeRequest();
        } catch (Exception e) {
            exception = e;
        }
        assertThat(exception, is(notNullValue()));
        assertThat(exception, is(instanceOf(APIException.class)));
        assertThat(exception.getCause(), is(nullValue()));
        assertThat(exception.getMessage(), is("Request failed with status code 400: Multifactor authentication required"));
        APIException authException = (APIException) exception;
        assertThat(authException.getDescription(), is("Multifactor authentication required"));
        assertThat(authException.getError(), is("mfa_required"));
        assertThat(authException.getValue("mfa_token"), is((Object) "Fe26...Ha"));
        assertThat(authException.getValue("non_existing_key"), is(nullValue()));
        assertThat(authException.getStatusCode(), is(400));
    }

    @Test
    public void shouldParseJSONErrorResponseWithDescription() throws Exception {
        CustomRequest<List> request = new CustomRequest<>(client, server.getBaseUrl(), "GET", listType);
        server.jsonResponse(AUTH_ERROR_WITH_DESCRIPTION, 400);
        Exception exception = null;
        try {
            request.execute().getBody();
            server.takeRequest();
        } catch (Exception e) {
            exception = e;
        }
        assertThat(exception, is(notNullValue()));
        assertThat(exception, is(instanceOf(APIException.class)));
        assertThat(exception.getCause(), is(nullValue()));
        assertThat(exception.getMessage(), is("Request failed with status code 400: The user already exists."));
        APIException authException = (APIException) exception;
        assertThat(authException.getDescription(), is("The user already exists."));
        assertThat(authException.getError(), is("user_exists"));
        assertThat(authException.getStatusCode(), is(400));
    }

    @Test
    public void shouldParseJSONErrorResponseWithMessage() throws Exception {
        CustomRequest<List> request = new CustomRequest<>(client, server.getBaseUrl(), "GET", listType);
        server.jsonResponse(MGMT_ERROR_WITH_MESSAGE, 400);
        Exception exception = null;
        try {
            request.execute().getBody();
            server.takeRequest();
        } catch (Exception e) {
            exception = e;
        }
        assertThat(exception, is(notNullValue()));
        assertThat(exception, is(instanceOf(APIException.class)));
        assertThat(exception.getCause(), is(nullValue()));
        assertThat(exception.getMessage(), is("Request failed with status code 400: Query validation error: 'String 'invalid_field' does not match pattern. Must be a comma separated list of the following values: allowed_logout_urls,change_password."));
        APIException authException = (APIException) exception;
        assertThat(authException.getDescription(), is("Query validation error: 'String 'invalid_field' does not match pattern. Must be a comma separated list of the following values: allowed_logout_urls,change_password."));
        assertThat(authException.getError(), is("invalid_query_string"));
        assertThat(authException.getStatusCode(), is(400));
    }

    @Test
    public void shouldParsePlainTextErrorResponse() throws Exception {
        CustomRequest<List> request = new CustomRequest<>(client, server.getBaseUrl(), "GET", listType);
        server.textResponse(AUTH_ERROR_PLAINTEXT, 400);
        Exception exception = null;
        try {
            request.execute().getBody();
            server.takeRequest();
        } catch (Exception e) {
            exception = e;
        }
        assertThat(exception, is(notNullValue()));
        assertThat(exception, is(instanceOf(APIException.class)));
        assertThat(exception.getCause(), is(instanceOf(JsonParseException.class)));
        assertThat(exception.getMessage(), is("Request failed with status code 400: A plain-text error response"));
        APIException authException = (APIException) exception;
        assertThat(authException.getDescription(), is("A plain-text error response"));
        assertThat(authException.getError(), is(nullValue()));
        assertThat(authException.getValue("non_existing_key"), is(nullValue()));
        assertThat(authException.getStatusCode(), is(400));
    }

    @Test
    public void shouldParseRateLimitsHeaders() {
        CustomRequest<List> request = new CustomRequest<>(client, server.getBaseUrl(), "GET", listType);
        server.rateLimitReachedResponse(100, 10, 5);
        Exception exception = null;
        try {
            request.execute().getBody();
            server.takeRequest();
        } catch (Exception e) {
            exception = e;
        }
        assertThat(exception, is(notNullValue()));
        assertThat(exception, is(instanceOf(RateLimitException.class)));
        assertThat(exception.getCause(), is(nullValue()));
        assertThat(exception.getMessage(), is("Request failed with status code 429: Rate limit reached"));
        RateLimitException rateLimitException = (RateLimitException) exception;
        assertThat(rateLimitException.getDescription(), is("Rate limit reached"));
        assertThat(rateLimitException.getError(), is(nullValue()));
        assertThat(rateLimitException.getValue("non_existing_key"), is(nullValue()));
        assertThat(rateLimitException.getStatusCode(), is(429));
        assertThat(rateLimitException.getLimit(), is(100L));
        assertThat(rateLimitException.getRemaining(), is(10L));
        assertThat(rateLimitException.getReset(), is(5L));
    }

    @Test
    public void shouldDefaultRateLimitsHeadersWhenMissing() {
        CustomRequest<List> request = new CustomRequest<>(client, server.getBaseUrl(), "GET", listType);
        server.rateLimitReachedResponse(-1, -1, -1);
        Exception exception = null;
        try {
            request.execute().getBody();
            server.takeRequest();
        } catch (Exception e) {
            exception = e;
        }
        assertThat(exception, is(notNullValue()));
        assertThat(exception, is(instanceOf(RateLimitException.class)));
        assertThat(exception.getCause(), is(nullValue()));
        assertThat(exception.getMessage(), is("Request failed with status code 429: Rate limit reached"));
        RateLimitException rateLimitException = (RateLimitException) exception;
        assertThat(rateLimitException.getDescription(), is("Rate limit reached"));
        assertThat(rateLimitException.getError(), is(nullValue()));
        assertThat(rateLimitException.getValue("non_existing_key"), is(nullValue()));
        assertThat(rateLimitException.getStatusCode(), is(429));
        assertThat(rateLimitException.getLimit(), is(-1L));
        assertThat(rateLimitException.getRemaining(), is(-1L));
        assertThat(rateLimitException.getReset(), is(-1L));
    }

}

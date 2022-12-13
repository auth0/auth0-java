package com.auth0.net;

import com.auth0.client.MockServer;
import com.auth0.client.mgmt.TokenProvider;
import com.auth0.exception.APIException;
import com.auth0.exception.Auth0Exception;
import com.auth0.exception.RateLimitException;
import com.auth0.json.auth.TokenHolder;
import com.auth0.net.client.Auth0HttpClient;
import com.auth0.net.client.DefaultHttpClient;
import com.auth0.net.client.HttpMethod;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.RecordedRequest;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static com.auth0.client.MockServer.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CustomRequestTest {
    private MockServer server;
    private Auth0HttpClient client;

    @SuppressWarnings("deprecation")
    @Rule
    public ExpectedException exception = ExpectedException.none();
    private TypeReference<TokenHolder> tokenHolderType;
    private TypeReference<List> listType;
    private TypeReference<Void> voidType;

    private TokenProvider tokenProvider;

    @Before
    public void setUp() throws Exception {
        server = new MockServer();
        client = DefaultHttpClient.newBuilder().withMaxRetries(0).build();
        tokenHolderType = new TypeReference<TokenHolder>() {
        };
        listType = new TypeReference<List>() {
        };
        voidType = new TypeReference<Void>() {
        };
        tokenProvider = new TokenProvider() {
            @Override
            public String getToken() throws Auth0Exception {
                return "Bearer xyz";
            }

            @Override
            public CompletableFuture<String> getTokenAsync() {
                return CompletableFuture.completedFuture("Bearer xyz");
            }
        };
//        tokenProvider = () -> "Bearer xyz";
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void shouldCreateGETRequest() throws Exception {
        CustomRequest<TokenHolder> request = new CustomRequest<>(client, tokenProvider, server.getBaseUrl(), HttpMethod.GET, tokenHolderType);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(AUTH_TOKENS, 200);
        TokenHolder execute = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();
        assertThat(recordedRequest.getMethod(), is(HttpMethod.GET.toString()));
        assertThat(execute, is(notNullValue()));
    }

    @Test
    public void shouldCreatePOSTRequest() throws Exception {
        CustomRequest<TokenHolder> request = new CustomRequest<>(client, tokenProvider, server.getBaseUrl(), HttpMethod.POST, tokenHolderType);
        assertThat(request, is(notNullValue()));
        request.addParameter("non_empty", "body");

        server.jsonResponse(AUTH_TOKENS, 200);
        TokenHolder execute = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();
        assertThat(recordedRequest.getMethod(), is(HttpMethod.POST.toString()));
        assertThat(execute, is(notNullValue()));
    }

    @Test
    public void shouldAddParameters() throws Exception {
        CustomRequest<TokenHolder> request = new CustomRequest<>(client, tokenProvider, server.getBaseUrl(), HttpMethod.POST, tokenHolderType);
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
        CustomRequest<TokenHolder> request = new CustomRequest<>(client, tokenProvider, server.getBaseUrl(), HttpMethod.POST, tokenHolderType);
        request.addParameter("non_empty", "body");
        request.addHeader("Extra-Info", "this is a test");
        request.addHeader("Authorization", "Bearer my_access_token");

        server.jsonResponse(AUTH_TOKENS, 200);
        request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest.getHeader("Extra-Info"), is("this is a test"));
//        assertThat(recordedRequest.getHeader("Authorization"), is("Bearer my_access_token"));
    }

    @Test
    public void shouldNotOverrideContentTypeHeader() throws Exception {
        CustomRequest<TokenHolder> request = new CustomRequest<>(client, tokenProvider, server.getBaseUrl(), HttpMethod.POST, tokenHolderType);
        request.addParameter("non_empty", "body");
        request.addHeader("Content-Type", "plaintext");

        server.jsonResponse(AUTH_TOKENS, 200);
        request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest.getHeader("Content-Type"), is("application/json"));
    }

    @Test
    public void shouldThrowOnBodyCreationFailure() throws Exception {
        ObjectMapper mapper = mock(ObjectMapper.class);
        when(mapper.writeValueAsBytes(any(Object.class))).thenThrow(JsonProcessingException.class);

        CustomRequest request = new CustomRequest<>(client, tokenProvider, server.getBaseUrl(), HttpMethod.POST, mapper, voidType);
        request.addParameter("name", "value");
        exception.expect(Auth0Exception.class);
        exception.expectCause(Matchers.<Throwable>instanceOf(JsonProcessingException.class));
        exception.expectMessage("Couldn't create the request body.");
        request.execute().getBody();
    }

    @Test
    public void shouldParseSuccessfulResponse() throws Exception {
        CustomRequest<TokenHolder> request = new CustomRequest<>(client, tokenProvider, server.getBaseUrl(), HttpMethod.GET, tokenHolderType);
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
        CustomRequest<List> request = new CustomRequest<>(client, tokenProvider, server.getBaseUrl(), HttpMethod.GET, listType);
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
        CustomRequest<List> request = new CustomRequest<>(client, tokenProvider, server.getBaseUrl(), HttpMethod.GET, listType);
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
        CustomRequest<List> request = new CustomRequest<>(client, tokenProvider, server.getBaseUrl(), HttpMethod.GET, listType);
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
        CustomRequest<List> request = new CustomRequest<>(client, tokenProvider, server.getBaseUrl(), HttpMethod.GET, listType);
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
        CustomRequest<List> request = new CustomRequest<>(client, tokenProvider, server.getBaseUrl(), HttpMethod.GET, listType);
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
        CustomRequest<List> request = new CustomRequest<>(client, tokenProvider, server.getBaseUrl(), HttpMethod.GET, listType);
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
        CustomRequest<List> request = new CustomRequest<>(client, tokenProvider, server.getBaseUrl(), HttpMethod.GET, listType);
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
        CustomRequest<List> request = new CustomRequest<>(client, tokenProvider, server.getBaseUrl(), HttpMethod.GET, listType);
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
        CustomRequest<List> request = new CustomRequest<>(client, tokenProvider, server.getBaseUrl(), HttpMethod.GET, listType);
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

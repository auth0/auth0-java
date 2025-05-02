package com.auth0.net;

import com.auth0.AssertsUtil;
import com.auth0.client.MockServer;
import com.auth0.client.mgmt.TokenProvider;
import com.auth0.exception.APIException;
import com.auth0.exception.Auth0Exception;
import com.auth0.exception.RateLimitException;
import com.auth0.json.auth.TokenHolder;
import com.auth0.net.client.*;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.RecordedRequest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static com.auth0.AssertsUtil.verifyThrows;
import static com.auth0.client.MockServer.*;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

// FIXME: These test require mocking of the final class okhttp3.Response. To do so
//  an opt-in incubating Mockito feature is used, for more information see:
//  https://github.com/mockito/mockito/wiki/What%27s-new-in-Mockito-2#mock-the-unmockable-opt-in-mocking-of-final-classesmethods
//  This issue can be tracked to see if/when this feature will become standard for Mockito (perhaps Mockito 4):
//  https://github.com/mockito/mockito/issues/1728
public class BaseRequestTest {
    private MockServer server;
    private Auth0HttpClient client;
    private TokenProvider tokenProvider;

    private TypeReference<TokenHolder> tokenHolderType;
    private TypeReference<List> listType;
    private TypeReference<Void> voidType;

    @BeforeEach
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
            public String getToken() {
                return "xyz";
            }
            @Override
            public CompletableFuture<String> getTokenAsync() {
                return CompletableFuture.completedFuture("xyz");
            }
        };
    }

    @AfterEach
    public void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void handlesIOException() throws Exception {
        DefaultHttpClient client = mock(DefaultHttpClient.class);
        when(client.sendRequest(any())).thenThrow(IOException.class);

        BaseRequest<String> req = new BaseRequest<String>(client, tokenProvider, "", HttpMethod.GET, new TypeReference<String>() {
        }) {
            @Override
            public BaseRequest<String> addHeader(String name, String value) {
                return null;
            }

            @Override
            public BaseRequest<String> addParameter(String name, Object value) {
                return null;
            }

            @Override
            public BaseRequest<String> setBody(Object body) {
                return null;
            }

            @Override
            protected Auth0HttpRequest createRequest(String apiToken) {
                return null;
            }

            @Override
            protected String parseResponseBody(Auth0HttpResponse response) {
                return null;
            }
        };

        Auth0Exception exception = verifyThrows(Auth0Exception.class, req::execute);
        assertThat(exception.getCause(), is(instanceOf(IOException.class)));
    }

    @Test
    public void asyncHandlesIOExceptionWhenCreatingRequest() {
        DefaultHttpClient client = DefaultHttpClient.newBuilder().build();

        BaseRequest<String> req = new BaseRequest<String>(client, tokenProvider, "", HttpMethod.GET, new TypeReference<String>() {
        }) {
            @Override
            public BaseRequest<String> addHeader(String name, String value) {
                return null;
            }

            @Override
            public BaseRequest<String> addParameter(String name, Object value) {
                return null;
            }

            @Override
            public BaseRequest<String> setBody(Object body) {
                return null;
            }

            @Override
            protected Auth0HttpRequest createRequest(String apiToken) throws Auth0Exception {
                throw new Auth0Exception("error", new IOException("boom"));
            }

            @Override
            protected String parseResponseBody(Auth0HttpResponse response) {
                return null;
            }
        };

        Auth0HttpRequest a0Request = Auth0HttpRequest.newBuilder("https://foo.com", HttpMethod.GET).build();
        CompletableFuture<Response<String>> future = req.executeAsync();
        ExecutionException e = verifyThrows(ExecutionException.class, future::get);
        assertThat(e.getCause(), is(instanceOf(IOException.class)));
    }

    @Test
    public void shouldCreateGETRequest() throws Exception {
        BaseRequest<TokenHolder> request = new BaseRequest<>(client, tokenProvider, server.getBaseUrl(), HttpMethod.GET, tokenHolderType);
        assertThat(request, Matchers.is(notNullValue()));

        server.jsonResponse(AUTH_TOKENS, 200);
        TokenHolder execute = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();
        assertThat(recordedRequest.getMethod(), Matchers.is(HttpMethod.GET.toString()));
        assertThat(execute, Matchers.is(notNullValue()));
    }

    @Test
    public void shouldCreatePOSTRequest() throws Exception {
        BaseRequest<TokenHolder> request = new BaseRequest<>(client, tokenProvider, server.getBaseUrl(),  HttpMethod.POST, tokenHolderType);
        assertThat(request, Matchers.is(notNullValue()));
        request.addParameter("non_empty", "body");

        server.jsonResponse(AUTH_TOKENS, 200);
        TokenHolder execute = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();
        assertThat(recordedRequest.getMethod(), Matchers.is(HttpMethod.POST.toString()));
        assertThat(execute, Matchers.is(notNullValue()));
    }

    @Test
    public void shouldAddParameters() throws Exception {
        BaseRequest<TokenHolder> request = new BaseRequest<>(client, tokenProvider, server.getBaseUrl(),  HttpMethod.POST, tokenHolderType);
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
        BaseRequest<TokenHolder> request = new BaseRequest<>(client, tokenProvider, server.getBaseUrl(),  HttpMethod.POST, tokenHolderType);
        request.addParameter("non_empty", "body");
        request.addHeader("Extra-Info", "this is a test");
        request.addHeader("Authorization", "Bearer my_access_token");
        request.addHeader("X-Client-Quota", getTokenQuotaString());
        request.addHeader("X-Organization-Quota", getTokenQuotaString());

        server.jsonResponse(AUTH_TOKENS, 200);
        request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest.getHeader("Extra-Info"), Matchers.is("this is a test"));
        // auth header will be done on the request handling
        assertThat(recordedRequest.getHeader("Authorization"), Matchers.is("Bearer xyz"));
    }

    @Test
    public void shouldNotOverrideContentTypeHeader() throws Exception {
        BaseRequest<TokenHolder> request = new BaseRequest<>(client, tokenProvider, server.getBaseUrl(),  HttpMethod.POST, tokenHolderType);
        request.addParameter("non_empty", "body");
        request.addHeader("Content-Type", "plaintext");

        server.jsonResponse(AUTH_TOKENS, 200);
        request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest.getHeader("Content-Type"), Matchers.is("application/json"));
    }

    @Test
    public void shouldThrowOnBodyCreationFailure() throws Exception {
        ObjectMapper mapper = mock(ObjectMapper.class);
        when(mapper.writeValueAsBytes(any(Object.class))).thenThrow(JsonProcessingException.class);

        BaseRequest request = new BaseRequest<>(client, tokenProvider, server.getBaseUrl(),  HttpMethod.POST, mapper, voidType);
        request.addParameter("name", "value");

        Auth0Exception e = AssertsUtil.verifyThrows(Auth0Exception.class,
            () -> request.execute().getBody(),
            "Couldn't create the request body.");
        assertThat(e.getCause(), Matchers.instanceOf(JsonProcessingException.class));
    }

    @Test
    public void shouldParseSuccessfulResponse() throws Exception {
        BaseRequest<TokenHolder> request = new BaseRequest<>(client, tokenProvider, server.getBaseUrl(),  HttpMethod.GET, tokenHolderType);
        server.jsonResponse(AUTH_TOKENS, 200);
        TokenHolder response = request.execute().getBody();
        server.takeRequest();

        assertThat(response, Matchers.is(notNullValue()));
        assertThat(response.getAccessToken(), not(emptyOrNullString()));
        assertThat(response.getIdToken(), not(emptyOrNullString()));
        assertThat(response.getRefreshToken(), not(emptyOrNullString()));
        assertThat(response.getTokenType(), not(emptyOrNullString()));
        assertThat(response.getExpiresIn(), Matchers.is(notNullValue()));
    }

    @Test
    public void shouldThrowOnParseInvalidSuccessfulResponse() throws Exception {
        BaseRequest<List> request = new BaseRequest<>(client, tokenProvider, server.getBaseUrl(),  HttpMethod.GET, listType);
        server.jsonResponse(AUTH_TOKENS, 200);
        Exception exception = null;
        try {
            request.execute().getBody();
            server.takeRequest();
        } catch (Exception e) {
            exception = e;
        }
        assertThat(exception, Matchers.is(notNullValue()));
        assertThat(exception, Matchers.is(Matchers.instanceOf(APIException.class)));
        assertThat(exception.getCause(), Matchers.is(Matchers.instanceOf(JsonMappingException.class)));
        assertThat(exception.getMessage(), Matchers.is("Request failed with status code 200: Failed to parse the response body."));
        APIException authException = (APIException) exception;
        assertThat(authException.getDescription(), Matchers.is("Failed to parse the response body."));
        assertThat(authException.getError(), Matchers.is(nullValue()));
        assertThat(authException.getStatusCode(), Matchers.is(200));
    }

    @Test
    public void shouldParseJSONErrorResponseWithErrorDescription() throws Exception {
        BaseRequest<List> request = new BaseRequest<>(client, tokenProvider, server.getBaseUrl(),  HttpMethod.GET, listType);
        server.jsonResponse(AUTH_ERROR_WITH_ERROR_DESCRIPTION, 400);
        Exception exception = null;
        try {
            request.execute().getBody();
            server.takeRequest();
        } catch (Exception e) {
            exception = e;
        }
        assertThat(exception, Matchers.is(notNullValue()));
        assertThat(exception, Matchers.is(Matchers.instanceOf(APIException.class)));
        assertThat(exception.getCause(), Matchers.is(nullValue()));
        assertThat(exception.getMessage(), Matchers.is("Request failed with status code 400: the connection was not found"));
        APIException authException = (APIException) exception;
        assertThat(authException.getDescription(), Matchers.is("the connection was not found"));
        assertThat(authException.getError(), Matchers.is("invalid_request"));
        assertThat(authException.getStatusCode(), Matchers.is(400));
    }

    @Test
    public void shouldParseJSONErrorResponseWithError() throws Exception {
        BaseRequest<List> request = new BaseRequest<>(client, tokenProvider, server.getBaseUrl(),  HttpMethod.GET, listType);
        server.jsonResponse(AUTH_ERROR_WITH_ERROR, 400);
        Exception exception = null;
        try {
            request.execute().getBody();
            server.takeRequest();
        } catch (Exception e) {
            exception = e;
        }
        assertThat(exception, Matchers.is(notNullValue()));
        assertThat(exception, Matchers.is(Matchers.instanceOf(APIException.class)));
        assertThat(exception.getCause(), Matchers.is(nullValue()));
        assertThat(exception.getMessage(), Matchers.is("Request failed with status code 400: missing username for Username-Password-Authentication connection with requires_username enabled"));
        APIException authException = (APIException) exception;
        assertThat(authException.getDescription(), Matchers.is("missing username for Username-Password-Authentication connection with requires_username enabled"));
        assertThat(authException.getError(), Matchers.is("missing username for Username-Password-Authentication connection with requires_username enabled"));
        assertThat(authException.getStatusCode(), Matchers.is(400));
    }

    @SuppressWarnings("RedundantCast")
    @Test
    public void shouldParseJSONErrorResponseWithDescriptionAndExtraProperties() throws Exception {
        BaseRequest<List> request = new BaseRequest<>(client, tokenProvider, server.getBaseUrl(),  HttpMethod.GET, listType);
        server.jsonResponse(AUTH_ERROR_WITH_DESCRIPTION_AND_EXTRA_PROPERTIES, 400);
        Exception exception = null;
        try {
            request.execute().getBody();
            server.takeRequest();
        } catch (Exception e) {
            exception = e;
        }
        assertThat(exception, Matchers.is(notNullValue()));
        assertThat(exception, Matchers.is(Matchers.instanceOf(APIException.class)));
        assertThat(exception.getCause(), Matchers.is(nullValue()));
        assertThat(exception.getMessage(), Matchers.is("Request failed with status code 400: Multifactor authentication required"));
        APIException authException = (APIException) exception;
        assertThat(authException.getDescription(), Matchers.is("Multifactor authentication required"));
        assertThat(authException.getError(), Matchers.is("mfa_required"));
        assertThat(authException.getValue("mfa_token"), Matchers.is((Object) "Fe26...Ha"));
        assertThat(authException.getValue("non_existing_key"), Matchers.is(nullValue()));
        assertThat(authException.getStatusCode(), Matchers.is(400));
    }

    @Test
    public void shouldParseJSONErrorResponseWithDescription() throws Exception {
        BaseRequest<List> request = new BaseRequest<>(client, tokenProvider, server.getBaseUrl(),  HttpMethod.GET, listType);
        server.jsonResponse(AUTH_ERROR_WITH_DESCRIPTION, 400);
        Exception exception = null;
        try {
            request.execute().getBody();
            server.takeRequest();
        } catch (Exception e) {
            exception = e;
        }
        assertThat(exception, Matchers.is(notNullValue()));
        assertThat(exception, Matchers.is(Matchers.instanceOf(APIException.class)));
        assertThat(exception.getCause(), Matchers.is(nullValue()));
        assertThat(exception.getMessage(), Matchers.is("Request failed with status code 400: The user already exists."));
        APIException authException = (APIException) exception;
        assertThat(authException.getDescription(), Matchers.is("The user already exists."));
        assertThat(authException.getError(), Matchers.is("user_exists"));
        assertThat(authException.getStatusCode(), Matchers.is(400));
    }

    @Test
    public void shouldParseJSONErrorResponseWithMessage() throws Exception {
        BaseRequest<List> request = new BaseRequest<>(client, tokenProvider, server.getBaseUrl(),  HttpMethod.GET, listType);
        server.jsonResponse(MGMT_ERROR_WITH_MESSAGE, 400);
        Exception exception = null;
        try {
            request.execute().getBody();
            server.takeRequest();
        } catch (Exception e) {
            exception = e;
        }
        assertThat(exception, Matchers.is(notNullValue()));
        assertThat(exception, Matchers.is(Matchers.instanceOf(APIException.class)));
        assertThat(exception.getCause(), Matchers.is(nullValue()));
        assertThat(exception.getMessage(), Matchers.is("Request failed with status code 400: Query validation error: 'String 'invalid_field' does not match pattern. Must be a comma separated list of the following values: allowed_logout_urls,change_password."));
        APIException authException = (APIException) exception;
        assertThat(authException.getDescription(), Matchers.is("Query validation error: 'String 'invalid_field' does not match pattern. Must be a comma separated list of the following values: allowed_logout_urls,change_password."));
        assertThat(authException.getError(), Matchers.is("invalid_query_string"));
        assertThat(authException.getStatusCode(), Matchers.is(400));
    }

    @Test
    public void shouldParsePlainTextErrorResponse() throws Exception {
        BaseRequest<List> request = new BaseRequest<>(client, tokenProvider, server.getBaseUrl(),  HttpMethod.GET, listType);
        server.textResponse(AUTH_ERROR_PLAINTEXT, 400);
        Exception exception = null;
        try {
            request.execute().getBody();
            server.takeRequest();
        } catch (Exception e) {
            exception = e;
        }
        assertThat(exception, Matchers.is(notNullValue()));
        assertThat(exception, Matchers.is(Matchers.instanceOf(APIException.class)));
        assertThat(exception.getCause(), Matchers.is(Matchers.instanceOf(JsonParseException.class)));
        assertThat(exception.getMessage(), Matchers.is("Request failed with status code 400: A plain-text error response"));
        APIException authException = (APIException) exception;
        assertThat(authException.getDescription(), Matchers.is("A plain-text error response"));
        assertThat(authException.getError(), Matchers.is(nullValue()));
        assertThat(authException.getValue("non_existing_key"), Matchers.is(nullValue()));
        assertThat(authException.getStatusCode(), Matchers.is(400));
    }

    @Test
    public void shouldParseRateLimitException() throws Exception {
        BaseRequest<List> request = new BaseRequest<>(client, tokenProvider, server.getBaseUrl(),  HttpMethod.GET, listType);
        server.rateLimitReachedResponse(100, 10, 5, RATE_LIMIT_ERROR);
        Exception exception = null;
        try {
            request.execute().getBody();
            server.takeRequest();
        } catch (Exception e) {
            exception = e;
        }
        assertThat(exception, Matchers.is(notNullValue()));
        assertThat(exception, Matchers.is(Matchers.instanceOf(RateLimitException.class)));
        assertThat(exception.getCause(), Matchers.is(nullValue()));
        assertThat(exception.getMessage(), Matchers.is("Request failed with status code 429: Global limit has been reached"));
        RateLimitException rateLimitException = (RateLimitException) exception;
        assertThat(rateLimitException.getDescription(), Matchers.is("Global limit has been reached"));
        assertThat(rateLimitException.getError(), Matchers.is("too_many_requests"));
        assertThat(rateLimitException.getValue("non_existing_key"), Matchers.is(nullValue()));
        assertThat(rateLimitException.getStatusCode(), Matchers.is(429));
        assertThat(rateLimitException.getLimit(), Matchers.is(100L));
        assertThat(rateLimitException.getRemaining(), Matchers.is(10L));
        assertThat(rateLimitException.getReset(), Matchers.is(5L));
    }

    @Test
    public void shouldParseRateLimitExceptionWithZeroRemaining() throws Exception {
        BaseRequest<List> request = new BaseRequest<>(client, tokenProvider, server.getBaseUrl(),  HttpMethod.GET, listType);
        server.rateLimitReachedResponse(100, 0, 5, RATE_LIMIT_ERROR, getTokenQuotaString(), getTokenQuotaString());
        Exception exception = null;
        try {
            request.execute().getBody();
            server.takeRequest();
        } catch (Exception e) {
            exception = e;
        }
        assertThat(exception, Matchers.is(notNullValue()));
        assertThat(exception, Matchers.is(Matchers.instanceOf(RateLimitException.class)));
        assertThat(exception.getCause(), Matchers.is(nullValue()));
        assertThat(exception.getMessage(), Matchers.is("Request failed with status code 429: Global limit has been reached"));
        RateLimitException rateLimitException = (RateLimitException) exception;
        assertThat(rateLimitException.getDescription(), Matchers.is("Global limit has been reached"));
        assertThat(rateLimitException.getError(), Matchers.is("too_many_requests"));
        assertThat(rateLimitException.getValue("non_existing_key"), Matchers.is(nullValue()));
        assertThat(rateLimitException.getStatusCode(), Matchers.is(429));
        assertThat(rateLimitException.getLimit(), Matchers.is(100L));
        assertThat(rateLimitException.getRemaining(), Matchers.is(0L));
        assertThat(rateLimitException.getReset(), Matchers.is(5L));
        assertThat(rateLimitException.getClientQuotaLimit().getPerDay().getQuota(), Matchers.is(getTokenQuota().getPerDay().getQuota()));
        assertThat(rateLimitException.getOrganizationQuotaLimit().getPerDay().getQuota(), Matchers.is(getTokenQuota().getPerDay().getQuota()));
    }

    @Test
    public void shouldDefaultRateLimitsHeadersWhenMissing() throws Exception {
        BaseRequest<List> request = new BaseRequest<>(client, tokenProvider, server.getBaseUrl(),  HttpMethod.GET, listType);
        server.rateLimitReachedResponse(-1, -1, -1);
        Exception exception = null;
        try {
            request.execute().getBody();
            server.takeRequest();
        } catch (Exception e) {
            exception = e;
        }
        assertThat(exception, Matchers.is(notNullValue()));
        assertThat(exception, Matchers.is(Matchers.instanceOf(RateLimitException.class)));
        assertThat(exception.getCause(), Matchers.is(nullValue()));
        assertThat(exception.getMessage(), Matchers.is("Request failed with status code 429: Rate limit reached"));
        RateLimitException rateLimitException = (RateLimitException) exception;
        assertThat(rateLimitException.getDescription(), Matchers.is("Rate limit reached"));
        assertThat(rateLimitException.getError(), Matchers.is(nullValue()));
        assertThat(rateLimitException.getValue("non_existing_key"), Matchers.is(nullValue()));
        assertThat(rateLimitException.getStatusCode(), Matchers.is(429));
        assertThat(rateLimitException.getLimit(), Matchers.is(-1L));
        assertThat(rateLimitException.getRemaining(), Matchers.is(-1L));
        assertThat(rateLimitException.getReset(), Matchers.is(-1L));
        assertThat(rateLimitException.getClientQuotaLimit(), Matchers.is(nullValue()));
        assertThat(rateLimitException.getOrganizationQuotaLimit(), Matchers.is(nullValue()));
    }

    private TokenQuotaBucket getTokenQuota() {
        TokenQuotaLimit perHourLimit = new TokenQuotaLimit(100, 80, 3600);
        TokenQuotaLimit perDayLimit = new TokenQuotaLimit(100, 90, 86400);
        return new TokenQuotaBucket(perHourLimit, perDayLimit);
    }

    public String getTokenQuotaString() {
        TokenQuotaLimit perHourLimit = new TokenQuotaLimit(100, 80, 3600);
        TokenQuotaLimit perDayLimit = new TokenQuotaLimit(100, 90, 86400);

        StringBuilder builder = new StringBuilder();

        builder.append(String.format("b=per_hour;q=%d;r=%d;t=%d",
            perHourLimit.getQuota(), perHourLimit.getRemaining(), perHourLimit.getTime()));

        if (builder.length() > 0) {
            builder.append(",");
        }
        builder.append(String.format("b=per_day;q=%d;r=%d;t=%d",
            perDayLimit.getQuota(), perDayLimit.getRemaining(), perDayLimit.getTime()));

        return builder.toString();
    }
}

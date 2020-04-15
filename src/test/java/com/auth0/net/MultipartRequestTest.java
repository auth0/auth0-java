package com.auth0.net;

import com.auth0.client.MockServer;
import com.auth0.exception.APIException;
import com.auth0.exception.Auth0Exception;
import com.auth0.exception.RateLimitException;
import com.auth0.json.auth.TokenHolder;
import com.auth0.net.multipart.FilePart;
import com.auth0.net.multipart.KeyValuePart;
import com.auth0.net.multipart.RecordedMultipartRequest;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.Call;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.mockwebserver.RecordedRequest;
import org.hamcrest.Matchers;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.UUID;

import static com.auth0.client.MockServer.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MultipartRequestTest {
    private MockServer server;
    private OkHttpClient client;

    @Rule
    public ExpectedException exception = ExpectedException.none();
    private TypeReference<TokenHolder> tokenHolderType;
    private TypeReference<List> listType;
    private TypeReference<Void> voidType;

    public static final String MULTIPART_FILE = "src/test/resources/mgmt/multipart-sample.json";

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
    public void shouldNotSupportGETMethod() throws Exception {
        exception.expect(instanceOf(IllegalArgumentException.class));
        exception.expectMessage("Multipart/form-data requests do not support the GET method.");
        MultipartRequest<TokenHolder> request = new MultipartRequest<>(client, server.getBaseUrl(), "GET", tokenHolderType);
    }

    @Test
    public void shouldCreatePOSTRequest() throws Exception {
        MultipartRequest<TokenHolder> request = new MultipartRequest<>(client, server.getBaseUrl(), "POST", tokenHolderType);
        assertThat(request, is(notNullValue()));
        request.addPart("non_empty", "body");

        server.jsonResponse(AUTH_TOKENS, 200);
        TokenHolder execute = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();
        Assert.assertThat(recordedRequest.getMethod(), is("POST"));
        Assert.assertThat(execute, is(notNullValue()));
    }

    @Test
    public void shouldAddMultipleParts() throws Exception {
        String boundary = UUID.randomUUID().toString();
        MultipartBody.Builder bodyBuilder = new MultipartBody.Builder(boundary);
        MultipartRequest<TokenHolder> request = new MultipartRequest<>(client, server.getBaseUrl(), "POST", new ObjectMapper(), tokenHolderType, bodyBuilder);

        File fileValue = new File(MULTIPART_FILE);
        request.addPart("keyName", "keyValue");
        request.addPart("jsonFile", fileValue, "text/json");

        server.jsonResponse(AUTH_TOKENS, 200);
        request.execute();
        RecordedRequest recordedRequest = server.takeRequest();
        RecordedMultipartRequest recordedMultipartRequest = new RecordedMultipartRequest(recordedRequest);
        assertThat(recordedMultipartRequest.getPartsCount(), is(2));
        assertThat(recordedMultipartRequest.getBoundary(), is(boundary));

        KeyValuePart formParam = recordedMultipartRequest.getKeyValuePart("keyName");
        assertThat(formParam, is(notNullValue()));
        assertThat(formParam.getValue(), is("keyValue"));

        FilePart jsonFile = recordedMultipartRequest.getFilePart("jsonFile");
        assertThat(jsonFile, is(notNullValue()));
        String utf8Contents = new String(Files.readAllBytes(fileValue.toPath()));
        assertThat(jsonFile.getContentType(), is("text/json"));
        assertThat(jsonFile.getFilename(), is("multipart-sample.json"));
        assertThat(jsonFile.getValue(), is(utf8Contents));
    }

    @Test
    public void shouldNotOverrideContentTypeHeader() throws Exception {
        MultipartBody.Builder bodyBuilder = new MultipartBody.Builder("5c49fdf2");
        MultipartRequest<TokenHolder> request = new MultipartRequest<>(client, server.getBaseUrl(), "POST", new ObjectMapper(), tokenHolderType, bodyBuilder);
        request.addPart("non_empty", "body");
        request.addHeader("Content-Type", "plaintext");

        server.jsonResponse(AUTH_TOKENS, 200);
        request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest.getHeader("Content-Type"), is("multipart/form-data; boundary=5c49fdf2"));
    }

    @Test
    public void shouldAddHeaders() throws Exception {
        MultipartRequest<TokenHolder> request = new MultipartRequest<>(client, server.getBaseUrl(), "POST", tokenHolderType);
        request.addPart("non_empty", "body");
        request.addHeader("Extra-Info", "this is a test");
        request.addHeader("Authorization", "Bearer my_access_token");

        server.jsonResponse(AUTH_TOKENS, 200);
        request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest.getHeader("Extra-Info"), is("this is a test"));
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
        MultipartRequest<Void> request = new MultipartRequest<>(client, server.getBaseUrl(), "POST", voidType);
        request.addPart("non_empty", "body");
        request.execute();
    }

    @Test
    public void shouldThrowOnBodyCreationFailure() throws Exception {
        Exception exception = null;
        try {
            MultipartRequest<Void> request = new MultipartRequest<>(client, server.getBaseUrl(), "POST", voidType);
            request.execute();
        } catch (Exception e) {
            exception = e;
        }
        assertThat(exception, is(notNullValue()));
        assertThat(exception, is(instanceOf(Auth0Exception.class)));
        assertThat(exception.getMessage(), is("Couldn't create the request body."));
        assertThat(exception.getCause(), is(instanceOf(IOException.class)));
        assertThat(exception.getCause().getMessage(), is("Cannot create multipart/form-data request body with zero parts."));
    }

    @Test
    public void shouldParseSuccessfulResponse() throws Exception {
        MultipartRequest<TokenHolder> request = new MultipartRequest<>(client, server.getBaseUrl(), "POST", tokenHolderType);
        request.addPart("non_empty", "body");
        server.jsonResponse(AUTH_TOKENS, 200);
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
        MultipartRequest<List> request = new MultipartRequest<>(client, server.getBaseUrl(), "POST", listType);
        request.addPart("non_empty", "body");
        server.jsonResponse(AUTH_TOKENS, 200);
        Exception exception = null;
        try {
            request.execute();
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
        MultipartRequest<List> request = new MultipartRequest<>(client, server.getBaseUrl(), "POST", listType);
        request.addPart("non_empty", "body");
        server.jsonResponse(AUTH_ERROR_WITH_ERROR_DESCRIPTION, 400);
        Exception exception = null;
        try {
            request.execute();
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
        MultipartRequest<List> request = new MultipartRequest<>(client, server.getBaseUrl(), "POST", listType);
        request.addPart("non_empty", "body");
        server.jsonResponse(AUTH_ERROR_WITH_ERROR, 400);
        Exception exception = null;
        try {
            request.execute();
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

    @Test
    public void shouldParseJSONErrorResponseWithDescriptionAndExtraProperties() throws Exception {
        MultipartRequest<List> request = new MultipartRequest<>(client, server.getBaseUrl(), "POST", listType);
        request.addPart("non_empty", "body");
        server.jsonResponse(AUTH_ERROR_WITH_DESCRIPTION_AND_EXTRA_PROPERTIES, 400);
        Exception exception = null;
        try {
            request.execute();
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
        assertThat(authException.getValue("mfa_token"), is("Fe26...Ha"));
        assertThat(authException.getValue("non_existing_key"), is(nullValue()));
        assertThat(authException.getStatusCode(), is(400));
    }

    @Test
    public void shouldParseJSONErrorResponseWithDescription() throws Exception {
        MultipartRequest<List> request = new MultipartRequest<>(client, server.getBaseUrl(), "POST", listType);
        request.addPart("non_empty", "body");
        server.jsonResponse(AUTH_ERROR_WITH_DESCRIPTION, 400);
        Exception exception = null;
        try {
            request.execute();
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
        MultipartRequest<List> request = new MultipartRequest<>(client, server.getBaseUrl(), "POST", listType);
        request.addPart("non_empty", "body");
        server.jsonResponse(MGMT_ERROR_WITH_MESSAGE, 400);
        Exception exception = null;
        try {
            request.execute();
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
        MultipartRequest<List> request = new MultipartRequest<>(client, server.getBaseUrl(), "POST", listType);
        request.addPart("non_empty", "body");
        server.textResponse(AUTH_ERROR_PLAINTEXT, 400);
        Exception exception = null;
        try {
            request.execute();
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
    public void shouldParseRateLimitsHeaders() throws Exception {
        MultipartRequest<List> request = new MultipartRequest<>(client, server.getBaseUrl(), "POST", listType);
        request.addPart("non_empty", "body");
        server.rateLimitReachedResponse(100, 10, 5);
        Exception exception = null;
        try {
            request.execute();
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
    public void shouldDefaultRateLimitsHeadersWhenMissing() throws Exception {
        MultipartRequest<List> request = new MultipartRequest<>(client, server.getBaseUrl(), "POST", listType);
        request.addPart("non_empty", "body");
        server.rateLimitReachedResponse(-1, -1, -1);
        Exception exception = null;
        try {
            request.execute();
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

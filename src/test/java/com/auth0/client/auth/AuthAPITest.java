package com.auth0.client.auth;

import com.auth0.client.MockServer;
import com.auth0.exception.APIException;
import com.auth0.json.auth.*;
import com.auth0.net.BaseRequest;
import com.auth0.net.Request;
import com.auth0.net.SignUpRequest;
import com.auth0.net.TokenRequest;
import com.auth0.net.client.Auth0HttpClient;
import com.auth0.net.client.Auth0HttpRequest;
import com.auth0.net.client.Auth0HttpResponse;
import com.auth0.net.client.HttpMethod;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.Rule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static com.auth0.AssertsUtil.verifyThrows;
import static com.auth0.client.MockServer.*;
import static com.auth0.client.RecordedRequestMatcher.hasHeader;
import static com.auth0.client.RecordedRequestMatcher.hasMethodAndPath;
import static com.auth0.client.UrlMatcher.hasQueryParameter;
import static com.auth0.client.UrlMatcher.isUrl;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.hamcrest.collection.IsMapContaining.hasKey;

public class AuthAPITest {

    private static final String DOMAIN = "domain.auth0.com";
    private static final String CLIENT_ID = "clientId";
    private static final String CLIENT_SECRET = "clientSecret";
    private static final String PASSWORD_STRENGTH_ERROR_RESPONSE_NONE = "src/test/resources/auth/password_strength_error_none.json";
    private static final String PASSWORD_STRENGTH_ERROR_RESPONSE_SOME = "src/test/resources/auth/password_strength_error_some.json";

    private MockServer server;
    private AuthAPI api;
    private AuthAPI apiNoClientAuthentication;

    @SuppressWarnings("deprecation")
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @BeforeEach
    public void setUp() throws Exception {
        server = new MockServer();
        api = AuthAPI.newBuilder(server.getBaseUrl(), CLIENT_ID, CLIENT_SECRET).build();
        apiNoClientAuthentication = AuthAPI.newBuilder(server.getBaseUrl(), CLIENT_ID).build();
    }

    @AfterEach
    public void tearDown() throws Exception {
        server.stop();
    }

    // Configuration

    @Test
    @SuppressWarnings("deprecation")
    public void shouldAcceptHttpOptions() {
        AuthAPI api = new AuthAPI(DOMAIN, CLIENT_ID, CLIENT_SECRET, new com.auth0.client.HttpOptions());
        assertThat(api, is(notNullValue()));
    }

    @Test
    public void shouldAcceptHttpClient() {
        Auth0HttpClient httpClient = new Auth0HttpClient() {
            @Override
            public Auth0HttpResponse sendRequest(Auth0HttpRequest request) throws IOException {
                return null;
            }

            @Override
            public CompletableFuture<Auth0HttpResponse> sendRequestAsync(Auth0HttpRequest request) {
                return null;
            }
        };

        AuthAPI api = AuthAPI.newBuilder(DOMAIN, CLIENT_ID, CLIENT_SECRET)
            .withHttpClient(httpClient)
            .build();

        assertThat(api, is(notNullValue()));
        assertThat(api.getHttpClient(), is(notNullValue()));
    }

    @Test
    public void shouldAcceptDomainWithNoScheme() {
        AuthAPI api = AuthAPI.newBuilder("me.something.com", CLIENT_ID, CLIENT_SECRET).build();

        assertThat(api.getBaseUrl(), is(notNullValue()));
        assertThat(api.getBaseUrl().toString(), isUrl("https", "me.something.com"));
    }

    @Test
    public void shouldAcceptDomainWithHttpScheme() {
        AuthAPI api = AuthAPI.newBuilder("http://me.something.com", CLIENT_ID, CLIENT_SECRET).build();

        assertThat(api.getBaseUrl(), is(notNullValue()));
        assertThat(api.getBaseUrl().toString(), isUrl("http", "me.something.com"));
    }

    @Test
    public void shouldThrowWhenDomainIsInvalid() {
        verifyThrows(IllegalArgumentException.class,
            () -> AuthAPI.newBuilder("", CLIENT_ID, CLIENT_SECRET).build(),
            "The domain had an invalid format and couldn't be parsed as an URL.");
    }

    @Test
    public void shouldThrowWhenDomainIsNull() {
        verifyThrows(IllegalArgumentException.class,
            () -> AuthAPI.newBuilder(null, CLIENT_ID, CLIENT_SECRET).build(),
            "'domain' cannot be null!");
    }

    @Test
    public void shouldThrowWhenClientIdIsNull() {
        verifyThrows(IllegalArgumentException.class,
            () -> AuthAPI.newBuilder(DOMAIN, null, CLIENT_SECRET).build(),
            "'client id' cannot be null!");
    }

    @Test
    public void shouldAcceptNullClientSecret() {
        assertThat(AuthAPI.newBuilder(DOMAIN, CLIENT_ID, (String) null).build(),
            is(notNullValue()));
    }

    @Test
    @SuppressWarnings("deprecation")
    public void shouldThrowOnInValidMaxRequestsPerHostConfiguration() {
        com.auth0.client.HttpOptions options = new com.auth0.client.HttpOptions();
        verifyThrows(IllegalArgumentException.class,
            () -> options.setMaxRequestsPerHost(0),
            "maxRequestsPerHost must be one or greater.");
    }

    //Authorize

    @Test
    public void shouldThrowWhenAuthorizeUrlBuilderRedirectUriIsNull() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.authorizeUrl(null),
            "'redirect uri' must be a valid URL!");
    }

    @Test
    public void shouldThrowWhenAuthorizeUrlBuilderRedirectUriIsNotValidURL() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.authorizeUrl("notvalid.url"),
            "'redirect uri' must be a valid URL!");
    }

    @Test
    public void shouldGetAuthorizeUrlBuilder() {
        AuthorizeUrlBuilder builder = api.authorizeUrl("https://domain.auth0.com/callback");
        assertThat(builder, is(notNullValue()));
    }

    @Test
    public void shouldSetAuthorizeUrlBuilderDefaultValues() {
        AuthAPI api = AuthAPI.newBuilder("domain.auth0.com", CLIENT_ID, CLIENT_SECRET).build();
        String url = api.authorizeUrl("https://domain.auth0.com/callback").build();

        assertThat(url, isUrl("https", "domain.auth0.com", "/authorize"));
        assertThat(url, hasQueryParameter("response_type", "code"));
        assertThat(url, hasQueryParameter("client_id", CLIENT_ID));
        assertThat(url, hasQueryParameter("redirect_uri", "https://domain.auth0.com/callback"));
        assertThat(url, hasQueryParameter("connection", null));
    }


    //Logout

    @Test
    public void shouldThrowWhenLogoutUrlBuilderReturnToUrlIsNull() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.logoutUrl(null, true),
            "'return to url' must be a valid URL!");
    }

    @Test
    public void shouldThrowWhenLogoutUrlBuilderRedirectUriIsNotValidURL() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.logoutUrl("notvalid.url", true),
            "'return to url' must be a valid URL!");
    }

    @Test
    public void shouldGetLogoutUrlBuilder() {
        LogoutUrlBuilder builder = api.logoutUrl("https://domain.auth0.com/callback", true);
        assertThat(builder, is(notNullValue()));
    }

    @Test
    public void shouldSetLogoutUrlBuilderDefaultValues() {
        AuthAPI api = AuthAPI.newBuilder("domain.auth0.com", CLIENT_ID, CLIENT_SECRET).build();
        String url = api.logoutUrl("https://my.domain.com/welcome", false).build();

        assertThat(url, isUrl("https", "domain.auth0.com", "/v2/logout"));
        assertThat(url, hasQueryParameter("client_id", null));
        assertThat(url, hasQueryParameter("returnTo", "https://my.domain.com/welcome"));
    }

    @Test
    public void shouldSetLogoutUrlBuilderDefaultValuesAndClientId() {
        AuthAPI api = AuthAPI.newBuilder("domain.auth0.com", CLIENT_ID, CLIENT_SECRET).build();
        String url = api.logoutUrl("https://my.domain.com/welcome", true).build();

        assertThat(url, isUrl("https", "domain.auth0.com", "/v2/logout"));
        assertThat(url, hasQueryParameter("client_id", CLIENT_ID));
        assertThat(url, hasQueryParameter("returnTo", "https://my.domain.com/welcome"));
    }


    //UserInfo

    @Test
    public void shouldThrowOnUserInfoWithNullAccessToken() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.userInfo(null),
            "'access token' cannot be null!");
    }

    @Test
    public void shouldCreateUserInfoRequest() throws Exception {
        Request<UserInfo> request = api.userInfo("accessToken");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(AUTH_USER_INFO, 200);
        UserInfo response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/userinfo"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer accessToken"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getValues(), is(notNullValue()));
        assertThat(response.getValues(), hasEntry("email_verified", false));
        assertThat(response.getValues(), hasEntry("email", "test.account@userinfo.com"));
        assertThat(response.getValues(), hasEntry("clientID", "q2hnj2iu..."));
        assertThat(response.getValues(), hasEntry("updated_at", "2016-12-05T15:15:40.545Z"));
        assertThat(response.getValues(), hasEntry("name", "test.account@userinfo.com"));
        assertThat(response.getValues(), hasEntry("picture", "https://s.gravatar.com/avatar/dummy.png"));
        assertThat(response.getValues(), hasEntry("user_id", "auth0|58454..."));
        assertThat(response.getValues(), hasEntry("nickname", "test.account"));
        assertThat(response.getValues(), hasEntry("created_at", "2016-12-05T11:16:59.640Z"));
        assertThat(response.getValues(), hasEntry("sub", "auth0|58454..."));
        assertThat(response.getValues(), hasKey("identities"));
        List<Map<String, Object>> identities = (List<Map<String, Object>>) response.getValues().get("identities");
        assertThat(identities, hasSize(1));
        assertThat(identities.get(0), hasEntry("user_id", "58454..."));
        assertThat(identities.get(0), hasEntry("provider", "auth0"));
        assertThat(identities.get(0), hasEntry("connection", "Username-Password-Authentication"));
        assertThat(identities.get(0), hasEntry("isSocial", false));
    }


    //Reset Password

    @Test
    public void shouldThrowOnResetPasswordWithNullEmail() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.resetPassword(null, "my-connection"),
            "'email' cannot be null!");
    }

    @Test
    public void shouldThrowOnResetPasswordWithNullConnection() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.resetPassword("me@auth0.com", null),
            "'connection' cannot be null!");
    }

    @Test
    public void shouldCreateResetPasswordRequest() throws Exception {
        Request<Void> request = api.resetPassword("me@auth0.com", "db-connection");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(AUTH_RESET_PASSWORD, 200);
        Void response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.POST, "/dbconnections/change_password"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body, hasEntry("email", "me@auth0.com"));
        assertThat(body, hasEntry("connection", "db-connection"));
        assertThat(body, hasEntry("client_id", CLIENT_ID));
        assertThat(body, not(hasKey("password")));

        assertThat(response, is(nullValue()));
    }

    @Test
    public void shouldCreateResetPasswordRequestWithSpecifiedClientId() throws Exception {
        Request<Void> request = api.resetPassword("CLIENT-ID", "me@auth0.com", "db-connection");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(AUTH_RESET_PASSWORD, 200);
        Void response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.POST, "/dbconnections/change_password"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body, hasEntry("email", "me@auth0.com"));
        assertThat(body, hasEntry("connection", "db-connection"));
        assertThat(body, hasEntry("client_id", "CLIENT-ID"));
        assertThat(body, not(hasKey("password")));

        assertThat(response, is(nullValue()));
    }

    //Sign Up

    @SuppressWarnings("deprecation")
    @Test
    public void shouldThrowOnSignUpWithNullEmail() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.signUp(null, "p455w0rd", "my-connection"),
            "'email' cannot be null!");
    }

    @SuppressWarnings("deprecation")
    @Test
    public void shouldThrowOnSignUpWithNullPasswordString() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.signUp("me@auth0.com", (String) null, "my-connection"),
            "'password' cannot be null!");
    }

    @Test
    public void shouldThrowOnSignUpWithNullPasswordCharArray() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.signUp("me@auth0.com", (char[]) null, "my-connection"),
            "'password' cannot be null!");
    }

    @SuppressWarnings("deprecation")
    @Test
    public void shouldThrowOnSignUpWithNullConnection() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.signUp("me@auth0.com", "p455w0rd", null),
            "'connection' cannot be null!");
    }

    @SuppressWarnings("deprecation")
    @Test
    public void shouldThrowOnUsernameSignUpWithNullEmail() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.signUp(null, "me", "p455w0rd", "my-connection"),
            "'email' cannot be null!");
    }

    @SuppressWarnings("deprecation")
    @Test
    public void shouldThrowOnUsernameSignUpWithNullUsername() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.signUp("me@auth0.com", null, "p455w0rd", "my-connection"),
            "'username' cannot be null!");
    }

    @SuppressWarnings("deprecation")
    @Test
    public void shouldThrowOnUsernameSignUpWithNullPasswordString() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.signUp("me@auth0.com", "me", (String) null, "my-connection"),
            "'password' cannot be null!");
    }

    @Test
    public void shouldThrowOnUsernameSignUpWithNullPasswordCharArray() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.signUp("me@auth0.com", "me", (char[]) null, "my-connection"),
            "'password' cannot be null!");
    }

    @SuppressWarnings("deprecation")
    @Test
    public void shouldThrowOnUsernameSignUpWithNullConnection() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.signUp("me@auth0.com", "me", "p455w0rd", null),
            "'connection' cannot be null!");
    }

    @Test
    public void shouldHaveNotStrongPasswordWithDetailedDescription() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        FileReader fr = new FileReader(PASSWORD_STRENGTH_ERROR_RESPONSE_NONE);
        Map<String, Object> mapPayload = mapper.readValue(fr, new TypeReference<Map<String, Object>>() {
        });
        APIException ex = new APIException(mapPayload, 400);
        assertThat(ex.getError(), is("invalid_password"));
        String expectedDescription = "At least 10 characters in length; Contain at least 3 of the following 4 types of characters: lower case letters (a-z), upper case letters (A-Z), numbers (i.e. 0-9), special characters (e.g. !@#$%^&*); Should contain: lower case letters (a-z), upper case letters (A-Z), numbers (i.e. 0-9), special characters (e.g. !@#$%^&*); No more than 2 identical characters in a row (e.g., \"aaa\" not allowed)";
        assertThat(ex.getDescription(), is(expectedDescription));
    }

    @Test
    public void shouldHaveNotStrongPasswordWithShortDetailedDescription() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        FileReader fr = new FileReader(PASSWORD_STRENGTH_ERROR_RESPONSE_SOME);
        Map<String, Object> mapPayload = mapper.readValue(fr, new TypeReference<Map<String, Object>>() {
        });
        APIException ex = new APIException(mapPayload, 400);
        assertThat(ex.getError(), is("invalid_password"));
        String expectedDescription = "Should contain: lower case letters (a-z), upper case letters (A-Z), numbers (i.e. 0-9), special characters (e.g. !@#$%^&*)";
        assertThat(ex.getDescription(), is(expectedDescription));
    }

    @Test
    public void shouldCreateSignUpRequestWithUsername() throws Exception {
        @SuppressWarnings("deprecation")
        SignUpRequest request = api.signUp("me@auth0.com", "me", "p455w0rd", "db-connection");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(AUTH_SIGN_UP_USERNAME, 200);
        CreatedUser response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.POST, "/dbconnections/signup"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body, hasEntry("email", "me@auth0.com"));
        assertThat(body, hasEntry("username", "me"));
        assertThat(body, hasEntry("password", "p455w0rd"));
        assertThat(body, hasEntry("connection", "db-connection"));
        assertThat(body, hasEntry("client_id", CLIENT_ID));

        assertThat(response, is(notNullValue()));
        assertThat(response.getUserId(), is("58457fe6b27"));
        assertThat(response.getEmail(), is("me@auth0.com"));
        assertThat(response.isEmailVerified(), is(false));
        assertThat(response.getUsername(), is("me"));
    }

    @Test
    public void shouldCreateSignUpRequest() throws Exception {
        @SuppressWarnings("deprecation")
        SignUpRequest request = api.signUp("me@auth0.com", "p455w0rd", "db-connection");

        assertThat(request, is(notNullValue()));

        server.jsonResponse(AUTH_SIGN_UP, 200);
        CreatedUser response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.POST, "/dbconnections/signup"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body, hasEntry("email", "me@auth0.com"));
        assertThat(body, hasEntry("password", "p455w0rd"));
        assertThat(body, hasEntry("connection", "db-connection"));
        assertThat(body, hasEntry("client_id", CLIENT_ID));
        assertThat(body, not(hasKey("username")));

        assertThat(response, is(notNullValue()));
        assertThat(response.getUserId(), is("58457fe6b27"));
        assertThat(response.getEmail(), is("me@auth0.com"));
        assertThat(response.isEmailVerified(), is(false));
        assertThat(response.getUsername(), is(nullValue()));
    }

    @Test
    public void shouldCreateSignUpRequestWithCustomParameters() throws Exception {
        @SuppressWarnings("deprecation")
        SignUpRequest request = api.signUp("me@auth0.com", "p455w0rd", "db-connection");
        assertThat(request, is(notNullValue()));
        Map<String, String> customFields = new HashMap<>();
        customFields.put("age", "25");
        customFields.put("address", "123, fake street");
        request.setCustomFields(customFields);

        server.jsonResponse(AUTH_SIGN_UP, 200);
        CreatedUser response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.POST, "/dbconnections/signup"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body, hasEntry("email", "me@auth0.com"));
        assertThat(body, hasEntry("password", "p455w0rd"));
        assertThat(body, hasEntry("connection", "db-connection"));
        assertThat(body, hasEntry("client_id", CLIENT_ID));
        assertThat(body, hasKey("user_metadata"));
        Map<String, String> metadata = (Map<String, String>) body.get("user_metadata");
        assertThat(metadata, hasEntry("age", "25"));
        assertThat(metadata, hasEntry("address", "123, fake street"));
        assertThat(body, not(hasKey("username")));

        assertThat(response, is(notNullValue()));
        assertThat(response.getUserId(), is("58457fe6b27"));
        assertThat(response.getEmail(), is("me@auth0.com"));
        assertThat(response.isEmailVerified(), is(false));
        assertThat(response.getUsername(), is(nullValue()));
    }


    //Log In with AuthorizationCode Grant

    @Test
    public void shouldThrowOnLogInWithAuthorizationCodeGrantAndRedirectUriWithNullCode() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.exchangeCode(null, "https://domain.auth0.com/callback"),
            "'code' cannot be null!");
    }

    @Test
    public void shouldThrowOnLogInWithAuthorizationCodeGrantAndRedirectUriWithNullRedirectUri() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.exchangeCode("code", null),
            "'redirect uri' cannot be null!");
    }

    @Test
    public void shouldCreateLogInWithAuthorizationCodeGrantRequest() throws Exception {
        TokenRequest request = api.exchangeCode("code123", "https://domain.auth0.com/callback");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(AUTH_TOKENS, 200);
        TokenHolder response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.POST, "/oauth/token"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body, hasEntry("code", "code123"));
        assertThat(body, hasEntry("redirect_uri", "https://domain.auth0.com/callback"));
        assertThat(body, hasEntry("grant_type", "authorization_code"));
        assertThat(body, hasEntry("client_id", CLIENT_ID));
        assertThat(body, hasEntry("client_secret", CLIENT_SECRET));

        assertThat(response, is(notNullValue()));
        assertThat(response.getAccessToken(), not(emptyOrNullString()));
        assertThat(response.getIdToken(), not(emptyOrNullString()));
        assertThat(response.getRefreshToken(), not(emptyOrNullString()));
        assertThat(response.getTokenType(), not(emptyOrNullString()));
        assertThat(response.getExpiresIn(), is(notNullValue()));
    }

    @Test
    public void authorizationCodeGrantRequestRequiresClientAuthentication() throws Exception {
        verifyThrows(IllegalStateException.class,
            () -> apiNoClientAuthentication.exchangeCode("code", "https://domain.auth0.com/callback"),
            "A client secret or client assertion signing key is required for this operation");
    }

    @Test
    public void shouldCreateLogInWithAuthorizationCodeGrantRequestWithCustomParameters() throws Exception {
        TokenRequest request = api.exchangeCode("code123", "https://domain.auth0.com/callback");
        assertThat(request, is(notNullValue()));
        request.setAudience("https://myapi.auth0.com/users");
        request.setRealm("dbconnection");
        request.setScope("profile photos contacts");

        server.jsonResponse(AUTH_TOKENS, 200);
        TokenHolder response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.POST, "/oauth/token"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body, hasEntry("code", "code123"));
        assertThat(body, hasEntry("redirect_uri", "https://domain.auth0.com/callback"));
        assertThat(body, hasEntry("grant_type", "authorization_code"));
        assertThat(body, hasEntry("client_id", CLIENT_ID));
        assertThat(body, hasEntry("client_secret", CLIENT_SECRET));
        assertThat(body, hasEntry("audience", "https://myapi.auth0.com/users"));
        assertThat(body, hasEntry("realm", "dbconnection"));
        assertThat(body, hasEntry("scope", "profile photos contacts"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getAccessToken(), not(emptyOrNullString()));
        assertThat(response.getIdToken(), not(emptyOrNullString()));
        assertThat(response.getRefreshToken(), not(emptyOrNullString()));
        assertThat(response.getTokenType(), not(emptyOrNullString()));
        assertThat(response.getExpiresIn(), is(notNullValue()));
    }


    //Log In with Password grant

    @SuppressWarnings("deprecation")
    @Test
    public void shouldThrowOnLogInWithPasswordWithNullUsername() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.login(null, "p455w0rd"),
            "'email or username' cannot be null!");
    }

    @SuppressWarnings("deprecation")
    @Test
    public void shouldThrowOnLogInWithPasswordWithNullPassword() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.login("me", (String) null),
            "'password' cannot be null!");
    }

    @Test
    public void shouldThrowOnLogInWithCharPasswordWithNullPassword() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.login("me", (char[]) null),
            "'password' cannot be null!");
    }

    @Test
    public void shouldCreateLogInWithPasswordGrantRequest() throws Exception {
        @SuppressWarnings("deprecation")
        TokenRequest request = api.login("me", "p455w0rd");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(AUTH_TOKENS, 200);
        TokenHolder response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.POST, "/oauth/token"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body, hasEntry("grant_type", "password"));
        assertThat(body, hasEntry("client_id", CLIENT_ID));
        assertThat(body, hasEntry("client_secret", CLIENT_SECRET));
        assertThat(body, hasEntry("username", "me"));
        assertThat(body, hasEntry("password", "p455w0rd"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getAccessToken(), not(emptyOrNullString()));
        assertThat(response.getIdToken(), not(emptyOrNullString()));
        assertThat(response.getRefreshToken(), not(emptyOrNullString()));
        assertThat(response.getTokenType(), not(emptyOrNullString()));
        assertThat(response.getExpiresIn(), is(notNullValue()));
    }

    @Test
    @SuppressWarnings("deprecation")
    public void passwordGrantRequestRequiresClientAuthentication() {
        verifyThrows(IllegalStateException.class,
            () -> apiNoClientAuthentication.login("me", "p455w0rd"),
            "A client secret or client assertion signing key is required for this operation");
    }

    @Test
    public void shouldCreateLogInWithPasswordGrantRequestWithCustomParameters() throws Exception {
        @SuppressWarnings("deprecation")
        TokenRequest request = api.login("me", "p455w0rd");
        assertThat(request, is(notNullValue()));
        request.setRealm("dbconnection");
        request.setScope("profile photos contacts");
        request.setAudience("https://myapi.auth0.com/users");

        server.jsonResponse(AUTH_TOKENS, 200);
        TokenHolder response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.POST, "/oauth/token"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body, hasEntry("grant_type", "password"));
        assertThat(body, hasEntry("client_id", CLIENT_ID));
        assertThat(body, hasEntry("client_secret", CLIENT_SECRET));
        assertThat(body, hasEntry("username", "me"));
        assertThat(body, hasEntry("password", "p455w0rd"));
        assertThat(body, hasEntry("realm", "dbconnection"));
        assertThat(body, hasEntry("scope", "profile photos contacts"));
        assertThat(body, hasEntry("audience", "https://myapi.auth0.com/users"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getAccessToken(), not(emptyOrNullString()));
        assertThat(response.getIdToken(), not(emptyOrNullString()));
        assertThat(response.getRefreshToken(), not(emptyOrNullString()));
        assertThat(response.getTokenType(), not(emptyOrNullString()));
        assertThat(response.getExpiresIn(), is(notNullValue()));
    }

    @Test
    public void shouldSetCustomHeaderWithPasswordlessRealmRequest() throws Exception {
        TokenRequest request = api.login("me", new char[]{'p','a','s','s','w','o','r','d'});
        request.addHeader("some-header", "some-value");

        server.jsonResponse(AUTH_TOKENS, 200);
        TokenHolder response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.POST, "/oauth/token"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("some-header", "some-value"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body, hasEntry("grant_type", "password"));
        assertThat(body, hasEntry("client_id", CLIENT_ID));
        assertThat(body, hasEntry("client_secret", CLIENT_SECRET));
        assertThat(body, hasEntry("username", "me"));
        assertThat(body, hasEntry("password", "password"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getAccessToken(), not(emptyOrNullString()));
        assertThat(response.getIdToken(), not(emptyOrNullString()));
        assertThat(response.getRefreshToken(), not(emptyOrNullString()));
        assertThat(response.getTokenType(), not(emptyOrNullString()));
        assertThat(response.getExpiresIn(), is(notNullValue()));
    }

    //Log In with PasswordRealm grant

    @SuppressWarnings("deprecation")
    @Test
    public void shouldThrowOnLogInWithPasswordRealmWithNullUsername() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.login(null, "p455w0rd", "realm"),
            "'email or username' cannot be null!");
    }

    @SuppressWarnings("deprecation")
    @Test
    public void shouldThrowOnLogInWithPasswordRealmWithNullPasswordString() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.login("me", (String) null, "realm"),
            "'password' cannot be null!");
    }

    @Test
    public void shouldThrowOnLogInWithPasswordRealmWithNullPasswordCharArray() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.login("me", (char[]) null, "realm"),
            "'password' cannot be null!");
    }

    @SuppressWarnings("deprecation")
    @Test
    public void shouldThrowOnLogInWithPasswordRealmWithNullRealm() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.login("me", "p455w0rd", null),
            "'realm' cannot be null!");
    }

    @Test
    public void shouldCreateLogInWithPasswordRealmGrantRequest() throws Exception {
        @SuppressWarnings("deprecation")
        TokenRequest request = api.login("me", "p455w0rd", "realm");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(AUTH_TOKENS, 200);
        TokenHolder response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.POST, "/oauth/token"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body, hasEntry("grant_type", "http://auth0.com/oauth/grant-type/password-realm"));
        assertThat(body, hasEntry("client_id", CLIENT_ID));
        assertThat(body, hasEntry("client_secret", CLIENT_SECRET));
        assertThat(body, hasEntry("username", "me"));
        assertThat(body, hasEntry("password", "p455w0rd"));
        assertThat(body, hasEntry("realm", "realm"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getAccessToken(), not(emptyOrNullString()));
        assertThat(response.getIdToken(), not(emptyOrNullString()));
        assertThat(response.getRefreshToken(), not(emptyOrNullString()));
        assertThat(response.getTokenType(), not(emptyOrNullString()));
        assertThat(response.getExpiresIn(), is(notNullValue()));
    }

    @Test
    @SuppressWarnings("deprecation")
    public void passwordRealmGrantRequestRequiresClientAuthentication()  {
        verifyThrows(IllegalStateException.class,
            () -> apiNoClientAuthentication.login("me", "p455w0rd", "realm"),
            "A client secret or client assertion signing key is required for this operation");
    }

    @Test
    public void shouldCreateLogInWithPasswordRealmGrantRequestWithCustomParameters() throws Exception {
        @SuppressWarnings("deprecation")
        TokenRequest request = api.login("me", "p455w0rd", "realm");
        assertThat(request, is(notNullValue()));
        request.setAudience("https://myapi.auth0.com/users");
        request.setRealm("dbconnection");
        request.setScope("profile photos contacts");

        server.jsonResponse(AUTH_TOKENS, 200);
        TokenHolder response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.POST, "/oauth/token"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body, hasEntry("grant_type", "http://auth0.com/oauth/grant-type/password-realm"));
        assertThat(body, hasEntry("client_id", CLIENT_ID));
        assertThat(body, hasEntry("client_secret", CLIENT_SECRET));
        assertThat(body, hasEntry("username", "me"));
        assertThat(body, hasEntry("password", "p455w0rd"));
        assertThat(body, hasEntry("audience", "https://myapi.auth0.com/users"));
        assertThat(body, hasEntry("realm", "dbconnection"));
        assertThat(body, hasEntry("scope", "profile photos contacts"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getAccessToken(), not(emptyOrNullString()));
        assertThat(response.getIdToken(), not(emptyOrNullString()));
        assertThat(response.getRefreshToken(), not(emptyOrNullString()));
        assertThat(response.getTokenType(), not(emptyOrNullString()));
        assertThat(response.getExpiresIn(), is(notNullValue()));
    }


    //Log In with ClientCredentials grant

    @Test
    public void shouldThrowOnLogInWithClientCredentialsWithNullAudience() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.requestToken(null),
            "'audience' cannot be null!");
    }

    @Test
    public void shouldCreateLogInWithClientCredentialsGrantRequest() throws Exception {
        TokenRequest request = api.requestToken("https://myapi.auth0.com/users");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(AUTH_TOKENS, 200);
        TokenHolder response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.POST, "/oauth/token"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body, hasEntry("grant_type", "client_credentials"));
        assertThat(body, hasEntry("client_id", CLIENT_ID));
        assertThat(body, hasEntry("client_secret", CLIENT_SECRET));
        assertThat(body, hasEntry("audience", "https://myapi.auth0.com/users"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getAccessToken(), not(emptyOrNullString()));
        assertThat(response.getIdToken(), not(emptyOrNullString()));
        assertThat(response.getRefreshToken(), not(emptyOrNullString()));
        assertThat(response.getTokenType(), not(emptyOrNullString()));
        assertThat(response.getExpiresIn(), is(notNullValue()));
    }

    @Test
    public void clientCredentialsGrantRequestRequiresClientAuthentication() {
        verifyThrows(IllegalStateException.class,
            () -> apiNoClientAuthentication.requestToken("https://myapi.auth0.com/users"),
            "A client secret or client assertion signing key is required for this operation");
    }

    // Login with Passwordless

    @Test
    public void shouldCreateStartEmailPasswordlessFlowRequest() throws Exception {
        Request<PasswordlessEmailResponse> request = api.startPasswordlessEmailFlow("user@domain.com",
                PasswordlessEmailType.CODE);
        assertThat(request, is(notNullValue()));

        emailPasswordlessRequest(request, true);
    }

    @Test
    public void shouldCreateStartEmailPasswordlessFlowRequestWithoutClientAuthentication() throws Exception {
        Request<PasswordlessEmailResponse> request = apiNoClientAuthentication.startPasswordlessEmailFlow("user@domain.com",
            PasswordlessEmailType.CODE);
        assertThat(request, is(notNullValue()));

        emailPasswordlessRequest(request, false);
    }

    private void emailPasswordlessRequest(Request<PasswordlessEmailResponse> request, boolean secretSent) throws Exception{
        server.jsonResponse(PASSWORDLESS_EMAIL_RESPONSE, 200);
        PasswordlessEmailResponse response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.POST, "/passwordless/start"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body, hasEntry("connection", "email"));
        assertThat(body, hasEntry("client_id", CLIENT_ID));
        if (secretSent) {
            assertThat(body, hasEntry("client_secret", CLIENT_SECRET));
        } else {
            assertThat(body, not(hasKey("client_secret")));
        }
        assertThat(body, hasEntry("email", "user@domain.com"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getEmail(), not(emptyOrNullString()));
        assertThat(response.getId(), not(emptyOrNullString()));
        assertThat(response.isEmailVerified(), is(notNullValue()));
    }

    @Test
    public void startPasswordlessEmailFlowShouldThrowWhenEmailIsNull() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.startPasswordlessEmailFlow(null, PasswordlessEmailType.CODE),
            "'email' cannot be null!");
    }

    @Test
    public void startPasswordlessEmailFlowShouldThrowWhenTypeIsNull() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.startPasswordlessEmailFlow("user@domain.com", null),
            "'type' cannot be null!");
    }

    @Test
    public void shouldCreateStartEmailPasswordlessFlowRequestWithCustomParams() throws Exception {
        Map<String, String> authParams = new HashMap<>();
        authParams.put("scope", "openid profile email");
        authParams.put("state", "abc123");

        BaseRequest<PasswordlessEmailResponse> request = api.startPasswordlessEmailFlow("user@domain.com", PasswordlessEmailType.CODE)
                .addParameter("authParams", authParams);

        // verify that connection parameter can be overridden for custom connection types
        request.addParameter("connection", "custom-email");

        assertThat(request, is(notNullValue()));

        server.jsonResponse(PASSWORDLESS_EMAIL_RESPONSE, 200);
        PasswordlessEmailResponse response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.POST, "/passwordless/start"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body, hasEntry("connection", "custom-email"));
        assertThat(body, hasEntry("client_id", CLIENT_ID));
        assertThat(body, hasEntry("client_secret", CLIENT_SECRET));
        assertThat(body, hasEntry("email", "user@domain.com"));
        assertThat(body, hasKey("authParams"));
        Map<String, String> authParamsSent = (Map<String, String>) body.get("authParams");
        assertThat(authParamsSent, hasEntry("scope", authParams.get("scope")));
        assertThat(authParamsSent, hasEntry("state", authParams.get("state")));

        assertThat(response, is(notNullValue()));
        assertThat(response.getEmail(), not(emptyOrNullString()));
        assertThat(response.getId(), not(emptyOrNullString()));
        assertThat(response.isEmailVerified(), is(notNullValue()));
    }

    @Test
    public void shouldCreateStartSmsPasswordlessFlowRequest() throws Exception {
        Request<PasswordlessSmsResponse> request = api.startPasswordlessSmsFlow("+16511234567");
        assertThat(request, is(notNullValue()));

        smsPasswordlessFlow(request, true);
    }

    @Test
    public void shouldCreateStartSmsPasswordlessFlowRequestWithoutClientAuthentication() throws Exception {
        Request<PasswordlessSmsResponse> request = apiNoClientAuthentication.startPasswordlessSmsFlow("+16511234567");
        assertThat(request, is(notNullValue()));

        smsPasswordlessFlow(request, false);
    }

    private void smsPasswordlessFlow(Request<PasswordlessSmsResponse> request, boolean secretSent) throws Exception {
        server.jsonResponse(PASSWORDLESS_SMS_RESPONSE, 200);
        PasswordlessSmsResponse response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.POST, "/passwordless/start"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body, hasEntry("connection", "sms"));
        assertThat(body, hasEntry("client_id", CLIENT_ID));
        if (secretSent) {
            assertThat(body, hasEntry("client_secret", CLIENT_SECRET));
        } else {
            assertThat(body, not(hasKey("client_secret")));
        }
        assertThat(body, hasEntry("phone_number", "+16511234567"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getPhoneNumber(), not(emptyOrNullString()));
        assertThat(response.getId(), not(emptyOrNullString()));
        assertThat(response.isPhoneVerified(), is(notNullValue()));
        assertThat(response.getRequestLanguage(), is(nullValue()));
    }

    @Test
    public void shouldCreateStartSmsPasswordlessFlowRequestWithCustomConnection() throws Exception {
        BaseRequest<PasswordlessSmsResponse> request = api.startPasswordlessSmsFlow("+16511234567");

        // verify that connection parameter can be overridden for custom connection types
        request.addParameter("connection", "custom-sms");

        assertThat(request, is(notNullValue()));

        server.jsonResponse(PASSWORDLESS_SMS_RESPONSE, 200);
        PasswordlessSmsResponse response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.POST, "/passwordless/start"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body, hasEntry("connection", "custom-sms"));
        assertThat(body, hasEntry("client_id", CLIENT_ID));
        assertThat(body, hasEntry("client_secret", CLIENT_SECRET));
        assertThat(body, hasEntry("phone_number", "+16511234567"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getPhoneNumber(), not(emptyOrNullString()));
        assertThat(response.getId(), not(emptyOrNullString()));
        assertThat(response.isPhoneVerified(), is(notNullValue()));
        assertThat(response.getRequestLanguage(), is(nullValue()));
    }

    @Test
    public void startPasswordlessSmsFlowShouldThrowWhenPhoneIsNull() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.startPasswordlessSmsFlow(null),
            "'phoneNumber' cannot be null!");
    }

    @Test
    public void shouldCreateLoginWithPasswordlessCodeRequest() throws Exception {
        TokenRequest request = api.exchangePasswordlessOtp("+16511234567", "email", "otp".toCharArray());
        assertThat(request, is(notNullValue()));

        passwordlessCodeRequest(request, true);
    }

    @Test
    public void shouldCreateLoginWithPasswordlessCodeRequestWithoutClientAuthentication() throws Exception {
        TokenRequest request = apiNoClientAuthentication.exchangePasswordlessOtp("+16511234567", "email", "otp".toCharArray());
        assertThat(request, is(notNullValue()));

        passwordlessCodeRequest(request, false);
    }

    private void passwordlessCodeRequest(TokenRequest request, boolean secretSent) throws Exception {
        server.jsonResponse(AUTH_TOKENS, 200);
        TokenHolder response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.POST, "/oauth/token"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body, hasEntry("client_id", CLIENT_ID));
        if (secretSent) {
            assertThat(body, hasEntry("client_secret", CLIENT_SECRET));
        } else {
            assertThat(body, not(hasKey("client_secret")));
        }
        assertThat(body, hasEntry("realm", "email"));
        assertThat(body, hasEntry("grant_type", "http://auth0.com/oauth/grant-type/passwordless/otp"));
        assertThat(body, hasEntry("otp", "otp"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getScope(), is(nullValue()));
        assertThat(response.getAccessToken(), is(notNullValue()));
        assertThat(response.getExpiresIn(), is(notNullValue()));
        assertThat(response.getIdToken(), is(notNullValue()));
        assertThat(response.getTokenType(), is(notNullValue()));
    }

    //Revoke a Token

    @Test
    public void shouldThrowOnRevokeTokenWithNullToken() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.revokeToken(null),
            "'refresh token' cannot be null!");
    }

    @Test
    public void shouldCreateRevokeTokenRequest() throws Exception {
        Request<Void> request = api.revokeToken("2679NfkaBn62e6w5E8zNEzjr");
        assertThat(request, is(notNullValue()));

        revokeTokenRequest(request, true);
    }

    @Test
    public void shouldCreateRevokeTokenRequestWithoutClientAuthentication() throws Exception {
        Request<Void> request = apiNoClientAuthentication.revokeToken("2679NfkaBn62e6w5E8zNEzjr");
        assertThat(request, is(notNullValue()));

        revokeTokenRequest(request, false);
    }

    private void revokeTokenRequest( Request<Void> request, boolean secretSent) throws Exception {
        server.emptyResponse(200);
        Void response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.POST, "/oauth/revoke"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body, hasEntry("client_id", CLIENT_ID));
        if (secretSent) {
            assertThat(body, hasEntry("client_secret", CLIENT_SECRET));
        } else {
            assertThat(body, not(hasKey("client_secret")));
        }
        assertThat(body, hasEntry("token", "2679NfkaBn62e6w5E8zNEzjr"));

        assertThat(response, is(nullValue()));
    }


    //Renew Authentication using Refresh Token

    @Test
    public void shouldThrowOnRenewAuthWithNullRefreshToken() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.renewAuth(null),
            "'refresh token' cannot be null!");
    }

    @Test
    public void shouldCreateRenewTokenRequest() throws Exception {
        TokenRequest request = api.renewAuth("ej2E8zNEzjrcSD2edjaE");
        assertThat(request, is(notNullValue()));

        renewTokenRequest(request, true);
    }

    @Test
    public void shouldCreateRenewTokenRequestWithoutClientAuthentication() throws Exception {
        TokenRequest request = apiNoClientAuthentication.renewAuth("ej2E8zNEzjrcSD2edjaE");
        assertThat(request, is(notNullValue()));

        renewTokenRequest(request, false);
    }

    private void renewTokenRequest(TokenRequest request, boolean secretSent) throws Exception {
        server.jsonResponse(AUTH_TOKENS, 200);
        TokenHolder response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.POST, "/oauth/token"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body, hasEntry("grant_type", "refresh_token"));
        assertThat(body, hasEntry("client_id", CLIENT_ID));
        if (secretSent) {
            assertThat(body, hasEntry("client_secret", CLIENT_SECRET));
        } else {
            assertThat(body, not(hasKey("clieht_secret")));
        }
        assertThat(body, hasEntry("refresh_token", "ej2E8zNEzjrcSD2edjaE"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getAccessToken(), not(emptyOrNullString()));
        assertThat(response.getIdToken(), not(emptyOrNullString()));
        assertThat(response.getRefreshToken(), not(emptyOrNullString()));
        assertThat(response.getTokenType(), not(emptyOrNullString()));
        assertThat(response.getExpiresIn(), is(notNullValue()));
    }

    // PKCE

    @Test
    public void shouldCreateLogInWithAuthorizationCodeGrantWithPKCERequest() throws Exception {
        TokenRequest request = api.exchangeCodeWithVerifier("code123", "verifier", "https://domain.auth0.com/callback");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(AUTH_TOKENS, 200);
        TokenHolder response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.POST, "/oauth/token"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body, hasEntry("code", "code123"));
        assertThat(body, hasEntry("code_verifier", "verifier"));
        assertThat(body, hasEntry("redirect_uri", "https://domain.auth0.com/callback"));
        assertThat(body, hasEntry("grant_type", "authorization_code"));
        assertThat(body, hasEntry("client_id", CLIENT_ID));
        assertThat(body, hasEntry("client_secret", CLIENT_SECRET));

        assertThat(response, is(notNullValue()));
        assertThat(response.getAccessToken(), not(emptyOrNullString()));
        assertThat(response.getIdToken(), not(emptyOrNullString()));
        assertThat(response.getRefreshToken(), not(emptyOrNullString()));
        assertThat(response.getTokenType(), not(emptyOrNullString()));
        assertThat(response.getExpiresIn(), is(notNullValue()));
    }

    @Test
    public void shouldThrowWhenVerifierNull() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.exchangeCodeWithVerifier("code", null,"https://domain.auth0.com/callback"),
            "'verifier' cannot be null!");
    }

    // MFA

    @Test
    public void shouldThrowWhenExchangeMfaOtpCalledWithNullMfaToken() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.exchangeMfaOtp(null, new char[]{'o','t','p'}),
            "'mfa token' cannot be null!");
    }

    @Test
    public void shouldThrowWhenExchangeMfaOtpCalledWithNullOtp() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.exchangeMfaOtp("mfaToken", null),
            "'otp' cannot be null!");
    }

    @Test
    public void shouldCreateExchangeMfaOtpRequest() throws Exception {
        TokenRequest request = api.exchangeMfaOtp("mfaToken", new char[]{'o','t','p'});
        assertThat(request, is(notNullValue()));

        mfaOtpRequest(request, true);
    }

    @Test
    public void shouldCreateExchangeMfaOtpRequestWithoutClientAuthentication() throws Exception {
        TokenRequest request = apiNoClientAuthentication.exchangeMfaOtp("mfaToken", new char[]{'o','t','p'});
        assertThat(request, is(notNullValue()));

        mfaOtpRequest(request, false);
    }

    private void mfaOtpRequest(TokenRequest request, boolean secretSent) throws Exception {
        server.jsonResponse(AUTH_TOKENS, 200);
        TokenHolder response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.POST, "/oauth/token"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body, hasEntry("grant_type", "http://auth0.com/oauth/grant-type/mfa-otp"));
        assertThat(body, hasEntry("client_id", CLIENT_ID));
        if (secretSent) {
            assertThat(body, hasEntry("client_secret", CLIENT_SECRET));
        } else {
            assertThat(body, not(hasKey("client_secret")));
        }
        assertThat(body, hasEntry("mfa_token", "mfaToken"));
        assertThat(body, hasEntry("otp", "otp"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getAccessToken(), not(emptyOrNullString()));
        assertThat(response.getIdToken(), not(emptyOrNullString()));
        assertThat(response.getRefreshToken(), not(emptyOrNullString()));
        assertThat(response.getTokenType(), not(emptyOrNullString()));
        assertThat(response.getExpiresIn(), is(notNullValue()));
    }

    @Test
    public void shouldThrowWhenExchangeMfaOobCalledWithNullMfaToken() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.exchangeMfaOob(null, new char[]{'o','t','p'}, null),
            "'mfa token' cannot be null!");
    }

    @Test
    public void shouldThrowWhenExchangeMfaOobCalledWithNullOoob() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.exchangeMfaOob("mfaToken", null, null),
            "'OOB code' cannot be null!");
    }

    @Test
    public void shouldCreateExchangeMfaOobRequest() throws Exception {
        TokenRequest request = api.exchangeMfaOob("mfaToken", new char[]{'o','o','b'}, null);
        assertThat(request, is(notNullValue()));

        mfaOobExchangeRequest(request, null, true);
    }

    @Test
    public void shouldCreateExchangeMfaOobRequestWithoutSecret() throws Exception {
        TokenRequest request = apiNoClientAuthentication.exchangeMfaOob("mfaToken", new char[]{'o','o','b'}, new char[]{'b','o','b'});
        assertThat(request, is(notNullValue()));

        mfaOobExchangeRequest(request, "bob", false);
    }

    private void mfaOobExchangeRequest(TokenRequest request, String bindingCode, boolean secretSent) throws Exception {
        server.jsonResponse(AUTH_TOKENS, 200);
        TokenHolder response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.POST, "/oauth/token"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body, hasEntry("grant_type", "http://auth0.com/oauth/grant-type/mfa-oob"));
        assertThat(body, hasEntry("client_id", CLIENT_ID));

        if (bindingCode != null) {
            assertThat(body, hasEntry("binding_code", bindingCode));
        } else {
            assertThat(body, not(hasKey("binding_code")));
        }

        if (secretSent) {
            assertThat(body, hasEntry("client_secret", CLIENT_SECRET));
        } else {
            assertThat(body, not(hasKey("client_secret")));
        }
        assertThat(body, hasEntry("mfa_token", "mfaToken"));
        assertThat(body, hasEntry("oob_code", "oob"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getAccessToken(), not(emptyOrNullString()));
        assertThat(response.getIdToken(), not(emptyOrNullString()));
        assertThat(response.getRefreshToken(), not(emptyOrNullString()));
        assertThat(response.getTokenType(), not(emptyOrNullString()));
        assertThat(response.getExpiresIn(), is(notNullValue()));
    }

    @Test
    public void shouldThrowWhenExchangeMfaRecoveryCodeCalledWithNullMfaToken() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.exchangeMfaRecoveryCode(null, new char[]{'c','o','d','e'}),
            "'mfa token' cannot be null!");
    }

    @Test
    public void shouldThrowWhenExchangeMfaRecoveryCodeCalledWithNullCode() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.exchangeMfaRecoveryCode("mfaToken", null),
            "'recovery code' cannot be null!");
    }

    @Test
    public void shouldCreateExchangeMfaRecoveryCodeRequest() throws Exception {
        TokenRequest request = api.exchangeMfaRecoveryCode("mfaToken", new char[]{'c','o','d','e'});
        assertThat(request, is(notNullValue()));

        mfaRecoveryCodeExchangeRequest(request, true);
    }

    @Test
    public void shouldCreateExchangeMfaRecoveryCodeRequestWithoutSecret() throws Exception {
        TokenRequest request = apiNoClientAuthentication.exchangeMfaRecoveryCode("mfaToken", new char[]{'c','o','d','e'});
        assertThat(request, is(notNullValue()));

        mfaRecoveryCodeExchangeRequest(request, false);
    }

    private void mfaRecoveryCodeExchangeRequest(TokenRequest request, boolean secretSent) throws Exception {
        server.jsonResponse(AUTH_TOKENS, 200);
        TokenHolder response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.POST, "/oauth/token"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body, hasEntry("grant_type", "http://auth0.com/oauth/grant-type/mfa-recovery-code"));
        assertThat(body, hasEntry("client_id", CLIENT_ID));

        if (secretSent) {
            assertThat(body, hasEntry("client_secret", CLIENT_SECRET));
        } else {
            assertThat(body, not(hasKey("client_secret")));
        }
        assertThat(body, hasEntry("mfa_token", "mfaToken"));
        assertThat(body, hasEntry("recovery_code", "code"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getAccessToken(), not(emptyOrNullString()));
        assertThat(response.getIdToken(), not(emptyOrNullString()));
        assertThat(response.getRefreshToken(), not(emptyOrNullString()));
        assertThat(response.getTokenType(), not(emptyOrNullString()));
        assertThat(response.getExpiresIn(), is(notNullValue()));
    }

    @Test
    public void addOtpAuthenticatorThrowsWhenTokenNull() throws Exception {
        verifyThrows(IllegalArgumentException.class,
            () -> api.addOtpAuthenticator(null),
            "'mfa token' cannot be null!");
    }

    @Test
    public void addOtpAuthenticatorRequest() throws Exception {
        Request<CreatedOtpResponse> request = api.addOtpAuthenticator("mfaToken");

        server.jsonResponse(AUTH_OTP_AUTHENTICATOR_RESPONSE, 200);
        CreatedOtpResponse response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.POST, "/mfa/associate"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body, hasEntry("authenticator_types", Collections.singletonList("otp")));

        assertThat(response, is(notNullValue()));
        assertThat(response.getAuthenticatorType(), not(emptyOrNullString()));
        assertThat(response.getSecret(), not(emptyOrNullString()));
        assertThat(response.getBarcodeUri(), not(emptyOrNullString()));
        assertThat(response.getRecoveryCodes(), notNullValue());
    }

    @Test
    public void addOobAuthenticatorThrowsWhenTokenNull() throws Exception {
        verifyThrows(IllegalArgumentException.class,
            () -> api.addOobAuthenticator(null, Collections.singletonList("otp"), null),
            "'mfa token' cannot be null!");
    }

    @Test
    public void addOobAuthenticatorThrowsWhenChannelsNull() throws Exception {
        verifyThrows(IllegalArgumentException.class,
            () -> api.addOobAuthenticator("mfaToken", null, null),
            "'OOB channels' cannot be null!");
    }

    @Test
    public void addOobAuthenticatorRequest() throws Exception {
        Request<CreatedOobResponse> request = api.addOobAuthenticator("mfaToken", Collections.singletonList("sms"), "phone-number");

        server.jsonResponse(AUTH_OOB_AUTHENTICATOR_RESPONSE, 200);
        CreatedOobResponse response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.POST, "/mfa/associate"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body, hasEntry("authenticator_types", Collections.singletonList("oob")));
        assertThat(body, hasEntry("oob_channels", Collections.singletonList("sms")));
        assertThat(body, hasEntry("phone_number", "phone-number"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getAuthenticatorType(), not(emptyOrNullString()));
        assertThat(response.getOobChannel(), not(emptyOrNullString()));
        assertThat(response.getOobCode(), not(emptyOrNullString()));
        assertThat(response.getBarcodeUri(), not(emptyOrNullString()));
        assertThat(response.getRecoveryCodes(), notNullValue());
    }

    @Test
    public void listAuthenticatorsThrowsWhenTokenNull() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.listAuthenticators(null),
            "'access token' cannot be null!");
    }

    @Test
    public void listAuthenticatorsRequest() throws Exception {
        Request<List<MfaAuthenticator>> request = api.listAuthenticators("token");

        server.jsonResponse(AUTH_LIST_AUTHENTICATORS_RESPONSE, 200);
        List<MfaAuthenticator> response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/mfa/authenticators"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer token"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void deleteAuthenticatorThrowsWhenTokenNull() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.deleteAuthenticator(null, "authenticatorId"),
            "'access token' cannot be null!");
    }

    @Test
    public void deleteAuthenticatorThrowsWhenAuthenticatorIdNull() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.deleteAuthenticator("Bearer accessToken", null),
            "'authenticator id' cannot be null!");
    }

    @Test
    public void deleteAuthenticatorRequest() throws Exception {
        Request<Void> request = api.deleteAuthenticator("accessToken", "authenticatorId");

        server.jsonResponse(AUTH_LIST_AUTHENTICATORS_RESPONSE, 200);
        Void response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.DELETE, "/mfa/authenticators/authenticatorId"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer accessToken"));

        assertThat(response, is(nullValue()));
    }

    @Test
    public void challengeRequestThrowsWhenTokenNull() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.mfaChallengeRequest(null, "otp", "authenticatorId"),
            "'mfa token' cannot be null!");
    }

    @Test
    public void challengeRequest() throws Exception {
        Request<MfaChallengeResponse> request = api.mfaChallengeRequest("mfaToken", "otp", "authenticatorId");

        server.jsonResponse(AUTH_CHALLENGE_RESPONSE, 200);
        MfaChallengeResponse response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.POST, "/mfa/challenge"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body, hasEntry("mfa_token", "mfaToken"));
        assertThat(body, hasEntry("client_id", CLIENT_ID));
        assertThat(body, hasEntry("client_secret", CLIENT_SECRET));
        assertThat(body, hasEntry("challenge_type", "otp"));
        assertThat(body, hasEntry("authenticator_id", "authenticatorId"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getChallengeType(), not(emptyOrNullString()));
        assertThat(response.getBindingMethod(), not(emptyOrNullString()));
        assertThat(response.getOobCode(), not(emptyOrNullString()));
    }

    // Client Assertion tests
    @Test
    public void shouldAddAndPreferClientAuthentication() throws Exception {
        AuthAPI authAPI = AuthAPI.newBuilder(server.getBaseUrl(), CLIENT_ID, CLIENT_SECRET)
            .withClientAssertionSigner(new TestAssertionSigner("token"))
            .build();
        TokenRequest request = authAPI.exchangeCode("code123", "https://domain.auth0.com/callback");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(AUTH_TOKENS, 200);
        TokenHolder response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.POST, "/oauth/token"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body, hasEntry("code", "code123"));
        assertThat(body, hasEntry("redirect_uri", "https://domain.auth0.com/callback"));
        assertThat(body, hasEntry("grant_type", "authorization_code"));
        assertThat(body, hasEntry("client_id", CLIENT_ID));
        assertThat(body, not(hasEntry("client_secret", CLIENT_SECRET)));
        assertThat(body, hasEntry("client_assertion", "token"));
        assertThat(body, hasEntry("client_assertion_type", "urn:ietf:params:oauth:client-assertion-type:jwt-bearer"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getAccessToken(), not(emptyOrNullString()));
        assertThat(response.getIdToken(), not(emptyOrNullString()));
        assertThat(response.getRefreshToken(), not(emptyOrNullString()));
        assertThat(response.getTokenType(), not(emptyOrNullString()));
        assertThat(response.getExpiresIn(), is(notNullValue()));
    }

    @Test
    public void shouldNotAddAnyParamsIfNoSecretOrAssertion() throws Exception {
        AuthAPI authAPI = AuthAPI.newBuilder(server.getBaseUrl(), CLIENT_ID).build();
        TokenRequest request = authAPI.exchangeCodeWithVerifier("code123", "verifier", "https://domain.auth0.com/callback");

        assertThat(request, is(notNullValue()));

        server.jsonResponse(AUTH_TOKENS, 200);
        TokenHolder response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.POST, "/oauth/token"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body, hasEntry("code", "code123"));
        assertThat(body, hasEntry("code_verifier", "verifier"));
        assertThat(body, hasEntry("redirect_uri", "https://domain.auth0.com/callback"));
        assertThat(body, hasEntry("grant_type", "authorization_code"));
        assertThat(body, hasEntry("client_id", CLIENT_ID));
        assertThat(body, not(hasEntry("client_secret", CLIENT_SECRET)));
        assertThat(body, not(hasEntry("client_assertion", "token")));
        assertThat(body, not(hasEntry("client_assertion_type", "urn:ietf:params:oauth:client-assertion-type:jwt-bearer")));

        assertThat(response, is(notNullValue()));
        assertThat(response.getAccessToken(), not(emptyOrNullString()));
        assertThat(response.getIdToken(), not(emptyOrNullString()));
        assertThat(response.getRefreshToken(), not(emptyOrNullString()));
        assertThat(response.getTokenType(), not(emptyOrNullString()));
        assertThat(response.getExpiresIn(), is(notNullValue()));
    }

    @Test
    public void authorizeUrlWithPARShouldThrowWhenRequestUriNull() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.authorizeUrlWithPAR(null),
            "'request uri' cannot be null!");
    }

    @Test
    public void shouldBuildAuthorizeUrlWithPAR() {
        AuthAPI api = AuthAPI.newBuilder("domain.auth0.com", CLIENT_ID, CLIENT_SECRET).build();
        String url = api.authorizeUrlWithPAR("urn:example:bwc4JK-ESC0w8acc191e-Y1LTC2");
        assertThat(url, is(notNullValue()));
        assertThat(url, isUrl("https", "domain.auth0.com", "/authorize"));

        assertThat(url, hasQueryParameter("request_uri", "urn:example:bwc4JK-ESC0w8acc191e-Y1LTC2"));
        assertThat(url, hasQueryParameter("client_id", CLIENT_ID));
    }

    @Test
    public void pushedAuthorizationRequestShouldThrowWhenRedirectUriIsNull() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.pushedAuthorizationRequest(null, "code", Collections.emptyMap()),
            "'redirect uri' must be a valid URL!");
    }

    @Test
    public void pushedAuthorizationRequestShouldThrowWhenResponseTypeIsNull() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.pushedAuthorizationRequest("https://domain.com/callback", null, Collections.emptyMap()),
            "'response type' cannot be null!");
    }

    @Test
    public void shouldCreatePushedAuthorizationRequestWithNullAdditionalParams() throws Exception {
        Request<PushedAuthorizationResponse> request = api.pushedAuthorizationRequest("https://domain.com/callback", "code", null);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(PUSHED_AUTHORIZATION_RESPONSE, 200);
        PushedAuthorizationResponse response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.POST, "/oauth/par"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/x-www-form-urlencoded"));

        String body = readFromRequest(recordedRequest);
        assertThat(body, containsString("client_id=" + CLIENT_ID));
        assertThat(body, containsString("redirect_uri=" + "https%3A%2F%2Fdomain.com%2Fcallback"));
        assertThat(body, containsString("response_type=" + "code"));
        assertThat(body, containsString("client_secret=" + CLIENT_SECRET));

        assertThat(response, is(notNullValue()));
        assertThat(response.getRequestURI(), not(emptyOrNullString()));
        assertThat(response.getExpiresIn(), notNullValue());
    }

    @Test
    public void shouldCreatePushedAuthorizationRequestWithAdditionalParams() throws Exception {
        Map<String, String> additionalParams = new HashMap<>();
        additionalParams.put("audience", "aud");
        additionalParams.put("connection", "conn");
        Request<PushedAuthorizationResponse> request = api.pushedAuthorizationRequest("https://domain.com/callback", "code", additionalParams);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(PUSHED_AUTHORIZATION_RESPONSE, 200);
        PushedAuthorizationResponse response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.POST, "/oauth/par"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/x-www-form-urlencoded"));

        String body = readFromRequest(recordedRequest);
        assertThat(body, containsString("client_id=" + CLIENT_ID));
        assertThat(body, containsString("redirect_uri=" + "https%3A%2F%2Fdomain.com%2Fcallback"));
        assertThat(body, containsString("response_type=" + "code"));
        assertThat(body, containsString("client_secret=" + CLIENT_SECRET));
        assertThat(body, containsString("audience=" + "aud"));
        assertThat(body, containsString("connection=" + "conn"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getRequestURI(), not(emptyOrNullString()));
        assertThat(response.getExpiresIn(), notNullValue());
    }

    @Test
    public void shouldCreatePushedAuthorizationRequestWithoutSecret() throws Exception {
        AuthAPI api = AuthAPI.newBuilder(server.getBaseUrl(), CLIENT_ID).build();
        Request<PushedAuthorizationResponse> request = api.pushedAuthorizationRequest("https://domain.com/callback", "code", null);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(PUSHED_AUTHORIZATION_RESPONSE, 200);
        PushedAuthorizationResponse response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.POST, "/oauth/par"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/x-www-form-urlencoded"));

        String body = readFromRequest(recordedRequest);
        assertThat(body, containsString("client_id=" + CLIENT_ID));
        assertThat(body, containsString("redirect_uri=" + "https%3A%2F%2Fdomain.com%2Fcallback"));
        assertThat(body, containsString("response_type=" + "code"));
        assertThat(body, not(containsString("client_secret")));

        assertThat(response, is(notNullValue()));
        assertThat(response.getRequestURI(), not(emptyOrNullString()));
        assertThat(response.getExpiresIn(), notNullValue());
    }

    static class TestAssertionSigner implements ClientAssertionSigner {

        private final String token;

        public TestAssertionSigner(String token) {
            this.token = token;
        }

        @Override
        public String createSignedClientAssertion(String issuer, String audience, String subject) {
            return token;
        }
    }
}

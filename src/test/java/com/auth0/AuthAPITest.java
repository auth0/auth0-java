package com.auth0;

import com.auth0.json.TokenHolder;
import com.auth0.json.UserInfo;
import com.auth0.net.AuthRequest;
import com.auth0.net.Request;
import com.auth0.net.SignUpRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.mockwebserver.RecordedRequest;
import okio.Buffer;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.hamcrest.collection.IsMapContaining.hasKey;
import static org.junit.Assert.assertThat;

public class AuthAPITest {

    private static final String DOMAIN = "domain.auth0.com";
    private static final String CLIENT_ID = "clientId";
    private static final String CLIENT_SECRET = "clientSecret";

    @Mock
    OkHttpClient client;
    private MockServer server;
    private AuthAPI api;
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        server = new MockServer();
        api = new AuthAPI(server.getBaseUrl(), CLIENT_ID, CLIENT_SECRET);
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    // Configuration

    @Test
    public void shouldAcceptDomainWithNoScheme() throws Exception {
        AuthAPI api = new AuthAPI("me.something.com", CLIENT_ID, CLIENT_SECRET);

        HttpUrl parsed = HttpUrl.parse(api.getBaseUrl());
        MatcherAssert.assertThat(parsed, CoreMatchers.is(notNullValue()));
        MatcherAssert.assertThat(parsed.host(), CoreMatchers.is("me.something.com"));
        MatcherAssert.assertThat(parsed.scheme(), CoreMatchers.is("https"));
    }

    @Test
    public void shouldAcceptDomainWithHttpScheme() throws Exception {
        AuthAPI api = new AuthAPI("http://me.something.com", CLIENT_ID, CLIENT_SECRET);

        HttpUrl parsed = HttpUrl.parse(api.getBaseUrl());
        MatcherAssert.assertThat(parsed, CoreMatchers.is(notNullValue()));
        MatcherAssert.assertThat(parsed.host(), CoreMatchers.is("me.something.com"));
        MatcherAssert.assertThat(parsed.scheme(), CoreMatchers.is("http"));
    }

    @Test
    public void shouldThrowWhenDomainIsNull() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'domain' cannot be null!");
        new AuthAPI(null, CLIENT_ID, CLIENT_SECRET);
    }

    @Test
    public void shouldThrowWhenClientIdIsNull() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'client id' cannot be null!");
        new AuthAPI(DOMAIN, null, CLIENT_SECRET);
    }

    @Test
    public void shouldThrowWhenClientSecretIsNull() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'client secret' cannot be null!");
        new AuthAPI(DOMAIN, CLIENT_ID, null);
    }


    //Authorize

    @Test
    public void shouldThrowWhenAuthorizeUrlBuilderConnectionIsNull() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'connection' cannot be null!");
        api.authorize(null, "https://domain.auth0.com/callback");
    }

    @Test
    public void shouldThrowWhenAuthorizeUrlBuilderRedirectUriIsNull() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'redirect uri' cannot be null!");
        api.authorize("my-connection", null);
    }

    @Test
    public void shouldGetAuthorizeUrlBuilder() throws Exception {
        AuthorizeUrlBuilder builder = api.authorize("my-connection", "https://domain.auth0.com/callback");
        assertThat(builder, is(notNullValue()));
    }

    @Test
    public void shouldSetAuthorizeUrlBuilderDefaultValues() throws Exception {
        AuthAPI api = new AuthAPI("domain.auth0.com", CLIENT_ID, CLIENT_SECRET);
        String url = api.authorize("my-connection", "https://domain.auth0.com/callback").build();
        HttpUrl parsed = HttpUrl.parse(url);

        MatcherAssert.assertThat(url, not(isEmptyOrNullString()));
        MatcherAssert.assertThat(parsed, is(notNullValue()));
        MatcherAssert.assertThat(parsed.scheme(), is("https"));
        MatcherAssert.assertThat(parsed.host(), is("domain.auth0.com"));
        MatcherAssert.assertThat(parsed.pathSegments().size(), is(1));
        MatcherAssert.assertThat(parsed.pathSegments().get(0), is("authorize"));

        MatcherAssert.assertThat(parsed.queryParameter("response_type"), is("code"));
        MatcherAssert.assertThat(parsed.queryParameter("client_id"), is(CLIENT_ID));
        MatcherAssert.assertThat(parsed.queryParameter("redirect_uri"), is("https://domain.auth0.com/callback"));
        MatcherAssert.assertThat(parsed.queryParameter("connection"), is("my-connection"));
    }


    //Logout

    @Test
    public void shouldThrowWhenLogoutUrlBuilderReturnToUrlIsNull() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'return to url' cannot be null!");
        api.logout(null, true);
    }

    @Test
    public void shouldGetLogoutUrlBuilder() throws Exception {
        LogoutUrlBuilder builder = api.logout("https://domain.auth0.com/callback", true);
        assertThat(builder, is(notNullValue()));
    }

    @Test
    public void shouldSetLogoutUrlBuilderDefaultValues() throws Exception {
        AuthAPI api = new AuthAPI("domain.auth0.com", CLIENT_ID, CLIENT_SECRET);
        String url = api.logout("https://my.domain.com/welcome", false).build();
        HttpUrl parsed = HttpUrl.parse(url);

        MatcherAssert.assertThat(url, not(isEmptyOrNullString()));
        MatcherAssert.assertThat(parsed, is(notNullValue()));
        MatcherAssert.assertThat(parsed.scheme(), is("https"));
        MatcherAssert.assertThat(parsed.host(), is("domain.auth0.com"));
        MatcherAssert.assertThat(parsed.pathSegments().size(), is(2));
        MatcherAssert.assertThat(parsed.pathSegments().get(0), is("v2"));
        MatcherAssert.assertThat(parsed.pathSegments().get(1), is("logout"));

        MatcherAssert.assertThat(parsed.queryParameter("client_id"), is(nullValue()));
        MatcherAssert.assertThat(parsed.queryParameter("returnTo"), is("https://my.domain.com/welcome"));
    }

    @Test
    public void shouldSetLogoutUrlBuilderDefaultValuesAndClientId() throws Exception {
        AuthAPI api = new AuthAPI("domain.auth0.com", CLIENT_ID, CLIENT_SECRET);
        String url = api.logout("https://my.domain.com/welcome", true).build();
        HttpUrl parsed = HttpUrl.parse(url);

        MatcherAssert.assertThat(url, not(isEmptyOrNullString()));
        MatcherAssert.assertThat(parsed, is(notNullValue()));
        MatcherAssert.assertThat(parsed.scheme(), is("https"));
        MatcherAssert.assertThat(parsed.host(), is("domain.auth0.com"));
        MatcherAssert.assertThat(parsed.pathSegments().size(), is(2));
        MatcherAssert.assertThat(parsed.pathSegments().get(0), is("v2"));
        MatcherAssert.assertThat(parsed.pathSegments().get(1), is("logout"));

        MatcherAssert.assertThat(parsed.queryParameter("client_id"), is(CLIENT_ID));
        MatcherAssert.assertThat(parsed.queryParameter("returnTo"), is("https://my.domain.com/welcome"));
    }


    //UserInfo

    @Test
    public void shouldThrowWhenUserInfoAccessTokenIsNull() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'access token' cannot be null!");
        api.userInfo(null);
    }

    @Test
    public void shouldCreateUserInfoRequest() throws Exception {
        Request<UserInfo> request = api.userInfo("accessToken");
        assertThat(request, is(notNullValue()));

        server.userInfoResponse();
        UserInfo response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest.getMethod(), is("GET"));
        assertThat(recordedRequest.getPath(), is("/userinfo"));
        assertThat(recordedRequest.getHeader("Content-Type"), is("application/json"));
        assertThat(recordedRequest.getHeader("Authorization"), is("Bearer accessToken"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getValues(), is(notNullValue()));
        assertThat(response.getValues(), hasEntry("email_verified", (Object) false));
        assertThat(response.getValues(), hasEntry("email", (Object) "test.account@userinfo.com"));
        assertThat(response.getValues(), hasEntry("clientID", (Object) "q2hnj2iu..."));
        assertThat(response.getValues(), hasEntry("updated_at", (Object) "2016-12-05T15:15:40.545Z"));
        assertThat(response.getValues(), hasEntry("name", (Object) "test.account@userinfo.com"));
        assertThat(response.getValues(), hasEntry("picture", (Object) "https://s.gravatar.com/avatar/dummy.png"));
        assertThat(response.getValues(), hasEntry("user_id", (Object) "auth0|58454..."));
        assertThat(response.getValues(), hasEntry("nickname", (Object) "test.account"));
        assertThat(response.getValues(), hasEntry("created_at", (Object) "2016-12-05T11:16:59.640Z"));
        assertThat(response.getValues(), hasEntry("sub", (Object) "auth0|58454..."));
        assertThat(response.getValues(), hasKey("identities"));
        List<Map<String, Object>> identities = (List<Map<String, Object>>) response.getValues().get("identities");
        assertThat(identities, hasSize(1));
        assertThat(identities.get(0), hasEntry("user_id", (Object) "58454..."));
        assertThat(identities.get(0), hasEntry("provider", (Object) "auth0"));
        assertThat(identities.get(0), hasEntry("connection", (Object) "Username-Password-Authentication"));
        assertThat(identities.get(0), hasEntry("isSocial", (Object) false));
    }


    //Reset Password

    @Test
    public void shouldThrowWhenResetPasswordEmailIsNull() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'email' cannot be null!");
        api.resetPassword(null, "my-connection");
    }

    @Test
    public void shouldThrowWhenResetPasswordConnectionIsNull() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'connection' cannot be null!");
        api.resetPassword("me@auth0.com", null);
    }

    @Test
    public void shouldCreateResetPasswordRequest() throws Exception {
        Request<Void> request = api.resetPassword("me@auth0.com", "db-connection");
        assertThat(request, is(notNullValue()));

        server.resetPasswordRequest();
        Void response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest.getMethod(), is("POST"));
        assertThat(recordedRequest.getPath(), is("/dbconnections/change_password"));
        assertThat(recordedRequest.getHeader("Content-Type"), is("application/json"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body, hasEntry("email", (Object) "me@auth0.com"));
        assertThat(body, hasEntry("connection", (Object) "db-connection"));
        assertThat(body, hasEntry("client_id", (Object) CLIENT_ID));
        assertThat(body, not(hasKey("password")));

        assertThat(response, is(nullValue()));
    }


    //Sign Up

    @Test
    public void shouldThrowWhenSignUpEmailIsNull() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'email' cannot be null!");
        api.signUp(null, "p455w0rd", "my-connection");
    }

    @Test
    public void shouldThrowWhenSignUpPasswordIsNull() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'password' cannot be null!");
        api.signUp("me@auth0.com", null, "my-connection");
    }

    @Test
    public void shouldThrowWhenSignUpConnectionIsNull() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'connection' cannot be null!");
        api.signUp("me@auth0.com", "p455w0rd", null);
    }

    @Test
    public void shouldThrowWhenUsernameSignUpEmailIsNull() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'email' cannot be null!");
        api.signUp(null, "me", "p455w0rd", "my-connection");
    }

    @Test
    public void shouldThrowWhenUsernameSignUpUsernameIsNull() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'username' cannot be null!");
        api.signUp("me@auth0.com", null, "p455w0rd", "my-connection");
    }

    @Test
    public void shouldThrowWhenUsernameSignUpPasswordIsNull() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'password' cannot be null!");
        api.signUp("me@auth0.com", "me", null, "my-connection");
    }

    @Test
    public void shouldThrowWhenUsernameSignUpConnectionIsNull() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'connection' cannot be null!");
        api.signUp("me@auth0.com", "me", "p455w0rd", null);
    }

    @Test
    public void shouldCreateSignUpRequestWithUsername() throws Exception {
        Request<Void> request = api.signUp("me@auth0.com", "me", "p455w0rd", "db-connection");
        assertThat(request, is(notNullValue()));

        server.signUpRequest();
        Void response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest.getMethod(), is("POST"));
        assertThat(recordedRequest.getPath(), is("/dbconnections/signup"));
        assertThat(recordedRequest.getHeader("Content-Type"), is("application/json"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body, hasEntry("email", (Object) "me@auth0.com"));
        assertThat(body, hasEntry("username", (Object) "me"));
        assertThat(body, hasEntry("password", (Object) "p455w0rd"));
        assertThat(body, hasEntry("connection", (Object) "db-connection"));
        assertThat(body, hasEntry("client_id", (Object) CLIENT_ID));

        assertThat(response, is(nullValue()));
    }

    @Test
    public void shouldCreateSignUpRequest() throws Exception {
        SignUpRequest request = api.signUp("me@auth0.com", "p455w0rd", "db-connection");
        assertThat(request, is(notNullValue()));

        server.signUpRequest();
        Void response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest.getMethod(), is("POST"));
        assertThat(recordedRequest.getPath(), is("/dbconnections/signup"));
        assertThat(recordedRequest.getHeader("Content-Type"), is("application/json"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body, hasEntry("email", (Object) "me@auth0.com"));
        assertThat(body, hasEntry("password", (Object) "p455w0rd"));
        assertThat(body, hasEntry("connection", (Object) "db-connection"));
        assertThat(body, hasEntry("client_id", (Object) CLIENT_ID));
        assertThat(body, not(hasKey("username")));

        assertThat(response, is(nullValue()));
    }

    @Test
    public void shouldCreateSignUpRequestWithCustomParameters() throws Exception {
        SignUpRequest request = api.signUp("me@auth0.com", "p455w0rd", "db-connection");
        assertThat(request, is(notNullValue()));
        Map<String, String> customFields = new HashMap<>();
        customFields.put("age", "25");
        customFields.put("address", "123, fake street");
        request.setCustomFields(customFields);

        server.signUpRequest();
        Void response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest.getMethod(), is("POST"));
        assertThat(recordedRequest.getPath(), is("/dbconnections/signup"));
        assertThat(recordedRequest.getHeader("Content-Type"), is("application/json"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body, hasEntry("email", (Object) "me@auth0.com"));
        assertThat(body, hasEntry("password", (Object) "p455w0rd"));
        assertThat(body, hasEntry("connection", (Object) "db-connection"));
        assertThat(body, hasEntry("client_id", (Object) CLIENT_ID));
        assertThat(body, hasKey("user_metadata"));
        Map<String, String> metadata = (Map<String, String>) body.get("user_metadata");
        assertThat(metadata, hasEntry("age", "25"));
        assertThat(metadata, hasEntry("address", "123, fake street"));
        assertThat(body, not(hasKey("username")));

        assertThat(response, is(nullValue()));
    }

    //Log In with AuthorizationCode Grant

    @Test
    public void shouldThrowWhenLogInWithAuthorizationCodeGrantCodeIsNull() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'code' cannot be null!");
        api.loginWithAuthorizationCode(null);
    }

    @Test
    public void shouldThrowWhenLogInWithAuthorizationCodeGrantAndRedirectUriCodeIsNull() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'code' cannot be null!");
        api.loginWithAuthorizationCode(null, "https://domain.auth0.com/callback");
    }

    @Test
    public void shouldThrowWhenLogInWithAuthorizationCodeGrantAndRedirectUriRedirectUriIsNull() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'redirect uri' cannot be null!");
        api.loginWithAuthorizationCode("code", null);
    }

    @Test
    public void shouldCreateLogInWithAuthorizationCodeGrantRequestWithRedirectUri() throws Exception {
        AuthRequest request = api.loginWithAuthorizationCode("code123", "https://domain.auth0.com/callback");
        assertThat(request, is(notNullValue()));

        server.loginRequest();
        TokenHolder response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest.getMethod(), is("POST"));
        assertThat(recordedRequest.getPath(), is("/oauth/token"));
        assertThat(recordedRequest.getHeader("Content-Type"), is("application/json"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body, hasEntry("code", (Object) "code123"));
        assertThat(body, hasEntry("redirect_uri", (Object) "https://domain.auth0.com/callback"));
        assertThat(body, hasEntry("grant_type", (Object) "authorization_code"));
        assertThat(body, hasEntry("client_id", (Object) CLIENT_ID));
        assertThat(body, hasEntry("client_secret", (Object) CLIENT_SECRET));

        assertThat(response, is(notNullValue()));
        assertThat(response.getAccessToken(), not(isEmptyOrNullString()));
        assertThat(response.getIdToken(), not(isEmptyOrNullString()));
        assertThat(response.getRefreshToken(), not(isEmptyOrNullString()));
        assertThat(response.getTokenType(), not(isEmptyOrNullString()));
        assertThat(response.getExpiresIn(), is(notNullValue()));
    }

    @Test
    public void shouldCreateLogInWithAuthorizationCodeGrantRequest() throws Exception {
        AuthRequest request = api.loginWithAuthorizationCode("code123");
        assertThat(request, is(notNullValue()));

        server.loginRequest();
        TokenHolder response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest.getMethod(), is("POST"));
        assertThat(recordedRequest.getPath(), is("/oauth/token"));
        assertThat(recordedRequest.getHeader("Content-Type"), is("application/json"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body, hasEntry("code", (Object) "code123"));
        assertThat(body, hasEntry("grant_type", (Object) "authorization_code"));
        assertThat(body, hasEntry("client_id", (Object) CLIENT_ID));
        assertThat(body, hasEntry("client_secret", (Object) CLIENT_SECRET));
        assertThat(body, not(hasKey("redirect_uri")));

        assertThat(response, is(notNullValue()));
        assertThat(response.getAccessToken(), not(isEmptyOrNullString()));
        assertThat(response.getIdToken(), not(isEmptyOrNullString()));
        assertThat(response.getRefreshToken(), not(isEmptyOrNullString()));
        assertThat(response.getTokenType(), not(isEmptyOrNullString()));
        assertThat(response.getExpiresIn(), is(notNullValue()));
    }

    @Test
    public void shouldCreateLogInWithAuthorizationCodeGrantRequestWithCustomParameters() throws Exception {
        AuthRequest request = api.loginWithAuthorizationCode("code123");
        assertThat(request, is(notNullValue()));
        request.setAudience("https://myapi.auth0.com/users");
        request.setRealm("dbconnection");
        request.setScope("profile photos contacts");

        server.loginRequest();
        TokenHolder response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest.getMethod(), is("POST"));
        assertThat(recordedRequest.getPath(), is("/oauth/token"));
        assertThat(recordedRequest.getHeader("Content-Type"), is("application/json"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body, hasEntry("code", (Object) "code123"));
        assertThat(body, hasEntry("grant_type", (Object) "authorization_code"));
        assertThat(body, hasEntry("client_id", (Object) CLIENT_ID));
        assertThat(body, hasEntry("client_secret", (Object) CLIENT_SECRET));
        assertThat(body, not(hasKey("redirect_uri")));
        assertThat(body, hasEntry("audience", (Object) "https://myapi.auth0.com/users"));
        assertThat(body, hasEntry("realm", (Object) "dbconnection"));
        assertThat(body, hasEntry("scope", (Object) "profile photos contacts"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getAccessToken(), not(isEmptyOrNullString()));
        assertThat(response.getIdToken(), not(isEmptyOrNullString()));
        assertThat(response.getRefreshToken(), not(isEmptyOrNullString()));
        assertThat(response.getTokenType(), not(isEmptyOrNullString()));
        assertThat(response.getExpiresIn(), is(notNullValue()));
    }


    //Log In with Password grant

    @Test
    public void shouldThrowWhenLogInWithPasswordUsernameIsNull() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'email or username' cannot be null!");
        api.loginWithPassword(null, "p455w0rd", "https://myapi.auth0.com/users");
    }

    @Test
    public void shouldThrowWhenLogInWithPasswordPasswordIsNull() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'password' cannot be null!");
        api.loginWithPassword("me", null, "https://myapi.auth0.com/users");
    }

    @Test
    public void shouldThrowWhenLogInWithPasswordAudienceIsNull() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'audience' cannot be null!");
        api.loginWithPassword("me", "p455w0rd", null);
    }

    @Test
    public void shouldCreateLogInWithPasswordGrantRequest() throws Exception {
        AuthRequest request = api.loginWithPassword("me", "p455w0rd", "https://myapi.auth0.com/users");
        assertThat(request, is(notNullValue()));

        server.loginRequest();
        TokenHolder response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest.getMethod(), is("POST"));
        assertThat(recordedRequest.getPath(), is("/oauth/token"));
        assertThat(recordedRequest.getHeader("Content-Type"), is("application/json"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body, hasEntry("grant_type", (Object) "password"));
        assertThat(body, hasEntry("client_id", (Object) CLIENT_ID));
        assertThat(body, hasEntry("client_secret", (Object) CLIENT_SECRET));
        assertThat(body, hasEntry("username", (Object) "me"));
        assertThat(body, hasEntry("password", (Object) "p455w0rd"));
        assertThat(body, hasEntry("audience", (Object) "https://myapi.auth0.com/users"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getAccessToken(), not(isEmptyOrNullString()));
        assertThat(response.getIdToken(), not(isEmptyOrNullString()));
        assertThat(response.getRefreshToken(), not(isEmptyOrNullString()));
        assertThat(response.getTokenType(), not(isEmptyOrNullString()));
        assertThat(response.getExpiresIn(), is(notNullValue()));
    }

    @Test
    public void shouldCreateLogInWithPasswordGrantRequestWithCustomParameters() throws Exception {
        AuthRequest request = api.loginWithPassword("me", "p455w0rd", "https://myapi.auth0.com/users");
        assertThat(request, is(notNullValue()));
        request.setAudience("https://myapi.auth0.com/users");
        request.setRealm("dbconnection");
        request.setScope("profile photos contacts");

        server.loginRequest();
        TokenHolder response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest.getMethod(), is("POST"));
        assertThat(recordedRequest.getPath(), is("/oauth/token"));
        assertThat(recordedRequest.getHeader("Content-Type"), is("application/json"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body, hasEntry("grant_type", (Object) "password"));
        assertThat(body, hasEntry("client_id", (Object) CLIENT_ID));
        assertThat(body, hasEntry("client_secret", (Object) CLIENT_SECRET));
        assertThat(body, hasEntry("username", (Object) "me"));
        assertThat(body, hasEntry("password", (Object) "p455w0rd"));
        assertThat(body, hasEntry("audience", (Object) "https://myapi.auth0.com/users"));
        assertThat(body, hasEntry("realm", (Object) "dbconnection"));
        assertThat(body, hasEntry("scope", (Object) "profile photos contacts"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getAccessToken(), not(isEmptyOrNullString()));
        assertThat(response.getIdToken(), not(isEmptyOrNullString()));
        assertThat(response.getRefreshToken(), not(isEmptyOrNullString()));
        assertThat(response.getTokenType(), not(isEmptyOrNullString()));
        assertThat(response.getExpiresIn(), is(notNullValue()));
    }


    // Utils

    private Map<String, Object> bodyFromRequest(RecordedRequest request) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        MapType mapType = mapper.getTypeFactory().constructMapType(HashMap.class, String.class, Object.class);
        Buffer body = request.getBody();
        try {
            return mapper.readValue(body.inputStream(), mapType);
        } catch (IOException e) {
            throw e;
        } finally {
            body.close();
        }
    }


}
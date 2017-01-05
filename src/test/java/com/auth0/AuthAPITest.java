package com.auth0;

import com.auth0.json.UserInfo;
import com.auth0.net.CustomRequest;
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
    public void shouldCreateUserInfoRequest() throws Exception {
        CustomRequest<UserInfo> request = (CustomRequest<UserInfo>) api.userInfo("accessToken");
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

    private Map<String, String> bodyFromRequest(RecordedRequest request) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        MapType mapType = mapper.getTypeFactory().constructMapType(HashMap.class, String.class, String.class);
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
package com.auth0.authentication;

import com.auth0.authentication.result.Credentials;
import com.google.gson.JsonParseException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.FileReader;
import java.io.IOException;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class CredentialsGsonTest extends GsonBaseTest {
    private static final String OPEN_ID_OFFLINE_ACCESS_CREDENTIALS = "src/test/resources/credentials_openid_refresh_token.json";
    private static final String OPEN_ID_CREDENTIALS = "src/test/resources/credentials_openid.json";
    private static final String BASIC_CREDENTIALS = "src/test/resources/credentials.json";

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        gson = GsonProvider.buildGson();
    }

    @Test
    public void shouldFailWithEmptyJsonCredentials() throws Exception {
        expectedException.expect(JsonParseException.class);
        buildCredentialsFrom(json(EMPTY_OBJECT));
    }

    @Test
    public void shouldFailWithInvalidJsonCredentials() throws Exception {
        expectedException.expect(JsonParseException.class);
        buildCredentialsFrom(json(INVALID));
    }

    @Test
    public void shouldReturnBasicCredentials() throws Exception {
        final Credentials credentials = buildCredentialsFrom(json(BASIC_CREDENTIALS));
        assertThat(credentials, is(notNullValue()));
        assertThat(credentials.getAccessToken(), is(notNullValue()));
        assertThat(credentials.getIdToken(), is(nullValue()));
        assertThat(credentials.getType(), equalTo("bearer"));
        assertThat(credentials.getRefreshToken(), is(nullValue()));
    }

    @Test
    public void shouldReturnCredentialsWithIdToken() throws Exception {
        final Credentials credentials = buildCredentialsFrom(json(OPEN_ID_CREDENTIALS));
        assertThat(credentials, is(notNullValue()));
        assertThat(credentials.getAccessToken(), is(notNullValue()));
        assertThat(credentials.getIdToken(), is(notNullValue()));
        assertThat(credentials.getType(), equalTo("bearer"));
        assertThat(credentials.getRefreshToken(), is(nullValue()));
    }

    @Test
    public void shouldReturnCredentialsWithRefreshToken() throws Exception {
        final Credentials credentials = buildCredentialsFrom(json(OPEN_ID_OFFLINE_ACCESS_CREDENTIALS));
        assertThat(credentials, is(notNullValue()));
        assertThat(credentials.getAccessToken(), is(notNullValue()));
        assertThat(credentials.getIdToken(), is(notNullValue()));
        assertThat(credentials.getType(), equalTo("bearer"));
        assertThat(credentials.getRefreshToken(), is(notNullValue()));
    }

    private Credentials buildCredentialsFrom(FileReader json) throws IOException {
        return pojoFrom(json, Credentials.class);
    }

}

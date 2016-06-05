package com.auth0.authentication;

import com.auth0.authentication.result.Credentials;
import com.auth0.authentication.result.UserIdentity;
import com.auth0.authentication.result.UserProfile;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;

import static com.auth0.authentication.GsonProviderTest.UserIdentityMatcher.isUserIdentity;
import static com.auth0.authentication.GsonProviderTest.UserProfileMatcher.isNormalizedProfile;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class GsonProviderTest {

    private static final String BASIC_CREDENTIALS = "src/test/resources/credentials.json";
    private static final String OPEN_ID_CREDENTIALS = "src/test/resources/credentials_openid.json";
    private static final String OPEN_ID_OFFLINE_ACCESS_CREDENTIALS = "src/test/resources/credentials_openid_refresh_token.json";
    private static final String PROFILE = "src/test/resources/profile.json";
    private static final String PROFILE_FULL = "src/test/resources/profile_full.json";
    private static final String ID = "auth0|1234567890";
    private static final String NAME = "info @ auth0";
    private static final String NICKNAME = "a0";
    private static final String EMPTY_OBJECT = "src/test/resources/empty_object.json";
    private static final String INVALID = "src/test/resources/invalid.json";

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private Gson gson;

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

    @Test
    public void shouldFailWithInvalidJsonProfile() throws Exception {
        expectedException.expect(JsonParseException.class);
        pojoFrom(json(INVALID), UserProfile.class);
    }

    @Test
    public void shouldFailWithEmptyJsonProfile() throws Exception {
        expectedException.expect(JsonParseException.class);
        pojoFrom(json(EMPTY_OBJECT), UserProfile.class);
    }

    @Test
    public void shouldReturnNormalizedProfile() throws Exception {
        UserProfile profile = pojoFrom(json(PROFILE), UserProfile.class);
        assertThat(profile, isNormalizedProfile(ID, NAME, NICKNAME));
        assertThat(profile.getIdentities(), hasSize(1));
        assertThat(profile.getIdentities(), hasItem(isUserIdentity("1234567890", "auth0", "Username-Password-Authentication")));
        assertThat(profile.getUserMetadata(), anEmptyMap());
        assertThat(profile.getAppMetadata(), anEmptyMap());
    }

    @Test
    public void shouldReturnProfileWithOptionalFields() throws Exception {
        UserProfile profile = pojoFrom(json(PROFILE_FULL), UserProfile.class);
        assertThat(profile, isNormalizedProfile(ID, NAME, NICKNAME));
        assertThat(profile.getEmail(), equalTo("info@auth0.com"));
        assertThat(profile.getCreatedAt(), equalTo(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse("2014-07-06T18:33:49.005Z")));
    }

    @Test
    public void shouldReturnProfileWithMultipleIdentities() throws Exception {
        UserProfile profile = pojoFrom(json(PROFILE_FULL), UserProfile.class);
        assertThat(profile, isNormalizedProfile(ID, NAME, NICKNAME));
        assertThat(profile.getIdentities(), hasItem(isUserIdentity("1234567890", "auth0", "Username-Password-Authentication")));
        assertThat(profile.getIdentities(), hasItem(isUserIdentity("999997950999976", "facebook", "facebook")));
    }

    @Test
    public void shouldReturnProfileWithMetadata() throws Exception {
        UserProfile profile = pojoFrom(json(PROFILE_FULL), UserProfile.class);
        assertThat(profile, isNormalizedProfile(ID, NAME, NICKNAME));
        assertThat(profile.getUserMetadata(), hasEntry("first_name", (Object)"Info"));
        assertThat(profile.getUserMetadata(), hasEntry("last_name", (Object)"Auth0"));
        assertThat(profile.getUserMetadata(), hasEntry("first_name", (Object)"Info"));
        assertThat(profile.getAppMetadata(), hasEntry("role", (Object)"admin"));
        assertThat(profile.getAppMetadata(), hasEntry("tier", (Object)2.0));
        assertThat(profile.getAppMetadata(), hasEntry("blocked", (Object)false));
    }

    private Credentials buildCredentialsFrom(FileReader json) throws IOException {
        return pojoFrom(json, Credentials.class);
    }

    private <T> T pojoFrom(FileReader json, Class<T> clazz) throws IOException {
        return gson.getAdapter(clazz).fromJson(json);
    }

    private FileReader json(String name) throws FileNotFoundException {
        return new FileReader(name);
    }

    static class UserProfileMatcher extends AttributeMatcher<UserProfile> {

        private final Matcher<String> idMatcher;
        private final Matcher<String> nameMatcher;
        private final Matcher<String> nicknameMatcher;
        private final Matcher<String> pictureMatcher;

        private UserProfileMatcher(Matcher<String> idMatcher, Matcher<String> nameMatcher, Matcher<String> nicknameMatcher, Matcher<String> pictureMatcher) {
            this.idMatcher = idMatcher;
            this.nameMatcher = nameMatcher;
            this.nicknameMatcher = nicknameMatcher;
            this.pictureMatcher = pictureMatcher;
        }

        @Override
        protected boolean matchesSafely(UserProfile profile, Description mismatchDescription) {
            return performMatch(idMatcher, profile.getId(), mismatchDescription, "id")
                    && performMatch(nameMatcher, profile.getName(), mismatchDescription, "name")
                    && performMatch(nicknameMatcher, profile.getNickname(), mismatchDescription, "nickname")
                    && performMatch(pictureMatcher, profile.getPictureURL(), mismatchDescription, "picture");
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("a normalized profile with ")
                    .appendText("id: ").appendDescriptionOf(idMatcher).appendText(" ")
                    .appendText("name: ").appendDescriptionOf(nameMatcher).appendText(" ")
                    .appendText("nickname: ").appendDescriptionOf(nicknameMatcher).appendText(" ")
                    .appendText("picture: ").appendDescriptionOf(pictureMatcher);
        }

        static Matcher<UserProfile> isNormalizedProfile(String id, String name, String nickname) {
            return new UserProfileMatcher(equalTo(id), equalTo(name), equalTo(nickname), not(isEmptyOrNullString()));
        }
    }

    static class UserIdentityMatcher extends AttributeMatcher<UserIdentity> {

        private final Matcher<String> idMatcher;
        private final Matcher<String> providerMatcher;
        private final Matcher<String> connectionMatcher;

        private UserIdentityMatcher(Matcher<String> idMatcher, Matcher<String> providerMatcher, Matcher<String> connectionMatcher) {
            this.idMatcher = idMatcher;
            this.providerMatcher = providerMatcher;
            this.connectionMatcher = connectionMatcher;
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("a user identity with ")
                    .appendText("id: ").appendDescriptionOf(idMatcher).appendText(" ")
                    .appendText("provider: ").appendDescriptionOf(providerMatcher).appendText(" ")
                    .appendText("connection: ").appendDescriptionOf(connectionMatcher);
        }

        @Override
        protected boolean matchesSafely(UserIdentity identity, Description mismatchDescription) {
            return performMatch(idMatcher, identity.getId(), mismatchDescription, "id")
                    && performMatch(providerMatcher, identity.getProvider(), mismatchDescription, "provider")
                    && performMatch(connectionMatcher, identity.getConnection(), mismatchDescription, "connection");
        }

        static Matcher<UserIdentity> isUserIdentity(String id, String provider, String connection) {
            return new UserIdentityMatcher(equalTo(id), equalTo(provider), equalTo(connection));
        }
    }

    static abstract class AttributeMatcher<T> extends TypeSafeDiagnosingMatcher<T> {
        <E> boolean performMatch(Matcher<E> matcher, E value, Description description, String attributeName) {
            boolean matches = matcher.matches(value);
            if (!matches) {
                description.appendText(attributeName).appendText(" ");
                matcher.describeMismatch(value, description);
                description.appendText(" and not expected: ").appendDescriptionOf(matcher);
            }
            return matches;
        }
    }
}
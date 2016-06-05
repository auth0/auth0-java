package com.auth0.authentication;

import com.auth0.authentication.result.UserProfile;
import com.google.gson.JsonParseException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.text.SimpleDateFormat;

import static com.auth0.util.UserIdentityMatcher.isUserIdentity;
import static com.auth0.util.UserProfileMatcher.isNormalizedProfile;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.hasEntry;
import static org.junit.Assert.assertThat;

public class UserProfileGsonTest extends GsonBaseTest {
    private static final String NICKNAME = "a0";
    private static final String NAME = "info @ auth0";
    private static final String ID = "auth0|1234567890";
    private static final String PROFILE_FULL = "src/test/resources/profile_full.json";
    private static final String PROFILE = "src/test/resources/profile.json";

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        gson = GsonProvider.buildGson();
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

}

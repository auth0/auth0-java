package com.auth0.authentication;

import com.auth0.authentication.result.UserIdentity;
import com.google.gson.JsonParseException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static com.auth0.util.UserIdentityMatcher.isUserIdentity;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class UserIdentityGsonTest extends GsonBaseTest {

    private static final String AUTH0 = "src/test/resources/identity_auth0.json";
    private static final String FACEBOOK = "src/test/resources/identity_facebook.json";

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        gson = GsonProvider.buildGson();
    }

    @Test
    public void shouldFailWithInvalidJson() throws Exception {
        expectedException.expect(JsonParseException.class);
        pojoFrom(json(INVALID), UserIdentity.class);
    }

    @Test
    public void shouldFailWithEmptyJson() throws Exception {
        expectedException.expect(JsonParseException.class);
        pojoFrom(json(EMPTY_OBJECT), UserIdentity.class);
    }

    @Test
    public void shouldBuildBasic() throws Exception {
        UserIdentity identity = pojoFrom(json(AUTH0), UserIdentity.class);
        assertThat(identity, isUserIdentity("1234567890", "auth0", "Username-Password-Authentication"));
        assertThat(identity.getProfileInfo(), anEmptyMap());
        assertThat(identity.isSocial(), is(false));
        assertThat(identity.getAccessToken(), nullValue());
        assertThat(identity.getAccessTokenSecret(), nullValue());
    }

    @Test
    public void shouldBuildWithExtraValues() throws Exception {
        UserIdentity identity = pojoFrom(json(FACEBOOK), UserIdentity.class);
        assertThat(identity, isUserIdentity("999997950999976", "facebook", "facebook"));
        assertThat(identity.getProfileInfo(), hasEntry("given_name", (Object)"John"));
        assertThat(identity.getProfileInfo(), hasEntry("family_name", (Object)"Foobar"));
        assertThat(identity.getProfileInfo(), hasEntry("email_verified", (Object)true));
        assertThat(identity.getProfileInfo(), hasEntry("gender", (Object)"male"));
    }

}

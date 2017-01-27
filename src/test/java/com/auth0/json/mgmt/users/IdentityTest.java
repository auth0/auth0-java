package com.auth0.json.mgmt.users;

import com.auth0.json.JsonTest;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class IdentityTest extends JsonTest<Identity> {

    private static final String json = "{\"connection\":\"auth0\",\"user_id\":\"user|123\",\"isSocial\":true,\"provider\":\"oauth\",\"access_token\":\"aTokEn\",\"profileData\":{}}";

    @Test
    public void shouldDeserialize() throws Exception {
        Identity identity = fromJSON(json, Identity.class);

        assertThat(identity, is(notNullValue()));
        assertThat(identity.getConnection(), is("auth0"));
        assertThat(identity.getUserId(), is("user|123"));
        assertThat(identity.isSocial(), is(true));
        assertThat(identity.getProvider(), is("oauth"));
        assertThat(identity.getAccessToken(), is("aTokEn"));
        assertThat(identity.getProfileData(), is(notNullValue()));
    }
}
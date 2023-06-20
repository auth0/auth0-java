package com.auth0.json.mgmt.users;

import com.auth0.json.JsonTest;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.collection.IsMapContaining.hasEntry;

public class ProfileDataTest extends JsonTest<ProfileData> {

    private static final String json = "{\"email\":\"me@auth0.com\",\"email_verified\":true,\"name\":\"John\", \"username\":\"usr\", \"given_name\":\"John\", \"family_name\":\"Walker\", \"phone_number\":\"1234567890\", \"phone_verified\":true, \"description\":\"My description\"}";

    @Test
    public void shouldDeserialize() throws Exception {
        ProfileData data = fromJSON(json, ProfileData.class);

        assertThat(data, is(notNullValue()));
        assertThat(data.getEmail(), is("me@auth0.com"));
        assertThat(data.isEmailVerified(), is(true));
        assertThat(data.getName(), is("John"));
        assertThat(data.getUsername(), is("usr"));
        assertThat(data.getGivenName(), is("John"));
        assertThat(data.getFamilyName(), is("Walker"));
        assertThat(data.getPhoneNumber(), is("1234567890"));
        assertThat(data.isPhoneVerified(), is(true));
        assertThat(data.getValues(), is(notNullValue()));
        assertThat(data.getValues(), hasEntry("description", "My description"));
    }
}

package com.auth0.json.auth;

import com.auth0.json.JsonTest;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class CreatedUserTest extends JsonTest<CreatedUser> {

    private static final String jsonStandard = "{\"email\":\"sample@email.com\",\"username\":\"john.doe\",\"email_verified\":true,\"_id\": \"from|_id\"}";
    private static final String jsonCustom = "{\"email\":\"sample@email.com\",\"username\":\"john.doe\",\"email_verified\":true,\"id\": \"from|id\"}";
    private static final String jsonCustomExternal = "{\"email\":\"sample@email.com\",\"username\":\"john.doe\",\"email_verified\":true,\"user_id\": \"from|user_id\"}";
    private static final String jsonMultiple = "{\"email\":\"sample@email.com\",\"username\":\"john.doe\",\"email_verified\":true,\"user_id\": \"from|user_id\",\"id\": \"from|id\",\"_id\": \"from|_id\"}";

    @Test
    public void shouldDeserializePreferringStandardUserId() throws Exception {
        CreatedUser user = fromJSON(jsonMultiple, CreatedUser.class);

        assertThat(user, is(notNullValue()));

        assertThat(user.getEmail(), is("sample@email.com"));
        assertThat(user.getUsername(), is("john.doe"));
        assertThat(user.isEmailVerified(), is(true));
        assertThat(user.getUserId(), is("from|_id"));
    }

    @Test
    public void shouldDeserializeStandardConnectionUser() throws Exception {
        CreatedUser user = fromJSON(jsonStandard, CreatedUser.class);

        assertThat(user, is(notNullValue()));

        assertThat(user.getEmail(), is("sample@email.com"));
        assertThat(user.getUsername(), is("john.doe"));
        assertThat(user.isEmailVerified(), is(true));
        assertThat(user.getUserId(), is("from|_id"));
    }

    @Test
    public void shouldDeserializeCustomConnectionUser() throws Exception {
        CreatedUser user = fromJSON(jsonCustom, CreatedUser.class);

        assertThat(user, is(notNullValue()));

        assertThat(user.getEmail(), is("sample@email.com"));
        assertThat(user.getUsername(), is("john.doe"));
        assertThat(user.isEmailVerified(), is(true));
        assertThat(user.getUserId(), is("from|id"));
    }

    @Test
    public void shouldDeserializeCustomExternalConnectionUser() throws Exception {
        CreatedUser user = fromJSON(jsonCustomExternal, CreatedUser.class);

        assertThat(user, is(notNullValue()));

        assertThat(user.getEmail(), is("sample@email.com"));
        assertThat(user.getUsername(), is("john.doe"));
        assertThat(user.isEmailVerified(), is(true));
        assertThat(user.getUserId(), is("from|user_id"));
    }
}

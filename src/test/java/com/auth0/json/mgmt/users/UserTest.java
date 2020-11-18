package com.auth0.json.mgmt.users;

import com.auth0.json.JsonMatcher;
import com.auth0.json.JsonTest;
import org.junit.Test;

import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.collection.IsMapContaining.hasEntry;

public class UserTest extends JsonTest<User> {

    private static final String json = "{\"user_id\":\"user|123\",\"connection\":\"auth0\",\"client_id\":\"client123\",\"password\":\"pwd\",\"verify_password\":true,\"username\":\"usr\",\"email\":\"me@auth0.com\",\"email_verified\":true,\"verify_email\":true,\"phone_number\":\"1234567890\",\"phone_verified\":true,\"verify_phone_number\":true,\"picture\":\"https://pic.ture/12\",\"name\":\"John\",\"nickname\":\"Johny\",\"given_name\":\"John\",\"family_name\":\"Walker\",\"app_metadata\":{},\"user_metadata\":{},\"blocked\":true,\"context\":\"extra information\"}";
    private static final String readOnlyJson = "{\"user_id\":\"user|123\",\"last_ip\":\"10.0.0.1\",\"last_login\":\"2016-02-23T19:57:29.532Z\",\"last_password_reset\":\"2016-02-23T19:57:29Z\",\"logins_count\":10,\"created_at\":\"2016-02-23T19:57:29.532Z\",\"updated_at\":\"2016-02-23T19:57:29.532Z\",\"identities\":[]}";

    @Test
    public void shouldHaveEmptyValuesByDefault() throws Exception{
        User user = new User();
        assertThat(user.getValues(), is(notNullValue()));

        User user2 = new User("my-connection");
        assertThat(user2.getConnection(), is("my-connection"));
        assertThat(user2.getValues(), is(notNullValue()));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void shouldSerialize() throws Exception {
        User user = new User("auth0");
        user.setId("123456789");
        user.setPassword("pwd");
        user.setVerifyPassword(true);
        user.setUsername("usr");
        user.setEmail("me@auth0.com");
        user.setClientId("client123");
        user.setVerifyEmail(true);
        user.setEmailVerified(true);
        user.setPhoneNumber("1234567890");
        user.setVerifyPhoneNumber(true);
        user.setPhoneVerified(true);
        user.setPicture("https://pic.ture/12");
        user.setName("John");
        user.setNickname("Johny");
        user.setGivenName("John");
        user.setFamilyName("Walker");
        user.setUserMetadata(Collections.emptyMap());
        user.setAppMetadata(Collections.emptyMap());
        user.setBlocked(true);

        String serialized = toJSON(user);
        assertThat(serialized, is(notNullValue()));

        assertThat(serialized, JsonMatcher.hasEntry("connection", "auth0"));
        assertThat(serialized, JsonMatcher.hasEntry("user_id", "123456789"));
        assertThat(serialized, JsonMatcher.hasEntry("password", "pwd"));
        assertThat(serialized, JsonMatcher.hasEntry("verify_password", true));
        assertThat(serialized, JsonMatcher.hasEntry("username", "usr"));
        assertThat(serialized, JsonMatcher.hasEntry("email", "me@auth0.com"));
        assertThat(serialized, JsonMatcher.hasEntry("verify_email", true));
        assertThat(serialized, JsonMatcher.hasEntry("email_verified", true));
        assertThat(serialized, JsonMatcher.hasEntry("client_id", "client123"));
        assertThat(serialized, JsonMatcher.hasEntry("phone_number", "1234567890"));
        assertThat(serialized, JsonMatcher.hasEntry("verify_phone_number", true));
        assertThat(serialized, JsonMatcher.hasEntry("phone_verified", true));
        assertThat(serialized, JsonMatcher.hasEntry("picture", "https://pic.ture/12"));
        assertThat(serialized, JsonMatcher.hasEntry("name", "John"));
        assertThat(serialized, JsonMatcher.hasEntry("nickname", "Johny"));
        assertThat(serialized, JsonMatcher.hasEntry("given_name", "John"));
        assertThat(serialized, JsonMatcher.hasEntry("family_name", "Walker"));
        assertThat(serialized, JsonMatcher.hasEntry("user_metadata", notNullValue()));
        assertThat(serialized, JsonMatcher.hasEntry("app_metadata", notNullValue()));
        assertThat(serialized, JsonMatcher.hasEntry("blocked", true));
    }

    @Test
    public void shouldDeserialize() throws Exception {
        User user = fromJSON(json, User.class);

        assertThat(user, is(notNullValue()));
        assertThat(user.getId(), is("user|123"));
        assertThat(user.getConnection(), is("auth0"));
        assertThat(user.getPassword(), is(new char[]{'p','w','d'}));
        assertThat(user.willVerifyPassword(), is(true));
        assertThat(user.getUsername(), is("usr"));
        assertThat(user.getEmail(), is("me@auth0.com"));
        assertThat(user.willVerifyEmail(), is(true));
        assertThat(user.getClientId(), is("client123"));
        assertThat(user.isEmailVerified(), is(true));
        assertThat(user.getPhoneNumber(), is("1234567890"));
        assertThat(user.willVerifyPhoneNumber(), is(true));
        assertThat(user.isPhoneVerified(), is(true));
        assertThat(user.getPicture(), is("https://pic.ture/12"));
        assertThat(user.getName(), is("John"));
        assertThat(user.getNickname(), is("Johny"));
        assertThat(user.getGivenName(), is("John"));
        assertThat(user.getFamilyName(), is("Walker"));
        assertThat(user.getUserMetadata(), is(notNullValue()));
        assertThat(user.getAppMetadata(), is(notNullValue()));
        assertThat(user.isBlocked(), is(true));
        assertThat(user.getValues(), is(notNullValue()));
        assertThat(user.getValues(), hasEntry("context", "extra information"));
    }

    @Test
    public void shouldIncludeReadOnlyValuesOnDeserialize() throws Exception {
        User user = fromJSON(readOnlyJson, User.class);
        assertThat(user, is(notNullValue()));

        assertThat(user.getId(), is("user|123"));
        assertThat(user.getCreatedAt(), is(parseJSONDate("2016-02-23T19:57:29.532Z")));
        assertThat(user.getUpdatedAt(), is(parseJSONDate("2016-02-23T19:57:29.532Z")));
        assertThat(user.getLastLogin(), is(parseJSONDate("2016-02-23T19:57:29.532Z")));
        assertThat(user.getLastPasswordReset(), is(parseJSONDate("2016-02-23T19:57:29.000Z")));
        assertThat(user.getIdentities(), is(notNullValue()));
        assertThat(user.getLastIP(), is("10.0.0.1"));
        assertThat(user.getLoginsCount(), is(10));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void shouldHandleNullPasswordString() {
        User user = new User();
        user.setPassword((String) null);

        assertThat(user.getPassword(), is(nullValue()));
    }

    @Test
    public void shouldHandleNullPasswordCharArray() {
        User user = new User();
        user.setPassword((char[]) null);

        assertThat(user.getPassword(), is(nullValue()));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void shouldGetPasswordAsCharArray() {
        String password = "secret";
        User user = new User();
        user.setPassword(password);

        assertThat(user.getPassword(), is(password.toCharArray()));
    }
}

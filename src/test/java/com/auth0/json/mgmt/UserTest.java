package com.auth0.json.mgmt;

import com.auth0.json.JsonTest;
import org.junit.Test;

import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class UserTest extends JsonTest<User> {

    private static final String json = "{\"connection\":\"auth0\",\"client_id\":\"client123\",\"password\":\"pwd\",\"verify_password\":true,\"username\":\"usr\",\"email\":\"me@auth0.com\",\"email_verified\":true,\"verify_email\":true,\"phone_number\":\"1234567890\",\"phone_verified\":true,\"verify_phone_number\":true,\"picture\":\"https://pic.ture/12\",\"name\":\"John\",\"nickname\":\"Johny\",\"given_name\":\"John\",\"family_name\":\"Walker\",\"created_at\":\"12:12:12\",\"updated_at\":\"12:12:12\",\"identities\":[],\"app_metadata\":{},\"user_metadata\":{},\"last_ip\":\"10.0.0.1\",\"last_login\":\"12:12:12\",\"logins_count\":10,\"blocked\":true}";
    private static final String readOnlyJson = "{\"user_id\":\"user|123\"}";

    @Test
    public void shouldSerialize() throws Exception {
        User user = new User("auth0");
        user.setPassword("pwd", true);
        user.setUsername("usr");
        user.setEmail("me@auth0.com", "client123", true);
        user.setEmailVerified(true);
        user.setPhoneNumber("1234567890", true);
        user.setPhoneVerified(true);
        user.setPicture("https://pic.ture/12");
        user.setName("John");
        user.setNickname("Johny");
        user.setGivenName("John");
        user.setFamilyName("Walker");
        user.setCreatedAt("12:12:12");
        user.setUpdatedAt("12:12:12");
        user.setIdentities(Collections.<Identity>emptyList());
        user.setUserMetadata(Collections.<String, Object>emptyMap());
        user.setAppMetadata(Collections.<String, Object>emptyMap());
        user.setLastIp("10.0.0.1");
        user.setLastLogin("12:12:12");
        user.setLoginsCount(10);
        user.setBlocked(true);

        String serialized = toJSON(user);
        assertThat(serialized, is(notNullValue()));
        assertThat(serialized, is(equalTo(json)));
    }

    @Test
    public void shouldDeserialize() throws Exception {
        User user = fromJSON(json, User.class);

        assertThat(user, is(notNullValue()));
        assertThat(user.getConnection(), is("auth0"));
        assertThat(user.getPassword(), is("pwd"));
        assertThat(user.getVerifyPassword(), is(true));
        assertThat(user.getUsername(), is("usr"));
        assertThat(user.getEmail(), is("me@auth0.com"));
        assertThat(user.getVerifyEmail(), is(true));
        assertThat(user.getClientId(), is("client123"));
        assertThat(user.getEmailVerified(), is(true));
        assertThat(user.getPhoneNumber(), is("1234567890"));
        assertThat(user.getVerifyPhoneNumber(), is(true));
        assertThat(user.getPhoneVerified(), is(true));
        assertThat(user.getPicture(), is("https://pic.ture/12"));
        assertThat(user.getName(), is("John"));
        assertThat(user.getNickname(), is("Johny"));
        assertThat(user.getGivenName(), is("John"));
        assertThat(user.getFamilyName(), is("Walker"));
        assertThat(user.getCreatedAt(), is("12:12:12"));
        assertThat(user.getUpdatedAt(), is("12:12:12"));
        assertThat(user.getIdentities(), is(notNullValue()));
        assertThat(user.getUserMetadata(), is(notNullValue()));
        assertThat(user.getAppMetadata(), is(notNullValue()));
        assertThat(user.getLastIp(), is("10.0.0.1"));
        assertThat(user.getLastLogin(), is("12:12:12"));
        assertThat(user.getLoginsCount(), is(10));
        assertThat(user.getBlocked(), is(true));
    }

    @Test
    public void shouldIncludeReadOnlyValuesOnDeserialize() throws Exception {
        User user = fromJSON(readOnlyJson, User.class);
        assertThat(user, is(notNullValue()));

        assertThat(user.getId(), is("user|123"));
    }
}
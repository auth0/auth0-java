package com.auth0.json.mgmt.authenticationmethods;

import com.auth0.json.JsonMatcher;
import com.auth0.json.JsonTest;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;

public class AuthenticationMethodTest extends JsonTest<AuthenticationMethod> {

    @Test
    public void shouldDeserialize() throws Exception {
        String orgJson = "{\n" +
            "      \"id\":\"email|dev_1cIsjj3hICWeOWTb\",\n" +
            "      \"type\":\"email\",\n" +
            "      \"confirmed\":true,\n" +
            "      \"email\":\"****@temp****\",\n" +
            "      \"name\":\"name\",\n" +
            "      \"link_id\":\"linkId\",\n" +
            "      \"phone_number\":\"1231212312\",\n" +
            "      \"key_id\":\"keyId\",\n" +
            "      \"public_key\":\"publicKey\",\n" +
            "      \"created_at\":\"2023-01-19T15:15:16.427Z\",\n" +
            "      \"enrolled_at\":\"2023-01-19T15:15:16.427Z\",\n" +
            "      \"last_auth_at\":\"2023-01-19T15:15:16.427Z\",\n" +
            "      \"last_auth_at\":\"2023-01-19T15:15:16.427Z\",\n" +
            "      \"totp_secret\":\"totp\",\n" +
            "      \"preferred_authentication_method\":\"phone\",\n" +
            "      \"relying_party_identifier\":\"abc\",\n" +
            "      \"authentication_methods\":[]\n" +
            "   }";


        AuthenticationMethod authenticationMethod = fromJSON(orgJson, AuthenticationMethod.class);
        assertThat(authenticationMethod, is(notNullValue()));
        assertThat(authenticationMethod.getId(), is("email|dev_1cIsjj3hICWeOWTb"));
        assertThat(authenticationMethod.getType(), is("email"));
        assertThat(authenticationMethod.getName(), is("name"));
        assertThat(authenticationMethod.getLinkId(), is("linkId"));
        assertThat(authenticationMethod.getPhoneNumber(), is("1231212312"));
        assertThat(authenticationMethod.getKeyId(), is("keyId"));
        assertThat(authenticationMethod.getPublicKey(), is("publicKey"));
        assertThat(authenticationMethod.getTotpSecret(), is("totp"));
        assertThat(authenticationMethod.getPreferredAuthenticationMethod(), is("phone"));
        assertThat(authenticationMethod.getRelyingPartyIdentifier(), is("abc"));
        assertThat(authenticationMethod.getConfirmed(), is(true));
        assertThat(authenticationMethod.getEmail(), is("****@temp****"));
        assertThat(authenticationMethod.getCreatedAt(), is(parseJSONDate(("2023-01-19T15:15:16.427Z"))));
        assertThat(authenticationMethod.getLastAuthedAt(), is(parseJSONDate(("2023-01-19T15:15:16.427Z"))));
        assertThat(authenticationMethod.getEnrolledAt(), is(parseJSONDate(("2023-01-19T15:15:16.427Z"))));
        assertThat(authenticationMethod.getAuthenticationMethods(), Matchers.is(notNullValue()));

    }

    @Test
    public void shouldSerialize() throws Exception {
        AuthenticationMethod authenticationMethod = new AuthenticationMethod();

        authenticationMethod.setType("phone");
        authenticationMethod.setName("abc");
        authenticationMethod.setPhoneNumber("1231212312");
        authenticationMethod.setKeyId("keyid");
        authenticationMethod.setPublicKey("pubkey");
        authenticationMethod.setTotpSecret("totp");
        authenticationMethod.setPreferredAuthenticationMethod("phone");
        authenticationMethod.setRelyingPartyIdentifier("123");
        authenticationMethod.setEmail("temp@temp.com");

        String serialized = toJSON(authenticationMethod);
        assertThat(serialized, is(notNullValue()));
        assertThat(serialized, JsonMatcher.hasEntry("type", "phone"));
        assertThat(serialized, JsonMatcher.hasEntry("name", "abc"));
        assertThat(serialized, JsonMatcher.hasEntry("phone_number", "1231212312"));
        assertThat(serialized, JsonMatcher.hasEntry("key_id", "keyid"));
        assertThat(serialized, JsonMatcher.hasEntry("public_key", "pubkey"));
        assertThat(serialized, JsonMatcher.hasEntry("totp_secret", "totp"));
        assertThat(serialized, JsonMatcher.hasEntry("preferred_authentication_method", "phone"));
        assertThat(serialized, JsonMatcher.hasEntry("relying_party_identifier", "123"));
        assertThat(serialized, JsonMatcher.hasEntry("email", "temp@temp.com"));
    }
}


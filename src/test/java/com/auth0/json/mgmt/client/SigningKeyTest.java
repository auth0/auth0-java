package com.auth0.json.mgmt.client;

import com.auth0.json.JsonMatcher;
import com.auth0.json.JsonTest;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class SigningKeyTest extends JsonTest<SigningKey> {

    private static final String json = "{\"cert\":\"cert\",\"pkcs7\":\"pkcs7\",\"subject\":\"subject\"}";

    @Test
    public void shouldSerialize() throws Exception {
        SigningKey key = new SigningKey("cert", "pkcs7", "subject");

        String serialized = toJSON(key);
        assertThat(serialized, is(notNullValue()));
        assertThat(serialized, JsonMatcher.hasEntry("cert", "cert"));
        assertThat(serialized, JsonMatcher.hasEntry("pkcs7", "pkcs7"));
        assertThat(serialized, JsonMatcher.hasEntry("subject", "subject"));
    }

    @Test
    public void shouldDeserialize() throws Exception {
        SigningKey key = fromJSON(json, SigningKey.class);

        assertThat(key, is(notNullValue()));
        assertThat(key.getCert(), is("cert"));
        assertThat(key.getPKCS7(), is("pkcs7"));
        assertThat(key.getSubject(), is("subject"));
    }
}
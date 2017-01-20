package com.auth0.json.mgmt.client;

import com.auth0.JsonMatcher;
import com.auth0.json.JsonTest;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class EncryptionKeyTest extends JsonTest<EncryptionKey> {

    private static final String json = "{\"pub\":\"pub\",\"cert\":\"cert\",\"subject\":\"subject\"}";

    @Test
    public void shouldSerialize() throws Exception {
        EncryptionKey key = new EncryptionKey("pub", "cert");
        key.setSubject("subject");

        String serialized = toJSON(key);
        assertThat(serialized, is(notNullValue()));
        assertThat(serialized, JsonMatcher.hasEntry("pub", "pub"));
        assertThat(serialized, JsonMatcher.hasEntry("cert", "cert"));
        assertThat(serialized, JsonMatcher.hasEntry("subject", "subject"));
    }

    @Test
    public void shouldDeserialize() throws Exception {
        EncryptionKey key = fromJSON(json, EncryptionKey.class);

        assertThat(key, is(notNullValue()));

        assertThat(key.getPub(), is("pub"));
        assertThat(key.getCert(), is("cert"));
        assertThat(key.getSubject(), is("subject"));
    }
}
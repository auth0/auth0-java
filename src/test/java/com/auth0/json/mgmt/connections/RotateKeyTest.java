package com.auth0.json.mgmt.connections;

import com.auth0.json.JsonMatcher;
import com.auth0.json.JsonTest;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class RotateKeyTest extends JsonTest<RotateKey> {
    private static final String json = "src/test/resources/mgmt/rotate_key.json";

    @Test
    public void shouldSerialize() throws Exception {
        RotateKey rotateKey = new RotateKey();
        rotateKey.setCert("-----BEGIN CERTIFICATE-----cert-key-----END CERTIFICATE-----");
        rotateKey.setKid("kid-1");

        String serialized = toJSON(rotateKey);
        assertThat(serialized, is(notNullValue()));
        assertThat(serialized, JsonMatcher.hasEntry("cert", "-----BEGIN CERTIFICATE-----cert-key-----END CERTIFICATE-----"));
        assertThat(serialized, JsonMatcher.hasEntry("kid", "kid-1"));
    }

    @Test
    public void shouldDeserialize() throws Exception {
        RotateKey deserialized = fromJSON(readTextFile(json), RotateKey.class);

        assertThat(deserialized, is(notNullValue()));
        assertThat(deserialized.getCert(), is("-----BEGIN CERTIFICATE-----cert-key-----END CERTIFICATE-----"));
        assertThat(deserialized.getKid(), is("kid-1"));

    }
}

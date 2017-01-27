package com.auth0.json.mgmt.client;

import com.auth0.json.JsonMatcher;
import com.auth0.json.JsonTest;
import org.hamcrest.collection.IsMapContaining;
import org.junit.Test;

import java.util.Collections;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class JWTConfigurationTest extends JsonTest<JWTConfiguration> {

    private static final String json = "{\"lifetime_in_seconds\":123,\"scopes\":{\"key\":\"value\"},\"alg\":\"alg\"}";
    private static final String readOnlyJson = "{\"secret_encoded\":true}";

    @Test
    public void shouldSerialize() throws Exception {
        JWTConfiguration config = new JWTConfiguration(123, Collections.singletonMap("key", "value"), "alg");

        String serialized = toJSON(config);
        assertThat(serialized, is(notNullValue()));
        assertThat(serialized, JsonMatcher.hasEntry("lifetime_in_seconds", 123));
        assertThat(serialized, JsonMatcher.hasEntry("scopes", Collections.singletonMap("key", "value")));
        assertThat(serialized, JsonMatcher.hasEntry("alg", "alg"));
    }

    @Test
    public void shouldDeserialize() throws Exception {
        JWTConfiguration config = fromJSON(json, JWTConfiguration.class);

        assertThat(config, is(notNullValue()));

        assertThat(config.getAlgorithm(), is("alg"));
        assertThat(config.getLifetimeInSeconds(), is(123));
        assertThat(config.getScopes(), is(notNullValue()));
        assertThat((Map<String, String>) config.getScopes(), IsMapContaining.hasEntry("key", "value"));
    }

    @Test
    public void shouldIncludeReadOnlyValuesOnDeserialize() throws Exception {
        JWTConfiguration config = fromJSON(readOnlyJson, JWTConfiguration.class);
        assertThat(config, is(notNullValue()));

        assertThat(config.isSecretEncoded(), is(true));
    }
}
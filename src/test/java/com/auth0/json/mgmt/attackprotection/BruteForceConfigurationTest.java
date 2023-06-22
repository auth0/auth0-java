package com.auth0.json.mgmt.attackprotection;

import com.auth0.json.JsonMatcher;
import com.auth0.json.JsonTest;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class BruteForceConfigurationTest extends JsonTest<BruteForceConfiguration> {

    String json = "{\n" +
        "  \"enabled\": false,\n" +
        "  \"shields\": [\n" +
        "    \"block\",\n" +
        "    \"user_notification\"\n" +
        "  ],\n" +
        "  \"allowlist\": [\n" +
        "    \"143.204.0.105\",\n" +
        "    \"2600:9000:208f:ca00:d:f5f5:b40:93a1\"\n" +
        "  ],\n" +
        "  \"mode\": \"count_per_identifier_and_ip\",\n" +
        "  \"max_attempts\": 10\n" +
        "}";

    @Test
    public void shouldSerialize() throws Exception {
        BruteForceConfiguration bruteForceConfiguration = new BruteForceConfiguration();
        bruteForceConfiguration.setEnabled(true);
        bruteForceConfiguration.setMaxAttempts(10);
        bruteForceConfiguration.setMode("count_per_identifier_and_ip");
        bruteForceConfiguration.setAllowList(Arrays.asList("123", "456"));
        bruteForceConfiguration.setShields(Arrays.asList("user_notification", "block"));

        String json = toJSON(bruteForceConfiguration);
        assertThat(json, JsonMatcher.hasEntry("enabled", true));
        assertThat(json, JsonMatcher.hasEntry("max_attempts", 10));
        assertThat(json, JsonMatcher.hasEntry("mode", "count_per_identifier_and_ip"));
        assertThat(json, JsonMatcher.hasEntry("allowlist", Arrays.asList("123", "456")));
        assertThat(json, JsonMatcher.hasEntry("shields", Arrays.asList("user_notification", "block")));
    }

    @Test
    public void shouldDeserialize() throws Exception {
        BruteForceConfiguration bruteForceConfiguration = fromJSON(json, BruteForceConfiguration.class);

        assertThat(bruteForceConfiguration, notNullValue());
        assertThat(bruteForceConfiguration.getEnabled(), is(false));
        assertThat(bruteForceConfiguration.getMode(), is("count_per_identifier_and_ip"));
        assertThat(bruteForceConfiguration.getMaxAttempts(), is(10));
        assertThat(bruteForceConfiguration.getShields(), is(notNullValue()));
        assertThat(bruteForceConfiguration.getShields().size(), is(2));
        assertThat(bruteForceConfiguration.getShields(), contains("block", "user_notification"));
        assertThat(bruteForceConfiguration.getAllowList(), is(notNullValue()));
        assertThat(bruteForceConfiguration.getAllowList().size(), is(2));
        assertThat(bruteForceConfiguration.getAllowList(), contains("143.204.0.105", "2600:9000:208f:ca00:d:f5f5:b40:93a1"));
    }
}

package com.auth0.json.mgmt.attackprotection;

import com.auth0.json.JsonMatcher;
import com.auth0.json.JsonTest;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class SuspiciousIPThrottlingConfigurationTest extends JsonTest<SuspiciousIPThrottlingConfiguration> {

    private final String json = "{\n" +
        "  \"enabled\": false,\n" +
        "  \"shields\": [\n" +
        "    \"block\",\n" +
        "    \"admin_notification\"\n" +
        "  ],\n" +
        "  \"allowlist\": [\n" +
        "    \"143.204.0.105\",\n" +
        "    \"2600:9000:208f:ca00:d:f5f5:b40:93a1\"\n" +
        "  ],\n" +
        "  \"stage\": {\n" +
        "    \"pre-login\": {\n" +
        "      \"max_attempts\": 100,\n" +
        "      \"rate\": 864000\n" +
        "    },\n" +
        "    \"pre-user-registration\": {\n" +
        "      \"max_attempts\": 50,\n" +
        "      \"rate\": 1728000\n" +
        "    }\n" +
        "  }\n" +
        "}";

    @Test
    public void shouldDeserialize() throws Exception {
        SuspiciousIPThrottlingConfiguration config = fromJSON(json, SuspiciousIPThrottlingConfiguration.class);

        assertThat(config, is(notNullValue()));
        assertThat(config.getEnabled(), is(false));
        assertThat(config.getShields(), is(Arrays.asList("block", "admin_notification")));
        assertThat(config.getAllowList(), is(Arrays.asList("143.204.0.105", "2600:9000:208f:ca00:d:f5f5:b40:93a1")));
        assertThat(config.getStage(), is(notNullValue()));
        assertThat(config.getStage().getPreLoginStage(), is(notNullValue()));
        assertThat(config.getStage().getPreLoginStage().getMaxAttempts(), is(100));
        assertThat(config.getStage().getPreLoginStage().getRate(), is(864000));
        assertThat(config.getStage().getPreUserRegistrationStage(), is(notNullValue()));
        assertThat(config.getStage().getPreUserRegistrationStage().getMaxAttempts(), is(50));
        assertThat(config.getStage().getPreUserRegistrationStage().getRate(), is(1728000));

    }

    @Test
    public void shouldSerialize() throws Exception {
        List<String> allowList = Arrays.asList("123", "456");
        List<String> shields = Arrays.asList("block", "user_notification");

        StageEntry preLogin = new StageEntry();
        preLogin.setMaxAttempts(100);
        preLogin.setRate(864000);

        StageEntry preRegistration = new StageEntry();
        preLogin.setMaxAttempts(50);
        preLogin.setRate(1728000);

        Stage stage = new Stage();
        stage.setPreLoginStage(preLogin);
        stage.setPreUserRegistrationStage(preRegistration);

        SuspiciousIPThrottlingConfiguration config = new SuspiciousIPThrottlingConfiguration();
        config.setEnabled(true);
        config.setAllowList(allowList);
        config.setShields(shields);
        config.setStage(stage);

        String json = toJSON(config);

        assertThat(json, JsonMatcher.hasEntry("enabled", true));
        assertThat(json, JsonMatcher.hasEntry("shields", shields));
        assertThat(json, JsonMatcher.hasEntry("allowlist", allowList));
        assertThat(json, JsonMatcher.hasEntry("stage", is(notNullValue())));
    }
}

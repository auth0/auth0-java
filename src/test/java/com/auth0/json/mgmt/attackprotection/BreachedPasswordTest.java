package com.auth0.json.mgmt.attackprotection;

import com.auth0.json.JsonMatcher;
import com.auth0.json.JsonTest;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class BreachedPasswordTest extends JsonTest<BreachedPassword> {

    private String json = "{\n" +
        "  \"enabled\": true,\n" +
        "  \"shields\": [\n" +
        "    \"block\",\n" +
        "    \"admin_notification\"\n" +
        "  ],\n" +
        "  \"admin_notification_frequency\": [\n" +
        "    \"immediately\",\n" +
        "    \"weekly\"\n" +
        "  ],\n" +
        "  \"method\": \"standard\"\n" +
        "}";

    @Test
    public void shouldSerialize() throws Exception {
        BreachedPassword breachedPassword = new BreachedPassword();
        breachedPassword.setAdminNotificationFrequency(Arrays.asList("immediately", "daily"));
        breachedPassword.setShields(Arrays.asList("admin_notification", "block"));
        breachedPassword.setMethod("standard");
        breachedPassword.setEnabled(true);

        String json = toJSON(breachedPassword);

        assertThat(json, JsonMatcher.hasEntry("enabled", true));
        assertThat(json, JsonMatcher.hasEntry("method", "standard"));
        assertThat(json, JsonMatcher.hasEntry("admin_notification_frequency", Arrays.asList("immediately", "daily")));
        assertThat(json, JsonMatcher.hasEntry("shields", Arrays.asList("admin_notification", "block")));
    }

    @Test
    public void shouldDeserialize() throws Exception {
        BreachedPassword breachedPassword = fromJSON(json, BreachedPassword.class);
        assertThat(breachedPassword, notNullValue());
        assertThat(breachedPassword.getEnabled(), is(true));
        assertThat(breachedPassword.getMethod(), is("standard"));
        assertThat(breachedPassword.getShields(), is(notNullValue()));
        assertThat(breachedPassword.getShields().size(), is(2));
        assertThat(breachedPassword.getShields(), contains("block", "admin_notification"));
        assertThat(breachedPassword.getAdminNotificationFrequency(), is(notNullValue()));
        assertThat(breachedPassword.getAdminNotificationFrequency().size(), is(2));
        assertThat(breachedPassword.getAdminNotificationFrequency(), contains("immediately", "weekly"));
    }
}

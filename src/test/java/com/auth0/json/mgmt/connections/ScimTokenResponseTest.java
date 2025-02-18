package com.auth0.json.mgmt.connections;

import com.auth0.json.JsonMatcher;
import com.auth0.json.JsonTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.auth0.json.JsonMatcher.hasEntry;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;

public class ScimTokenResponseTest extends JsonTest<ScimTokenResponse> {
    private final static String json = "{\n" +
        "  \"token_id\": \"tok_000000001\",\n" +
        "  \"scopes\": [\n" +
        "    \"get:users\"\n" +
        "  ],\n" +
        "  \"created_at\": \"2025-01-23T12:34:46.321Z\",\n" +
        "  \"valid_until\": \"2025-01-23T12:51:26.321Z\",\n" +
        "  \"last_used_at\": \"2025-01-23T12:51:26.321Z\"\n" +
        "}";


    @Test
    public void serialize() throws Exception {
        ScimTokenResponse request = new ScimTokenResponse();
        request.setTokenId("tok_000000001");
        List<String> scopes = new ArrayList<>();
        scopes.add("get:users");
        request.setScopes(scopes);
        request.setCreatedAt("2025-01-23T12:34:46.321Z");
        request.setValidUntil("2025-01-23T12:51:26.321Z");
        request.setLastUsedAt("2025-01-23T12:51:26.321Z");

        String serialized = toJSON(request);
        assertThat(serialized, is(notNullValue()));
        assertThat(serialized, JsonMatcher.hasEntry("token_id", "tok_000000001"));
        assertThat(serialized, hasEntry("scopes", notNullValue()));
        assertThat(serialized, containsString("\"scopes\":[\"get:users\"]"));
        assertThat(serialized, JsonMatcher.hasEntry("created_at", "2025-01-23T12:34:46.321Z"));
        assertThat(serialized, JsonMatcher.hasEntry("valid_until", "2025-01-23T12:51:26.321Z"));
        assertThat(serialized, JsonMatcher.hasEntry("last_used_at", "2025-01-23T12:51:26.321Z"));
    }

    @Test
    public void deserialize() throws Exception {
        ScimTokenResponse deserialized = fromJSON(json, ScimTokenResponse.class);

        assertThat(deserialized, is(notNullValue()));

        assertThat(deserialized.getTokenId(), is("tok_000000001"));
        assertThat(deserialized.getScopes().get(0), is("get:users"));
        assertThat(deserialized.getCreatedAt(), is("2025-01-23T12:34:46.321Z"));
        assertThat(deserialized.getValidUntil(), is("2025-01-23T12:51:26.321Z"));
        assertThat(deserialized.getLastUsedAt(), is("2025-01-23T12:51:26.321Z"));
    }
}

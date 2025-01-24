package com.auth0.json.mgmt.connections;

import com.auth0.json.JsonTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.auth0.json.JsonMatcher.hasEntry;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ScimTokenCreateResponseTest extends JsonTest<ScimTokenCreateResponse> {
    public static final String CONNECTION_SCIM_TOKEN = "src/test/resources/mgmt/connection_scim_token.json";

    @Test
    public void deserialize() throws Exception {
        ScimTokenCreateResponse deserialized = fromJSON(readTextFile(CONNECTION_SCIM_TOKEN), ScimTokenCreateResponse.class);

        assertThat(deserialized.getTokenId(), is("tok_000000001"));
        assertThat(deserialized.getToken(), is("tok_iwioqiwoqoqiwqoiwqwi"));
        assertThat(deserialized.getScopes().get(0), is("get:users"));
        assertThat(deserialized.getCreatedAt(), is("2025-01-23T12:34:46.321Z"));
        assertThat(deserialized.getValidUntil(), is("2025-01-23T12:51:26.321Z"));
    }

    @Test
    public void serialize() throws Exception {
        ScimTokenCreateResponse serialize = new ScimTokenCreateResponse();
        serialize.setTokenId("tok_000000001");
        serialize.setToken("tok_iwioqiwoqoqiwqoiwqwi");
        List<String> scopes = new ArrayList<>();
        scopes.add("get:users");
        serialize.setScopes(scopes);
        serialize.setCreatedAt("2025-01-23T12:34:46.321Z");
        serialize.setValidUntil("2025-01-23T12:51:26.321Z");

        String serialized = toJSON(serialize);
        assertThat(serialized, is(notNullValue()));

        assertThat(serialized, hasEntry("token_id", "tok_000000001"));
        assertThat(serialized, hasEntry("token", "tok_iwioqiwoqoqiwqoiwqwi"));
        assertThat(serialized, hasEntry("scopes", notNullValue()));
        assertThat(serialized, containsString("\"scopes\":[\"get:users\"]"));
        assertThat(serialized, hasEntry("created_at", "2025-01-23T12:34:46.321Z"));
        assertThat(serialized, hasEntry("valid_until", "2025-01-23T12:51:26.321Z"));
    }
}

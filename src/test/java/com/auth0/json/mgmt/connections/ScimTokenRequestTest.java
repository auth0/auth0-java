package com.auth0.json.mgmt.connections;

import com.auth0.json.JsonTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.auth0.json.JsonMatcher.hasEntry;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ScimTokenRequestTest extends JsonTest<ScimTokenRequest> {

    public static final String json = "{\n" +
        "  \"scopes\": [\"get:users\"],\n" +
        "  \"token_lifetime\": 1000\n" +
        "}";

    @Test
    public void serialize() throws Exception {
        ScimTokenRequest request = new ScimTokenRequest();
        request.setTokenLifetime(1000);
        List<String> scopes = new ArrayList<>();
        scopes.add("get:users");
        request.setScopes(scopes);

        String serialized = toJSON(request);
        assertThat(serialized, is(notNullValue()));
        assertThat(serialized, hasEntry("scopes", notNullValue()));
        assertThat(serialized, containsString("\"scopes\":[\"get:users\"]"));
        assertThat(serialized, hasEntry("token_lifetime", 1000));
    }

    @Test
    public void deserialize() throws Exception {
        ScimTokenRequest deserialized = fromJSON(json, ScimTokenRequest.class);

        assertThat(deserialized, is(notNullValue()));

        assertThat(deserialized.getTokenLifetime(), is(1000));
        assertThat(deserialized.getScopes().get(0), is("get:users"));
    }
}

package com.auth0.json.mgmt.connections;

import com.auth0.json.JsonMatcher;
import com.auth0.json.JsonTest;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class EnabledClientRequestTest extends JsonTest<EnabledClientRequest> {
    private static final String json = "{\"client_id\":\"1\",\"status\":true}";

    @Test
    public void shouldSerialize() throws Exception {
        EnabledClientRequest enabledClientRequest = new EnabledClientRequest("1", true);

        String serialized = toJSON(enabledClientRequest);
        assertThat(serialized, is(notNullValue()));
        assertThat(serialized, JsonMatcher.hasEntry("client_id", "1"));
        assertThat(serialized, JsonMatcher.hasEntry("status", true));
    }

}

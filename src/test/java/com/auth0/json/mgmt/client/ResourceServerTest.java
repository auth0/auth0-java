package com.auth0.json.mgmt.client;

import com.auth0.JsonMatcher;
import com.auth0.json.JsonTest;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ResourceServerTest extends JsonTest<ResourceServer> {

    private static final String json = "{\"identifier\":\"id\",\"scopes\":[{\"value\":\"read:client_grants\"},{\"value\":\"create:client_grants\"}]}";
    private static final String readOnlyJson = "{\"identifier\":\"id\"}";

    @Test
    public void shouldSerialize() throws Exception {
        ResourceServer server = new ResourceServer("id");
        server.setScopes(Arrays.<Object>asList(Collections.singletonMap("value", "read:client_grants"), Collections.singletonMap("value", "create:client_grants")));

        String serialized = toJSON(server);
        assertThat(serialized, is(notNullValue()));
        assertThat(serialized, JsonMatcher.hasEntry("identifier", "id"));
        assertThat(serialized, JsonMatcher.hasEntry("scopes", notNullValue()));
    }

    @Test
    public void shouldDeserialize() throws Exception {
        ResourceServer server = fromJSON(json, ResourceServer.class);

        assertThat(server, is(notNullValue()));
        assertThat(server.getIdentifier(), is("id"));
        assertThat(server.getScopes(), notNullValue());
        assertThat((Map<String, Object>) server.getScopes().get(0), hasEntry("value", (Object) "read:client_grants"));
        assertThat((Map<String, Object>) server.getScopes().get(1), hasEntry("value", (Object) "create:client_grants"));
    }

    @Test
    public void shouldIncludeReadOnlyValuesOnDeserialize() throws Exception {
        ResourceServer server = fromJSON(readOnlyJson, ResourceServer.class);
        assertThat(server, is(notNullValue()));

        assertThat(server.getIdentifier(), is("id"));
    }
}
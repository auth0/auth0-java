package com.auth0.json.mgmt.client;

import com.auth0.json.JsonTest;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ResourceServerTest extends JsonTest<ResourceServer> {

    private static final String json = "{\"identifier\":\"id\",\"scopes\":[\"one\",\"two\"]}";

    @Test
    public void shouldSerialize() throws Exception {
        ResourceServer server = new ResourceServer(null, null);
        server.setIdentifier("id");
        server.setScopes(Arrays.asList("one", "two"));

        String serialized = toJSON(server);
        assertThat(serialized, is(notNullValue()));
        assertThat(serialized, is(equalTo(json)));
    }

    @Test
    public void shouldDeserialize() throws Exception {
        ResourceServer server = fromJSON(json, ResourceServer.class);

        assertThat(server, is(notNullValue()));
        assertThat(server.getIdentifier(), is("id"));
        assertThat(server.getScopes(), contains("one", "two"));
    }
}
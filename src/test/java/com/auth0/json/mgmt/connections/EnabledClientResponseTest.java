package com.auth0.json.mgmt.connections;

import com.auth0.json.JsonMatcher;
import com.auth0.json.JsonTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class EnabledClientResponseTest extends JsonTest<EnabledClientResponse> {
    private static final String json = "src/test/resources/mgmt/enabled_clients_for_connection.json";

    @Test
    public void shouldSerialize() throws Exception {
        EnabledClientResponse enabledClientResponse = new EnabledClientResponse();
        List<Clients> clientsList = new ArrayList<>();
        clientsList.add(new Clients("client-1"));
        clientsList.add(new Clients("client-2"));
        enabledClientResponse.setClients(clientsList);
        enabledClientResponse.setNext("next");

        String serialized = toJSON(enabledClientResponse);
        assertThat(serialized, is(notNullValue()));
        assertThat(serialized, JsonMatcher.hasEntry("next", "next"));
    }

    @Test
    public void shouldDeserialize() throws Exception {
        EnabledClientResponse deserialized = fromJSON(readTextFile(json), EnabledClientResponse.class);

        assertThat(deserialized, is(notNullValue()));
        assertThat(deserialized.getClients().get(0).getClientId(), is("client-1"));
        assertThat(deserialized.getClients().get(1).getClientId(), is("client-2"));
        assertThat(deserialized.getNext(), is("next"));
    }
}

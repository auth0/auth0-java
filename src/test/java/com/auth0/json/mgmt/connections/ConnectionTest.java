package com.auth0.json.mgmt.connections;

import com.auth0.json.JsonMatcher;
import com.auth0.json.JsonTest;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ConnectionTest extends JsonTest<Connection> {

    private static final String json = "{\"name\": \"my-connection\",\"display_name\": \"My cool connection!\",\"strategy\": \"auth0\",\"options\": {},\"enabled_clients\": [\"client1\",\"client2\"],\"metadata\": {\"key\": \"value\"},\"realms\": [\"realm1\",\"realm2\"]}";
    private static final String readOnlyJson = "{\"id\":\"connectionId\"}";

    private static final String jsonAd = "{\"name\":\"my-ad-connection\",\"strategy\":\"ad\",\"provisioning_ticket_url\":\"https://demo.auth0.com/p/ad/ddQTRlVt\",\"options\":{},\"enabled_clients\":[\"client1\",\"client2\"]}";

    @Test
    public void shouldSerialize() throws Exception {
        Connection connection = new Connection("my-connection", "auth0");
        connection.setDisplayName("COOL!");
        connection.setOptions(new HashMap<String, Object>());
        connection.setEnabledClients(Arrays.asList("client1", "client2"));
        connection.setMetadata(new HashMap<String, String>());
        connection.setRealms(Arrays.asList("realm1", "realm2"));

        String serialized = toJSON(connection);
        assertThat(serialized, is(notNullValue()));
        assertThat(serialized, JsonMatcher.hasEntry("name", "my-connection"));
        assertThat(serialized, JsonMatcher.hasEntry("display_name", "COOL!"));
        assertThat(serialized, JsonMatcher.hasEntry("strategy", "auth0"));
        assertThat(serialized, JsonMatcher.hasEntry("options", notNullValue()));
        assertThat(serialized, JsonMatcher.hasEntry("enabled_clients", Arrays.asList("client1", "client2")));
        assertThat(serialized, JsonMatcher.hasEntry("provisioning_ticket_url", null));
        assertThat(serialized, JsonMatcher.hasEntry("metadata", notNullValue()));
        assertThat(serialized, JsonMatcher.hasEntry("realms", Arrays.asList("realm1", "realm2")));
    }

    @Test
    public void shouldDeserialize() throws Exception {
        Connection connection = fromJSON(json, Connection.class);

        assertThat(connection, is(notNullValue()));
        assertThat(connection.getName(), is("my-connection"));
        assertThat(connection.getDisplayName(), is("My cool connection!"));
        assertThat(connection.getOptions(), is(notNullValue()));
        assertThat(connection.getStrategy(), is("auth0"));
        assertThat(connection.getEnabledClients(), contains("client1", "client2"));
        assertThat(connection.getProvisioningTicketUrl(), is(nullValue()));
        assertThat(connection.getMetadata(), is(notNullValue()));
        assertThat(connection.getRealms(), contains("realm1", "realm2"));
    }

    @Test
    public void shouldDeserializeAd() throws Exception {
        Connection connection = fromJSON(jsonAd, Connection.class);

        assertThat(connection, is(notNullValue()));
        assertThat(connection.getName(), is("my-ad-connection"));
        assertThat(connection.getOptions(), is(notNullValue()));
        assertThat(connection.getStrategy(), is("ad"));
        assertThat(connection.getEnabledClients(), contains("client1", "client2"));
        assertThat(connection.getProvisioningTicketUrl(), is("https://demo.auth0.com/p/ad/ddQTRlVt"));
        assertThat(connection.getMetadata(), is(nullValue()));
    }

    @Test
    public void shouldIncludeReadOnlyValuesOnDeserialize() throws Exception {
        Connection connection = fromJSON(readOnlyJson, Connection.class);
        assertThat(connection, is(notNullValue()));

        assertThat(connection.getId(), is("connectionId"));
    }
}

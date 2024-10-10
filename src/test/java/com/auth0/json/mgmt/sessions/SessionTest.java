package com.auth0.json.mgmt.sessions;

import com.auth0.json.JsonTest;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class SessionTest extends JsonTest<Session> {

    private static final String json = "{\n" +
        "  \"id\": \"sessionId1\",\n" +
        "  \"user_id\": \"userId1\",\n" +
        "  \"created_at\": \"2024-09-04T06:41:46.145Z\",\n" +
        "  \"updated_at\": \"2024-09-04T06:41:46.621Z\",\n" +
        "  \"authenticated_at\": \"2024-09-04T06:41:46.145Z\",\n" +
        "  \"authentication\": {\n" +
        "    \"methods\": [\n" +
        "      {\n" +
        "        \"name\": \"federated\",\n" +
        "        \"timestamp\": \"2024-09-04T06:41:46.145Z\"\n" +
        "      }\n" +
        "    ]\n" +
        "  },\n" +
        "  \"idle_expires_at\": \"2024-09-07T06:41:46.622Z\",\n" +
        "  \"expires_at\": \"2024-09-11T06:41:46.145Z\",\n" +
        "  \"device\": {\n" +
        "    \"initial_user_agent\": \"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/128.0.0.0 Safari/537.36\",\n" +
        "    \"initial_asn\": \"1234\",\n" +
        "    \"initial_ip\": \"134.1.15.0\",\n" +
        "    \"last_user_agent\": \"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/128.0.0.0 Safari/537.36\",\n" +
        "    \"last_ip\": \"134.1.15.0\",\n" +
        "    \"last_asn\": \"1234\"\n" +
        "  },\n" +
        "  \"clients\": [\n" +
        "    {\n" +
        "      \"client_id\": \"clientId1\"\n" +
        "    }\n" +
        "  ]\n" +
        "}";

    private static final String readOnlyJson = "{\n" +
        "  \"user_id\": \"userId1\",\n" +
        "  \"clients\": [\n" +
        "    {\n" +
        "      \"client_id\": \"clientId1\"\n" +
        "    }\n" +
        "  ]\n" +
        "}";

    @Test
    public void shouldDeserialize() throws Exception {
        Session session = fromJSON(json, Session.class);

        assertThat(session, is(notNullValue()));
        assertThat(session.getId(), is("sessionId1"));
        assertThat(session.getUserId(), is("userId1"));
        assertThat(session.getCreatedAt(), is(parseJSONDate("2024-09-04T06:41:46.145Z")));
        assertThat(session.getUpdatedAt(), is(parseJSONDate("2024-09-04T06:41:46.621Z")));
        assertThat(session.getAuthenticatedAt(), is(parseJSONDate("2024-09-04T06:41:46.145Z")));

        assertThat(session.getAuthentication().getMethods(), is(notNullValue()));
        assertThat(session.getAuthentication().getMethods().get(0).getName(), is("federated"));
        assertThat(session.getAuthentication().getMethods().get(0).getTimestamp(), is(parseJSONDate("2024-09-04T06:41:46.145Z")));

        assertThat(session.getIdleExpiresAt(), is(parseJSONDate("2024-09-07T06:41:46.622Z")));
        assertThat(session.getExpiresAt(), is(parseJSONDate("2024-09-11T06:41:46.145Z")));

        assertThat(session.getDevice(), is(notNullValue()));
        assertThat(session.getDevice().getInitialASN(), is("1234"));
        assertThat(session.getDevice().getInitialIP(), is("134.1.15.0"));
        assertThat(session.getDevice().getInitialUserAgent(), is("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/128.0.0.0 Safari/537.36"));
        assertThat(session.getDevice().getLastASN(), is("1234"));
        assertThat(session.getDevice().getLastIP(), is("134.1.15.0"));
        assertThat(session.getDevice().getLastUserAgent(), is("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/128.0.0.0 Safari/537.36"));

        assertThat(session.getClients(), is(notNullValue()));
        assertThat(session.getClients().get(0).getClientId(), is("clientId1"));
    }

    @Test
    public void shouldIncludeReadOnlyValuesOnDeserialize() throws Exception {
        Session session = fromJSON(readOnlyJson, Session.class);
        assertThat(session, is(notNullValue()));
        assertThat(session.getUserId(), is("userId1"));
        assertThat(session.getClients().get(0).getClientId(), is("clientId1"));
    }


}

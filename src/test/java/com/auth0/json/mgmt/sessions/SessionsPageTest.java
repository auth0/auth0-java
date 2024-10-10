package com.auth0.json.mgmt.sessions;

import com.auth0.json.JsonTest;
import com.auth0.json.mgmt.sessions.SessionsPage;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

public class SessionsPageTest extends JsonTest<SessionsPage> {
    private static final String json = "{\n" +
        "  \"sessions\": [\n" +
        "    {\n" +
        "      \"id\": \"sessionId1\",\n" +
        "      \"user_id\": \"userId1\",\n" +
        "      \"created_at\": \"2024-06-26T09:10:26.643Z\",\n" +
        "      \"updated_at\": \"2024-06-26T09:10:27.131Z\",\n" +
        "      \"authenticated_at\": \"2024-06-26T09:10:26.643Z\",\n" +
        "      \"authentication\": {\n" +
        "        \"methods\": [\n" +
        "          {\n" +
        "            \"name\": \"pwd\",\n" +
        "            \"timestamp\": \"2024-06-26T09:10:26.643Z\"\n" +
        "          }\n" +
        "        ]\n" +
        "      },\n" +
        "      \"idle_expires_at\": \"2024-06-26T09:40:27.131Z\",\n" +
        "      \"expires_at\": \"2024-07-03T09:10:26.643Z\",\n" +
        "      \"device\": {\n" +
        "        \"initial_asn\": \"1234\",\n" +
        "        \"initial_ip\": \"203.0.113.1\",\n" +
        "        \"initial_user_agent\": \"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36\",\n" +
        "        \"last_user_agent\": \"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36\",\n" +
        "        \"last_ip\": \"203.0.113.1\",\n" +
        "        \"last_asn\": \"1234\"\n" +
        "      },\n" +
        "      \"clients\": [\n" +
        "        {\n" +
        "          \"client_id\": \"clientId1\"\n" +
        "        }\n" +
        "      ]\n" +
        "    }\n" +
        "  ],\n" +
        "  \"next\": \"sessionId1\"\n" +
        "}";
    private static final String jsonWithTotals = "{\n" +
        "  \"sessions\": [\n" +
        "    {\n" +
        "      \"id\": \"sessionId1\",\n" +
        "      \"user_id\": \"userId1\",\n" +
        "      \"created_at\": \"2024-06-26T09:10:26.643Z\",\n" +
        "      \"updated_at\": \"2024-06-26T09:10:27.131Z\",\n" +
        "      \"authenticated_at\": \"2024-06-26T09:10:26.643Z\",\n" +
        "      \"authentication\": {\n" +
        "        \"methods\": [\n" +
        "          {\n" +
        "            \"name\": \"pwd\",\n" +
        "            \"timestamp\": \"2024-06-26T09:10:26.643Z\"\n" +
        "          }\n" +
        "        ]\n" +
        "      },\n" +
        "      \"idle_expires_at\": \"2024-06-26T09:40:27.131Z\",\n" +
        "      \"expires_at\": \"2024-07-03T09:10:26.643Z\",\n" +
        "      \"device\": {\n" +
        "        \"initial_user_agent\": \"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/128.0.0.0 Safari/537.36\",\n" +
        "        \"initial_asn\": \"1234\",\n" +
        "        \"initial_ip\": \"203.0.113.1\",\n" +
        "        \"last_user_agent\": \"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36\",\n" +
        "        \"last_ip\": \"203.0.113.1\",\n" +
        "        \"last_asn\": \"1234\"\n" +
        "      },\n" +
        "      \"clients\": [\n" +
        "        {\n" +
        "          \"client_id\": \"clientId1\"\n" +
        "        }\n" +
        "      ]\n" +
        "    }\n" +
        "  ],\n" +
        "  \"next\": \"sessionId1\",\n" +
        "  \"total\": 11\n" +
        "}";

    @Test
    public void shouldDeserialize() throws Exception {
        SessionsPage page = fromJSON(json, SessionsPage.class);

        assertThat(page.getTotal(), is(nullValue()));
        assertThat(page.getNext(), is("sessionId1"));
        assertThat(page.getSessions().size(), is(1));
        assertThat(page.getSessions().get(0).getId(), is("sessionId1"));
        assertThat(page.getSessions().get(0).getUserId(), is("userId1"));
        assertThat(page.getSessions().get(0).getCreatedAt(), is(parseJSONDate("2024-06-26T09:10:26.643Z")));
        assertThat(page.getSessions().get(0).getUpdatedAt(), is(parseJSONDate("2024-06-26T09:10:27.131Z")));
        assertThat(page.getSessions().get(0).getAuthenticatedAt(), is(parseJSONDate("2024-06-26T09:10:26.643Z")));
        assertThat(page.getSessions().get(0).getIdleExpiresAt(), is(parseJSONDate("2024-06-26T09:40:27.131Z")));
        assertThat(page.getSessions().get(0).getExpiresAt(), is(parseJSONDate("2024-07-03T09:10:26.643Z")));

        assertThat(page.getSessions().get(0).getDevice().getInitialASN(), is("1234"));
        assertThat(page.getSessions().get(0).getDevice().getInitialIP(), is("203.0.113.1"));
        assertThat(page.getSessions().get(0).getDevice().getLastUserAgent(), is("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36"));
        assertThat(page.getSessions().get(0).getDevice().getLastIP(), is("203.0.113.1"));
        assertThat(page.getSessions().get(0).getDevice().getLastASN(), is("1234"));
        assertThat(page.getSessions().get(0).getDevice().getLastUserAgent(), is("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36"));

        assertThat(page.getSessions().get(0).getClients().size(), is(1));
        assertThat(page.getSessions().get(0).getClients().get(0).getClientId(), is("clientId1"));

        assertThat(page.getSessions().get(0).getAuthentication().getMethods().size(), is(1));
        assertThat(page.getSessions().get(0).getAuthentication().getMethods().get(0).getName(), is("pwd"));
        assertThat(page.getSessions().get(0).getAuthentication().getMethods().get(0).getTimestamp(), is(parseJSONDate("2024-06-26T09:10:26.643Z")));
        assertThat(page.getSessions().get(0).getAuthentication().getMethods().get(0).getType(), is(nullValue()));
    }

    @Test
    public void shouldDeserializeWithTotals() throws Exception {
        SessionsPage page = fromJSON(jsonWithTotals, SessionsPage.class);

        assertThat(page.getTotal(), is(11));
    }
}

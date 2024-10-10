package com.auth0.json.mgmt.refreshtokens;

import com.auth0.json.JsonTest;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class RefreshTokenTest extends JsonTest<RefreshToken> {

    private static final String json = "{\n" +
        "  \"id\": \"tokenId1\",\n" +
        "  \"user_id\": \"userId1\",\n" +
        "  \"created_at\": \"2024-06-26T09:10:26.643Z\",\n" +
        "  \"idle_expires_at\": \"2024-06-26T09:10:27.131Z\",\n" +
        "  \"expires_at\": \"2024-07-03T09:10:26.643Z\",\n" +
        "  \"device\": {\n" +
        "    \"initial_asn\": \"1234\",\n" +
        "    \"initial_ip\": \"203.0.113.1\",\n" +
        "    \"initial_user_agent\": \"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36\",\n" +
        "    \"last_user_agent\": \"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36\",\n" +
        "    \"last_ip\": \"203.0.113.1\",\n" +
        "    \"last_asn\": \"1234\"\n" +
        "  },\n" +
        "  \"client_id\": \"clientId1\",\n" +
        "  \"session_id\": \"sessionId1\",\n" +
        "  \"rotating\": false,\n" +
        "  \"resource_servers\": [\n" +
        "    {\n" +
        "      \"audience\": \"https://api.example.com\",\n" +
        "      \"scopes\": [\n" +
        "        \"read:examples\",\n" +
        "        \"write:examples\"\n" +
        "      ]\n" +
        "    }\n" +
        "  ],\n" +
        "  \"last_exchanged_at\": \"2024-07-03T09:10:26.643Z\"\n" +
        "}";

    private static final String readOnlyJson = "{\n" +
        "  \"user_id\": \"userId1\",\n" +
        "  \"client_id\": \"clientId1\",\n" +
        "  \"session_id\": \"sessionId1\"\n" +
        "}";

    @Test
    public void shouldDeserialize() throws Exception {
        RefreshToken refreshToken = fromJSON(json, RefreshToken.class);
        assertThat(refreshToken, is(notNullValue()));

        assertThat(refreshToken.getId(), is("tokenId1"));
        assertThat(refreshToken.getUserId(), is("userId1"));
        assertThat(refreshToken.getCreatedAt(), is(parseJSONDate("2024-06-26T09:10:26.643Z")));
        assertThat(refreshToken.getIdleExpiresAt(), is(parseJSONDate("2024-06-26T09:10:27.131Z")));
        assertThat(refreshToken.getExpiresAt(), is(parseJSONDate("2024-07-03T09:10:26.643Z")));

        assertThat(refreshToken.getDevice(), is(notNullValue()));
        assertThat(refreshToken.getDevice().getInitialAsn(), is("1234"));
        assertThat(refreshToken.getDevice().getInitialIp(), is("203.0.113.1"));
        assertThat(refreshToken.getDevice().getInitialUserAgent(), is("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36"));
        assertThat(refreshToken.getDevice().getLastUserAgent(), is("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36"));
        assertThat(refreshToken.getDevice().getLastIp(), is("203.0.113.1"));
        assertThat(refreshToken.getDevice().getLastAsn(), is("1234"));

        assertThat(refreshToken.getClientId(), is("clientId1"));
        assertThat(refreshToken.getSessionId(), is("sessionId1"));
        assertThat(refreshToken.isRotating(), is(false));

        assertThat(refreshToken.getResourceServers(), is(notNullValue()));
        assertThat(refreshToken.getResourceServers().get(0).getAudience(), is("https://api.example.com"));
        assertThat(refreshToken.getResourceServers().get(0).getScopes(), is(notNullValue()));
        assertThat(refreshToken.getResourceServers().get(0).getScopes().get(0), is("read:examples"));
        assertThat(refreshToken.getResourceServers().get(0).getScopes().get(1), is("write:examples"));

        assertThat(refreshToken.getLastExchangedAt(), is(parseJSONDate("2024-07-03T09:10:26.643Z")));
    }

    @Test
    public void shouldIncludeReadOnlyValuesOnDeserialize() throws Exception {
        RefreshToken refreshToken = fromJSON(readOnlyJson, RefreshToken.class);
        assertThat(refreshToken, is(notNullValue()));
        assertThat(refreshToken.getUserId(), is("userId1"));
        assertThat(refreshToken.getClientId(), is("clientId1"));
        assertThat(refreshToken.getSessionId(), is("sessionId1"));
    }
}

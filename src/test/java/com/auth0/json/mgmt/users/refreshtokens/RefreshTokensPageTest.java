package com.auth0.json.mgmt.users.refreshtokens;

import com.auth0.json.JsonTest;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

public class RefreshTokensPageTest extends JsonTest<RefreshTokensPage> {
    private static final String json = "{\n\"tokens\": [\n{\n\"id\": \"tokenId1\",\n\"user_id\": \"userId1\",\n\"created_at\": \"2024-06-26T09:10:26.643Z\",\n\"updated_at\": \"2024-06-26T09:10:27.131Z\",\n\"expires_at\": \"2024-07-03T09:10:26.643Z\",\n\"client_id\": \"clientId1\",\n\"session_id\": \"sessionId1\",\n\"rotating\": false,\n\"resource_servers\": [\n{\n\"audience\": \"https://api.example.com\",\n\"scopes\": [\n\"read:examples\",\n\"write:examples\"\n]\n}\n]\n},\n{\n\"id\": \"tokenId2\",\n\"user_id\": \"userId1\",\n\"created_at\": \"2024-06-26T09:10:26.643Z\",\n\"updated_at\": \"2024-06-26T09:10:27.131Z\",\n\"expires_at\": \"2024-07-03T09:10:26.643Z\",\n\"client_id\": \"clientId2\",\n\"session_id\": \"sessionId2\",\n\"rotating\": true,\n\"resource_servers\": [\n{\n\"audience\": \"https://api.example.com\",\n\"scopes\": [\n\"read:examples\",\n\"write:examples\"\n]\n}\n]\n}\n],\n\"next\": \"token1\"\n}\n";
    private static final String jsonWithTotal = "{\n\"tokens\": [\n{\n\"id\": \"tokenId1\",\n\"user_id\": \"userId1\",\n\"created_at\": \"2024-06-26T09:10:26.643Z\",\n\"updated_at\": \"2024-06-26T09:10:27.131Z\",\n\"expires_at\": \"2024-07-03T09:10:26.643Z\",\n\"client_id\": \"clientId1\",\n\"session_id\": \"sessionId1\",\n\"rotating\": false,\n\"resource_servers\": [\n{\n\"audience\": \"https://api.example.com\",\n\"scopes\": [\n\"read:examples\",\n\"write:examples\"\n]\n}\n]\n},\n{\n\"id\": \"tokenId2\",\n\"user_id\": \"userId1\",\n\"created_at\": \"2024-06-26T09:10:26.643Z\",\n\"updated_at\": \"2024-06-26T09:10:27.131Z\",\n\"expires_at\": \"2024-07-03T09:10:26.643Z\",\n\"client_id\": \"clientId2\",\n\"session_id\": \"sessionId2\",\n\"rotating\": true,\n\"resource_servers\": [\n{\n\"audience\": \"https://api.example.com\",\n\"scopes\": [\n\"read:examples\",\n\"write:examples\"\n]\n}\n]\n}\n],\n\"next\": \"token1\",\n\"total\": 11\n}\n";

    @Test
    public void shouldDeserialize() throws Exception {
        RefreshTokensPage page = fromJSON(json, RefreshTokensPage.class);

        assertThat(page.getTotal(), is(nullValue()));
        assertThat(page.getNext(), is("token1"));

        assertThat(page.getTokens().size(), is(2));
        assertThat(page.getTokens().get(0).getId(), is("tokenId1"));
        assertThat(page.getTokens().get(0).getUserId(), is("userId1"));
        assertThat(page.getTokens().get(0).getCreatedAt(), is(parseJSONDate("2024-06-26T09:10:26.643Z")));
        assertThat(page.getTokens().get(0).getIdleExpiresAt(), is(nullValue()));
        assertThat(page.getTokens().get(0).getExpiresAt(), is(parseJSONDate("2024-07-03T09:10:26.643Z")));
        assertThat(page.getTokens().get(0).getClientId(), is("clientId1"));
        assertThat(page.getTokens().get(0).getSessionId(), is("sessionId1"));
        assertThat(page.getTokens().get(0).isRotating(), is(false));

        assertThat(page.getTokens().get(0).getResourceServers().size(), is(1));
        assertThat(page.getTokens().get(0).getResourceServers().get(0).getAudience(), is("https://api.example.com"));
        assertThat(page.getTokens().get(0).getResourceServers().get(0).getScopes().size(), is(2));
        assertThat(page.getTokens().get(0).getResourceServers().get(0).getScopes().get(0), is("read:examples"));
        assertThat(page.getTokens().get(0).getResourceServers().get(0).getScopes().get(1), is("write:examples"));
    }

    @Test
    public void shouldDeserializeWithTotal() throws Exception {
        RefreshTokensPage page = fromJSON(jsonWithTotal, RefreshTokensPage.class);

        assertThat(page.getTotal(), is(11));
    }
}

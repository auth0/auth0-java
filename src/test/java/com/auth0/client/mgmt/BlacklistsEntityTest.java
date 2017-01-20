package com.auth0.client.mgmt;

import com.auth0.json.mgmt.Token;
import com.auth0.net.Request;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static com.auth0.client.MockServer.*;
import static com.auth0.client.RecordedRequestMatcher.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class BlacklistsEntityTest extends BaseMgmtEntityTest {

    @Test
    public void shouldThrowOnGetBlacklistedTokensWithNullAudience() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'audience' cannot be null!");
        api.blacklists().getBlacklist(null);
    }

    @Test
    public void shouldGetBlacklistedTokens() throws Exception {
        Request<List<Token>> request = api.blacklists().getBlacklist("myapi");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_BLACKLISTED_TOKENS_LIST, 200);
        List<Token> response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/blacklists/tokens"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("aud", "myapi"));

        assertThat(response, is(notNullValue()));
        assertThat(response, hasSize(2));
    }

    @Test
    public void shouldReturnEmptyBlacklistedTokens() throws Exception {
        Request<List<Token>> request = api.blacklists().getBlacklist("myapi");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_EMPTY_LIST, 200);
        List<Token> response = request.execute();

        assertThat(response, is(notNullValue()));
        assertThat(response, is(emptyCollectionOf(Token.class)));
    }

    @Test
    public void shouldThrowOnBlacklistTokensWithNullToken() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'token' cannot be null!");
        api.blacklists().blacklistToken(null);
    }

    @Test
    public void shouldBlacklistToken() throws Exception {
        Request request = api.blacklists().blacklistToken(new Token("id"));
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_BLACKLISTED_TOKENS_LIST, 200);
        request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("POST", "/api/v2/blacklists/tokens"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(1));
        assertThat(body, hasEntry("jti", (Object) "id"));
    }
}

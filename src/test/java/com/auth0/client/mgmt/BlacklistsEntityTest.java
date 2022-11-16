package com.auth0.client.mgmt;

import com.auth0.json.mgmt.Token;
import com.auth0.net.Request;
import com.auth0.net.client.HttpMethod;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static com.auth0.client.MockServer.*;
import static com.auth0.client.RecordedRequestMatcher.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class BlacklistsEntityTest extends BaseMgmtEntityTest {

    @Test
    public void shouldThrowOnGetBlacklistedTokensWithNullAudience() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'audience' cannot be null!");
        api.blacklists().getBlacklist(null);
    }

    @Test
    public void shouldGetBlacklistedTokens() throws Exception {
        Request<List<Token>> request = api.blacklists().getBlacklist("myapi");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_BLACKLISTED_TOKENS_LIST, 200);
        List<Token> response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/blacklists/tokens"));
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
        List<Token> response = request.execute().getBody();

        assertThat(response, is(notNullValue()));
        assertThat(response, is(emptyCollectionOf(Token.class)));
    }

    @Test
    public void shouldThrowOnBlacklistTokensWithNullToken() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'token' cannot be null!");
        api.blacklists().blacklistToken(null);
    }

    @Test
    public void shouldBlacklistToken() throws Exception {
        Request<Void> request = api.blacklists().blacklistToken(new Token("id"));
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_BLACKLISTED_TOKENS_LIST, 200);
        request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.POST, "/api/v2/blacklists/tokens"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(1));
        assertThat(body, hasEntry("jti", "id"));
    }
}

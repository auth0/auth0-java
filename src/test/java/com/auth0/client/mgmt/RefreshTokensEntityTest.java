package com.auth0.client.mgmt;

import com.auth0.json.mgmt.refreshtokens.RefreshToken;
import com.auth0.net.Request;
import com.auth0.net.client.HttpMethod;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Test;

import static com.auth0.AssertsUtil.verifyThrows;
import static com.auth0.client.MockServer.MGMT_REFRESH_TOKEN;
import static com.auth0.client.RecordedRequestMatcher.hasHeader;
import static com.auth0.client.RecordedRequestMatcher.hasMethodAndPath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class RefreshTokensEntityTest extends BaseMgmtEntityTest{

    @Test
    public void shouldThrowOnGetWithNullRefreshTokenId() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.refreshTokens().get(null),
            "'refresh token ID' cannot be null!");
    }

    @Test
    public void shouldGetRefreshToken() throws Exception {
        Request<RefreshToken> request = api.refreshTokens().get("refresh_token_ID");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_REFRESH_TOKEN, 200);
        RefreshToken response =request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/refresh-tokens/refresh_token_ID"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnDeleteWithNullRefreshTokenId() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.refreshTokens().delete(null),
            "'refresh token ID' cannot be null!");
    }

    @Test
    public void shouldDeleteRefreshToken() throws Exception {
        Request<Void> request = api.refreshTokens().delete("refresh_token_ID");
        assertThat(request, is(notNullValue()));

        server.noContentResponse();
        request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.DELETE, "/api/v2/refresh-tokens/refresh_token_ID"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
    }
}

package com.auth0.client.mgmt;

import com.auth0.json.mgmt.sessions.Session;
import com.auth0.net.Request;
import com.auth0.net.client.HttpMethod;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Test;

import static com.auth0.AssertsUtil.verifyThrows;
import static com.auth0.client.MockServer.MGMT_SESSION;
import static com.auth0.client.RecordedRequestMatcher.hasHeader;
import static com.auth0.client.RecordedRequestMatcher.hasMethodAndPath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class SessionsEntityTest extends BaseMgmtEntityTest{
    @Test
    public void getSessionShouldThrowOnNullSessionId() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.sessions().get(null),
            "'session ID' cannot be null!");
    }

    @Test
    public void shouldGetSession() throws Exception {
        Request<Session> request = api.sessions().get("session_ID");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_SESSION, 200);
        Session response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/sessions/session_ID"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void deleteSessionShouldThrowOnNullSessionId() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.sessions().delete(null),
            "'session ID' cannot be null!");
    }

    @Test
    public void shouldDeleteSession() throws Exception {
        Request<Void> request = api.sessions().delete("session_ID");
        assertThat(request, is(notNullValue()));

        server.noContentResponse();
        request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.DELETE, "/api/v2/sessions/session_ID"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
    }

}

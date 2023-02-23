package com.auth0.client.mgmt;

import com.auth0.json.mgmt.userblocks.UserBlocks;
import com.auth0.net.Request;
import com.auth0.net.client.HttpMethod;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.Test;

import static com.auth0.client.MockServer.MGMT_USER_BLOCKS;
import static com.auth0.client.RecordedRequestMatcher.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class UserBlocksEntityTest extends BaseMgmtEntityTest {

    @Test
    public void shouldThrowOnGetUserBlocksByIdentifierWithNullId() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'identifier' cannot be null!");
        api.userBlocks().getByIdentifier(null);
    }

    @Test
    public void shouldGetUserBlocksByIdentifier() throws Exception {
        Request<UserBlocks> request = api.userBlocks().getByIdentifier("username");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_USER_BLOCKS, 200);
        UserBlocks response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/user-blocks"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("identifier", "username"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnGetUserBlocksWithNullId() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'user id' cannot be null!");
        api.userBlocks().get(null);
    }

    @Test
    public void shouldGetUserBlocks() throws Exception {
        Request<UserBlocks> request = api.userBlocks().get("1");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_USER_BLOCKS, 200);
        UserBlocks response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/user-blocks/1"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnDeleteUserBlocksByIdentifierWithNullId() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'identifier' cannot be null!");
        api.userBlocks().deleteByIdentifier(null);
    }

    @Test
    public void shouldDeleteUserBlocksByIdentifier() throws Exception {
        Request<Void> request = api.userBlocks().deleteByIdentifier("username");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_USER_BLOCKS, 200);
        request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.DELETE, "/api/v2/user-blocks"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("identifier", "username"));
    }

    @Test
    public void shouldThrowOnDeleteUserBlocksWithNullId() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'user id' cannot be null!");
        api.userBlocks().delete(null);
    }

    @Test
    public void shouldDeleteUserBlocks() throws Exception {
        Request<Void> request = api.userBlocks().delete("1");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_USER_BLOCKS, 200);
        request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.DELETE, "/api/v2/user-blocks/1"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
    }
}

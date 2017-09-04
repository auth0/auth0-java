package com.auth0.client.mgmt;

import com.auth0.json.mgmt.Grant;
import com.auth0.net.Request;

import okhttp3.mockwebserver.RecordedRequest;

import org.junit.Test;

import java.util.List;

import static com.auth0.client.MockServer.*;
import static com.auth0.client.RecordedRequestMatcher.hasHeader;
import static com.auth0.client.RecordedRequestMatcher.hasMethodAndPath;
import static com.auth0.client.RecordedRequestMatcher.hasQueryParameter;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class GrantsEntityTest extends BaseMgmtEntityTest {

    @Test
    public void shouldListGrants() throws Exception {
        Request<List<Grant>> request = api.grants().list("userId");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_GRANTS_LIST, 200);
        List<Grant> response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/grants"));
        assertThat(recordedRequest, hasQueryParameter("user_id", "userId"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
        assertThat(response, hasSize(2));
        for (Grant grant : response) {
            assertThat(grant.getAudience(), notNullValue());
            assertThat(grant.getClientId(), notNullValue());
            assertThat(grant.getId(), notNullValue());
            assertThat(grant.getScope(), notNullValue());
            assertThat(grant.getScope(), hasSize(2));
            assertThat(grant.getUserId(), equalTo("userId"));
        }
        
    }

    @Test
    public void shouldReturnEmptyGrants() throws Exception {
        Request<List<Grant>> request = api.grants().list("userId");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_EMPTY_LIST, 200);
        List<Grant> response = request.execute();

        assertThat(response, is(notNullValue()));
        assertThat(response, is(emptyCollectionOf(Grant.class)));
    }

    @Test
    public void shouldThrowOnDeleteGrantWithNullId() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'grant id' cannot be null!");
        api.grants().delete(null);
    }
    
    @Test
    public void shouldDeleteGrantById() throws Exception {
        Request request = api.grants().delete("1");
        assertThat(request, is(notNullValue()));

        server.emptyResponse(200);
        request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("DELETE", "/api/v2/grants/1"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
    }
    
    @Test
    public void shouldThrowOnDeleteAllGrantsWithNullUserId() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'user id' cannot be null!");
        api.grants().deleteAll(null);
    }
    
    @Test
    public void shouldDeleteAllGrantsByUserId() throws Exception {
        Request request = api.grants().deleteAll("userId");
        assertThat(request, is(notNullValue()));

        server.emptyResponse(200);
        request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("DELETE", "/api/v2/grants"));
        assertThat(recordedRequest, hasQueryParameter("user_id", "userId"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
    }
    
}

package com.auth0.client.mgmt;

import com.auth0.client.mgmt.filter.GrantsFilter;
import com.auth0.json.mgmt.grants.GrantsPage;
import com.auth0.net.Request;
import com.auth0.net.client.HttpMethod;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Test;

import static com.auth0.client.MockServer.MGMT_GRANTS_LIST;
import static com.auth0.client.MockServer.MGMT_GRANTS_PAGED_LIST;
import static com.auth0.client.RecordedRequestMatcher.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static com.auth0.AssertsUtil.verifyThrows;

public class GrantsEntityTest extends BaseMgmtEntityTest {

    @Test
    public void shouldListGrantsWithoutFilter() throws Exception {
        Request<GrantsPage> request = api.grants().list("userId", null);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_GRANTS_LIST, 200);
        GrantsPage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/grants"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("user_id", "userId"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(2));
    }

    @Test
    public void shouldThrowOnListGrantsWithoutFilterWithNullUserId() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.grants().list(null, null),
            "'user id' cannot be null!");
    }

    @Test
    public void shouldListGrantsWithPage() throws Exception {
        GrantsFilter filter = new GrantsFilter().withPage(23, 5);
        Request<GrantsPage> request = api.grants().list("userId", filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_GRANTS_LIST, 200);
        GrantsPage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/grants"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("user_id", "userId"));
        assertThat(recordedRequest, hasQueryParameter("page", "23"));
        assertThat(recordedRequest, hasQueryParameter("per_page", "5"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(2));
    }

    @Test
    public void shouldListGrantsWithTotals() throws Exception {
        GrantsFilter filter = new GrantsFilter().withTotals(true);
        Request<GrantsPage> request = api.grants().list("userId", filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_GRANTS_PAGED_LIST, 200);
        GrantsPage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/grants"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("user_id", "userId"));
        assertThat(recordedRequest, hasQueryParameter("include_totals", "true"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(2));
        assertThat(response.getStart(), is(0));
        assertThat(response.getLength(), is(14));
        assertThat(response.getTotal(), is(14));
        assertThat(response.getLimit(), is(50));
    }

    @Test
    public void shouldListGrantsWithAdditionalProperties() throws Exception {
        GrantsFilter filter = new GrantsFilter()
                .withAudience("https://myapi.auth0.com")
                .withClientId("u9e3hh3e9j2fj9092ked");
        Request<GrantsPage> request = api.grants().list("userId", filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_GRANTS_LIST, 200);
        GrantsPage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/grants"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("user_id", "userId"));
        assertThat(recordedRequest, hasQueryParameter("audience", "https://myapi.auth0.com"));
        assertThat(recordedRequest, hasQueryParameter("client_id", "u9e3hh3e9j2fj9092ked"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(2));
    }

    @Test
    public void shouldThrowOnDeleteGrantWithNullId() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.grants().delete(null),
            "'grant id' cannot be null!");
    }

    @Test
    public void shouldDeleteGrantById() throws Exception {
        Request<Void> request = api.grants().delete("1");
        assertThat(request, is(notNullValue()));

        server.emptyResponse(200);
        request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.DELETE, "/api/v2/grants/1"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
    }

    @Test
    public void shouldThrowOnDeleteAllGrantsWithNullUserId() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.grants().deleteAll(null),
            "'user id' cannot be null!");
    }

    @Test
    public void shouldDeleteAllGrantsByUserId() throws Exception {
        Request<Void> request = api.grants().deleteAll("userId");
        assertThat(request, is(notNullValue()));

        server.emptyResponse(200);
        request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.DELETE, "/api/v2/grants"));
        assertThat(recordedRequest, hasQueryParameter("user_id", "userId"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
    }

}

package com.auth0.client.mgmt;

import com.auth0.client.mgmt.filter.ClientGrantsFilter;
import com.auth0.json.mgmt.clientgrants.ClientGrant;
import com.auth0.json.mgmt.clientgrants.ClientGrantsPage;
import com.auth0.net.Request;
import com.auth0.net.client.HttpMethod;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Map;

import static com.auth0.client.MockServer.*;
import static com.auth0.client.RecordedRequestMatcher.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static com.auth0.AssertsUtil.verifyThrows;

public class ClientGrantsEntityTest extends BaseMgmtEntityTest {

    @Test
    public void shouldListClientGrantsWithoutFilter() throws Exception {
        Request<ClientGrantsPage> request = api.clientGrants().list(null);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_CLIENT_GRANTS_LIST, 200);
        ClientGrantsPage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/client-grants"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(2));
    }

    @Test
    public void shouldListClientGrantsWithPage() throws Exception {
        ClientGrantsFilter filter = new ClientGrantsFilter().withPage(23, 5);
        Request<ClientGrantsPage> request = api.clientGrants().list(filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_CLIENT_GRANTS_LIST, 200);
        ClientGrantsPage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/client-grants"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("page", "23"));
        assertThat(recordedRequest, hasQueryParameter("per_page", "5"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(2));
    }

    @Test
    public void shouldListClientGrantsWithTotals() throws Exception {
        ClientGrantsFilter filter = new ClientGrantsFilter().withTotals(true);
        Request<ClientGrantsPage> request = api.clientGrants().list(filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_CLIENT_GRANTS_PAGED_LIST, 200);
        ClientGrantsPage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/client-grants"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("include_totals", "true"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(2));
        assertThat(response.getStart(), is(0));
        assertThat(response.getLength(), is(14));
        assertThat(response.getTotal(), is(14));
        assertThat(response.getLimit(), is(50));
    }

    @Test
    public void shouldListClientGrantsWithAdditionalProperties() throws Exception {
        ClientGrantsFilter filter = new ClientGrantsFilter()
                .withAudience("https://myapi.auth0.com")
                .withClientId("u9e3hh3e9j2fj9092ked");
        Request<ClientGrantsPage> request = api.clientGrants().list(filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_CLIENT_GRANTS_LIST, 200);
        ClientGrantsPage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/client-grants"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("audience", "https://myapi.auth0.com"));
        assertThat(recordedRequest, hasQueryParameter("client_id", "u9e3hh3e9j2fj9092ked"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(2));
    }

    @Test
    public void shouldThrowOnCreateClientGrantWithNullClientId() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.clientGrants().create(null, "audience", new String[]{"openid"}),
            "'client id' cannot be null!");
    }

    @Test
    public void shouldThrowOnCreateClientGrantWithNullAudience() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.clientGrants().create("clientId", null, new String[]{"openid"}),
            "'audience' cannot be null!");
    }

    @Test
    public void shouldThrowOnCreateClientGrantWithNullScope() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.clientGrants().create("clientId", "audience", null),
            "'scope' cannot be null!");
    }

    @Test
    public void shouldCreateClientGrant() throws Exception {
        Request<ClientGrant> request = api.clientGrants().create("clientId", "audience", new String[]{"openid"});
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_CLIENT_GRANT, 200);
        ClientGrant response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.POST, "/api/v2/client-grants"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(3));
        assertThat(body, hasEntry("client_id", "clientId"));
        assertThat(body, hasEntry("audience", "audience"));
        assertThat(body, hasKey("scope"));
        assertThat((Iterable<?>) body.get("scope"), contains("openid"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnDeleteClientGrantWithNullId() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.clientGrants().delete(null),
            "'client grant id' cannot be null!");
    }

    @Test
    public void shouldDeleteClientGrant() throws Exception {
        Request<Void> request = api.clientGrants().delete("1");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_CLIENT_GRANT, 200);
        request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.DELETE, "/api/v2/client-grants/1"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
    }

    @Test
    public void shouldThrowOnUpdateClientGrantWithNullId() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.clientGrants().update(null, new String[]{}),
            "'client grant id' cannot be null!");
    }

    @Test
    public void shouldThrowOnUpdateClientGrantWithNullScope() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.clientGrants().update("clientGrantId", null),
            "'scope' cannot be null!");
    }

    @Test
    public void shouldUpdateClientGrant() throws Exception {
        Request<ClientGrant> request = api.clientGrants().update("1", new String[]{"openid", "profile"});
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_CLIENT_GRANT, 200);
        ClientGrant response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.PATCH, "/api/v2/client-grants/1"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(1));
        assertThat((ArrayList<?>) body.get("scope"), contains("openid", "profile"));

        assertThat(response, is(notNullValue()));
    }

}

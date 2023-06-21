package com.auth0.client.mgmt;

import com.auth0.client.mgmt.filter.ConnectionFilter;
import com.auth0.json.mgmt.connections.Connection;
import com.auth0.json.mgmt.connections.ConnectionsPage;
import com.auth0.net.Request;
import com.auth0.net.client.HttpMethod;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.auth0.AssertsUtil.verifyThrows;
import static com.auth0.client.MockServer.*;
import static com.auth0.client.RecordedRequestMatcher.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ConnectionsEntityTest extends BaseMgmtEntityTest {

    @Test
    public void shouldListConnectionsWithoutFilter() throws Exception {
        Request<ConnectionsPage> request = api.connections().listAll(null);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_CONNECTIONS_LIST, 200);
        ConnectionsPage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/connections"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(2));
    }

    @Test
    public void shouldListConnectionsWithPage() throws Exception {
        ConnectionFilter filter = new ConnectionFilter().withPage(23, 5);
        Request<ConnectionsPage> request = api.connections().listAll(filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_CONNECTIONS_LIST, 200);
        ConnectionsPage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/connections"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("page", "23"));
        assertThat(recordedRequest, hasQueryParameter("per_page", "5"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(2));
    }

    @Test
    public void shouldListConnectionsWithTotals() throws Exception {
        ConnectionFilter filter = new ConnectionFilter().withTotals(true);
        Request<ConnectionsPage> request = api.connections().listAll(filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_CONNECTIONS_PAGED_LIST, 200);
        ConnectionsPage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/connections"));
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
    public void shouldThrowOnGetConnectionWithNullId() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.connections().get(null, null),
            "'connection id' cannot be null!");
    }

    @Test
    public void shouldGetConnection() throws Exception {
        Request<Connection> request = api.connections().get("1", null);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_CONNECTION, 200);
        Connection response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/connections/1"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldGetConnectionWithFields() throws Exception {
        ConnectionFilter filter = new ConnectionFilter().withFields("some,random,fields", true);
        Request<Connection> request = api.connections().get("1", filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_CONNECTION, 200);
        Connection response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/connections/1"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("fields", "some,random,fields"));
        assertThat(recordedRequest, hasQueryParameter("include_fields", "true"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnCreateConnectionWithNullData() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.connections().create(null),
            "'connection' cannot be null!");
    }

    @Test
    public void shouldCreateConnection() throws Exception {
        Request<Connection> request = api.connections().create(new Connection("my-connection", "auth0"));
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_CONNECTION, 200);
        Connection response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.POST, "/api/v2/connections"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(2));
        assertThat(body, hasEntry("name", "my-connection"));
        assertThat(body, hasEntry("strategy", "auth0"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnDeleteConnectionWithNullId() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.connections().delete(null),
            "'connection id' cannot be null!");
    }

    @Test
    public void shouldDeleteConnection() throws Exception {
        Request<Void> request = api.connections().delete("1");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_CONNECTION, 200);
        request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.DELETE, "/api/v2/connections/1"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
    }

    @Test
    public void shouldThrowOnUpdateConnectionWithNullId() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.connections().update(null, new Connection("my-connection", "auth0")),
            "'connection id' cannot be null!");
    }

    @Test
    public void shouldThrowOnUpdateConnectionWithNullData() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.connections().update("1", null),
            "'connection' cannot be null!");
    }

    @Test
    public void shouldUpdateConnection() throws Exception {
        Request<Connection> request = api.connections().update("1", new Connection("my-connection", "auth0"));
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_CONNECTION, 200);
        Connection response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.PATCH, "/api/v2/connections/1"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(2));
        assertThat(body, hasEntry("name", "my-connection"));
        assertThat(body, hasEntry("strategy", "auth0"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnDeleteConnectionUserWithNullId() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.connections().deleteUser(null, "user@domain.com"),
            "'connection id' cannot be null!");
    }

    @Test
    public void shouldThrowOnDeleteConnectionUserWithNullEmail() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.connections().deleteUser("1", null),
            "'email' cannot be null!");
    }

    @Test
    public void shouldDeleteConnectionUser() throws Exception {
        Request<Void> request = api.connections().deleteUser("1", "user@domain.com");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_CONNECTION, 200);
        request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.DELETE, "/api/v2/connections/1/users"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("email", "user@domain.com"));
    }
}

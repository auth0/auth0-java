package com.auth0.client.mgmt;

import com.auth0.client.mgmt.filter.ConnectionFilter;
import com.auth0.json.mgmt.Connection;
import com.auth0.json.mgmt.ConnectionsPage;
import com.auth0.net.Request;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static com.auth0.client.MockServer.*;
import static com.auth0.client.RecordedRequestMatcher.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class ConnectionsEntityTest extends BaseMgmtEntityTest {

    @Test
    public void shouldListConnections() throws Exception {
        Request<List<Connection>> request = api.connections().list(null);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_CONNECTIONS_LIST, 200);
        List<Connection> response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/connections"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
        assertThat(response, hasSize(2));
    }

    @Test
    public void shouldListConnectionsWithStrategy() throws Exception {
        ConnectionFilter filter = new ConnectionFilter().withStrategy("auth0");
        Request<List<Connection>> request = api.connections().list(filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_CONNECTIONS_LIST, 200);
        List<Connection> response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/connections"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("strategy", "auth0"));

        assertThat(response, is(notNullValue()));
        assertThat(response, hasSize(2));
    }

    @Test
    public void shouldListConnectionsWithName() throws Exception {
        ConnectionFilter filter = new ConnectionFilter().withName("my-connection");
        Request<List<Connection>> request = api.connections().list(filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_CONNECTIONS_LIST, 200);
        List<Connection> response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/connections"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("name", "my-connection"));

        assertThat(response, is(notNullValue()));
        assertThat(response, hasSize(2));
    }

    @Test
    public void shouldListConnectionsWithFields() throws Exception {
        ConnectionFilter filter = new ConnectionFilter().withFields("some,random,fields", true);
        Request<List<Connection>> request = api.connections().list(filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_CONNECTIONS_LIST, 200);
        List<Connection> response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/connections"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("fields", "some,random,fields"));
        assertThat(recordedRequest, hasQueryParameter("include_fields", "true"));

        assertThat(response, is(notNullValue()));
        assertThat(response, hasSize(2));
    }

    @Test
    public void shouldListConnectionsWithoutFilter() throws Exception {
        Request<ConnectionsPage> request = api.connections().listAll(null);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_CONNECTIONS_LIST, 200);
        ConnectionsPage response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/connections"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(2));
    }

    @Test
    public void shouldNotListConnectionsWithTotals() throws Exception {
        ConnectionFilter filter = new ConnectionFilter().withTotals(true);
        Request<List<Connection>> request = api.connections().list(filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_CONNECTIONS_LIST, 200);
        List<Connection> response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/connections"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, not(hasQueryParameter("include_totals")));

        assertThat(response, is(notNullValue()));
        assertThat(response, hasSize(2));
    }

    @Test
    public void shouldListConnectionsWithPage() throws Exception {
        ConnectionFilter filter = new ConnectionFilter().withPage(23, 5);
        Request<ConnectionsPage> request = api.connections().listAll(filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_CONNECTIONS_LIST, 200);
        ConnectionsPage response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/connections"));
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
        ConnectionsPage response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/connections"));
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
    public void shouldReturnEmptyConnections() throws Exception {
        Request<List<Connection>> request = api.connections().list(null);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_EMPTY_LIST, 200);
        List<Connection> response = request.execute();

        assertThat(response, is(notNullValue()));
        assertThat(response, is(emptyCollectionOf(Connection.class)));
    }

    @Test
    public void shouldThrowOnGetConnectionWithNullId() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'connection id' cannot be null!");
        api.connections().get(null, null);
    }

    @Test
    public void shouldGetConnection() throws Exception {
        Request<Connection> request = api.connections().get("1", null);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_CONNECTION, 200);
        Connection response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/connections/1"));
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
        Connection response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/connections/1"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("fields", "some,random,fields"));
        assertThat(recordedRequest, hasQueryParameter("include_fields", "true"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnCreateConnectionWithNullData() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'connection' cannot be null!");
        api.connections().create(null);
    }

    @Test
    public void shouldCreateConnection() throws Exception {
        Request<Connection> request = api.connections().create(new Connection("my-connection", "auth0"));
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_CONNECTION, 200);
        Connection response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("POST", "/api/v2/connections"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(2));
        assertThat(body, hasEntry("name", (Object) "my-connection"));
        assertThat(body, hasEntry("strategy", (Object) "auth0"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnDeleteConnectionWithNullId() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'connection id' cannot be null!");
        api.connections().delete(null);
    }

    @Test
    public void shouldDeleteConnection() throws Exception {
        Request request = api.connections().delete("1");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_CONNECTION, 200);
        request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("DELETE", "/api/v2/connections/1"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
    }

    @Test
    public void shouldThrowOnUpdateConnectionWithNullId() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'connection id' cannot be null!");
        api.connections().update(null, new Connection("my-connection", "auth0"));
    }

    @Test
    public void shouldThrowOnUpdateConnectionWithNullData() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'connection' cannot be null!");
        api.connections().update("1", null);
    }

    @Test
    public void shouldUpdateConnection() throws Exception {
        Request<Connection> request = api.connections().update("1", new Connection("my-connection", "auth0"));
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_CONNECTION, 200);
        Connection response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("PATCH", "/api/v2/connections/1"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(2));
        assertThat(body, hasEntry("name", (Object) "my-connection"));
        assertThat(body, hasEntry("strategy", (Object) "auth0"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnDeleteConnectionUserWithNullId() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'connection id' cannot be null!");
        api.connections().deleteUser(null, "user@domain.com");
    }

    @Test
    public void shouldThrowOnDeleteConnectionUserWithNullEmail() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'email' cannot be null!");
        api.connections().deleteUser("1", null);
    }

    @Test
    public void shouldDeleteConnectionUser() throws Exception {
        Request request = api.connections().deleteUser("1", "user@domain.com");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_CONNECTION, 200);
        request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("DELETE", "/api/v2/connections/1/users"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("email", "user@domain.com"));
    }
}

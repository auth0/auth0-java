package com.auth0.client.mgmt;

import com.auth0.client.mgmt.filter.ConnectionFilter;
import com.auth0.json.mgmt.connections.*;
import com.auth0.net.Request;
import com.auth0.net.client.HttpMethod;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
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
    public void shouldListConnectionsWithFrom() throws Exception {
        ConnectionFilter filter = new ConnectionFilter().withFrom("10");
        Request<ConnectionsPage> request = api.connections().listAll(filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_CONNECTIONS_PAGED_LIST, 200);
        ConnectionsPage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/connections"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("from", "10"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(2));
        assertThat(response.getStart(), is(0));
        assertThat(response.getLength(), is(14));
        assertThat(response.getTotal(), is(14));
        assertThat(response.getLimit(), is(50));
    }

    @Test
    public void shouldListConnectionsWithTake() throws Exception {
        ConnectionFilter filter = new ConnectionFilter().withTake(1);
        Request<ConnectionsPage> request = api.connections().listAll(filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_CONNECTIONS_PAGED_LIST, 200);
        ConnectionsPage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/connections"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("take", "1"));

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

    @Test
    public void shouldThrowOnGetScimConfigurationWithNullId() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.connections().getScimConfiguration(null),
            "'connection id' cannot be null!");
    }

    @Test
    public void shouldGetScimConfiguration() throws Exception {
        Request<ScimConfigurationResponse> request = api.connections().getScimConfiguration("1");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_CONNECTION_SCIM_CONFIGURATION, 200);
        ScimConfigurationResponse response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/connections/1/scim-configuration"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnDeleteScimConfigurationWithNullId() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.connections().deleteScimConfiguration(null),
            "'connection id' cannot be null!");
    }

    @Test
    public void shouldDeleteScimConfiguration() throws Exception {
        Request<Void> request = api.connections().deleteScimConfiguration("1");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_CONNECTION_SCIM_CONFIGURATION, 200);
        request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.DELETE, "/api/v2/connections/1/scim-configuration"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
    }

    @Test
    public void shouldThrowOnUpdateScimConfigurationWithNullId() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.connections().updateScimConfiguration(null, null),
            "'connection id' cannot be null!");
    }

    @Test
    public void shouldThrowOnUpdateScimConfigurationWithNullScimConfigurationRequest() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.connections().updateScimConfiguration("1", null),
            "'scim configuration request' cannot be null!");
    }

    @Test
    public void shouldThrowOnUpdateScimConfigurationWithNullUserIdAttribute() {
        ScimConfigurationRequest request = new ScimConfigurationRequest();
        request.setMapping(getMappings());

        verifyThrows(IllegalArgumentException.class,
            () -> api.connections().updateScimConfiguration("1", request),
            "'user id attribute' cannot be null!");
    }

    @Test
    public void shouldThrowOnUpdateScimConfigurationWithNullMapping() {
        ScimConfigurationRequest request = new ScimConfigurationRequest();
        request.setUserIdAttribute("externalId");

        verifyThrows(IllegalArgumentException.class,
            () -> api.connections().updateScimConfiguration("1", request),
            "'mapping' cannot be null!");
    }

    @Test
    public void shouldUpdateScimConfiguration() throws Exception {
        ScimConfigurationRequest scimConfigurationRequest = getScimConfiguration();
        Request<ScimConfigurationResponse> request = api.connections().updateScimConfiguration("1", scimConfigurationRequest);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_CONNECTION_SCIM_CONFIGURATION, 200);
        ScimConfigurationResponse response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.PATCH, "/api/v2/connections/1/scim-configuration"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(2));
        assertThat(body, hasEntry("user_id_attribute", "externalId"));
        assertThat(body, hasKey("mapping"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnCreateScimConfigurationWithNullId() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.connections().createScimConfiguration(null, getScimConfiguration()),
            "'connection id' cannot be null!");
    }

    @Test
    public void shouldCreateScimConfiguration() throws Exception {
        ScimConfigurationRequest scimConfigurationRequest = getScimConfiguration();
        Request<ScimConfigurationResponse> request = api.connections().createScimConfiguration("1", scimConfigurationRequest);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_CONNECTION_SCIM_CONFIGURATION, 200);
        ScimConfigurationResponse response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.POST, "/api/v2/connections/1/scim-configuration"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(2));
        assertThat(body, hasEntry("user_id_attribute", "externalId"));
        assertThat(body, hasKey("mapping"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnGetDefaultScimConfigurationWithNullId() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.connections().createScimConfiguration(null, getScimConfiguration()),
            "'connection id' cannot be null!");
    }

    @Test
    public void shouldGetDefaultScimConfiguration() throws Exception {
        Request<DefaultScimMappingResponse> request = api.connections().getDefaultScimConfiguration("1");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_CONNECTION_DEFAULT_SCIM_CONFIGURATION, 200);
        DefaultScimMappingResponse response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/connections/1/scim-configuration/default-mapping"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnGetScimTokenWithNullId() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.connections().getScimToken(null),
            "'connection id' cannot be null!");
    }

    @Test
    public void shouldGetScimToken() throws Exception {
        Request<List<ScimTokenResponse>> request = api.connections().getScimToken("1");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_CONNECTION_SCIM_TOKENS, 200);
        List<ScimTokenResponse> response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/connections/1/scim-configuration/tokens"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
        assertThat(response.size(), is(2));
    }

    @Test
    public void shouldThrowOnCreateScimTokenWithNullId() {
        ScimTokenRequest request = getScimToken();

        verifyThrows(IllegalArgumentException.class,
            () -> api.connections().createScimToken(null, request),
            "'connection id' cannot be null!");
    }

    @Test
    public void shouldThrowOnCreateScimTokenWithNullScimTokenRequest() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.connections().createScimToken("1", null),
            "'scim token request' cannot be null!");
    }

    @Test
    public void shouldCreateScimToken() throws Exception {
        ScimTokenRequest scimTokenRequest = getScimToken();
        Request<ScimTokenCreateResponse> request = api.connections().createScimToken("1", scimTokenRequest);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_CONNECTION_SCIM_TOKEN, 200);
        ScimTokenBaseResponse response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.POST, "/api/v2/connections/1/scim-configuration/tokens"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(2));
        assertThat(body, hasKey("scopes"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnDeleteScimTokenWithNullId() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.connections().deleteScimToken(null, "1"),
            "'connection id' cannot be null!");
    }

    @Test
    public void shouldThrowOnDeleteScimTokenWithNullTokenId() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.connections().deleteScimToken("1", null),
            "'token id' cannot be null!");
    }

    @Test
    public void shouldDeleteScimToken() throws Exception {
        Request<Void> request = api.connections().deleteScimToken("1", "1");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_CONNECTION_SCIM_CONFIGURATION, 200);
        request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.DELETE, "/api/v2/connections/1/scim-configuration/tokens/1"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
    }

    @Test
    public void shouldThrowOnCheckConnectionStatusWithNullId() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.connections().checkConnectionStatus(null),
            "'connection id' cannot be null!");
    }

    @Test
    public void shouldCheckConnectionStatus() throws Exception {
        Request<Void> request = api.connections().checkConnectionStatus("1");
        assertThat(request, is(notNullValue()));

        server.emptyResponse(204);
        request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/connections/1/status"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
    }

    private ScimTokenRequest getScimToken() {
        ScimTokenRequest request = new ScimTokenRequest();
        List<String> scopes = new ArrayList<>();
        scopes.add("get:users");
        request.setScopes(scopes);
        request.setTokenLifetime(1000);
        return request;
    }

    private ScimConfigurationRequest getScimConfiguration() {
        ScimConfigurationRequest request = new ScimConfigurationRequest();
        request.setUserIdAttribute("externalId");
        request.setMapping(getMappings());
        return request;
    }

    private List<Mapping> getMappings() {
        List<Mapping> mappingList = new ArrayList<>();
        mappingList.add(new Mapping("user_id", "id"));
        return mappingList;
    }

}

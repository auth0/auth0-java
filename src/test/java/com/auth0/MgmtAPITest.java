package com.auth0;

import com.auth0.json.mgmt.Connection;
import com.auth0.json.mgmt.DeviceCredentials;
import com.auth0.json.mgmt.client.Client;
import com.auth0.json.mgmt.clientgrant.ClientGrant;
import com.auth0.net.ConnectionFilter;
import com.auth0.net.DeviceCredentialsFilter;
import com.auth0.net.Request;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.auth0.MockServer.*;
import static com.auth0.RecordedRequestMatcher.*;
import static com.auth0.UrlMatcher.isUrl;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class MgmtAPITest {

    private static final String DOMAIN = "domain.auth0.com";
    private static final String API_TOKEN = "apiToken";

    private MockServer server;
    private MgmtAPI api;
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        server = new MockServer();
        api = new MgmtAPI(server.getBaseUrl(), API_TOKEN);
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    // Configuration

    @Test
    public void shouldAcceptDomainWithNoScheme() throws Exception {
        MgmtAPI api = new MgmtAPI("me.something.com", API_TOKEN);

        assertThat(api.getBaseUrl(), isUrl("https", "me.something.com"));
    }

    @Test
    public void shouldAcceptDomainWithHttpScheme() throws Exception {
        MgmtAPI api = new MgmtAPI("http://me.something.com", API_TOKEN);

        assertThat(api.getBaseUrl(), isUrl("http", "me.something.com"));
    }

    @Test
    public void shouldThrowWhenDomainIsNull() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'domain' cannot be null!");
        new MgmtAPI(null, API_TOKEN);
    }

    @Test
    public void shouldThrowWhenApiTokenIsNull() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'api token' cannot be null!");
        new MgmtAPI(DOMAIN, null);
    }


    //Client Grants

    @Test
    public void shouldListClientGrants() throws Exception {
        Request<List<ClientGrant>> request = api.listClientGrants();
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_CLIENT_GRANTS_LIST, 200);
        List<ClientGrant> response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/client-grants"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
        assertThat(response, hasSize(2));
    }

    @Test
    public void shouldReturnEmptyClientGrants() throws Exception {
        Request<List<ClientGrant>> request = api.listClientGrants();
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_EMPTY_LIST, 200);
        List<ClientGrant> response = request.execute();

        assertThat(response, is(notNullValue()));
        assertThat(response, is(emptyCollectionOf(ClientGrant.class)));
    }

    @Test
    public void shouldThrowOnCreateClientGrantWithNullClientId() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'client id' cannot be null!");
        api.createClientGrant(null, "audience", new String[]{"openid"});
    }

    @Test
    public void shouldThrowOnCreateClientGrantWithNullAudience() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'audience' cannot be null!");
        api.createClientGrant("clientId", null, new String[]{"openid"});
    }

    @Test
    public void shouldThrowOnCreateClientGrantWithNullScope() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'scope' cannot be null!");
        api.createClientGrant("clientId", "audience", null);
    }

    @Test
    public void shouldCreateClientGrant() throws Exception {
        Request<ClientGrant> request = api.createClientGrant("clientId", "audience", new String[]{"openid"});
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_CLIENT_GRANT, 200);
        ClientGrant response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("POST", "/api/v2/client-grants"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(3));
        assertThat(body, hasEntry("client_id", (Object) "clientId"));
        assertThat(body, hasEntry("audience", (Object) "audience"));
        assertThat(body, hasKey("scope"));
        assertThat((Iterable<String>) body.get("scope"), contains("openid"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnDeleteClientGrantWithNullId() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'client grant id' cannot be null!");
        api.deleteClientGrant(null);
    }

    @Test
    public void shouldDeleteClientGrant() throws Exception {
        Request request = api.deleteClientGrant("1");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_CLIENT_GRANT, 200);
        request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("DELETE", "/api/v2/client-grants/1"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
    }

    @Test
    public void shouldThrowOnUpdateClientGrantWithNullId() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'client grant id' cannot be null!");
        api.updateClientGrant(null, new String[]{});
    }

    @Test
    public void shouldThrowOnUpdateClientGrantWithNullScope() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'scope' cannot be null!");
        api.updateClientGrant("clientGrantId", null);
    }

    @Test
    public void shouldUpdateClientGrant() throws Exception {
        Request<ClientGrant> request = api.updateClientGrant("1", new String[]{"openid", "profile"});
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_CLIENT_GRANT, 200);
        ClientGrant response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("PATCH", "/api/v2/client-grants/1"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(1));
        assertThat((ArrayList<String>) body.get("scope"), contains("openid", "profile"));

        assertThat(response, is(notNullValue()));
    }


    //Clients

    @Test
    public void shouldListClients() throws Exception {
        Request<List<Client>> request = api.listClients();
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_CLIENTS_LIST, 200);
        List<Client> response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/clients"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
        assertThat(response, hasSize(2));
    }

    @Test
    public void shouldReturnEmptyClients() throws Exception {
        Request<List<Client>> request = api.listClients();
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_EMPTY_LIST, 200);
        List<Client> response = request.execute();

        assertThat(response, is(notNullValue()));
        assertThat(response, is(emptyCollectionOf(Client.class)));
    }

    @Test
    public void shouldThrowOnGetClientWithNullId() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'client id' cannot be null!");
        api.getClient(null);
    }

    @Test
    public void shouldGetClient() throws Exception {
        Request<Client> request = api.getClient("1");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_CLIENT, 200);
        Client response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/clients/1"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnCreateClientWithNullData() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'client' cannot be null!");
        api.createClient(null);
    }

    @Test
    public void shouldCreateClient() throws Exception {
        Request<Client> request = api.createClient(new Client("My Application"));
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_CLIENT, 200);
        Client response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("POST", "/api/v2/clients"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(1));
        assertThat(body, hasEntry("name", (Object) "My Application"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnDeleteClientWithNullId() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'client id' cannot be null!");
        api.deleteClient(null);
    }

    @Test
    public void shouldDeleteClient() throws Exception {
        Request request = api.deleteClient("1");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_CLIENT, 200);
        request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("DELETE", "/api/v2/clients/1"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
    }

    @Test
    public void shouldThrowOnUpdateClientWithNullId() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'client id' cannot be null!");
        api.updateClient(null, new Client("name"));
    }

    @Test
    public void shouldThrowOnUpdateClientWithNullData() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'client' cannot be null!");
        api.updateClient("clientId", null);
    }

    @Test
    public void shouldUpdateClient() throws Exception {
        Request<Client> request = api.updateClient("1", new Client("My Application"));
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_CLIENT, 200);
        Client response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("PATCH", "/api/v2/clients/1"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(1));
        assertThat(body, hasEntry("name", (Object) "My Application"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnRotateClientSecretWithNullId() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'client id' cannot be null!");
        api.rotateClientSecret(null);
    }

    @Test
    public void shouldRotateClientSecret() throws Exception {
        Request<Client> request = api.rotateClientSecret("1");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_CLIENT, 200);
        Client response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("POST", "/api/v2/clients/1/rotate-secret"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Content-Length", "0"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }


    //Connections

    @Test
    public void shouldListConnections() throws Exception {
        Request<List<Connection>> request = api.listConnections(null);
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
        Request<List<Connection>> request = api.listConnections(filter);
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
        Request<List<Connection>> request = api.listConnections(filter);
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
        Request<List<Connection>> request = api.listConnections(filter);
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
    public void shouldReturnEmptyConnections() throws Exception {
        Request<List<Connection>> request = api.listConnections(null);
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
        api.getConnection(null, null);
    }

    @Test
    public void shouldGetConnection() throws Exception {
        Request<Connection> request = api.getConnection("1", null);
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
        Request<Connection> request = api.getConnection("1", filter);
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
        api.createConnection(null);
    }

    @Test
    public void shouldCreateConnection() throws Exception {
        Request<Connection> request = api.createConnection(new Connection("my-connection", "auth0"));
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
        api.deleteConnection(null);
    }

    @Test
    public void shouldDeleteConnection() throws Exception {
        Request request = api.deleteConnection("1");
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
        api.updateConnection(null, new Connection("my-connection", "auth0"));
    }

    @Test
    public void shouldThrowOnUpdateConnectionWithNullData() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'connection' cannot be null!");
        api.updateConnection("1", null);
    }

    @Test
    public void shouldUpdateConnection() throws Exception {
        Request<Connection> request = api.updateConnection("1", new Connection("my-connection", "auth0"));
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
        api.deleteConnectionUser(null, "user@domain.com");
    }

    @Test
    public void shouldThrowOnDeleteConnectionUserWithNullEmail() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'email' cannot be null!");
        api.deleteConnectionUser("1", null);
    }

    @Test
    public void shouldDeleteConnectionUser() throws Exception {
        Request request = api.deleteConnectionUser("1", "user@domain.com");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_CONNECTION, 200);
        request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("DELETE", "/api/v2/connections/1/users"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("email", "user@domain.com"));
    }


    // DeviceCredentials


    @Test
    public void shouldListDeviceCredentials() throws Exception {
        Request<List<DeviceCredentials>> request = api.listDeviceCredentials(null);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_DEVICE_CREDENTIALS_LIST, 200);
        List<DeviceCredentials> response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/device-credentials"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
        assertThat(response, hasSize(2));
    }

    @Test
    public void shouldListDeviceCredentialsWithClientId() throws Exception {
        DeviceCredentialsFilter filter = new DeviceCredentialsFilter().withClientId("client_23");
        Request<List<DeviceCredentials>> request = api.listDeviceCredentials(filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_DEVICE_CREDENTIALS_LIST, 200);
        List<DeviceCredentials> response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/device-credentials"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("client_id", "client_23"));

        assertThat(response, is(notNullValue()));
        assertThat(response, hasSize(2));
    }

    @Test
    public void shouldListDeviceCredentialsWithUserId() throws Exception {
        DeviceCredentialsFilter filter = new DeviceCredentialsFilter().withUserId("user_23");
        Request<List<DeviceCredentials>> request = api.listDeviceCredentials(filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_DEVICE_CREDENTIALS_LIST, 200);
        List<DeviceCredentials> response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/device-credentials"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("user_id", "user_23"));

        assertThat(response, is(notNullValue()));
        assertThat(response, hasSize(2));
    }


    @Test
    public void shouldListDeviceCredentialsWithType() throws Exception {
        DeviceCredentialsFilter filter = new DeviceCredentialsFilter().withType("public_key");
        Request<List<DeviceCredentials>> request = api.listDeviceCredentials(filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_DEVICE_CREDENTIALS_LIST, 200);
        List<DeviceCredentials> response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/device-credentials"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("type", "public_key"));

        assertThat(response, is(notNullValue()));
        assertThat(response, hasSize(2));
    }


    @Test
    public void shouldListDeviceCredentialsWithFields() throws Exception {
        DeviceCredentialsFilter filter = new DeviceCredentialsFilter().withFields("some,random,fields", true);
        Request<List<DeviceCredentials>> request = api.listDeviceCredentials(filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_DEVICE_CREDENTIALS_LIST, 200);
        List<DeviceCredentials> response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/device-credentials"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("fields", "some,random,fields"));
        assertThat(recordedRequest, hasQueryParameter("include_fields", "true"));

        assertThat(response, is(notNullValue()));
        assertThat(response, hasSize(2));
    }

    @Test
    public void shouldReturnEmptyDeviceCredentials() throws Exception {
        Request<List<DeviceCredentials>> request = api.listDeviceCredentials(null);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_EMPTY_LIST, 200);
        List<DeviceCredentials> response = request.execute();

        assertThat(response, is(notNullValue()));
        assertThat(response, is(emptyCollectionOf(DeviceCredentials.class)));
    }

    @Test
    public void shouldThrowOnCreateDeviceCredentialsWithNullData() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'device credentials' cannot be null!");
        api.createDeviceCredentials(null);
    }

    @Test
    public void shouldCreateDeviceCredentials() throws Exception {
        Request<DeviceCredentials> request = api.createDeviceCredentials(new DeviceCredentials("device", "public_key", "val123", "id123", "clientId"));
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_DEVICE_CREDENTIALS, 200);
        DeviceCredentials response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("POST", "/api/v2/device-credentials"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(5));
        assertThat(body, hasEntry("device_name", (Object) "device"));
        assertThat(body, hasEntry("type", (Object) "public_key"));
        assertThat(body, hasEntry("value", (Object) "val123"));
        assertThat(body, hasEntry("device_id", (Object) "id123"));
        assertThat(body, hasEntry("client_id", (Object) "clientId"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnDeleteDeviceCredentialsWithNullId() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'device credentials id' cannot be null!");
        api.deleteDeviceCredentials(null);
    }

    @Test
    public void shouldDeleteDeviceCredentials() throws Exception {
        Request request = api.deleteDeviceCredentials("1");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_DEVICE_CREDENTIALS, 200);
        request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("DELETE", "/api/v2/device-credentials/1"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
    }

}
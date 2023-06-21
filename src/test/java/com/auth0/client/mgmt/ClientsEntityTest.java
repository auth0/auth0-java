package com.auth0.client.mgmt;

import com.auth0.client.mgmt.filter.ClientFilter;
import com.auth0.client.mgmt.filter.FieldsFilter;
import com.auth0.json.mgmt.client.Client;
import com.auth0.json.mgmt.client.ClientsPage;
import com.auth0.json.mgmt.client.Credential;
import com.auth0.net.Request;
import com.auth0.net.client.HttpMethod;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static com.auth0.client.MockServer.*;
import static com.auth0.client.RecordedRequestMatcher.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static com.auth0.AssertsUtil.verifyThrows;

public class ClientsEntityTest extends BaseMgmtEntityTest {

    @Test
    public void shouldListClientsWithoutFilter() throws Exception {
        Request<ClientsPage> request = api.clients().list(null);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_CLIENTS_LIST, 200);
        ClientsPage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/clients"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(2));
    }

    @Test
    public void shouldListClientsWithPage() throws Exception {
        ClientFilter filter = new ClientFilter().withPage(23, 5);
        Request<ClientsPage> request = api.clients().list(filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_CLIENTS_LIST, 200);
        ClientsPage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/clients"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("page", "23"));
        assertThat(recordedRequest, hasQueryParameter("per_page", "5"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(2));
    }

    @Test
    public void shouldListClientsWithTotals() throws Exception {
        ClientFilter filter = new ClientFilter().withTotals(true);
        Request<ClientsPage> request = api.clients().list(filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_CLIENTS_PAGED_LIST, 200);
        ClientsPage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/clients"));
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
    public void shouldListClientsWithFields() throws Exception {
        ClientFilter filter = new ClientFilter().withFields("some,random,fields", true);
        Request<ClientsPage> request = api.clients().list(filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_CLIENTS_PAGED_LIST, 200);
        ClientsPage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/clients"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("fields", "some,random,fields"));
        assertThat(recordedRequest, hasQueryParameter("include_fields", "true"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(2));
    }

    @Test
    public void shouldListClientsWithAdditionalProperties() throws Exception {
        ClientFilter filter = new ClientFilter()
                .withAppType("regular_web,native")
                .withIsFirstParty(true)
                .withIsGlobal(true);
        Request<ClientsPage> request = api.clients().list(filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_CLIENTS_LIST, 200);
        ClientsPage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/clients"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("app_type", "regular_web,native"));
        assertThat(recordedRequest, hasQueryParameter("is_first_party", "true"));
        assertThat(recordedRequest, hasQueryParameter("is_global", "true"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(2));
    }

    @Test
    public void shouldThrowOnGetClientWithNullId() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.clients().get(null),
            "'client id' cannot be null!");
    }

    @Test
    public void shouldGetClient() throws Exception {
        Request<Client> request = api.clients().get("1");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_CLIENT, 200);
        Client response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/clients/1"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldGetClientWithFilter() throws Exception {
        FieldsFilter fieldsFilter = new FieldsFilter()
            .withFields("name,client_id,app_type,tenant", true);

        Request<Client> request = api.clients().get("1", fieldsFilter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_CLIENT, 200);
        Client response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/clients/1"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("fields", "name,client_id,app_type,tenant"));
        assertThat(recordedRequest, hasQueryParameter("include_fields", "true"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldGetClientWithNullFilter() throws Exception {
        Request<Client> request = api.clients().get("1", null);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_CLIENT, 200);
        Client response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/clients/1"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnCreateClientWithNullData() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.clients().create(null),
            "'client' cannot be null!");
    }

    @Test
    public void shouldCreateClient() throws Exception {
        Request<Client> request = api.clients().create(new Client("My Application"));
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_CLIENT, 200);
        Client response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.POST, "/api/v2/clients"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(1));
        assertThat(body, hasEntry("name", "My Application"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnDeleteClientWithNullId() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.clients().delete(null),
            "'client id' cannot be null!");
    }

    @Test
    public void shouldDeleteClient() throws Exception {
        Request<Void> request = api.clients().delete("1");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_CLIENT, 200);
        request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.DELETE, "/api/v2/clients/1"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
    }

    @Test
    public void shouldThrowOnUpdateClientWithNullId() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.clients().update(null, new Client("name")),
            "'client id' cannot be null!");
    }

    @Test
    public void shouldThrowOnUpdateClientWithNullData() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.clients().update("clientId", null),
            "'client' cannot be null!");
    }

    @Test
    public void shouldUpdateClient() throws Exception {
        Request<Client> request = api.clients().update("1", new Client("My Application"));
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_CLIENT, 200);
        Client response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.PATCH, "/api/v2/clients/1"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(1));
        assertThat(body, hasEntry("name", "My Application"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnRotateClientSecretWithNullId() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.clients().rotateSecret(null),
            "'client id' cannot be null!");
    }

    @Test
    public void shouldRotateClientSecret() throws Exception {
        Request<Client> request = api.clients().rotateSecret("1");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_CLIENT, 200);
        Client response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.POST, "/api/v2/clients/1/rotate-secret"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Content-Length", "0"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldListClientCredentials() throws Exception {
        Request<List<Credential>> request = api.clients().listCredentials("clientId");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_CLIENT_CREDENTIAL_LIST, 200);
        List<Credential> response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/clients/clientId/credentials"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnListCredentialsWithNullClientId() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.clients().listCredentials(null),
            "'client id' cannot be null!");
    }

    @Test
    public void shouldGetClientCredential() throws Exception {
        Request<Credential> request = api.clients().getCredential("clientId", "credId");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_CLIENT_CREDENTIAL, 200);
        Credential response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/clients/clientId/credentials/credId"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnGetCredentialsWithNullClientId() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.clients().getCredential(null, "credId"),
            "'client id' cannot be null!");
    }

    @Test
    public void shouldThrowOnGetCredentialsWithNullCredentialId() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.clients().getCredential("clientId", null),
            "'credential id' cannot be null!");
    }

    @Test
    public void shouldCreateClientCredential() throws Exception {
        Credential credential = new Credential("public_key", "pem");
        Request<Credential> request = api.clients().createCredential("clientId", credential);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_CLIENT_CREDENTIAL, 201);
        Credential response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.POST, "/api/v2/clients/clientId/credentials"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(2));
        assertThat(body, hasEntry("credential_type", "public_key"));
        assertThat(body, hasEntry("pem", "pem"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnCreateCredentialsWithNullClientId() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.clients().createCredential(null, new Credential()),
            "'client id' cannot be null!");
    }

    @Test
    public void shouldThrowOnCreateCredentialsWithNullCredentialId() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.clients().deleteCredential("clientId", null),
            "'credential id' cannot be null!");
    }

    @Test
    public void shouldDeleteCredential() throws Exception {
        Request<Void> request = api.clients().deleteCredential("clientId", "credId");
        assertThat(request, is(notNullValue()));

        server.emptyResponse(204);
        request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.DELETE, "/api/v2/clients/clientId/credentials/credId"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
    }

    @Test
    public void shouldThrowOnDeleteCredentialsWithNullClientId() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.clients().deleteCredential(null, "credId"),
            "'client id' cannot be null!");
    }

    @Test
    public void shouldThrowOnDeleteCredentialsWithNullCredentialId() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.clients().deleteCredential("clientId", null),
            "'credential id' cannot be null!");
    }
}

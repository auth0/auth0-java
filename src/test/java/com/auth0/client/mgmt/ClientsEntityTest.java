package com.auth0.client.mgmt;

import com.auth0.json.mgmt.client.Client;
import com.auth0.net.Request;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static com.auth0.client.MockServer.*;
import static com.auth0.client.RecordedRequestMatcher.hasHeader;
import static com.auth0.client.RecordedRequestMatcher.hasMethodAndPath;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class ClientsEntityTest extends BaseMgmtEntityTest {

    @Test
    public void shouldListClients() throws Exception {
        Request<List<Client>> request = api.clients().list();
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
        Request<List<Client>> request = api.clients().list();
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
        api.clients().get(null);
    }

    @Test
    public void shouldGetClient() throws Exception {
        Request<Client> request = api.clients().get("1");
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
        api.clients().create(null);
    }

    @Test
    public void shouldCreateClient() throws Exception {
        Request<Client> request = api.clients().create(new Client("My Application"));
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
        api.clients().delete(null);
    }

    @Test
    public void shouldDeleteClient() throws Exception {
        Request request = api.clients().delete("1");
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
        api.clients().update(null, new Client("name"));
    }

    @Test
    public void shouldThrowOnUpdateClientWithNullData() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'client' cannot be null!");
        api.clients().update("clientId", null);
    }

    @Test
    public void shouldUpdateClient() throws Exception {
        Request<Client> request = api.clients().update("1", new Client("My Application"));
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
        api.clients().rotateSecret(null);
    }

    @Test
    public void shouldRotateClientSecret() throws Exception {
        Request<Client> request = api.clients().rotateSecret("1");
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

}

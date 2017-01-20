package com.auth0.client.mgmt;

import com.auth0.json.mgmt.client.ResourceServer;
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

public class ResourceServersEntityTest extends BaseMgmtEntityTest {
    @Test
    public void shouldListResourceServers() throws Exception {
        Request<List<ResourceServer>> request = api.resourceServers().listResourceServers();
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_RESOURCE_SERVERS_LIST, 200);
        List<ResourceServer> response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/resource-servers"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
        assertThat(response, hasSize(2));
    }

    @Test
    public void shouldReturnEmptyResourceServers() throws Exception {
        Request<List<ResourceServer>> request = api.resourceServers().listResourceServers();
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_EMPTY_LIST, 200);
        List<ResourceServer> response = request.execute();

        assertThat(response, is(notNullValue()));
        assertThat(response, is(emptyCollectionOf(ResourceServer.class)));
    }

    @Test
    public void shouldThrowOnGetResourceServerWithNullId() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'resource server id' cannot be null!");
        api.resourceServers().getResourceServer(null);
    }

    @Test
    public void shouldGetResourceServer() throws Exception {
        Request<ResourceServer> request = api.resourceServers().getResourceServer("1");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_RESOURCE_SERVER, 200);
        ResourceServer response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/resource-servers/1"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnCreateResourceServerWithNullData() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'resource server' cannot be null!");
        api.resourceServers().createResourceServer(null);
    }

    @Test
    public void shouldCreateResourceServer() throws Exception {
        Request<ResourceServer> request = api.resourceServers().createResourceServer(new ResourceServer("id"));
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_RESOURCE_SERVER, 200);
        ResourceServer response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("POST", "/api/v2/resource-servers"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(1));
        assertThat(body, hasEntry("identifier", (Object) "id"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnDeleteResourceServerWithNullId() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'resource server id' cannot be null!");
        api.resourceServers().deleteResourceServer(null);
    }

    @Test
    public void shouldDeleteResourceServer() throws Exception {
        Request request = api.resourceServers().deleteResourceServer("1");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_RESOURCE_SERVER, 200);
        request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("DELETE", "/api/v2/resource-servers/1"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
    }

    @Test
    public void shouldThrowOnUpdateResourceServerWithNullId() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'resource server id' cannot be null!");
        api.resourceServers().updateResourceServer(null, new ResourceServer("id"));
    }

    @Test
    public void shouldThrowOnUpdateResourceServerWithNullData() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'resource server' cannot be null!");
        api.resourceServers().updateResourceServer("1", null);
    }

    @Test
    public void shouldUpdateResourceServer() throws Exception {
        Request<ResourceServer> request = api.resourceServers().updateResourceServer("1", new ResourceServer("1"));
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_RESOURCE_SERVER, 200);
        ResourceServer response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("PATCH", "/api/v2/resource-servers/1"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(1));
        assertThat(body, hasEntry("identifier", (Object) "1"));

        assertThat(response, is(notNullValue()));
    }
}

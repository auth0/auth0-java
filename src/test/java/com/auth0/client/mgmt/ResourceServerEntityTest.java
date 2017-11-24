package com.auth0.client.mgmt;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.auth0.json.mgmt.ResourceServer;
import com.auth0.json.mgmt.Scope;
import com.auth0.net.Request;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.Test;

import static com.auth0.client.MockServer.MGMT_RESOURCE_SERVER;
import static com.auth0.client.MockServer.MGMT_RESOURCE_SERVERS_LIST;
import static com.auth0.client.MockServer.bodyFromRequest;
import static com.auth0.client.RecordedRequestMatcher.hasHeader;
import static com.auth0.client.RecordedRequestMatcher.hasMethodAndPath;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class ResourceServerEntityTest extends BaseMgmtEntityTest {
    @Test
    public void shouldListResourceServers() throws Exception {
        Request<List<ResourceServer>> request = api.resourceServers().list();

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
    public void shouldUpdateResourceServer() throws Exception {
        Request<ResourceServer> request = api.resourceServers()
                                             .update("23445566abab",
                                                     new ResourceServer("23445566abab",
                                                                        "Some API",
                                                                        "https://api.my-company.com/api/v2/",
                                                                        Collections.singletonList(
                                                                                new Scope("update:something",
                                                                                          "Description")),
                                                                        "RS256",
                                                                        "secret",
                                                                        false,
                                                                        false,
                                                                        0,
                                                                        "verification_key",
                                                                        "verification_location",
                                                                        false));
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_RESOURCE_SERVER, 200);
        ResourceServer response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("PATCH", "/api/v2/resource-servers/23445566abab"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(12));
        assertThat(body, hasEntry("identifier", (Object) "https://api.my-company.com/api/v2/"));

        assertThat(response.getIdentifier(), is("https://api.my-company.com/api/v2/"));
    }

    @Test
    public void shouldGetResourceServerById() throws Exception {
        Request<ResourceServer> request = api.resourceServers()
                                             .get("23445566abab");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_RESOURCE_SERVER, 200);
        ResourceServer response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/resource-servers/23445566abab"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response.getIdentifier(), is("https://api.my-company.com/api/v2/"));
    }

    @Test
    public void shouldCreateResourceServer() throws Exception {
        Request<ResourceServer> request = api.resourceServers()
                                             .create(new ResourceServer("https://api.my-company.com/api/v2/"));
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_RESOURCE_SERVER, 200);
        ResourceServer response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("POST", "/api/v2/resource-servers"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(1));
        assertThat(body, hasEntry("identifier", (Object) "https://api.my-company.com/api/v2/"));

        assertThat(response.getIdentifier(), is("https://api.my-company.com/api/v2/"));
    }

    @Test
    public void shouldDeleteResourceServer() throws Exception {
        Request<Void> request = api.resourceServers()
                                   .delete("23445566abab");
        assertThat(request, is(notNullValue()));

        server.emptyResponse(200);
        request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("DELETE", "/api/v2/resource-servers/23445566abab"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
    }
}

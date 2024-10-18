package com.auth0.client.mgmt;

import com.auth0.client.mgmt.filter.ResourceServersFilter;
import com.auth0.json.mgmt.resourceserver.ResourceServer;
import com.auth0.json.mgmt.resourceserver.ResourceServersPage;
import com.auth0.json.mgmt.resourceserver.Scope;
import com.auth0.net.Request;
import com.auth0.net.client.HttpMethod;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Map;

import static com.auth0.client.MockServer.*;
import static com.auth0.client.RecordedRequestMatcher.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ResourceServerEntityTest extends BaseMgmtEntityTest {


    @Test
    public void shouldListResourceServerWithoutFilter() throws Exception {
        Request<ResourceServersPage> request = api.resourceServers().list(null);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_RESOURCE_SERVERS_LIST, 200);
        ResourceServersPage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/resource-servers"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(2));
    }

    @Test
    public void shouldListResourceServerWithPage() throws Exception {
        ResourceServersFilter filter = new ResourceServersFilter().withPage(23, 5);
        Request<ResourceServersPage> request = api.resourceServers().list(filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_RESOURCE_SERVERS_LIST, 200);
        ResourceServersPage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/resource-servers"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("page", "23"));
        assertThat(recordedRequest, hasQueryParameter("per_page", "5"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(2));
    }

    @Test
    public void shouldListResourceServerWithTotals() throws Exception {
        ResourceServersFilter filter = new ResourceServersFilter().withTotals(true);
        Request<ResourceServersPage> request = api.resourceServers().list(filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_RESOURCE_SERVERS_PAGED_LIST, 200);
        ResourceServersPage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/resource-servers"));
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
    public void shouldListResourceServerWithIdentifiers() throws Exception {
        ResourceServersFilter filter = new ResourceServersFilter().withIdentifiers("identifier");
        Request<ResourceServersPage> request = api.resourceServers().list(filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_RESOURCE_SERVERS_PAGED_LIST, 200);
        ResourceServersPage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/resource-servers"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("identifiers", "identifier"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(2));
        assertThat(response.getStart(), is(0));
        assertThat(response.getLength(), is(14));
        assertThat(response.getTotal(), is(14));
        assertThat(response.getLimit(), is(50));
    }

    @Test
    public void shouldListResourceServerWithCheckpointPagination() throws Exception {
        ResourceServersFilter filter = new ResourceServersFilter().withCheckpointPagination("tokenId2", 5);
        Request<ResourceServersPage> request = api.resourceServers().list(filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_RESOURCE_SERVERS_PAGED_LIST, 200);
        ResourceServersPage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/resource-servers"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("from", "tokenId2"));
        assertThat(recordedRequest, hasQueryParameter("take", "5"));
        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(2));
        assertThat(response.getStart(), is(0));
        assertThat(response.getLength(), is(14));
        assertThat(response.getTotal(), is(14));
        assertThat(response.getLimit(), is(50));
    }

    @Test
    public void shouldUpdateResourceServer() throws Exception {
        ResourceServer resourceServer = new ResourceServer("https://api.my-company.com/api/v2/");
        resourceServer.setId("23445566abab");
        resourceServer.setName("Some API");
        resourceServer.setScopes(Collections.singletonList(new Scope("update:something")));
        resourceServer.setSigningAlgorithm("RS256");
        resourceServer.setSigningSecret("secret");
        resourceServer.setAllowOfflineAccess(false);
        resourceServer.setEnforcePolicies(true);
        resourceServer.setSkipConsentForVerifiableFirstPartyClients(false);
        resourceServer.setTokenDialect("access_token");
        resourceServer.setTokenLifetime(0);
        resourceServer.setVerificationLocation("verification_location");
        Request<ResourceServer> request = api.resourceServers()
                .update("23445566abab", resourceServer);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_RESOURCE_SERVER, 200);
        ResourceServer response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.PATCH, "/api/v2/resource-servers/23445566abab"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(12));
        assertThat(body, hasEntry("identifier", "https://api.my-company.com/api/v2/"));

        assertThat(response.getIdentifier(), is("https://api.my-company.com/api/v2/"));
    }

    @Test
    public void shouldGetResourceServerById() throws Exception {
        Request<ResourceServer> request = api.resourceServers()
                .get("23445566abab");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_RESOURCE_SERVER, 200);
        ResourceServer response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/resource-servers/23445566abab"));
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
        ResourceServer response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.POST, "/api/v2/resource-servers"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(1));
        assertThat(body, hasEntry("identifier", "https://api.my-company.com/api/v2/"));

        assertThat(response.getIdentifier(), is("https://api.my-company.com/api/v2/"));
    }

    @Test
    public void shouldDeleteResourceServer() throws Exception {
        Request<Void> request = api.resourceServers()
                .delete("23445566abab");
        assertThat(request, is(notNullValue()));

        server.emptyResponse(200);
        request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.DELETE, "/api/v2/resource-servers/23445566abab"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
    }
}

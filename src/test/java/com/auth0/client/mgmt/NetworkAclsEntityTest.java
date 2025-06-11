package com.auth0.client.mgmt;

import com.auth0.client.MockServer;
import com.auth0.client.mgmt.filter.NetworkAclsFilter;
import com.auth0.json.mgmt.networkacls.*;
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

public class NetworkAclsEntityTest extends BaseMgmtEntityTest {

    // Network ACLs entity

    @Test
    public void shouldListNetworkAclsWithoutFilter() throws Exception {
        Request<NetworkAclsPage> request = api.networkAcls().list(null);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.MGMT_NETWORK_ACLS_LIST, 200);
        NetworkAclsPage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/network-acls"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(2));
    }

    @Test
    public void shouldListNetworkAclsWithFilter() throws Exception {
        NetworkAclsFilter filter = new NetworkAclsFilter()
            .withPage(0, 10)
            .withTotals(true);

        Request<NetworkAclsPage> request = api.networkAcls().list(filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.MGMT_NETWORK_ACLS_LIST, 200);
        NetworkAclsPage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/network-acls"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("page", "0"));
        assertThat(recordedRequest, hasQueryParameter("per_page", "10"));
        assertThat(recordedRequest, hasQueryParameter("include_totals", "true"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(2));
    }

    @Test
    public void shouldThrowOnCreateNetworkAclWithNullNetworkAcls() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.networkAcls().create(null),
            "'network acls' cannot be null!");
    }

    @Test
    public void shouldCreateNetworkAcl() throws Exception {
        NetworkAcls networkAclsToCreate = new NetworkAcls();
        networkAclsToCreate.setDescription("Log America and Canada all the time.");
        networkAclsToCreate.setActive(true);
        networkAclsToCreate.setPriority(1);
        Rule rule = new Rule();
        Action action = new Action();
        action.setLog(true);
        rule.setAction(action);

        rule.setScope("authentication");

        Match match = new Match();
        List<String> geoCountryCodes = new ArrayList<>();
        geoCountryCodes.add("US");
        geoCountryCodes.add("CA");

        match.setGeoCountryCodes(geoCountryCodes);
        rule.setMatch(match);
        networkAclsToCreate.setRule(rule);


        Request<NetworkAcls> request = api.networkAcls().create(networkAclsToCreate);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.MGMT_NETWORK_ACLS, 201);
        NetworkAcls response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.POST, "/api/v2/network-acls"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body, aMapWithSize(4));
        assertThat(body, hasEntry("description", "Log America and Canada all the time."));
        assertThat(body, hasEntry("active", true));
        assertThat(body, hasEntry("priority", 1));
        assertThat(body, hasEntry(is("rule"), is(notNullValue())));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnGetNetworkAclWithNullId() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.networkAcls().get(null),
            "'id' cannot be null!");
    }

    @Test
    public void shouldGetNetworkAcl() throws Exception {
        Request<NetworkAcls> request = api.networkAcls().get("acl_1");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.MGMT_NETWORK_ACLS, 200);
        NetworkAcls response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/network-acls/acl_1"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getId(), is("acl_1"));
        assertThat(response.getDescription(), is("Log America and Canada all the time."));
    }

    @Test
    public void shouldThrowOnDeleteNetworkAclWithNullId() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.networkAcls().delete(null),
            "'id' cannot be null!");
    }

    @Test
    public void shouldDeleteNetworkAcl() throws Exception {
        Request<Void> request = api.networkAcls().delete("acl_1");
        assertThat(request, is(notNullValue()));

        server.emptyResponse(204);
        request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.DELETE, "/api/v2/network-acls/acl_1"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
    }

    @Test
    public void shouldThrowOnUpdateNetworkAclWithNullId() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.networkAcls().update(null, new NetworkAcls()),
            "'id' cannot be null!");
    }

    @Test
    public void shouldThrowOnUpdateNetworkAclWithNullNetworkAcls() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.networkAcls().update("acl_1", null),
            "'network acls' cannot be null!");
    }

    @Test
    public void shouldUpdateNetworkAcl() throws Exception {
        NetworkAcls networkAclsToUpdate = new NetworkAcls();
        networkAclsToUpdate.setDescription("Log America and Canada all the time.");
        networkAclsToUpdate.setActive(false);
        networkAclsToUpdate.setPriority(5);

        Request<NetworkAcls> request = api.networkAcls().update("acl_1", networkAclsToUpdate);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.MGMT_NETWORK_ACLS, 200);
        NetworkAcls response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.PUT, "/api/v2/network-acls/acl_1"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body, aMapWithSize(3));
        assertThat(body, hasEntry("description", "Log America and Canada all the time."));
        assertThat(body, hasEntry("active", false));
        assertThat(body, hasEntry("priority", 5));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnPatchNetworkAclWithNullId() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.networkAcls().patch(null, new NetworkAcls()),
            "'id' cannot be null!");
    }

    @Test
    public void shouldPatchNetworkAcl() throws Exception {
        NetworkAcls networkAclsToPatch = new NetworkAcls();
        networkAclsToPatch.setActive(true);
        networkAclsToPatch.setPriority(3);

        Request<NetworkAcls> request = api.networkAcls().patch("acl_1", networkAclsToPatch);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.MGMT_NETWORK_ACLS, 200);
        NetworkAcls response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.PATCH, "/api/v2/network-acls/acl_1"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body, aMapWithSize(2));
        assertThat(body, hasEntry("active", true));
        assertThat(body, hasEntry("priority", 3));

        assertThat(response, is(notNullValue()));
    }
}

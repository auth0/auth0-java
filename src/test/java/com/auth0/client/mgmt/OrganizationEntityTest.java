package com.auth0.client.mgmt;

import com.auth0.client.MockServer;
import com.auth0.client.mgmt.filter.*;
import com.auth0.exception.Auth0Exception;
import com.auth0.json.mgmt.client.Client;
import com.auth0.json.mgmt.client.ClientDefaultOrganization;
import com.auth0.json.mgmt.client.ClientsPage;
import com.auth0.json.mgmt.clientgrants.ClientGrant;
import com.auth0.json.mgmt.clientgrants.ClientGrantsPage;
import com.auth0.json.mgmt.organizations.*;
import com.auth0.json.mgmt.resourceserver.ResourceServer;
import com.auth0.json.mgmt.roles.RolesPage;
import com.auth0.net.Request;
import com.auth0.net.client.HttpMethod;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Test;

import java.util.*;

import static com.auth0.AssertsUtil.verifyThrows;
import static com.auth0.client.MockServer.*;
import static com.auth0.client.RecordedRequestMatcher.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class OrganizationEntityTest extends BaseMgmtEntityTest {

    //  Organizations entity

    @Test
    public void shouldListOrgsWithoutFilter() throws Exception {
        Request<OrganizationsPage> request = api.organizations().list(null);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.ORGANIZATIONS_LIST, 200);
        OrganizationsPage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/organizations"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(2));
    }

    @Test
    public void shouldListOrgsWithPage() throws Exception {
        PageFilter filter = new PageFilter().withPage(2, 30);
        Request<OrganizationsPage> request = api.organizations().list(filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.ORGANIZATIONS_PAGED_LIST, 200);
        OrganizationsPage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/organizations"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("page", "2"));
        assertThat(recordedRequest, hasQueryParameter("per_page", "30"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(2));
    }

    @Test
    public void shouldListOrgsWithCheckpointPagination() throws Exception {
        PageFilter filter = new PageFilter().withTake(10).withFrom("from-id");
        Request<OrganizationsPage> request = api.organizations().list(filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(ORGANIZATIONS_CHECKPOINT_PAGED_LIST, 200);
        OrganizationsPage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/organizations"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("take", "10"));
        assertThat(recordedRequest, hasQueryParameter("from", "from-id"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(2));
    }

    @Test
    public void shouldListOrgsWithTotals() throws Exception {
        PageFilter filter = new PageFilter().withTotals(true);
        Request<OrganizationsPage> request = api.organizations().list(filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.ORGANIZATIONS_PAGED_LIST, 200);
        OrganizationsPage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/organizations"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("include_totals", "true"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(2));
    }

    @Test
    public void shouldThrowOnGetOrgWithNullId() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.organizations().get(null),
            "'organization ID' cannot be null!");
    }

    @Test
    public void shouldGetOrganization() throws Exception {
        Request<Organization> request = api.organizations().get("org_123");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.ORGANIZATION, 200);
        Organization response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/organizations/org_123"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnGetOrgWithNullName() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.organizations().getByName( null),
            "'organization name' cannot be null!");
    }

    @Test
    public void shouldGetOrganizationByName() throws Exception {
        Request<Organization> request = api.organizations().getByName("org-1");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.ORGANIZATION, 200);
        Organization response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/organizations/name/org-1"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnCreateOrgWithNullOrg() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.organizations().create(null),
            "'organization' cannot be null!");
    }

    @Test
    public void shouldCreateOrganization() throws Exception {
        Organization orgToCreate = new Organization("test-org");
        orgToCreate.setDisplayName("display name");

        Colors colors = new Colors();
        colors.setPageBackground("#FF0000");
        colors.setPrimary("#FF0000");
        Branding branding = new Branding();
        branding.setColors(colors);
        branding.setLogoUrl("https://some-uri.com");
        orgToCreate.setBranding(branding);

        Map<String, Object> metadata = new HashMap<>();
        metadata.put("key1", "val1");
        orgToCreate.setMetadata(metadata);

        EnabledConnection enabledConnection = new EnabledConnection();
        enabledConnection.setConnectionId("con-1");
        enabledConnection.setAssignMembershipOnLogin(false);
        List<EnabledConnection> enabledConnections = new ArrayList<>();
        enabledConnections.add(enabledConnection);
        orgToCreate.setEnabledConnections(enabledConnections);

        Request<Organization> request = api.organizations().create(orgToCreate);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.ORGANIZATION, 200);
        Organization response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.POST, "/api/v2/organizations"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body, aMapWithSize(5));
        assertThat(body, hasEntry("name", "test-org"));
        assertThat(body, hasEntry("display_name", "display name"));
        assertThat(body, hasEntry("metadata", metadata));
        assertThat(body, hasEntry(is("enabled_connections"), is(notNullValue())));
        assertThat(body, hasEntry(is("branding"), is(notNullValue())));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnUpdateOrgWithNullOrgId() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.organizations().update(null, new Organization()),
            "'organization ID' cannot be null!");
    }

    @Test
    public void shouldThrowOnUpdateOrgWithNullOrg() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.organizations().update("org_123", null),
            "'organization' cannot be null!");
    }

    @Test
    public void shouldUpdateOrganization() throws Exception {
        Organization orgToUpdate = new Organization("test-org");
        orgToUpdate.setDisplayName("display name");

        Colors colors = new Colors();
        colors.setPageBackground("#FF0000");
        colors.setPrimary("#FF0000");
        Branding branding = new Branding();
        branding.setColors(colors);
        branding.setLogoUrl("https://some-uri.com");
        orgToUpdate.setBranding(branding);

        Map<String, Object> metadata = new HashMap<>();
        metadata.put("key1", "val1");
        orgToUpdate.setMetadata(metadata);

        Request<Organization> request = api.organizations().update("org_abc", orgToUpdate);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.ORGANIZATION, 200);
        Organization response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.PATCH, "/api/v2/organizations/org_abc"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body, aMapWithSize(4));
        assertThat(body, hasEntry("name", "test-org"));
        assertThat(body, hasEntry("display_name", "display name"));
        assertThat(body, hasEntry("metadata", metadata));
        assertThat(body, hasEntry(is("branding"), is(notNullValue())));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnDeleteOrgWithNullId() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.organizations().delete(null),
            "'organization ID' cannot be null!");
    }

    @Test
    public void shouldDeleteOrganization() throws Exception {
        Request<Void> request = api.organizations().delete("org_abc");
        assertThat(request, is(notNullValue()));

        server.emptyResponse(204);
        request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.DELETE, "/api/v2/organizations/org_abc"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
    }

    // Organization members entity

    @Test
    public void shouldThrowOnGetMembersWithNullId() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.organizations().getMembers(null, null),
            "'organization ID' cannot be null!");
    }

    @Test
    public void shouldListOrgMembersWithoutFilter() throws Exception {
        Request<MembersPage> request = api.organizations().getMembers("org_abc", null);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.ORGANIZATION_MEMBERS_LIST, 200);
        MembersPage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/organizations/org_abc/members"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(3));
    }

    @Test
    public void shouldListOrgMembersWithFieldsFilter() throws Exception {
        FieldsFilter fieldsFilter = new FieldsFilter().withFields("name,email,user_id,roles", true);

        Request<MembersPage> request = api.organizations().getMembers("org_abc", null, fieldsFilter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.ORGANIZATION_MEMBERS_LIST, 200);
        MembersPage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/organizations/org_abc/members"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("fields", "name,email,user_id,roles"));
        assertThat(recordedRequest, hasQueryParameter("include_fields", "true"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(3));
    }

    @Test
    public void shouldListOrgMembersWithPage() throws Exception {
        PageFilter filter = new PageFilter().withPage(0, 20);
        Request<MembersPage> request = api.organizations().getMembers("org_abc", filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.ORGANIZATION_MEMBERS_LIST, 200);
        MembersPage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/organizations/org_abc/members"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("page", "0"));
        assertThat(recordedRequest, hasQueryParameter("per_page", "20"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(3));
    }

    @Test
    public void shouldListOrgMembersWithFieldsFilterAndPageFilter() throws Exception {
        PageFilter pageFilter = new PageFilter().withPage(0, 20);
        FieldsFilter fieldsFilter = new FieldsFilter().withFields("name,email,user_id,roles", true);

        Request<MembersPage> request = api.organizations().getMembers("org_abc", pageFilter, fieldsFilter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.ORGANIZATION_MEMBERS_LIST, 200);
        MembersPage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/organizations/org_abc/members"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("fields", "name,email,user_id,roles"));
        assertThat(recordedRequest, hasQueryParameter("include_fields", "true"));
        assertThat(recordedRequest, hasQueryParameter("page", "0"));
        assertThat(recordedRequest, hasQueryParameter("per_page", "20"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(3));
    }

    @Test
    public void shouldListOrgMembersWithTotals() throws Exception {
        PageFilter filter = new PageFilter().withTotals(true);
        Request<MembersPage> request = api.organizations().getMembers("org_abc", filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.ORGANIZATION_MEMBERS_PAGED_LIST, 200);
        MembersPage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/organizations/org_abc/members"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("include_totals", "true"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(3));
    }

    @Test
    public void shouldListOrgMembersWithCheckpointPageResponse() throws Exception {
        PageFilter filter = new PageFilter().withTake(3).withFrom("from-pointer");
        Request<MembersPage> request = api.organizations().getMembers("org_abc", filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(ORGANIZATION_MEMBERS_CHECKPOINT_PAGED_LIST, 200);
        MembersPage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/organizations/org_abc/members"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("take", "3"));
        assertThat(recordedRequest, hasQueryParameter("from", "from-pointer"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(3));
    }

    @Test
    public void shouldThrowOnAddMembersWhenOrgIdNull() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.organizations().addMembers(null, new Members(Collections.singletonList("user1"))),
            "'organization ID' cannot be null!");
    }

    @Test
    public void shouldThrowOnAddMembersWhenMembersIsNull() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.organizations().addMembers("org_abc", null),
            "'members' cannot be null!");
    }

    @Test
    public void shouldAddMembersToOrganization() throws Exception {
        List<String> membersList = Arrays.asList("user1", "user2");
        Members members = new Members(membersList);
        Request<Void> request = api.organizations().addMembers("org_abc", members);
        assertThat(request, is(notNullValue()));

        server.emptyResponse(204);
        request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.POST, "/api/v2/organizations/org_abc/members"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body, aMapWithSize(1));
        assertThat(body, hasEntry("members", membersList));
    }

    @Test
    public void shouldThrowOnDeleteMembersWhenOrgIdNull() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.organizations().deleteMembers(null, new Members(Collections.singletonList("user1"))),
            "'organization ID' cannot be null!");
    }

    @Test
    public void shouldThrowOnDeleteMembersWhenMembersIsNull() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.organizations().deleteMembers("org_abc", null),
            "'members' cannot be null!");
    }

    @Test
    public void shouldDeleteMembersFromOrganization() throws Exception {
        List<String> membersList = Collections.singletonList("user1");
        Members members = new Members(membersList);
        Request<Void> request = api.organizations().deleteMembers("org_abc", members);
        assertThat(request, is(notNullValue()));

        server.emptyResponse(204);
        request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.DELETE, "/api/v2/organizations/org_abc/members"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body, aMapWithSize(1));
        assertThat(body, hasEntry("members", membersList));
    }

    // Organization connections

    @Test
    public void shouldThrowOnGetConnectionsWhenOrgIdNull() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.organizations().deleteMembers(null, null),
            "'organization ID' cannot be null!");
    }

    @Test
    public void shouldListOrganizationConnectionsWithoutFilter() throws Exception {
        Request<EnabledConnectionsPage> request = api.organizations().getConnections("org_abc", null);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.ORGANIZATION_CONNECTIONS_LIST, 200);
        EnabledConnectionsPage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/organizations/org_abc/enabled_connections"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(2));
    }

    @Test
    public void shouldGetOrganizationConnectionsWithPage() throws Exception {
        PageFilter filter = new PageFilter().withPage(2, 30);
        Request<EnabledConnectionsPage> request = api.organizations().getConnections("org_abc", filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.ORGANIZATION_CONNECTIONS_LIST, 200);
        EnabledConnectionsPage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/organizations/org_abc/enabled_connections"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("page", "2"));
        assertThat(recordedRequest, hasQueryParameter("per_page", "30"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(2));
    }

    @Test
    public void shouldGetOrganizationConnectionsWithTotals() throws Exception {
        PageFilter filter = new PageFilter().withTotals(true);
        Request<EnabledConnectionsPage> request = api.organizations().getConnections("org_abc", filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.ORGANIZATION_CONNECTIONS_PAGED_LIST, 200);
        EnabledConnectionsPage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/organizations/org_abc/enabled_connections"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("include_totals", "true"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(2));
    }

    @Test
    public void shouldThrowOnGetConnectionWhenOrgIdNull() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.organizations().getConnection(null, "con_id"),
            "'organization ID' cannot be null!");
    }

    @Test
    public void shouldThrowOnGetConnectionWhenConnectionIdNull() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.organizations().getConnection("org_abc",null),
            "'connection ID' cannot be null!");
    }

    @Test
    public void shouldGetConnection() throws Exception {
        Request<EnabledConnection> request = api.organizations().getConnection("org_123", "con_abc");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(ORGANIZATION_CONNECTION, 200);
        EnabledConnection response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/organizations/org_123/enabled_connections/con_abc"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnAddConnectionWhenOrgIdNull() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.organizations().addConnection(null, new EnabledConnection()),
            "'organization ID' cannot be null!");
    }

    @Test
    public void shouldThrowOnAddConnectionWhenConnectionIsNull() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.organizations().addConnection("org_abc", null),
            "'connection' cannot be null!");
    }

    @Test
    public void shouldAddConnection() throws Exception {
        EnabledConnection connection = new EnabledConnection();
        connection.setAssignMembershipOnLogin(false);
        connection.setConnectionId("con_123");
        connection.setShowAsButton(true);

        Request<EnabledConnection> request = api.organizations().addConnection("org_abc", connection);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(ORGANIZATION_CONNECTION, 201);
        request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.POST, "/api/v2/organizations/org_abc/enabled_connections"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body, aMapWithSize(3));
        assertThat(body, hasEntry("connection_id", "con_123"));
        assertThat(body, hasEntry("assign_membership_on_login", false));
        assertThat(body, hasEntry("show_as_button", true));
    }

    @Test
    public void shouldThrowOnDeleteConnectionWhenOrgIdNull() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.organizations().deleteConnection(null, "con_123"),
            "'organization ID' cannot be null!");
    }

    @Test
    public void shouldThrowOnDeleteConnectionWhenConnectionIsNull() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.organizations().deleteConnection("org_abc", null),
            "'connection ID' cannot be null!");
    }

    @Test
    public void shouldDeleteConnection() throws Exception {
        Request<Void> request = api.organizations().deleteConnection("org_abc", "con_123");
        assertThat(request, is(notNullValue()));

        server.emptyResponse(204);
        request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.DELETE, "/api/v2/organizations/org_abc/enabled_connections/con_123"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
    }

    @Test
    public void shouldThrowOnUpdateConnectionWhenOrgIdNull() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.organizations().updateConnection(null, "con_123", new EnabledConnection()),
            "'organization ID' cannot be null!");
    }

    @Test
    public void shouldThrowOnUpdateConnectionWhenUserIdIsNull() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.organizations().updateConnection("org_abc", null, new EnabledConnection()),
            "'connection ID' cannot be null!");
    }

    @Test
    public void shouldThrowOnUpdateConnectionWhenConnectionIsNull() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.organizations().updateConnection("org_abc", "con_123", null),
            "'connection' cannot be null!");

    }

    @Test
    public void shouldUpdateOrgConnection() throws Exception {
        EnabledConnection connection = new EnabledConnection();
        connection.setAssignMembershipOnLogin(true);
        connection.setShowAsButton(false);

        Request<EnabledConnection> request = api.organizations().updateConnection("org_abc", "con_123", connection);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(ORGANIZATION_CONNECTION, 201);
        request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.PATCH, "/api/v2/organizations/org_abc/enabled_connections/con_123"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body, aMapWithSize(2));
        assertThat(body, hasEntry("assign_membership_on_login", true));
        assertThat(body, hasEntry("show_as_button", false));
    }

    // Organization roles

    @Test
    public void shouldThrowOnGetOrgRolesWhenOrgIdIsNull() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.organizations().getRoles(null, "user_123", null),
            "'organization ID' cannot be null!");
    }

    @Test
    public void shouldThrowOnGetOrgRolesWhenUserIdIsNull() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.organizations().getRoles("org_abc", null, null),
            "'user ID' cannot be null!");
    }

    @Test
    public void shouldGetOrgRolesWithoutPaging() throws Exception {
        Request<RolesPage> request = api.organizations().getRoles("org_abc", "user_123", null);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(ORGANIZATION_MEMBER_ROLES_LIST, 200);
        RolesPage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/organizations/org_abc/members/user_123/roles"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(1));
    }

    @Test
    public void shouldGetOrgRolesWithPaging() throws Exception {
        Request<RolesPage> request = api.organizations().getRoles("org_abc", "user_123", new PageFilter().withPage(0, 20));
        assertThat(request, is(notNullValue()));

        server.jsonResponse(ORGANIZATION_MEMBER_ROLES_LIST, 200);
        RolesPage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/organizations/org_abc/members/user_123/roles"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("page", "0"));
        assertThat(recordedRequest, hasQueryParameter("per_page", "20"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(1));
    }

    @Test
    public void shouldGetOrgRolesWithIncludeTotals() throws Exception {
        Request<RolesPage> request = api.organizations().getRoles("org_abc", "user_123", new PageFilter().withTotals(true));
        assertThat(request, is(notNullValue()));

        server.jsonResponse(ORGANIZATION_MEMBER_ROLES_PAGED_LIST, 200);
        RolesPage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/organizations/org_abc/members/user_123/roles"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("include_totals", "true"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(1));
    }

    @Test
    public void shouldThrowOnAddOrgRolesWhenOrgIdIsNull() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.organizations().addRoles(null, "user_123", new Roles(Collections.singletonList("role_id"))),
            "'organization ID' cannot be null!");
    }

    @Test
    public void shouldThrowOnAddOrgRolesWhenUserIdIsNull() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.organizations().addRoles("org_abc", null, new Roles(Collections.singletonList("role_id"))),
            "'user ID' cannot be null!");
    }

    @Test
    public void shouldThrowOnAddOrgRolesWhenRolesIsNull() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.organizations().addRoles("org_abc", "user_123", null),
            "'roles' cannot be null!");
    }

    @Test
    public void shouldAddOrgRoles() throws Exception {
        List<String> rolesList = Arrays.asList("role_1", "role_2");
        Request<Void> request = api.organizations().addRoles("org_abc", "user_123", new Roles(rolesList));
        assertThat(request, is(notNullValue()));

        server.emptyResponse(204);
        request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.POST, "/api/v2/organizations/org_abc/members/user_123/roles"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body, aMapWithSize(1));
        assertThat(body, hasEntry("roles", rolesList));
    }

    @Test
    public void shouldThrowOnDeleteOrgRolesWhenOrgIdIsNull() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.organizations().deleteRoles(null, "user_123", new Roles(Collections.singletonList("role_id"))),
            "'organization ID' cannot be null!");
    }

    @Test
    public void shouldThrowDeleteOrgRolesWhenUserIdIsNull() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.organizations().deleteRoles("org_abc", null, new Roles(Collections.singletonList("role_id"))),
            "'user ID' cannot be null!");
    }

    @Test
    public void shouldThrowOnDeleteOrgRolesWhenRolesIsNull() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.organizations().deleteRoles("org_abc", "user_123", null),
            "'roles' cannot be null!");

    }

    @Test
    public void shouldDeleteOrgRoles() throws Exception {
        List<String> rolesList = Arrays.asList("role_1", "role_2");
        Request<Void> request = api.organizations().deleteRoles("org_abc", "user_123", new Roles(rolesList));
        assertThat(request, is(notNullValue()));

        server.emptyResponse(204);
        request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.DELETE, "/api/v2/organizations/org_abc/members/user_123/roles"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body, aMapWithSize(1));
        assertThat(body, hasEntry("roles", rolesList));
    }

    // Invitations

    @Test
    public void shouldThrowOnGetInvitationWithNullOrgId() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.organizations().getInvitation(null, "invitation_id", null),
            "'organization ID' cannot be null!");
    }

    @Test
    public void shouldThrowOnGetInvitationWithNullInvitationId() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.organizations().getInvitation("org_id", null, null),
            "'invitation ID' cannot be null!");
    }

    @Test
    public void shouldGetInvitationWithoutFilter() throws Exception {
        Request<Invitation> request = api.organizations().getInvitation("org_123", "invitation_id", null);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(INVITATION, 200);
        Invitation response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/organizations/org_123/invitations/invitation_id"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldGetInvitationWithFilter() throws Exception {
        Request<Invitation> request = api.organizations().getInvitation("org_123", "invitation_id",
            new FieldsFilter().withFields("id,invitation_url,invitee", true));
        assertThat(request, is(notNullValue()));

        server.jsonResponse(INVITATION, 200);
        Invitation response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/organizations/org_123/invitations/invitation_id"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("fields", "id,invitation_url,invitee"));
        assertThat(recordedRequest, hasQueryParameter("include_fields", "true"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnGetInvitationsWithNullOrgId() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.organizations().getInvitations(null, null),
            "'organization ID' cannot be null!");
    }

    @Test
    public void shouldGetInvitationsWithoutFilter() throws Exception {
        Request<InvitationsPage> request = api.organizations().getInvitations("org_123", null);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(INVITATIONS_LIST, 200);
        InvitationsPage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/organizations/org_123/invitations"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldGetInvitationsWithFilter() throws Exception {
        InvitationsFilter filter = new InvitationsFilter()
            .withSort("created_at:1")
            .withFields("invitee,inviter,created_at", true);

        Request<InvitationsPage> request = api.organizations().getInvitations("org_123", filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(INVITATIONS_LIST, 200);
        InvitationsPage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/organizations/org_123/invitations"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("fields", "invitee,inviter,created_at"));
        assertThat(recordedRequest, hasQueryParameter("include_fields", "true"));
        assertThat(recordedRequest, hasQueryParameter("sort", "created_at:1"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldGetInvitationsWithPaging() throws Exception {
        InvitationsFilter filter = new InvitationsFilter().withPage(0, 20);

        Request<InvitationsPage> request = api.organizations().getInvitations("org_123", filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(INVITATIONS_LIST, 200);
        InvitationsPage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/organizations/org_123/invitations"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("per_page", "20"));
        assertThat(recordedRequest, hasQueryParameter("page", "0"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldGetInvitationsWithTotals() throws Exception {
        InvitationsFilter filter = new InvitationsFilter().withTotals(true);

        Request<InvitationsPage> request = api.organizations().getInvitations("org_123", filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(INVITATIONS_PAGED_LIST, 200);
        InvitationsPage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/organizations/org_123/invitations"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("include_totals", "true"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnCreateInvitationWithNullOrgId() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.organizations().createInvitation(null, null),
            "'organization ID' cannot be null!");
    }

    @Test
    public void shouldThrowOnCreateInvitationWithNullInvitation() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.organizations().createInvitation("org_abc", null),
            "'invitation' cannot be null!");
    }

    @Test
    public void shouldCreateInvitation() throws Exception {
        Invitation invitation = new Invitation(new Inviter("name"), new Invitee("name@domain.com"), "con_id");
        invitation.setTtlInSeconds(300);
        invitation.setConnectionId("con_id");
        invitation.setSendInvitationEmail(true);

        Request<Invitation> request = api.organizations().createInvitation("org_123", invitation);

        assertThat(request, is(notNullValue()));

        server.jsonResponse(INVITATION, 201);
        Invitation response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.POST, "/api/v2/organizations/org_123/invitations"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body, aMapWithSize(6));
        assertThat(body, hasEntry("send_invitation_email", true));
        assertThat(body, hasEntry("connection_id", "con_id"));
        assertThat(body, hasEntry("ttl_sec", 300));
        assertThat(body, hasEntry("inviter", Collections.singletonMap("name", "name")));
        assertThat(body, hasEntry("invitee", Collections.singletonMap("email", "name@domain.com")));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnDeleteInvitationWithNullOrgId() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.organizations().deleteInvitation(null, "inv_123"),
            "'organization ID' cannot be null!");
    }

    @Test
    public void shouldThrowOnDeleteInvitationWithNullInvitationId() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.organizations().deleteInvitation("org_abc", null),
            "'invitation ID' cannot be null!");
    }

    @Test
    public void shouldDeleteInvitation() throws Exception {
        Request<Void> request = api.organizations().deleteInvitation("org_abc", "inv_123");
        assertThat(request, is(notNullValue()));

        server.emptyResponse(204);
        request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.DELETE, "/api/v2/organizations/org_abc/invitations/inv_123"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
    }

    @Test
    public void shouldListClientGrantsWithoutFilter() throws Exception {
        Request<OrganizationClientGrantsPage> request = api.organizations().listClientGrants("orgId", null);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(ORGANIZATION_CLIENT_GRANTS, 200);
        OrganizationClientGrantsPage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/organizations/orgId/client-grants"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(2));
    }

    @Test
    public void shouldListClientGrantsWithFilter() throws Exception {
        OrganizationClientGrantsFilter filter = new OrganizationClientGrantsFilter();
        filter
            .withClientId("clientId")
            .withAudience("https://api-identifier/")
            .withPage(1, 2)
            .withTotals(true);

        Request<OrganizationClientGrantsPage> request = api.organizations().listClientGrants("orgId", filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(ORGANIZATION_CLIENT_GRANTS_PAGED_LIST, 200);
        OrganizationClientGrantsPage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/organizations/orgId/client-grants"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("page", "1"));
        assertThat(recordedRequest, hasQueryParameter("per_page", "2"));
        assertThat(recordedRequest, hasQueryParameter("include_totals", "true"));
        assertThat(recordedRequest, hasQueryParameter("audience", "https://api-identifier/"));
        assertThat(recordedRequest, hasQueryParameter("client_id", "clientId"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(1));
    }

    @Test
    public void shouldListClientGrantsWithGrantIds() throws Exception {
        OrganizationClientGrantsFilter filter = new OrganizationClientGrantsFilter();
        filter
            .withClientId("clientId")
            .withAudience("https://api-identifier/")
            .withGrantIds("cgr_123456789012,cgr_abcdefghijkl");

        Request<OrganizationClientGrantsPage> request = api.organizations().listClientGrants("orgId", filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(ORGANIZATION_CLIENT_GRANTS_PAGED_LIST, 200);
        OrganizationClientGrantsPage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/organizations/orgId/client-grants"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("grant_ids", "cgr_123456789012,cgr_abcdefghijkl"));
        assertThat(recordedRequest, hasQueryParameter("audience", "https://api-identifier/"));
        assertThat(recordedRequest, hasQueryParameter("client_id", "clientId"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(1));
    }

    @Test
    public void shouldThrowOnGetClientGrantsWithNullOrgId() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.organizations().listClientGrants(null, null),
            "'organization ID' cannot be null!");
    }

    @Test
    public void shouldCreateClientGrant() throws Exception {
        CreateOrganizationClientGrantRequestBody requestBody = new CreateOrganizationClientGrantRequestBody("grant-id");

        Request<OrganizationClientGrant> request = api.organizations().addClientGrant("org_123", requestBody);

        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_CLIENT_GRANT, 201);
        OrganizationClientGrant response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.POST, "/api/v2/organizations/org_123/client-grants"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body, aMapWithSize(1));
        assertThat(body, hasEntry("grant_id", "grant-id"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnCreateClientGrantWithNullOrgId() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.organizations().addClientGrant(null, new CreateOrganizationClientGrantRequestBody("id")),
            "'organization ID' cannot be null!");
    }

    @Test
    public void shouldThrowOnCreateClientGreatWithNullGrant() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.organizations().addClientGrant("org_1213", null),
            "'client grant' cannot be null!");
    }

    @Test
    public void shouldDeleteClientGrant() throws Exception {
        Request<Void> request = api.organizations().deleteClientGrant("org_abc", "grant_123");
        assertThat(request, is(notNullValue()));

        server.emptyResponse(204);
        request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.DELETE, "/api/v2/organizations/org_abc/client-grants/grant_123"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
    }

    @Test
    public void shouldThrowOnDeleteClientGrantWithNullOrgId() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.organizations().deleteClientGrant(null, "grant-id"),
            "'organization ID' cannot be null!");
    }

    @Test
    public void shouldThrowOnDeleteClientGreatWithNullGrant() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.organizations().deleteClientGrant("org_1213", null),
            "'client grant ID' cannot be null!");
    }

    @Test
    public void testClientGrantsWithOrg() throws Auth0Exception {

        Organization organization = null;
        ResourceServer resourceServer = null;
        Client client = null;
        ClientGrant clientGrant = null;
        OrganizationClientGrant organizationClientGrant = null;

        try {
            //Create organization
            organization = givenAnOrganization();

            //Create resource server
            resourceServer = givenAResourceServer();

            //Create client
            client = createNewClient(organization.getId());

            //Create client grants
            clientGrant = createNewClientGrant(client, resourceServer);

            //Associates the grant with an organization.
            organizationClientGrant = api.organizations().addClientGrant(organization.getId(), new CreateOrganizationClientGrantRequestBody(clientGrant.getId())).execute().getBody();

            ClientFilter clientFilter = new ClientFilter();
            clientFilter.withQuery("client_grant.organization_id:" + organization.getId());

            // List all clients associated with a ClientGrant given an organizationID as query param
            ClientsPage clientsPage = api.clients().list(clientFilter).execute().getBody();

            for (Client c : clientsPage.getItems()) {
                assertThat(organization.getId(), is(c.getDefaultOrganization().getOrganizationId()));
            }

            OrganizationClientGrantsFilter filter = new OrganizationClientGrantsFilter();
            filter.withGrantIds(clientGrant.getId());

            // List all ClientGrants given a list of grant_ids as query param
            OrganizationClientGrantsPage organizationClientGrantsPage = api.organizations().listClientGrants(organization.getId(), filter).execute().getBody();

            assertThat(organizationClientGrantsPage.getItems().size(), is(1));
            assertThat(organizationClientGrantsPage.getItems().get(0).getClientId(), is(clientGrant.getClientId()));

            // Remove the associated ClientGrants
            api.organizations().deleteClientGrant(organization.getId(), organizationClientGrant.getId()).execute();

            // List all ClientGrants which should be an empty list since grant has been removed from the organization.
            OrganizationClientGrantsPage organizationClientGrantsPage1 = api.organizations().listClientGrants(organization.getId(), filter).execute().getBody();
            assertThat(organizationClientGrantsPage1.getItems().size(), is(0));

            // Delete the ClientGrant.
            api.clientGrants().delete(clientGrant.getId()).execute();

            // Retrieve the ClientGrant and ensure error is return since grant has been deleted.
            ClientGrantsPage clientGrantsPage = api.clientGrants().list(new ClientGrantsFilter().withClientId(client.getClientId())).execute().getBody();
            assertThat(clientGrantsPage.getItems().size(), is(0));
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private ClientGrant createNewClientGrant(Client client, ResourceServer resourceServer) throws Auth0Exception {
        ClientGrant clientGrant = new ClientGrant();
        clientGrant.setClientId(client.getClientId());
        clientGrant.setAudience(resourceServer.getIdentifier());
        clientGrant.setScope(Arrays.asList("create:resource", "create:organization_client_grants"));
        clientGrant.setAllowAnyOrganization(true);
        clientGrant.setOrganizationUsage("allow");

        return api.clientGrants().create(client.getClientId(), resourceServer.getIdentifier(), new String[]{"create:resource", "create:organization_client_grants"}).execute().getBody();
    }

    private Client createNewClient(String orgId) throws Auth0Exception {
        Client client = new Client("Test Client (" + System.currentTimeMillis() + ")");
        client.setDescription("This is just a test client.");
        client.setOrganizationUsage("allow");
        client.setDefaultOrganization(new ClientDefaultOrganization(Arrays.asList("client_credentials"), orgId));

        return api.clients().create(client).execute().getBody();
    }

    private Organization givenAnOrganization() throws Auth0Exception {
        Organization organization = new Organization();
        organization.setName("test-organization");
        organization.setDisplayName("test-organization");

        return api.organizations().create(organization).execute().getBody();
    }

    private ResourceServer givenAResourceServer() throws Auth0Exception {
        ResourceServer resourceServer = new ResourceServer("https://www.tanyaisawesome.com");
        resourceServer.setName("tanyaisawesome");

        return api.resourceServers().create(resourceServer).execute().getBody();
    }
}

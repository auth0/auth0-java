package com.auth0.client.mgmt;

import com.auth0.client.mgmt.filter.BaseFilter;
import com.auth0.client.mgmt.filter.FieldsFilter;
import com.auth0.client.mgmt.filter.InvitationsFilter;
import com.auth0.client.mgmt.filter.PageFilter;
import com.auth0.json.mgmt.RolesPage;
import com.auth0.json.mgmt.organizations.*;
import com.auth0.net.CustomRequest;
import com.auth0.net.Request;
import com.auth0.net.VoidRequest;
import com.auth0.net.client.HttpClient;
import com.auth0.net.client.HttpMethod;
import com.auth0.utils.Asserts;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.HttpUrl;

import java.util.Map;

/**
 * Class that provides an implementation of the Organization endpoints of the Management API.
 * <p>
 * This class is not thread-safe.
 *
 * @see ManagementAPI
 */
public class OrganizationsEntity extends BaseManagementEntity {

    private final static String ORGS_PATH = "api/v2/organizations";
    private final static String AUTHORIZATION_HEADER = "Authorization";

    OrganizationsEntity(HttpClient client, HttpUrl baseUrl, String apiToken) {
        super(client, baseUrl, apiToken);
    }

    // Organizations Entity

    /**
     * Get all organizations. A token with {@code read:organizations} scope is required.
     *
     * @param filter an optional pagination filter
     * @return a Request to execute
     *
     * @see <a href="https://auth0.com/docs/api/management/v2#!/Organizations/get_organizations">https://auth0.com/docs/api/management/v2#!/Organizations/get_organizations</a>
     */
    public Request<OrganizationsPage> list(PageFilter filter) {
        HttpUrl.Builder builder = baseUrl
            .newBuilder()
            .addPathSegments(ORGS_PATH);

        applyFilter(filter, builder);

        String url = builder.build().toString();
        CustomRequest<OrganizationsPage> request = new CustomRequest<>(client, url, HttpMethod.GET, new TypeReference<OrganizationsPage>() {
        });

        request.addHeader(AUTHORIZATION_HEADER, "Bearer " + apiToken);
        return request;
    }

    /**
     * Get an organization. A token with {@code read:organizations} scope is required.
     *
     * @param orgId the ID of the organization to retrieve
     * @return a Request to execute
     *
     * @see <a href="https://auth0.com/docs/api/management/v2#!/Organizations/get_organizations_by_id">https://auth0.com/docs/api/management/v2#!/Organizations/get_organizations_by_id</a>
     */
    public Request<Organization> get(String orgId) {
        Asserts.assertNotNull(orgId, "organization ID");

        String url = baseUrl
            .newBuilder()
            .addPathSegments(ORGS_PATH)
            .addPathSegment(orgId)
            .build()
            .toString();

        CustomRequest<Organization> request = new CustomRequest<>(client, url, HttpMethod.GET, new TypeReference<Organization>() {
        });

        request.addHeader(AUTHORIZATION_HEADER, "Bearer " + apiToken);
        return request;
    }

    /**
     * Get an organization. A token with {@code read:organizations} scope is required.
     *
     * @param orgName the name of the organization to retrieve
     * @return a Request to execute
     *
     * @see <a href="https://auth0.com/docs/api/management/v2#!/Organizations/get_name_by_name">https://auth0.com/docs/api/management/v2#!/Organizations/get_name_by_name</a>
     */
    public Request<Organization> getByName(String orgName) {
        Asserts.assertNotNull(orgName, "organization name");

        String url = baseUrl
            .newBuilder()
            .addPathSegments(ORGS_PATH)
            .addPathSegment("name")
            .addPathSegment(orgName)
            .build()
            .toString();

        CustomRequest<Organization> request = new CustomRequest<>(client, url, HttpMethod.GET, new TypeReference<Organization>() {
        });

        request.addHeader(AUTHORIZATION_HEADER, "Bearer " + apiToken);
        return request;
    }

    /**
     * Create an organization. A token with {@code create:organizations} scope is required.
     *
     * @param organization the organization to create
     * @return a Request to execute
     *
     * @see <a href="https://auth0.com/docs/api/management/v2#!/Organizations/post_organizations">https://auth0.com/docs/api/management/v2#!/Organizations/post_organizations</a>
     */
    public Request<Organization> create(Organization organization) {
        Asserts.assertNotNull(organization, "organization");

        String url = baseUrl
            .newBuilder()
            .addPathSegments(ORGS_PATH)
            .build()
            .toString();

        CustomRequest<Organization> request = new CustomRequest<>(client, url, HttpMethod.POST, new TypeReference<Organization>() {
        });

        request.addHeader(AUTHORIZATION_HEADER, "Bearer " + apiToken);
        request.setBody(organization);
        return request;
    }

    /**
     * Update an organization. A token with {@code update:organizations} scope is required.
     *
     * @param orgId the ID of the organization to update
     * @param organization the updated organization
     * @return a Request to execute
     *
     * @see <a href="https://auth0.com/docs/api/management/v2#!/Organizations/patch_organizations_by_id">https://auth0.com/docs/api/management/v2#!/Organizations/patch_organizations_by_id</a>
     */
    public Request<Organization> update(String orgId, Organization organization) {
        Asserts.assertNotNull(orgId, "organization ID");
        Asserts.assertNotNull(organization, "organization");

        String url = baseUrl
            .newBuilder()
            .addPathSegments(ORGS_PATH)
            .addPathSegment(orgId)
            .build()
            .toString();

        CustomRequest<Organization> request = new CustomRequest<>(client, url, HttpMethod.PATCH, new TypeReference<Organization>() {
        });

        request.addHeader(AUTHORIZATION_HEADER, "Bearer " + apiToken);
        request.setBody(organization);
        return request;
    }

    /**
     * Delete an organization. A token with {@code delete:organizations} scope is required.
     *
     * @param orgId the ID of the organization to delete
     * @return a Request to execute
     *
     * @see <a href="https://auth0.com/docs/api/management/v2#!/Organizations/delete_organizations_by_id">https://auth0.com/docs/api/management/v2#!/Organizations/delete_organizations_by_id</a>
     */
    public Request<Void> delete(String orgId) {
        Asserts.assertNotNull(orgId, "organization ID");

        String url = baseUrl
            .newBuilder()
            .addPathSegments(ORGS_PATH)
            .addPathSegment(orgId)
            .build()
            .toString();

        VoidRequest voidRequest = new VoidRequest(client, url, HttpMethod.DELETE);
        voidRequest.addHeader(AUTHORIZATION_HEADER, "Bearer " + apiToken);
        return voidRequest;
    }

    // Organization members

    /**
     * Get the members of an organization. A token with {@code read:organization_members} scope is required.
     *
     * @param orgId the ID of the organization
     * @param filter an optional pagination filter
     * @return a Request to execute
     *
     * @see <a href="https://auth0.com/docs/api/management/v2#!/Organizations/get_members">https://auth0.com/docs/api/management/v2#!/Organizations/get_members</a>
     */
    public Request<MembersPage> getMembers(String orgId, PageFilter filter) {
        Asserts.assertNotNull(orgId, "organization ID");

        HttpUrl.Builder builder = baseUrl
            .newBuilder()
            .addPathSegments(ORGS_PATH)
            .addPathSegment(orgId)
            .addPathSegment("members");

        applyFilter(filter, builder);

        String url = builder.build().toString();
        CustomRequest<MembersPage> request = new CustomRequest<>(client, url, HttpMethod.GET, new TypeReference<MembersPage>() {
        });
        request.addHeader(AUTHORIZATION_HEADER, "Bearer " + apiToken);
        return request;
    }

    /**
     * Add members to an organization. A token with {@code create:organization_members} scope is required.
     *
     * @param orgId the ID of the organization
     * @param members The members to add
     * @return a Request to execute
     *
     * @see <a href="https://auth0.com/docs/api/management/v2#!/Organizations/post_members">https://auth0.com/docs/api/management/v2#!/Organizations/post_members</a>
     */
    public Request<Void> addMembers(String orgId, Members members) {
        Asserts.assertNotNull(orgId, "organization ID");
        Asserts.assertNotNull(members, "members");

        String url = baseUrl
            .newBuilder()
            .addPathSegments(ORGS_PATH)
            .addPathSegment(orgId)
            .addPathSegment("members")
            .build()
            .toString();

        VoidRequest request = new VoidRequest(client, url, HttpMethod.POST);
        request.addHeader(AUTHORIZATION_HEADER, "Bearer " + apiToken);
        request.setBody(members);
        return request;
    }

    /**
     * Delete members from an organization. A token with {@code delete:organization_members} scope is required.
     *
     * @param orgId the ID of the organization
     * @param members The members to remove
     * @return a Request to execute
     *
     * @see <a href="https://auth0.com/docs/api/management/v2#!/Organizations/delete_members">https://auth0.com/docs/api/management/v2#!/Organizations/delete_members</a>
     */
    public Request<Void> deleteMembers(String orgId, Members members) {
        Asserts.assertNotNull(orgId, "organization ID");
        Asserts.assertNotNull(members, "members");

        String url = baseUrl
            .newBuilder()
            .addPathSegments(ORGS_PATH)
            .addPathSegment(orgId)
            .addPathSegment("members")
            .build()
            .toString();

        VoidRequest request = new VoidRequest(client, url, HttpMethod.DELETE);
        request.addHeader(AUTHORIZATION_HEADER, "Bearer " + apiToken);
        request.setBody(members);
        return request;
    }

    // Organization connections

    /**
     * Get the connections of an organization. A token with {@code read:organization_connections} scope is required.
     *
     * @param orgId the ID of the organization
     * @param filter an optional pagination filter
     * @return a Request to execute
     *
     * @see <a href="https://auth0.com/docs/api/management/v2#!/Organizations/get_enabled_connections">https://auth0.com/docs/api/management/v2#!/Organizations/get_enabled_connections</a>
     */
    public Request<EnabledConnectionsPage> getConnections(String orgId, PageFilter filter) {
        Asserts.assertNotNull(orgId, "organization ID");

        HttpUrl.Builder builder = baseUrl
            .newBuilder()
            .addPathSegments(ORGS_PATH)
            .addPathSegment(orgId)
            .addPathSegment("enabled_connections");

        applyFilter(filter, builder);

        String url = builder.build().toString();
        CustomRequest<EnabledConnectionsPage> request = new CustomRequest<>(client, url, HttpMethod.GET, new TypeReference<EnabledConnectionsPage>() {
        });
        request.addHeader(AUTHORIZATION_HEADER, "Bearer " + apiToken);
        return request;
    }

    /**
     * Get an organization's connection.
     *
     * @param orgId the ID of the organization
     * @param connectionId the ID of the connection
     * @return a Request to execute
     *
     * @see <a href="https://auth0.com/docs/api/management/v2#!/Organizations/get_enabled_connections_by_connectionId">https://auth0.com/docs/api/management/v2#!/Organizations/get_enabled_connections_by_connectionId</a>
     */
    public Request<EnabledConnection> getConnection(String orgId, String connectionId) {
        Asserts.assertNotNull(orgId, "organization ID");
        Asserts.assertNotNull(connectionId, "connection ID");

        String url = baseUrl
            .newBuilder()
            .addPathSegments(ORGS_PATH)
            .addPathSegment(orgId)
            .addPathSegment("enabled_connections")
            .addPathSegment(connectionId)
            .build()
            .toString();

        CustomRequest<EnabledConnection> request = new CustomRequest<>(client, url, HttpMethod.GET, new TypeReference<EnabledConnection>() {
        });
        request.addHeader(AUTHORIZATION_HEADER, "Bearer " + apiToken);
        return request;
    }

    /**
     * Add a connection to an organization. A token with {@code create:organization_connections} scope is required.
     *
     * @param orgId the ID of the organization
     * @param connection The connection to add
     * @return a Request to execute
     *
     * @see <a href="https://auth0.com/docs/api/management/v2#!/Organizations/post_enabled_connections">https://auth0.com/docs/api/management/v2#!/Organizations/post_enabled_connections</a>
     */
    public Request<EnabledConnection> addConnection(String  orgId, EnabledConnection connection) {
        Asserts.assertNotNull(orgId, "organization ID");
        Asserts.assertNotNull(connection, "connection");

        String url = baseUrl
            .newBuilder()
            .addPathSegments(ORGS_PATH)
            .addPathSegment(orgId)
            .addPathSegment("enabled_connections")
            .build()
            .toString();

        CustomRequest<EnabledConnection> request = new CustomRequest<>(client, url, HttpMethod.POST, new TypeReference<EnabledConnection>() {
        });
        request.addHeader(AUTHORIZATION_HEADER, "Bearer " + apiToken);
        request.setBody(connection);
        return request;
    }

    /**
     * Delete a connection from an organization. A token with {@code delete:organization_connections} scope is required.
     *
     * @param orgId the ID of the organization
     * @param connectionId the ID of the connection to delete
     * @return a Request to execute
     *
     * @see <a href="https://auth0.com/docs/api/management/v2#!/Organizations/delete_enabled_connections_by_connectionId">https://auth0.com/docs/api/management/v2#!/Organizations/delete_enabled_connections_by_connectionId</a>
     */
    public Request<Void> deleteConnection(String  orgId, String connectionId) {
        Asserts.assertNotNull(orgId, "organization ID");
        Asserts.assertNotNull(connectionId, "connection ID");

        String url = baseUrl
            .newBuilder()
            .addPathSegments(ORGS_PATH)
            .addPathSegment(orgId)
            .addPathSegment("enabled_connections")
            .addPathSegment(connectionId)
            .build()
            .toString();

        VoidRequest voidRequest = new VoidRequest(client, url, HttpMethod.DELETE);
        voidRequest.addHeader(AUTHORIZATION_HEADER, "Bearer " + apiToken);
        return voidRequest;
    }

    /**
     * Update a connection of an organization. A token with {@code update:organization_connections} scope is required.
     *
     * @param orgId the ID of the organization
     * @param connectionId the ID of the connection to update
     * @param connection the connection to update
     * @return a Request to execute
     *
     * @see <a href="https://auth0.com/docs/api/management/v2#!/Organizations/patch_enabled_connections_by_connectionId">https://auth0.com/docs/api/management/v2#!/Organizations/patch_enabled_connections_by_connectionId</a>
     */
    public Request<EnabledConnection> updateConnection(String orgId, String connectionId, EnabledConnection connection) {
        Asserts.assertNotNull(orgId, "organization ID");
        Asserts.assertNotNull(connectionId, "connection ID");
        Asserts.assertNotNull(connection, "connection");

        String url = baseUrl
            .newBuilder()
            .addPathSegments(ORGS_PATH)
            .addPathSegment(orgId)
            .addPathSegment("enabled_connections")
            .addPathSegment(connectionId)
            .build()
            .toString();

        CustomRequest<EnabledConnection> request = new CustomRequest<>(client, url, HttpMethod.PATCH, new TypeReference<EnabledConnection>() {
        });
        request.addHeader(AUTHORIZATION_HEADER, "Bearer " + apiToken);
        request.setBody(connection);
        return request;
    }

    // Org roles

    /**
     * Get the roles for a member of an organization. A token with {@code read:organization_members} scope is required.
     *
     * @param orgId the ID of the organization
     * @param userId the ID of the user
     * @param filter an optional pagination filter
     * @return a Request to execute
     *
     * @see <a href="https://auth0.com/docs/api/management/v2#!/Organizations/get_organization_member_roles">https://auth0.com/docs/api/management/v2#!/Organizations/get_organization_member_roles</a>
     */
    public Request<RolesPage> getRoles(String orgId, String userId, PageFilter filter) {
        Asserts.assertNotNull(orgId, "organization ID");
        Asserts.assertNotNull(userId, "user ID");

        HttpUrl.Builder builder = baseUrl
            .newBuilder()
            .addPathSegments(ORGS_PATH)
            .addPathSegment(orgId)
            .addPathSegment("members")
            .addPathSegment(userId)
            .addPathSegment("roles");

        applyFilter(filter, builder);

        String url = builder.build().toString();
        CustomRequest<RolesPage> request = new CustomRequest<>(client, url, HttpMethod.GET, new TypeReference<RolesPage>() {
        });
        request.addHeader(AUTHORIZATION_HEADER, "Bearer " + apiToken);
        return request;
    }

    /**
     * Add roles for a member of an organization. A token with {@code create:organization_member_roles} scope is required.
     *
     * @param orgId the ID of the organization
     * @param userId the ID of the user
     * @param roles the roles to add
     * @return a Request to execute
     *
     * @see <a href="https://auth0.com/docs/api/management/v2#!/Organizations/post_organization_member_roles">https://auth0.com/docs/api/management/v2#!/Organizations/post_organization_member_roles</a>
     */
    public Request<Void> addRoles(String orgId, String userId, Roles roles) {
        Asserts.assertNotNull(orgId, "organization ID");
        Asserts.assertNotNull(userId, "user ID");
        Asserts.assertNotNull(roles, "roles");

        String url = baseUrl
            .newBuilder()
            .addPathSegments(ORGS_PATH)
            .addPathSegment(orgId)
            .addPathSegment("members")
            .addPathSegment(userId)
            .addPathSegment("roles")
            .build()
            .toString();

        VoidRequest request = new VoidRequest(client, url, HttpMethod.POST);
        request.addHeader(AUTHORIZATION_HEADER, "Bearer " + apiToken);
        request.setBody(roles);
        return request;
    }

    /**
     * Delete roles from a member of an organization. A token with {@code delete:organization_member_roles} scope is required.
     *
     * @param orgId the ID of the organization
     * @param userId the ID of the user
     * @param roles the roles to delete
     * @return a Request to execute
     *
     * @see <a href="https://auth0.com/docs/api/management/v2#!/Organizations/delete_organization_member_roles">https://auth0.com/docs/api/management/v2#!/Organizations/delete_organization_member_roles</a>
     */
    public Request<Void> deleteRoles(String orgId, String userId, Roles roles) {
        Asserts.assertNotNull(orgId, "organization ID");
        Asserts.assertNotNull(userId, "user ID");
        Asserts.assertNotNull(roles, "roles");

        String url = baseUrl
            .newBuilder()
            .addPathSegments(ORGS_PATH)
            .addPathSegment(orgId)
            .addPathSegment("members")
            .addPathSegment(userId)
            .addPathSegment("roles")
            .build()
            .toString();

        VoidRequest request = new VoidRequest(client, url, HttpMethod.DELETE);
        request.addHeader(AUTHORIZATION_HEADER, "Bearer " + apiToken);
        request.setBody(roles);
        return request;
    }

    // Organization invitations

    /**
     * Create an invitation. A token with {@code create:organization_invitations} scope is required.
     *
     * @param orgId the ID of the organization
     * @param invitation the invitation to create
     * @return a Request to execute
     *
     * @see <a href="https://auth0.com/docs/api/management/v2#!/Organizations/post_invitations">https://auth0.com/docs/api/management/v2#!/Organizations/post_invitations</a>
     */
    public Request<Invitation> createInvitation(String orgId, Invitation invitation) {
        Asserts.assertNotNull(orgId, "organization ID");
        Asserts.assertNotNull(invitation, "invitation");

        String url = baseUrl
            .newBuilder()
            .addPathSegments(ORGS_PATH)
            .addPathSegment(orgId)
            .addPathSegment("invitations")
            .build()
            .toString();

        CustomRequest<Invitation> request = new CustomRequest<>(client, url, HttpMethod.POST, new TypeReference<Invitation>() {
        });
        request.addHeader(AUTHORIZATION_HEADER, "Bearer " + apiToken);
        request.setBody(invitation);
        return request;

    }

    /**
     * Get an invitation. A token with {@code read:organization_invitations} scope is required.
     *
     * @param orgId the ID of the organization
     * @param invitationId the ID of the invitation
     * @param filter an optional result filter
     * @return a Request to execute
     *
     * @see <a href="https://auth0.com/docs/api/management/v2#!/Organizations/get_invitations_by_invitation_id">https://auth0.com/docs/api/management/v2#!/Organizations/get_invitations_by_invitation_id</a>
     */
    public Request<Invitation> getInvitation(String orgId, String invitationId, FieldsFilter filter) {
        Asserts.assertNotNull(orgId, "organization ID");
        Asserts.assertNotNull(invitationId, "invitation ID");

        HttpUrl.Builder builder = baseUrl
            .newBuilder()
            .addPathSegments(ORGS_PATH)
            .addPathSegment(orgId)
            .addPathSegment("invitations")
            .addPathSegment(invitationId);

        applyFilter(filter, builder);

        String url = builder.build().toString();
        CustomRequest<Invitation> request = new CustomRequest<>(client, url, HttpMethod.GET, new TypeReference<Invitation>() {
        });
        request.addHeader(AUTHORIZATION_HEADER, "Bearer " + apiToken);
        return request;
    }

    /**
     * Gets all invitations for an organization. A token with {@code read:organization_invitations} scope is required.
     *
     * @param orgId the ID of the organization
     * @param filter an optional result filter
     * @return a Request to execute
     *
     * @see <a href="https://auth0.com/docs/api/management/v2#!/Organizations/get_invitations">https://auth0.com/docs/api/management/v2#!/Organizations/get_invitations</a>
     */
    public Request<InvitationsPage> getInvitations(String orgId, InvitationsFilter filter) {
        Asserts.assertNotNull(orgId, "organization ID");

        HttpUrl.Builder builder = baseUrl
            .newBuilder()
            .addPathSegments(ORGS_PATH)
            .addPathSegment(orgId)
            .addPathSegment("invitations");

        applyFilter(filter, builder);

        String url = builder.build().toString();
        CustomRequest<InvitationsPage> request = new CustomRequest<>(client, url, HttpMethod.GET, new TypeReference<InvitationsPage>() {
        });
        request.addHeader(AUTHORIZATION_HEADER, "Bearer " + apiToken);
        return request;
    }

    /**
     * Delete an invitation. A token with {@code delete:organization_invitations`} scope is required.
     *
     * @param orgId the ID of the organization
     * @param invitationId the ID of the invitation to delete
     * @return a Request to execute
     *
     * @see <a href="https://auth0.com/docs/api/management/v2#!/Organizations/delete_invitations_by_invitation_id">https://auth0.com/docs/api/management/v2#!/Organizations/delete_invitations_by_invitation_id</a>
     */
    public Request<Void> deleteInvitation(String orgId, String invitationId) {
        Asserts.assertNotNull(orgId, "organization ID");
        Asserts.assertNotNull(invitationId, "invitation ID");

        String url = baseUrl
            .newBuilder()
            .addPathSegments(ORGS_PATH)
            .addPathSegment(orgId)
            .addPathSegment("invitations")
            .addPathSegment(invitationId)
            .build()
            .toString();

        VoidRequest request = new VoidRequest(client, url, HttpMethod.DELETE);
        request.addHeader(AUTHORIZATION_HEADER, "Bearer " + apiToken);
        return request;
    }

    private void applyFilter(BaseFilter filter, HttpUrl.Builder builder) {
        if (filter != null) {
            for (Map.Entry<String, Object> e : filter.getAsMap().entrySet()) {
                builder.addQueryParameter(e.getKey(), String.valueOf(e.getValue()));
            }
        }
    }
}

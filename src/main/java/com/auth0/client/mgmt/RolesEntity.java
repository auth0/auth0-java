package com.auth0.client.mgmt;

import com.auth0.client.mgmt.filter.PageFilter;
import com.auth0.client.mgmt.filter.RolesFilter;
import com.auth0.json.mgmt.permissions.Permission;
import com.auth0.json.mgmt.permissions.PermissionsPage;
import com.auth0.json.mgmt.roles.Role;
import com.auth0.json.mgmt.roles.RolesPage;
import com.auth0.json.mgmt.users.UsersPage;
import com.auth0.net.BaseRequest;
import com.auth0.net.Request;
import com.auth0.net.VoidRequest;
import com.auth0.net.client.Auth0HttpClient;
import com.auth0.net.client.HttpMethod;
import com.auth0.utils.Asserts;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.HttpUrl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class that provides an implementation of the Roles methods of the Management API as defined in https://auth0.com/docs/api/management/v2#!/Roles/
 * <p>
 * This class is not thread-safe.
 *
 * @see ManagementAPI
 */
public class RolesEntity extends BaseManagementEntity {

  RolesEntity(Auth0HttpClient client, HttpUrl baseUrl,
              TokenProvider tokenProvider) {
    super(client, baseUrl, tokenProvider);
  }

  /**
   * Request all Roles created by this tenant that can be assigned to a given user or user group.
   * A token with read:roles is needed
   * See https://auth0.com/docs/api/management/v2#!/Roles/get_roles
   *
   * @param filter optional filtering and pagination criteria
   * @return a Request to execute
   */
  public Request<RolesPage> list(RolesFilter filter) {
    HttpUrl.Builder builder = baseUrl
        .newBuilder()
        .addEncodedPathSegments("api/v2/roles");
    if (filter != null) {
      for (Map.Entry<String, Object> e : filter.getAsMap().entrySet()) {
        builder.addQueryParameter(e.getKey(), String.valueOf(e.getValue()));
      }
    }
    String url = builder.build().toString();
      return new BaseRequest<>(this.client, tokenProvider, url, HttpMethod.GET, new TypeReference<RolesPage>() {});
  }

  /**
   * Get a single role created by this tenant that can be assigned to a given user or user group.
   * A token with scope read:roles is needed.
   * See https://auth0.com/docs/api/management/v2#!/Roles/get_roles_by_id
   *
   * @param roleId the id of the user to retrieve.
   * @return a Request to execute.
   */
  public Request<Role> get(String roleId) {
    Asserts.assertNotNull(roleId, "role id");

    HttpUrl.Builder builder = baseUrl
        .newBuilder()
        .addEncodedPathSegments("api/v2/roles")
        .addEncodedPathSegment(roleId);

    String url = builder.build().toString();
      return new BaseRequest<>(this.client, tokenProvider, url, HttpMethod.GET, new TypeReference<Role>() {});
  }


  /**
   * Create a Role.
   * A token with scope create:roles is needed.
   * See https://auth0.com/docs/api/management/v2#!/Roles/post_roles
   *
   * @param role the role data to set
   * @return a Request to execute.
   */
  public Request<Role> create(Role role) {
    Asserts.assertNotNull(role, "role");

    String url = baseUrl
        .newBuilder()
        .addEncodedPathSegments("api/v2/roles")
        .build()
        .toString();
    BaseRequest<Role> request = new BaseRequest<>(this.client, tokenProvider, url, HttpMethod.POST, new TypeReference<Role>() {});
    request.setBody(role);
    return request;
  }

  /**
   * Delete an existing Role.
   * A token with scope delete:roles is needed.
   * See https://auth0.com/docs/api/management/v2#!/Roles/delete_roles_by_id
   *
   * @param roleId The id of the role to delete.
   * @return a Request to execute.
   */
  public Request<Void> delete(String roleId) {
    Asserts.assertNotNull(roleId, "role id");

    final String url = baseUrl
        .newBuilder()
        .addEncodedPathSegments("api/v2/roles")
        .addEncodedPathSegment(roleId)
        .build()
        .toString();
      return new VoidRequest(this.client, tokenProvider, url, HttpMethod.DELETE);
  }

  /**
   * Update an existing Role.
   * A token with scope update:roles is needed.
   * See https://auth0.com/docs/api/management/v2#!/Roles/patch_roles_by_id
   *
   * @param roleId the role id
   * @param role the role data to set. It can't include id.
   * @return a Request to execute.
   */
  public Request<Role> update(String roleId, Role role) {
    Asserts.assertNotNull(roleId, "role id");
    Asserts.assertNotNull(role, "role");

    String url = baseUrl
        .newBuilder()
        .addEncodedPathSegments("api/v2/roles")
        .addEncodedPathSegment(roleId)
        .build()
        .toString();
    BaseRequest<Role> request = new BaseRequest<>(this.client, tokenProvider, url, HttpMethod.PATCH, new TypeReference<Role>() {});
    request.setBody(role);
    return request;
  }

  /**
   * Lists the users that have been associated with a given role.
   * A token with scope read:users and read:roles is needed.
   *
   * See https://auth0.com/docs/api/management/v2#!/Roles/get_role_user
   *
   * @param roleId the role id
   * @param filter an optional pagination filter
   * @return a Request to execute
   */
  public Request<UsersPage> listUsers(String roleId, PageFilter filter) {
    Asserts.assertNotNull(roleId, "role id");
    HttpUrl.Builder builder = baseUrl
        .newBuilder()
        .addEncodedPathSegments("api/v2/roles")
        .addEncodedPathSegment(roleId)
        .addEncodedPathSegment("users");
    if (filter != null) {
      for (Map.Entry<String, Object> e : filter.getAsMap().entrySet()) {
        builder.addQueryParameter(e.getKey(), String.valueOf(e.getValue()));
      }
    }
    String url = builder.build().toString();
      return new BaseRequest<>(this.client, tokenProvider, url, HttpMethod.GET, new TypeReference<UsersPage>() {});
  }

  /**
   * Assign users to a role.
   * A token with update:roles is needed.
   * See https://auth0.com/docs/api/management/v2#!/Roles/post_role_users
   *
   * @param roleId the role id
   * @param userIds a list of user ids to assign to the role
   * @return a Request to execute.
   */
  public Request<Void> assignUsers(String roleId, List<String> userIds) {
    Asserts.assertNotNull(roleId, "role id");
    Asserts.assertNotEmpty(userIds, "user ids");

    Map<String, List<String>> body = new HashMap<>();
    body.put("users", userIds);

    String url = baseUrl
        .newBuilder()
        .addEncodedPathSegments("api/v2/roles")
        .addEncodedPathSegment(roleId)
        .addEncodedPathSegment("users")
        .build()
        .toString();
    VoidRequest request = new VoidRequest(this.client, tokenProvider, url, HttpMethod.POST);
    request.setBody(body);
    return request;
  }

  /**
   * Get the permissions associated to the role.
   * A token with read:roles is needed.
   * See https://auth0.com/docs/api/management/v2#!/Roles/get_permissions
   *
   * @param roleId the role id
   * @param filter an optional pagination filter
   * @return a Request to execute
   */
  public Request<PermissionsPage> listPermissions(String roleId, PageFilter filter) {
    Asserts.assertNotNull(roleId, "role id");

    HttpUrl.Builder builder = baseUrl
        .newBuilder()
        .addEncodedPathSegments("api/v2/roles")
        .addEncodedPathSegment(roleId)
        .addEncodedPathSegment("permissions");
    if (filter != null) {
      for (Map.Entry<String, Object> e : filter.getAsMap().entrySet()) {
        builder.addQueryParameter(e.getKey(), String.valueOf(e.getValue()));
      }
    }
    String url = builder.build().toString();
      return new BaseRequest<>(this.client, tokenProvider, url, HttpMethod.GET, new TypeReference<PermissionsPage>() {});
  }

  /**
   * Un-associate permissions from a role.
   * A token with update:roles is needed.
   * See https://auth0.com/docs/api/management/v2#!/Roles/delete_role_permission_assignment
   *
   * @param roleId the role id
   * @param permissions a list of permission objects to un-associate from the role
   * @return a Request to execute
   */
  public Request<Void> removePermissions(String roleId, List<Permission> permissions) {
    Asserts.assertNotNull(roleId, "role id");
    Asserts.assertNotEmpty(permissions, "permissions");

    Map<String, List<Permission>> body = new HashMap<>();
    body.put("permissions", permissions);

    final String url = baseUrl
        .newBuilder()
        .addEncodedPathSegments("api/v2/roles")
        .addEncodedPathSegment(roleId)
        .addEncodedPathSegment("permissions")
        .build()
        .toString();
    VoidRequest request = new VoidRequest(this.client, tokenProvider, url, HttpMethod.DELETE);
    request.setBody(body);
    return request;
  }

  /**
   * Associate permissions with a role. Only the `permission_name` and
   * `resource_server_identifier` Permission attributes should be specified.
   * A token with update:roles is needed.
   * See https://auth0.com/docs/api/management/v2#!/Roles/post_role_permission_assignment
   *
   * @param roleId the role id
   * @param permissions a list of permission objects to associate to the role
   * @return a Request to execute
   */
  public Request<Void> addPermissions(String roleId, List<Permission> permissions) {
    Asserts.assertNotNull(roleId, "role id");
    Asserts.assertNotEmpty(permissions, "permissions");

    Map<String, List<Permission>> body = new HashMap<>();
    body.put("permissions", permissions);

    final String url = baseUrl
        .newBuilder()
        .addEncodedPathSegments("api/v2/roles")
        .addEncodedPathSegment(roleId)
        .addEncodedPathSegment("permissions")
        .build()
        .toString();
    VoidRequest request = new VoidRequest(this.client, tokenProvider, url, HttpMethod.POST);
    request.setBody(body);
    return request;
  }
}

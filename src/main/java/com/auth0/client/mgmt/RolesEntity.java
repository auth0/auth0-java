package com.auth0.client.mgmt;

import com.auth0.client.mgmt.filter.PageFilter;
import com.auth0.client.mgmt.filter.RolesFilter;
import com.auth0.json.mgmt.Permission;
import com.auth0.json.mgmt.PermissionsPage;
import com.auth0.json.mgmt.Role;
import com.auth0.json.mgmt.RolesPage;
import com.auth0.json.mgmt.users.UsersPage;
import com.auth0.net.CustomRequest;
import com.auth0.net.Request;
import com.auth0.net.VoidRequest;
import com.auth0.utils.Asserts;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RolesEntity extends BaseManagementEntity {

  RolesEntity(OkHttpClient client, HttpUrl baseUrl,
      String apiToken) {
    super(client, baseUrl, apiToken);
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
    CustomRequest<RolesPage> request = new CustomRequest<>(this.client, url, "GET", new TypeReference<RolesPage>() {});
    request.addHeader("Authorization", "Bearer " + apiToken);
    return request;
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
        .addEncodedPathSegments(roleId);

    String url = builder.build().toString();
    CustomRequest<Role> request = new CustomRequest<>(this.client, url, "GET", new TypeReference<Role>() {});
    request.addHeader("Authorization", "Bearer " + apiToken);
    return request;
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
    CustomRequest<Role> request = new CustomRequest<>(this.client, url, "POST", new TypeReference<Role>() {});
    request.addHeader("Authorization", "Bearer " + apiToken);
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
  public Request delete(String roleId) {
    Asserts.assertNotNull(roleId, "role id");

    final String url = baseUrl
        .newBuilder()
        .addEncodedPathSegments("api/v2/roles")
        .addEncodedPathSegments(roleId)
        .build()
        .toString();
    VoidRequest request = new VoidRequest(this.client, url, "DELETE");
    request.addHeader("Authorization", "Bearer " + apiToken);
    return request;
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
        .addEncodedPathSegments(roleId)
        .build()
        .toString();
    CustomRequest<Role> request = new CustomRequest<>(this.client, url, "PATCH", new TypeReference<Role>() {});
    request.addHeader("Authorization", "Bearer " + apiToken);
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
        .addEncodedPathSegments(roleId)
        .addEncodedPathSegments("users");
    if (filter != null) {
      for (Map.Entry<String, Object> e : filter.getAsMap().entrySet()) {
        builder.addQueryParameter(e.getKey(), String.valueOf(e.getValue()));
      }
    }
    String url = builder.build().toString();
    CustomRequest<UsersPage> request = new CustomRequest<>(this.client, url, "GET", new TypeReference<UsersPage>() {});
    request.addHeader("Authorization", "Bearer " + apiToken);
    return request;
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
  public Request assignUsers(String roleId, List<String> userIds) {
    Asserts.assertNotNull(roleId, "role id");
    Asserts.assertNotEmpty(userIds, "user ids");

    Map<String, List<String>> body = new HashMap<>();
    body.put("users", userIds);

    String url = baseUrl
        .newBuilder()
        .addEncodedPathSegments("api/v2/roles")
        .addEncodedPathSegments(roleId)
        .addEncodedPathSegments("users")
        .build()
        .toString();
    VoidRequest request = new VoidRequest(this.client, url, "POST");
    request.addHeader("Authorization", "Bearer " + apiToken);
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
        .addEncodedPathSegments(roleId)
        .addEncodedPathSegments("permissions");
    if (filter != null) {
      for (Map.Entry<String, Object> e : filter.getAsMap().entrySet()) {
        builder.addQueryParameter(e.getKey(), String.valueOf(e.getValue()));
      }
    }
    String url = builder.build().toString();
    CustomRequest<PermissionsPage> request = new CustomRequest<>(this.client, url, "GET", new TypeReference<PermissionsPage>() {});
    request.addHeader("Authorization", "Bearer " + apiToken);
    return request;
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
  public Request removePermissions(String roleId, List<Permission> permissions) {
    Asserts.assertNotNull(roleId, "role id");
    Asserts.assertNotEmpty(permissions, "permissions");

    Map<String, List<Permission>> body = new HashMap<>();
    body.put("permissions", permissions);

    final String url = baseUrl
        .newBuilder()
        .addEncodedPathSegments("api/v2/roles")
        .addEncodedPathSegments(roleId)
        .addEncodedPathSegments("permissions")
        .build()
        .toString();
    VoidRequest request = new VoidRequest(this.client, url, "DELETE");
    request.setBody(body);
    request.addHeader("Authorization", "Bearer " + apiToken);
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
  public Request addPermissions(String roleId, List<Permission> permissions) {
    Asserts.assertNotNull(roleId, "role id");
    Asserts.assertNotEmpty(permissions, "permissions");

    Map<String, List<Permission>> body = new HashMap<>();
    body.put("permissions", permissions);

    final String url = baseUrl
        .newBuilder()
        .addEncodedPathSegments("api/v2/roles")
        .addEncodedPathSegments(roleId)
        .addEncodedPathSegments("permissions")
        .build()
        .toString();
    VoidRequest request = new VoidRequest(this.client, url, "POST");
    request.setBody(body);
    request.addHeader("Authorization", "Bearer " + apiToken);
    return request;
  }
}

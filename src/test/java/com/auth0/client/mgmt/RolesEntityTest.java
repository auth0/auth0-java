package com.auth0.client.mgmt;

import static com.auth0.client.MockServer.MGMT_EMPTY_LIST;
import static com.auth0.client.MockServer.MGMT_ROLE;
import static com.auth0.client.MockServer.MGMT_ROLES_LIST;
import static com.auth0.client.MockServer.MGMT_ROLES_PAGED_LIST;
import static com.auth0.client.MockServer.MGMT_ROLE_PERMISSIONS_LIST;
import static com.auth0.client.MockServer.MGMT_ROLE_PERMISSIONS_PAGED_LIST;
import static com.auth0.client.MockServer.MGMT_ROLE_USERS_LIST;
import static com.auth0.client.MockServer.MGMT_ROLE_USERS_PAGED_LIST;
import static com.auth0.client.MockServer.bodyFromRequest;
import static com.auth0.client.RecordedRequestMatcher.hasHeader;
import static com.auth0.client.RecordedRequestMatcher.hasMethodAndPath;
import static com.auth0.client.RecordedRequestMatcher.hasQueryParameter;
import static org.hamcrest.Matchers.emptyCollectionOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import com.auth0.client.mgmt.filter.PageFilter;
import com.auth0.client.mgmt.filter.RolesFilter;
import com.auth0.json.mgmt.Permission;
import com.auth0.json.mgmt.PermissionsPage;
import com.auth0.json.mgmt.Role;
import com.auth0.json.mgmt.RolesPage;
import com.auth0.json.mgmt.users.User;
import com.auth0.json.mgmt.users.UsersPage;
import com.auth0.net.Request;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.Test;

public class RolesEntityTest extends BaseMgmtEntityTest {

  @Test
  public void shouldListRolesWithoutFilter() throws Exception {
    Request<RolesPage> request = api.roles().list(null);
    assertThat(request, is(notNullValue()));

    server.jsonResponse(MGMT_ROLES_LIST, 200);
    RolesPage response = request.execute();
    RecordedRequest recordedRequest = server.takeRequest();

    assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/roles"));
    assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
    assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

    assertThat(response, is(notNullValue()));
    assertThat(response.getItems(), hasSize(2));
  }

  @Test
  public void shouldListRolesWithPage() throws Exception {
    RolesFilter filter = new RolesFilter().withPage(23, 5);
    Request<RolesPage> request = api.roles().list(filter);
    assertThat(request, is(notNullValue()));

    server.jsonResponse(MGMT_ROLES_LIST, 200);
    RolesPage response = request.execute();
    RecordedRequest recordedRequest = server.takeRequest();

    assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/roles"));
    assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
    assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
    assertThat(recordedRequest, hasQueryParameter("page", "23"));
    assertThat(recordedRequest, hasQueryParameter("per_page", "5"));

    assertThat(response, is(notNullValue()));
    assertThat(response.getItems(), hasSize(2));
  }

  @Test
  public void shouldListRolesWithTotals() throws Exception {
    RolesFilter filter = new RolesFilter().withTotals(true);
    Request<RolesPage> request = api.roles().list(filter);
    assertThat(request, is(notNullValue()));

    server.jsonResponse(MGMT_ROLES_PAGED_LIST, 200);
    RolesPage response = request.execute();
    RecordedRequest recordedRequest = server.takeRequest();

    assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/roles"));
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
  public void shouldGetRole() throws Exception {
    Request<Role> request = api.roles().get("1");
    assertThat(request, is(notNullValue()));

    server.jsonResponse(MGMT_ROLE, 200);
    Role response = request.execute();
    RecordedRequest recordedRequest = server.takeRequest();

    assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/roles/1"));
    assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
    assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

    assertThat(response, is(notNullValue()));
  }

  @Test
  public void shouldThrowOnGetRoleWithNullRoleId() throws Exception {
    exception.expect(IllegalArgumentException.class);
    exception.expectMessage("'role id' cannot be null!");
    api.roles().get(null);
  }

  @Test
  public void shouldThrowOnCreateRoleWithNullData() throws Exception {
    exception.expect(IllegalArgumentException.class);
    exception.expectMessage("'user' cannot be null!");
    api.users().create(null);
  }

  @Test
  public void shouldCreateRole() throws Exception {
    Role newRole = new Role();
    newRole.setName("roleId");
    Request<Role> request = api.roles().create(newRole);
    assertThat(request, is(notNullValue()));

    server.jsonResponse(MGMT_ROLE, 200);
    Role response = request.execute();
    RecordedRequest recordedRequest = server.takeRequest();

    assertThat(recordedRequest, hasMethodAndPath("POST", "/api/v2/roles"));
    assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
    assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

    Map<String, Object> body = bodyFromRequest(recordedRequest);
    assertThat(body.size(), is(1));
    assertThat(body, hasEntry("name", (Object) "roleId"));

    assertThat(response, is(notNullValue()));
  }

  @Test
  public void shouldThrowOnUpdateRoleWithNullId() throws Exception {
    exception.expect(IllegalArgumentException.class);
    exception.expectMessage("'role id' cannot be null!");
    api.roles().update(null, new Role());
  }

  @Test
  public void shouldThrowOnUpdateRoleWithNullData() throws Exception {
    exception.expect(IllegalArgumentException.class);
    exception.expectMessage("'role' cannot be null!");
    api.roles().update("1", null);
  }

  @Test
  public void shouldUpdateRole() throws Exception {
    Role updatedRole = new Role();
    updatedRole.setName("roleId");
    Request<Role> request = api.roles().update("1", updatedRole);
    assertThat(request, is(notNullValue()));

    server.jsonResponse(MGMT_ROLE, 200);
    Role response = request.execute();
    RecordedRequest recordedRequest = server.takeRequest();

    assertThat(recordedRequest, hasMethodAndPath("PATCH", "/api/v2/roles/1"));
    assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
    assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

    Map<String, Object> body = bodyFromRequest(recordedRequest);
    assertThat(body.size(), is(1));
    assertThat(body, hasEntry("name", (Object) "roleId"));

    assertThat(response, is(notNullValue()));
  }

  @Test
  public void shouldThrowOnDeleteRoleWithNullId() throws Exception {
    exception.expect(IllegalArgumentException.class);
    exception.expectMessage("'role id' cannot be null!");
    api.roles().delete(null);
  }

  @Test
  public void shouldDeleteRole() throws Exception {
    Request request = api.roles().delete("1");
    assertThat(request, is(notNullValue()));

    server.emptyResponse(200);
    request.execute();
    RecordedRequest recordedRequest = server.takeRequest();

    assertThat(recordedRequest, hasMethodAndPath("DELETE", "/api/v2/roles/1"));
    assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
    assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
  }

  @Test
  public void shouldThrowOnListUsersWithNullId() throws Exception {
    exception.expect(IllegalArgumentException.class);
    exception.expectMessage("'role id' cannot be null!");
    api.roles().listUsers(null, null);
  }

  @Test
  public void shouldListUsersWithoutFilter() throws Exception {
    Request<UsersPage> request = api.roles().listUsers("1", null);
    assertThat(request, is(notNullValue()));

    server.jsonResponse(MGMT_ROLE_USERS_PAGED_LIST, 200);
    UsersPage response = request.execute();
    RecordedRequest recordedRequest = server.takeRequest();

    assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/roles/1/users"));
    assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
    assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

    assertThat(response, is(notNullValue()));
    assertThat(response.getItems(), hasSize(2));
  }

  @Test
  public void shouldListUsersWithPage() throws Exception {
    PageFilter filter = new PageFilter().withPage(23, 5);
    Request<UsersPage> request = api.roles().listUsers("1", filter);
    assertThat(request, is(notNullValue()));

    server.jsonResponse(MGMT_ROLE_USERS_PAGED_LIST, 200);
    UsersPage response = request.execute();
    RecordedRequest recordedRequest = server.takeRequest();

    assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/roles/1/users"));
    assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
    assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
    assertThat(recordedRequest, hasQueryParameter("page", "23"));
    assertThat(recordedRequest, hasQueryParameter("per_page", "5"));

    assertThat(response, is(notNullValue()));
    assertThat(response.getItems(), hasSize(2));
  }

  @Test
  public void shouldListUsersWithTotals() throws Exception {
    PageFilter filter = new PageFilter().withTotals(true);
    Request<UsersPage> request = api.roles().listUsers("1", filter);
    assertThat(request, is(notNullValue()));

    server.jsonResponse(MGMT_ROLE_USERS_PAGED_LIST, 200);
    UsersPage response = request.execute();
    RecordedRequest recordedRequest = server.takeRequest();

    assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/roles/1/users"));
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
  public void shouldThrowOnAssignUsersWithNullId() throws Exception {
    exception.expect(IllegalArgumentException.class);
    exception.expectMessage("'role id' cannot be null!");
    api.roles().assignUsers(null, Collections.emptyList());
  }

  @Test
  public void shouldThrowOnAssignUsersWithNullList() throws Exception {
    exception.expect(IllegalArgumentException.class);
    exception.expectMessage("'user ids' cannot be null!");
    api.roles().assignUsers("1", null);
  }

  @Test
  public void shouldThrowOnAssignUsersWithEmptyList() throws Exception {
    exception.expect(IllegalArgumentException.class);
    exception.expectMessage("'user ids' cannot be empty!");
    api.roles().assignUsers("1", Collections.emptyList());
  }

  @Test
  public void shouldAssignUsers() throws Exception {
    List<String> userIds = new ArrayList<>();
    userIds.add("userId");
    Request request = api.roles().assignUsers("1", userIds);
    assertThat(request, is(notNullValue()));

    server.emptyResponse(200);
    Object response = request.execute();
    RecordedRequest recordedRequest = server.takeRequest();

    assertThat(recordedRequest, hasMethodAndPath("POST", "/api/v2/roles/1/users"));
    assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
    assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

    Map<String, Object> body = bodyFromRequest(recordedRequest);
    assertThat(body.size(), is(1));
    assertThat(body, hasKey("users"));

    assertThat(response, is(nullValue()));
  }

  @Test
  public void shouldListPermissionsWithoutFilter() throws Exception {
    Request<PermissionsPage> request = api.roles().listPermissions("1", null);
    assertThat(request, is(notNullValue()));

    server.jsonResponse(MGMT_ROLE_PERMISSIONS_PAGED_LIST, 200);
    PermissionsPage response = request.execute();
    RecordedRequest recordedRequest = server.takeRequest();

    assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/roles/1/permissions"));
    assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
    assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

    assertThat(response, is(notNullValue()));
    assertThat(response.getItems(), hasSize(2));
  }

  @Test
  public void shouldListPermissionsWithPage() throws Exception {
    PageFilter filter = new PageFilter().withPage(23, 5);
    Request<PermissionsPage> request = api.roles().listPermissions("1", filter);
    assertThat(request, is(notNullValue()));

    server.jsonResponse(MGMT_ROLE_PERMISSIONS_PAGED_LIST, 200);
    PermissionsPage response = request.execute();
    RecordedRequest recordedRequest = server.takeRequest();

    assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/roles/1/permissions"));
    assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
    assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
    assertThat(recordedRequest, hasQueryParameter("page", "23"));
    assertThat(recordedRequest, hasQueryParameter("per_page", "5"));

    assertThat(response, is(notNullValue()));
    assertThat(response.getItems(), hasSize(2));
  }

  @Test
  public void shouldListPermissionsWithTotal() throws Exception {
    PageFilter filter = new PageFilter().withTotals(true);
    Request<PermissionsPage> request = api.roles().listPermissions("1", filter);
    assertThat(request, is(notNullValue()));

    server.jsonResponse(MGMT_ROLE_PERMISSIONS_PAGED_LIST, 200);
    PermissionsPage response = request.execute();
    RecordedRequest recordedRequest = server.takeRequest();

    assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/roles/1/permissions"));
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
  public void shouldThrowOnAddPermissionsWithNullId() throws Exception {
    exception.expect(IllegalArgumentException.class);
    exception.expectMessage("'role id' cannot be null!");
    api.roles().addPermissions(null, Collections.emptyList());
  }

  @Test
  public void shouldThrowOnAddPermissionsWithNullList() throws Exception {
    exception.expect(IllegalArgumentException.class);
    exception.expectMessage("'permissions' cannot be null!");
    api.roles().addPermissions("1", null);
  }

  @Test
  public void shouldThrowOnAddPermissionsWithEmptyList() throws Exception {
    exception.expect(IllegalArgumentException.class);
    exception.expectMessage("'permissions' cannot be empty!");
    api.roles().addPermissions("1", Collections.emptyList());
  }

  @Test
  public void shouldAddPermissions() throws Exception {
    List<Permission> permissions = new ArrayList<>();
    Permission permission = new Permission();
    permission.setName("permissionName");
    permissions.add(permission);
    Request request = api.roles().addPermissions("1", permissions);
    assertThat(request, is(notNullValue()));

    server.emptyResponse(200);
    Object response = request.execute();
    RecordedRequest recordedRequest = server.takeRequest();

    assertThat(recordedRequest, hasMethodAndPath("POST", "/api/v2/roles/1/permissions"));
    assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
    assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

    Map<String, Object> body = bodyFromRequest(recordedRequest);
    assertThat(body.size(), is(1));
    assertThat(body, hasKey("permissions"));

    assertThat(response, is(nullValue()));
  }

  @Test
  public void shouldThrowOnRemovePermissionsWithNullId() throws Exception {
    exception.expect(IllegalArgumentException.class);
    exception.expectMessage("'role id' cannot be null!");
    api.roles().removePermissions(null, Collections.emptyList());
  }

  @Test
  public void shouldThrowOnRemovePermissionsWithNullList() throws Exception {
    exception.expect(IllegalArgumentException.class);
    exception.expectMessage("'permissions' cannot be null!");
    api.roles().removePermissions("1", null);
  }

  @Test
  public void shouldThrowOnRemovePermissionsWithEmptyList() throws Exception {
    exception.expect(IllegalArgumentException.class);
    exception.expectMessage("'permissions' cannot be empty!");
    api.roles().removePermissions("1", Collections.emptyList());
  }

  @Test
  public void shouldRemovePermissions() throws Exception {
    List<Permission> permissions = new ArrayList<>();
    Permission permission = new Permission();
    permission.setName("permissionName");
    permissions.add(permission);
    Request request = api.roles().removePermissions("1", permissions);
    assertThat(request, is(notNullValue()));

    server.emptyResponse(200);
    Object response = request.execute();
    RecordedRequest recordedRequest = server.takeRequest();

    assertThat(recordedRequest, hasMethodAndPath("DELETE", "/api/v2/roles/1/permissions"));
    assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
    assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

    Map<String, Object> body = bodyFromRequest(recordedRequest);
    assertThat(body.size(), is(1));
    assertThat(body, hasKey("permissions"));

    assertThat(response, is(nullValue()));
  }
}

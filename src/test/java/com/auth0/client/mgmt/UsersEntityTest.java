package com.auth0.client.mgmt;

import com.auth0.client.mgmt.filter.FieldsFilter;
import com.auth0.client.mgmt.filter.LogEventFilter;
import com.auth0.client.mgmt.filter.PageFilter;
import com.auth0.client.mgmt.filter.UserFilter;
import com.auth0.json.mgmt.guardian.Enrollment;
import com.auth0.json.mgmt.logevents.LogEvent;
import com.auth0.json.mgmt.logevents.LogEventsPage;
import com.auth0.json.mgmt.organizations.OrganizationsPage;
import com.auth0.json.mgmt.permissions.Permission;
import com.auth0.json.mgmt.permissions.PermissionsPage;
import com.auth0.json.mgmt.roles.RolesPage;
import com.auth0.json.mgmt.users.Identity;
import com.auth0.json.mgmt.users.RecoveryCode;
import com.auth0.json.mgmt.users.User;
import com.auth0.json.mgmt.users.UsersPage;
import com.auth0.json.mgmt.users.authenticationmethods.AuthenticationMethod;
import com.auth0.json.mgmt.users.authenticationmethods.AuthenticationMethodsPage;
import com.auth0.net.Request;
import com.auth0.net.client.HttpMethod;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.auth0.AssertsUtil.verifyThrows;
import static com.auth0.client.MockServer.*;
import static com.auth0.client.RecordedRequestMatcher.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class UsersEntityTest extends BaseMgmtEntityTest {

    @Test
    public void shouldListUsersByEmail() throws Exception {
        Request<List<User>> request = api.users().listByEmail("johndoe@auth0.com", null);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_USERS_LIST, 200);
        List<User> response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/users-by-email"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("email", "johndoe@auth0.com"));

        assertThat(response, is(notNullValue()));
        assertThat(response, hasSize(2));
    }

    @Test
    public void shouldListUsersByEmailWithFields() throws Exception {
        FieldsFilter filter = new FieldsFilter().withFields("some,random,fields", true);
        Request<List<User>> request = api.users().listByEmail("johndoe@auth0.com", filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_USERS_LIST, 200);
        List<User> response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/users-by-email"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("email", "johndoe@auth0.com"));
        assertThat(recordedRequest, hasQueryParameter("fields", "some,random,fields"));
        assertThat(recordedRequest, hasQueryParameter("include_fields", "true"));

        assertThat(response, is(notNullValue()));
        assertThat(response, hasSize(2));
    }

    @Test
    public void shouldReturnEmptyUsersByEmail() throws Exception {
        Request<List<User>> request = api.users().listByEmail("missing@auth0.com", null);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_EMPTY_LIST, 200);
        List<User> response = request.execute().getBody();

        assertThat(response, is(notNullValue()));
        assertThat(response, is(emptyCollectionOf(User.class)));
    }

    @Test
    public void shouldListUsers() throws Exception {
        Request<UsersPage> request = api.users().list(null);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_USERS_LIST, 200);
        UsersPage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/users"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(2));
    }

    @Test
    public void shouldListUsersWithPage() throws Exception {
        UserFilter filter = new UserFilter().withPage(23, 5);
        Request<UsersPage> request = api.users().list(filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_USERS_LIST, 200);
        UsersPage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/users"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("page", "23"));
        assertThat(recordedRequest, hasQueryParameter("per_page", "5"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(2));
    }

    @Test
    public void shouldListUsersWithTotals() throws Exception {
        UserFilter filter = new UserFilter().withTotals(true);
        Request<UsersPage> request = api.users().list(filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_USERS_PAGED_LIST, 200);
        UsersPage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/users"));
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
    public void shouldListUsersWithSort() throws Exception {
        UserFilter filter = new UserFilter().withSort("date:1");
        Request<UsersPage> request = api.users().list(filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_USERS_LIST, 200);
        UsersPage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/users"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("sort", "date:1"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(2));
    }

    @Test
    public void shouldListUsersWithQuery() throws Exception {
        UserFilter filter = new UserFilter().withQuery("email:\\*@gmail.com");
        Request<UsersPage> request = api.users().list(filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_USERS_LIST, 200);
        UsersPage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/users"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, not(hasQueryParameter("search_engine")));
        assertThat(recordedRequest, hasQueryParameter("q", "email:\\*@gmail.com"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(2));
    }

    @Test
    public void shouldListUsersWithFields() throws Exception {
        UserFilter filter = new UserFilter().withFields("some,random,fields", true);
        Request<UsersPage> request = api.users().list(filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_USERS_LIST, 200);
        UsersPage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/users"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("fields", "some,random,fields"));
        assertThat(recordedRequest, hasQueryParameter("include_fields", "true"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(2));
    }

    @Test
    public void shouldReturnEmptyUsers() throws Exception {
        Request<UsersPage> request = api.users().list(null);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_EMPTY_LIST, 200);
        UsersPage response = request.execute().getBody();

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), is(emptyCollectionOf(User.class)));
    }

    @Test
    public void shouldThrowOnGetUserWithNullId() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.users().get(null, null),
            "'user id' cannot be null!");
    }

    @Test
    public void shouldGetUser() throws Exception {
        Request<User> request = api.users().get("1", null);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_USER, 200);
        User response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/users/1"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldGetUserWithFields() throws Exception {
        UserFilter filter = new UserFilter().withFields("some,random,fields", true);
        Request<User> request = api.users().get("1", filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_USER, 200);
        User response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/users/1"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("fields", "some,random,fields"));
        assertThat(recordedRequest, hasQueryParameter("include_fields", "true"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnCreateUserWithNullData() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.users().create(null),
            "'user' cannot be null!");
    }

    @Test
    public void shouldCreateUser() throws Exception {
        Request<User> request = api.users().create(new User("auth0"));
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_USER, 200);
        User response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.POST, "/api/v2/users"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(1));
        assertThat(body, hasEntry("connection", "auth0"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnDeleteUserWithNullId() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.users().delete(null),
            "'user id' cannot be null!");
    }

    @Test
    public void shouldDeleteUser() throws Exception {
        Request<Void> request = api.users().delete("1");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_USER, 200);
        request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.DELETE, "/api/v2/users/1"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
    }

    @Test
    public void shouldThrowOnUpdateUserWithNullId() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.users().update(null, new User("auth0")),
            "'user id' cannot be null!");
    }

    @Test
    public void shouldThrowOnUpdateUserWithNullData() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.users().update("1", null),
            "'user' cannot be null!");
    }

    @Test
    public void shouldUpdateUser() throws Exception {
        Request<User> request = api.users().update("1", new User("auth0"));
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_USER, 200);
        User response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.PATCH, "/api/v2/users/1"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(1));
        assertThat(body, hasEntry("connection", "auth0"));

        assertThat(response, is(notNullValue()));
    }


    @Test
    public void shouldThrowOnGetUserGuardianEnrollmentsWithNullId() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.users().getEnrollments(null),
            "'user id' cannot be null!");
    }

    @Test
    public void shouldGetUserGuardianEnrollments() throws Exception {
        Request<List<Enrollment>> request = api.users().getEnrollments("1");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_GUARDIAN_ENROLLMENTS_LIST, 200);
        List<Enrollment> response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/users/1/enrollments"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldReturnEmptyUserGuardianEnrollments() throws Exception {
        Request<List<Enrollment>> request = api.users().getEnrollments("1");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_EMPTY_LIST, 200);
        List<Enrollment> response = request.execute().getBody();

        assertThat(response, is(notNullValue()));
        assertThat(response, is(emptyCollectionOf(Enrollment.class)));
    }

    @Test
    public void shouldThrowOnGetUserLogEventsWithNullId() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.users().getLogEvents(null, null),
            "'user id' cannot be null!");
    }

    @Test
    public void shouldGetUserLogEvents() throws Exception {
        Request<LogEventsPage> request = api.users().getLogEvents("1", null);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_LOG_EVENTS_LIST, 200);
        LogEventsPage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/users/1/logs"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldGetUserLogEventsWithQuery() throws Exception {
        LogEventFilter filter = new LogEventFilter().withQuery("date:[2022-03-13 TO *]");
        Request<LogEventsPage> request = api.users().getLogEvents("1", filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_LOG_EVENTS_LIST, 200);
        LogEventsPage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/users/1/logs"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("q", "date:[2022-03-13 TO *]"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(2));
    }

    @Test
    public void shouldGetUserLogEventsWithPage() throws Exception {
        LogEventFilter filter = new LogEventFilter().withPage(23, 5);
        Request<LogEventsPage> request = api.users().getLogEvents("1", filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_LOG_EVENTS_LIST, 200);
        LogEventsPage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/users/1/logs"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("page", "23"));
        assertThat(recordedRequest, hasQueryParameter("per_page", "5"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(2));
    }

    @Test
    public void shouldGetUserLogEventsWithTotals() throws Exception {
        LogEventFilter filter = new LogEventFilter().withTotals(true);
        Request<LogEventsPage> request = api.users().getLogEvents("1", filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_LOG_EVENTS_PAGED_LIST, 200);
        LogEventsPage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/users/1/logs"));
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
    public void shouldGetUserLogEventsWithSort() throws Exception {
        LogEventFilter filter = new LogEventFilter().withSort("date:1");
        Request<LogEventsPage> request = api.users().getLogEvents("1", filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_LOG_EVENTS_LIST, 200);
        LogEventsPage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/users/1/logs"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("sort", "date:1"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(2));
    }

    @Test
    public void shouldGetUserLogEventsWithFields() throws Exception {
        LogEventFilter filter = new LogEventFilter().withFields("some,random,fields", true);
        Request<LogEventsPage> request = api.users().getLogEvents("1", filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_LOG_EVENTS_LIST, 200);
        LogEventsPage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/users/1/logs"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("fields", "some,random,fields"));
        assertThat(recordedRequest, hasQueryParameter("include_fields", "true"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(2));
    }

    @Test
    public void shouldReturnEmptyUserLogEvents() throws Exception {
        Request<LogEventsPage> request = api.users().getLogEvents("1", null);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_EMPTY_LIST, 200);
        LogEventsPage response = request.execute().getBody();

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), is(emptyCollectionOf(LogEvent.class)));
    }

    @Test
    public void shouldThrowOnDeleteUserAuthenticatorsWithNullId() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.users().deleteAllAuthenticators(null),
            "'user id' cannot be null!");
    }

    @Test
    public void shouldDeleteUserAuthenticators() throws Exception {
        Request<Void> request = api.users().deleteAllAuthenticators("auth0|23");
        assertThat(request, is(notNullValue()));

        server.emptyResponse(204);
        request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.DELETE, "/api/v2/users/auth0%7C23/authenticators"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
    }

    @Test
    public void shouldThrowOnDeleteUserMultifactorProviderWithNullId() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.users().deleteMultifactorProvider(null, "duo"),
            "'user id' cannot be null!");
    }

    @Test
    public void shouldThrowOnDeleteUserMultifactorProviderWithNullProvider() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.users().deleteMultifactorProvider("1", null),
            "'provider' cannot be null!");
    }

    @Test
    public void shouldDeleteUserMultifactorProvider() throws Exception {
        Request<Void> request = api.users().deleteMultifactorProvider("1", "duo");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_EMAIL_PROVIDER, 200);
        request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.DELETE, "/api/v2/users/1/multifactor/duo"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
    }

    @Test
    public void shouldThrowOnRotateUserRecoveryCodeWithNullId() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.users().rotateRecoveryCode(null),
            "'user id' cannot be null!");
    }

    @Test
    public void shouldRotateUserRecoveryCode() throws Exception {
        Request<RecoveryCode> request = api.users().rotateRecoveryCode("1");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_RECOVERY_CODE, 200);
        RecoveryCode response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.POST, "/api/v2/users/1/recovery-code-regeneration"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnLinkUserIdentityWithNullPrimaryId() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.users().linkIdentity(null, "2", "auth0", null),
            "'primary user id' cannot be null!");
    }

    @Test
    public void shouldThrowOnLinkUserIdentityWithNullSecondaryId() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.users().linkIdentity("1", null, "auth0", null),
            "'secondary user id' cannot be null!");
    }

    @Test
    public void shouldThrowOnLinkUserIdentityWithNullSecondaryToken() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.users().linkIdentity("1", null),
            "'secondary id token' cannot be null!");
    }

    @Test
    public void shouldThrowOnLinkUserIdentityWithNullUserId() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.users().linkIdentity(null, "2"),
            "'primary user id' cannot be null!");
    }

    @Test
    public void shouldThrowOnLinkUserIdentityWithNullProvider() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.users().linkIdentity("1", "2", null, null),
            "'provider' cannot be null!");
    }

    @Test
    public void shouldLinkUserIdentity() throws Exception {
        Request<List<Identity>> request = api.users().linkIdentity("1", "2", "auth0", null);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_IDENTITIES_LIST, 200);
        List<Identity> response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.POST, "/api/v2/users/1/identities"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(2));
        assertThat(body, hasEntry("provider", "auth0"));
        assertThat(body, hasEntry("user_id", "2"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldLinkUserIdentityBySecondaryIdToken() throws Exception {
        Request<List<Identity>> request = api.users().linkIdentity("1", "2");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_IDENTITIES_LIST, 200);
        List<Identity> response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.POST, "/api/v2/users/1/identities"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(1));
        assertThat(body, hasEntry("link_with", "2"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldLinkUserIdentityWithConnectionId() throws Exception {
        Request<List<Identity>> request = api.users().linkIdentity("1", "2", "auth0", "123456790");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_IDENTITIES_LIST, 200);
        List<Identity> response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.POST, "/api/v2/users/1/identities"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(3));
        assertThat(body, hasEntry("provider", "auth0"));
        assertThat(body, hasEntry("user_id", "2"));
        assertThat(body, hasEntry("connection_id", "123456790"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnUnlinkUserIdentityWithNullPrimaryId() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.users().unlinkIdentity(null, "2", "auth0"),
            "'primary user id' cannot be null!");
    }

    @Test
    public void shouldThrowOnUnlinkUserIdentityWithNullSecondaryId() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.users().unlinkIdentity("1", null, "auth0"),
            "'secondary user id' cannot be null!");
    }

    @Test
    public void shouldThrowOnUnlinkUserIdentityWithNullProvider() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.users().unlinkIdentity("1", "2", null),
            "'provider' cannot be null!");
    }

    @Test
    public void shouldUnlinkUserIdentity() throws Exception {
        Request<List<Identity>> request = api.users().unlinkIdentity("1", "2", "auth0");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_IDENTITIES_LIST, 200);
        List<Identity> response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.DELETE, "/api/v2/users/1/identities/auth0/2"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnListRolesWithNullId() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.users().listRoles(null, null),
            "'user id' cannot be null!");
    }

    @Test
    public void shouldListRolesWithoutFilter() throws Exception {
        Request<RolesPage> request = api.users().listRoles("1", null);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_USER_ROLES_PAGED_LIST, 200);
        RolesPage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/users/1/roles"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(2));
    }

    @Test
    public void shouldListRolesWithPage() throws Exception {
        PageFilter filter = new PageFilter().withPage(23, 5);
        Request<RolesPage> request = api.users().listRoles("1", filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_USER_ROLES_PAGED_LIST, 200);
        RolesPage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/users/1/roles"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("page", "23"));
        assertThat(recordedRequest, hasQueryParameter("per_page", "5"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(2));
    }

    @Test
    public void shouldListRolesWithTotals() throws Exception {
        PageFilter filter = new PageFilter().withTotals(true);
        Request<RolesPage> request = api.users().listRoles("1", filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_USER_ROLES_PAGED_LIST, 200);
        RolesPage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/users/1/roles"));
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
    public void shouldThrowOnAddRolesWithNullId() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.users().addRoles(null, Collections.emptyList()),
            "'user id' cannot be null!");
    }

    @Test
    public void shouldThrowOnAddRolesWithNullList() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.users().addRoles("1", null),
            "'role ids' cannot be null!");
    }

    @Test
    public void shouldThrowOnAddRolesWithEmptyList() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.users().addRoles("1", Collections.emptyList()),
            "'role ids' cannot be empty!");
    }

    @Test
    public void shouldAddRoles() throws Exception {
        Request<Void> request = api.users().addRoles("1",  Collections.singletonList("roleId"));
        assertThat(request, is(notNullValue()));

        server.emptyResponse(200);
        Object response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.POST, "/api/v2/users/1/roles"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(1));
        assertThat(body, hasKey("roles"));

        assertThat(response, is(nullValue()));
    }

    @Test
    public void shouldThrowOnRemoveRolesWithNullId() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.users().removeRoles(null, Collections.emptyList()),
            "'user id' cannot be null!");
    }

    @Test
    public void shouldThrowOnRemoveRolesWithNullList() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.users().removeRoles("1", null),
            "'role ids' cannot be null!");
    }

    @Test
    public void shouldThrowOnRemoveRolesWithEmptyList() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.users().removeRoles("1", Collections.emptyList()),
            "'role ids' cannot be empty!");
    }

    @Test
    public void shouldRemoveRoles() throws Exception {
        Request<Void> request = api.users().removeRoles("1", Collections.singletonList("roleId"));
        assertThat(request, is(notNullValue()));

        server.emptyResponse( 200);
        Object response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.DELETE, "/api/v2/users/1/roles"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(1));
        assertThat(body, hasKey("roles"));

        assertThat(response, is(nullValue()));
    }

    @Test
    public void shouldListPermissionsWithoutFilter() throws Exception {
        Request<PermissionsPage> request = api.users().listPermissions("1", null);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_USER_PERMISSIONS_PAGED_LIST, 200);
        PermissionsPage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/users/1/permissions"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(2));
    }

    @Test
    public void shouldListPermissionsWithPage() throws Exception {
        PageFilter filter = new PageFilter().withPage(23, 5);
        Request<PermissionsPage> request = api.users().listPermissions("1", filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_USER_PERMISSIONS_PAGED_LIST, 200);
        PermissionsPage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/users/1/permissions"));
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
        Request<PermissionsPage> request = api.users().listPermissions("1", filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_USER_PERMISSIONS_PAGED_LIST, 200);
        PermissionsPage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/users/1/permissions"));
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
    public void shouldThrowOnAddPermissionsWithNullId() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.users().addPermissions(null, Collections.emptyList()),
            "'user id' cannot be null!");
    }

    @Test
    public void shouldThrowOnAddPermissionsWithNullList() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.users().addPermissions("1", null),
            "'permissions' cannot be null!");
    }

    @Test
    public void shouldThrowOnAddPermissionsWithEmptyList() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.users().addPermissions("1", Collections.emptyList()),
            "'permissions' cannot be empty!");
    }

    @Test
    public void shouldAddPermissions() throws Exception {
        List<Permission> permissions = new ArrayList<>();
        Permission permission = new Permission();
        permission.setName("permissionName");
        permissions.add(permission);
        Request<Void> request = api.users().addPermissions("1", permissions);
        assertThat(request, is(notNullValue()));

        server.emptyResponse(200);
        Object response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.POST, "/api/v2/users/1/permissions"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(1));
        assertThat(body, hasKey("permissions"));

        assertThat(response, is(nullValue()));
    }

    @Test
    public void shouldThrowOnRemovePermissionsWithNullId() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.users().removePermissions(null, Collections.emptyList()),
            "'user id' cannot be null!");
    }

    @Test
    public void shouldThrowOnRemovePermissionsWithNullList() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.users().removePermissions("roleId", null),
            "'permissions' cannot be null!");
    }

    @Test
    public void shouldThrowOnRemovePermissionsWithEmptyList() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.users().removePermissions("roleId", Collections.emptyList()),
            "'permissions' cannot be empty!");
    }

    @Test
    public void shouldRemovePermissions() throws Exception {
        List<Permission> permissions = new ArrayList<>();
        Permission permission = new Permission();
        permission.setName("permissionName");
        permissions.add(permission);
        Request<Void> request = api.users().removePermissions("1", permissions);
        assertThat(request, is(notNullValue()));

        server.emptyResponse(200);
        Object response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.DELETE, "/api/v2/users/1/permissions"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(1));
        assertThat(body, hasKey("permissions"));

        assertThat(response, is(nullValue()));
    }

    @Test
    public void shouldThrowOnGetOrganizationsWithNullUserId() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.users().getOrganizations(null, null),
            "'user ID' cannot be null!");
    }

    @Test
    public void shouldGetUserOrganizationsWithoutFilter() throws Exception {
        Request<OrganizationsPage> request = api.users().getOrganizations("1", null);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(ORGANIZATIONS_LIST, 200);
        OrganizationsPage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/users/1/organizations"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldGetUserOrganizationsWithPaging() throws Exception {
        Request<OrganizationsPage> request = api.users().getOrganizations("1",
            new PageFilter().withPage(0, 20));
        assertThat(request, is(notNullValue()));

        server.jsonResponse(ORGANIZATIONS_LIST, 200);
        OrganizationsPage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/users/1/organizations"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("page", "0"));
        assertThat(recordedRequest, hasQueryParameter("per_page", "20"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldGetUserOrganizationsWithTotals() throws Exception {
        Request<OrganizationsPage> request = api.users().getOrganizations("1",
            new PageFilter().withTotals(true));
        assertThat(request, is(notNullValue()));

        server.jsonResponse(ORGANIZATIONS_PAGED_LIST, 200);
        OrganizationsPage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/users/1/organizations"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("include_totals", "true"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(2));
        assertThat(response.getStart(), is(0));
        assertThat(response.getTotal(), is(2));
        assertThat(response.getLimit(), is(20));
    }

    @Test
    public void shouldGetUserAuthenticationMethodsWithNullId() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.users().getAuthenticationMethods(null, null),
            "'user ID' cannot be null!");
    }

    @Test
    public void shouldGetUserAuthenticationMethodsWithoutFilter() throws Exception {
        Request<AuthenticationMethodsPage> request = api.users().getAuthenticationMethods("1", null);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(AUTHENTICATOR_METHOD_LIST, 200);
        AuthenticationMethodsPage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/users/1/authentication-methods"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }



    @Test
    public void shouldGetUserAuthenticationMethodsWithPaging() throws Exception {
        Request<AuthenticationMethodsPage> request = api.users().getAuthenticationMethods("1",
            new PageFilter().withPage(0, 20));
        assertThat(request, is(notNullValue()));

        server.jsonResponse(AUTHENTICATOR_METHOD_PAGED_LIST, 200);
        AuthenticationMethodsPage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/users/1/authentication-methods"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("page", "0"));
        assertThat(recordedRequest, hasQueryParameter("per_page", "20"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldGetUserAuthenticationMethodsWithTotals() throws Exception {
        Request<AuthenticationMethodsPage> request = api.users().getAuthenticationMethods("1",
            new PageFilter().withTotals(true));
        assertThat(request, is(notNullValue()));

        server.jsonResponse(AUTHENTICATOR_METHOD_PAGED_LIST, 200);
        AuthenticationMethodsPage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/users/1/authentication-methods"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("include_totals", "true"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(1));
        assertThat(response.getStart(), is(0));
        assertThat(response.getTotal(), is(3));
        assertThat(response.getLimit(), is(1));
    }

    @Test
    public void shouldUpdateUserAuthenticationMethodsWithNullId() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.users().updateAuthenticationMethods(null, new ArrayList<>()),
            "'user ID' cannot be null!");
    }

    @Test
    public void shouldUpdateUserAuthenticationMethodsWithAuthenticatorMethodsNull() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.users().updateAuthenticationMethods("1", null),
            "'authentication methods' cannot be null!");
    }

    @Test
    public void shouldUpdateUserAuthenticationMethods() throws Exception {
        ArrayList<AuthenticationMethod> list = new ArrayList<>();
        AuthenticationMethod authenticationMethod = new AuthenticationMethod();
        list.add(authenticationMethod);
        Request<List<AuthenticationMethod>> request = api.users().updateAuthenticationMethods("1", list);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(AUTHENTICATOR_METHOD_UPDATE, 200);
        List<AuthenticationMethod> response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.PUT, "/api/v2/users/1/authentication-methods"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));

    }

    @Test
    public void shouldNotCreateUserAuthenticationMethodsWithNullId() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.users().createAuthenticationMethods(null, new AuthenticationMethod()),
            "'user ID' cannot be null!");
    }

    @Test
    public void shouldNotCreateUserAuthenticationMethodsWithAuthenticatorMethodsNull() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.users().createAuthenticationMethods("1", null),
            "'authentication methods' cannot be null!");
    }

    @Test
    public void shouldCreateUserAuthenticationMethods() throws Exception {
        AuthenticationMethod authenticationMethod = new AuthenticationMethod();
        Request<AuthenticationMethod> request = api.users().createAuthenticationMethods("1", authenticationMethod);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(AUTHENTICATOR_METHOD_CREATE, 200);
        AuthenticationMethod response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.POST, "/api/v2/users/1/authentication-methods"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldNotGetUserAuthenticationMethodsByIdWithNullId() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.users().getAuthenticationMethodById(null, "1"),
            "'user ID' cannot be null!");
    }

    @Test
    public void shouldNotGetUserAuthenticationMethodsByIdWithAuthenticatorMethodIdNull() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.users().getAuthenticationMethodById("1", null),
            "'authentication method ID' cannot be null!");
    }

    @Test
    public void shouldGetUserAuthenticationMethodById() throws Exception {
        Request<AuthenticationMethod> request = api.users().getAuthenticationMethodById("1", "1");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(AUTHENTICATOR_METHOD_BY_ID, 200);
        AuthenticationMethod response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/users/1/authentication-methods/1"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldNotUpdateUserAuthenticationMethodsByIdWithNullId() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.users().updateAuthenticationMethodById(null, "1", new AuthenticationMethod()),
            "'user ID' cannot be null!");
    }

    @Test
    public void shouldNotUpdateUserAuthenticationMethodsByIdWithAuthenticatorMethodIdNull() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.users().updateAuthenticationMethodById("1", null, new AuthenticationMethod()),
            "'authentication method ID' cannot be null!");
    }

    @Test
    public void shouldNotUpdateUserAuthenticationMethodsByIdWithAuthenticatorMethodNull() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.users().updateAuthenticationMethodById("1", "1", null),
            "'authentication method' cannot be null!");
    }

    @Test
    public void shouldUpdateUserAuthenticationMethodById() throws Exception {
        Request<AuthenticationMethod> request = api.users().updateAuthenticationMethodById("1", "1", new AuthenticationMethod());
        assertThat(request, is(notNullValue()));

        server.jsonResponse(AUTHENTICATOR_METHOD_UPDATE_BY_ID, 200);
        AuthenticationMethod response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.PATCH, "/api/v2/users/1/authentication-methods/1"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldNotDeleteUserAuthenticationMethodsByIdWithNullId() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.users().deleteAuthenticationMethodById(null, "1"),
            "'user ID' cannot be null!");
    }

    @Test
    public void shouldNotDeleteUserAuthenticationMethodsByIdWithAuthenticatorMethodIdNull() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.users().deleteAuthenticationMethodById("1", null),
            "'authentication method ID' cannot be null!");
    }

    @Test
    public void shouldDeleteUserAuthenticationMethodById() throws Exception {
        Request<Void> request = api.users().deleteAuthenticationMethodById("1", "1");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(AUTHENTICATOR_METHOD_UPDATE_BY_ID, 200);
        Void response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.DELETE, "/api/v2/users/1/authentication-methods/1"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
    }

    @Test
    public void invalidateRememberedBrowsersThrowsWhenUserIdIsNull() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.users().invalidateRememberedBrowsers(null),
            "'user ID' cannot be null!");
    }

    @Test
    public void shouldInvalidateRememberedBrowsers() throws Exception {
        Request<Void> request = api.users().invalidateRememberedBrowsers("userId");
        assertThat(request, is(notNullValue()));

        server.noContentResponse();
        Void response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.POST, "/api/v2/users/userId/multifactor/actions/invalidate-remember-browser"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
    }
}

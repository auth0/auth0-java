package com.auth0.client.mgmt;

import static com.auth0.client.MockServer.MGMT_EMAIL_PROVIDER;
import static com.auth0.client.MockServer.MGMT_EMPTY_LIST;
import static com.auth0.client.MockServer.MGMT_GUARDIAN_ENROLLMENTS_LIST;
import static com.auth0.client.MockServer.MGMT_IDENTITIES_LIST;
import static com.auth0.client.MockServer.MGMT_LOG_EVENTS_LIST;
import static com.auth0.client.MockServer.MGMT_LOG_EVENTS_PAGED_LIST;
import static com.auth0.client.MockServer.MGMT_RECOVERY_CODE;
import static com.auth0.client.MockServer.MGMT_USER;
import static com.auth0.client.MockServer.MGMT_USERS_LIST;
import static com.auth0.client.MockServer.MGMT_USERS_PAGED_LIST;
import static com.auth0.client.MockServer.MGMT_USER_PERMISSIONS_PAGED_LIST;
import static com.auth0.client.MockServer.MGMT_USER_ROLES_PAGED_LIST;
import static com.auth0.client.MockServer.bodyFromRequest;
import static com.auth0.client.RecordedRequestMatcher.hasHeader;
import static com.auth0.client.RecordedRequestMatcher.hasMethodAndPath;
import static com.auth0.client.RecordedRequestMatcher.hasQueryParameter;
import static org.hamcrest.Matchers.emptyCollectionOf;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import com.auth0.client.mgmt.filter.FieldsFilter;
import com.auth0.client.mgmt.filter.LogEventFilter;
import com.auth0.client.mgmt.filter.PageFilter;
import com.auth0.client.mgmt.filter.UserFilter;
import com.auth0.json.mgmt.Permission;
import com.auth0.json.mgmt.PermissionsPage;
import com.auth0.json.mgmt.RolesPage;
import com.auth0.json.mgmt.guardian.Enrollment;
import com.auth0.json.mgmt.logevents.LogEvent;
import com.auth0.json.mgmt.logevents.LogEventsPage;
import com.auth0.json.mgmt.users.Identity;
import com.auth0.json.mgmt.users.RecoveryCode;
import com.auth0.json.mgmt.users.User;
import com.auth0.json.mgmt.users.UsersPage;
import com.auth0.net.Request;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.Test;

public class UsersEntityTest extends BaseMgmtEntityTest {

    @Test
    public void shouldListUsersByEmail() throws Exception {
        Request<List<User>> request = api.users().listByEmail("johndoe@auth0.com", null);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_USERS_LIST, 200);
        List<User> response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/users-by-email"));
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
        List<User> response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/users-by-email"));
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
        List<User> response = request.execute();

        assertThat(response, is(notNullValue()));
        assertThat(response, is(emptyCollectionOf(User.class)));
    }

    @Test
    public void shouldListUsers() throws Exception {
        Request<UsersPage> request = api.users().list(null);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_USERS_LIST, 200);
        UsersPage response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/users"));
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
        UsersPage response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/users"));
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
        UsersPage response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/users"));
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
        UsersPage response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/users"));
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
        UsersPage response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/users"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, not(hasQueryParameter("search_engine")));
        assertThat(recordedRequest, hasQueryParameter("q", "email%3A%5C*%40gmail.com"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(2));
    }

    @Test
    public void shouldListUsersWithFields() throws Exception {
        UserFilter filter = new UserFilter().withFields("some,random,fields", true);
        Request<UsersPage> request = api.users().list(filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_USERS_LIST, 200);
        UsersPage response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/users"));
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
        UsersPage response = request.execute();

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), is(emptyCollectionOf(User.class)));
    }

    @Test
    public void shouldThrowOnGetUserWithNullId() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'user id' cannot be null!");
        api.users().get(null, null);
    }

    @Test
    public void shouldGetUser() throws Exception {
        Request<User> request = api.users().get("1", null);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_USER, 200);
        User response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/users/1"));
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
        User response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/users/1"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("fields", "some,random,fields"));
        assertThat(recordedRequest, hasQueryParameter("include_fields", "true"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnCreateUserWithNullData() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'user' cannot be null!");
        api.users().create(null);
    }

    @Test
    public void shouldCreateUser() throws Exception {
        Request<User> request = api.users().create(new User("auth0"));
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_USER, 200);
        User response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("POST", "/api/v2/users"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(1));
        assertThat(body, hasEntry("connection", (Object) "auth0"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnDeleteUserWithNullId() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'user id' cannot be null!");
        api.users().delete(null);
    }

    @Test
    public void shouldDeleteUser() throws Exception {
        Request request = api.users().delete("1");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_USER, 200);
        request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("DELETE", "/api/v2/users/1"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
    }

    @Test
    public void shouldThrowOnUpdateUserWithNullId() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'user id' cannot be null!");
        api.users().update(null, new User("auth0"));
    }

    @Test
    public void shouldThrowOnUpdateUserWithNullData() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'user' cannot be null!");
        api.users().update("1", null);
    }

    @Test
    public void shouldUpdateUser() throws Exception {
        Request<User> request = api.users().update("1", new User("auth0"));
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_USER, 200);
        User response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("PATCH", "/api/v2/users/1"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(1));
        assertThat(body, hasEntry("connection", (Object) "auth0"));

        assertThat(response, is(notNullValue()));
    }


    @Test
    public void shouldThrowOnGetUserGuardianEnrollmentsWithNullId() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'user id' cannot be null!");
        api.users().getEnrollments(null);
    }

    @Test
    public void shouldGetUserGuardianEnrollments() throws Exception {
        Request<List<Enrollment>> request = api.users().getEnrollments("1");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_GUARDIAN_ENROLLMENTS_LIST, 200);
        List<Enrollment> response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/users/1/enrollments"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldReturnEmptyUserGuardianEnrollments() throws Exception {
        Request<List<Enrollment>> request = api.users().getEnrollments("1");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_EMPTY_LIST, 200);
        List<Enrollment> response = request.execute();

        assertThat(response, is(notNullValue()));
        assertThat(response, is(emptyCollectionOf(Enrollment.class)));
    }

    @Test
    public void shouldThrowOnGetUserLogEventsWithNullId() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'user id' cannot be null!");
        api.users().getLogEvents(null, null);
    }

    @Test
    public void shouldGetUserLogEvents() throws Exception {
        Request<LogEventsPage> request = api.users().getLogEvents("1", null);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_LOG_EVENTS_LIST, 200);
        LogEventsPage response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/users/1/logs"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldGetUserLogEventsWithPage() throws Exception {
        LogEventFilter filter = new LogEventFilter().withPage(23, 5);
        Request<LogEventsPage> request = api.users().getLogEvents("1", filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_LOG_EVENTS_LIST, 200);
        LogEventsPage response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/users/1/logs"));
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
        LogEventsPage response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/users/1/logs"));
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
        LogEventsPage response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/users/1/logs"));
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
        LogEventsPage response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/users/1/logs"));
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
        LogEventsPage response = request.execute();

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), is(emptyCollectionOf(LogEvent.class)));
    }

    @Test
    public void shouldThrowOnDeleteUserMultifactorProviderWithNullId() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'user id' cannot be null!");
        api.users().deleteMultifactorProvider(null, "duo");
    }

    @Test
    public void shouldThrowOnDeleteUserMultifactorProviderWithNullProvider() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'provider' cannot be null!");
        api.users().deleteMultifactorProvider("1", null);
    }

    @Test
    public void shouldDeleteUserMultifactorProvider() throws Exception {
        Request request = api.users().deleteMultifactorProvider("1", "duo");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_EMAIL_PROVIDER, 200);
        request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("DELETE", "/api/v2/users/1/multifactor/duo"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
    }

    @Test
    public void shouldThrowOnRotateUserRecoveryCodeWithNullId() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'user id' cannot be null!");
        api.users().rotateRecoveryCode(null);
    }

    @Test
    public void shouldRotateUserRecoveryCode() throws Exception {
        Request<RecoveryCode> request = api.users().rotateRecoveryCode("1");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_RECOVERY_CODE, 200);
        RecoveryCode response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("POST", "/api/v2/users/1/recovery-code-regeneration"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnLinkUserIdentityWithNullPrimaryId() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'primary user id' cannot be null!");
        api.users().linkIdentity(null, "2", "auth0", null);
    }

    @Test
    public void shouldThrowOnLinkUserIdentityWithNullSecondaryId() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'secondary user id' cannot be null!");
        api.users().linkIdentity("1", null, "auth0", null);
    }

    @Test
    public void shouldThrowOnLinkUserIdentityWithNullProvider() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'provider' cannot be null!");
        api.users().linkIdentity("1", "2", null, null);
    }

    @Test
    public void shouldLinkUserIdentity() throws Exception {
        Request<List<Identity>> request = api.users().linkIdentity("1", "2", "auth0", null);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_IDENTITIES_LIST, 200);
        List<Identity> response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("POST", "/api/v2/users/1/identities"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(2));
        assertThat(body, hasEntry("provider", (Object) "auth0"));
        assertThat(body, hasEntry("user_id", (Object) "2"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldLinkUserIdentityWithConnectionId() throws Exception {
        Request<List<Identity>> request = api.users().linkIdentity("1", "2", "auth0", "123456790");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_IDENTITIES_LIST, 200);
        List<Identity> response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("POST", "/api/v2/users/1/identities"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(3));
        assertThat(body, hasEntry("provider", (Object) "auth0"));
        assertThat(body, hasEntry("user_id", (Object) "2"));
        assertThat(body, hasEntry("connection_id", (Object) "123456790"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnUnlinkUserIdentityWithNullPrimaryId() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'primary user id' cannot be null!");
        api.users().unlinkIdentity(null, "2", "auth0");
    }

    @Test
    public void shouldThrowOnUnlinkUserIdentityWithNullSecondaryId() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'secondary user id' cannot be null!");
        api.users().unlinkIdentity("1", null, "auth0");
    }

    @Test
    public void shouldThrowOnUnlinkUserIdentityWithNullProvider() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'provider' cannot be null!");
        api.users().unlinkIdentity("1", "2", null);
    }

    @Test
    public void shouldUnlinkUserIdentity() throws Exception {
        Request<List<Identity>> request = api.users().unlinkIdentity("1", "2", "auth0");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_IDENTITIES_LIST, 200);
        List<Identity> response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("DELETE", "/api/v2/users/1/identities/auth0/2"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnListRolesWithNullId() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'user id' cannot be null!");
        api.users().listRoles(null, null);
    }

    @Test
    public void shouldListRolesWithoutFilter() throws Exception {
        Request<RolesPage> request = api.users().listRoles("1", null);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_USER_ROLES_PAGED_LIST, 200);
        RolesPage response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/users/1/roles"));
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
        RolesPage response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/users/1/roles"));
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
        RolesPage response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/users/1/roles"));
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
    public void shouldThrowOnAddRolesWithNullId() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'user id' cannot be null!");
        api.users().addRoles(null, Collections.emptyList());
    }

    @Test
    public void shouldThrowOnAddRolesWithNullList() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'role ids' cannot be null!");
        api.users().addRoles("1", null);
    }

    @Test
    public void shouldThrowOnAddRolesWithEmptyList() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'role ids' cannot be empty!");
        api.users().addRoles("1", Collections.emptyList());
    }

    @Test
    public void shouldAddRoles() throws Exception {
        Request request = api.users().addRoles("1",  Collections.singletonList("roleId"));
        assertThat(request, is(notNullValue()));

        server.emptyResponse(200);
        Object response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("POST", "/api/v2/users/1/roles"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(1));
        assertThat(body, hasKey("roles"));

        assertThat(response, is(nullValue()));
    }

    @Test
    public void shouldThrowOnRemoveRolesWithNullId() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'user id' cannot be null!");
        api.users().removeRoles(null, Collections.emptyList());
    }

    @Test
    public void shouldThrowOnRemoveRolesWithNullList() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'role ids' cannot be null!");
        api.users().removeRoles("1", null);
    }

    @Test
    public void shouldThrowOnRemoveRolesWithEmptyList() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'role ids' cannot be empty!");
        api.users().removeRoles("1", Collections.emptyList());
    }

    @Test
    public void shouldRemoveRoles() throws Exception {
        Request request = api.users().removeRoles("1", Collections.singletonList("roleId"));
        assertThat(request, is(notNullValue()));

        server.emptyResponse( 200);
        Object response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("DELETE", "/api/v2/users/1/roles"));
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
        PermissionsPage response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/users/1/permissions"));
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
        PermissionsPage response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/users/1/permissions"));
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
        PermissionsPage response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/users/1/permissions"));
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
        exception.expectMessage("'user id' cannot be null!");
        api.users().addPermissions(null, Collections.emptyList());
    }

    @Test
    public void shouldThrowOnAddPermissionsWithNullList() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'permissions' cannot be null!");
        api.users().addPermissions("1", null);
    }

    @Test
    public void shouldThrowOnAddPermissionsWithEmptyList() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'permissions' cannot be empty!");
        api.users().addPermissions("1", Collections.emptyList());
    }

    @Test
    public void shouldAddPermissions() throws Exception {
        List<Permission> permissions = new ArrayList<>();
        Permission permission = new Permission();
        permission.setName("permissionName");
        permissions.add(permission);
        Request request = api.users().addPermissions("1", permissions);
        assertThat(request, is(notNullValue()));

        server.emptyResponse(200);
        Object response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("POST", "/api/v2/users/1/permissions"));
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
        exception.expectMessage("'user id' cannot be null!");
        api.users().removePermissions(null, Collections.emptyList());
    }

    @Test
    public void shouldThrowOnRemovePermissionsWithNullList() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'permissions' cannot be null!");
        api.users().removePermissions("roleId", null);
    }

    @Test
    public void shouldThrowOnRemovePermissionsWithEmptyList() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'permissions' cannot be empty!");
        api.users().removePermissions("roleId", Collections.emptyList());
    }

    @Test
    public void shouldRemovePermissions() throws Exception {
        List<Permission> permissions = new ArrayList<>();
        Permission permission = new Permission();
        permission.setName("permissionName");
        permissions.add(permission);
        Request request = api.users().removePermissions("1", permissions);
        assertThat(request, is(notNullValue()));

        server.emptyResponse(200);
        Object response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("DELETE", "/api/v2/users/1/permissions"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(1));
        assertThat(body, hasKey("permissions"));

        assertThat(response, is(nullValue()));
    }
}

package com.auth0.management.users;

import com.auth0.Auth0;
import com.auth0.management.result.LogsListPage;
import com.auth0.management.result.User;
import com.auth0.management.result.UsersListPage;
import com.auth0.request.internal.RequestFactory;
import com.auth0.util.MockBaseCallback;
import com.auth0.util.UsersManagementAPI;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.mockwebserver.RecordedRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.auth0.util.CallbackMatcher.hasNoError;
import static com.auth0.util.CallbackMatcher.hasPayload;
import static com.auth0.util.CallbackMatcher.hasPayloadOfType;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class UsersAPIClientTest {

    private static final String CLIENT_ID = "CLIENT_ID";
    private static final String TOKEN = "TOKEN";
    private static final String USER_ID = "USER_ID";

    private UsersAPIClient users;

    private UsersManagementAPI mockAPI;

    @Before
    public void setUp() throws Exception {
        mockAPI = new UsersManagementAPI();
        final String domain = mockAPI.getDomain();
        Auth0 auth0 = new Auth0(CLIENT_ID, domain, domain);
        users = new UsersAPIClient(auth0, new OkHttpClient(), new Gson(), new RequestFactory(), TOKEN);
    }

    @After
    public void tearDown() throws Exception {
        mockAPI.shutdown();
    }

    @Test
    public void shouldGetFullUser() throws Exception {
        mockAPI.willReturnUser();

        final MockBaseCallback<User> callback = new MockBaseCallback<>();

        users.get(USER_ID)
                .start(callback);

        assertThat(callback, hasPayloadOfType(User.class));

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getHeader("Authorization"), equalTo("Bearer TOKEN"));
        assertThat(request.getMethod(), equalTo("GET"));
        assertThat(request.getPath(), equalTo("/api/v2/users/USER_ID"));
    }

    @Test
    public void shouldGetUserIncludingFields() throws Exception {
        mockAPI.willReturnUser();

        final MockBaseCallback<User> callback = new MockBaseCallback<>();

        users.get(USER_ID)
                .onlyInclude("field1", "field2")
                .start(callback);

        assertThat(callback, hasPayloadOfType(User.class));

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getHeader("Authorization"), equalTo("Bearer TOKEN"));
        assertThat(request.getMethod(), equalTo("GET"));
        assertThat(request.getPath(), equalTo("/api/v2/users/USER_ID?include_fields=true&fields=field1,field2"));
    }

    @Test
    public void shouldGetUserExcludingFields() throws Exception {
        mockAPI.willReturnUser();

        final MockBaseCallback<User> callback = new MockBaseCallback<>();

        users.get(USER_ID)
                .exclude("field3", "field4")
                .start(callback);

        assertThat(callback, hasPayloadOfType(User.class));

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getHeader("Authorization"), equalTo("Bearer TOKEN"));
        assertThat(request.getMethod(), equalTo("GET"));
        assertThat(request.getPath(), equalTo("/api/v2/users/USER_ID?include_fields=false&fields=field3,field4"));
    }

    @Test
    public void shouldUpdateBlocked() throws Exception {
        mockAPI.willReturnUser();

        final MockBaseCallback<User> callback = new MockBaseCallback<>();

        users.update(USER_ID)
                .setBlocked(false)
                .start(callback);

        assertThat(callback, hasPayloadOfType(User.class));

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getHeader("Authorization"), equalTo("Bearer TOKEN"));
        assertThat(request.getMethod(), equalTo("PATCH"));
        assertThat(request.getPath(), equalTo("/api/v2/users/USER_ID"));

        Map<String, Object> body = bodyFromRequest(request);
        assertThat(body, hasEntry("blocked", (Object) false));
    }

    @Test
    public void shouldUpdateAppMetadata() throws Exception {
        mockAPI.willReturnUser();

        final MockBaseCallback<User> callback = new MockBaseCallback<>();

        HashMap<String, Object> appMetadata = new HashMap<>();
        appMetadata.put("app_field1_key", "app_field1_value");

        users.update(USER_ID)
                .setAppMetadata(appMetadata)
                .start(callback);

        assertThat(callback, hasPayloadOfType(User.class));

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getHeader("Authorization"), equalTo("Bearer TOKEN"));
        assertThat(request.getMethod(), equalTo("PATCH"));
        assertThat(request.getPath(), equalTo("/api/v2/users/USER_ID"));

        Map<String, Object> body = bodyFromRequest(request);
        assertThat(body, hasEntry("app_metadata", (Object) appMetadata));

        Map<String, Object> appMetadata2 = (Map<String, Object>) body.get("app_metadata");
        assertThat(appMetadata2, hasEntry("app_field1_key", (Object) "app_field1_value"));
    }

    @Test
    public void shouldUpdateEmailAlreadyVerified() throws Exception {
        mockAPI.willReturnUser();

        final MockBaseCallback<User> callback = new MockBaseCallback<>();

        users.update(USER_ID)
                .setPhoneNumber("+1 555 666 777")
                .setPhoneNumberVerified(true)
                .forConnection("some_connection_name")
                .withClientId("some_client_id")
                .start(callback);

        assertThat(callback, hasPayloadOfType(User.class));

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getHeader("Authorization"), equalTo("Bearer TOKEN"));
        assertThat(request.getMethod(), equalTo("PATCH"));
        assertThat(request.getPath(), equalTo("/api/v2/users/USER_ID"));

        Map<String, Object> body = bodyFromRequest(request);
        assertThat(body, hasEntry("phone_number", (Object) "+1 555 666 777"));
        assertThat(body, hasEntry("connection", (Object) "some_connection_name"));
        assertThat(body, hasEntry("client_id", (Object) "some_client_id"));
        assertThat(body, hasEntry("phone_verified", (Object) true));
    }

    @Test
    public void shouldUpdateEmailAndRequireVerification() throws Exception {
        mockAPI.willReturnUser();

        final MockBaseCallback<User> callback = new MockBaseCallback<>();

        users.update(USER_ID)
                .setEmail("email@example.com")
                .requiresEmailVerification(true)
                .forConnection("some_connection_name")
                .withClientId("some_client_id")
                .start(callback);

        assertThat(callback, hasPayloadOfType(User.class));

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getHeader("Authorization"), equalTo("Bearer TOKEN"));
        assertThat(request.getMethod(), equalTo("PATCH"));
        assertThat(request.getPath(), equalTo("/api/v2/users/USER_ID"));

        Map<String, Object> body = bodyFromRequest(request);
        assertThat(body, hasEntry("email", (Object) "email@example.com"));
        assertThat(body, hasEntry("connection", (Object) "some_connection_name"));
        assertThat(body, hasEntry("client_id", (Object) "some_client_id"));
        assertThat(body, hasEntry("verify_email", (Object) true));
    }

    @Test
    public void shouldUpdatePhoneNumberAlreadyVerified() throws Exception {
        mockAPI.willReturnUser();

        final MockBaseCallback<User> callback = new MockBaseCallback<>();

        users.update(USER_ID)
                .setEmail("email@example.com")
                .setEmailVerified(true)
                .forConnection("some_connection_name")
                .withClientId("some_client_id")
                .start(callback);

        assertThat(callback, hasPayloadOfType(User.class));

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getHeader("Authorization"), equalTo("Bearer TOKEN"));
        assertThat(request.getMethod(), equalTo("PATCH"));
        assertThat(request.getPath(), equalTo("/api/v2/users/USER_ID"));

        Map<String, Object> body = bodyFromRequest(request);
        assertThat(body, hasEntry("email", (Object) "email@example.com"));
        assertThat(body, hasEntry("connection", (Object) "some_connection_name"));
        assertThat(body, hasEntry("client_id", (Object) "some_client_id"));
        assertThat(body, hasEntry("email_verified", (Object) true));
    }

    @Test
    public void shouldUpdatePhoneNumberAndRequireVerification() throws Exception {
        mockAPI.willReturnUser();

        final MockBaseCallback<User> callback = new MockBaseCallback<>();

        users.update(USER_ID)
                .setPhoneNumber("+1 555 666 777")
                .requiresPhoneNumberVerification(true)
                .forConnection("some_connection_name")
                .withClientId("some_client_id")
                .start(callback);

        assertThat(callback, hasPayloadOfType(User.class));

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getHeader("Authorization"), equalTo("Bearer TOKEN"));
        assertThat(request.getMethod(), equalTo("PATCH"));
        assertThat(request.getPath(), equalTo("/api/v2/users/USER_ID"));

        Map<String, Object> body = bodyFromRequest(request);
        assertThat(body, hasEntry("phone_number", (Object) "+1 555 666 777"));
        assertThat(body, hasEntry("connection", (Object) "some_connection_name"));
        assertThat(body, hasEntry("client_id", (Object) "some_client_id"));
        assertThat(body, hasEntry("verify_phone_number", (Object) true));
    }

    @Test
    public void shouldUpdatePasswordAndRequireVerification() throws Exception {
        mockAPI.willReturnUser();

        final MockBaseCallback<User> callback = new MockBaseCallback<>();

        users.update(USER_ID)
                .setPassword("some secret password")
                .verifyPasswordByEmail(true)
                .forConnection("some_connection_name")
                .withClientId("some_client_id")
                .start(callback);

        assertThat(callback, hasPayloadOfType(User.class));

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getHeader("Authorization"), equalTo("Bearer TOKEN"));
        assertThat(request.getMethod(), equalTo("PATCH"));
        assertThat(request.getPath(), equalTo("/api/v2/users/USER_ID"));

        Map<String, Object> body = bodyFromRequest(request);
        assertThat(body, hasEntry("password", (Object) "some secret password"));
        assertThat(body, hasEntry("connection", (Object) "some_connection_name"));
        assertThat(body, hasEntry("client_id", (Object) "some_client_id"));
        assertThat(body, hasEntry("verify_password", (Object) true));
    }

    @Test
    public void shouldUpdateUsernameAndIncludeConnection() throws Exception {
        mockAPI.willReturnUser();

        final MockBaseCallback<User> callback = new MockBaseCallback<>();

        users.update(USER_ID)
                .setUsername("my_username")
                .forConnection("connection_name")
                .start(callback);

        assertThat(callback, hasPayloadOfType(User.class));

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getHeader("Authorization"), equalTo("Bearer TOKEN"));
        assertThat(request.getMethod(), equalTo("PATCH"));
        assertThat(request.getPath(), equalTo("/api/v2/users/USER_ID"));

        Map<String, Object> body = bodyFromRequest(request);
        assertThat(body, hasEntry("username", (Object) "my_username"));
        assertThat(body, hasEntry("connection", (Object) "connection_name"));
    }

    @Test
    public void shouldUpdateUserMetadata() throws Exception {
        mockAPI.willReturnUser();

        final MockBaseCallback<User> callback = new MockBaseCallback<>();

        HashMap<String, Object> userMetadata = new HashMap<>();
        userMetadata.put("user_field1_key", "user_field1_value");

        users.update(USER_ID)
                .setUserMetadata(userMetadata)
                .start(callback);

        assertThat(callback, hasPayloadOfType(User.class));

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getHeader("Authorization"), equalTo("Bearer TOKEN"));
        assertThat(request.getMethod(), equalTo("PATCH"));
        assertThat(request.getPath(), equalTo("/api/v2/users/USER_ID"));

        Map<String, Object> body = bodyFromRequest(request);
        assertThat(body, hasEntry("user_metadata", (Object) userMetadata));

        Map<String, Object> userMetadata2 = (Map<String, Object>) body.get("user_metadata");
        assertThat(userMetadata2, hasEntry("user_field1_key", (Object) "user_field1_value"));
    }

    @Test
    public void shouldDeleteUser() throws Exception {
        mockAPI.willReturnSuccess(204);

        final MockBaseCallback<Void> callback = new MockBaseCallback<>();

        users.delete(USER_ID)
                .start(callback);

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getHeader("Authorization"), equalTo("Bearer TOKEN"));
        assertThat(request.getMethod(), equalTo("DELETE"));
        assertThat(request.getPath(), equalTo("/api/v2/users/USER_ID"));

        assertThat(callback, hasNoError());
    }

    @Test
    public void shouldLinkToUserUsingJwt() throws Exception {
        mockAPI.willReturnIdentities();

        final MockBaseCallback<List<Map<String, Object>>> callback = new MockBaseCallback<>();

        users.link(USER_ID)
                .withAccountToken("SOME_OTHER_TOKEN")
                .start(callback);

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getHeader("Authorization"), equalTo("Bearer TOKEN"));
        assertThat(request.getMethod(), equalTo("POST"));
        assertThat(request.getPath(), equalTo("/api/v2/users/USER_ID/identities"));

        Map<String, Object> body = bodyFromRequest(request);
        assertThat(body, hasEntry("link_with", (Object) "SOME_OTHER_TOKEN"));

        List<Map<String, Object>> payload = getIdentitiesPayload();
        assertThat(callback, hasPayload(payload));
    }

    @Test
    public void shouldLinkToUserWithProviderOnly() throws Exception {
        mockAPI.willReturnIdentities();

        final MockBaseCallback<List<Map<String, Object>>> callback = new MockBaseCallback<>();

        users.link(USER_ID)
                .withUser("SOME_OTHER_USER_ID")
                .ofProvider("SOME_PROVIDER")
                .start(callback);

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getHeader("Authorization"), equalTo("Bearer TOKEN"));
        assertThat(request.getMethod(), equalTo("POST"));
        assertThat(request.getPath(), equalTo("/api/v2/users/USER_ID/identities"));

        Map<String, Object> body = bodyFromRequest(request);
        assertThat(body, hasEntry("user_id", (Object) "SOME_OTHER_USER_ID"));
        assertThat(body, hasEntry("provider", (Object) "SOME_PROVIDER"));

        List<Map<String, Object>> payload = getIdentitiesPayload();
        assertThat(callback, hasPayload(payload));
    }

    @Test
    public void shouldLinkToUserWithProviderAndConnection() throws Exception {
        mockAPI.willReturnIdentities();

        final MockBaseCallback<List<Map<String, Object>>> callback = new MockBaseCallback<>();

        users.link(USER_ID)
                .withUser("SOME_OTHER_USER_ID")
                .ofProvider("SOME_PROVIDER")
                .setConnection("SOME_CONNECTION_ID")
                .start(callback);

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getHeader("Authorization"), equalTo("Bearer TOKEN"));
        assertThat(request.getMethod(), equalTo("POST"));
        assertThat(request.getPath(), equalTo("/api/v2/users/USER_ID/identities"));

        Map<String, Object> body = bodyFromRequest(request);
        assertThat(body, hasEntry("user_id", (Object) "SOME_OTHER_USER_ID"));
        assertThat(body, hasEntry("provider", (Object) "SOME_PROVIDER"));
        assertThat(body, hasEntry("connection_id", (Object) "SOME_CONNECTION_ID"));

        List<Map<String, Object>> payload = getIdentitiesPayload();
        assertThat(callback, hasPayload(payload));
    }

    @Test
    public void shouldUnlinkAccount() throws Exception {
        mockAPI.willReturnIdentities();

        final MockBaseCallback<List<Map<String, Object>>> callback = new MockBaseCallback<>();

        users.unlink(USER_ID)
                .fromUser("SOME_OTHER_USER_ID")
                .ofProvider("SOME_PROVIDER")
                .start(callback);

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getHeader("Authorization"), equalTo("Bearer TOKEN"));
        assertThat(request.getMethod(), equalTo("DELETE"));
        assertThat(request.getPath(), equalTo("/api/v2/users/USER_ID/identities/SOME_PROVIDER/SOME_OTHER_USER_ID"));

        List<Map<String, Object>> payload = getIdentitiesPayload();
        assertThat(callback, hasPayload(payload));
    }

    @Test
    public void shouldDeleteMultifactorProvider() throws Exception {
        mockAPI.willReturnSuccess(204);

        final MockBaseCallback<Void> callback = new MockBaseCallback<>();

        users.multifactor(USER_ID)
                .deleteProvider("THE_MFA_PROVIDER_TO_DELETE")
                .start(callback);

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getHeader("Authorization"), equalTo("Bearer TOKEN"));
        assertThat(request.getMethod(), equalTo("DELETE"));
        assertThat(request.getPath(), equalTo("/api/v2/users/USER_ID/multifactor/THE_MFA_PROVIDER_TO_DELETE"));

        assertThat(callback, hasNoError());
    }

    @Test
    public void shouldGetLogs() throws Exception {
        mockAPI.willReturnLogs();

        final MockBaseCallback<LogsListPage> callback = new MockBaseCallback<>();

        users.logs(USER_ID)
                .start(callback);

        assertThat(callback, hasPayloadOfType(LogsListPage.class));

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getHeader("Authorization"), equalTo("Bearer TOKEN"));
        assertThat(request.getMethod(), equalTo("GET"));
        assertThat(request.getPath(), equalTo("/api/v2/users/USER_ID/logs?include_totals=true"));
    }

    @Test
    public void shouldGetLogsWithCustomPerPage() throws Exception {
        mockAPI.willReturnLogs();

        final MockBaseCallback<LogsListPage> callback = new MockBaseCallback<>();

        users.logs(USER_ID)
                .perPage(10)
                .start(callback);

        assertThat(callback, hasPayloadOfType(LogsListPage.class));

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getHeader("Authorization"), equalTo("Bearer TOKEN"));
        assertThat(request.getMethod(), equalTo("GET"));
        assertThat(request.getPath(), equalTo("/api/v2/users/USER_ID/logs?per_page=10&include_totals=true"));
    }

    @Test
    public void shouldGetLogsWithCustomPage() throws Exception {
        mockAPI.willReturnLogs();

        final MockBaseCallback<LogsListPage> callback = new MockBaseCallback<>();

        users.logs(USER_ID)
                .page(50)
                .start(callback);

        assertThat(callback, hasPayloadOfType(LogsListPage.class));

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getHeader("Authorization"), equalTo("Bearer TOKEN"));
        assertThat(request.getMethod(), equalTo("GET"));
        assertThat(request.getPath(), equalTo("/api/v2/users/USER_ID/logs?page=50&include_totals=true"));
    }

    @Test
    public void shouldGetLogsWithCustomSortFieldAsc() throws Exception {
        mockAPI.willReturnLogs();

        final MockBaseCallback<LogsListPage> callback = new MockBaseCallback<>();

        users.logs(USER_ID)
                .sortBy("SORT_FIELD", UserLogsRequest.SORT_ASC)
                .start(callback);

        assertThat(callback, hasPayloadOfType(LogsListPage.class));

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getHeader("Authorization"), equalTo("Bearer TOKEN"));
        assertThat(request.getMethod(), equalTo("GET"));
        assertThat(request.getPath(), equalTo("/api/v2/users/USER_ID/logs?sort=SORT_FIELD:1&include_totals=true"));
    }

    @Test
    public void shouldGetLogsWithCustomSortFieldDesc() throws Exception {
        mockAPI.willReturnLogs();

        final MockBaseCallback<LogsListPage> callback = new MockBaseCallback<>();

        users.logs(USER_ID)
                .sortBy("SORT_FIELD", UserLogsRequest.SORT_DESC)
                .start(callback);

        assertThat(callback, hasPayloadOfType(LogsListPage.class));

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getHeader("Authorization"), equalTo("Bearer TOKEN"));
        assertThat(request.getMethod(), equalTo("GET"));
        assertThat(request.getPath(), equalTo("/api/v2/users/USER_ID/logs?sort=SORT_FIELD:-1&include_totals=true"));
    }

    @Test
    public void shouldGetLogsCustom() throws Exception {
        mockAPI.willReturnLogs();

        final MockBaseCallback<LogsListPage> callback = new MockBaseCallback<>();

        users.logs(USER_ID)
                .perPage(2)
                .page(3)
                .sortBy("A_SORT_FIELD", UserLogsRequest.SORT_ASC)
                .start(callback);

        assertThat(callback, hasPayloadOfType(LogsListPage.class));

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getHeader("Authorization"), equalTo("Bearer TOKEN"));
        assertThat(request.getMethod(), equalTo("GET"));
        assertThat(request.getPath(), equalTo("/api/v2/users/USER_ID/logs?per_page=2&sort=A_SORT_FIELD:1&page=3&include_totals=true"));
    }

    @Test
    public void shouldGetUsersWithCustomPage() throws Exception {
        mockAPI.willReturnUsers();

        final MockBaseCallback<UsersListPage> callback = new MockBaseCallback<>();

        users.list()
                .page(55)
                .start(callback);

        assertThat(callback, hasPayloadOfType(UsersListPage.class));

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getHeader("Authorization"), equalTo("Bearer TOKEN"));
        assertThat(request.getMethod(), equalTo("GET"));
        assertThat(request.getPath(), equalTo("/api/v2/users?page=55&include_totals=true&search_engine=v2"));
    }

    @Test
    public void shouldGetUsersWithCustomPerPage() throws Exception {
        mockAPI.willReturnUsers();

        final MockBaseCallback<UsersListPage> callback = new MockBaseCallback<>();

        users.list()
                .perPage(21)
                .start(callback);

        assertThat(callback, hasPayloadOfType(UsersListPage.class));

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getHeader("Authorization"), equalTo("Bearer TOKEN"));
        assertThat(request.getMethod(), equalTo("GET"));
        assertThat(request.getPath(), equalTo("/api/v2/users?per_page=21&include_totals=true&search_engine=v2"));
    }

    @Test
    public void shouldGetUsersWithCustomSortFieldAsc() throws Exception {
        mockAPI.willReturnUsers();

        final MockBaseCallback<UsersListPage> callback = new MockBaseCallback<>();

        users.list()
                .sortBy("A_SORT_FIELD", UserListRequest.SORT_ASC)
                .start(callback);

        assertThat(callback, hasPayloadOfType(UsersListPage.class));

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getHeader("Authorization"), equalTo("Bearer TOKEN"));
        assertThat(request.getMethod(), equalTo("GET"));
        assertThat(request.getPath(), equalTo("/api/v2/users?sort=A_SORT_FIELD:1&include_totals=true&search_engine=v2"));
    }

    @Test
    public void shouldGetUsersWithCustomSortFieldDesc() throws Exception {
        mockAPI.willReturnUsers();

        final MockBaseCallback<UsersListPage> callback = new MockBaseCallback<>();

        users.list()
                .sortBy("A_SORT_FIELD", UserListRequest.SORT_DESC)
                .start(callback);

        assertThat(callback, hasPayloadOfType(UsersListPage.class));

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getHeader("Authorization"), equalTo("Bearer TOKEN"));
        assertThat(request.getMethod(), equalTo("GET"));
        assertThat(request.getPath(), equalTo("/api/v2/users?sort=A_SORT_FIELD:-1&include_totals=true&search_engine=v2"));
    }

    @Test
    public void shouldGetUsersWithSearch() throws Exception {
        mockAPI.willReturnUsers();

        final MockBaseCallback<UsersListPage> callback = new MockBaseCallback<>();

        users.list()
                .search("name:Nicolas")
                .start(callback);

        assertThat(callback, hasPayloadOfType(UsersListPage.class));

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getHeader("Authorization"), equalTo("Bearer TOKEN"));
        assertThat(request.getMethod(), equalTo("GET"));
        assertThat(request.getPath(), equalTo("/api/v2/users?q=name:Nicolas&include_totals=true&search_engine=v2"));
    }

    @Test
    public void shouldGetUsersOnlyIncludingSomeField() throws Exception {
        mockAPI.willReturnUsers();

        final MockBaseCallback<UsersListPage> callback = new MockBaseCallback<>();

        users.list()
                .onlyInclude("name", "email")
                .start(callback);

        assertThat(callback, hasPayloadOfType(UsersListPage.class));

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getHeader("Authorization"), equalTo("Bearer TOKEN"));
        assertThat(request.getMethod(), equalTo("GET"));
        assertThat(request.getPath(), equalTo("/api/v2/users?include_fields=true&include_totals=true&search_engine=v2&fields=name,email"));
    }

    @Test
    public void shouldGetUsersExcludingField() throws Exception {
        mockAPI.willReturnUsers();

        final MockBaseCallback<UsersListPage> callback = new MockBaseCallback<>();

        users.list()
                .exclude("logins_count")
                .start(callback);

        assertThat(callback, hasPayloadOfType(UsersListPage.class));

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getHeader("Authorization"), equalTo("Bearer TOKEN"));
        assertThat(request.getMethod(), equalTo("GET"));
        assertThat(request.getPath(), equalTo("/api/v2/users?include_fields=false&include_totals=true&search_engine=v2&fields=logins_count"));
    }

    @Test
    public void shouldGetUsersCustom() throws Exception {
        mockAPI.willReturnUsers();

        final MockBaseCallback<UsersListPage> callback = new MockBaseCallback<>();

        users.list()
                .search("email:somebody@example.com")
                .onlyInclude("name", "email")
                .perPage(2)
                .page(3)
                .sortBy("last_login", UserListRequest.SORT_DESC)
                .start(callback);

        assertThat(callback, hasPayloadOfType(UsersListPage.class));

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getHeader("Authorization"), equalTo("Bearer TOKEN"));
        assertThat(request.getMethod(), equalTo("GET"));
        assertThat(request.getPath(), equalTo("/api/v2/users?per_page=2&sort=last_login:-1&page=3&q=email:somebody@example.com&include_fields=true&include_totals=true&search_engine=v2&fields=name,email"));
    }

    private Map<String, Object> bodyFromRequest(RecordedRequest request) throws java.io.IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(request.getBody().inputStream());
        return new Gson().fromJson(inputStreamReader, new TypeToken<Map<String, Object>>() {}.getType());
    }

    private List<Map<String, Object>> getIdentitiesPayload() {
        Map<String, Object> profileData = new HashMap<>();
        profileData.put("email", "");
        profileData.put("email_verified", false);
        profileData.put("name", "");
        profileData.put("username", "johndoe");
        profileData.put("given_name", "");
        profileData.put("phone_number", "");
        profileData.put("phone_verified", false);
        profileData.put("family_name", "");

        Map<String, Object> twitterIdentity = new HashMap<>();
        twitterIdentity.put("connection", "twitter");
        twitterIdentity.put("user_id", "191919191919191");
        twitterIdentity.put("provider", "twitter");
        twitterIdentity.put("isSocial", false);
        twitterIdentity.put("profileData", profileData);

        Map<String, Object> facebookIdentity = new HashMap<>();
        facebookIdentity.put("connection", "facebook");
        facebookIdentity.put("user_id", "5757575757575757");
        facebookIdentity.put("provider", "facebook");
        facebookIdentity.put("isSocial", true);
        facebookIdentity.put("profileData", profileData);

        List<Map<String, Object>> payload = new ArrayList<>(2);
        payload.add(twitterIdentity);
        payload.add(facebookIdentity);
        return payload;
    }
}
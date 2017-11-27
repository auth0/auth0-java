package com.auth0.client.mgmt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.auth0.client.mgmt.builder.RequestBuilder;
import com.auth0.client.mgmt.filter.FieldsFilter;
import com.auth0.client.mgmt.filter.LogEventFilter;
import com.auth0.client.mgmt.filter.UserFilter;
import com.auth0.json.mgmt.guardian.Enrollment;
import com.auth0.json.mgmt.logevents.LogEventsPage;
import com.auth0.json.mgmt.users.Identity;
import com.auth0.json.mgmt.users.RecoveryCode;
import com.auth0.json.mgmt.users.User;
import com.auth0.json.mgmt.users.UsersPage;
import com.auth0.net.Request;
import com.auth0.utils.Asserts;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

/**
 * Class that provides an implementation of the Users methods of the Management API as defined in https://auth0.com/docs/api/management/v2#!/Users and https://auth0.com/docs/api/management/v2#!/Users_By_Email
 */
@SuppressWarnings("WeakerAccess")
public class UsersEntity {
    private final RequestBuilder requestBuilder;

    UsersEntity(OkHttpClient client, HttpUrl baseUrl, String apiToken) {
        requestBuilder = new RequestBuilder(client, baseUrl, apiToken);
    }

    /**
     * Request all the Users that match a given email. A token with scope read:users is needed.
     * If you want the identities.access_token property to be included, you will also need the scope read:user_idp_tokens.
     * See https://auth0.com/docs/api/management/v2#!/Users_By_Email/get_users_by_email
     *
     * @param email  the email of the users to look up.
     * @param filter the filter to use. Can be null.
     * @return a Request to execute.
     */
    public Request<List<User>> listByEmail(String email, FieldsFilter filter) {
        Asserts.assertNotNull(email, "email");

       return requestBuilder.get("api/v2/users-by-email")
                      .queryParameter("email", email)
                      .queryParameters(filter)
                      .request(new TypeReference<List<User>>() {
                      });
    }

    /**
     * Request all the Users. A token with scope read:users is needed.
     * If you want the identities.access_token property to be included, you will also need the scope read:user_idp_tokens.
     * See https://auth0.com/docs/api/management/v2#!/Users/get_users
     *
     * @param filter the filter to use. Can be null.
     * @return a Request to execute.
     */
    public Request<UsersPage> list(UserFilter filter) {

        return requestBuilder.get("api/v2/users")
                             .queryParameters(filter)
                             .request(new TypeReference<UsersPage>() {
                             });
    }

    /**
     * Request a User. A token with scope read:users is needed.
     * If you want the identities.access_token property to be included, you will also need the scope read:user_idp_tokens.
     * See https://auth0.com/docs/api/management/v2#!/Users/get_users_by_id
     *
     * @param userId the id of the user to retrieve.
     * @param filter the filter to use. Can be null.
     * @return a Request to execute.
     */
    public Request<User> get(String userId, UserFilter filter) {
        Asserts.assertNotNull(userId, "user id");

        return requestBuilder.get("api/v2/users", userId)
                             .queryParameters(filter)
                             .request(new TypeReference<User>() {
                             });
    }

    /**
     * Create a User. A token with scope create:users is needed.
     * See https://auth0.com/docs/api/management/v2#!/Users/post_users
     *
     * @param user the user data to set
     * @return a Request to execute.
     */
    public Request<User> create(User user) {
        Asserts.assertNotNull(user, "user");

        return requestBuilder.post("api/v2/users")
                             .body(user)
                             .request(new TypeReference<User>() {
                             });
    }

    /**
     * Delete an existing User. A token with scope delete:users is needed.
     * See https://auth0.com/docs/api/management/v2#!/Users/delete_users_by_id
     *
     * @param userId the user id
     * @return a Request to execute.
     */
    public Request delete(String userId) {
        Asserts.assertNotNull(userId, "user id");

        return requestBuilder.delete("api/v2/users", userId)
                             .request();
    }

    /**
     * Update an existing User. A token with scope update:users is needed. If you're updating app_metadata you'll also need update:users_app_metadata scope.
     * See https://auth0.com/docs/api/management/v2#!/Users/patch_users_by_id
     *
     * @param userId the user id
     * @param user   the user data to set. It can't include id.
     * @return a Request to execute.
     */
    public Request<User> update(String userId, User user) {
        Asserts.assertNotNull(userId, "user id");
        Asserts.assertNotNull(user, "user");

        return requestBuilder.patch("api/v2/users", userId)
                             .body(user)
                             .request(new TypeReference<User>() {
                             });
    }

    /**
     * Request all the Guardian Enrollments for a given User. A token with scope read:users is needed.
     * See https://auth0.com/docs/api/management/v2#!/Users/get_enrollments
     *
     * @param userId the id of the user to retrieve.
     * @return a Request to execute.
     */
    public Request<List<Enrollment>> getEnrollments(String userId) {
        Asserts.assertNotNull(userId, "user id");

        return requestBuilder.get("api/v2/users", userId, "enrollments")
                             .request(new TypeReference<List<Enrollment>>() {
                             });
    }

    /**
     * Request all the Events Log for a given User. A token with scope read:logs is needed.
     * See https://auth0.com/docs/api/management/v2#!/Users/get_logs_by_user
     *
     * @param userId the id of the user to retrieve.
     * @param filter the filter to use.
     * @return a Request to execute.
     */
    public Request<LogEventsPage> getLogEvents(String userId, LogEventFilter filter) {
        Asserts.assertNotNull(userId, "user id");

        return requestBuilder.get("api/v2/users", userId, "logs")
                             .queryParameters(filter)
                             .request(new TypeReference<LogEventsPage>() {
                             });
    }

    /**
     * Delete an existing User's Multifactor Provider. A token with scope update:users is needed.
     * See https://auth0.com/docs/api/management/v2#!/Users/delete_multifactor_by_provider
     *
     * @param userId   the user id
     * @param provider the multifactor provider
     * @return a Request to execute.
     */
    public Request deleteMultifactorProvider(String userId, String provider) {
        Asserts.assertNotNull(userId, "user id");
        Asserts.assertNotNull(provider, "provider");

        return requestBuilder.delete("api/v2/users", userId, "multifactor", provider)
                             .request();
    }

    /**
     * Rotates a User's Guardian recovery code. A token with scope update:users is needed.
     *
     * @param userId the user id
     * @return a Request to execute.
     * @see <a href="https://auth0.com/docs/api/management/v2#!/Users/post_recovery_code_regeneration">Management API2 docs</a>
     */
    public Request<RecoveryCode> rotateRecoveryCode(String userId) {
        Asserts.assertNotNull(userId, "user id");

        return requestBuilder.post("api/v2/users", userId, "recovery-code-regeneration")
                             .request(new TypeReference<RecoveryCode>() {
                             });
    }

    /**
     * Links two User's Identities. A token with scope update:users is needed.
     * See https://auth0.com/docs/api/management/v2#!/Users/post_identities
     *
     * @param primaryUserId   the primary identity's user id
     * @param secondaryUserId the secondary identity's user id
     * @param provider        the provider name of the secondary identity.
     * @param connectionId    the connection id of the secondary account being linked, useful if the provider is 'auth0' and you have several connections. Can be null.
     * @return a Request to execute.
     */
    public Request<List<Identity>> linkIdentity(String primaryUserId, String secondaryUserId, String provider, String connectionId) {
        Asserts.assertNotNull(primaryUserId, "primary user id");
        Asserts.assertNotNull(secondaryUserId, "secondary user id");
        Asserts.assertNotNull(provider, "provider");

        Map<String, String> body = new HashMap<>();
        body.put("provider", provider);
        body.put("user_id", secondaryUserId);
        if (connectionId != null) {
            body.put("connection_id", connectionId);
        }

        return requestBuilder.post("api/v2/users", primaryUserId, "identities")
                             .body(body)
                             .request(new TypeReference<List<Identity>>() {
                             });
    }

    /**
     * Un-links two User's Identities. A token with scope update:users is needed.
     * See https://auth0.com/docs/api/management/v2#!/Users/delete_provider_by_user_id
     *
     * @param primaryUserId   the primary identity's user id
     * @param secondaryUserId the secondary identity's user id
     * @param provider        the provider name of the secondary identity.
     * @return a Request to execute.
     */
    public Request<List<Identity>> unlinkIdentity(String primaryUserId, String secondaryUserId, String provider) {
        Asserts.assertNotNull(primaryUserId, "primary user id");
        Asserts.assertNotNull(secondaryUserId, "secondary user id");
        Asserts.assertNotNull(provider, "provider");

        return requestBuilder.delete("api/v2/users", primaryUserId, "identities", provider, secondaryUserId)
                             .request(new TypeReference<List<Identity>>() {
                             });
    }
}

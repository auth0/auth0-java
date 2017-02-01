package com.auth0.client.mgmt;

import com.auth0.client.mgmt.filter.LogEventFilter;
import com.auth0.client.mgmt.filter.UserFilter;
import com.auth0.json.mgmt.guardian.Enrollment;
import com.auth0.json.mgmt.logevents.LogEventsPage;
import com.auth0.json.mgmt.users.Identity;
import com.auth0.json.mgmt.users.RecoveryCode;
import com.auth0.json.mgmt.users.User;
import com.auth0.json.mgmt.users.UsersPage;
import com.auth0.net.CustomRequest;
import com.auth0.net.EmptyBodyRequest;
import com.auth0.net.Request;
import com.auth0.net.VoidRequest;
import com.auth0.utils.Asserts;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

import java.util.List;
import java.util.Map;

/**
 * Class that provides an implementation of the Users methods of the Management API as defined in https://auth0.com/docs/api/management/v2#!/Users
 */
@SuppressWarnings("WeakerAccess")
public class UsersEntity extends BaseManagementEntity {

    UsersEntity(OkHttpClient client, String baseUrl, String apiToken) {
        super(client, baseUrl, apiToken);
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
        HttpUrl.Builder builder = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("users");
        if (filter != null) {
            for (Map.Entry<String, Object> e : filter.getAsMap().entrySet()) {
                builder.addQueryParameter(e.getKey(), String.valueOf(e.getValue()));
            }
        }
        String url = builder.build().toString();
        CustomRequest<UsersPage> request = new CustomRequest<>(client, url, "GET", new TypeReference<UsersPage>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
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

        HttpUrl.Builder builder = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("users")
                .addPathSegment(userId);
        if (filter != null) {
            for (Map.Entry<String, Object> e : filter.getAsMap().entrySet()) {
                builder.addQueryParameter(e.getKey(), String.valueOf(e.getValue()));
            }
        }
        String url = builder.build().toString();
        CustomRequest<User> request = new CustomRequest<>(client, url, "GET", new TypeReference<User>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
    }

    /**
     * Create a new User. A token with scope create:users is needed.
     * See https://auth0.com/docs/api/management/v2#!/Users/post_users
     *
     * @param user the user data to set
     * @return a Request to execute.
     */
    public Request<User> create(User user) {
        Asserts.assertNotNull(user, "user");

        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("users")
                .build()
                .toString();
        CustomRequest<User> request = new CustomRequest<>(this.client, url, "POST", new TypeReference<User>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        request.setBody(user);
        return request;
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

        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("users")
                .addPathSegment(userId)
                .build()
                .toString();
        VoidRequest request = new VoidRequest(client, url, "DELETE");
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
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

        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("users")
                .addPathSegment(userId)
                .build()
                .toString();
        CustomRequest<User> request = new CustomRequest<>(this.client, url, "PATCH", new TypeReference<User>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        request.setBody(user);
        return request;
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

        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("users")
                .addPathSegment(userId)
                .addPathSegment("enrollments")
                .build()
                .toString();

        CustomRequest<List<Enrollment>> request = new CustomRequest<>(client, url, "GET", new TypeReference<List<Enrollment>>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
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

        HttpUrl.Builder builder = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("users")
                .addPathSegment(userId)
                .addPathSegment("logs");
        if (filter != null) {
            for (Map.Entry<String, Object> e : filter.getAsMap().entrySet()) {
                builder.addQueryParameter(e.getKey(), String.valueOf(e.getValue()));
            }
        }
        String url = builder.build().toString();
        CustomRequest<LogEventsPage> request = new CustomRequest<>(client, url, "GET", new TypeReference<LogEventsPage>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
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

        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("users")
                .addPathSegment(userId)
                .addPathSegment("multifactor")
                .addPathSegment(provider)
                .build()
                .toString();
        VoidRequest request = new VoidRequest(client, url, "DELETE");
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
    }

    /**
     * Rotates a User's Guardian Recovery Code. A token with scope update:users is needed.
     * <p/>
     * See https://auth0.com/docs/api/management/v2#!/Users/post_recovery_code_regeneration
     *
     * @param userId the user id
     * @return a Request to execute.
     */
    public Request<RecoveryCode> rotateRecoveryCode(String userId) {
        Asserts.assertNotNull(userId, "user id");

        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("users")
                .addPathSegment(userId)
                .addPathSegment("recovery-code-regeneration")
                .build()
                .toString();

        EmptyBodyRequest<RecoveryCode> request = new EmptyBodyRequest<>(client, url, "POST", new TypeReference<RecoveryCode>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
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

        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("users")
                .addPathSegment(primaryUserId)
                .addPathSegment("identities")
                .build()
                .toString();

        CustomRequest<List<Identity>> request = new CustomRequest<>(client, url, "POST", new TypeReference<List<Identity>>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        request.addParameter("provider", provider);
        request.addParameter("user_id", secondaryUserId);
        if (connectionId != null) {
            request.addParameter("connection_id", connectionId);
        }
        return request;
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

        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("users")
                .addPathSegment(primaryUserId)
                .addPathSegment("identities")
                .addPathSegment(provider)
                .addPathSegment(secondaryUserId)
                .build()
                .toString();

        CustomRequest<List<Identity>> request = new CustomRequest<>(client, url, "DELETE", new TypeReference<List<Identity>>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
    }

}

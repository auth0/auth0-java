package com.auth0.client.mgmt;

import com.auth0.client.mgmt.filter.*;
import com.auth0.json.mgmt.authenticationmethods.AuthenticationMethod;
import com.auth0.json.mgmt.authenticationmethods.AuthenticationMethodsPage;
import com.auth0.json.mgmt.permissions.Permission;
import com.auth0.json.mgmt.permissions.PermissionsPage;
import com.auth0.json.mgmt.roles.RolesPage;
import com.auth0.json.mgmt.guardian.Enrollment;
import com.auth0.json.mgmt.logevents.LogEventsPage;
import com.auth0.json.mgmt.organizations.OrganizationsPage;
import com.auth0.json.mgmt.users.Identity;
import com.auth0.json.mgmt.users.RecoveryCode;
import com.auth0.json.mgmt.users.User;
import com.auth0.json.mgmt.users.UsersPage;
import com.auth0.net.EmptyBodyRequest;
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

import static com.auth0.client.mgmt.filter.QueryFilter.KEY_QUERY;

/**
 * Class that provides an implementation of the Users methods of the Management API as defined in https://auth0.com/docs/api/management/v2#!/Users and https://auth0.com/docs/api/management/v2#!/Users_By_Email
 * <p>
 * This class is not thread-safe.
 *
 * @see ManagementAPI
 */
@SuppressWarnings("WeakerAccess")
public class UsersEntity extends BaseManagementEntity {

    UsersEntity(Auth0HttpClient client, HttpUrl baseUrl, TokenProvider tokenProvider) {
        super(client, baseUrl, tokenProvider);
    }

    /**
     * Request all the Users that match a given email.
     * A token with scope read:users is needed.
     * If you want the identities.access_token property to be included, you will also need the scope read:user_idp_tokens.
     * See https://auth0.com/docs/api/management/v2#!/Users_By_Email/get_users_by_email
     *
     * @param email  the email of the users to look up.
     * @param filter the filter to use. Can be null.
     * @return a Request to execute.
     */
    public Request<List<User>> listByEmail(String email, FieldsFilter filter) {
        Asserts.assertNotNull(email, "email");

        HttpUrl.Builder builder = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/users-by-email");
        builder.addQueryParameter("email", email);
        if (filter != null) {
            for (Map.Entry<String, Object> e : filter.getAsMap().entrySet()) {
                builder.addQueryParameter(e.getKey(), String.valueOf(e.getValue()));
            }
        }

        String url = builder.build().toString();
        return new BaseRequest<>(client, tokenProvider, url, HttpMethod.GET, new TypeReference<List<User>>() {
        });
    }

    /**
     * Request all the Users.
     * A token with scope read:users is needed.
     * If you want the identities.access_token property to be included, you will also need the scope read:user_idp_tokens.
     * See https://auth0.com/docs/api/management/v2#!/Users/get_users
     *
     * @param filter the filter to use. Can be null.
     * @return a Request to execute.
     */
    public Request<UsersPage> list(UserFilter filter) {
        HttpUrl.Builder builder = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/users");
        encodeAndAddQueryParam(builder, filter);
        String url = builder.build().toString();
        return new BaseRequest<>(client, tokenProvider, url, HttpMethod.GET, new TypeReference<UsersPage>() {
        });
    }

    /**
     * Request a User.
     * A token with scope read:users is needed.
     * If you want the identities.access_token property to be included, you will also need the scope read:user_idp_tokens.
     * See https://auth0.com/docs/api/management/v2#!/Users/get_users_by_id
     *
     * @param userId the id of the user to retrieve.
     * @param filter the filter to use. Can be null.
     * @return a Request to execute.
     */
    public Request<User> get(String userId, UserFilter filter) {
        Asserts.assertNotNull(userId, "user id");

        HttpUrl.Builder builder = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/users")
                .addPathSegment(userId);
        if (filter != null) {
            for (Map.Entry<String, Object> e : filter.getAsMap().entrySet()) {
                builder.addQueryParameter(e.getKey(), String.valueOf(e.getValue()));
            }
        }
        String url = builder.build().toString();
        return new BaseRequest<>(client, tokenProvider, url, HttpMethod.GET, new TypeReference<User>() {
        });
    }

    /**
     * Create a User.
     * A token with scope create:users is needed.
     * See https://auth0.com/docs/api/management/v2#!/Users/post_users
     *
     * @param user the user data to set
     * @return a Request to execute.
     */
    public Request<User> create(User user) {
        Asserts.assertNotNull(user, "user");

        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/users")
                .build()
                .toString();
        BaseRequest<User> request = new BaseRequest<>(this.client, tokenProvider, url, HttpMethod.POST, new TypeReference<User>() {
        });
        request.setBody(user);
        return request;
    }

    /**
     * Delete an existing User.
     * A token with scope delete:users is needed.
     * See https://auth0.com/docs/api/management/v2#!/Users/delete_users_by_id
     *
     * @param userId the user id
     * @return a Request to execute.
     */
    public Request<Void> delete(String userId) {
        Asserts.assertNotNull(userId, "user id");

        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/users")
                .addPathSegment(userId)
                .build()
                .toString();
        return new VoidRequest(client, tokenProvider, url, HttpMethod.DELETE);
    }

    /**
     * Update an existing User.
     * A token with scope update:users is needed.
     * If you're updating app_metadata you'll also need update:users_app_metadata scope.
     * See https://auth0.com/docs/api/management/v2#!/Users/patch_users_by_id
     *
     * @param userId the user id
     * @param user   the user data to set. It can't include id.
     * @return a Request to execute.
     */
    public Request<User> update(String userId, User user) {
        Asserts.assertNotNull(userId, "user id");
        Asserts.assertNotNull(user, "user");

        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/users")
                .addPathSegment(userId)
                .build()
                .toString();
        BaseRequest<User> request = new BaseRequest<>(this.client, tokenProvider, url, HttpMethod.PATCH, new TypeReference<User>() {
        });
        request.setBody(user);
        return request;
    }

    /**
     * Retreive the first confirmed enrollment, or a pending enrollment if none are confirmed.
     * A token with scope read:users is needed.
     * See https://auth0.com/docs/api/management/v2#!/Users/get_enrollments
     *
     * @param userId the id of the user to retrieve.
     * @return a Request to execute.
     */
    public Request<List<Enrollment>> getEnrollments(String userId) {
        Asserts.assertNotNull(userId, "user id");

        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/users")
                .addPathSegment(userId)
                .addPathSegment("enrollments")
                .build()
                .toString();

        return new BaseRequest<>(client, tokenProvider, url, HttpMethod.GET, new TypeReference<List<Enrollment>>() {
        });
    }

    /**
     * Request all the Events Log for a given User.
     * A token with scope read:logs is needed.
     * See https://auth0.com/docs/api/management/v2#!/Users/get_logs_by_user
     *
     * @param userId the id of the user to retrieve.
     * @param filter the filter to use.
     * @return a Request to execute.
     */
    public Request<LogEventsPage> getLogEvents(String userId, LogEventFilter filter) {
        Asserts.assertNotNull(userId, "user id");

        HttpUrl.Builder builder = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/users")
                .addPathSegment(userId)
                .addPathSegment("logs");

        encodeAndAddQueryParam(builder, filter);
        String url = builder.build().toString();
        return new BaseRequest<>(client, tokenProvider, url, HttpMethod.GET, new TypeReference<LogEventsPage>() {
        });
    }

    /**
     * Delete all an user's authenticators.
     * A token with scope delete:guardian_enrollments is needed.
     *
     * See <a href="https://auth0.com/docs/api/management/v2#!/Users/delete_authenticators">API docs</a>
     *
     * @param userId   the user id
     * @return a Request to execute.
     */
    public Request<Void> deleteAllAuthenticators(String userId) {
        Asserts.assertNotNull(userId, "user id");

        return voidRequest(HttpMethod.DELETE, builder -> builder.withPathSegments(String.format("api/v2/users/%s/authenticators", userId)));
    }
    
    /**
     * Delete an existing User's Multifactor Provider.
     * A token with scope update:users is needed.
     * See https://auth0.com/docs/api/management/v2#!/Users/delete_multifactor_by_provider
     *
     * @param userId   the user id
     * @param provider the multifactor provider
     * @return a Request to execute.
     */
    public Request<Void> deleteMultifactorProvider(String userId, String provider) {
        Asserts.assertNotNull(userId, "user id");
        Asserts.assertNotNull(provider, "provider");

        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/users")
                .addPathSegment(userId)
                .addPathSegment("multifactor")
                .addPathSegment(provider)
                .build()
                .toString();
        return new VoidRequest(client, tokenProvider, url, HttpMethod.DELETE);
    }

    /**
     * Rotates a User's Guardian recovery code.
     * A token with scope update:users is needed.
     *
     * @param userId the user id
     * @return a Request to execute.
     * @see <a href="https://auth0.com/docs/api/management/v2#!/Users/post_recovery_code_regeneration">Management API2 docs</a>
     */
    public Request<RecoveryCode> rotateRecoveryCode(String userId) {
        Asserts.assertNotNull(userId, "user id");

        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/users")
                .addPathSegment(userId)
                .addPathSegment("recovery-code-regeneration")
                .build()
                .toString();

        return new EmptyBodyRequest<>(client, tokenProvider, url, HttpMethod.POST, new TypeReference<RecoveryCode>() {
        });
    }

    /**
     * Links two User's Identities.
     * A token with scope update:users is needed.
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

        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/users")
                .addPathSegment(primaryUserId)
                .addPathSegment("identities")
                .build()
                .toString();

        BaseRequest<List<Identity>> request =  new BaseRequest<>(client, tokenProvider, url, HttpMethod.POST, new TypeReference<List<Identity>>() {
        });
        request.addParameter("provider", provider);
        request.addParameter("user_id", secondaryUserId);
        if (connectionId != null) {
            request.addParameter("connection_id", connectionId);
        }
        return request;
    }

    /**
     * A token with scope update:current_user_identities is needed.
     * It only works for the user the access token represents.
     * See https://auth0.com/docs/api/management/v2#!/Users/post_identities
     *
     * @param primaryUserId the primary identity's user id associated with the access token this client was configured with.
     * @param secondaryIdToken the user ID token representing the identity to link with the current user
     * @return a Request to execute.
     */
    public Request<List<Identity>> linkIdentity(String primaryUserId, String secondaryIdToken) {
        Asserts.assertNotNull(primaryUserId, "primary user id");
        Asserts.assertNotNull(secondaryIdToken, "secondary id token");

        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/users")
                .addPathSegment(primaryUserId)
                .addPathSegment("identities")
                .build()
                .toString();

        BaseRequest<List<Identity>> request =  new BaseRequest<>(client, tokenProvider, url, HttpMethod.POST, new TypeReference<List<Identity>>() {
        });
        request.addParameter("link_with", secondaryIdToken);

        return request;
    }

    /**
     * Un-links two User's Identities.
     * A token with scope update:users is needed.
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

        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/users")
                .addPathSegment(primaryUserId)
                .addPathSegment("identities")
                .addPathSegment(provider)
                .addPathSegment(secondaryUserId)
                .build()
                .toString();

        return new BaseRequest<>(client, tokenProvider, url, HttpMethod.DELETE, new TypeReference<List<Identity>>() {
        });
    }

    /**
     * Get the permissions associated to the user.
     * A token with read:users is needed.
     * See https://auth0.com/docs/api/management/v2#!/Users/get_permissions
     *
     * @param userId the role id
     * @param filter an optional pagination filter
     * @return a Request to execute
     */
    public Request<PermissionsPage> listPermissions(String userId, PageFilter filter) {
        Asserts.assertNotNull(userId, "user id");
        HttpUrl.Builder builder = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/users")
                .addPathSegments(userId)
                .addPathSegments("permissions");
        if (filter != null) {
            for (Map.Entry<String, Object> e : filter.getAsMap().entrySet()) {
                builder.addQueryParameter(e.getKey(), String.valueOf(e.getValue()));
            }
        }
        String url = builder.build().toString();
        return new BaseRequest<>(client, tokenProvider, url, HttpMethod.GET, new TypeReference<PermissionsPage>() {
        });
    }


    /**
     * Remove permissions from a user.
     * A token with update:users is needed.
     * See https://auth0.com/docs/api/management/v2#!/Users/delete_permissions
     *
     * @param userId      the user id
     * @param permissions a list of permission objects to remove from the user
     * @return a Request to execute
     */
    public Request<Void> removePermissions(String userId, List<Permission> permissions) {
        Asserts.assertNotNull(userId, "user id");
        Asserts.assertNotEmpty(permissions, "permissions");

        Map<String, List<Permission>> body = new HashMap<>();
        body.put("permissions", permissions);

        final String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/users")
                .addPathSegments(userId)
                .addPathSegments("permissions")
                .build()
                .toString();
        VoidRequest request = new VoidRequest(this.client, tokenProvider, url, HttpMethod.DELETE);
        request.setBody(body);
        return request;
    }

    /**
     * Assign permissions to a user.
     * A token with update:users is needed.
     * See https://auth0.com/docs/api/management/v2#!/Users/post_permissions
     *
     * @param userId      the user id
     * @param permissions a list of permission objects to assign to the user
     * @return a Request to execute
     */
    public Request<Void> addPermissions(String userId, List<Permission> permissions) {
        Asserts.assertNotNull(userId, "user id");
        Asserts.assertNotEmpty(permissions, "permissions");

        Map<String, List<Permission>> body = new HashMap<>();
        body.put("permissions", permissions);

        final String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/users")
                .addPathSegments(userId)
                .addPathSegments("permissions")
                .build()
                .toString();
        VoidRequest request = new VoidRequest(this.client, tokenProvider, url, HttpMethod.POST);
        request.setBody(body);
        return request;
    }

    /**
     * Get the roles associated with a user.
     * A token with read:users and read:roles is needed.
     * See https://auth0.com/docs/api/management/v2#!/Users/get_user_roles
     *
     * @param userId the role id
     * @param filter an optional pagination filter
     * @return a Request to execute
     */
    public Request<RolesPage> listRoles(String userId, PageFilter filter) {
        Asserts.assertNotNull(userId, "user id");
        HttpUrl.Builder builder = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/users")
                .addPathSegments(userId)
                .addPathSegments("roles");
        if (filter != null) {
            for (Map.Entry<String, Object> e : filter.getAsMap().entrySet()) {
                builder.addQueryParameter(e.getKey(), String.valueOf(e.getValue()));
            }
        }
        String url = builder.build().toString();
        return new BaseRequest<>(client, tokenProvider, url, HttpMethod.GET, new TypeReference<RolesPage>() {
        });
    }


    /**
     * Remove roles from a user.
     * A token with update:users is needed.
     * See https://auth0.com/docs/api/management/v2#!/Users/delete_user_roles
     *
     * @param userId  the user id
     * @param roleIds a list of role ids to remove from the user
     * @return a Request to execute
     */
    public Request<Void> removeRoles(String userId, List<String> roleIds) {
        Asserts.assertNotNull(userId, "user id");
        Asserts.assertNotEmpty(roleIds, "role ids");

        Map<String, List<String>> body = new HashMap<>();
        body.put("roles", roleIds);

        final String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/users")
                .addPathSegments(userId)
                .addPathSegments("roles")
                .build()
                .toString();
        VoidRequest request = new VoidRequest(this.client, tokenProvider, url, HttpMethod.DELETE);
        request.setBody(body);
        return request;
    }

    /**
     * Assign roles to a user.
     * A token with update:users is needed.
     * See https://auth0.com/docs/api/management/v2#!/Users/post_user_roles
     *
     * @param userId  the user id
     * @param roleIds a list of role ids to assign to the user
     * @return a Request to execute
     */
    public Request<Void> addRoles(String userId, List<String> roleIds) {
        Asserts.assertNotNull(userId, "user id");
        Asserts.assertNotEmpty(roleIds, "role ids");

        Map<String, List<String>> body = new HashMap<>();
        body.put("roles", roleIds);

        final String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/users")
                .addPathSegments(userId)
                .addPathSegments("roles")
                .build()
                .toString();
        VoidRequest request = new VoidRequest(this.client, tokenProvider, url, HttpMethod.POST);
        request.setBody(body);
        return request;
    }

    /**
     * Get the organizations a user belongs to.
     * A token with {@code read:users} and {@code read:organizations} is required.
     *
     * @param userId the user ID
     * @param filter an optional pagination filter
     * @return a Request to execute
     *
     * @see <a href="https://auth0.com/docs/api/management/v2#!/Users/get_organizations">https://auth0.com/docs/api/management/v2#!/Users/get_organizations</a>
     */
    public Request<OrganizationsPage> getOrganizations(String userId, PageFilter filter) {
        Asserts.assertNotNull(userId, "user ID");

        HttpUrl.Builder builder = baseUrl
            .newBuilder()
            .addPathSegments("api/v2/users")
            .addPathSegment(userId)
            .addPathSegment("organizations");

        if (filter != null) {
            for (Map.Entry<String, Object> e : filter.getAsMap().entrySet()) {
                builder.addQueryParameter(e.getKey(), String.valueOf(e.getValue()));
            }
        }
        String url = builder.build().toString();
        return new BaseRequest<>(client, tokenProvider, url, HttpMethod.GET, new TypeReference<OrganizationsPage>() {
        });
    }

    /**
     * Get the authentication methods of the user.
     * A token with {@code read:authentication_methods} is required.
     *
     * @param userId the user ID
     * @param filter an optional pagination filter
     * @return a Request to execute
     *
     * @see <a href="https://auth0.com/docs/api/management/v2#!/Users/get_authentication_methods">https://auth0.com/docs/api/management/v2#!/Users/get_authentication_methods</a>
     */
    public Request<AuthenticationMethodsPage> getAuthenticationMethods(String userId, PageFilter filter) {
        Asserts.assertNotNull(userId, "user ID");

        HttpUrl.Builder builder = baseUrl
            .newBuilder()
            .addPathSegments("api/v2/users")
            .addPathSegment(userId)
            .addPathSegment("authentication-methods");

        if (filter != null) {
            for (Map.Entry<String, Object> e : filter.getAsMap().entrySet()) {
                builder.addQueryParameter(e.getKey(), String.valueOf(e.getValue()));
            }
        }
        String url = builder.build().toString();
        return new BaseRequest<>(client, tokenProvider, url, HttpMethod.GET, new TypeReference<AuthenticationMethodsPage>() {
        });
    }

    /**
     * Create an authentication method for a given user.
     * A token with scope {@code create:authentication_methods} is needed.
     * @see <a href="https://auth0.com/docs/api/management/v2#!/Users/post_authentication_methods">https://auth0.com/docs/api/management/v2#!/Users/post_authentication_methods</a>
     *
     * @param userId the user to add authentication method to
     * @param authenticationMethod the authentication method to be created
     * @return a Request to execute.
     */
    public Request<AuthenticationMethod> createAuthenticationMethods(String userId, AuthenticationMethod authenticationMethod) {
        Asserts.assertNotNull(userId, "user ID");
        Asserts.assertNotNull(authenticationMethod, "authentication methods");

        String url = baseUrl
            .newBuilder()
            .addPathSegments("api/v2/users")
            .addPathSegment(userId)
            .addPathSegment("authentication-methods")
            .build()
            .toString();
        BaseRequest<AuthenticationMethod> request = new BaseRequest<>(this.client, tokenProvider, url, HttpMethod.POST, new TypeReference<AuthenticationMethod>() {
        });
        request.setBody(authenticationMethod);
        return request;
    }

    /**
     * Updates an authentication method for a given user.
     * A token with scope {@code update:authentication_methods} is needed.
     * @see <a href="https://auth0.com/docs/api/management/v2#!/Users/put_authentication_methods">https://auth0.com/docs/api/management/v2#!/Users/put_authentication_methods</a>
     *
     * @param userId the user to update authentication method
     * @param authenticationMethods the list of authentication method information to be updated
     * @return a Request to execute.
     */
    public Request<List<AuthenticationMethod>> updateAuthenticationMethods(String userId, List<AuthenticationMethod> authenticationMethods) {
        Asserts.assertNotNull(userId, "user ID");
        Asserts.assertNotNull(authenticationMethods, "authentication methods");

        String url = baseUrl
            .newBuilder()
            .addPathSegments("api/v2/users")
            .addPathSegment(userId)
            .addPathSegment("authentication-methods")
            .build()
            .toString();
        BaseRequest<List<AuthenticationMethod>> request = new BaseRequest<>(this.client, tokenProvider, url, HttpMethod.PUT, new TypeReference<List<AuthenticationMethod>>() {
        });
        request.setBody(authenticationMethods);
        return request;
    }

    /**
     * Gets an authentication method by ID.
     * A token with scope {@code read:authentication_methods} is needed.
     * @see <a href="https://auth0.com/docs/api/management/v2#!/Users/get_authentication_methods_by_authentication_method_id">https://auth0.com/docs/api/management/v2#!/Users/get_authentication_methods_by_authentication_method_id</a>
     *
     * @param userId the user to get authentication method for
     * @param authenticationMethodId the authentication method to be fetched
     * @return a Request to execute.
     */
    public Request<AuthenticationMethod> getAuthenticationMethodById(String userId, String authenticationMethodId) {
        Asserts.assertNotNull(userId, "user ID");
        Asserts.assertNotNull(userId, "authentication method ID");

        String url = baseUrl
            .newBuilder()
            .addPathSegments("api/v2/users")
            .addPathSegment(userId)
            .addPathSegment("authentication-methods")
            .addPathSegment(authenticationMethodId)
            .build()
            .toString();

        return new BaseRequest<>(this.client, tokenProvider, url, HttpMethod.GET, new TypeReference<AuthenticationMethod>() {
        });
    }

    /**
     * Deletes an authentication method by ID.
     * A token with scope {@code delete:authentication_methods} is needed.
     * @see <a href="https://auth0.com/docs/api/management/v2#!/Users/delete_authentication_methods_by_authentication_method_id">https://auth0.com/docs/api/management/v2#!/Users/delete_authentication_methods_by_authentication_method_id</a>
     *
     * @param userId the user to delete the authentication method for
     * @param authenticationMethodId the authentication method to be deleted
     * @return a Request to execute.
     */
    public Request<Void> deleteAuthenticationMethodById(String userId, String authenticationMethodId) {
        Asserts.assertNotNull(userId, "user ID");
        Asserts.assertNotNull(authenticationMethodId, "authentication method ID");

        String url = baseUrl
            .newBuilder()
            .addPathSegments("api/v2/users")
            .addPathSegment(userId)
            .addPathSegment("authentication-methods")
            .addPathSegment(authenticationMethodId)
            .build()
            .toString();

        return new BaseRequest<>(this.client, tokenProvider, url, HttpMethod.DELETE, new TypeReference<Void>() {
        });
    }

    /**
     * Updates an authentication method.
     * A token with scope {@code update:authentication_methods} is needed.
     * @see <a href="https://auth0.com/docs/api/management/v2#!/Users/patch_authentication_methods_by_authentication_method_id">https://auth0.com/docs/api/management/v2#!/Users/patch_authentication_methods_by_authentication_method_id</a>
     *
     * @param userId the user to delete the authentication method for
     * @param authenticationMethodId the authentication method to be deleted
     * @param authenticationMethod the information to be updated
     * @return a Request to execute.
     */
    public Request<AuthenticationMethod> updateAuthenticationMethodById(String userId, String authenticationMethodId, AuthenticationMethod authenticationMethod) {
        Asserts.assertNotNull(userId, "user ID");
        Asserts.assertNotNull(authenticationMethodId, "authentication method ID");
        Asserts.assertNotNull(authenticationMethod, "authentication method");

        String url = baseUrl
            .newBuilder()
            .addPathSegments("api/v2/users")
            .addPathSegment(userId)
            .addPathSegment("authentication-methods")
            .addPathSegment(authenticationMethodId)
            .build()
            .toString();

        BaseRequest<AuthenticationMethod> request = new BaseRequest<>(this.client, tokenProvider, url, HttpMethod.PATCH, new TypeReference<AuthenticationMethod>() {
        });
        request.setBody(authenticationMethod);
        return request;
    }

    private static void encodeAndAddQueryParam(HttpUrl.Builder builder, BaseFilter filter) {
        if (filter != null) {
            for (Map.Entry<String, Object> e : filter.getAsMap().entrySet()) {
                if (KEY_QUERY.equals(e.getKey())) {
                    builder.addEncodedQueryParameter(e.getKey(), String.valueOf(e.getValue()));
                } else {
                    builder.addQueryParameter(e.getKey(), String.valueOf(e.getValue()));
                }
            }
        }
    }
}

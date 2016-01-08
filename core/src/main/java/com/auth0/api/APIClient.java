package com.auth0.api;

import com.auth0.api.authentication.AuthenticationAPIClient;
import com.auth0.api.callback.AuthenticationCallback;
import com.auth0.api.callback.BaseCallback;
import com.auth0.api.callback.RefreshIdTokenCallback;
import com.auth0.core.Application;
import com.auth0.core.Auth0;
import com.auth0.core.Connection;
import com.auth0.core.DatabaseUser;
import com.auth0.core.Strategy;
import com.auth0.core.UserProfile;

import java.util.HashMap;
import java.util.Map;

/**
 * API client for Auth0 Authentication API.
 * @see <a href="https://auth0.com/docs/auth-api">Auth API docs</a>
 * @deprecated Use {@link AuthenticationAPIClient}
 */
@Deprecated
@SuppressWarnings("all")
public class APIClient extends BaseAPIClient {

    private static final String DEFAULT_DB_CONNECTION = "Username-Password-Authentication";

    private Application application;
    private AuthenticationAPIClient newClient;

    /**
     * Creates a new API client instance providing Auth API and Configuration Urls different than the default. (Useful for on premise deploys).
     * @param clientID Your application clientID.
     * @param baseURL Auth0's auth API endpoint
     * @param configurationURL Auth0's enpoint where App info can be retrieved.
     * @param tenantName Name of the tenant. Can be null
     */
    public APIClient(String clientID, String baseURL, String configurationURL, String tenantName) {
        super(clientID, baseURL, configurationURL, tenantName);
        this.newClient = new AuthenticationAPIClient(new Auth0(clientID, baseURL, configurationURL));
    }

    /**
     * Creates a new API client instance providing Auth API and Configuration Urls different than the default. (Useful for on premise deploys).
     * @param clientID Your application clientID.
     * @param baseURL Auth0's auth API endpoint
     * @param configurationURL Auth0's enpoint where App info can be retrieved.
     */
    public APIClient(String clientID, String baseURL, String configurationURL) {
        this(clientID, baseURL, configurationURL, null);
    }

    /**
     * Creates a new API client using clientID and tenant name.
     * @param clientID Your application clientID.
     * @param tenantName Name of the tenant.
     * @deprecated since 1.7.0, instead build using clientID and baseURL
     */
    public APIClient(String clientID, String tenantName) {
        super(clientID, tenantName);
        this.newClient = new AuthenticationAPIClient(new Auth0(clientID, getBaseURL()));
    }

    /**
     * Fetch application information from {@link #getConfigurationURL()}
     * @param callback called with the application info on success or with the failure reason.
     */
    public void fetchApplicationInfo(final BaseCallback<Application> callback) {
        newClient.fetchApplicationInfo()
                .start(new BaseCallback<Application>() {
                    @Override
                    public void onSuccess(Application payload) {
                        callback.onSuccess(payload);
                        application = payload;
                    }

                    @Override
                    public void onFailure(Throwable error) {
                        callback.onFailure(error);
                    }
                });
    }

    /**
     * Performs a Database connection login with username/email and password.
     * @param username email or username required to login. By default it should be an email
     * @param password user's password
     * @param parameters additional parameters sent to the API like 'scope'
     * @param callback called with User's profile and tokens or failure reason
     */
    public void login(final String username, String password, Map<String, Object> parameters, final AuthenticationCallback callback) {
        Map<String, Object> request = com.auth0.api.ParameterBuilder.newBuilder()
                .setClientId(getClientID())
                .setConnection(getDBConnectionName())
                .addAll(parameters)
                .asDictionary();

        newClient
                .login(username, password)
                .addParameters(request)
                .start(callback);
    }

    /**
     * Performs a Social connection login using Identity Provider (IdP) credentials.
     * @param connectionName name of the social connection
     * @param accessToken accessToken from the IdP.
     * @param parameters additional parameters sent to the API like 'scope'
     * @param callback called with User's profile and tokens or failure reason
     */
    public void socialLogin(final String connectionName, String accessToken, Map<String, Object> parameters, final AuthenticationCallback callback) {
        Map<String, Object> params = parameters != null ? new HashMap<>(parameters) : new HashMap<String, Object>();
        if (params.containsKey("access_token")) {
            params.put("main_access_token", params.remove("access_token"));
        }
        Map<String, Object> request = com.auth0.api.ParameterBuilder.newBuilder()
                .addAll(params)
                .asDictionary();

        newClient
                .loginWithOAuthAccessToken(accessToken, connectionName)
                .addParameters(request)
                .start(callback);
    }

    /**
     * Performs a SMS connection login with a phone number and verification code.
     * @param phoneNumber number where the verificationCode was received.
     * @param verificationCode received by SMS.
     * @param parameters additional parameters sent to the API like 'scope'
     * @param callback called with User's profile and tokens or failure reason
     */
    public void smsLogin(String phoneNumber, String verificationCode, Map<String, Object> parameters, final AuthenticationCallback callback) {
        newClient.loginWithPhoneNumber(phoneNumber, verificationCode)
                .addParameters(parameters)
                .start(callback);
    }

    /**
     * Performs an Email connection login with an email and verification code.
     * @param email where the user received the verificationCode
     * @param verificationCode sent by email
     * @param parameters to be sent for authentication in the request, useful to add extra values to Auth0 or override defaults
     * @param callback called with user's profile and tokens, or failure reason
     */
    public void emailLogin(String email, String verificationCode, Map<String, Object> parameters, final AuthenticationCallback callback) {
        newClient.loginWithEmail(email, verificationCode)
                .addParameters(parameters)
                .start(callback);
    }

    /**
     * Creates and logs in a new User using a Database connection.
     * @param email new user email
     * @param username new user username
     * @param password new user password
     * @param parameters additional parameters additional parameters sent to the API like 'scope'
     * @param callback called with User's profile and tokens or failure reason
     */
    public void signUp(final String email, final String username, final String password, final Map<String, Object> parameters, final AuthenticationCallback callback) {
        Map<String, Object> request = com.auth0.api.ParameterBuilder.newBuilder()
                .setConnection(getDBConnectionName())
                .addAll(parameters)
                .asDictionary();
        newClient.signUp(email, password, username)
                .addSignUpParameters(request)
                .addAuthenticationParameters(request)
                .start(callback);
    }

    /**
     * Creates and logs in a new User using a Database connection.
     * @param email new user email
     * @param password new user password
     * @param parameters additional parameters additional parameters sent to the API like 'scope'
     * @param callback called with User's profile and tokens or failure reason
     */
    public void signUp(final String email, final String password, final Map<String, Object> parameters, final AuthenticationCallback callback) {
        Map<String, Object> request = com.auth0.api.ParameterBuilder.newBuilder()
                .setConnection(getDBConnectionName())
                .addAll(parameters)
                .asDictionary();
        newClient.signUp(email, password)
                .addSignUpParameters(request)
                .addAuthenticationParameters(parameters)
                .start(callback);
    }

    /**
     * Creates a new user using a Database connection
     * @param email new user email
     * @param username new user username
     * @param password new user password
     * @param parameters additional parameters additional parameters sent to the API like 'scope'
     * @param callback callback that will notify if the user was successfully created or not.
     */
    public void createUser(final String email, final String username, final String password, final Map<String, Object> parameters, final BaseCallback<Void> callback) {
        Map<String, Object> request = com.auth0.api.ParameterBuilder.newBuilder()
                .setConnection(getDBConnectionName())
                .addAll(parameters)
                .asDictionary();
        newClient.createUser(email, password, username)
                .addParameters(request)
                .start(new BaseCallback<DatabaseUser>() {
                    @Override
                    public void onSuccess(DatabaseUser payload) {
                        callback.onSuccess(null);
                    }

                    @Override
                    public void onFailure(Throwable error) {
                        callback.onFailure(error);
                    }
                });
    }

    /**
     * Creates a new user using a Database connection
     * @param email new user email
     * @param password new user password
     * @param parameters additional parameters additional parameters sent to the API like 'scope'
     * @param callback callback that will notify if the user was successfully created or not.
     */
    public void createUser(final String email, final String password, final Map<String, Object> parameters, final BaseCallback<Void> callback) {
        Map<String, Object> request = com.auth0.api.ParameterBuilder.newBuilder()
                .setConnection(getDBConnectionName())
                .addAll(parameters)
                .asDictionary();
        newClient.createUser(email, password)
                .addParameters(request)
                .start(new BaseCallback<DatabaseUser>() {
                    @Override
                    public void onSuccess(DatabaseUser payload) {
                        callback.onSuccess(null);
                    }

                    @Override
                    public void onFailure(Throwable error) {
                        callback.onFailure(error);
                    }
                });
    }

    /**
     * Request a change for the user's password using a Database connection.
     * @param email user's email
     * @param newPassword new password for the user
     * @param parameters additional parameters additional parameters sent to the API like 'scope'
     * @param callback callback that will notify if the user password request was sent or not.
     */
    public void changePassword(final String email, String newPassword, Map<String, Object> parameters, BaseCallback<Void> callback) {
        Map<String, Object> request = ParameterBuilder.newBuilder()
                .setConnection(getDBConnectionName())
                .addAll(parameters)
                .asDictionary();
        newClient.changePassword(email, newPassword)
                .addParameters(request)
                .start(callback);
    }

    /**
     * Fetches the user's profile with a valid 'id_token'
     * @param idToken a JWT token associated to the user.
     * @param callback called with the user's profile on success or with the failure reason
     */
    public void fetchUserProfile(String idToken, final BaseCallback<UserProfile> callback) {
        newClient.tokenInfo(idToken)
                .start(callback);
    }

    /**
     * Obtains a new id_token from Auth0 using another valid id_token
     * @param idToken user's id_token
     * @param parameters delegation api parameters
     * @param callback called with new token in success or with the failure reason on error
     */
    public void fetchIdTokenWithIdToken(String idToken, Map<String, Object> parameters, final RefreshIdTokenCallback callback) {
        newClient.delegationWithIdToken(idToken)
                .addParameters(parameters)
                .start(callback);
    }

    /**
     * Obtains a new id_token from Auth0 using a refresh_token obtained on login when the scope has 'offline_access'
     * @param refreshToken user's refresh token
     * @param parameters delegation api parameters
     * @param callback called with new token in success or with the failure reason on error
     */
    public void fetchIdTokenWithRefreshToken(String refreshToken, Map<String, Object> parameters, final RefreshIdTokenCallback callback) {
        newClient.delegationWithRefreshToken(refreshToken)
                .addParameters(parameters)
                .start(callback);
    }

    /**
     * Calls Auth0 delegation API to perform a delegated authentication, e.g. fetch Firebase or AWS tokens to call their API.
     * The response of this API call will return a different response based on 'api_type' parameters, that's why the callback only returns a {@link Map}.
     * @param parameters delegation api parameters
     * @param callback called with delegation api response in success or with the failure reason on error.
     */
    public void fetchDelegationToken(Map<String, Object> parameters, final BaseCallback<Map<String, Object>> callback) {
        newClient.delegation()
                .addParameters(parameters)
                .start(callback);
    }

    /**
     * Remove an account from another accounts identities.
     * @param userId Id of the user account to remove, e.g.: if its a facebook account it will be 'facebook|fb_user_id'.
     * @param accessToken Access token of the account that owns the account to unlink
     * @param callback to call on either success or failure.
     */
    public void unlinkAccount(String userId, String accessToken, final BaseCallback<Void> callback) {
        newClient.unlink(userId, accessToken)
                .start(callback);
    }

    /**
     * Start passwordless authentication flow calling "/passwordless/start" API.
     * @param parameters sent to API to start the flow
     * @param callback to call on either success or failure
     */
    public void startPasswordless(Map<String, Object> parameters, final BaseCallback<Void> callback) {
        newClient.passwordless()
                .addParameters(parameters)
                .start(callback);
    }

    /**
     * Request a verification code to login to be sent via SMS
     * @param phoneNumber where the code is sent
     * @param callback to call on either success or failure
     */
    public void requestSMSVerificationCode(String phoneNumber, final BaseCallback<Void> callback) {
        newClient.passwordlessWithSMS(phoneNumber, false)
                .start(callback);
    }

    /**
     * Request a verification code to login to be sent via email
     * @param email where the code is sent
     * @param callback to call on either success or failure
     */
    public void requestEmailVerificationCode(String email, final BaseCallback<Void> callback) {
        newClient.passwordlessWithEmail(email, false)
                .start(callback);
    }

    /**
     * Starts passwordless authentication with SMS, this will send a One Time Password to the user's phone via SMS
     * @param phoneNumber to where the SMS one time password will be sent
     * @param callback to call on either success or failure
     * @deprecated in favor of generic {@link #startPasswordless(Map, BaseCallback)} or more specific ones {@link #requestSMSVerificationCode(String, BaseCallback)} and {@link #requestEmailVerificationCode(String, BaseCallback)}
     */
    public void startPasswordless(String phoneNumber, final BaseCallback<Void> callback) {
        requestSMSVerificationCode(phoneNumber, callback);
    }

    private String getDBConnectionName() {
        if (this.application == null || this.application.getDatabaseStrategy() == null) {
            return DEFAULT_DB_CONNECTION;
        }
        Strategy strategy = this.application.getDatabaseStrategy();
        Connection db = strategy.getConnections().get(0);
        return db.getName();
    }

}

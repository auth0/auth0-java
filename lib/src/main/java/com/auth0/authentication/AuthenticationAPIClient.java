/*
 * AuthenticationAPIClient.java
 *
 * Copyright (c) 2015 Auth0 (http://auth0.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.auth0.authentication;

import com.auth0.Auth0;
import com.auth0.authentication.result.Credentials;
import com.auth0.authentication.result.DatabaseUser;
import com.auth0.authentication.result.Delegation;
import com.auth0.authentication.result.UserProfile;
import com.auth0.request.internal.RequestFactory;
import com.auth0.request.AuthenticationRequest;
import com.auth0.request.ParameterizableRequest;
import com.auth0.request.Request;
import com.auth0.util.Telemetry;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;

import java.util.Map;

import static com.auth0.authentication.ParameterBuilder.GRANT_TYPE_PASSWORD;

/**
 * API client for Auth0 Authentication API.
 *
 * @see <a href="https://auth0.com/docs/auth-api">Auth API docs</a>
 */
public class AuthenticationAPIClient {

    private static final String DEFAULT_DB_CONNECTION = "Username-Password-Authentication";
    private static final String SMS_CONNECTION = "sms";
    private static final String EMAIL_CONNECTION = "email";
    private static final String USERNAME_KEY = "username";
    private static final String PASSWORD_KEY = "password";
    private static final String EMAIL_KEY = "email";
    private static final String PHONE_NUMBER_KEY = "phone_number";
    private static final String USER_ID_KEY = "user_id";
    private static final String CLIENT_ID_KEY = "clientID";
    private static final String DELEGATION_PATH = "delegation";
    private static final String ACCESS_TOKEN_PATH = "access_token";
    private static final String SIGN_UP_PATH = "signup";
    private static final String DB_CONNECTIONS_PATH = "dbconnections";
    private static final String CHANGE_PASSWORD_PATH = "change_password";
    private static final String UNLINK_PATH = "unlink";
    private static final String PASSWORDLESS_PATH = "passwordless";
    private static final String START_PATH = "start";
    private static final String OAUTH_PATH = "oauth";
    private static final String RESOURCE_OWNER_PATH = "ro";
    private static final String TOKEN_INFO_PATH = "tokeninfo";

    private final Auth0 auth0;
    private final OkHttpClient client;
    private final ObjectMapper mapper;
    private final RequestFactory factory;

    private String defaultDatabaseConnection = DEFAULT_DB_CONNECTION;

    /**
     * Creates a new API client instance providing Auth0 account info.
     *
     * @param auth0 account information
     */
    public AuthenticationAPIClient(Auth0 auth0) {
        this(auth0, new OkHttpClient(), new ObjectMapper());
    }

    private AuthenticationAPIClient(Auth0 auth0, OkHttpClient client, ObjectMapper mapper) {
        this.auth0 = auth0;
        this.client = client;
        this.mapper = mapper;
        this.factory = new RequestFactory();
        final Telemetry telemetry = auth0.getTelemetry();
        if (telemetry != null) {
            factory.setClientInfo(telemetry.getValue());
        }
    }

    public String getClientId() {
        return auth0.getClientId();
    }

    public String getBaseURL() {
        return auth0.getDomainUrl();
    }

    /**
     * Set the value of 'User-Agent' header for every request to Auth0 Authentication API
     *
     * @param userAgent value to send in every request to Auth0
     */
    public void setUserAgent(String userAgent) {
        factory.setUserAgent(userAgent);
    }

    /**
     * Set the default Auth0 database connection name used. By default is 'Username-Password-Authentication'
     *
     * @param defaultDatabaseConnection name to use on every login with DB connection
     */
    public void setDefaultDatabaseConnection(String defaultDatabaseConnection) {
        this.defaultDatabaseConnection = defaultDatabaseConnection;
    }

    /**
     * Log in a user with email/username and password using a DB connection.
     * Example usage:
     * <pre><code>
     * client.login("{username or email}", "{password}")
     *      .setConnection("{database connection name}")
     *      .start(new BaseCallback<Credentials>() {
     *          {@literal@}Override
     *          public void onSuccess(Credentials payload) { }
     *
     *          {@literal@}Override
     *          public void onFailure(Auth0Exception error) { }
     *      });
     * </code></pre>
     *
     * @param usernameOrEmail of the user depending of the type of DB connection
     * @param password        of the user
     * @return a request to configure and start that will yield {@link Credentials}
     */
    public AuthenticationRequest login(String usernameOrEmail, String password) {
        Map<String, Object> requestParameters = ParameterBuilder.newAuthenticationBuilder()
                .set(USERNAME_KEY, usernameOrEmail)
                .set(PASSWORD_KEY, password)
                .setGrantType(GRANT_TYPE_PASSWORD)
                .asDictionary();
        return loginWithResourceOwner(requestParameters);
    }

    /**
     * Log in a user with a OAuth 'access_token' of a Identity Provider like Facebook or Twitter using <a href="https://auth0.com/docs/auth-api#!#post--oauth-access_token">'\oauth\access_token' endpoint</a>
     * Example usage:
     * <pre><code>
     * client.loginWithOAuthAccessToken("{token}", "{connection name}")
     *      .setConnection("second-database")
     *      .start(new BaseCallback<Credentials>() {
     *          {@literal@}Override
     *          public void onSuccess(Credentials payload) { }

     *          {@literal@}Override
     *          public void onFailure(Auth0Exception error) { }
     *      });
     * </code></pre>
     *
     * @param token      obtained from the IdP
     * @param connection that will be used to authenticate the user, e.g. 'facebook'
     * @return a request to configure and start that will yield {@link Credentials}
     */
    public AuthenticationRequest loginWithOAuthAccessToken(String token, String connection) {
        HttpUrl url = HttpUrl.parse(auth0.getDomainUrl()).newBuilder()
                .addPathSegment(OAUTH_PATH)
                .addPathSegment(ACCESS_TOKEN_PATH)
                .build();

        Map<String, Object> parameters = ParameterBuilder.newAuthenticationBuilder()
                .setClientId(getClientId())
                .setConnection(connection)
                .setAccessToken(token)
                .asDictionary();

        return factory.authenticationPOST(url, client, mapper)
                .addAuthenticationParameters(parameters);
    }

    /**
     * Log in a user using a phone number and a verification code received via SMS (Part of passwordless login flow)
     * Example usage:
     * <pre><code>
     * client.loginWithPhoneNumber("{phone number}", "{code}")
     *      .start(new BaseCallback<Credentials>() {
     *          {@literal@}Override
     *          public void onSuccess(Credentials payload) { }

     *          {@literal@}@Override
     *          public void onFailure(Auth0Exception error) { }
     *      });
     * </code></pre>
     *
     * @param phoneNumber      where the user received the verification code
     * @param verificationCode sent by Auth0 via SMS
     * @return a request to configure and start that will yield {@link Credentials}
     */
    public AuthenticationRequest loginWithPhoneNumber(String phoneNumber, String verificationCode) {
        Map<String, Object> parameters = ParameterBuilder.newAuthenticationBuilder()
                .set(USERNAME_KEY, phoneNumber)
                .set(PASSWORD_KEY, verificationCode)
                .setGrantType(GRANT_TYPE_PASSWORD)
                .setClientId(getClientId())
                .setConnection(SMS_CONNECTION)
                .asDictionary();
        return loginWithResourceOwner(parameters);
    }

    /**
     * Log in a user using an email and a verification code received via Email (Part of passwordless login flow)
     * Example usage:
     * <pre><code>
     * client.loginWithEmail("{email}", "{code}")
     *      .start(new BaseCallback<Credentials>() {
     *          {@literal@}Override
     *          public void onSuccess(Credentials payload) { }

     *          {@literal@}@Override
     *          public void onFailure(Auth0Exception error) { }
     *      });
     * </code></pre>
     *
     * @param email            where the user received the verification code
     * @param verificationCode sent by Auth0 via Email
     * @return a request to configure and start that will yield {@link Credentials}
     */
    public AuthenticationRequest loginWithEmail(String email, String verificationCode) {
        Map<String, Object> parameters = ParameterBuilder.newAuthenticationBuilder()
                .set(USERNAME_KEY, email)
                .set(PASSWORD_KEY, verificationCode)
                .setGrantType(GRANT_TYPE_PASSWORD)
                .setClientId(getClientId())
                .setConnection(EMAIL_CONNECTION)
                .asDictionary();
        return loginWithResourceOwner(parameters);
    }

    /**
     * Fetch the token information from Auth0
     * Example usage:
     * <pre><code>
     * client.tokenInfo("{id_token}")
     *      .start(new BaseCallback<UserProfile>() {
     *          {@literal@}Override
     *          public void onSuccess(UserProfile payload) { }

     *          {@literal@}@Override
     *          public void onFailure(Auth0Exception error) { }
     *      });
     * </code></pre>
     *
     * @param idToken used to fetch it's information
     * @return a request to start
     */
    public Request<UserProfile> tokenInfo(String idToken) {
        return profileRequest()
                .addParameter(ParameterBuilder.ID_TOKEN_KEY, idToken);
    }

    /**
     * Creates a user in a DB connection using <a href="https://auth0.com/docs/auth-api#!#post--dbconnections-signup">'/dbconnections/signup' endpoint</a>
     * Example usage:
     * <pre><code>
     * client.createUser("{email}", "{password}", "{username}")
     *      .setConnection("{connection name}")
     *      .start(new BaseCallback<DatabaseUser>() {
     *          {@literal@}Override
     *          public void onSuccess(DatabaseUser payload) { }
     *
     *          {@literal@}@Override
     *          public void onFailure(Auth0Exception error) { }
     *      });
     * </code></pre>
     *
     * @param email    of the user and must be non null
     * @param password of the user and must be non null
     * @param username of the user and must be non null
     * @return a request to start
     */
    public DatabaseConnectionRequest<DatabaseUser> createUser(String email, String password, String username) {
        HttpUrl url = HttpUrl.parse(auth0.getDomainUrl()).newBuilder()
                .addPathSegment(DB_CONNECTIONS_PATH)
                .addPathSegment(SIGN_UP_PATH)
                .build();

        Map<String, Object> parameters = ParameterBuilder.newBuilder()
                .set(USERNAME_KEY, username)
                .set(EMAIL_KEY, email)
                .set(PASSWORD_KEY, password)
                .setConnection(defaultDatabaseConnection)
                .setClientId(getClientId())
                .asDictionary();
        final ParameterizableRequest<DatabaseUser> request = factory.POST(url, client, mapper, DatabaseUser.class)
                .addParameters(parameters);
        return new DatabaseConnectionRequest<>(request);
    }

    /**
     * Creates a user in a DB connection using <a href="https://auth0.com/docs/auth-api#!#post--dbconnections-signup">'/dbconnections/signup' endpoint</a>
     * Example usage:
     * <pre><code>
     * client.createUser("{email}", "{password}")
     *      .setConnection("{connection name}")
     *      .start(new BaseCallback<DatabaseUser>() {
     *          {@literal@}Override
     *          public void onSuccess(DatabaseUser payload) { }
     *
     *          {@literal@}@Override
     *          public void onFailure(Auth0Exception error) { }
     *      });
     * </code></pre>
     *
     * @param email    of the user and must be non null
     * @param password of the user and must be non null
     * @return a request to start
     */
    public DatabaseConnectionRequest<DatabaseUser> createUser(String email, String password) {
        return createUser(email, password, null);
    }

    /**
     * Creates a user in a DB connection using <a href="https://auth0.com/docs/auth-api#!#post--dbconnections-signup">'/dbconnections/signup' endpoint</a>
     * and then logs in
     * Example usage:
     * <pre><code>
     * client.signUp("{email}", "{password}", "{username}")
     *      .setConnection("{connection name}")
     *      .start(new BaseCallback<Credentials>() {
     *          {@literal@}Override
     *          public void onSuccess(Credentials payload) {}
     *
     *          {@literal@}Override
     *          public void onFailure(Auth0Exception error) {}
     *      });
     * </code></pre>
     *
     * @param email    of the user and must be non null
     * @param password of the user and must be non null
     * @param username of the user and must be non null
     * @return a request to configure and start that will yield {@link Credentials}
     */
    public SignUpRequest signUp(String email, String password, String username) {
        final DatabaseConnectionRequest<DatabaseUser> createUserRequest = createUser(email, password, username);
        final AuthenticationRequest authenticationRequest = login(email, password);
        return new SignUpRequest(createUserRequest, authenticationRequest);
    }

    /**
     * Creates a user in a DB connection using <a href="https://auth0.com/docs/auth-api#!#post--dbconnections-signup">'/dbconnections/signup' endpoint</a>
     * and then logs in
     * Example usage:
     * <pre><code>
     * client.signUp("{email}", "{password}")
     *      .setConnection("{connection name}")
     *      .start(new BaseCallback<Credentials>() {
     *          {@literal@}Override
     *          public void onSuccess(Credentials payload) {}
     *
     *          {@literal@}Override
     *          public void onFailure(Auth0Exception error) {}
     *      });
     * </code></pre>
     *
     * @param email    of the user and must be non null
     * @param password of the user and must be non null
     * @return a request to configure and start that will yield {@link Credentials}
     */
    public SignUpRequest signUp(String email, String password) {
        DatabaseConnectionRequest<DatabaseUser> createUserRequest = createUser(email, password);
        final AuthenticationRequest authenticationRequest = login(email, password);
        return new SignUpRequest(createUserRequest, authenticationRequest);
    }

    /**
     * Request a change password using <a href="https://auth0.com/docs/auth-api#!#post--dbconnections-change_password">'/dbconnections/change_password'</a>
     * Example usage:
     * <pre><code>
     * client.requestChangePassword("{email}")
     *      .start(new BaseCallback<Void>() {
     *          {@literal@}Override
     *          public void onSuccess(Void payload) {}
     *
     *          {@literal@}Override
     *          public void onFailure(Auth0Exception error) {}
     *      });
     * </code></pre>
     *
     * @param email of the user that changes the password. It's also where the email will be sent with the link to perform the change password.
     * @return a request to configure and start
     */
    public DatabaseConnectionRequest<Void> requestChangePassword(String email) {
        HttpUrl url = HttpUrl.parse(auth0.getDomainUrl()).newBuilder()
                .addPathSegment(DB_CONNECTIONS_PATH)
                .addPathSegment(CHANGE_PASSWORD_PATH)
                .build();

        final Map<String, Object> parameters = ParameterBuilder.newBuilder()
                .set(EMAIL_KEY, email)
                .setClientId(getClientId())
                .setConnection(defaultDatabaseConnection)
                .asDictionary();
        final ParameterizableRequest<Void> request = factory.POST(url, client, mapper)
                .addParameters(parameters);
        return new DatabaseConnectionRequest<>(request);
    }

    /**
     * Performs a <a href="https://auth0.com/docs/auth-api#!#post--delegation">delegation</a> request that will yield a new Auth0 'id_token'
     * Example usage:
     * <pre><code>
     * client.delegationWithIdToken("{id token}")
     *      .start(new BaseCallback<Delegation>() {
     *          {@literal@}Override
     *          public void onSuccess(Delegation payload) {}
     *
     *          {@literal@}Override
     *          public void onFailure(Auth0Exception error) {}
     *      });
     * </code></pre>
     *
     * @param idToken issued by Auth0 for the user. The token must not be expired.
     * @return a request to configure and start
     */
    public DelegationRequest<Delegation> delegationWithIdToken(String idToken) {
        ParameterizableRequest<Delegation> request = delegation(Delegation.class)
                .addParameter(ParameterBuilder.ID_TOKEN_KEY, idToken);

        return new DelegationRequest<>(request)
                .setApiType(DelegationRequest.DEFAULT_API_TYPE);
    }

    /**
     * Performs a <a href="https://auth0.com/docs/auth-api#!#post--delegation">delegation</a> request that will yield a new Auth0 'id_token'.
     * Check our <a href="https://auth0.com/docs/refresh-token">refresh token</a> docs for more information
     * Example usage:
     * <pre><code>
     * client.delegationWithRefreshToken("{refresh token}")
     *      .start(new BaseCallback<Delegation>() {
     *          {@literal@}Override
     *          public void onSuccess(Delegation payload) {}
     *
     *          {@literal@}Override
     *          public void onFailure(Auth0Exception error) {}
     *      });
     * </code></pre>
     *
     * @param refreshToken issued by Auth0 for the user when using the 'offline_access' scope when logging in.
     * @return a request to configure and start
     */
    public DelegationRequest<Delegation> delegationWithRefreshToken(String refreshToken) {
        ParameterizableRequest<Delegation> request = delegation(Delegation.class)
                .addParameter(ParameterBuilder.REFRESH_TOKEN_KEY, refreshToken);

        return new DelegationRequest<>(request)
                .setApiType(DelegationRequest.DEFAULT_API_TYPE);
    }

    /**
     * Performs a <a href="https://auth0.com/docs/auth-api#!#post--delegation">delegation</a> request that will yield a delegation token.
     * Example usage:
     * <pre><code>
     * client.delegationWithIdToken("{id token}", "{app type, e.g. firebase}")
     *      .start(new BaseCallback<Map<String, Object>>() {
     *          {@literal@}Override
     *          public void onSuccess(Map<String, Object> payload) {}
     *
     *          {@literal@}Override
     *          public void onFailure(Auth0Exception error) {}
     *      });
     * </code></pre>
     *
     * @param idToken issued by Auth0 for the user. The token must not be expired.
     * @param apiType the delegation 'api_type' parameter
     * @return a request to configure and start
     */
    public DelegationRequest<Map<String, Object>> delegationWithIdToken(String idToken, String apiType) {
        ParameterizableRequest<Map<String, Object>> request = delegation()
                .addParameter(ParameterBuilder.ID_TOKEN_KEY, idToken);

        return new DelegationRequest<>(request)
                .setApiType(apiType);
    }

    /**
     * Unlink a user identity calling <a href="https://auth0.com/docs/auth-api#!#post--unlink">'/unlink'</a> endpoint
     * Example usage:
     * <pre><code>
     * client.unlink("{auth0 user id}", "{user access token}")
     *      .start(new BaseCallback<Void>() {
     *          {@literal@}Override
     *          public void onSuccess(Void payload) {}
     *
     *          {@literal@}Override
     *          public void onFailure(Auth0Exception error) {}
     *      });
     * </code></pre>
     *
     * @param userId      of the identity to unlink
     * @param accessToken of the main identity obtained after login
     * @return a request to start
     */
    public Request<Void> unlink(String userId, String accessToken) {
        HttpUrl url = HttpUrl.parse(auth0.getDomainUrl()).newBuilder()
                .addPathSegment(UNLINK_PATH)
                .build();

        final Map<String, Object> parameters = ParameterBuilder.newBuilder()
                .setAccessToken(accessToken)
                .set(CLIENT_ID_KEY, getClientId())
                .set(USER_ID_KEY, userId)
                .asDictionary();

        return factory.POST(url, client, mapper)
                .addParameters(parameters);
    }

    /**
     * Start a passwordless flow with <a href="https://auth0.com/docs/auth-api#!#post--with_email">Email</a>
     * Example usage:
     * <pre><code>
     * client.passwordlessWithEmail("{email}", PasswordlessType.CODE)
     *      .start(new BaseCallback<Void>() {
     *          {@literal@}Override
     *          public void onSuccess(Void payload) {}
     *
     *          {@literal@}Override
     *          public void onFailure(Auth0Exception error) {}
     *      });
     * </code></pre>
     *
     * @param email            that will receive a verification code to use for login
     * @param passwordlessType indicate whether the email should contain a code, link or magic link (android & iOS)
     * @return a request to configure and start
     */
    public ParameterizableRequest<Void> passwordlessWithEmail(String email, PasswordlessType passwordlessType) {
        final Map<String, Object> parameters = ParameterBuilder.newBuilder()
                .set(EMAIL_KEY, email)
                .setSend(passwordlessType)
                .setConnection(EMAIL_CONNECTION)
                .asDictionary();

        return passwordless()
                .addParameters(parameters);
    }

    /**
     * Start a passwordless flow with <a href="https://auth0.com/docs/auth-api#!#post--with_sms">SMS</a>
     * Example usage:
     * <pre><code>
     * client.passwordlessWithSms("{phone number}", PasswordlessType.CODE)
     *      .start(new BaseCallback<Void>() {
     *          {@literal@}Override
     *          public void onSuccess(Void payload) {}
     *
     *          {@literal@}Override
     *          public void onFailure(Auth0Exception error) {}
     *      });
     * </code></pre>
     *
     * @param phoneNumber      where an SMS with a verification code will be sent
     * @param passwordlessType indicate whether the SMS should contain a code, link or magic link (android & iOS)
     * @return a request to configure and start
     */
    public ParameterizableRequest<Void> passwordlessWithSMS(String phoneNumber, PasswordlessType passwordlessType) {
        final Map<String, Object> parameters = ParameterBuilder.newBuilder()
                .set(PHONE_NUMBER_KEY, phoneNumber)
                .setSend(passwordlessType)
                .setConnection(SMS_CONNECTION)
                .asDictionary();
        return passwordless()
                .addParameters(parameters);
    }

    /**
     * Performs a custom <a href="https://auth0.com/docs/auth-api#!#post--delegation">delegation</a> request that will
     * yield a delegation token.
     * Example usage:
     * <pre><code>
     * client.delegation()
     *      .addParameter("api_type", "firebase")
     *      .start(new BaseCallback<Map<String, Object>>() {
     *          {@literal@}Override
     *          public void onSuccess(Map<String, Object> payload) {}
     *
     *          {@literal@}Override
     *          public void onFailure(Auth0Exception error) {}
     *      });
     * </code></pre>
     *
     * @return a request to configure and start
     */
    public ParameterizableRequest<Map<String, Object>> delegation() {
        HttpUrl url = HttpUrl.parse(auth0.getDomainUrl()).newBuilder()
                .addPathSegment(DELEGATION_PATH)
                .build();

        final Map<String, Object> parameters = ParameterBuilder.newBuilder()
                .setClientId(getClientId())
                .setGrantType(ParameterBuilder.GRANT_TYPE_JWT)
                .asDictionary();
        return factory.rawPOST(url, client, mapper)
                .addParameters(parameters);
    }

    protected <T> ParameterizableRequest<T> delegation(Class<T> clazz) {
        HttpUrl url = HttpUrl.parse(auth0.getDomainUrl()).newBuilder()
                .addPathSegment(DELEGATION_PATH)
                .build();

        final Map<String, Object> parameters = ParameterBuilder.newBuilder()
                .setClientId(getClientId())
                .setGrantType(ParameterBuilder.GRANT_TYPE_JWT)
                .asDictionary();

        return factory.POST(url, client, mapper, clazz)
                .addParameters(parameters);
    }

    /**
     * Start a custom passwordless flow
     *
     * @return a request to configure and start
     */
    public ParameterizableRequest<Void> passwordless() {
        HttpUrl url = HttpUrl.parse(auth0.getDomainUrl()).newBuilder()
                .addPathSegment(PASSWORDLESS_PATH)
                .addPathSegment(START_PATH)
                .build();

        final Map<String, Object> parameters = ParameterBuilder.newBuilder()
                .setClientId(getClientId())
                .asDictionary();
        return factory.POST(url, client, mapper)
                .addParameters(parameters);
    }

    /**
     * Fetch the user's profile after it's authenticated by a login request.
     * If the login request fails, the returned request will fail
     *
     * @param authenticationRequest that will authenticate a user with Auth0 and return a {@see Credentials}
     * @return a {@see ProfileRequest} that first logins and the fetches the profile
     */
    public ProfileRequest getProfileAfter(AuthenticationRequest authenticationRequest) {
        final ParameterizableRequest<UserProfile> profileRequest = profileRequest();
        return new ProfileRequest(authenticationRequest, profileRequest);
    }

    private AuthenticationRequest loginWithResourceOwner(Map<String, Object> parameters) {
        HttpUrl url = HttpUrl.parse(auth0.getDomainUrl()).newBuilder()
                .addPathSegment(OAUTH_PATH)
                .addPathSegment(RESOURCE_OWNER_PATH)
                .build();

        final Map<String, Object> requestParameters = ParameterBuilder.newBuilder()
                .setClientId(getClientId())
                .setConnection(defaultDatabaseConnection)
                .addAll(parameters)
                .asDictionary();
        return factory.authenticationPOST(url, client, mapper)
                .addAuthenticationParameters(requestParameters);
    }

    private ParameterizableRequest<UserProfile> profileRequest() {
        HttpUrl url = HttpUrl.parse(auth0.getDomainUrl()).newBuilder()
                .addPathSegment(TOKEN_INFO_PATH)
                .build();

        return factory.POST(url, client, mapper, UserProfile.class);
    }
}

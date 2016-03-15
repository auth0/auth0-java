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
import com.auth0.authentication.result.DatabaseUser;
import com.auth0.authentication.result.Delegation;
import com.auth0.authentication.result.Token;
import com.auth0.authentication.result.UserProfile;
import com.auth0.internal.RequestFactory;
import com.auth0.request.ParameterizableRequest;
import com.auth0.request.Request;
import com.auth0.util.Metrics;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;

import java.util.Map;

import static com.auth0.authentication.ParameterBuilder.GRANT_TYPE_JWT;
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
    private static final String ID_TOKEN = "id_token";

    private final Auth0 auth0;
    private final OkHttpClient client;
    private final ObjectMapper mapper;
    private final RequestFactory factory;

    private String defaultDbConnection = DEFAULT_DB_CONNECTION;

    /**
     * Creates a new API client instance providing Auth0 account info.
     *
     * @param auth0 account information
     */
    public AuthenticationAPIClient(Auth0 auth0) {
        this(auth0, new OkHttpClient(), new ObjectMapper());
    }

    /**
     * Creates a new API client instance providing Auth API and Configuration Urls different than the default. (Useful for on premise deploys).
     *
     * @param clientID         Your application clientID.
     * @param baseURL          Auth0's auth API endpoint
     * @param configurationURL Auth0's endpoint where App info can be retrieved.
     */
    @SuppressWarnings("unused")
    public AuthenticationAPIClient(String clientID, String baseURL, String configurationURL) {
        this(new Auth0(clientID, baseURL, configurationURL));
    }

    private AuthenticationAPIClient(Auth0 auth0, OkHttpClient client, ObjectMapper mapper) {
        this.auth0 = auth0;
        this.client = client;
        this.mapper = mapper;
        this.factory = new RequestFactory();
        final Metrics metrics = auth0.getMetrics();
        if (metrics != null) {
            factory.setClientInfo(metrics.getValue());
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
     * Set the default DB connection name used. By default is 'Username-Password-Authentication'
     *
     * @param defaultDbConnection name to use on every login with DB connection
     */
    public void setDefaultDbConnection(String defaultDbConnection) {
        this.defaultDbConnection = defaultDbConnection;
    }

    /**
     * Log in a user with email/username and password using a DB connection and fetch it's profile from Auth0
     *
     * @param usernameOrEmail of the user depending of the type of DB connection
     * @param password        of the user
     * @return a request to configure and start that will yield {@link Token} and {@link UserProfile}
     */
    public ParameterizableRequest<Token> login(String usernameOrEmail, String password) {
        Map<String, Object> requestParameters = ParameterBuilder.newAuthenticationBuilder()
                .set(USERNAME_KEY, usernameOrEmail)
                .set(PASSWORD_KEY, password)
                .setGrantType(GRANT_TYPE_PASSWORD)
                .asDictionary();
        return loginWithResourceOwner(requestParameters);
    }

    /**
     * Log in a user with a OAuth 'access_token' of a Identity Provider like Facebook or Twitter using <a href="https://auth0.com/docs/auth-api#!#post--oauth-access_token">'\oauth\access_token' endpoint</a>
     *
     * @param token      obtained from the IdP
     * @param connection that will be used to authenticate the user, e.g. 'facebook'
     * @return a request to configure and start that will yield {@link Token} and {@link UserProfile}
     */
    public ParameterizableRequest<Token> loginWithOAuthAccessToken(String token, String connection) {
        HttpUrl url = HttpUrl.parse(auth0.getDomainUrl()).newBuilder()
                .addPathSegment(OAUTH_PATH)
                .addPathSegment(ACCESS_TOKEN_PATH)
                .build();

        Map<String, Object> parameters = ParameterBuilder.newAuthenticationBuilder()
                .setClientId(getClientId())
                .setConnection(connection)
                .setAccessToken(token)
                .asDictionary();

        ParameterizableRequest<Token> credentialsRequest = factory.POST(url, client, mapper, Token.class);
        credentialsRequest.getParameterBuilder().addAll(parameters);
        return credentialsRequest;
    }

    /**
     * Log in a user using a phone number and a verification code received via SMS (Part of passwordless login flow)
     *
     * @param phoneNumber      where the user received the verification code
     * @param verificationCode sent by Auth0 via SMS
     * @return a request to configure and start that will yield {@link Token} and {@link UserProfile}
     */
    public ParameterizableRequest<Token> loginWithPhoneNumber(String phoneNumber, String verificationCode) {
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
     *
     * @param email            where the user received the verification code
     * @param verificationCode sent by Auth0 via Email
     * @return a request to configure and start that will yield {@link Token} and {@link UserProfile}
     */
    public ParameterizableRequest<Token> loginWithEmail(String email, String verificationCode) {
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
     *
     * @param idToken used to fetch it's information
     * @return a request to start
     */
    public Request<UserProfile> tokenInfo(String idToken) {
        ParameterizableRequest<UserProfile> request = profileRequest();
        request.getParameterBuilder().set(ParameterBuilder.ID_TOKEN_KEY, idToken);
        return request;
    }

    /**
     * Creates a user in a DB connection using <a href="https://auth0.com/docs/auth-api#!#post--dbconnections-signup">'/dbconnections/signup' endpoint</a>
     *
     * @param email    of the user and must be non null
     * @param password of the user and must be non null
     * @param username of the user and must be non null
     * @return a request to start
     */
    public ParameterizableRequest<DatabaseUser> createUser(String email, String password, String username) {
        HttpUrl url = HttpUrl.parse(auth0.getDomainUrl()).newBuilder()
                .addPathSegment(DB_CONNECTIONS_PATH)
                .addPathSegment(SIGN_UP_PATH)
                .build();

        ParameterizableRequest<DatabaseUser> request = factory.POST(url, client, mapper, DatabaseUser.class);
        request.getParameterBuilder()
                .set(USERNAME_KEY, username)
                .set(EMAIL_KEY, email)
                .set(PASSWORD_KEY, password)
                .setConnection(defaultDbConnection)
                .setClientId(getClientId());
        return request;
    }

    /**
     * Creates a user in a DB connection using <a href="https://auth0.com/docs/auth-api#!#post--dbconnections-signup">'/dbconnections/signup' endpoint</a>
     *
     * @param email    of the user and must be non null
     * @param password of the user and must be non null
     * @return a request to start
     */
    public ParameterizableRequest<DatabaseUser> createUser(String email, String password) {
        return createUser(email, password, null);
    }

    /**
     * Creates a user in a DB connection using <a href="https://auth0.com/docs/auth-api#!#post--dbconnections-signup">'/dbconnections/signup' endpoint</a>
     * and then logs in and fetches it's user profile
     *
     * @param email    of the user and must be non null
     * @param password of the user and must be non null
     * @param username of the user and must be non null
     * @return a request to configure and start that will yield {@link Token} and {@link UserProfile}
     */
    public SignUpRequest signUp(String email, String password, String username) {
        final ParameterizableRequest<DatabaseUser> createUserRequest = createUser(email, password, username);
        final ParameterizableRequest<Token> authenticationRequest = login(email, password);
        return new SignUpRequest(createUserRequest, authenticationRequest);
    }

    /**
     * Creates a user in a DB connection using <a href="https://auth0.com/docs/auth-api#!#post--dbconnections-signup">'/dbconnections/signup' endpoint</a>
     * and then logs in and fetches it's user profile
     *
     * @param email    of the user and must be non null
     * @param password of the user and must be non null
     * @return a request to configure and start that will yield {@link Token} and {@link UserProfile}
     */
    public SignUpRequest signUp(String email, String password) {
        ParameterizableRequest<DatabaseUser> createUserRequest = createUser(email, password);
        final ParameterizableRequest<Token> authenticationRequest = login(email, password);
        return new SignUpRequest(createUserRequest, authenticationRequest);
    }

    /**
     * Perform a change password request using <a href="https://auth0.com/docs/auth-api#!#post--dbconnections-change_password">'/dbconnections/change_password'</a>
     *
     * @param email of the user that changes the password. It's also where the confirmation email will be sent
     * @return a request to configure and start
     */
    public ChangePasswordRequest changePassword(String email) {
        HttpUrl url = HttpUrl.parse(auth0.getDomainUrl()).newBuilder()
                .addPathSegment(DB_CONNECTIONS_PATH)
                .addPathSegment(CHANGE_PASSWORD_PATH)
                .build();

        ParameterizableRequest<Void> request = factory.POST(url, client, mapper);
        request.getParameterBuilder()
                .set(EMAIL_KEY, email)
                .setClientId(getClientId())
                .setConnection(defaultDbConnection);
        return new ChangePasswordRequest(request);
    }

    /**
     * Performs a <a href="https://auth0.com/docs/auth-api#!#post--delegation">delegation</a> request that will yield a new Auth0 'id_token'
     *
     * @param idToken issued by Auth0 for the user. The token must not be expired.
     * @return a request to configure and start
     */
    public DelegationRequest<Delegation> delegationWithIdToken(String idToken) {
        ParameterizableRequest<Delegation> request = delegation(Delegation.class);
        request.getParameterBuilder().set(ParameterBuilder.ID_TOKEN_KEY, idToken);

        return new DelegationRequest<>(request)
                .setApiType(DelegationRequest.DEFAULT_API_TYPE);
    }

    /**
     * Performs a <a href="https://auth0.com/docs/auth-api#!#post--delegation">delegation</a> request that will yield a new Auth0 'id_token'.
     * Check our <a href="https://auth0.com/docs/refresh-token">refresh token</a> docs for more information
     *
     * @param refreshToken issued by Auth0 for the user when using the 'offline_access' scope when logging in.
     * @return a request to configure and start
     */
    public DelegationRequest<Delegation> delegationWithRefreshToken(String refreshToken) {
        ParameterizableRequest<Delegation> request = delegation(Delegation.class);
        request.getParameterBuilder().set(ParameterBuilder.REFRESH_TOKEN_KEY, refreshToken);

        return new DelegationRequest<>(request)
                .setApiType(DelegationRequest.DEFAULT_API_TYPE);
    }

    /**
     * Performs a <a href="https://auth0.com/docs/auth-api#!#post--delegation">delegation</a> request that will yield a delegation token.
     *
     * @param idToken issued by Auth0 for the user. The token must not be expired.
     * @param apiType the delegation 'api_type' parameter
     * @return a request to configure and start
     */
    public DelegationRequest<Map<String, Object>> delegationWithIdToken(String idToken, String apiType) {
        ParameterizableRequest<Map<String, Object>> request = delegation();
        request.getParameterBuilder().set(ParameterBuilder.ID_TOKEN_KEY, idToken);

        return new DelegationRequest<>(request)
                .setApiType(apiType);
    }

    /**
     * Performs a <a href="https://auth0.com/docs/auth-api#!#post--delegation">delegation</a> request that will yield a delegation token.
     * Check our <a href="https://auth0.com/docs/refresh-token">refresh token</a> docs for more information
     *
     * @param refreshToken issued by Auth0 for the user when using the 'offline_access' scope when logging in.
     * @param apiType      the delegation 'api_type' parameter
     * @return a request to configure and start
     */
    public DelegationRequest<Map<String, Object>> delegationWithRefreshToken(String refreshToken, String apiType) {
        ParameterizableRequest<Map<String, Object>> request = delegation();
        request.getParameterBuilder().set(ParameterBuilder.REFRESH_TOKEN_KEY, refreshToken);

        return new DelegationRequest<>(request).setApiType(apiType);
    }

    /**
     * Unlink a user identity calling <a href="https://auth0.com/docs/auth-api#!#post--unlink">'/unlink'</a> endpoint
     *
     * @param userId      of the identity to unlink
     * @param accessToken of the main identity obtained after login
     * @return a request to start
     */
    public Request<Void> unlink(String userId, String accessToken) {
        HttpUrl url = HttpUrl.parse(auth0.getDomainUrl()).newBuilder()
                .addPathSegment(UNLINK_PATH)
                .build();

        ParameterizableRequest<Void> request = factory.POST(url, client, mapper);
        request.getParameterBuilder()
                .setAccessToken(accessToken)
                .set(CLIENT_ID_KEY, getClientId())
                .set(USER_ID_KEY, userId);
        return request;
    }

    /**
     * Start a passwordless flow with <a href="https://auth0.com/docs/auth-api#!#post--with_email">Email</a>
     *
     * @param email            that will receive a verification code to use for login
     * @param passwordlessType indicate whether the email should contain a code, link or magic link (android & iOS)
     * @return a request to configure and start
     */
    public ParameterizableRequest<Void> passwordlessWithEmail(String email, PasswordlessType passwordlessType) {
        ParameterizableRequest<Void> request = passwordless();
        request.getParameterBuilder()
                .set(EMAIL_KEY, email)
                .setSend(passwordlessType)
                .setConnection(EMAIL_CONNECTION);
        return request;
    }

    /**
     * Start a passwordless flow with <a href="https://auth0.com/docs/auth-api#!#post--with_sms">SMS</a>
     *
     * @param phoneNumber      where an SMS with a verification code will be sent
     * @param passwordlessType indicate whether the SMS should contain a code, link or magic link (android & iOS)
     * @return a request to configure and start
     */
    public ParameterizableRequest<Void> passwordlessWithSMS(String phoneNumber, PasswordlessType passwordlessType) {
        ParameterizableRequest<Void> request = passwordless();
        request.getParameterBuilder()
                .set(PHONE_NUMBER_KEY, phoneNumber)
                .setSend(passwordlessType)
                .setConnection(SMS_CONNECTION);
        return request;
    }

    /**
     * Performs a custom <a href="https://auth0.com/docs/auth-api#!#post--delegation">delegation</a> request that will
     * yield a delegation token.
     *
     * @return a request to configure and start
     */
    public ParameterizableRequest<Map<String, Object>> delegation() {
        HttpUrl url = HttpUrl.parse(auth0.getDomainUrl()).newBuilder()
                .addPathSegment(DELEGATION_PATH)
                .build();

        ParameterizableRequest<Map<String, Object>> request = factory.rawPOST(url, client, mapper);
        request.getParameterBuilder()
                .setClientId(getClientId())
                .setGrantType(ParameterBuilder.GRANT_TYPE_JWT);
        return request;
    }

    protected <T> ParameterizableRequest<T> delegation(Class<T> clazz) {
        HttpUrl url = HttpUrl.parse(auth0.getDomainUrl()).newBuilder()
                .addPathSegment(DELEGATION_PATH)
                .build();

        ParameterizableRequest<T> request = factory.POST(url, client, mapper, clazz);
        request.getParameterBuilder()
                .setClientId(getClientId())
                .setGrantType(GRANT_TYPE_JWT);

        return request;
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

        ParameterizableRequest<Void> request = factory.POST(url, client, mapper);
        request.getParameterBuilder().setClientId(getClientId());
        return request;
    }

    public AuthenticationRequest getProfileAfter(ParameterizableRequest<Token> loginRequest) {
        final ParameterizableRequest<UserProfile> profileRequest = profileRequest();
        return new AuthenticationRequest(loginRequest, profileRequest);
    }

    protected ParameterizableRequest<Token> loginWithResourceOwner(Map<String, Object> parameters) {
        HttpUrl url = HttpUrl.parse(auth0.getDomainUrl()).newBuilder()
                .addPathSegment(OAUTH_PATH)
                .addPathSegment(RESOURCE_OWNER_PATH)
                .build();

        ParameterizableRequest<Token> request = factory.POST(url, client, mapper, Token.class);
        request.getParameterBuilder()
                .setClientId(getClientId())
                .setConnection(defaultDbConnection)
                .addAll(parameters);
        return request;
    }

    private ParameterizableRequest<UserProfile> profileRequest() {
        HttpUrl url = HttpUrl.parse(auth0.getDomainUrl()).newBuilder()
                .addPathSegment(TOKEN_INFO_PATH)
                .build();

        return factory.POST(url, client, mapper, UserProfile.class);
    }
}

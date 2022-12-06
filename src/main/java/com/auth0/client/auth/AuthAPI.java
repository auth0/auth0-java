package com.auth0.client.auth;

import com.auth0.client.HttpOptions;
import com.auth0.client.mgmt.ManagementAPI;
import com.auth0.json.auth.PasswordlessEmailResponse;
import com.auth0.json.auth.PasswordlessSmsResponse;
import com.auth0.json.auth.UserInfo;
import com.auth0.net.*;
import com.auth0.net.client.Auth0HttpClient;
import com.auth0.net.client.DefaultHttpClient;
import com.auth0.net.client.HttpMethod;
import com.auth0.utils.Asserts;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.TestOnly;

import java.util.Objects;

/**
 * Class that provides an implementation of of the Authentication and Authorization API methods defined by the
 * <a href="https://auth0.com/docs/api/authentication">Auth0 Authentication API</a>.
 * Instances are created using the {@link Builder}. If you are also using the {@link ManagementAPI}, it is recommended
 * to configure each with the same {@link DefaultHttpClient} to enable both API clients to share the same Http client.
 * <p>
 * To use with a confidential client, instantiate an instance with a client secret:
 * <pre>
 * {@code
 * AuthAPI auth = AuthAPI.newBuilder("{DOMAIN}", "{CLIENT-ID}", "{CLIENT-SECRET}").build();
 * }
 * </pre>
 * <p>
 * To use with a public client, or when only using APIs that do not require a client secret:
 * <pre>
 * {@code
 * AuthAPI auth = AuthAPI.newBuilder("{DOMAIN}", "{CLIENT-ID}").build();
 * }
 * </pre>
 * Operations that always require a client secret will throw a {@code InvalidStateException} if the client is not created
 * with a secret.
 */
@SuppressWarnings("WeakerAccess")
public class AuthAPI {

    private static final String KEY_CLIENT_ID = "client_id";
    private static final String KEY_CLIENT_SECRET = "client_secret";
    private static final String KEY_GRANT_TYPE = "grant_type";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_AUDIENCE = "audience";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_CONNECTION = "connection";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_REFRESH_TOKEN = "refresh_token";
    private static final String KEY_OTP = "otp";
    private static final String KEY_REALM = "realm";
    private static final String KEY_MFA_TOKEN = "mfa_token";

    private static final String PATH_OAUTH = "oauth";
    private static final String PATH_TOKEN = "token";
    private static final String PATH_DBCONNECTIONS = "dbconnections";
    private static final String PATH_REVOKE = "revoke";
    private static final String PATH_PASSWORDLESS = "passwordless";
    private static final String PATH_START = "start";

    private final Auth0HttpClient client;
    private final String clientId;
    private final String clientSecret;
    private final HttpUrl baseUrl;

    /**
     * Create a new instance with the given tenant's domain, application's client id and client secret.
     * These values can be obtained at {@code https://manage.auth0.com/#/applications/{YOUR_CLIENT_ID}/settings}.
     * In addition, accepts an {@link HttpOptions} that will be used to configure the networking client.
     *
     * @deprecated Use the {@link Builder} to configure and create instances.
     *
     * @param domain       tenant's domain.
     * @param clientId     the application's client id.
     * @param clientSecret the application's client secret.
     * @param options      configuration options for this client instance.
     * @see #AuthAPI(String, String, String)
     */
    @Deprecated
    public AuthAPI(String domain, String clientId, String clientSecret, HttpOptions options) {
        this(domain, clientId, clientSecret, buildNetworkingClient(options));
    }

    /**
     * Create a new instance with the given tenant's domain, application's client id and client secret.
     * These values can be obtained at {@code https://manage.auth0.com/#/applications/{YOUR_CLIENT_ID}/}settings.
     *
     * @deprecated Use the {@link Builder} to configure and create instances.
     *
     * @param domain       tenant's domain.
     * @param clientId     the application's client id.
     * @param clientSecret the application's client secret.
     */
    @Deprecated
    public AuthAPI(String domain, String clientId, String clientSecret) {
        this(domain, clientId, clientSecret, new HttpOptions());
    }

    /**
     * Initialize a new {@link Builder} to configure and create an instance. Use this to construct an instance
     * with a client secret when using a confidential client (Regular Web Application).
     * @param domain the tenant's domain. Must be a non-null valid HTTPS URL.
     * @param clientId the application's client ID.
     * @param clientSecret the applications client secret.
     * @return a Builder for further configuration.
     */
    public static AuthAPI.Builder newBuilder(String domain, String clientId, String clientSecret) {
        return new AuthAPI.Builder(domain, clientId, clientSecret);
    }

    /**
     * Initialize a new {@link Builder} to configure and create an instance. Use this to construct an instance
     * <strong>without</strong> a client secret (for example, when only using APIs that do not require a secret).
     * @param domain the tenant's domain. Must be a non-null valid HTTPS URL.
     * @param clientId the application's client ID.
     * @return a Builder for further configuration.
     */
    public static AuthAPI.Builder newBuilder(String domain, String clientId) {
        return new AuthAPI.Builder(domain, clientId);
    }

    private AuthAPI(String domain, String clientId, String clientSecret, Auth0HttpClient httpClient) {
        Asserts.assertNotNull(domain, "domain");
        Asserts.assertNotNull(clientId, "client id");
        Asserts.assertNotNull(httpClient, "Http client");

        this.baseUrl = createBaseUrl(domain);
        if (baseUrl == null) {
            throw new IllegalArgumentException("The domain had an invalid format and couldn't be parsed as an URL.");
        }
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.client = httpClient;

    }
    /**
     * Given a set of options, it creates a new instance of the {@link OkHttpClient}
     * configuring them according to their availability.
     *
     * @param options the options to set to the client.
     * @return a new networking client instance configured as requested.
     */
    private static Auth0HttpClient buildNetworkingClient(HttpOptions options) {
        Asserts.assertNotNull(options, "Http options");
        return DefaultHttpClient.newBuilder()
            .withLogging(options.getLoggingOptions())
            .withMaxRetries(options.getManagementAPIMaxRetries())
            .withMaxRequests(options.getMaxRequests())
            .withMaxRequestsPerHost(options.getMaxRequestsPerHost())
            .withProxy(options.getProxyOptions())
            .withConnectTimeout(options.getConnectTimeout())
            .withReadTimeout(options.getReadTimeout())
            .build();
    }

    @TestOnly
    Auth0HttpClient getHttpClient() {
        return this.client;
    }

    @TestOnly
    HttpUrl getBaseUrl() {
        return baseUrl;
    }

    private HttpUrl createBaseUrl(String domain) {
        String url = domain;
        if (!domain.startsWith("https://") && !domain.startsWith("http://")) {
            url = "https://" + domain;
        }
        return HttpUrl.parse(url);
    }

    public String getManagementAPIAudience() {
        return baseUrl.newBuilder().addPathSegments("api/v2/").toString();
    }

    /**
     * Creates an instance of the {@link AuthorizeUrlBuilder} with the given redirect url.
     * i.e.:
     * <pre>
     * {@code
     * String url = authAPI.authorizeUrl("https://me.auth0.com/callback")
     *      .withConnection("facebook")
     *      .withAudience("https://api.me.auth0.com/users")
     *      .withScope("openid contacts")
     *      .withState("my-custom-state")
     *      .build();
     * }
     * </pre>
     *
     * @param redirectUri the URL to redirect to after authorization has been granted by the user. Your Auth0 application
     *                    must have this URL as one of its Allowed Callback URLs. Must be a valid non-encoded URL.
     * @return a new instance of the {@link AuthorizeUrlBuilder} to configure.
     */
    public AuthorizeUrlBuilder authorizeUrl(String redirectUri) {
        Asserts.assertValidUrl(redirectUri, "redirect uri");

        return AuthorizeUrlBuilder.newInstance(baseUrl, clientId, redirectUri);
    }

    /**
     * Creates an instance of the {@link LogoutUrlBuilder} with the given return-to url.
     * i.e.:
     * <pre>
     * {@code
     * String url = authAPI.logoutUrl("https://me.auth0.com/home", true)
     *      .useFederated(true)
     *      .withAccessToken("A9CvPwFojaBIA9CvI");
     * }
     * </pre>
     *
     * @param returnToUrl the URL the user should be navigated to upon logout. Must be a valid non-encoded URL.
     * @param setClientId whether the client_id value must be set or not. If {@code true}, the {@code returnToUrl} must
     *                    be included in your Auth0 Application's Allowed Logout URLs list. If {@code false}, the
     *                    {@code returnToUrl} must be included in your Auth0's Allowed Logout URLs at the Tenant level.
     * @return a new instance of the {@link LogoutUrlBuilder} to configure.
     */
    public LogoutUrlBuilder logoutUrl(String returnToUrl, boolean setClientId) {
        Asserts.assertValidUrl(returnToUrl, "return to url");

        return LogoutUrlBuilder.newInstance(baseUrl, clientId, returnToUrl, setClientId);
    }


    /**
     * Request the user information related to the access token.
     * i.e.:
     * <pre>
     * {@code
     * try {
     *      UserInfo result = authAPI.userInfo("A9CvPwFojaBIA9CvI").execute().getBody();
     * } catch (Auth0Exception e) {
     *      //Something happened
     * }
     * }
     * </pre>
     *
     * @see <a href="https://auth0.com/docs/api/authentication#get-user-info">Get User Info API docs</a>
     * @param accessToken a valid access token belonging to an API signed with RS256 algorithm and containing the scope 'openid'.
     * @return a Request to execute.
     */
    public Request<UserInfo> userInfo(String accessToken) {
        Asserts.assertNotNull(accessToken, "access token");

        String url = baseUrl
                .newBuilder()
                .addPathSegment("userinfo")
                .build()
                .toString();
        CustomRequest<UserInfo> request = new CustomRequest<>(client, null, url, HttpMethod.GET, new TypeReference<UserInfo>() {
        });
        request.addHeader("Authorization", "Bearer " + accessToken);
        return request;
    }

    /**
     * Request a password reset for the given email and database connection. The response will always be successful even if
     * there's no user associated to the given email for that database connection.
     * i.e.:
     * <pre>
     * {@code
     * try {
     *      authAPI.resetPassword("me@auth0.com", "db-connection").execute().getBody();
     * } catch (Auth0Exception e) {
     *      //Something happened
     * }
     * }
     * </pre>
     *
     * @see <a href="https://auth0.com/docs/api/authentication#change-password">Change Password API docs</a>
     * @param email      the email associated to the database user.
     * @param connection the database connection where the user was created.
     * @return a Request to execute.
     */
    public Request<Void> resetPassword(String email, String connection) {
        Asserts.assertNotNull(email, "email");
        Asserts.assertNotNull(connection, "connection");

        String url = baseUrl
                .newBuilder()
                .addPathSegment(PATH_DBCONNECTIONS)
                .addPathSegment("change_password")
                .build()
                .toString();
        VoidRequest request = new VoidRequest(client, null, url, HttpMethod.POST);
        request.addParameter(KEY_CLIENT_ID, clientId);
        request.addParameter(KEY_EMAIL, email);
        request.addParameter(KEY_CONNECTION, connection);
        return request;
    }

    /**
     * Creates a sign up request with the given credentials and database connection.
     * "Requires Username" option must be turned on in the Connection's configuration first.
     * i.e.:
     * <pre>
     * {@code
     * try {
     *      Map<String, String> fields = new HashMap<String, String>();
     *      fields.put("age", "25);
     *      fields.put("city", "Buenos Aires");
     *      authAPI.signUp("me@auth0.com", "myself", "topsecret", "db-connection")
     *          .setCustomFields(fields)
     *          .execute();
     * } catch (Auth0Exception e) {
     *      //Something happened
     * }
     * }
     * </pre>
     *
     * @param email      the desired user's email.
     * @param username   the desired user's username.
     * @param password   the desired user's password.
     * @param connection the database connection where the user is going to be created.
     * @return a Request to configure and execute.
     * @deprecated Use {@linkplain #signUp(String, String, char[], String)} instead.
     */
    @Deprecated
    public SignUpRequest signUp(String email, String username, String password, String connection) {
        return this.signUp(email, username, password != null ? password.toCharArray() : null, connection);
    }

    /**
     * Creates a sign up request with the given credentials and database connection.
     * "Requires Username" option must be turned on in the Connection's configuration first.
     * i.e.:
     * <pre>
     * {@code
     * try {
     *      Map<String, String> fields = new HashMap<String, String>();
     *      fields.put("age", "25);
     *      fields.put("city", "Buenos Aires");
     *      authAPI.signUp("me@auth0.com", "myself", new char[]{'s','e','c','r','e','t'}, "db-connection")
     *          .setCustomFields(fields)
     *          .execute();
     * } catch (Auth0Exception e) {
     *      //Something happened
     * }
     * }
     * </pre>
     *
     * @see <a href="https://auth0.com/docs/api/authentication#signup">Signup API docs</a>
     * @param email      the desired user's email.
     * @param username   the desired user's username.
     * @param password   the desired user's password.
     * @param connection the database connection where the user is going to be created.
     * @return a Request to configure and execute.
     */
    public SignUpRequest signUp(String email, String username, char[] password, String connection) {
        Asserts.assertNotNull(username, "username");

        CreateUserRequest request = (CreateUserRequest) this.signUp(email, password, connection);
        request.addParameter(KEY_USERNAME, username);
        return request;
    }

    /**
     * Creates a sign up request with the given credentials and database connection.
     * i.e.:
     * <pre>
     * {@code
     * try {
     *      Map<String, String> fields = new HashMap<String, String>();
     *      fields.put("age", "25);
     *      fields.put("city", "Buenos Aires");
     *      authAPI.signUp("me@auth0.com", "topsecret", "db-connection")
     *          .setCustomFields(fields)
     *          .execute();
     * } catch (Auth0Exception e) {
     *      //Something happened
     * }
     * }
     * </pre>
     *
     * @param email      the desired user's email.
     * @param password   the desired user's password.
     * @param connection the database connection where the user is going to be created.
     * @return a Request to configure and execute.
     * @deprecated Use {@linkplain #signUp(String, char[], String)} instead.
     */
    @Deprecated
    public SignUpRequest signUp(String email, String password, String connection) {
        return this.signUp(email, password != null ? password.toCharArray() : null, connection);
    }

    /**
     * Creates a sign up request with the given credentials and database connection.
     * <pre>
     * {@code
     * try {
     *      Map<String, String> fields = new HashMap<String, String>();
     *      fields.put("age", "25);
     *      fields.put("city", "Buenos Aires");
     *      authAPI.signUp("me@auth0.com", new char[]{'s','e','c','r','e','t'}, "db-connection")
     *          .setCustomFields(fields)
     *          .execute();
     * } catch (Auth0Exception e) {
     *      //Something happened
     * }
     * }
     * </pre>
     *
     * @see <a href="https://auth0.com/docs/api/authentication#signup">Signup API docs</a>
     * @param email      the desired user's email.
     * @param password   the desired user's password.
     * @param connection the database connection where the user is going to be created.
     * @return a Request to configure and execute.
     */
    public SignUpRequest signUp(String email, char[] password, String connection) {
        Asserts.assertNotNull(email, "email");
        Asserts.assertNotNull(password, "password");
        Asserts.assertNotNull(connection, "connection");

        String url = baseUrl
                .newBuilder()
                .addPathSegment(PATH_DBCONNECTIONS)
                .addPathSegment("signup")
                .build()
                .toString();
        CreateUserRequest request = new CreateUserRequest(client, url);
        request.addParameter(KEY_CLIENT_ID, clientId);
        request.addParameter(KEY_EMAIL, email);
        request.addParameter(KEY_PASSWORD, password);
        request.addParameter(KEY_CONNECTION, connection);
        return request;
    }

    /**
     * Creates a log in request using the 'Password' grant and the given credentials.
     * i.e.:
     * <pre>
     * {@code
     * try {
     *      TokenHolder result = authAPI.login("me@auth0.com", "topsecret")
     *          .setScope("openid email nickname")
     *          .execute();
     * } catch (Auth0Exception e) {
     *      //Something happened
     * }
     * }
     * </pre>
     *
     * @param emailOrUsername the identity of the user.
     * @param password        the password of the user.
     * @return a Request to configure and execute.
     * @deprecated Use {@linkplain #login(String, char[])} instead.
     */
    @Deprecated
    public TokenRequest login(String emailOrUsername, String password) {
        return this.login(emailOrUsername, password != null ? password.toCharArray() : null);
    }

    /**
     * Creates a log in request using the 'Password' grant and the given credentials.
     * <strong>
     * This flow should only be used from highly-trusted applications that cannot do redirects.
     * If you can use redirect-based flows from your app, we recommend using the Authorization Code Flow instead.
     * </strong>
     * i.e.:
     * <pre>
     * {@code
     * try {
     *      TokenHolder result = authAPI.login("me@auth0.com", new char[]{'s','e','c','r','e','t})
     *          .setScope("openid email nickname")
     *          .execute()
     *          .getBody();
     * } catch (Auth0Exception e) {
     *      //Something happened
     * }
     * }
     * </pre>
     *
     * @see <a href="https://auth0.com/docs/api/authentication#client-credentials-flow">Resource Owner Password API docs</a>.
     * @param emailOrUsername the identity of the user.
     * @param password        the password of the user.
     * @return a Request to configure and execute.
     */
    public TokenRequest login(String emailOrUsername, char[] password) {
        Asserts.assertNotNull(emailOrUsername, "email or username");
        Asserts.assertNotNull(password, "password");

        TokenRequest request = new TokenRequest(client, getTokenUrl());
        request.addParameter(KEY_CLIENT_ID, clientId);
        request.addParameter(KEY_GRANT_TYPE, "password");
        request.addParameter(KEY_USERNAME, emailOrUsername);
        request.addParameter(KEY_PASSWORD, password);
        addSecret(request, true);
        return request;
    }

    /**
     * Creates a log in request using the 'Password Realm' grant and the given credentials.
     * Default used realm and audience are defined in the "API Authorization Settings" in the account's advanced settings in the Auth0 Dashboard.
     * <pre>
     * {@code
     * try {
     *      TokenHolder result = authAPI.login("me@auth0.com", "topsecret", "my-realm")
     *          .setAudience("https://myapi.me.auth0.com/users")
     *          .execute()
     *          .getBody();
     * } catch (Auth0Exception e) {
     *      //Something happened
     * }
     * }
     * </pre>
     *
     * @param emailOrUsername the identity of the user.
     * @param password        the password of the user.
     * @param realm           the realm to use.
     * @return a Request to configure and execute.
     * @deprecated Use {@linkplain #login(String, char[], String)} instead.
     */
    @Deprecated
    public TokenRequest login(String emailOrUsername, String password, String realm) {
        return this.login(emailOrUsername, password != null ? password.toCharArray() : null, realm);
    }

    /**
     * Creates a log in request using the 'Password Realm' grant and the given credentials.
     * Default used realm and audience are defined in the "API Authorization Settings" in the account's advanced settings in the Auth0 Dashboard.
     * <strong>
     * This flow should only be used from highly-trusted applications that cannot do redirects.
     * If you can use redirect-based flows from your app, we recommend using the Authorization Code Flow instead.
     * </strong>
     * <pre>
     * {@code
     * try {
     *      TokenHolder result = authAPI.login("me@auth0.com", new char[]{'s','e','c','r','e','t'}, "my-realm")
     *          .setAudience("https://myapi.me.auth0.com/users")
     *          .execute()
     *          .getBody();
     * } catch (Auth0Exception e) {
     *      //Something happened
     * }
     * }
     * </pre>
     *
     * @param emailOrUsername the identity of the user.
     * @param password        the password of the user.
     * @param realm           the realm to use.
     * @return a Request to configure and execute.
     * @see <a href="https://auth0.com/docs/api/authentication#client-credentials-flow">Resource Owner Password API docs</a>.
     */
    public TokenRequest login(String emailOrUsername, char[] password, String realm) {
        Asserts.assertNotNull(emailOrUsername, "email or username");
        Asserts.assertNotNull(password, "password");
        Asserts.assertNotNull(realm, "realm");

        TokenRequest request = new TokenRequest(client, getTokenUrl());
        request.addParameter(KEY_CLIENT_ID, clientId);
        request.addParameter(KEY_GRANT_TYPE, "http://auth0.com/oauth/grant-type/password-realm");
        request.addParameter(KEY_USERNAME, emailOrUsername);
        request.addParameter(KEY_PASSWORD, password);
        request.addParameter(KEY_REALM, realm);
        addSecret(request, true);
        return request;
    }

    /**
     * Creates a login request using the Passwordless grant type.
     * Confidential clients (Regular Web Apps) <strong>must</strong> have a client secret configured on this {@code AuthAPI} instance.
     *
     * <pre>
     * {@code
     * try {
     *      TokenHolder result = authAPI.exchangePasswordlessOtp("user@domain.com", "email", new char[]{'c','o','d','e'})
     *          .execute()
     *          .getBody();
     * } catch (Auth0Exception e) {
     *      // Something happened
     * }
     * }
     * </pre>
     *
     * @param emailOrPhone The email or phone number of the user. Must not be null.
     * @param realm The realm to use. Typically "email" or "sms", unless using a custom Passwordless connection. Must not be null.
     * @param otp The one-time password used to authenticate using Passwordless connections. Must not be null.
     *
     * @return A request to configure and execute
     *
     * @see <a href="https://auth0.com/docs/connections/passwordless/reference/relevant-api-endpoints">Using Passwordless APIs</a>
     * @see <a href="https://auth0.com/docs/api/authentication#authenticate-user">Passwordless Authenticate User API docs</a>
     * @see com.auth0.client.auth.AuthAPI#startPasswordlessEmailFlow(String, PasswordlessEmailType)
     * @see com.auth0.client.auth.AuthAPI#startPasswordlessSmsFlow(String)
     */
    public TokenRequest exchangePasswordlessOtp(String emailOrPhone, String realm, char[] otp) {
        Asserts.assertNotNull(emailOrPhone, "emailOrPhone");
        Asserts.assertNotNull(realm, "realm");
        Asserts.assertNotNull(otp, "otp");

        TokenRequest request = new TokenRequest(client, getTokenUrl());
        request.addParameter(KEY_CLIENT_ID, clientId);
        request.addParameter(KEY_GRANT_TYPE, "http://auth0.com/oauth/grant-type/passwordless/otp");
        request.addParameter(KEY_USERNAME, emailOrPhone);
        request.addParameter(KEY_REALM, realm);
        request.addParameter(KEY_OTP, otp);
        addSecret(request, false);
        return request;
    }

    /**
     * Creates a request to get a Token for the given audience using the 'Client Credentials' grant.
     * Default used realm is defined in the "API Authorization Settings" in the account's advanced settings in the Auth0 Dashboard.
     * <strong>This operation requires that a client secret be configured for the {@code AuthAPI} client.</strong>
     * <pre>
     * {@code
     * try {
     *      TokenHolder result = authAPI.requestToken("https://myapi.me.auth0.com/users")
     *          .setRealm("my-realm")
     *          .execute()
     *          .getBody();
     * } catch (Auth0Exception e) {
     *      //Something happened
     * }
     * }
     * </pre>
     *
     * @see <a href="https://auth0.com/docs/api/authentication#client-credentials-flow">Client Credentials Flow API docs</a>
     * @param audience the audience of the API to request access to.
     * @return a Request to configure and execute.
     */
    public TokenRequest requestToken(String audience) {
        Asserts.assertNotNull(audience, "audience");

        TokenRequest request = new TokenRequest(client, getTokenUrl());
        request.addParameter(KEY_CLIENT_ID, clientId);
        request.addParameter(KEY_GRANT_TYPE, "client_credentials");
        request.addParameter(KEY_AUDIENCE, audience);
        addSecret(request, true);
        return request;
    }

    /**
     * Creates a request to revoke an existing Refresh Token.
     * Confidential clients (Regular Web Apps) <strong>must</strong> have a client secret configured on this {@code AuthAPI} instance.
     * <pre>
     * {@code
     * try {
     *      authAPI.revokeToken("ej2E8zNEzjrcSD2edjaE")
     *          .execute();
     * } catch (Auth0Exception e) {
     *      //Something happened
     * }
     * }
     * </pre>
     *
     * @see <a href="https://auth0.com/docs/api/authentication#revoke-refresh-token">Revoke refresh token API docs</a>
     * @param refreshToken the refresh token to revoke.
     * @return a Request to execute.
     */
    public Request<Void> revokeToken(String refreshToken) {
        Asserts.assertNotNull(refreshToken, "refresh token");

        String url = baseUrl
                .newBuilder()
                .addPathSegment(PATH_OAUTH)
                .addPathSegment(PATH_REVOKE)
                .build()
                .toString();
        VoidRequest request = new VoidRequest(client, null, url, HttpMethod.POST);
        request.addParameter(KEY_CLIENT_ID, clientId);
        request.addParameter(KEY_TOKEN, refreshToken);
        addSecret(request, false);
        return request;
    }


    /**
     * Creates a request to renew the authentication and get fresh new credentials using a valid Refresh Token and the
     * {@code refresh_token} grant.
     * Confidential clients (Regular Web Apps) <strong>must</strong> have a client secret configured on this {@code AuthAPI} instance.
     *
     * <pre>
     * {@code
     * try {
     *      TokenHolder result = authAPI.renewAuth("ej2E8zNEzjrcSD2edjaE")
     *          .execute()
     *          .getBody();
     * } catch (Auth0Exception e) {
     *      //Something happened
     * }
     * }
     * </pre>
     *
     * @see <a href="https://auth0.com/docs/api/authentication#refresh-token">Refresh Token API docs</a>
     * @param refreshToken the refresh token to use to get fresh new credentials.
     * @return a Request to configure and execute.
     */
    public TokenRequest renewAuth(String refreshToken) {
        Asserts.assertNotNull(refreshToken, "refresh token");

        TokenRequest request = new TokenRequest(client, getTokenUrl());
        request.addParameter(KEY_CLIENT_ID, clientId);
        request.addParameter(KEY_GRANT_TYPE, "refresh_token");
        request.addParameter(KEY_REFRESH_TOKEN, refreshToken);
        addSecret(request, false);
        return request;
    }

    /**
     * Creates a request to exchange the code obtained in the /authorize call using the 'Authorization Code' grant.
     * This operation requires the {@code AuthAPI} instance to have a client secret configured.
     * <pre>
     * {@code
     * try {
     *      TokenHolder result = authAPI.exchangeCode("SnWoFLMzApDskr", "https://me.auth0.com/callback")
     *          .setScope("openid name nickname")
     *          .execute()
     *          .getBody();
     * } catch (Auth0Exception e) {
     *      //Something happened
     * }
     * }
     * </pre>
     *
     * @see <a href="https://auth0.com/docs/api/authentication#authorization-code-flow">Authorization Code Flow API docs</a>
     * @param code        the authorization code received from the /authorize call.
     * @param redirectUri the redirect uri sent on the /authorize call.
     * @return a Request to configure and execute.
     */
    public TokenRequest exchangeCode(String code, String redirectUri) {
        return exchangeCode(code, redirectUri, true);
    }

    /**
     * Creates a request to exchange the code obtained from the {@code /authorize} call using the Authorization Code
     * with PKCE grant.
     * Confidential clients (Regular Web Apps) <strong>must</strong> have a client secret configured on this {@code AuthAPI} instance.
     * <pre>
     * {@code
     * AuthAPI auth = AuthAPI.newBuilder("DOMAIN", "CLIENT-ID", "CLIENT-SECRET").build();
     *
     * SecureRandom sr = new SecureRandom();
     * byte[] code = new byte[32];
     * sr.nextBytes(code);
     * String verifier = Base64.getUrlEncoder().withoutPadding().encodeToString(code);
     *
     * byte[] bytes = verifier.getBytes("US-ASCII");
     * MessageDigest md = MessageDigest.getInstance("SHA-256");
     * md.update(bytes, 0, bytes.length);
     * byte[] digest = md.digest();
     * String challenge = Base64.getUrlEncoder().withoutPadding().encodeToString(digest);
     *
     * // generate authorize URL with code challenge derived from verifier
     * String url = auth.authorizeUrl("https://me.auth0.com/callback")
     *      .withCodeChallenge(challenge)
     *      .build();
     *
     * // on redirect, exchange code and verify challenge
     * try {
     *      TokenHolder result = auth.exchangeCodeWithVerifier("CODE", verifier, "https://me.auth0.com/callback")
     *          .setScope("openid name nickname")
     *          .execute();
     * } catch (Auth0Exception e) {
     *      // Something happened
     * }
     * }
     * </pre>
     *
     * @param code the authorization code received from the {@code /authorize} call.
     * @param verifier the cryptographically random key that was used to generate the {@code code_challenge} passed to
     *                 {@code /authorize}
     * @param redirectUri the redirect uri sent on the /authorize call.
     * @return a Request to configure and execute.
     */
    public TokenRequest exchangeCodeWithVerifier(String code, String verifier, String redirectUri) {
        Asserts.assertNotNull(code, "code");
        Asserts.assertNotNull(redirectUri, "redirect uri");
        Asserts.assertNotNull(verifier, "verifier");

        TokenRequest request = exchangeCode(code, redirectUri, false);
        request.addParameter("code_verifier", verifier);
        return request;
    }

    /**
     * Create a request to send an email containing a link or a code to begin authentication with Passwordless connections.
     * Confidential clients (Regular Web Apps) <strong>must</strong> have a client secret configured on this {@code AuthAPI} instance.
     * <pre>
     * {@code
     * try {
     *      PasswordlessEmailResponse result = authAPI.startPasswordlessEmailFlow("user@domain.com", PasswordlessEmailType.CODE)
     *          .execute()
     *          .getBody();
     * } catch (Auth0Exception e) {
     *      // Something happened
     * }
     * }
     * </pre>
     *
     * @param email the email address to send the code or link to. Must not be null.
     * @param type the type of the passwordless email request. Must not be null.
     *
     * @return a Request to configure and execute.
     *
     * @see <a href="https://auth0.com/docs/connections/passwordless/guides/email-otp">Passwordless Authentication with Email documentation</a>
     * @see <a href="https://auth0.com/docs/api/authentication#get-code-or-link">Get code or link API reference documentation</a>
     */
    public CustomRequest<PasswordlessEmailResponse> startPasswordlessEmailFlow(String email, PasswordlessEmailType type) {
        Asserts.assertNotNull(email, "email");
        Asserts.assertNotNull(type, "type");

        String url = baseUrl
                .newBuilder()
                .addPathSegment(PATH_PASSWORDLESS)
                .addPathSegment(PATH_START)
                .build()
                .toString();

        CustomRequest<PasswordlessEmailResponse> request = new CustomRequest<>(client, null, url, HttpMethod.POST, new TypeReference<PasswordlessEmailResponse>() {
        });
        request.addParameter(KEY_CLIENT_ID, clientId);
        request.addParameter(KEY_CONNECTION, "email");
        request.addParameter(KEY_EMAIL, email);
        request.addParameter("send", type.getType());
        addSecret(request, false);
        return request;
    }

    /**
     * Create a request to send a text message containing a code to begin authentication with Passwordless connections.
     * Confidential clients (Regular Web Apps) <strong>must</strong> have a client secret configured on this {@code AuthAPI} instance.
     * <pre>
     * {@code
     * try {
     *      PasswordlessSmsResponse result = authAPI.startPasswordlessSmsFlow("+16511234567")
     *          .execute()
     *          .getBody();
     * } catch (Auth0Exception e) {
     *      // Something happened
     * }
     * }
     * </pre>
     *
     * @param phoneNumber The phone number to send the code to. Must not be null.
     *
     * @return a Request to configure and execute.
     *
     * @see <a href="https://auth0.com/docs/connections/passwordless/guides/sms-otp">Passwordless Authentication with SMS documentation</a>
     * @see <a href="https://auth0.com/docs/api/authentication#get-code-or-link">Get code or link API reference documentation</a>
     */
    public CustomRequest<PasswordlessSmsResponse> startPasswordlessSmsFlow(String phoneNumber) {
        Asserts.assertNotNull(phoneNumber, "phoneNumber");

        String url = baseUrl
                .newBuilder()
                .addPathSegment(PATH_PASSWORDLESS)
                .addPathSegment(PATH_START)
                .build()
                .toString();

        CustomRequest<PasswordlessSmsResponse> request = new CustomRequest<>(client, null, url, HttpMethod.POST, new TypeReference<PasswordlessSmsResponse>() {
        });
        request.addParameter(KEY_CLIENT_ID, clientId);
        request.addParameter(KEY_CONNECTION, "sms");
        request.addParameter("phone_number", phoneNumber);
        addSecret(request, false);
        return request;
    }

    /**
     * Creates a request to exchange the mfa token and one-time password (OTP) to authenticate a user with an MFA OTP Authenticator.
     * Confidential clients (Regular Web Apps) <strong>must</strong> have a client secret configured on this {@code AuthAPI} instance.
     * <pre>
     * {@code
     * try {
     *      TokenHolder result = authAPI.exchangeMfaOtp("the-mfa-token", new char[]{'a','n','o','t','p'})
     *          .execute()
     *          .getBody();
     * } catch (Auth0Exception e) {
     *      //Something happened
     * }
     * }
     * </pre>
     *
     * @param mfaToken the mfa_token received from the mfa_required error that occurred during login. Must not be null.
     * @param otp      the OTP Code provided by the user. Must not be null.
     *
     * @return a Request to configure and execute.
     *
     * @see <a href="https://auth0.com/docs/api/authentication#verify-with-one-time-password-otp-">Verify with one-time password (OTP) API documentation</a>
     */
    public TokenRequest exchangeMfaOtp(String mfaToken, char[] otp) {
        Asserts.assertNotNull(mfaToken, "mfa token");
        Asserts.assertNotNull(otp, "otp");

        TokenRequest request = new TokenRequest(client, getTokenUrl());
        request.addParameter(KEY_CLIENT_ID, clientId);
        request.addParameter(KEY_GRANT_TYPE, "http://auth0.com/oauth/grant-type/mfa-otp");
        request.addParameter(KEY_MFA_TOKEN, mfaToken);
        request.addParameter(KEY_OTP, otp);
        addSecret(request, false);
        return request;
    }

    private TokenRequest exchangeCode(String code, String redirectUri, boolean secretRequired) {
        Asserts.assertNotNull(code, "code");
        Asserts.assertNotNull(redirectUri, "redirect uri");

        TokenRequest request = new TokenRequest(client, getTokenUrl());
        request.addParameter(KEY_CLIENT_ID, clientId);
        request.addParameter(KEY_GRANT_TYPE, "authorization_code");
        request.addParameter("code", code);
        request.addParameter("redirect_uri", redirectUri);
        addSecret(request, secretRequired);
        return request;
    }

    private String getTokenUrl() {
        return baseUrl
            .newBuilder()
            .addPathSegment(PATH_OAUTH)
            .addPathSegment(PATH_TOKEN)
            .build()
            .toString();
    }

    private void addSecret(CustomRequest<?> request, boolean required) {
        if (required && Objects.isNull(this.clientSecret)) {
            throw new IllegalStateException("A client secret is required for this operation");
        }
        if (Objects.nonNull(this.clientSecret)) {
            request.addParameter(KEY_CLIENT_SECRET, this.clientSecret);
        }
    }

    /**
     * Builder for {@link AuthAPI} API client instances.
     */
    public static class Builder {
        private final String domain;
        private final String clientId;
        private final String clientSecret;
        private Auth0HttpClient httpClient;

        /**
         * Create a new Builder
         * @param domain the domain of the tenant.
         * @param clientId the client ID of the Auth0 application.
         * @param clientSecret the client secret of the Auth0 application.
         */
        public Builder(String domain, String clientId, String clientSecret) {
            this.domain = domain;
            this.clientId = clientId;
            this.clientSecret = clientSecret;
        }

        public Builder(String domain, String clientId) {
            this.domain = domain;
            this.clientId = clientId;
            this.clientSecret = null;
        }

        /**
         * Configure the client with an {@link Auth0HttpClient}.
         * @param httpClient the HTTP client to use when making requests.
         * @return the builder instance.
         * @see DefaultHttpClient
         */
        public Builder withHttpClient(Auth0HttpClient httpClient) {
            this.httpClient = httpClient;
            return this;
        }

        /**
         * Builds an {@link AuthAPI} instance using this builder's configuration.
         * @return the configured {@code AuthAPI} instance.
         */
        public AuthAPI build() {
            return new AuthAPI(domain, clientId, clientSecret,
                Objects.nonNull(httpClient) ? httpClient : DefaultHttpClient.newBuilder().build());
        }
    }
}

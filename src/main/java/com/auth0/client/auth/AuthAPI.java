package com.auth0.client.auth;

import com.auth0.json.auth.UserInfo;
import com.auth0.net.*;
import com.auth0.utils.Asserts;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;

/**
 * Class that provides an implementation of some of the Authentication and Authorization API methods defined in https://auth0.com/docs/api/authentication.
 * To begin create a new instance of {@link #AuthAPI(String, String, String)} using the tenant domain, and the Application's client id and client secret.
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

    private static final String PATH_OAUTH = "oauth";
    private static final String PATH_TOKEN = "token";
    private static final String PATH_DBCONNECTIONS = "dbconnections";
    private static final String PATH_REVOKE = "revoke";

    private final OkHttpClient client;
    private final String clientId;
    private final String clientSecret;
    private final HttpUrl baseUrl;
    private final TelemetryInterceptor telemetry;
    private final HttpLoggingInterceptor logging;

    /**
     * Create a new instance with the given tenant's domain, application's client id and client secret. These values can be obtained at https://manage.auth0.com/#/applications/{YOUR_CLIENT_ID}/settings.
     *
     * @param domain       tenant's domain.
     * @param clientId     the application's client id.
     * @param clientSecret the application's client secret.
     */
    public AuthAPI(String domain, String clientId, String clientSecret) {
        Asserts.assertNotNull(domain, "domain");
        Asserts.assertNotNull(clientId, "client id");
        Asserts.assertNotNull(clientSecret, "client secret");

        this.baseUrl = createBaseUrl(domain);
        if (baseUrl == null) {
            throw new IllegalArgumentException("The domain had an invalid format and couldn't be parsed as an URL.");
        }
        this.clientId = clientId;
        this.clientSecret = clientSecret;

        telemetry = new TelemetryInterceptor();
        logging = new HttpLoggingInterceptor();
        logging.setLevel(Level.NONE);
        client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor(telemetry)
                .build();
    }

    /**
     * Avoid sending Telemetry data in every request to the Auth0 servers.
     */
    public void doNotSendTelemetry() {
        telemetry.setEnabled(false);
    }

    /**
     * Whether to enable or not the current HTTP Logger for every Request, Response and other sensitive information.
     *
     * @param enabled whether to enable the HTTP logger or not.
     */
    public void setLoggingEnabled(boolean enabled) {
        logging.setLevel(enabled ? Level.BODY : Level.NONE);
    }

    //Visible for Testing
    OkHttpClient getClient() {
        return client;
    }

    //Visible for Testing
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

    /**
     * Creates an instance of the {@link AuthorizeUrlBuilder} with the given redirect url.
     * i.e.:
     * <pre>
     * {@code
     * AuthAPI auth = new AuthAPI("me.auth0.com", "B3c6RYhk1v9SbIJcRIOwu62gIUGsnze", "2679NfkaBn62e6w5E8zNEzjr-yWfkaBne");
     * String url = auth.authorizeUrl("https://me.auth0.com/callback")
     *      .withConnection("facebook")
     *      .withAudience("https://api.me.auth0.com/users")
     *      .withScope("openid contacts")
     *      .withState("my-custom-state")
     *      .build();
     * }
     * </pre>
     *
     * @param redirectUri the redirect_uri value to set, white-listed in the Application settings. Must be already URL Encoded.
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
     * AuthAPI auth = new AuthAPI("me.auth0.com", "B3c6RYhk1v9SbIJcRIOwu62gIUGsnze", "2679NfkaBn62e6w5E8zNEzjr-yWfkaBne");
     * String url = auth.logoutUrl("https://me.auth0.com/home", true)
     *      .useFederated(true)
     *      .withAccessToken("A9CvPwFojaBIA9CvI");
     * }
     * </pre>
     *
     * @param returnToUrl the redirect_uri value to set, white-listed in the Application settings. Must be already URL Encoded.
     * @param setClientId whether the client_id value must be set or not. This affects the white-list that the Auth0's Dashboard uses to validate the returnTo url.
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
     * AuthAPI auth = new AuthAPI("me.auth0.com", "B3c6RYhk1v9SbIJcRIOwu62gIUGsnze", "2679NfkaBn62e6w5E8zNEzjr-yWfkaBne");
     * try {
     *      UserInfo result = auth.userInfo("A9CvPwFojaBIA9CvI").execute();
     * } catch (Auth0Exception e) {
     *      //Something happened
     * }
     * }
     * </pre>
     *
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
        CustomRequest<UserInfo> request = new CustomRequest<>(client, url, "GET", new TypeReference<UserInfo>() {
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
     * AuthAPI auth = new AuthAPI("me.auth0.com", "B3c6RYhk1v9SbIJcRIOwu62gIUGsnze", "2679NfkaBn62e6w5E8zNEzjr-yWfkaBne");
     * try {
     *      auth.resetPassword("me@auth0.com", "db-connection").execute();
     * } catch (Auth0Exception e) {
     *      //Something happened
     * }
     * }
     * </pre>
     *
     * @param email      the email associated to the database user.
     * @param connection the database connection where the user was created.
     * @return a Request to execute.
     */
    public Request resetPassword(String email, String connection) {
        Asserts.assertNotNull(email, "email");
        Asserts.assertNotNull(connection, "connection");

        String url = baseUrl
                .newBuilder()
                .addPathSegment(PATH_DBCONNECTIONS)
                .addPathSegment("change_password")
                .build()
                .toString();
        VoidRequest request = new VoidRequest(client, url, "POST");
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
     * AuthAPI auth = new AuthAPI("me.auth0.com", "B3c6RYhk1v9SbIJcRIOwu62gIUGsnze", "2679NfkaBn62e6w5E8zNEzjr-yWfkaBne");
     * try {
     *      Map<String, String> fields = new HashMap<String, String>();
     *      fields.put("age", "25);
     *      fields.put("city", "Buenos Aires");
     *      auth.signUp("me@auth0.com", "myself", "topsecret", "db-connection")
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
     */
    public SignUpRequest signUp(String email, String username, String password, String connection) {
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
     * AuthAPI auth = new AuthAPI("me.auth0.com", "B3c6RYhk1v9SbIJcRIOwu62gIUGsnze", "2679NfkaBn62e6w5E8zNEzjr-yWfkaBne");
     * try {
     *      Map<String, String> fields = new HashMap<String, String>();
     *      fields.put("age", "25);
     *      fields.put("city", "Buenos Aires");
     *      auth.signUp("me@auth0.com", "topsecret", "db-connection")
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
     */
    public SignUpRequest signUp(String email, String password, String connection) {
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
     * AuthAPI auth = new AuthAPI("me.auth0.com", "B3c6RYhk1v9SbIJcRIOwu62gIUGsnze", "2679NfkaBn62e6w5E8zNEzjr-yWfkaBne");
     * try {
     *      TokenHolder result = auth.login("me@auth0.com", "topsecret")
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
     */
    public AuthRequest login(String emailOrUsername, String password) {
        Asserts.assertNotNull(emailOrUsername, "email or username");
        Asserts.assertNotNull(password, "password");

        String url = baseUrl
                .newBuilder()
                .addPathSegment(PATH_OAUTH)
                .addPathSegment(PATH_TOKEN)
                .build()
                .toString();
        TokenRequest request = new TokenRequest(client, url);
        request.addParameter(KEY_CLIENT_ID, clientId);
        request.addParameter(KEY_CLIENT_SECRET, clientSecret);
        request.addParameter(KEY_GRANT_TYPE, "password");
        request.addParameter(KEY_USERNAME, emailOrUsername);
        request.addParameter(KEY_PASSWORD, password);
        return request;
    }

    /**
     * Creates a log in request using the 'Password Realm' grant and the given credentials.
     * Default used realm and audience are defined in the "API Authorization Settings" in the account's advanced settings in the Auth0 Dashboard.
     * <pre>
     * {@code
     * AuthAPI auth = new AuthAPI("me.auth0.com", "B3c6RYhk1v9SbIJcRIOwu62gIUGsnze", "2679NfkaBn62e6w5E8zNEzjr-yWfkaBne");
     * try {
     *      TokenHolder result = auth.login("me@auth0.com", "topsecret", "my-realm")
     *          .setAudience("https://myapi.me.auth0.com/users")
     *          .execute();
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
     */
    public AuthRequest login(String emailOrUsername, String password, String realm) {
        Asserts.assertNotNull(emailOrUsername, "email or username");
        Asserts.assertNotNull(password, "password");
        Asserts.assertNotNull(realm, "realm");

        String url = baseUrl
                .newBuilder()
                .addPathSegment(PATH_OAUTH)
                .addPathSegment(PATH_TOKEN)
                .build()
                .toString();
        TokenRequest request = new TokenRequest(client, url);
        request.addParameter(KEY_CLIENT_ID, clientId);
        request.addParameter(KEY_CLIENT_SECRET, clientSecret);
        request.addParameter(KEY_GRANT_TYPE, "http://auth0.com/oauth/grant-type/password-realm");
        request.addParameter(KEY_USERNAME, emailOrUsername);
        request.addParameter(KEY_PASSWORD, password);
        request.addParameter("realm", realm);
        return request;
    }

    /**
     * Creates a request to get a Token for the given audience using the 'Client Credentials' grant.
     * Default used realm is defined in the "API Authorization Settings" in the account's advanced settings in the Auth0 Dashboard.
     * <pre>
     * {@code
     * AuthAPI auth = new AuthAPI("me.auth0.com", "B3c6RYhk1v9SbIJcRIOwu62gIUGsnze", "2679NfkaBn62e6w5E8zNEzjr-yWfkaBne");
     * try {
     *      TokenHolder result = auth.requestToken("https://myapi.me.auth0.com/users")
     *          .setRealm("my-realm")
     *          .execute();
     * } catch (Auth0Exception e) {
     *      //Something happened
     * }
     * }
     * </pre>
     *
     * @param audience the audience of the API to request access to.
     * @return a Request to configure and execute.
     */
    public AuthRequest requestToken(String audience) {
        Asserts.assertNotNull(audience, "audience");

        String url = baseUrl
                .newBuilder()
                .addPathSegment(PATH_OAUTH)
                .addPathSegment(PATH_TOKEN)
                .build()
                .toString();
        TokenRequest request = new TokenRequest(client, url);
        request.addParameter(KEY_CLIENT_ID, clientId);
        request.addParameter(KEY_CLIENT_SECRET, clientSecret);
        request.addParameter(KEY_GRANT_TYPE, "client_credentials");
        request.addParameter(KEY_AUDIENCE, audience);
        return request;
    }

    /**
     * Creates a request to revoke an existing Refresh Token.
     * <pre>
     * {@code
     * AuthAPI auth = new AuthAPI("me.auth0.com", "B3c6RYhk1v9SbIJcRIOwu62gIUGsnze", "2679NfkaBn62e6w5E8zNEzjr-yWfkaBne");
     * try {
     *      auth.revokeToken("ej2E8zNEzjrcSD2edjaE")
     *          .execute();
     * } catch (Auth0Exception e) {
     *      //Something happened
     * }
     * }
     * </pre>
     *
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
        VoidRequest request = new VoidRequest(client, url, "POST");
        request.addParameter(KEY_CLIENT_ID, clientId);
        request.addParameter(KEY_CLIENT_SECRET, clientSecret);
        request.addParameter(KEY_TOKEN, refreshToken);
        return request;
    }


    /**
     * Creates a request to renew the authentication and get fresh new credentials using a valid Refresh Token and the 'refresh_token' grant.
     * <pre>
     * {@code
     * AuthAPI auth = new AuthAPI("me.auth0.com", "B3c6RYhk1v9SbIJcRIOwu62gIUGsnze", "2679NfkaBn62e6w5E8zNEzjr-yWfkaBne");
     * try {
     *      TokenHolder result = auth.renewAuth("ej2E8zNEzjrcSD2edjaE")
     *          .execute();
     * } catch (Auth0Exception e) {
     *      //Something happened
     * }
     * }
     * </pre>
     *
     * @param refreshToken the refresh token to use to get fresh new credentials.
     * @return a Request to configure and execute.
     */
    public AuthRequest renewAuth(String refreshToken) {
        Asserts.assertNotNull(refreshToken, "refresh token");

        String url = baseUrl
                .newBuilder()
                .addPathSegment(PATH_OAUTH)
                .addPathSegment(PATH_TOKEN)
                .build()
                .toString();
        TokenRequest request = new TokenRequest(client, url);
        request.addParameter(KEY_CLIENT_ID, clientId);
        request.addParameter(KEY_CLIENT_SECRET, clientSecret);
        request.addParameter(KEY_GRANT_TYPE, "refresh_token");
        request.addParameter(KEY_REFRESH_TOKEN, refreshToken);
        return request;
    }

    /**
     * Creates a request to exchange the code obtained in the /authorize call using the 'Authorization Code' grant.
     * <pre>
     * {@code
     * AuthAPI auth = new AuthAPI("me.auth0.com", "B3c6RYhk1v9SbIJcRIOwu62gIUGsnze", "2679NfkaBn62e6w5E8zNEzjr-yWfkaBne");
     * try {
     *      TokenHolder result = auth.exchangeCode("SnWoFLMzApDskr", "https://me.auth0.com/callback")
     *          .setScope("openid name nickname")
     *          .execute();
     * } catch (Auth0Exception e) {
     *      //Something happened
     * }
     * }
     * </pre>
     *
     * @param code        the authorization code received from the /authorize call.
     * @param redirectUri the redirect uri sent on the /authorize call.
     * @return a Request to configure and execute.
     */
    public AuthRequest exchangeCode(String code, String redirectUri) {
        Asserts.assertNotNull(code, "code");
        Asserts.assertNotNull(redirectUri, "redirect uri");

        String url = baseUrl
                .newBuilder()
                .addPathSegment(PATH_OAUTH)
                .addPathSegment(PATH_TOKEN)
                .build()
                .toString();
        TokenRequest request = new TokenRequest(client, url);
        request.addParameter(KEY_CLIENT_ID, clientId);
        request.addParameter(KEY_CLIENT_SECRET, clientSecret);
        request.addParameter(KEY_GRANT_TYPE, "authorization_code");
        request.addParameter("code", code);
        request.addParameter("redirect_uri", redirectUri);
        return request;
    }
}

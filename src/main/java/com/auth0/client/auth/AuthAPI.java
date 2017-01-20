package com.auth0.client.auth;

import com.auth0.Asserts;
import com.auth0.json.auth.UserInfo;
import com.auth0.net.*;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;

public class AuthAPI {

    private static final String KEY_CLIENT_ID = "client_id";
    private static final String KEY_CLIENT_SECRET = "client_secret";
    private static final String KEY_GRANT_TYPE = "grant_type";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_AUDIENCE = "audience";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_CONNECTION = "connection";

    private static final String PATH_OAUTH = "oauth";
    private static final String PATH_TOKEN = "token";
    private static final String PATH_DBCONNECTIONS = "dbconnections";

    private final OkHttpClient client;
    private final String clientId;
    private final String clientSecret;
    private final String baseUrl;

    public AuthAPI(String domain, String clientId, String clientSecret) {
        Asserts.assertNotNull(domain, "domain");
        Asserts.assertNotNull(clientId, "client id");
        Asserts.assertNotNull(clientSecret, "client secret");

        baseUrl = createBaseUrl(domain);
        if (baseUrl == null) {
            throw new IllegalArgumentException("The domain had an invalid format and couldn't be parsed as an URL.");
        }
        this.clientId = clientId;
        this.clientSecret = clientSecret;

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(Level.NONE);
        client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();
    }

    String getBaseUrl() {
        return baseUrl;
    }

    private String createBaseUrl(String domain) {
        String url = domain;
        if (!domain.startsWith("https://") && !domain.startsWith("http://")) {
            url = "https://" + domain;
        }
        HttpUrl baseUrl = HttpUrl.parse(url);
        return baseUrl == null ? null : baseUrl.newBuilder().build().toString();
    }

    /**
     * Creates a new instance of the {@link AuthorizeUrlBuilder} with the given redirect url.
     *
     * @param redirectUri the redirect_uri value to set, white-listed in the client settings. Must be already URL Encoded.
     * @return a new instance of the {@link AuthorizeUrlBuilder} to configure.
     */
    public AuthorizeUrlBuilder authorize(String redirectUri) {
        Asserts.assertNotNull(redirectUri, "redirect uri");

        return AuthorizeUrlBuilder.newInstance(baseUrl, clientId, redirectUri);
    }

    /**
     * Creates a new instance of the {@link AuthorizeUrlBuilder} with the given connection and redirect url parameters.
     *
     * @param returnToUrl the redirect_uri value to set, white-listed in the client settings. Must be already URL Encoded.
     * @param setClientId whether the client_id value must be set or not. This affects the white-list that the Auth0's Dashboard uses to validate the returnTo url.
     * @return a new instance of the {@link AuthorizeUrlBuilder} to configure.
     */
    public LogoutUrlBuilder logout(String returnToUrl, boolean setClientId) {
        Asserts.assertNotNull(returnToUrl, "return to url");

        return LogoutUrlBuilder.newInstance(baseUrl, clientId, returnToUrl, setClientId);
    }


    /**
     * Request the user information related to this access token.
     *
     * @param accessToken a valid access token belonging to an API signed with RS256 algorithm and containing the scope 'openid'.
     * @return a Request to execute.
     */
    public Request<UserInfo> userInfo(String accessToken) {
        Asserts.assertNotNull(accessToken, "access token");

        String url = HttpUrl.parse(baseUrl)
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
     *
     * @param email      the email associated to the database user.
     * @param connection the database connection where the user was created.
     * @return a Request to execute.
     */
    public Request resetPassword(String email, String connection) {
        Asserts.assertNotNull(email, "email");
        Asserts.assertNotNull(connection, "connection");

        String url = HttpUrl.parse(baseUrl)
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
     * Creates a new sign up request with the given credentials and database connection.
     * "Requires Username" option must be turned on in the Connection's configuration first.
     *
     * @param email      the desired user's email.
     * @param username   the desired user's username.
     * @param password   the desired user's password.
     * @param connection the database connection where the user is going to be created.
     * @return a Request to configure and execute.
     */
    public SignUpRequest signUp(String email, String username, String password, String connection) {
        Asserts.assertNotNull(username, "username");

        VoidRequest request = (VoidRequest) this.signUp(email, password, connection);
        request.addParameter(KEY_USERNAME, username);
        return request;
    }

    /**
     * Creates a new sign up request with the given credentials and database connection.
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

        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment(PATH_DBCONNECTIONS)
                .addPathSegment("signup")
                .build()
                .toString();
        VoidRequest request = new VoidRequest(client, url, "POST");
        request.addParameter(KEY_CLIENT_ID, clientId);
        request.addParameter(KEY_EMAIL, email);
        request.addParameter(KEY_PASSWORD, password);
        request.addParameter(KEY_CONNECTION, connection);
        return request;
    }

    /**
     * Creates a new log in request using the 'Authorization Code' grant and the given code and redirect uri parameters.
     *
     * @param code        the authorization code received from the /authorize call.
     * @param redirectUri the redirect uri sent on the /authorize call.
     * @return a Request to configure and execute.
     */
    public AuthRequest loginWithAuthorizationCode(String code, String redirectUri) {
        Asserts.assertNotNull(code, "code");
        Asserts.assertNotNull(redirectUri, "redirect uri");

        String url = HttpUrl.parse(baseUrl)
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

    /**
     * Creates a new log in request using the 'Password' grant and the given credentials.
     *
     * @param emailOrUsername the identity of the user.
     * @param password        the password of the user.
     * @return a Request to configure and execute.
     */
    public AuthRequest loginWithPassword(String emailOrUsername, String password) {
        Asserts.assertNotNull(emailOrUsername, "email or username");
        Asserts.assertNotNull(password, "password");

        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment(PATH_OAUTH)
                .addPathSegment(PATH_TOKEN)
                .build()
                .toString();
        TokenRequest request = new TokenRequest(client, url);
        request.addParameter(KEY_CLIENT_ID, clientId);
        request.addParameter(KEY_CLIENT_SECRET, clientSecret);
        request.addParameter(KEY_GRANT_TYPE, KEY_PASSWORD);
        request.addParameter(KEY_USERNAME, emailOrUsername);
        request.addParameter(KEY_PASSWORD, password);
        return request;
    }

    /**
     * Creates a new log in request using the 'Password Realm' grant and the given credentials.
     * Default used realm and audience are defined in the "API Authorization Settings" in the account's advanced settings in the Auth0 Dashboard.
     *
     * @param emailOrUsername the identity of the user.
     * @param password        the password of the user.
     * @return a Request to configure and execute.
     */
    public AuthRequest loginWithPasswordRealm(String emailOrUsername, String password, String realm) {
        Asserts.assertNotNull(emailOrUsername, "email or username");
        Asserts.assertNotNull(password, "password");
        Asserts.assertNotNull(realm, "realm");

        String url = HttpUrl.parse(baseUrl)
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
     * Creates a new log in request using the 'Client Credentials' grant for the given audience.
     * Default used realm and audience are defined in the "API Authorization Settings" in the account's advanced settings in the Auth0 Dashboard.
     *
     * @param audience the audience of the API to request access to.
     * @return a Request to configure and execute.
     */
    public AuthRequest loginWithClientCredentials(String audience) {
        Asserts.assertNotNull(audience, "audience");

        String url = HttpUrl.parse(baseUrl)
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
}

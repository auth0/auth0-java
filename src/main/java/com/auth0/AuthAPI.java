package com.auth0;

import com.auth0.json.UserInfo;
import com.auth0.net.*;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;

public class AuthAPI {

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
        logging.setLevel(Level.BODY);
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
     * Creates a new instance of the {@link AuthorizeUrlBuilder} with the given connection and redirect url parameters.
     *
     * @param connection  the connection value to set
     * @param redirectUri the redirect_uri value to set. Must be already URL Encoded.
     * @return a new instance of the {@link AuthorizeUrlBuilder} to configure.
     */
    public AuthorizeUrlBuilder authorize(String connection, String redirectUri) {
        Asserts.assertNotNull(connection, "connection");
        Asserts.assertNotNull(redirectUri, "redirect uri");

        return AuthorizeUrlBuilder.newInstance(baseUrl, clientId, redirectUri)
                .withConnection(connection);
    }

    /**
     * Creates a new instance of the {@link AuthorizeUrlBuilder} with the given connection and redirect url parameters.
     *
     * @param returnToUrl the redirect_uri value to set. Must be already URL Encoded.
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
        CustomRequest<UserInfo> request = new CustomRequest<>(client, url, "GET", UserInfo.class);
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
                .addPathSegment("dbconnections")
                .addPathSegment("change_password")
                .build()
                .toString();
        VoidRequest request = new VoidRequest(client, url, "POST");
        request.addParameter("client_id", clientId);
        request.addParameter("email", email);
        request.addParameter("connection", connection);
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
        request.addParameter("username", username);
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
                .addPathSegment("dbconnections")
                .addPathSegment("signup")
                .build()
                .toString();
        VoidRequest request = new VoidRequest(client, url, "POST");
        request.addParameter("client_id", clientId);
        request.addParameter("email", email);
        request.addParameter("password", password);
        request.addParameter("connection", connection);
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

        TokenRequest request = (TokenRequest) this.loginWithAuthorizationCode(code);
        request.addParameter("redirect_uri", redirectUri);
        return request;
    }

    /**
     * Creates a new log in request using the 'Authorization Code' grant and the given code parameter.
     *
     * @param code the authorization code received from the /authorize call.
     * @return a Request to configure and execute.
     */
    public AuthRequest loginWithAuthorizationCode(String code) {
        Asserts.assertNotNull(code, "code");

        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("oauth")
                .addPathSegment("token")
                .build()
                .toString();
        TokenRequest request = new TokenRequest(client, url);
        request.addParameter("client_id", clientId);
        request.addParameter("client_secret", clientSecret);
        request.addParameter("grant_type", "authorization_code");
        request.addParameter("code", code);
        return request;
    }

    /**
     * Creates a new log in request using the 'Password' grant and the given credentials.
     *
     * @param emailOrUsername the identity of the user.
     * @param password        the password of the user.
     * @param audience        the audience of the API to request access to.
     * @return a Request to configure and execute.
     */
    public AuthRequest loginWithPassword(String emailOrUsername, String password, String audience) {
        Asserts.assertNotNull(emailOrUsername, "email or username");
        Asserts.assertNotNull(password, "password");
        Asserts.assertNotNull(audience, "audience");

        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("oauth")
                .addPathSegment("token")
                .build()
                .toString();
        TokenRequest request = new TokenRequest(client, url);
        request.addParameter("client_id", clientId);
        request.addParameter("client_secret", clientSecret);
        request.addParameter("grant_type", "password");
        request.addParameter("username", emailOrUsername);
        request.addParameter("password", password);
        request.addParameter("audience", audience);
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
    public AuthRequest loginWithPasswordRealm(String emailOrUsername, String password) {
        Asserts.assertNotNull(emailOrUsername, "email or username");
        Asserts.assertNotNull(password, "password");

        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("oauth")
                .addPathSegment("token")
                .build()
                .toString();
        TokenRequest request = new TokenRequest(client, url);
        request.addParameter("client_id", clientId);
        request.addParameter("client_secret", clientSecret);
        request.addParameter("grant_type", "http://auth0.com/oauth/grant-type/password-realm");
        request.addParameter("username", emailOrUsername);
        request.addParameter("password", password);
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
                .addPathSegment("oauth")
                .addPathSegment("token")
                .build()
                .toString();
        TokenRequest request = new TokenRequest(client, url);
        request.addParameter("client_id", clientId);
        request.addParameter("client_secret", clientSecret);
        request.addParameter("grant_type", "client_credentials");
        request.addParameter("audience", audience);
        return request;
    }
}

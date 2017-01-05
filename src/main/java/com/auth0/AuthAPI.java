package com.auth0;

import com.auth0.json.UserInfo;
import com.auth0.net.CustomRequest;
import com.auth0.net.Request;
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

    String getBaseUrl(){
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
        return LogoutUrlBuilder.newInstance(baseUrl, clientId, returnToUrl, setClientId);
    }


    /**
     * Request the user information related to this access token.
     *
     * @param accessToken a valid access token belonging to an API signed with RS256 algorithm and containing the scope 'openid'.
     * @return a Request to configure and execute.
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
}

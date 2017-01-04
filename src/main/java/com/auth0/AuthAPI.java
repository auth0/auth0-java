package com.auth0;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;

public class AuthAPI {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private final OkHttpClient client;
    private final String domain;
    private final String clientId;
    private final String clientSecret;

    public AuthAPI(String domain, String clientId, String clientSecret) {
        Asserts.assertNotNull(domain, "domain");
        Asserts.assertNotNull(clientId, "client id");
        Asserts.assertNotNull(clientSecret, "client secret");

        this.domain = domain;
        this.clientId = clientId;
        this.clientSecret = clientSecret;

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(Level.BODY);
        client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();
    }

    /**
     * Creates a new instance of the {@link AuthorizeUrlBuilder} with the given connection and redirect url parameters.
     *
     * @param connection  the connection value to set
     * @param redirectUri the redirect_uri value to set. Must be already URL Encoded.
     * @return a new instance of the {@link AuthorizeUrlBuilder} to configure.
     */
    public AuthorizeUrlBuilder authorize(String connection, String redirectUri) {
        return AuthorizeUrlBuilder.newInstance(domain, clientId, redirectUri)
                .withConnection(connection);
    }

}

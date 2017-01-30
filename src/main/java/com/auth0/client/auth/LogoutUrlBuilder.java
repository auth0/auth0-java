package com.auth0.client.auth;

import okhttp3.HttpUrl;

import java.util.HashMap;
import java.util.Map;

import static com.auth0.utils.Asserts.assertNotNull;
import static com.auth0.utils.Asserts.assertValidUrl;

/**
 * Class that provides the methods to generate a valid Auth0 Logout Url. It's based on the https://auth0.com/docs/api/authentication#logout docs.
 */
@SuppressWarnings("WeakerAccess")
public class LogoutUrlBuilder {

    private final HttpUrl.Builder builder;
    private final HashMap<String, String> parameters;

    /**
     * Creates a new instance of the {@link LogoutUrlBuilder} using the given domain and base parameters.
     *
     * @param domain      the domain to use for this URL. Must be a valid URL.
     * @param clientId    the client_id value to set
     * @param returnToUrl the returnTo value to set. Must be already URL Encoded and must be white-listed in your Auth0's dashboard.
     * @param setClientId whether the client_id value must be set or not. This affects the white-list that the Auth0's Dashboard uses to validate the returnTo url.
     * @return a new instance of the {@link LogoutUrlBuilder} to configure.
     */
    static LogoutUrlBuilder newInstance(String domain, String clientId, String returnToUrl, boolean setClientId) {
        return new LogoutUrlBuilder(domain, setClientId ? clientId : null, returnToUrl);
    }

    private LogoutUrlBuilder(String domain, String clientId, String returnToUrl) {
        assertValidUrl(domain, "domain");
        assertNotNull(returnToUrl, "return to url");

        parameters = new HashMap<>();
        builder = HttpUrl.parse(domain).newBuilder()
                .addPathSegment("v2")
                .addPathSegment("logout")
                .addEncodedQueryParameter("returnTo", returnToUrl);
        if (clientId != null) {
            builder.addQueryParameter("client_id", clientId);
        }
    }

    /**
     * Sets the access token value.
     *
     * @param accessToken access token to set
     * @return the builder instance
     */
    public LogoutUrlBuilder withAccessToken(String accessToken) {
        assertNotNull(accessToken, "access token");
        parameters.put("access_token", accessToken);
        return this;
    }

    /**
     * Request Federated logout.
     *
     * @param federated whether or not to request Federated logout.
     * @return the builder instance
     */
    public LogoutUrlBuilder useFederated(boolean federated) {
        if (federated) {
            parameters.put("federated", "");
        } else {
            parameters.remove("federated");
        }
        return this;
    }

    /**
     * Creates a string representation of the URL with the configured parameters.
     *
     * @return the string URL
     */
    public String build() {
        for (Map.Entry<String, String> p : parameters.entrySet()) {
            builder.addQueryParameter(p.getKey(), p.getValue());
        }
        return builder.build().toString();
    }
}

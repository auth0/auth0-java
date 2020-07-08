package com.auth0.client.auth;

import okhttp3.HttpUrl;

import java.util.HashMap;
import java.util.Map;

import static com.auth0.utils.Asserts.assertNotNull;

/**
 * Class that provides the methods to generate a valid Auth0 Logout Url. It's based on the https://auth0.com/docs/api/authentication#logout docs.
 * <p>
 * This class is not thread-safe:
 * It makes use of {@link HashMap} for storing the parameters. Make sure to not call the builder methods
 * from a different or un-synchronized thread.
 */
@SuppressWarnings("WeakerAccess")
public class LogoutUrlBuilder {

    private final HttpUrl.Builder builder;
    private final Map<String, String> parameters;

    /**
     * Creates a instance of the {@link LogoutUrlBuilder} using the given domain and base parameters.
     *
     * @param baseUrl     the base url constructed from a valid domain.
     * @param clientId    the application's client_id value to set
     * @param returnToUrl the returnTo value to set. Must be already URL Encoded and must be white-listed in your Auth0's dashboard.
     * @param setClientId whether the client_id value must be set or not. This affects the white-list that the Auth0's Dashboard uses to validate the returnTo url.
     *                    If the client_id is set, the white-list is read from the Application's settings. If the client_id is not set, the white-list is read from the Tenant's settings.
     * @return a new instance of the {@link LogoutUrlBuilder} to configure.
     */
    static LogoutUrlBuilder newInstance(HttpUrl baseUrl, String clientId, String returnToUrl, boolean setClientId) {
        return new LogoutUrlBuilder(baseUrl, setClientId ? clientId : null, returnToUrl);
    }

    private LogoutUrlBuilder(HttpUrl url, String clientId, String returnToUrl) {
        assertNotNull(url, "base url");
        assertNotNull(returnToUrl, "return to url");

        parameters = new HashMap<>();
        builder = url.newBuilder()
                .addPathSegment("v2")
                .addPathSegment("logout")
                .addEncodedQueryParameter("returnTo", returnToUrl);
        if (clientId != null) {
            builder.addQueryParameter("client_id", clientId);
        }
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

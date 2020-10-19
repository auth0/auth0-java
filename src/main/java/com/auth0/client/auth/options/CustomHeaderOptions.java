package com.auth0.client.auth.options;

import java.util.HashMap;
import java.util.Map;

/**
 * Class used to supply custom headers to those request which require them.
 * <p>
 * This class is not thread-safe.
 *
 * @see <a href="https://auth0.com/docs/authorization/avoid-common-issues-with-resource-owner-password-flow-and-anomaly-detection#send-the-user-s-ip-address-from-your-server"> Auth0 Anomaly Detection Common Issues</a>
 */
public class CustomHeaderOptions
{
    protected final Map<String, String> parameters = new HashMap<>();

    public static final String AUTH0_FORWARDED_FOR_HEADER = "auth0-forwarded-for";

    /**
     * Include the `auth-forwarded-for` header
     *
     * @param forwardedForValue the ip address to pass through in the header value.
     * @return this customHeaderOptions instance
     */
    public CustomHeaderOptions withAuth0ForwardedForHeader(String forwardedForValue) {
        parameters.put(AUTH0_FORWARDED_FOR_HEADER, forwardedForValue);
        return this;
    }

    public Map<String, String> getAsMap() {
        return parameters;
    }
}

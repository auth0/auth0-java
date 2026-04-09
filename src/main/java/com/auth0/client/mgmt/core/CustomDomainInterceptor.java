package com.auth0.client.mgmt.core;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * OkHttp interceptor that enforces the Auth0-Custom-Domain header whitelist.
 *
 * <p>The Auth0-Custom-Domain header is only sent to specific API endpoints that generate
 * user-facing links (email verification, password change, invitations, etc.). This interceptor
 * strips the header from requests to non-whitelisted paths.
 *
 * <p>Whitelisted endpoints:
 * <ul>
 *   <li>{@code /jobs/verification-email}</li>
 *   <li>{@code /tickets/email-verification}</li>
 *   <li>{@code /tickets/password-change}</li>
 *   <li>{@code /organizations/{id}/invitations}</li>
 *   <li>{@code /users} and {@code /users/{id}}</li>
 *   <li>{@code /guardian/enrollments/ticket}</li>
 *   <li>{@code /self-service-profiles/{id}/sso-ticket}</li>
 * </ul>
 */
public class CustomDomainInterceptor implements Interceptor {

    public static final String HEADER_NAME = "Auth0-Custom-Domain";

    private static final List<Pattern> WHITELISTED_PATHS = Arrays.asList(
            Pattern.compile(".*/jobs/verification-email$"),
            Pattern.compile(".*/tickets/email-verification$"),
            Pattern.compile(".*/tickets/password-change$"),
            Pattern.compile(".*/organizations/[^/]+/invitations(/[^/]+)?$"),
            Pattern.compile(".*/users(/[^/]+)?$"),
            Pattern.compile(".*/guardian/enrollments/ticket$"),
            Pattern.compile(".*/self-service-profiles/[^/]+/sso-ticket(/[^/]+/revoke)?$"));

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        if (request.header(HEADER_NAME) != null && !isWhitelisted(request.url().encodedPath())) {
            request = request.newBuilder().removeHeader(HEADER_NAME).build();
        }

        return chain.proceed(request);
    }

    public static boolean isWhitelisted(String path) {
        for (Pattern pattern : WHITELISTED_PATHS) {
            if (pattern.matcher(path).matches()) {
                return true;
            }
        }
        return false;
    }
}

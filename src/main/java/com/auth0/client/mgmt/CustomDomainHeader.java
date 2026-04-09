package com.auth0.client.mgmt;

import com.auth0.client.mgmt.core.CustomDomainInterceptor;
import com.auth0.client.mgmt.core.RequestOptions;

/**
 * Convenience helper for creating per-request custom domain overrides.
 *
 * <p>Use this to override the global custom domain for a specific API call.
 * The header is only sent to whitelisted endpoints that generate user-facing links.
 *
 * <p>Example usage:
 * <pre>{@code
 * // Override the custom domain for a specific request
 * client.users().list(CustomDomainHeader.of("other.mycompany.com"));
 *
 * // Use with tickets
 * client.tickets().createEmailVerification(request, CustomDomainHeader.of("login.mycompany.com"));
 * }</pre>
 *
 * @see ManagementApiBuilder#customDomain(String) for setting a global custom domain
 */
public final class CustomDomainHeader {

    private CustomDomainHeader() {}

    /**
     * Creates a {@link RequestOptions} instance with the Auth0-Custom-Domain header set.
     *
     * @param domain The custom domain to use (e.g., "login.mycompany.com")
     * @return RequestOptions with the custom domain header configured
     */
    public static RequestOptions of(String domain) {
        return RequestOptions.builder()
                .addHeader(CustomDomainInterceptor.HEADER_NAME, domain)
                .build();
    }
}

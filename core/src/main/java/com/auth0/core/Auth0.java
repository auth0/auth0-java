/*
 * Auth0.java
 *
 * Copyright (c) 2015 Auth0 (http://auth0.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.auth0.core;

import android.net.Uri;

import com.auth0.api.APIClient;
import com.auth0.api.authentication.AuthenticationAPIClient;

public class Auth0 {

    private static final String AUTH0_US_CDN_URL = "https://cdn.auth0.com";
    private static final String DOT_AUTH0_DOT_COM = ".auth0.com";

    private final String clientId;
    private final String domainUrl;
    private final String configurationUrl;

    public Auth0(String clientId, String domain) {
        this(clientId, domain, null);
    }

    public Auth0(String clientId, String domain, String configurationDomain) {
        this.clientId = clientId;
        this.domainUrl = ensureUrlString(domain);
        this.configurationUrl = resolveConfiguration(configurationDomain, this.domainUrl);
    }

    private String resolveConfiguration(String configurationDomain, String domainUrl) {
        String url = ensureUrlString(configurationDomain);
        if (configurationDomain == null && domainUrl != null) {
            final Uri domainUri = Uri.parse(domainUrl);
            final String host = domainUri.getHost();
            if (host.endsWith(DOT_AUTH0_DOT_COM)) {
                String[] parts = host.split("\\.");
                if (parts.length > 3) {
                    url = "https://cdn." + parts[parts.length-3] + DOT_AUTH0_DOT_COM;
                } else {
                    url = AUTH0_US_CDN_URL;
                }
            } else {
                url = domainUrl;
            }
        }
        return url;
    }

    private String ensureUrlString(String url) {
        String safeUrl = null;
        if (url != null) {
            safeUrl = url.startsWith("http") ? url : "https://" + url;
        }
        return safeUrl;
    }

    public String getClientId() {
        return clientId;
    }

    public String getDomainUrl() {
        return domainUrl;
    }

    public String getConfigurationUrl() {
        return configurationUrl;
    }

    @Deprecated
    public APIClient newAPIClient() {
        return new APIClient(this.clientId, this.domainUrl, this.configurationUrl);
    }

    public AuthenticationAPIClient newAuthenticationAPIClient() {
        return new AuthenticationAPIClient(this);
    }

    public String getAuthorizeUrl() {
        return Uri.parse(domainUrl).buildUpon()
                .path("authorize")
                .build()
                .toString();
    }
}

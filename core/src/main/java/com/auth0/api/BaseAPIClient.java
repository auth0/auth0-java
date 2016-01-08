/*
 * BaseAPIClient.java
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

package com.auth0.api;

import android.net.Uri;

import com.auth0.api.internal.RequestFactory;

@Deprecated
abstract class BaseAPIClient {

    public static final String BASE_URL_FORMAT = "https://%s.auth0.com";
    public static final String AUTH0_US_CDN_URL = "https://cdn.auth0.com";
    public static final String AUTH0_EU_CDN_URL = "https://cdn.eu.auth0.com";

    private final String clientID;
    private final String configurationURL;
    private final String baseURL;

    public BaseAPIClient(String clientID, String baseURL, String configurationURL, String tenantName) {
        this.clientID = clientID;
        this.configurationURL = Uri.parse(configurationURL).buildUpon()
                .appendPath("client")
                .appendPath(this.clientID + ".js")
                .build().toString();
        this.baseURL = baseURL;
    }

    public BaseAPIClient(String clientID, String baseURL, String configurationURL) {
        this(clientID, baseURL, configurationURL, null);
    }

    @Deprecated
    public BaseAPIClient(String clientID, String tenantName) {
        this(clientID, String.format(BASE_URL_FORMAT, tenantName), AUTH0_US_CDN_URL, tenantName);
    }

    public String getClientID() {
        return clientID;
    }

    public String getBaseURL() {
        return baseURL;
    }

    public String getConfigurationURL() {
        return configurationURL;
    }

    public void setClientInfo(String clientInfo) {
        RequestFactory.setClientInfo(clientInfo);
    }
}

/*
 * ManagementAPIClient.java
 *
 * Copyright (c) 2016 Auth0 (http://auth0.com)
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

package com.auth0.management;


import com.auth0.Auth0;
import com.auth0.management.users.UsersAPIClient;
import com.auth0.request.internal.RequestFactory;
import com.auth0.util.Telemetry;
import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;


/**
 * API client for Auth0 Management API.
 *
 * @see <a href="https://auth0.com/docs/api/v2">Management API docs</a>
 */
public class ManagementAPIClient {

    private final Auth0 auth0;
    private final OkHttpClient client;
    private final Gson gson;
    private final RequestFactory factory;

    /**
     * Creates a new API client instance providing Auth0 account info.
     *
     * @param auth0 account information
     */
    public ManagementAPIClient(Auth0 auth0) {
        this(auth0, new OkHttpClient(), new Gson());
    }

    private ManagementAPIClient(Auth0 auth0, OkHttpClient client, Gson gson) {
        this.auth0 = auth0;
        this.client = client;
        this.gson = gson;
        this.factory = new RequestFactory();
        final Telemetry telemetry = auth0.getTelemetry();
        if (telemetry != null) {
            factory.setClientInfo(telemetry.getValue());
        }
    }

    public String getClientId() {
        return auth0.getClientId();
    }

    public String getBaseURL() {
        return auth0.getDomainUrl();
    }

    /**
     * Set the value of 'User-Agent' header for every request to Auth0 Authentication API
     *
     * @param userAgent value to send in every request to Auth0
     */
    public void setUserAgent(String userAgent) {
        factory.setUserAgent(userAgent);
    }

    /**
     * Gets an API client for Users Management API.
     *
     * @param token for authentication
     * @return an {@link UsersAPIClient}
     */
    public UsersAPIClient users(String token) {
        return new UsersAPIClient(auth0, client, gson, factory, token);
    }
}

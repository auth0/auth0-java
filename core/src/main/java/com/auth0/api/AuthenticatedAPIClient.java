/*
 * AuthenticatedAPIClient.java
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

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.auth0.api.callback.BaseCallback;
import com.auth0.api.internal.RequestFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;

import java.util.Map;

/**
 * API client for Auth0 API v2.
 * @see <a href="https://auth0.com/docs/apiv2">API v2 docs</a>
 * @deprecated To request a SMS message for passwordless use {@link com.auth0.api.authentication.AuthenticationAPIClient}
 */
@Deprecated
@SuppressWarnings("all")
public class AuthenticatedAPIClient extends BaseAPIClient {

    private static final String TAG = AuthenticatedAPIClient.class.getName();

    private String jwt;
    private OkHttpClient newClient = new OkHttpClient();

    /**
     * Creates a new API client instance providing  API and Configuration Urls different than the default. (Useful for on premise deploys).
     * @param clientID Your application clientID.
     * @param baseURL Auth0's  API endpoint
     * @param configurationURL Auth0's enpoint where App info can be retrieved.
     * @param tenantName Name of the tenant. Can be null
     */
    public AuthenticatedAPIClient(String clientID, String baseURL, String configurationURL, String tenantName) {
        super(clientID, baseURL, configurationURL, tenantName);
    }

    /**
     * Creates a new API client instance providing API and Configuration Urls different than the default. (Useful for on premise deploys).
     * @param clientID Your application clientID.
     * @param baseURL Auth0's API endpoint
     * @param configurationURL Auth0's endpoint where App info can be retrieved.
     */
    public AuthenticatedAPIClient(String clientID, String baseURL, String configurationURL) {
        super(clientID, baseURL, configurationURL);
    }

    /**
     * Creates a new API client using clientID and tenant name.
     * @param clientID Your application clientID.
     * @param tenantName Name of the tenant.
     */
    public AuthenticatedAPIClient(String clientID, String tenantName) {
        super(clientID, tenantName);
    }

    /**
     * Adds the header 'Authorization' with a given JWT token.
     * @param jwt a valid JWT token
     */
    public void setJWT(String jwt) {
        this.jwt = jwt;
    }

    /**
     * Creates a user and request a SMS code using a configured SMS connection
     * @param phoneNumber phone number that will receive the SMS code.
     * @param callback callback when the SMS is sent or with the failure reason.
     * @deprecated Use passwordless endpoints from {@link APIClient}
     */
    public void requestSmsCode(String phoneNumber, final BaseCallback<Void> callback) {
        HttpUrl url = HttpUrl.parse(getBaseURL()).newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("users")
                .build();
        Map<String, Object> params = ParameterBuilder.newBuilder()
                .clearAll()
                .setConnection("sms")
                .set("email_verified", false)
                .set("phone_number", phoneNumber)
                .asDictionary();

        Log.v(TAG, "Requesting SMS code for phone " + phoneNumber);

        RequestFactory.POST(url, newClient, new Handler(Looper.getMainLooper()), new ObjectMapper(), jwt)
                .addParameters(params)
                .start(callback);
    }
}

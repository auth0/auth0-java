/*
 * UnlinkUserRequest.java
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

package com.auth0.management.users;


import com.auth0.Auth0;
import com.auth0.Auth0Exception;
import com.auth0.callback.BaseCallback;
import com.auth0.request.Request;
import com.auth0.request.internal.RequestFactory;
import com.google.gson.Gson;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;

import java.util.List;
import java.util.Map;


/**
 * Request to unlink an account from the user.
 */
public class UnlinkUserRequest implements Request<List<Map<String, Object>>> {

    private final Auth0 auth0;
    private final OkHttpClient client;
    private final Gson gson;
    private final RequestFactory factory;
    private final String token;
    private final String userId;

    private String secondaryUserId;
    private String provider;

    public UnlinkUserRequest(Auth0 auth0, OkHttpClient client, Gson gson, RequestFactory factory, String token, String userId) {
        this.auth0 = auth0;
        this.client = client;
        this.gson = gson;
        this.factory = factory;
        this.token = token;
        this.userId = userId;
    }

    @Override
    public void start(BaseCallback<List<Map<String, Object>>> callback) {
        buildRequest().start(callback);
    }

    @Override
    public List<Map<String, Object>> execute() throws Auth0Exception {
        return buildRequest().execute();
    }

    /**
     * Sets the secondary user account, the one to unlink
     *
     * @param secondaryUserId the user id of the secondary account
     * @return itself
     */
    public UnlinkUserRequest fromUser(String secondaryUserId) {
        this.secondaryUserId = secondaryUserId;
        return this;
    }

    /**
     * Sets the provider of the secondary user account
     *
     * @param secondaryUserProvider the provider of the secondary user account
     * @return itself
     */
    public UnlinkUserRequest ofProvider(String secondaryUserProvider) {
        this.provider = secondaryUserProvider;
        return this;
    }

    private Request<List<Map<String, Object>>> buildRequest() {
        if (provider == null || secondaryUserId == null) {
            throw new IllegalArgumentException("You need to specify both user id and provider");
        }

        HttpUrl.Builder url = HttpUrl.parse(auth0.getDomainUrl()).newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("users")
                .addPathSegment(userId)
                .addPathSegment("identities")
                .addPathSegment(provider)
                .addPathSegment(secondaryUserId);

        return factory.rawDELETEList(url.build(), client, gson)
                .setBearer(token);
    }
}

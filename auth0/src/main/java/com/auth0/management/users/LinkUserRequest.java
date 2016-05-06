/*
 * LinkUserRequest.java
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


import com.auth0.Auth0Exception;
import com.auth0.callback.BaseCallback;
import com.auth0.request.ParameterizableRequest;
import com.auth0.request.Request;

import java.util.List;
import java.util.Map;


/**
 * Request to link a user with a secondary account.
 */
public class LinkUserRequest implements Request<List<Map<String, Object>>> {

    private static final String KEY_LINK_WITH = "link_with";
    private static final String KEY_PROVIDER = "provider";
    private static final String KEY_CONNECTION_ID = "connection_id";
    private static final String KEY_USER_ID = "user_id";

    private final ParameterizableRequest<List<Map<String, Object>>> request;

    public LinkUserRequest(ParameterizableRequest<List<Map<String, Object>>> request) {
        this.request = request;
    }

    @Override
    public void start(BaseCallback<List<Map<String, Object>>> callback) {
        request.start(callback);
    }

    @Override
    public List<Map<String, Object>> execute() throws Auth0Exception {
        return request.execute();
    }

    /**
     * Links the user with a secondary account, using a JWT obtained upon the secondary account's authentication.
     * No extra parameters are needed, but the authorization token should be an authenticated primary account's JWT and
     * must have the 'update:current_user_identities' scope
     *
     * @param secondaryAccountToken the secondary account's token
     * @return itself
     */
    public LinkUserRequest withAccountToken(String secondaryAccountToken) {
        request.addParameter(KEY_LINK_WITH, secondaryAccountToken);
        return this;
    }

    /**
     * Sets the user id of the secondary account being linked
     *
     * @param secondaryUserId of the secondary account
     * @return itself
     */
    public LinkUserRequest withUser(String secondaryUserId) {
        request.addParameter(KEY_USER_ID, secondaryUserId);
        return this;
    }

    /**
     * Sets the type of identity provider of the user of the secondary account being linked
     *
     * @param secondaryUserProvider of the secondary account
     * @return itself
     */
    public LinkUserRequest ofProvider(String secondaryUserProvider) {
        request.addParameter(KEY_PROVIDER, secondaryUserProvider);
        return this;
    }

    /**
     * Sets the id of the connection of the secondary account being linked. This is optional and may be useful in the
     * case of having more than a database connection for the 'auth0' provider
     *
     * @param connectionId the connection id
     * @return itself
     */
    public LinkUserRequest setConnection(String connectionId) {
        request.addParameter(KEY_CONNECTION_ID, connectionId);
        return this;
    }
}

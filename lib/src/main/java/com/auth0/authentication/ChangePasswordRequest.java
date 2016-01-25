/*
 * ChangePasswordRequest.java
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

package com.auth0.authentication;

import com.auth0.Auth0Exception;
import com.auth0.request.ParameterizableRequest;
import com.auth0.callback.BaseCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * Represent a request to change a user's password.
 */
public class ChangePasswordRequest implements ParameterizableRequest<Void> {

    private static final String PASSWORD_KEY = "password";

    private final ParameterizableRequest<Void> request;

    ChangePasswordRequest(ParameterizableRequest<Void> request) {
        this.request = request;
    }

    private ChangePasswordRequest addParameter(String key, Object value) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(key, value);
        return addParameters(parameters);
    }

    /**
     * Set the 'password' parameter to be sent in the request
     * @param newPassword the new password
     * @return itself
     */
    public ChangePasswordRequest setNewPassword(String newPassword) {
        return addParameter(PASSWORD_KEY, newPassword);
    }

    /**
     * Adds additional parameters to be sent in the request
     * @param parameters as a non-null dictionary
     * @return itself
     */
    @Override
    public ChangePasswordRequest addParameters(Map<String, Object> parameters) {
        request.addParameters(parameters);
        return this;
    }

    /**
     * Adds an additional header for the request
     * @param name of the header
     * @param value of the header
     * @return itself
     */
    @Override
    public ChangePasswordRequest addHeader(String name, String value) {
        request.addHeader(name, value);
        return this;
    }

    /**
     * Starts the change password request
     * @param callback called either on success or failure
     */
    @Override
    public void start(final BaseCallback<Void> callback) {
        request.start(callback);
    }

    /**
     * Executes the change password request
     * @return nothing on success
     * @throws Auth0Exception when the change password request fails
     */
    @Override
    public Void execute() throws Auth0Exception {
        return request.execute();
    }
}

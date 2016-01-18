/*
 * SignUpRequest.java
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

package com.auth0.authentication;

import com.auth0.Auth0Exception;
import com.auth0.authentication.api.ParameterizableRequest;
import com.auth0.authentication.api.Request;
import com.auth0.authentication.api.callback.BaseCallback;
import com.auth0.DatabaseUser;

import java.util.Map;

/**
 * Represent a request to create a user + log in + fetch user profile.
 */
public class SignUpRequest implements Request<Authentication> {

    private final ParameterizableRequest<DatabaseUser> signUpRequest;
    private final AuthenticationRequest authenticationRequest;

    SignUpRequest(ParameterizableRequest<DatabaseUser> signUpRequest, AuthenticationRequest authenticationRequest) {
        this.signUpRequest = signUpRequest;
        this.authenticationRequest = authenticationRequest;
    }

    /**
     * Add additional parameters for create user request
     * @param parameters as a non-null dictionary
     * @return itself
     */
    public SignUpRequest addSignUpParameters(Map<String, Object> parameters) {
        signUpRequest.addParameters(parameters);
        return this;
    }

    /**
     * Add additional parameters for login request
     * @param parameters as a non-null dictionary
     * @return itself
     */
    public SignUpRequest addAuthenticationParameters(Map<String, Object> parameters) {
        authenticationRequest.addParameters(parameters);
        return this;
    }

    /**
     * Set the scope used to authenticate the user
     * @param scope value
     * @return itself
     */
    public SignUpRequest setScope(String scope) {
        authenticationRequest.setScope(scope);
        return this;
    }

    /**
     * Set the connection used to authenticate
     * @param connection name
     * @return itself
     */
    public SignUpRequest setConnection(String connection) {
        authenticationRequest.setConnection(connection);
        return this;
    }

    /**
     * Starts to execute create user request and then logs the user in.
     * @param callback called on either success or failure.
     */
    @Override
    public void start(final BaseCallback<Authentication> callback) {
        signUpRequest.start(new BaseCallback<DatabaseUser>() {
            @Override
            public void onSuccess(final DatabaseUser user) {
                authenticationRequest
                        .start(callback);
            }

            @Override
            public void onFailure(Auth0Exception error) {
                callback.onFailure(error);
            }
        });
    }

    /**
     * Execute the create user request and then logs the user in.
     * @return authentication object on success
     * @throws Auth0Exception on failure
     */
    @Override
    public Authentication execute() throws Auth0Exception {
        signUpRequest.execute();
        return authenticationRequest.execute();
    }
}

/*
 * AuthenticationRequest.java
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

import com.auth0.authentication.api.ParameterBuilder;
import com.auth0.authentication.api.ParameterizableRequest;
import com.auth0.authentication.api.Request;
import com.auth0.authentication.api.callback.BaseCallback;
import com.auth0.Token;
import com.auth0.UserProfile;

import java.util.Map;

/**
 * Represent a Authentication request that consist of a log in and a fetch profile requests
 */
public class AuthenticationRequest implements Request<Authentication> {

    private final ParameterizableRequest<Token> credentialsRequest;
    private final ParameterizableRequest<UserProfile> tokenInfoRequest;

    AuthenticationRequest(ParameterizableRequest<Token> credentialsRequest, ParameterizableRequest<UserProfile> tokenInfoRequest) {
        this.credentialsRequest = credentialsRequest;
        this.tokenInfoRequest = tokenInfoRequest;
    }

    /**
     * Adds additional parameters for the login request
     * @param parameters as a non-null dictionary
     * @return itself
     */
    public AuthenticationRequest addParameters(Map<String, Object> parameters) {
        credentialsRequest.addParameters(parameters);
        return this;
    }

    /**
     * Set the scope used to authenticate the user
     * @param scope value
     * @return itself
     */
    public AuthenticationRequest setScope(String scope) {
        credentialsRequest.addParameters(new ParameterBuilder().clearAll().setScope(scope).asDictionary());
        return this;
    }

    /**
     * Set the connection used to authenticate
     * @param connection name
     * @return itself
     */
    public AuthenticationRequest setConnection(String connection) {
        credentialsRequest.addParameters(new ParameterBuilder().clearAll().setConnection(connection).asDictionary());
        return this;
    }

    /**
     * Starts the log in request and then fetches the user's profile
     * @param callback called on either success or failure
     */
    @Override
    public void start(final BaseCallback<Authentication> callback) {
        credentialsRequest.start(new BaseCallback<Token>() {
            @Override
            public void onSuccess(final Token token) {
                Map<String, Object> parameters = new ParameterBuilder()
                        .clearAll()
                        .set("id_token", token.getIdToken())
                        .asDictionary();
                tokenInfoRequest
                        .addParameters(parameters)
                        .start(new BaseCallback<UserProfile>() {
                            @Override
                            public void onSuccess(UserProfile profile) {
                                callback.onSuccess(new Authentication(profile, token));
                            }

                            @Override
                            public void onFailure(Throwable error) {
                                callback.onFailure(error);
                            }
                        });
            }

            @Override
            public void onFailure(Throwable error) {
                callback.onFailure(error);
            }
        });
    }

    /**
     * Executes the log in request and then fetches the user's profile
     * @return authentication object on success
     * @throws Throwable on failure
     */
    @Override
    public Authentication execute() throws Throwable {
        Token token = credentialsRequest.execute();
        Map<String, Object> parameters = new ParameterBuilder()
                .clearAll()
                .set("id_token", token.getIdToken())
                .asDictionary();
        UserProfile profile = tokenInfoRequest
                .addParameters(parameters)
                .execute();
        return new Authentication(profile, token);
    }
}

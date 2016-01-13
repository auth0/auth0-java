/*
 * DelegationRequest.java
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
import com.auth0.authentication.api.callback.BaseCallback;
import com.auth0.authentication.api.callback.RefreshIdTokenCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * Represent a delegation request for Auth0 tokens that will yield a new 'id_token'
 */
public class DelegationRequest {

    private static final String TOKEN_TYPE_KEY = "token_type";
    private static final String EXPIRES_IN_KEY = "expires_in";
    private static final String ID_TOKEN_KEY = "id_token";

    private static final String API_TYPE_KEY = "api_type";
    private static final String DEFAULT_API_TYPE = "app";

    private static final String TARGET_KEY = "target";

    private final ParameterizableRequest<Map<String, Object>> request;

    DelegationRequest(ParameterizableRequest<Map<String, Object>> request) {
        this.request = request;
        addParameter(API_TYPE_KEY, DEFAULT_API_TYPE);
    }

    private DelegationRequest addParameter(String key, Object value) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(key, value);
        request.addParameters(parameters);
        return this;
    }

    /**
     * Add additional parameters to be sent in the request
     * @param parameters as a non-null dictionary
     * @return itself
     */
    public DelegationRequest addParameters(Map<String, Object> parameters) {
        request.addParameters(parameters);
        return this;
    }

    /**
     * Set the api_type parameter to be sent in the request
     * @param apiType the delegation api type
     * @return itself
     */
    public DelegationRequest setApiType(String apiType) {
        return addParameter(TARGET_KEY, apiType);
    }

    /**
     * Set the scope used to make the delegation
     * @param scope value
     * @return itself
     */
    public DelegationRequest setScope(String scope) {
        return addParameters(new ParameterBuilder().clearAll().setScope(scope).asDictionary());
    }

    /**
     * Set the target parameter to be sent in the request
     * @param target the delegation target
     * @return itself
     */
    public DelegationRequest setTarget(String target) {
        return addParameter(TARGET_KEY, target);
    }

    /**
     * Performs the HTTP request against Auth0 API
     * @param callback called either on success or failure
     */
    public void start(final RefreshIdTokenCallback callback) {
        request.start(new BaseCallback<Map<String, Object>>() {
            @Override
            public void onSuccess(final Map<String, Object> payload) {
                final String id_token = (String) payload.get(ID_TOKEN_KEY);
                final String token_type = (String) payload.get(TOKEN_TYPE_KEY);
                final Integer expires_in = (Integer) payload.get(EXPIRES_IN_KEY);
                callback.onSuccess(id_token, token_type, expires_in);
            }

            @Override
            public void onFailure(Throwable error) {
                callback.onFailure(error);
            }
        });
    }

}

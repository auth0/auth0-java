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
import com.auth0.authentication.api.Request;
import com.auth0.authentication.api.callback.BaseCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * Represent a delegation request for Auth0 tokens that will yield a new 'id_token'
 */
public class DelegationRequest<T> implements Request<T> {

    private static final String API_TYPE_KEY = "api_type";
    public static final String DEFAULT_API_TYPE = "app";

    private static final String TARGET_KEY = "target";

    private final ParameterizableRequest<T> request;

    DelegationRequest(ParameterizableRequest<T> request) {
        this.request = request;
    }

    private DelegationRequest<T> addParameter(String key, Object value) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(key, value);
        return addParameters(parameters);
    }

    /**
     * Add additional parameters to be sent in the request
     * @param parameters as a non-null dictionary
     * @return itself
     */
    public DelegationRequest<T> addParameters(Map<String, Object> parameters) {
        request.addParameters(parameters);
        return this;
    }

    /**
     * Set the api_type parameter to be sent in the request
     * @param apiType the delegation api type
     * @return itself
     */
    public DelegationRequest<T> setApiType(String apiType) {
        return addParameter(API_TYPE_KEY, apiType);
    }

    /**
     * Set the scope used to make the delegation
     * @param scope value
     * @return itself
     */
    public DelegationRequest<T> setScope(String scope) {
        return addParameters(new ParameterBuilder().clearAll().setScope(scope).asDictionary());
    }

    /**
     * Set the target parameter to be sent in the request
     * @param target the delegation target
     * @return itself
     */
    public DelegationRequest<T> setTarget(String target) {
        return addParameter(TARGET_KEY, target);
    }

    /**
     * Performs the HTTP request against Auth0 API
     * @param callback called either on success or failure
     */
    @Override
    public void start(final BaseCallback<T> callback) {
        request.start(callback);
    }

    /**
     * Executes the HTTP request against Auth0 API
     * @return the delegation response on success
     * @throws Throwable on failure
     */
    @Override
    public T execute() throws Throwable {
        return request.execute();
    }
}

/*
 * ParameterBuilder.java
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

import com.auth0.util.CheckHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * Builder class for Auth API parameters.
 */
public class ParameterBuilder {

    public static final String GRANT_TYPE_PASSWORD = "password";
    public static final String GRANT_TYPE_JWT = "urn:ietf:params:oauth:grant-type:jwt-bearer";
    public static final String SCOPE_OPENID = "openid";
    public static final String SCOPE_OFFLINE_ACCESS = "openid offline_access";

    private static final String ACCESS_TOKEN_KEY = "access_token";
    private static final String CONNECTION_KEY = "connection";
    private static final String SEND_KEY = "send";
    private static final String CLIENT_ID_KEY = "client_id";
    private static final String GRANT_TYPE_KEY = "grant_type";
    private static final String SCOPE_KEY = "scope";
    private static final String DEVICE_KEY = "device";

    private Map<String, Object> parameters;

    /**
     * Creates a new builder
     */
    private ParameterBuilder() {
        this.parameters = new HashMap<>();
        setScope(SCOPE_OFFLINE_ACCESS);
    }

    /**
     * Creates a new builder with default parameters
     *
     * @param parameters default parameters
     */
    private ParameterBuilder(Map<String, Object> parameters) {
        CheckHelper.checkArgument(parameters != null, "Must provide non-null parameters");
        this.parameters = new HashMap<>(parameters);
    }

    /**
     * Sets the 'client_id' parameter
     *
     * @param clientId clientID
     * @return itself
     */
    public ParameterBuilder setClientId(String clientId) {
        return set(CLIENT_ID_KEY, clientId);
    }

    /**
     * Sets the 'grant_type' parameter
     *
     * @param grantType grant type
     * @return itself
     */
    public ParameterBuilder setGrantType(String grantType) {
        return set(GRANT_TYPE_KEY, grantType);
    }

    /**
     * Sets the 'connection' parameter
     *
     * @param connection name of the connection
     * @return itself
     */
    public ParameterBuilder setConnection(String connection) {
        return set(CONNECTION_KEY, connection);
    }

    /**
     * Sets the 'scope' parameter.
     *
     * @param scope a scope value
     * @return itself
     */
    public ParameterBuilder setScope(String scope) {
        return set(SCOPE_KEY, scope);
    }

    /**
     * Sets the 'device' parameter
     *
     * @param device a device name
     * @return itself
     */
    public ParameterBuilder setDevice(String device) {
        return set(DEVICE_KEY, device);
    }

    /**
     * Sets the 'access_token' parameter
     *
     * @param accessToken a access token
     * @return itself
     */
    public ParameterBuilder setAccessToken(String accessToken) {
        return set(ACCESS_TOKEN_KEY, accessToken);
    }

    /**
     * Sets the 'send' parameter
     *
     * @param passwordlessType the type of passwordless login
     * @return itself
     */
    public ParameterBuilder setSend(PasswordlessType passwordlessType) {
        switch (passwordlessType) {
            default:
            case CODE:
                return set(SEND_KEY, "code");
            case LINK:
                return set(SEND_KEY, "link");
            case LINK_ANDROID:
                return set(SEND_KEY, "link_android");
            case LINK_IOS:
                return set(SEND_KEY, "link_ios");
        }
    }

    /**
     * Sets a parameter
     *
     * @param key   parameter name
     * @param value parameter value
     * @return itself
     */
    public ParameterBuilder set(String key, Object value) {
        this.parameters.put(key, value);
        return this;
    }

    /**
     * Adds all parameter from a map
     *
     * @param parameters map with parameters to add
     * @return itself
     */
    public ParameterBuilder addAll(Map<String, Object> parameters) {
        if (parameters != null) {
            this.parameters.putAll(parameters);
        }
        return this;
    }

    /**
     * Clears all existing parameters
     *
     * @return itself
     */
    public ParameterBuilder clearAll() {
        parameters.clear();
        return this;
    }

    /**
     * Create a {@link Map} with all the parameters
     *
     * @return a new map with the parameters
     */
    public Map<String, Object> asDictionary() {
        return new HashMap<>(this.parameters);
    }

    /**
     * Creates a new instance of the builder with default values
     *
     * @return a new builder
     */
    public static ParameterBuilder newBuilder() {
        return new ParameterBuilder();
    }

    /**
     * Creates a new instance of the builder without any default values
     *
     * @return a new builder
     */
    public static ParameterBuilder newEmptyBuilder() {
        return new ParameterBuilder(new HashMap<String, Object>());
    }

    /**
     * Creates a new instance of the builder with parameters.
     *
     * @param parameters default parameters
     * @return a new builder
     */
    public static ParameterBuilder newBuilder(Map<String, Object> parameters) {
        return new ParameterBuilder(parameters);
    }

}

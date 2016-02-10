/*
 * AuthenticationParameterBuilder.java
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

import java.util.HashMap;
import java.util.Map;

public class AuthenticationParameterBuilder extends ParameterBuilder {

    public static final String SCOPE_OPENID = "openid";
    public static final String SCOPE_OFFLINE_ACCESS = "openid offline_access";
    public static final String GRANT_TYPE_PASSWORD = "password";
    public static final String GRANT_TYPE_JWT = "urn:ietf:params:oauth:grant-type:jwt-bearer";

    private static final String ACCESS_TOKEN = "access_token";
    private static final String CONNECTION = "connection";
    private static final String STATE = "state";
    private static final String NONCE = "nonce";
    private static final String SEND = "send";
    private static final String SCOPE = "scope";
    private static final String DEVICE = "device";

    public AuthenticationParameterBuilder() {
        super();
        setScope(SCOPE_OFFLINE_ACCESS);
    }

    /**
     * Sets the 'client_id' parameter
     *
     * @param clientId clientID
     * @return itself
     */
    public AuthenticationParameterBuilder setClientId(String clientId) {
        return set("client_id", clientId);
    }

    /**
     * Sets the 'grant_type' parameter
     *
     * @param grantType grant type
     * @return itself
     */
    public AuthenticationParameterBuilder setGrantType(String grantType) {
        return set("grant_type", grantType);
    }

    /**
     * Sets the 'connection' parameter
     *
     * @param connection name of the connection
     * @return itself
     */
    public AuthenticationParameterBuilder setConnection(String connection) {
        return set(CONNECTION, connection);
    }

    /**
     * Sets the 'scope' parameter.
     *
     * @param scope a scope value
     * @return itself
     */
    public AuthenticationParameterBuilder setScope(String scope) {
        return set(SCOPE, scope);
    }

    /**
     * Sets the 'device' parameter
     *
     * @param device a device name
     * @return itself
     */
    public AuthenticationParameterBuilder setDevice(String device) {
        return set(DEVICE, device);
    }

    /**
     * Sets the 'access_token' parameter
     *
     * @param accessToken a access token
     * @return itself
     */
    public AuthenticationParameterBuilder setAccessToken(String accessToken) {
        return set(ACCESS_TOKEN, accessToken);
    }

    /**
     * Sets the 'state' parameter
     *
     * @param state a state
     * @return itself
     */
    public AuthenticationParameterBuilder setState(String state) {
        return set(STATE, state);
    }

    /**
     * Sets the 'nonce' parameter
     *
     * @param nonce a nonce
     * @return itself
     */
    public AuthenticationParameterBuilder setNonce(String nonce) {
        return set(NONCE, nonce);
    }

    /**
     * Sets the 'send' parameter
     *
     * @param passwordlessType the type of passwordless login
     * @return itself
     */
    public AuthenticationParameterBuilder setSend(PasswordlessType passwordlessType) {
        switch (passwordlessType) {
            default:
            case CODE:
                return set(SEND, "code");
            case LINK:
                return set(SEND, "link");
            case LINK_ANDROID:
                return set(SEND, "link_android");
            case LINK_IOS:
                return set(SEND, "link_ios");
        }
    }


    /**
     * Sets a parameter
     *
     * @param key   parameter name
     * @param value parameter value
     * @return itself
     */
    public AuthenticationParameterBuilder set(String key, Object value) {
        super.set(key, value);
        return this;
    }

    /**
     * Adds all parameter from a map
     *
     * @param parameters map with parameters to add
     * @return itself
     */
    public AuthenticationParameterBuilder addAll(Map<String, Object> parameters) {
        super.addAll(parameters);
        return this;
    }


    /**
     * Clears all existing parameters
     *
     * @return itself
     */
    public AuthenticationParameterBuilder clearAll() {
        super.clearAll();
        return this;
    }

    /**
     * Creates a new instance of the builder with default values
     *
     * @return a new builder
     */
    public static AuthenticationParameterBuilder newBuilder() {
        return new AuthenticationParameterBuilder();
    }

}

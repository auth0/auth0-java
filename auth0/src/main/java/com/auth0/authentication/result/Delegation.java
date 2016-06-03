/*
 * Delegation.java
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

package com.auth0.authentication.result;

import com.google.gson.annotations.SerializedName;

import static com.auth0.util.CheckHelper.checkArgument;

/**
 * The result of a successful delegation to an Auth0 application that contains a new Auth0 'id_token'
 * See <a href="https://auth0.com/docs/auth-api#!#post--delegation">delegation</a> docs
 */
public class Delegation {
    @SerializedName("id_token")
    private final String idToken;
    @SerializedName("token_type")
    private final String type;
    @SerializedName("expires_in")
    private final Long expiresIn;

    public Delegation(String idToken, String type, Long expiresIn) {
        checkArgument(idToken != null, "id_token must be non-null");
        checkArgument(type != null, "token_type must be non-null");
        checkArgument(expiresIn != null, "expires_in must be non-null");
        this.idToken = idToken;
        this.type = type;
        this.expiresIn = expiresIn;
    }

    /**
     * Identity Token
     *
     * @return the 'id_token' value
     */
    public String getIdToken() {
        return idToken;
    }

    /**
     * Token type
     *
     * @return the 'token_type' value
     */
    public String getType() {
        return type;
    }

    /**
     * Token expire time in milliseconds since January 1, 1970, 00:00:00 GMT
     *
     * @return the 'expires_in' value
     */
    public Long getExpiresIn() {
        return expiresIn;
    }
}

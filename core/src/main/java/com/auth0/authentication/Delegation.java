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

package com.auth0.authentication;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import static com.auth0.authentication.api.util.CheckHelper.checkArgument;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Delegation {
    private final String idToken;
    private final String type;
    private final Integer expiresIn;

    @JsonCreator
    public Delegation(@JsonProperty(value = "id_token") String idToken,
                      @JsonProperty(value = "token_type") String type,
                      @JsonProperty(value = "expires_in") Integer expiresIn) {
        checkArgument(idToken != null, "id_token must be non-null");
        checkArgument(type != null, "token_type must be non-null");
        checkArgument(expiresIn != null, "expires_in must be non-null");
        this.idToken = idToken;
        this.type = type;
        this.expiresIn = expiresIn;
    }

    public String getIdToken() {
        return idToken;
    }

    public String getType() {
        return type;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }
}

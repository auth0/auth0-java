/*
 * MockAuthenticationCallback.java
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

package com.auth0.util;

import com.auth0.api.callback.AuthenticationCallback;
import com.auth0.core.Token;
import com.auth0.core.UserProfile;

import java.util.concurrent.Callable;

public class MockAuthenticationCallback implements AuthenticationCallback {

    private Token token;
    private UserProfile profile;
    private Throwable error;

    @Override
    public void onSuccess(UserProfile profile, Token token) {
        this.token = token;
        this.profile = profile;
    }

    @Override
    public void onFailure(Throwable error) {
        this.error = error;
    }

    public Callable<Token> token() {
        return new Callable<Token>() {
            @Override
            public Token call() throws Exception {
                return token;
            }
        };
    }

    public Callable<UserProfile> profile() {
        return new Callable<UserProfile>() {
            @Override
            public UserProfile call() throws Exception {
                return profile;
            }
        };
    }

    public Callable<Throwable> error() {
        return new Callable<Throwable>() {
            @Override
            public Throwable call() throws Exception {
                return error;
            }
        };
    }
}

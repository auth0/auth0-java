/*
 * DatabaseUser.java
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

package com.auth0.authentication.result;

import com.google.gson.annotations.SerializedName;

/**
 * Auth0 user created in a Database connection.
 *
 * @see com.auth0.authentication.AuthenticationAPIClient#signUp(String, String)
 * @see com.auth0.authentication.AuthenticationAPIClient#signUp(String, String, String)
 */
public class DatabaseUser {

    @SerializedName("email")
    private final String email;
    @SerializedName("username")
    private final String username;
    @SerializedName("email_verified")
    private final boolean emailVerified;

    public DatabaseUser(String email, String username, boolean emailVerified) {
        this.email = email;
        this.username = username;
        this.emailVerified = emailVerified;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }
}

/*
 * UserIdentity.java
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

import java.util.Map;

/**
 * Class that holds the information from a Identity Provider like Facebook or Twitter.
 */
public class UserIdentity {

    @SerializedName("user_id")
    private String id;
    @SerializedName("connection")
    private String connection;
    @SerializedName("provider")
    private String provider;
    @SerializedName("isSocial")
    private boolean social;
    @SerializedName("access_token")
    private String accessToken;
    @SerializedName("access_token_secret")
    private String accessTokenSecret;
    @SerializedName("profileData")
    private Map<String, Object> profileInfo;

    public UserIdentity(String id, String connection, String provider, boolean social,
                        String accessToken, String accessTokenSecret, Map<String, Object> profileInfo) {
        this.id = id;
        this.connection = connection;
        this.provider = provider;
        this.social = social;
        this.accessToken = accessToken;
        this.accessTokenSecret = accessTokenSecret;
        this.profileInfo = profileInfo;
    }

    public String getId() {
        return id;
    }

    public String getConnection() {
        return connection;
    }

    public String getProvider() {
        return provider;
    }

    public boolean isSocial() {
        return social;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getAccessTokenSecret() {
        return accessTokenSecret;
    }

    public Map<String, Object> getProfileInfo() {
        return profileInfo;
    }

    public String getUserIdentityId() {
        return String.format("%s|%s", this.provider, this.id);
    }
}

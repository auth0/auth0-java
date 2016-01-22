/*
 * UserProfileTest.java
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

package com.auth0;


import com.auth0.authentication.result.UserIdentity;
import com.auth0.authentication.result.UserProfile;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;


public class UserProfileTest {

    public static final String USER_ID = "IOU a user id";
    public static final String NAME = "Somebody Someone";
    public static final String NICKNAME = "somebody";
    public static final String EMAIL = "somebody@somwhere.com";
    public static final String CREATED_AT = "2014-07-06T18:33:49.005Z";
    public static final String PICTURE_URL = "http://somewhere.com/pic.jpg";
    public static final Object EXTRA_VALUE = "extra_value";
    public static final long CREATED_AT_TIMESTAMP = 1404671629005l;
    public static final String FACEBOOK = "facebook";
    public static final String TOKEN = "token";
    public static final String SECRET = "secret";

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void shouldRaiseExceptionWithNullValuesWhenCreatingInstance() throws Exception {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(equalTo("must supply non-null values"));
        new UserProfile((HashMap<String, Object>)null);
    }

    @Test
    public void shouldRaiseExceptionWithNoUserIdWhenCreatingInstance() throws Exception {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(equalTo("profile must have a user id"));
        new UserProfile(new HashMap<String, Object>());
    }

    @Test
    public void shouldInstantiateProfileWithId() throws Exception {
        Map<String, Object> values = new HashMap<String, Object>();
        values.put("user_id", USER_ID);
        UserProfile profile = new UserProfile(values);
        assertValidProfile(profile);
    }

    @Test
    public void shouldInstantiateWithBasicProfileInfo() throws Exception {
        Map<String, Object> values = new HashMap<String, Object>();
        values.put("user_id", USER_ID);
        values.put("name", NAME);
        values.put("nickname", NICKNAME);
        values.put("email", EMAIL);
        values.put("picture", PICTURE_URL);
        values.put("created_at", CREATED_AT);
        UserProfile profile = new UserProfile(values);
        assertValidProfile(profile);
        assertThat(profile.getName(), equalTo(NAME));
        assertThat(profile.getNickname(), equalTo(NICKNAME));
        assertThat(profile.getEmail(), equalTo(EMAIL));
        assertThat(profile.getPictureURL(), equalTo(PICTURE_URL));
        assertThat(profile.getCreatedAt().getTime(), equalTo(CREATED_AT_TIMESTAMP));
        assertThat(profile.getExtraInfo().size(), equalTo(0));
    }

    @Test
    public void shouldHandleExtraProfileInfo() throws Exception {
        Map<String, Object> values = new HashMap<String, Object>();
        values.put("user_id", USER_ID);
        values.put("extra_key", EXTRA_VALUE);
        UserProfile profile = new UserProfile(values);
        assertValidProfile(profile);
        assertThat(profile.getExtraInfo(), hasEntry("extra_key", EXTRA_VALUE));
    }

    @Test
    public void shouldHandleIdentities() throws Exception {
        Map<String, Object> values = new HashMap<String, Object>();
        Map<String, Object> identityValue = new HashMap<>();
        Map<String, Object> profileData = new HashMap<>();
        identityValue.put("user_id", USER_ID);
        identityValue.put("connection", FACEBOOK);
        identityValue.put("provider", FACEBOOK);
        identityValue.put("isSocial", true);
        identityValue.put("access_token", TOKEN);
        identityValue.put("access_token_secret", SECRET);
        identityValue.put("profileData", profileData);
        profileData.put("name", "John Doe");

        values.put("user_id", USER_ID);
        values.put("identities", Collections.singletonList(identityValue));
        UserProfile profile = new UserProfile(values);
        assertValidProfile(profile);
        assertThat(profile.getIdentities(), hasSize(1));
        UserIdentity identity = profile.getIdentities().get(0);
        assertThat(identity, isA(UserIdentity.class));
        assertThat(identity.getId(), equalTo(USER_ID));
        assertThat(identity.getProvider(), equalTo(FACEBOOK));
        assertThat(identity.getConnection(), equalTo(FACEBOOK));
        assertThat(identity.getAccessToken(), equalTo(TOKEN));
        assertThat(identity.getAccessTokenSecret(), equalTo(SECRET));
    }

    private void assertValidProfile(UserProfile profile) {
        assertThat(profile, is(notNullValue()));
        assertThat(profile.getId(), equalTo(USER_ID));
    }
}

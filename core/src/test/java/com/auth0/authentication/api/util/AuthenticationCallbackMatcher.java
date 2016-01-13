/*
 * AuthenticationCallbackMatcher.java
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

package com.auth0.authentication.api.util;

import com.auth0.Token;
import com.auth0.UserProfile;
import com.jayway.awaitility.core.ConditionTimeoutException;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import static com.jayway.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

public class AuthenticationCallbackMatcher extends BaseMatcher<MockAuthenticationCallback> {

    private final Matcher<Token> tokenMatcher;
    private final Matcher<UserProfile> profileMatcher;
    private final Matcher<Throwable> errorMatcher;

    public AuthenticationCallbackMatcher(Matcher<Token> tokenMatcher, Matcher<UserProfile> profileMatcher, Matcher<Throwable> errorMatcher) {
        this.tokenMatcher = tokenMatcher;
        this.profileMatcher = profileMatcher;
        this.errorMatcher = errorMatcher;
    }

    @Override
    public boolean matches(Object item) {
        MockAuthenticationCallback callback = (MockAuthenticationCallback) item;
        try {
            await().until(callback.token(), tokenMatcher);
            await().until(callback.profile(), profileMatcher);
            await().until(callback.error(), errorMatcher);
            return true;
        } catch (ConditionTimeoutException e) {
            return false;
        }
    }

    @Override
    public void describeTo(Description description) {
        description
                .appendText("successful method be called");
    }

    public static Matcher<MockAuthenticationCallback> hasTokenAndProfile() {
        return new AuthenticationCallbackMatcher(is(notNullValue(Token.class)), is(notNullValue(UserProfile.class)), is(nullValue(Throwable.class)));
    }

    public static Matcher<MockAuthenticationCallback> hasNeitherTokeNorProfile() {
        return new AuthenticationCallbackMatcher(is(nullValue(Token.class)), is(nullValue(UserProfile.class)), is(notNullValue(Throwable.class)));
    }

}

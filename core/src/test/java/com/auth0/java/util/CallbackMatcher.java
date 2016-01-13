/*
 * CallbackMatcher.java
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

package com.auth0.java.util;

import com.jayway.awaitility.core.ConditionTimeoutException;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import static com.jayway.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

public class CallbackMatcher<T> extends BaseMatcher<MockBaseCallback<T>> {
    private final Matcher<T> payloadMatcher;
    private final Matcher<Throwable> errorMatcher;

    public CallbackMatcher( Matcher<T> payloadMatcher, Matcher<Throwable> errorMatcher) {
        this.payloadMatcher = payloadMatcher;
        this.errorMatcher = errorMatcher;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean matches(Object item) {
        MockBaseCallback<T> callback = (MockBaseCallback<T>) item;
        try {
            await().until(callback.payload(), payloadMatcher);
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

    public static <T> Matcher<MockBaseCallback<T>> hasPayloadOfType(Class<T> clazz) {
        return new CallbackMatcher<>(isA(clazz), is(nullValue(Throwable.class)));
    }

    public static <T> Matcher<MockBaseCallback<T>> hasPayload(T payload) {
        return new CallbackMatcher<>(equalTo(payload), is(nullValue(Throwable.class)));
    }

    public static <T> Matcher<MockBaseCallback<T>> hasNoPayloadOfType(Class<T> clazz) {
        return new CallbackMatcher<>(is(nullValue(clazz)), is(notNullValue(Throwable.class)));
    }

    public static Matcher<MockBaseCallback<Void>> hasNoError() {
        return new CallbackMatcher<>(is(nullValue(Void.class)), is(nullValue(Throwable.class)));
    }

    public static Matcher<MockBaseCallback<Void>> hasError() {
        return new CallbackMatcher<>(is(nullValue(Void.class)), is(notNullValue(Throwable.class)));
    }
}

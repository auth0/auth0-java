/*
 * APIExceptionMather.java
 *
 * Copyright (c) 2014 Auth0 (http://auth0.com)
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

import com.auth0.api.APIClientException;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;

public class APIClientExceptionMatcher extends BaseMatcher<APIClientException> {

    private Matcher<Throwable> causeMatcher;
    private Matcher<Integer> statusCodeMatcher;
    private Matcher<Map<? extends String, ?>> errorMatcher;

    private APIClientExceptionMatcher() {}

    private APIClientExceptionMatcher(Matcher<Throwable> causeMatcher, Matcher<Integer> statusCodeMatcher, Matcher<Map<? extends String, ?>> errorMatcher) {
        this.causeMatcher = causeMatcher;
        this.statusCodeMatcher = statusCodeMatcher;
        this.errorMatcher = errorMatcher;
    }

    @Override
    public boolean matches(Object o) {
        APIClientException exception = (APIClientException) o;
        return causeMatcher.matches(exception.getCause())
                && statusCodeMatcher.matches(exception.getStatusCode())
                && errorMatcher.matches(exception.getResponseError());
    }

    @Override
    public void describeTo(Description description) {
        description
                .appendText("APIClientException with values: ")
                .appendDescriptionOf(causeMatcher)
                .appendText(" ")
                .appendDescriptionOf(statusCodeMatcher)
                .appendText(" ")
                .appendDescriptionOf(errorMatcher)
                .appendText(" ");
    }

    public static Matcher<APIClientException> hasGenericErrorWith(int statusCode, Throwable cause) {
        return new APIClientExceptionMatcher(equalTo(cause), equalTo(statusCode), Matchers.<Map<? extends String, ?>>equalTo(new HashMap<String, Object>()));
    }

    public static Matcher<APIClientException> hasErrorWith(int statusCode, Throwable cause, Object error) {
        return new APIClientExceptionMatcher(equalTo(cause), equalTo(statusCode), hasEntry("error", error));
    }
}

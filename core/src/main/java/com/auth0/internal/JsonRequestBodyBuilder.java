/*
 * JsonRequestBodyBuilder.java
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

package com.auth0.internal;

import com.auth0.RequestBodyBuildException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;

/**
 * Converts a POJO to JSON stored in a {@link RequestBody}
 */
abstract class JsonRequestBodyBuilder {

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static RequestBody createBody(Object pojo, ObjectWriter writer) throws RequestBodyBuildException {
        try {
            return RequestBody.create(JSON, writer.writeValueAsBytes(pojo));
        } catch (JsonProcessingException e) {
            throw new RequestBodyBuildException("Failed to convert " + pojo.getClass().getName() + " to JSON", e);
        }
    }
}

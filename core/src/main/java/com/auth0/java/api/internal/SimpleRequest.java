/*
 * AuthenticationRequest.java
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

package com.auth0.java.api.internal;

import com.auth0.java.api.APIClientException;
import com.auth0.java.api.ParameterizableRequest;
import com.auth0.java.api.Request;
import com.auth0.java.api.RequestBodyBuildException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

class SimpleRequest<T> extends BaseRequest<T> implements Request<T>, ParameterizableRequest<T>, Callback {

    private static final String TAG = SimpleRequest.class.getName();

    private final ObjectReader errorReader;
    private final String method;

    public SimpleRequest(HttpUrl url, OkHttpClient client, ObjectMapper mapper, String httpMethod, Class<T> clazz) {
        super(url, client, mapper.reader(clazz), mapper.writer());
        this.errorReader = mapper.reader(new TypeReference<Map<String, Object>>() {});
        this.method = httpMethod;
    }

    public SimpleRequest(HttpUrl url, OkHttpClient client, ObjectMapper mapper, String httpMethod) {
        super(url, client, mapper.reader(new TypeReference<Map<String, Object>>() {}), mapper.writer());
        this.errorReader = mapper.reader(new TypeReference<Map<String, Object>>() {
        });
        this.method = httpMethod;
    }

    @Override
    public void onResponse(Response response) throws IOException {
        //Log.d(TAG, String.format("Received response from request to %s with status code %d", response.request().urlString(), response.code()));
        final InputStream byteStream = response.body().byteStream();
        if (!response.isSuccessful()) {
            Throwable throwable;
            try {
                Map<String, Object> payload = errorReader.readValue(byteStream);
                throwable = new APIClientException("Request failed with response " + payload, response.code(), payload);
            } catch (IOException e) {
                throwable = new APIClientException("Request failed", response.code(), null);
            }
            postOnFailure(throwable);
            return;
        }

        try {
            //Log.d(TAG, "Received successful response from " + response.request().urlString());
            T payload = getReader().readValue(byteStream);
            postOnSuccess(payload);
        } catch (IOException e) {
            postOnFailure(new APIClientException("Request failed", response.code(), null));
        }
    }

    @Override
    protected com.squareup.okhttp.Request doBuildRequest(com.squareup.okhttp.Request.Builder builder) throws RequestBodyBuildException {
        RequestBody body = buildBody();
        return newBuilder()
                .method(method, body)
                .build();
    }
}

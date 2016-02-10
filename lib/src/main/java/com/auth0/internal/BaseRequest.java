/*
 * BaseRequest.java
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

import com.auth0.APIException;
import com.auth0.Auth0Exception;
import com.auth0.RequestBodyBuildException;
import com.auth0.authentication.ParameterBuilder;
import com.auth0.callback.BaseCallback;
import com.auth0.request.AuthorizableRequest;
import com.auth0.request.ParameterizableRequest;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

abstract class BaseRequest<T> implements ParameterizableRequest<T>, AuthorizableRequest<T>, Callback {

    private final Map<String, String> headers;
    protected final HttpUrl url;
    protected final OkHttpClient client;
    private final ObjectReader reader;
    private final ObjectReader errorReader;
    private final ObjectWriter writer;
    private final ParameterBuilder builder;

    private BaseCallback<T> callback;

    protected BaseRequest(HttpUrl url, OkHttpClient client, ObjectReader reader, ObjectReader errorReader, ObjectWriter writer) {
        this(url, client, reader, errorReader, writer, null);
    }

    public BaseRequest(HttpUrl url, OkHttpClient client, ObjectReader reader, ObjectReader errorReader, ObjectWriter writer, BaseCallback<T> callback) {
        this.url = url;
        this.client = client;
        this.reader = reader;
        this.errorReader = errorReader;
        this.writer = writer;
        this.callback = callback;
        this.headers = new HashMap<>();
        this.builder = ParameterBuilder.newEmptyBuilder();
    }

    protected void setCallback(BaseCallback<T> callback) {
        this.callback = callback;
    }

    protected void postOnSuccess(final T payload) {
        this.callback.onSuccess(payload);
    }

    protected final void postOnFailure(final Auth0Exception error) {
        this.callback.onFailure(error);
    }

    protected Request.Builder newBuilder() {
        final Request.Builder builder = new Request.Builder()
                .url(url);
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            builder.addHeader(entry.getKey(), entry.getValue());
        }
        return builder;
    }

    protected ObjectReader getReader() {
        return reader;
    }

    protected RequestBody buildBody() throws RequestBodyBuildException {
        return JsonRequestBodyBuilder.createBody(builder.asDictionary(), writer);
    }

    protected APIException parseUnsuccessfulResponse(Response response) {
        try {
            final InputStream byteStream = response.body().byteStream();
            Map<String, Object> payload = errorReader.readValue(byteStream);
            return new APIException("Request to " + url + " failed with response " + payload, response.code(), payload);
        } catch (Exception e) {
            return new APIException("Request to " + url + " failed", response.code(), null);
        }
    }

    private static abstract class CallbackTask<T> implements Runnable {
        protected final BaseCallback<T> callback;

        protected CallbackTask(BaseCallback<T> callback) {
            this.callback = callback;
        }
    }

    @Override
    public void onFailure(Request request, IOException e) {
        postOnFailure(new Auth0Exception("Failed to execute request to " + url.toString(), e));
    }

    @Override
    public ParameterizableRequest<T> addHeader(String name, String value) {
        headers.put(name, value);
        return this;
    }

    @Override
    public AuthorizableRequest<T> setBearer(String jwt) {
        addHeader("Authorization", "Bearer " + jwt);
        return this;
    }

    @Override
    public ParameterBuilder getParameterBuilder() {
        return builder;
    }

    @Override
    public void start(BaseCallback<T> callback) {
        setCallback(callback);
        try {
            Request request = doBuildRequest(newBuilder());
            client.newCall(request).enqueue(this);
        } catch (RequestBodyBuildException e) {
            callback.onFailure(e);
        }
    }

    protected abstract Request doBuildRequest(Request.Builder builder) throws RequestBodyBuildException;
}

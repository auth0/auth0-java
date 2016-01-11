/*
 * ResponseCallbackTask.java
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
import com.auth0.java.api.AuthorizableRequest;
import com.auth0.java.api.ParameterizableRequest;
import com.auth0.java.api.RequestBodyBuildException;
import com.auth0.java.api.callback.BaseCallback;
import com.auth0.java.util.Build;
import com.auth0.java.util.Log;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    private static final String TAG = BaseCallback.class.getName();

    private final Map<String, String> headers;
    private final Map<String, Object> parameters;
    private final HttpUrl url;
    private final OkHttpClient client;
    private final ObjectReader reader;
    private final ObjectWriter writer;
    protected final ObjectReader errorReader;

    private BaseCallback<T> callback;

    protected BaseRequest(HttpUrl url, OkHttpClient client, ObjectReader reader, ObjectWriter writer) {
        this(url, client, reader, writer, null);
    }

    public BaseRequest(HttpUrl url, OkHttpClient client, ObjectReader reader, ObjectWriter writer, BaseCallback<T> callback) {
        this.url = url;
        this.client = client;
        this.reader = reader;
        this.writer = writer;
        this.callback = callback;
        this.headers = new HashMap<>();
        this.parameters = new HashMap<>();
        this.headers.put("User-Agent", String.format("Android %s (%s %s;)", Build.VERSION.RELEASE, Build.MODEL, Build.MANUFACTURER));
        // TODO fix this, pass object mapper as parameter
        this.errorReader = new ObjectMapper().reader(new TypeReference<Map<String, Object>>() {});
    }

    protected void setCallback(BaseCallback<T> callback) {
        this.callback = callback;
    }

    protected void postOnSuccess(final T payload) {
        this.callback.onSuccess(payload);
    }

    protected final void postOnFailure(final Throwable error) {
        Log.e(TAG, "Failed to make request to " + url, error);
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

    protected Map<String, Object> getParameters() {
        return parameters;
    }

    protected ObjectReader getReader() {
        return reader;
    }

    protected RequestBody buildBody() throws RequestBodyBuildException {
        return JsonRequestBodyBuilder.createBody(parameters, writer);
    }

    private static abstract class CallbackTask<T> implements Runnable {
        protected final BaseCallback<T> callback;

        protected CallbackTask(BaseCallback<T> callback) {
            this.callback = callback;
        }
    }

    @Override
    public void onFailure(Request request, IOException e) {
        postOnFailure(e);
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
    public ParameterizableRequest<T> addParameters(Map<String, Object> parameters) {
        if (parameters != null) {
            this.parameters.putAll(parameters);
        }
        return this;
    }

    @Override
    public void start(BaseCallback<T> callback) {
        setCallback(callback);
        try {
            Request request = doBuildRequest(newBuilder());
            client.newCall(request).enqueue(this);
        } catch (RequestBodyBuildException e) {
            Log.e(TAG, "Failed to build JSON body with parameters " + parameters, e);
            callback.onFailure(new APIClientException("Failed to send request to " + url.toString(), e));
        }
    }

    @Override
    public T execute() throws Throwable {
        Request request;
        try {
            request = doBuildRequest(newBuilder());
        } catch (RequestBodyBuildException e) {
            Log.e(TAG, "Failed to build JSON body with parameters " + parameters, e);
            throw new APIClientException("Failed to send request to " + url.toString(), e);
        }

        Response response;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            Log.e(TAG, "Request failed to execute", e);
            throw new APIClientException("Failed to execute request to " + url.toString(), e);
        }

        Log.d(TAG, String.format("Received response from request to %s with status code %d", response.request().urlString(), response.code()));
        final InputStream byteStream = response.body().byteStream();
        if (!response.isSuccessful()) {
            Throwable throwable;
            try {
                Map<String, Object> payload = errorReader.readValue(byteStream);
                throwable = new APIClientException("Request failed with response " + payload, response.code(), payload);
            } catch (IOException e) {
                throwable = new APIClientException("Request failed", response.code(), null);
            }
            throw throwable;
        }

        try {
            Log.d(TAG, "Received successful response from " + response.request().urlString());
            T payload = getReader().readValue(byteStream);
            return payload;
        } catch (IOException e) {
            throw new APIClientException("Request failed", response.code(), null);
        }
    }

    protected abstract Request doBuildRequest(Request.Builder builder) throws RequestBodyBuildException;
}

/*
 * SimpleRequest.java
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

package com.auth0.request.internal;

import com.auth0.APIException;
import com.auth0.Auth0Exception;
import com.auth0.RequestBodyBuildException;
import com.auth0.request.ParameterizableRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.Reader;

class SimpleRequest<T> extends BaseRequest<T> implements ParameterizableRequest<T>, Callback {

    private final String method;

    public SimpleRequest(HttpUrl url, OkHttpClient client, Gson gson, String httpMethod, Class<T> clazz) {
        super(url, client, gson, gson.getAdapter(clazz));
        this.method = httpMethod;
    }

    public SimpleRequest(HttpUrl url, OkHttpClient client, Gson gson, String httpMethod) {
        super(url, client, gson, gson.getAdapter(new TypeToken<T>(){}));
        this.method = httpMethod;
    }

    @Override
    public void onResponse(Response response) throws IOException {
        if (!response.isSuccessful()) {
            APIException exception = parseUnsuccessfulResponse(response);
            postOnFailure(exception);
            return;
        }

        try {
            Reader charStream = response.body().charStream();
            T payload = getAdapter().fromJson(charStream);
            postOnSuccess(payload);
        } catch (IOException e) {
            postOnFailure(new Auth0Exception("Failed to parse response to request to " + url, e));
        }
    }

    @Override
    protected Request doBuildRequest(Request.Builder builder) throws RequestBodyBuildException {
        RequestBody body = buildBody();
        return newBuilder()
                .method(method, body)
                .build();
    }

    @Override
    public T execute() throws Auth0Exception {
        Request request = doBuildRequest(newBuilder());

        Response response;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            throw new Auth0Exception("Failed to execute request to " + url, e);
        }

        if (!response.isSuccessful()) {
            throw parseUnsuccessfulResponse(response);
        }

        try {
            Reader charStream = response.body().charStream();
            return getAdapter().fromJson(charStream);
        } catch (IOException e) {
            throw new Auth0Exception("Failed to parse response to request to " + url, e);
        }
    }
}

/*
 * VoidRequest.java
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

package com.auth0.authentication.api.internal;

import com.auth0.authentication.api.APIClientException;
import com.auth0.authentication.api.RequestBodyBuildException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

class VoidRequest extends BaseRequest<Void> implements Callback {

    private final ObjectReader errorReader;
    private final String httpMethod;

    public VoidRequest(HttpUrl url, OkHttpClient client, ObjectMapper mapper, String httpMethod) {
        super(url, client, null, mapper.writer());
        this.httpMethod = httpMethod;
        this.errorReader = mapper.reader(new TypeReference<Map<String, Object>>() {
        });
    }

    @Override
    public void onResponse(Response response) throws IOException {
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

        postOnSuccess(null);
    }

    @Override
    protected Request doBuildRequest(Request.Builder builder) {
        RequestBody body = buildBody();
        return newBuilder()
                .method(httpMethod, body)
                .build();
    }

    @Override
    public Void execute() throws Throwable {
        Request request;
        try {
            request = doBuildRequest(newBuilder());
        } catch (RequestBodyBuildException e) {
            throw new APIClientException("Failed to send request to " + url.toString(), e);
        }

        Response response;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            throw new APIClientException("Failed to execute request to " + url.toString(), e);
        }

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
        return null;
    }
}

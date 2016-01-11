/*
 * ApplicationInfoRequest.java
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
import com.auth0.java.core.Application;
import com.auth0.java.util.Log;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.util.Map;

class ApplicationInfoRequest extends BaseRequest<Application> implements Callback {

    private static final String TAG = ApplicationInfoRequest.class.getName();

    public ApplicationInfoRequest(OkHttpClient client, HttpUrl url, ObjectMapper mapper) {
        super(url, client, mapper.reader(Application.class), null);
    }

    @Override
    protected Request doBuildRequest(Request.Builder builder) {
        final Request request = builder.build();
        Log.v(TAG, "Fetching application info from " + request.urlString());
        return request;
    }

    @Override
    public void onFailure(Request request, IOException e) {
        postOnFailure(e);
    }

    @Override
    public void onResponse(Response response) throws IOException {
        if (!response.isSuccessful()) {
            String message = "Received app info failed response with code " + response.code() + " and body " + response.body().string();
            postOnFailure(new IOException(message));
            return;
        }
        try {
            String json = response.body().string();
            if (json.length() < 16) {
                throw new JSONException("Failed to parse JSONP");
            }
            json = json.substring(16); // replaces tokenizer.skipPast("Auth0.setClient(") because official (not android's) org.json does not have the method
            JSONTokener tokenizer = new JSONTokener(json);
            //tokenizer.skipPast("Auth0.setClient(");
            if (!tokenizer.more()) {
                postOnFailure(tokenizer.syntaxError("Invalid App Info JSONP"));
                return;
            }
            Object nextValue = tokenizer.nextValue();
            if (!(nextValue instanceof JSONObject)) {
                tokenizer.back();
                postOnFailure(tokenizer.syntaxError("Invalid JSON value of App Info"));
            }
            JSONObject jsonObject = (JSONObject) nextValue;
            Log.d(TAG, "Obtained JSON object from JSONP: " + jsonObject);
            Application app = getReader().readValue(jsonObject.toString());
            postOnSuccess(app);
        } catch (JSONException | IOException e) {
            postOnFailure(new APIClientException("Failed to parse JSONP", e));
        }
    }

    @Override
    public ParameterizableRequest<Application> addParameters(Map<String, Object> parameters) {
        return this;
    }

    @Override
    public ParameterizableRequest<Application> addHeader(String name, String value) {
        return this;
    }
}

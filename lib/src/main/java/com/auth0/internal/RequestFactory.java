/*
 * RequestFactory.java
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

import com.auth0.authentication.result.Credentials;
import com.auth0.request.AuthenticationRequest;
import com.auth0.request.AuthorizableRequest;
import com.auth0.request.ParameterizableRequest;
import com.auth0.util.Metrics;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;

import java.util.Map;

public class RequestFactory {

    private String clientInfo;
    private String userAgent;

    public void setClientInfo(String clientInfo) {
        this.clientInfo = clientInfo;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public <T> ParameterizableRequest<T> GET(HttpUrl url, OkHttpClient client, ObjectMapper mapper, Class<T> clazz) {
        final SimpleRequest<T> request = new SimpleRequest<>(url, client, mapper, "GET", clazz);
        addMetrics(request);
        return request;
    }

    public AuthenticationRequest authenticationPOST(HttpUrl url, OkHttpClient client, ObjectMapper mapper) {
        final BaseAuthenticationRequest request = new BaseAuthenticationRequest(url, client, mapper, "POST", Credentials.class);
        addMetrics(request);
        return request;
    }

    public <T> ParameterizableRequest<T> POST(HttpUrl url, OkHttpClient client, ObjectMapper mapper, Class<T> clazz) {
        final SimpleRequest<T> request = new SimpleRequest<>(url, client, mapper, "POST", clazz);
        addMetrics(request);
        return request;
    }

    public ParameterizableRequest<Map<String, Object>> rawPOST(HttpUrl url, OkHttpClient client, ObjectMapper mapper) {
        final SimpleRequest<Map<String, Object>> request = new SimpleRequest<>(url, client, mapper, "POST");
        addMetrics(request);
        return request;
    }

    public ParameterizableRequest<Void> POST(HttpUrl url, OkHttpClient client, ObjectMapper mapper) {
        final VoidRequest request = new VoidRequest(url, client, mapper, "POST");
        addMetrics(request);
        return request;
    }

    public ParameterizableRequest<Void> POST(HttpUrl url, OkHttpClient client, ObjectMapper mapper, String jwt) {
        final AuthorizableRequest<Void> request = new VoidRequest(url, client, mapper, "POST")
                .setBearer(jwt);
        addMetrics(request);
        return request;
    }

    public <T> ParameterizableRequest<T> PUT(HttpUrl url, OkHttpClient client, ObjectMapper mapper, Class<T> clazz) {
        final SimpleRequest<T> request = new SimpleRequest<>(url, client, mapper, "PUT", clazz);
        addMetrics(request);
        return request;
    }

    public <T> ParameterizableRequest<T> PATCH(HttpUrl url, OkHttpClient client, ObjectMapper mapper, Class<T> clazz) {
        final SimpleRequest<T> request = new SimpleRequest<>(url, client, mapper, "GET", clazz);
        addMetrics(request);
        return request;
    }

    public <T> ParameterizableRequest<T> DELETE(HttpUrl url, OkHttpClient client, ObjectMapper mapper, Class<T> clazz) {
        final SimpleRequest<T> request = new SimpleRequest<>(url, client, mapper, "DELETE", clazz);
        addMetrics(request);
        return request;
    }

    private <T> void addMetrics(ParameterizableRequest<T> request) {
        if (this.clientInfo != null) {
            request.addHeader(Metrics.HEADER_NAME, this.clientInfo);
        }
        if (this.userAgent!= null) {
            request.addHeader("User-Agent", this.userAgent);
        }
    }
}

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

package com.auth0.api.internal;

import android.os.Handler;

import com.auth0.api.AuthorizableRequest;
import com.auth0.api.ParameterizableRequest;
import com.auth0.api.Request;
import com.auth0.core.Application;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;

import java.util.Map;

public class RequestFactory {

    private static String CLIENT_INFO;

    private RequestFactory() {}

    public static void setClientInfo(String clientInfo) {
        CLIENT_INFO = clientInfo;
    }

    public static <T> ParameterizableRequest<T> GET(HttpUrl url, OkHttpClient client, Handler handler, ObjectMapper mapper, Class<T> clazz) {
        return addMetricHeader(new com.auth0.api.internal.SimpleRequest<>(handler, url, client, mapper, "GET", clazz));
    }

    public static <T> ParameterizableRequest<T> POST(HttpUrl url, OkHttpClient client, Handler handler, ObjectMapper mapper, Class<T> clazz) {
        return addMetricHeader(new com.auth0.api.internal.SimpleRequest<>(handler, url, client, mapper, "POST", clazz));
    }

    public static ParameterizableRequest<Map<String, Object>> rawPOST(HttpUrl url, OkHttpClient client, Handler handler, ObjectMapper mapper) {
        final com.auth0.api.internal.SimpleRequest<Map<String, Object>> request = new com.auth0.api.internal.SimpleRequest<>(handler, url, client, mapper, "POST");
        addMetricHeader(request);
        return request;
    }

    public static ParameterizableRequest<Void> POST(HttpUrl url, OkHttpClient client, Handler handler, ObjectMapper mapper) {
        return addMetricHeader(new com.auth0.api.internal.VoidRequest(handler, url, client, mapper, "POST"));
    }

    public static ParameterizableRequest<Void> POST(HttpUrl url, OkHttpClient client, Handler handler, ObjectMapper mapper, String jwt) {
        final AuthorizableRequest<Void> request = new com.auth0.api.internal.VoidRequest(handler, url, client, mapper, "POST")
                .setBearer(jwt);
        return addMetricHeader(request);
    }

    public static <T> ParameterizableRequest<T> PUT(HttpUrl url, OkHttpClient client, Handler handler, ObjectMapper mapper, Class<T> clazz) {
        return addMetricHeader(new com.auth0.api.internal.SimpleRequest<>(handler, url, client, mapper, "PUT", clazz));
    }

    public static <T> ParameterizableRequest<T> PATCH(HttpUrl url, OkHttpClient client, Handler handler, ObjectMapper mapper, Class<T> clazz) {
        return addMetricHeader(new com.auth0.api.internal.SimpleRequest<>(handler, url, client, mapper, "GET", clazz));
    }

    public static <T> ParameterizableRequest<T> DELETE(HttpUrl url, OkHttpClient client, Handler handler, ObjectMapper mapper, Class<T> clazz) {
        return addMetricHeader(new com.auth0.api.internal.SimpleRequest<>(handler, url, client, mapper, "DELETE", clazz));
    }

    public static Request<Application> newApplicationInfoRequest(HttpUrl url, OkHttpClient client, Handler handler, ObjectMapper mapper) {
        return addMetricHeader(new com.auth0.api.internal.ApplicationInfoRequest(handler, client, url, mapper));
    }

    private static <T> ParameterizableRequest<T> addMetricHeader(ParameterizableRequest<T> request) {
        if (CLIENT_INFO != null) {
            request.addHeader("Auth0-Client", CLIENT_INFO);
        }
        return request;
    }
}

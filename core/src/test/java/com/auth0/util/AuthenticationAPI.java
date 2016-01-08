/*
 * AuthenticationAPI.java
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

package com.auth0.util;

import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import com.squareup.okhttp.mockwebserver.RecordedRequest;

import java.io.IOException;

public class AuthenticationAPI  {

    public static final String REFRESH_TOKEN = "REFRESH_TOKEN";
    public static final String ID_TOKEN = "ID_TOKEN";
    public static final String ACCESS_TOKEN = "ACCESS_TOKEN";
    public static final String BEARER = "BEARER";
    public static final String GENERIC_TOKEN = "GENERIC_TOKEN";
    public static final String NEW_ID_TOKEN = "NEW_ID_TOKEN";
    public static final int EXPIRES_IN = 1234567890;
    public static final String TOKEN_TYPE = "TOKEN_TYPE";

    private MockWebServer server;

    public AuthenticationAPI() throws IOException {
        this.server = new MockWebServer();
        this.server.start();
    }

    public String getDomain() {
        return server.url("/").toString();
    }

    public void shutdown() throws IOException {
        this.server.shutdown();
    }

    public RecordedRequest takeRequest() throws InterruptedException {
        return server.takeRequest();
    }

    public AuthenticationAPI willReturnValidApplicationResponse() {
        return willReturnApplicationResponseWithBody("Auth0.setClient({\"id\":\"CLIENTID\",\"tenant\":\"overmind\",\"subscription\":\"free\",\"authorize\":\"https://samples.auth0.com/authorize\",\"callback\":\"http://localhost:3000/\",\"hasAllowedOrigins\":true,\"strategies\":[{\"name\":\"twitter\",\"connections\":[{\"name\":\"twitter\"}]}]});", 200);
    }

    public AuthenticationAPI willReturnSuccessfulChangePassword() {
        server.enqueue(responseWithJSON("NOT REALLY A JSON", 200));
        return this;
    }

    public AuthenticationAPI willReturnSuccessfulUnlinkAccount() {
        server.enqueue(responseWithJSON("NOT REALLY A JSON", 200));
        return this;
    }

    public AuthenticationAPI willReturnGenericDelegationToken() {
        String json = "{\n" +
                "  \"token\": \"" + GENERIC_TOKEN + "\"\n" +
                "}";
        server.enqueue(responseWithJSON(json, 200));
        return this;
    }

    public AuthenticationAPI willReturnSuccessfulPasswordlessStart() {
        String json = "{\n" +
                "  \"phone+number\": \"+1098098098\"\n" +
                "}";
        server.enqueue(responseWithJSON(json, 200));
        return this;
    }

    public AuthenticationAPI willReturnNewIdToken() {
        String json = "{\n" +
                "  \"id_token\": \"" + NEW_ID_TOKEN + "\",\n" +
                "  \"expires_in\": " + EXPIRES_IN + ",\n" +
                "  \"token_type\": \"" + TOKEN_TYPE + "\"\n" +
                "}";
        server.enqueue(responseWithJSON(json, 200));
        return this;
    }

    public AuthenticationAPI willReturnSuccessfulSignUp() {
        String json = "{\n" +
                "    \"_id\": \"gjsmgdkjs72jljsf2dsdhh\", \n" +
                "    \"email\": \"support@auth0.com\", \n" +
                "    \"email_verified\": false, \n" +
                "    \"username\": \"support\"\n" +
                "}";
        server.enqueue(responseWithJSON(json, 200));
        return this;
    }

    public AuthenticationAPI willReturnSuccessfulLogin() {
        String json = "{\n" +
                "  \"refresh_token\": \"" + REFRESH_TOKEN + "\",\n" +
                "  \"id_token\": \"" + ID_TOKEN + "\",\n" +
                "  \"access_token\": \"" + ACCESS_TOKEN + "\",\n" +
                "  \"token_type\": \"" + BEARER + "\"\n" +
                "}";
        server.enqueue(responseWithJSON(json, 200));
        return this;
    }

    public AuthenticationAPI willReturnFailedLogin() {
        String json = "{\n" +
                "  \"error\": \"invalid_request\",\n" +
                "  \"error_description\": \"a random error\"\n" +
                "}";
        server.enqueue(responseWithJSON(json, 400));
        return this;
    }

    public AuthenticationAPI willReturnTokenInfo() {
        String json = "{\n" +
                "  \"email\": \"p@p.xom\",\n" +
                "  \"email_verified\": false,\n" +
                "  \"picture\": \"https://secure.gravatar.com/avatar/cfacbe113a96fdfc85134534771d88b4?s=480&r=pg&d=https%3A%2F%2Fssl.gstatic.com%2Fs2%2Fprofiles%2Fimages%2Fsilhouette80.png\",\n" +
                "  \"user_id\": \"auth0|53b995f8bce68d9fc900099c\",\n" +
                "  \"name\": \"p@p.xom\",\n" +
                "  \"nickname\": \"p\",\n" +
                "  \"identities\": [\n" +
                "    {\n" +
                "      \"user_id\": \"53b995f8bce68d9fc900099c\",\n" +
                "      \"provider\": \"auth0\",\n" +
                "      \"connection\": \"Username-Password-Authentication\",\n" +
                "      \"isSocial\": false\n" +
                "    }\n" +
                " ],\n" +
                "  \"created_at\": \"2014-07-06T18:33:49.005Z\",\n" +
                "  \"username\": \"p\",\n" +
                "  \"updated_at\": \"2015-09-30T19:43:48.499Z\"\n" +
                "}";
        server.enqueue(responseWithJSON(json, 200));
        return this;
    }

    public AuthenticationAPI willReturnApplicationResponseWithBody(String body, int statusCode) {
        MockResponse response =  new MockResponse()
                .setResponseCode(statusCode)
                .addHeader("Content-Type", "application/x-javascript")
                .setBody(body);
        server.enqueue(response);
        return this;
    }

    private MockResponse responseWithJSON(String json, int statusCode) {
        return new MockResponse()
                .setResponseCode(statusCode)
                .addHeader("Content-Type", "application/json")
                .setBody(json);
    }

}

/*
 * Auth0Test.java
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

package com.auth0.java.core;

import com.auth0.java.api.authentication.AuthenticationAPIClient;

import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;

public class Auth0Test {

    private static final String CLIENT_ID = "CLIENT_ID";
    private static final String DOMAIN = "samples.auth0.com";
    private static final String CONFIG_DOMAIN_CUSTOM = "config.mydomain.com";
    private static final String EU_DOMAIN = "samples.eu.auth0.com";
    private static final String AU_DOMAIN = "samples.au.auth0.com";
    private static final String OTHER_DOMAIN = "samples-test.other-subdomain.other.auth0.com";

    @Test
    public void shouldBuildWithClientIdAndDomain() throws Exception {
        Auth0 auth0 = new Auth0(CLIENT_ID, DOMAIN);
        assertThat(auth0.getClientId(), equalTo(CLIENT_ID));
        assertThat(auth0.getDomainUrl(), equalTo("https://samples.auth0.com"));
        assertThat(auth0.getConfigurationUrl(), equalTo("https://cdn.auth0.com"));
    }

    @Test
    public void shouldBuildWithConfigurationDomainToo() throws Exception {
        Auth0 auth0 = new Auth0(CLIENT_ID, DOMAIN, CONFIG_DOMAIN_CUSTOM);
        assertThat(auth0.getClientId(), equalTo(CLIENT_ID));
        assertThat(auth0.getDomainUrl(), equalTo("https://samples.auth0.com"));
        assertThat(auth0.getConfigurationUrl(), equalTo("https://config.mydomain.com"));
    }

    @Test
    public void shouldHandleEUInstance() throws Exception {
        Auth0 auth0 = new Auth0(CLIENT_ID, EU_DOMAIN);
        assertThat(auth0.getClientId(), equalTo(CLIENT_ID));
        assertThat(auth0.getDomainUrl(), equalTo("https://samples.eu.auth0.com"));
        assertThat(auth0.getConfigurationUrl(), equalTo("https://cdn.eu.auth0.com"));
    }

    @Test
    public void shouldHandleAUInstance() throws Exception {
        Auth0 auth0 = new Auth0(CLIENT_ID, AU_DOMAIN);
        assertThat(auth0.getClientId(), equalTo(CLIENT_ID));
        assertThat(auth0.getDomainUrl(), equalTo("https://samples.au.auth0.com"));
        assertThat(auth0.getConfigurationUrl(), equalTo("https://cdn.au.auth0.com"));
    }

    @Test
    public void shouldHandleOtherInstance() throws Exception {
        Auth0 auth0 = new Auth0(CLIENT_ID, OTHER_DOMAIN);
        assertThat(auth0.getClientId(), equalTo(CLIENT_ID));
        assertThat(auth0.getDomainUrl(), equalTo("https://samples-test.other-subdomain.other.auth0.com"));
        assertThat(auth0.getConfigurationUrl(), equalTo("https://cdn.other.auth0.com"));
    }

    @Test
    public void shouldHandleNonAuth0Domain() throws Exception {
        Auth0 auth0 = new Auth0(CLIENT_ID, "mydomain.com");
        assertThat(auth0.getClientId(), equalTo(CLIENT_ID));
        assertThat(auth0.getDomainUrl(), equalTo("https://mydomain.com"));
        assertThat(auth0.getConfigurationUrl(), equalTo("https://mydomain.com"));
    }

    @Test
    public void shouldBuildNewClient() throws Exception {
        Auth0 auth0 = new Auth0(CLIENT_ID, DOMAIN);
        AuthenticationAPIClient client = auth0.newAuthenticationAPIClient();
        assertThat(client, is(notNullValue()));
        assertThat(client.getBaseURL(), equalTo("https://samples.auth0.com"));
        assertThat(client.getClientId(), equalTo(CLIENT_ID));
    }

    @Test
    public void shouldReturnAuthorizeUrl() throws Exception {
        Auth0 auth0 = new Auth0(CLIENT_ID, DOMAIN);
        assertThat(auth0.getAuthorizeUrl(), equalTo("https://samples.auth0.com/authorize"));
    }
}
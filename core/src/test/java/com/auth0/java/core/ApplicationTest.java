/*
 * ApplicationTest.java
 *
 * Copyright (c) 2016 Auth0 (http://auth0.com)
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

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.auth0.java.core.Strategies.ADFS;
import static com.auth0.java.core.Strategies.Auth0;
import static com.auth0.java.core.Strategies.Facebook;
import static com.auth0.java.core.Strategies.Office365;
import static com.auth0.java.core.Strategies.SAMLP;
import static com.auth0.java.core.Strategies.Twitter;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class ApplicationTest {

    public static final String ID = "ID";
    public static final String TENANT = "TENANT";
    public static final String AUTHORIZE_URL = "AUTHORIZE";
    public static final String CALLBACK_URL = "CALLBACK";
    public static final String SUBSCRIPTION = "SUBSCRIPTION";
    public static final boolean HAS_ALLOWED_ORIGINS = true;

    @Test
    public void shouldInstantiateApplication() throws Exception {
        Application application = newApplicationWithStrategies(Auth0);
        assertThat(application, is(notNullValue()));
    }

    @Test
    public void shouldHaveApplicationInfo() throws Exception {
        Application application = newApplicationWithStrategies(Auth0);
        assertThat(application.getId(), equalTo(ID));
        assertThat(application.getTenant(), equalTo(TENANT));
        assertThat(application.getAuthorizeURL(), equalTo(AUTHORIZE_URL));
        assertThat(application.getCallbackURL(), equalTo(CALLBACK_URL));
        assertThat(application.getSubscription(), equalTo(SUBSCRIPTION));
        assertThat(application.isHasAllowedOrigins(), equalTo(HAS_ALLOWED_ORIGINS));
    }

    @Test
    public void shouldReturnDatabaseStrategy() throws Exception {
        Application application = newApplicationWithStrategies(Auth0);
        assertThat(application.getDatabaseStrategy(), is(notNullValue()));
        assertThat(application.getDatabaseStrategy().getName(), equalTo(Auth0.getName()));
    }

    @Test
    public void shouldReturnNoDatabaseStrategy() throws Exception {
        Application application = newApplicationWithStrategies(Facebook);
        assertThat(application.getDatabaseStrategy(), is(nullValue()));
    }

    @Test
    public void shouldReturnSocialStrategies() throws Exception {
        Application application = newApplicationWithStrategies(Facebook, Twitter, Auth0);
        assertThat(application.getSocialStrategies().size(), equalTo(2));
    }

    @Test
    public void shouldReturnEnterpriseStrategies() throws Exception {
        Application application = newApplicationWithStrategies(Auth0, ADFS, SAMLP, Office365);
        assertThat(application.getEnterpriseStrategies().size(), equalTo(3));
    }

    private static Strategy newStrategyFor(Strategies strategyMetadata) {
        return new Strategy(strategyMetadata.getName(), Arrays.asList(mock(Connection.class)));
    }

    private static Application newApplicationWithStrategies(Strategies... list) {
        List<Strategy> strategies = new ArrayList<>();
        for (Strategies str: list) {
            strategies.add(newStrategyFor(str));
        }
        Application application = new Application(ID, TENANT, AUTHORIZE_URL, CALLBACK_URL, SUBSCRIPTION, HAS_ALLOWED_ORIGINS, strategies);
        return application;
    }
}

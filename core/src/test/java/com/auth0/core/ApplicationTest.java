package com.auth0.core;

import com.auth0.android.BuildConfig;
import com.auth0.core.Application;
import com.auth0.core.Connection;
import com.auth0.core.Strategies;
import com.auth0.core.Strategy;
import com.google.common.collect.Lists;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.Arrays;
import java.util.List;

import static com.auth0.core.Strategies.ADFS;
import static com.auth0.core.Strategies.Auth0;
import static com.auth0.core.Strategies.Facebook;
import static com.auth0.core.Strategies.Office365;
import static com.auth0.core.Strategies.SAMLP;
import static com.auth0.core.Strategies.Twitter;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 18, manifest = Config.NONE)
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
        List<Strategy> strategies = Lists.newArrayList();
        for (Strategies str: list) {
            strategies.add(newStrategyFor(str));
        }
        Application application = new Application(ID, TENANT, AUTHORIZE_URL, CALLBACK_URL, SUBSCRIPTION, HAS_ALLOWED_ORIGINS, strategies);
        return application;
    }
}

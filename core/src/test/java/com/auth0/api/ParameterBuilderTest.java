package com.auth0.api;

import android.os.Build;

import com.auth0.android.BuildConfig;
import com.auth0.api.ParameterBuilder;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 18, manifest = Config.NONE)
public class ParameterBuilderTest {

    public static final String CLIENT_ID = "CLIENT ID";
    public static final String GRANT_TYPE = "password";
    public static final String CONNECTION = "AD";
    public static final String DEVICE = "ANDROID TEST DEVICE";

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    private ParameterBuilder builder;

    @Before
    public void setUp() throws Exception {
        this.builder = ParameterBuilder.newBuilder();
    }

    @Test
    public void shouldInstantiateWithNoArguments() throws Exception {
        assertThat(new ParameterBuilder(), is(notNullValue()));
        assertThat(ParameterBuilder.newBuilder(), is(notNullValue()));
    }

    @Test
    public void shouldInstantiateWithDefaultScope() throws Exception {
        assertThat(new ParameterBuilder().asDictionary(), hasEntry("scope", ParameterBuilder.SCOPE_OFFLINE_ACCESS));
        assertThat(ParameterBuilder.newBuilder().asDictionary(), hasEntry("scope", ParameterBuilder.SCOPE_OFFLINE_ACCESS));
    }

    @Test
    public void shouldInstantiateWithArguments() throws Exception {
        assertThat(new ParameterBuilder(new HashMap<String, Object>()), is(notNullValue()));
        assertThat(ParameterBuilder.newBuilder(new HashMap<String, Object>()), is(notNullValue()));
    }

    @Test
    public void shouldFailToInstantiateWithNullParameters() throws Exception {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(equalToIgnoringCase("Must provide non-null parameters"));
        new ParameterBuilder(null);
    }

    @Test
    public void shouldFailToInstantiateWithNullParametersInFactoryMethod() throws Exception {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(equalToIgnoringCase("Must provide non-null parameters"));
        ParameterBuilder.newBuilder(null);
    }

    @Test
    public void shouldSetClientID() throws Exception {
        assertThat(builder.setClientId(CLIENT_ID).asDictionary(), hasEntry("client_id", CLIENT_ID));
    }

    @Test
    public void shouldSetScope() throws Exception {
        Map<String, Object> parameters = builder.setScope(ParameterBuilder.SCOPE_OPENID).asDictionary();
        assertThat(parameters, hasEntry("scope", ParameterBuilder.SCOPE_OPENID));
        assertThat(parameters, not(hasEntry("device", Build.MODEL)));
    }

    @Test
    public void shouldSetDevice() throws Exception {
        Map<String, Object> parameters = builder.setDevice(DEVICE).asDictionary();
        assertThat(parameters, hasEntry("device", DEVICE));
    }

    @Test
    public void shouldSetScopeWithOfflineAccess() throws Exception {
        Map<String, Object> parameters = builder.setScope(ParameterBuilder.SCOPE_OFFLINE_ACCESS).asDictionary();
        assertThat(parameters, hasEntry("scope", ParameterBuilder.SCOPE_OFFLINE_ACCESS));
        assertThat(parameters, hasEntry("device", Build.MODEL));
    }

    @Test
    public void shouldSetGrantType() throws Exception {
        assertThat(builder.setGrantType(GRANT_TYPE).asDictionary(), hasEntry("grant_type", GRANT_TYPE));
    }

    @Test
    public void shouldSetConnection() throws Exception {
        assertThat(builder.setConnection(CONNECTION).asDictionary(), hasEntry("connection", CONNECTION));
    }

    @Test
    public void shouldAddArbitraryEntry() throws Exception {
        assertThat(builder.set("key", "value").asDictionary(), hasEntry("key", "value"));
    }

    @Test
    public void shouldAddAllFromDictionary() throws Exception {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("key", "value");
        assertThat(builder.addAll(parameters).asDictionary(), hasEntry("key", "value"));
    }

    @Test
    public void shouldDoNothingWhenAddingNullParameters() throws Exception {
        assertThat(builder.addAll(null).asDictionary(), hasEntry("scope", ParameterBuilder.SCOPE_OFFLINE_ACCESS));
    }

    @Test
    public void shouldProvideADictionaryCopy() throws Exception {
        Map<String, Object> parameters = builder.setClientId(CLIENT_ID).asDictionary();
        parameters.put("key", "value");
        assertThat(builder.asDictionary(), not(hasEntry("key", "value")));
    }

    private static Matcher<Map<? extends String, ?>> hasEntry(String key, Object value) {
        return Matchers.hasEntry(key, value);
    }
}

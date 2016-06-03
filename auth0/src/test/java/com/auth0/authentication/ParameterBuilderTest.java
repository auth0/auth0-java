/*
 * ParameterBuilderTest.java
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

package com.auth0.authentication;


import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

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
        this.builder = ParameterBuilder.newAuthenticationBuilder();
    }

    @Test
    public void shouldInstantiateWithNoArguments() throws Exception {
        assertThat(ParameterBuilder.newAuthenticationBuilder(), is(notNullValue()));
    }

    @Test
    public void shouldInstantiateWithDefaultScope() throws Exception {
        assertThat(ParameterBuilder.newAuthenticationBuilder().asDictionary(), hasEntry("scope", ParameterBuilder.SCOPE_OPENID));
    }

    @Test
    public void shouldInstantiateWithArguments() throws Exception {
        assertThat(ParameterBuilder.newBuilder(new HashMap<String, Object>()), is(notNullValue()));
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
        Map<String, Object> parameters = builder.setScope(ParameterBuilder.SCOPE_OFFLINE_ACCESS).asDictionary();
        assertThat(parameters, hasEntry("scope", ParameterBuilder.SCOPE_OFFLINE_ACCESS));
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
    public void shouldNotAddNullEntry() throws Exception {
        assertThat(builder.set("key", null).asDictionary(), not(hasEntry("key", null)));
    }

    @Test
    public void shouldAddAllFromDictionary() throws Exception {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("key", "value");
        assertThat(builder.addAll(parameters).asDictionary(), hasEntry("key", "value"));
    }

    @Test
    public void shouldSkipNullValuesOnAddAllFromDictionary() throws Exception {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("key", "value");
        parameters.put("null", null);
        assertThat(builder.addAll(parameters).asDictionary(), hasEntry("key", "value"));
        assertThat(builder.addAll(parameters).asDictionary(), not(hasEntry("null", null)));
    }

    @Test
    public void shouldDoNothingWhenAddingNullParameters() throws Exception {
        assertThat(builder.addAll(null).asDictionary(), hasEntry("scope", ParameterBuilder.SCOPE_OPENID));
    }

    @Test
    public void shouldProvideADictionaryCopy() throws Exception {
        Map<String, Object> parameters = builder.setClientId(CLIENT_ID).asDictionary();
        builder.set("key", "value");
        assertThat(parameters, not(hasEntry("key", "value")));
    }

    @Test
    public void shouldProvideAnImmutableDictionary() throws Exception {
        Map<String, Object> parameters = builder.setClientId(CLIENT_ID).asDictionary();
        try {
            parameters.put("key", "value");
        } catch (Exception e) {
            assertThat(e.getClass().getName(), is(equalTo(UnsupportedOperationException.class.getName())));
        }
    }

    private static Matcher<Map<? extends String, ?>> hasEntry(String key, Object value) {
        return Matchers.hasEntry(key, value);
    }
}

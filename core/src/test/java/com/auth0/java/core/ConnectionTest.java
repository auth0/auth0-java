/*
 * ConnectionTest.java
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

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class ConnectionTest {

    public static final String CONNECTION_NAME = "Username-Password";
    public static final Object VALUE = "value";
    public static final String KEY = "key";

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void shouldBuildConnectionWithName() {
        Map<String, Object> values = new HashMap<>();
        values.put("name", CONNECTION_NAME);
        Connection connection = new Connection(values);
        assertNotNull(connection);
        assertThat(connection.getName(), equalTo(CONNECTION_NAME));
    }

    @Test
    public void shouldBuildConnectionWithValues() {
        Map<String, Object> values = new HashMap<>();
        values.put("name", CONNECTION_NAME);
        values.put(KEY, VALUE);
        Connection connection = new Connection(values);
        assertThat(connection.getValues(), hasEntry(KEY, VALUE));
    }

    @Test
    public void shouldNotStoreNameInValues() throws Exception {
        Map<String, Object> values = new HashMap<>();
        values.put("name", CONNECTION_NAME);
        Connection connection = new Connection(values);
        assertThat(connection.getValues(), not(hasKey("name")));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRaiseExceptionWhenNameIsNull() {
        Map<String, Object> values = null;
        new Connection(values);
    }

    @Test
    public void shouldReturnValueFromKey() {
        Map<String, Object> values = new HashMap<>();
        values.put("name", CONNECTION_NAME);
        values.put(KEY, VALUE);
        Connection connection = new Connection(values);
        String value = connection.getValueForKey(KEY);
        assertThat(value, equalTo(VALUE));
    }

    @Test
    public void shouldReturnBooleanValueFromKey() {
        Map<String, Object> values = new HashMap<>();
        values.put("name", CONNECTION_NAME);
        values.put(KEY, true);
        Connection connection = new Connection(values);
        boolean value = connection.booleanForKey(KEY);
        assertThat(value, is(true));
    }

    @Test
    public void shouldReturnDefaultBooleanValueFromKey() {
        Map<String, Object> values = new HashMap<>();
        values.put("name", CONNECTION_NAME);
        Connection connection = new Connection(values);
        boolean value = connection.booleanForKey(KEY);
        assertThat(value, is(false));
    }

    @Test
    public void shouldRaiseExceptionWhenValueIsNotBoolean() {
        expectedException.expect(ClassCastException.class);
        Map<String, Object> values = new HashMap<>();
        values.put("name", CONNECTION_NAME);
        values.put(KEY, VALUE);
        Connection connection = new Connection(values);
        connection.booleanForKey(KEY);
    }

    @Test
    public void shouldReturnDomainNameInSet() throws Exception {
        Map<String, Object> values = new HashMap<>();
        values.put("name", CONNECTION_NAME);
        values.put("domain", "domain.com");
        Connection connection = new Connection(values);
        assertThat(connection.getDomainSet(), hasItem("domain.com"));
    }

    @Test
    public void shouldReturnAllDomainNamesAsSet() throws Exception {
        Map<String, Object> values = new HashMap<>();
        values.put("name", CONNECTION_NAME);
        values.put("domain", "domain.com");
        values.put("domain_aliases", Arrays.asList("domain2.com", "domain3.com"));
        Connection connection = new Connection(values);
        assertThat(connection.getDomainSet(), hasItems("domain.com", "domain2.com", "domain3.com"));
    }

    @Test
    public void shouldReturnEmptySetWithNoDomainName() throws Exception {
        Map<String, Object> values = new HashMap<>();
        values.put("name", CONNECTION_NAME);
        Connection connection = new Connection(values);
        assertThat(connection.getDomainSet().isEmpty(), is(true));
    }
}

package com.auth0.net;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class DeviceCredentialsFilterTest {

    private DeviceCredentialsFilter filter;

    @Before
    public void setUp() throws Exception {
        filter = new DeviceCredentialsFilter();
    }

    @Test
    public void shouldFilterByClientId() throws Exception {
        DeviceCredentialsFilter instance = filter.withClientId("1234567890");

        assertThat(filter, is(instance));
        assertThat(filter.getAsMap(), is(notNullValue()));
        assertThat(filter.getAsMap(), Matchers.hasEntry("client_id", (Object) "1234567890"));
    }

    @Test
    public void shouldFilterByType() throws Exception {
        DeviceCredentialsFilter instance = filter.withType("public_key");

        assertThat(filter, is(instance));
        assertThat(filter.getAsMap(), is(notNullValue()));
        assertThat(filter.getAsMap(), Matchers.hasEntry("type", (Object) "public_key"));
    }

    @Test
    public void shouldFilterByUserId() throws Exception {
        DeviceCredentialsFilter instance = filter.withUserId("1234567890");

        assertThat(filter, is(instance));
        assertThat(filter.getAsMap(), is(notNullValue()));
        assertThat(filter.getAsMap(), Matchers.hasEntry("user_id", (Object) "1234567890"));
    }

    @Test
    public void shouldFilterWithFields() throws Exception {
        DeviceCredentialsFilter instance = filter.withFields("a,b,c", true);

        assertThat(filter, is(instance));
        assertThat(filter.getAsMap(), is(notNullValue()));
        assertThat(filter.getAsMap(), Matchers.hasEntry("fields", (Object) "a,b,c"));
        assertThat(filter.getAsMap(), Matchers.hasEntry("include_fields", (Object) true));
    }

    @Test
    public void shouldFilterWithoutFields() throws Exception {
        DeviceCredentialsFilter instance = filter.withFields("a,b,c", false);

        assertThat(filter, is(instance));
        assertThat(filter.getAsMap(), is(notNullValue()));
        assertThat(filter.getAsMap(), Matchers.hasEntry("fields", (Object) "a,b,c"));
        assertThat(filter.getAsMap(), Matchers.hasEntry("include_fields", (Object) false));
    }
}
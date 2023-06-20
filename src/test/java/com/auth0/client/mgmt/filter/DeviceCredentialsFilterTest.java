package com.auth0.client.mgmt.filter;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class DeviceCredentialsFilterTest {

    private DeviceCredentialsFilter filter;

    @BeforeEach
    public void setUp() {
        filter = new DeviceCredentialsFilter();
    }

    @Test
    public void shouldFilterByClientId() {
        DeviceCredentialsFilter instance = filter.withClientId("1234567890");

        assertThat(filter, is(instance));
        assertThat(filter.getAsMap(), is(notNullValue()));
        assertThat(filter.getAsMap(), Matchers.hasEntry("client_id", "1234567890"));
    }

    @Test
    public void shouldFilterByType() {
        DeviceCredentialsFilter instance = filter.withType("public_key");

        assertThat(filter, is(instance));
        assertThat(filter.getAsMap(), is(notNullValue()));
        assertThat(filter.getAsMap(), Matchers.hasEntry("type", "public_key"));
    }

    @Test
    public void shouldFilterByUserId() {
        DeviceCredentialsFilter instance = filter.withUserId("1234567890");

        assertThat(filter, is(instance));
        assertThat(filter.getAsMap(), is(notNullValue()));
        assertThat(filter.getAsMap(), Matchers.hasEntry("user_id", "1234567890"));
    }

    @Test
    public void shouldFilterWithFields() {
        DeviceCredentialsFilter instance = filter.withFields("a,b,c", true);

        assertThat(filter, is(instance));
        assertThat(filter.getAsMap(), is(notNullValue()));
        assertThat(filter.getAsMap(), Matchers.hasEntry("fields", "a,b,c"));
        assertThat(filter.getAsMap(), Matchers.hasEntry("include_fields", true));
    }

    @Test
    public void shouldFilterWithoutFields() {
        DeviceCredentialsFilter instance = filter.withFields("a,b,c", false);

        assertThat(filter, is(instance));
        assertThat(filter.getAsMap(), is(notNullValue()));
        assertThat(filter.getAsMap(), Matchers.hasEntry("fields", "a,b,c"));
        assertThat(filter.getAsMap(), Matchers.hasEntry("include_fields", false));
    }

    @Test
    public void shouldFilterWithPage() {
        DeviceCredentialsFilter instance = filter.withPage(1, 10);

        assertThat(filter, is(instance));
        assertThat(filter.getAsMap(), is(notNullValue()));
        assertThat(filter.getAsMap(), Matchers.hasEntry("page", 1));
        assertThat(filter.getAsMap(), Matchers.hasEntry("per_page", 10));
    }

    @Test
    public void shouldFilterWithTotals() {
        DeviceCredentialsFilter instance = filter.withTotals(true);

        assertThat(filter, is(instance));
        assertThat(filter.getAsMap(), is(notNullValue()));
        assertThat(filter.getAsMap(), Matchers.hasEntry("include_totals", true));
    }
}

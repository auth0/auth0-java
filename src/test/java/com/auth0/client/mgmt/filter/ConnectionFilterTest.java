package com.auth0.client.mgmt.filter;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class ConnectionFilterTest {

    private ConnectionFilter filter;

    @Before
    public void setUp() {
        filter = new ConnectionFilter();
    }

    @Test
    public void shouldFilterByStrategy() {
        ConnectionFilter instance = filter.withStrategy("auth0");

        assertThat(filter, is(instance));
        assertThat(filter.getAsMap(), is(notNullValue()));
        assertThat(filter.getAsMap(), Matchers.hasEntry("strategy", "auth0"));
    }

    @Test
    public void shouldFilterByName() {
        ConnectionFilter instance = filter.withName("my-connection");

        assertThat(filter, is(instance));
        assertThat(filter.getAsMap(), is(notNullValue()));
        assertThat(filter.getAsMap(), Matchers.hasEntry("name", "my-connection"));
    }

    @Test
    public void shouldFilterByPage() {
        ConnectionFilter instance = filter.withPage(5, 10);

        assertThat(filter, is(instance));
        assertThat(filter.getAsMap(), is(notNullValue()));
        assertThat(filter.getAsMap(), Matchers.hasEntry("per_page", 10));
        assertThat(filter.getAsMap(), Matchers.hasEntry("page", 5));
    }

    @Test
    public void shouldFilterWithFields() {
        ConnectionFilter instance = filter.withFields("a,b,c", true);

        assertThat(filter, is(instance));
        assertThat(filter.getAsMap(), is(notNullValue()));
        assertThat(filter.getAsMap(), Matchers.hasEntry("fields", "a,b,c"));
        assertThat(filter.getAsMap(), Matchers.hasEntry("include_fields", true));
    }

    @Test
    public void shouldIncludeTotals() {
        ConnectionFilter instance = filter.withTotals(true);

        assertThat(filter, is(instance));
        assertThat(filter.getAsMap(), is(notNullValue()));
        assertThat(filter.getAsMap(), Matchers.hasEntry("include_totals", true));
    }

    @Test
    public void shouldFilterWithoutFields() {
        ConnectionFilter instance = filter.withFields("a,b,c", false);

        assertThat(filter, is(instance));
        assertThat(filter.getAsMap(), is(notNullValue()));
        assertThat(filter.getAsMap(), Matchers.hasEntry("fields", "a,b,c"));
        assertThat(filter.getAsMap(), Matchers.hasEntry("include_fields", false));
    }
}

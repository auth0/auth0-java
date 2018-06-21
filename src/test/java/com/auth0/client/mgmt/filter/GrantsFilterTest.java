package com.auth0.client.mgmt.filter;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class GrantsFilterTest {

    private GrantsFilter filter;

    @Before
    public void setUp() throws Exception {
        filter = new GrantsFilter();
    }

    @Test
    public void shouldFilterByAudience() throws Exception {
        GrantsFilter instance = filter.withAudience("https://myapi.auth0.com");

        assertThat(filter, is(instance));
        assertThat(filter.getAsMap(), is(notNullValue()));
        assertThat(filter.getAsMap(), Matchers.hasEntry("audience", (Object) "https://myapi.auth0.com"));
    }

    @Test
    public void shouldFilterByClientId() throws Exception {
        GrantsFilter instance = filter.withClientId("n3roinr32i23iron23nr");

        assertThat(filter, is(instance));
        assertThat(filter.getAsMap(), is(notNullValue()));
        assertThat(filter.getAsMap(), Matchers.hasEntry("client_id", (Object) "n3roinr32i23iron23nr"));
    }

    @Test
    public void shouldFilterByPage() throws Exception {
        GrantsFilter instance = filter.withPage(5, 10);

        assertThat(filter, is(instance));
        assertThat(filter.getAsMap(), is(notNullValue()));
        assertThat(filter.getAsMap(), Matchers.hasEntry("per_page", (Object) 10));
        assertThat(filter.getAsMap(), Matchers.hasEntry("page", (Object) 5));
    }

    @Test
    public void shouldIncludeTotals() throws Exception {
        GrantsFilter instance = filter.withTotals(true);

        assertThat(filter, is(instance));
        assertThat(filter.getAsMap(), is(notNullValue()));
        assertThat(filter.getAsMap(), Matchers.hasEntry("include_totals", (Object) true));
    }

}
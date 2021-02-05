package com.auth0.client.mgmt.filter;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class ClientGrantsFilterTest {

    private ClientGrantsFilter filter;

    @Before
    public void setUp() {
        filter = new ClientGrantsFilter();
    }

    @Test
    public void shouldFilterByAudience() {
        ClientGrantsFilter instance = filter.withAudience("https://myapi.auth0.com");

        assertThat(filter, is(instance));
        assertThat(filter.getAsMap(), is(notNullValue()));
        assertThat(filter.getAsMap(), Matchers.hasEntry("audience", "https://myapi.auth0.com"));
    }

    @Test
    public void shouldFilterByClientId() {
        ClientGrantsFilter instance = filter.withClientId("n3roinr32i23iron23nr");

        assertThat(filter, is(instance));
        assertThat(filter.getAsMap(), is(notNullValue()));
        assertThat(filter.getAsMap(), Matchers.hasEntry("client_id", "n3roinr32i23iron23nr"));
    }

    @Test
    public void shouldFilterByPage() {
        ClientGrantsFilter instance = filter.withPage(5, 10);

        assertThat(filter, is(instance));
        assertThat(filter.getAsMap(), is(notNullValue()));
        assertThat(filter.getAsMap(), Matchers.hasEntry("per_page", 10));
        assertThat(filter.getAsMap(), Matchers.hasEntry("page", 5));
    }

    @Test
    public void shouldIncludeTotals() {
        ClientGrantsFilter instance = filter.withTotals(true);

        assertThat(filter, is(instance));
        assertThat(filter.getAsMap(), is(notNullValue()));
        assertThat(filter.getAsMap(), Matchers.hasEntry("include_totals", true));
    }

}

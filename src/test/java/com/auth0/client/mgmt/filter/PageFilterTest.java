package com.auth0.client.mgmt.filter;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

public class PageFilterTest {

    private PageFilter filter;

    @Before
    public void setUp() throws Exception {
        filter = new PageFilter();
    }

    @Test
    public void shouldFilterByPage() throws Exception {
        PageFilter instance = filter.withPage(5, 10);

        assertThat(filter, is(instance));
        assertThat(filter.getAsMap(), is(notNullValue()));
        assertThat(filter.getAsMap(), Matchers.hasEntry("per_page", (Object) 10));
        assertThat(filter.getAsMap(), Matchers.hasEntry("page", (Object) 5));
    }

    @Test
    public void shouldIncludeTotals() throws Exception {
        PageFilter instance = filter.withTotals(true);

        assertThat(filter, is(instance));
        assertThat(filter.getAsMap(), is(notNullValue()));
        assertThat(filter.getAsMap(), Matchers.hasEntry("include_totals", (Object) true));
    }

}

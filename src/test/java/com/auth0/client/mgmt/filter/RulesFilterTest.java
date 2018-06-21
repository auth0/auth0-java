package com.auth0.client.mgmt.filter;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class RulesFilterTest {

    private RulesFilter filter;

    @Before
    public void setUp() throws Exception {
        filter = new RulesFilter();
    }

    @Test
    public void shouldFilterWithEnabled() throws Exception {
        RulesFilter instance = filter.withEnabled(true);

        assertThat(filter, is(instance));
        assertThat(filter.getAsMap(), is(notNullValue()));
        assertThat(filter.getAsMap(), Matchers.hasEntry("enabled", (Object) true));
    }

    @Test
    public void shouldFilterWithFields() throws Exception {
        RulesFilter instance = filter.withFields("a,b,c", true);

        assertThat(filter, is(instance));
        assertThat(filter.getAsMap(), is(notNullValue()));
        assertThat(filter.getAsMap(), Matchers.hasEntry("fields", (Object) "a,b,c"));
        assertThat(filter.getAsMap(), Matchers.hasEntry("include_fields", (Object) true));
    }

    @Test
    public void shouldFilterWithoutFields() throws Exception {
        RulesFilter instance = filter.withFields("a,b,c", false);

        assertThat(filter, is(instance));
        assertThat(filter.getAsMap(), is(notNullValue()));
        assertThat(filter.getAsMap(), Matchers.hasEntry("fields", (Object) "a,b,c"));
        assertThat(filter.getAsMap(), Matchers.hasEntry("include_fields", (Object) false));
    }

    @Test
    public void shouldIncludeTotals() throws Exception {
        RulesFilter instance = filter.withTotals(true);

        assertThat(filter, is(instance));
        assertThat(filter.getAsMap(), is(notNullValue()));
        assertThat(filter.getAsMap(), Matchers.hasEntry("include_totals", (Object) true));
    }

    @Test
    public void shouldFilterByPage() throws Exception {
        RulesFilter instance = filter.withPage(15, 50);

        assertThat(filter, is(instance));
        assertThat(filter.getAsMap(), is(notNullValue()));
        assertThat(filter.getAsMap(), Matchers.hasEntry("page", (Object) 15));
        assertThat(filter.getAsMap(), Matchers.hasEntry("per_page", (Object) 50));
    }
}
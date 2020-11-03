package com.auth0.client.mgmt.filter;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

public class RoleFilterTest {

    private RolesFilter filter;

    @Before
    public void setUp() throws Exception {
        filter = new RolesFilter();
    }

    @Test
    public void shouldFilterByName() throws Exception {
        RolesFilter instance = filter.withName("roleName");

        assertThat(filter, is(instance));
        assertThat(filter.getAsMap(), is(notNullValue()));
        assertThat(filter.getAsMap(), Matchers.hasEntry("name_filter", "roleName"));
    }

    @Test
    public void shouldFilterByPage() throws Exception {
        RolesFilter instance = filter.withPage(5, 10);

        assertThat(filter, is(instance));
        assertThat(filter.getAsMap(), is(notNullValue()));
        assertThat(filter.getAsMap(), Matchers.hasEntry("per_page", 10));
        assertThat(filter.getAsMap(), Matchers.hasEntry("page", 5));
    }

    @Test
    public void shouldIncludeTotals() throws Exception {
        RolesFilter instance = filter.withTotals(true);

        assertThat(filter, is(instance));
        assertThat(filter.getAsMap(), is(notNullValue()));
        assertThat(filter.getAsMap(), Matchers.hasEntry("include_totals", true));
    }

}

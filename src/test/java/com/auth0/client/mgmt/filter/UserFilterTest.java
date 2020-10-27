package com.auth0.client.mgmt.filter;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class UserFilterTest {

    private UserFilter filter;

    @Before
    public void setUp() throws Exception {
        filter = new UserFilter();
    }

    @Test
    public void shouldNotSetADefaultSearchEngineValue() throws Exception {
        assertThat(filter.getAsMap(), is(notNullValue()));
        assertThat(filter.getAsMap(), not(Matchers.hasKey("search_engine")));
    }

    @Test
    public void shouldSetSearchEngine() throws Exception {
        UserFilter instance = filter.withSearchEngine("v3");

        assertThat(filter, is(instance));
        assertThat(filter.getAsMap(), is(notNullValue()));
        assertThat(filter.getAsMap(), Matchers.hasEntry("search_engine", (Object) "v3"));
    }

    @Test
    public void shouldIncludeTotals() throws Exception {
        UserFilter instance = filter.withTotals(true);

        assertThat(filter, is(instance));
        assertThat(filter.getAsMap(), is(notNullValue()));
        assertThat(filter.getAsMap(), Matchers.hasEntry("include_totals", (Object) true));
    }

    @Test
    public void shouldFilterByQuery() throws Exception {
        UserFilter instance = filter.withQuery("id=log123");

        assertThat(filter, is(instance));
        assertThat(filter.getAsMap(), is(notNullValue()));
        assertThat(filter.getAsMap(), Matchers.hasEntry("q", (Object) "id%3Dlog123"));
    }

    @Test
    public void shouldSort() throws Exception {
        UserFilter instance = filter.withSort("date:-1");

        assertThat(filter, is(instance));
        assertThat(filter.getAsMap(), is(notNullValue()));
        assertThat(filter.getAsMap(), Matchers.hasEntry("sort", (Object) "date:-1"));
    }

    @Test
    public void shouldFilterByPage() throws Exception {
        UserFilter instance = filter.withPage(15, 50);

        assertThat(filter, is(instance));
        assertThat(filter.getAsMap(), is(notNullValue()));
        assertThat(filter.getAsMap(), Matchers.hasEntry("page", (Object) 15));
        assertThat(filter.getAsMap(), Matchers.hasEntry("per_page", (Object) 50));
    }

    @Test
    public void shouldFilterByWithFields() throws Exception {
        UserFilter instance = filter.withFields("a,b,c", true);

        assertThat(filter, is(instance));
        assertThat(filter.getAsMap(), is(notNullValue()));
        assertThat(filter.getAsMap(), Matchers.hasEntry("fields", (Object) "a,b,c"));
        assertThat(filter.getAsMap(), Matchers.hasEntry("include_fields", (Object) true));
    }

    @Test
    public void shouldFilterWithoutFields() throws Exception {
        UserFilter instance = filter.withFields("a,b,c", false);

        assertThat(filter, is(instance));
        assertThat(filter.getAsMap(), is(notNullValue()));
        assertThat(filter.getAsMap(), Matchers.hasEntry("fields", (Object) "a,b,c"));
        assertThat(filter.getAsMap(), Matchers.hasEntry("include_fields", (Object) false));
    }
}
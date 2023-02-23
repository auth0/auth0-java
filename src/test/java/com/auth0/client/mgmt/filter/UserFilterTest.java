package com.auth0.client.mgmt.filter;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class UserFilterTest {

    private UserFilter filter;

    @Before
    public void setUp() {
        filter = new UserFilter();
    }

    @Test
    public void shouldNotSetADefaultSearchEngineValue() {
        assertThat(filter.getAsMap(), is(notNullValue()));
        assertThat(filter.getAsMap(), not(Matchers.hasKey("search_engine")));
    }

    @Test
    public void shouldSetSearchEngine() {
        UserFilter instance = filter.withSearchEngine("v3");

        assertThat(filter, is(instance));
        assertThat(filter.getAsMap(), is(notNullValue()));
        assertThat(filter.getAsMap(), Matchers.hasEntry("search_engine", "v3"));
    }

    @Test
    public void shouldIncludeTotals() {
        UserFilter instance = filter.withTotals(true);

        assertThat(filter, is(instance));
        assertThat(filter.getAsMap(), is(notNullValue()));
        assertThat(filter.getAsMap(), Matchers.hasEntry("include_totals", true));
    }

    @Test
    public void shouldFilterByQuery() {
        UserFilter instance = filter.withQuery("id=log123");

        assertThat(filter, is(instance));
        assertThat(filter.getAsMap(), is(notNullValue()));
        assertThat(filter.getAsMap(), Matchers.hasEntry("q", "id%3Dlog123"));
    }

    @Test
    public void shouldSort() {
        UserFilter instance = filter.withSort("date:-1");

        assertThat(filter, is(instance));
        assertThat(filter.getAsMap(), is(notNullValue()));
        assertThat(filter.getAsMap(), Matchers.hasEntry("sort", "date:-1"));
    }

    @Test
    public void shouldFilterByPage() {
        UserFilter instance = filter.withPage(15, 50);

        assertThat(filter, is(instance));
        assertThat(filter.getAsMap(), is(notNullValue()));
        assertThat(filter.getAsMap(), Matchers.hasEntry("page", 15));
        assertThat(filter.getAsMap(), Matchers.hasEntry("per_page", 50));
    }

    @Test
    public void shouldFilterByWithFields() {
        UserFilter instance = filter.withFields("a,b,c", true);

        assertThat(filter, is(instance));
        assertThat(filter.getAsMap(), is(notNullValue()));
        assertThat(filter.getAsMap(), Matchers.hasEntry("fields", "a,b,c"));
        assertThat(filter.getAsMap(), Matchers.hasEntry("include_fields", true));
    }

    @Test
    public void shouldFilterWithoutFields() {
        UserFilter instance = filter.withFields("a,b,c", false);

        assertThat(filter, is(instance));
        assertThat(filter.getAsMap(), is(notNullValue()));
        assertThat(filter.getAsMap(), Matchers.hasEntry("fields", "a,b,c"));
        assertThat(filter.getAsMap(), Matchers.hasEntry("include_fields", false));
    }

    @Test
    public void shouldFilterWithConnection() {
        UserFilter instance = filter.withConnection("test");

        assertThat(filter, is(instance));
        assertThat(filter.getAsMap(), is(notNullValue()));
        assertThat(filter.getAsMap(), Matchers.hasEntry("connection", "test"));
    }
}

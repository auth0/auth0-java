package com.auth0.client.mgmt.filter;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;

import static com.auth0.AssertsUtil.verifyThrows;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;

public class QueryFilterTest {

    private QueryFilter filter;

    @BeforeEach
    public void setUp() {
        filter = new QueryFilter();
    }

    @Test
    public void shouldIncludeTotals() {
        QueryFilter instance = filter.withTotals(true);

        assertThat(filter, is(instance));
        assertThat(filter.getAsMap(), is(notNullValue()));
        assertThat(filter.getAsMap(), Matchers.hasEntry("include_totals", true));
    }

    @Test
    public void shouldFilterByQuery() {
        QueryFilter instance = filter.withQuery("id=log123");

        assertThat(filter, is(instance));
        assertThat(filter.getAsMap(), is(notNullValue()));
        assertThat(filter.getAsMap(), Matchers.hasEntry("q", "id%3Dlog123"));
    }

    @Test
    public void shouldSort() {
        QueryFilter instance = filter.withSort("date:-1");

        assertThat(filter, is(instance));
        assertThat(filter.getAsMap(), is(notNullValue()));
        assertThat(filter.getAsMap(), Matchers.hasEntry("sort", "date:-1"));
    }

    @Test
    public void shouldFilterByPage() {
        QueryFilter instance = filter.withPage(15, 50);

        assertThat(filter, is(instance));
        assertThat(filter.getAsMap(), is(notNullValue()));
        assertThat(filter.getAsMap(), Matchers.hasEntry("page", 15));
        assertThat(filter.getAsMap(), Matchers.hasEntry("per_page", 50));
    }

    @Test
    public void shouldFilterByWithFields() {
        QueryFilter instance = filter.withFields("a,b,c", true);

        assertThat(filter, is(instance));
        assertThat(filter.getAsMap(), is(notNullValue()));
        assertThat(filter.getAsMap(), Matchers.hasEntry("fields", "a,b,c"));
        assertThat(filter.getAsMap(), Matchers.hasEntry("include_fields", true));
    }

    @Test
    public void shouldFilterWithoutFields() {
        QueryFilter instance = filter.withFields("a,b,c", false);

        assertThat(filter, is(instance));
        assertThat(filter.getAsMap(), is(notNullValue()));
        assertThat(filter.getAsMap(), Matchers.hasEntry("fields", "a,b,c"));
        assertThat(filter.getAsMap(), Matchers.hasEntry("include_fields", false));
    }

    @Test
    public void shouldThrowIllegalStateExceptionWhenUtf8IsNotSupported() throws Exception {
        String value = "my test value";
        QueryFilter filter = spy(new QueryFilter());
        doThrow(UnsupportedEncodingException.class).when(filter).urlEncode(value);

        IllegalStateException e = verifyThrows(IllegalStateException.class,
            () -> filter.withQuery(value),
            "UTF-8 encoding not supported by current Java platform implementation.");
        assertThat(e.getCause(), instanceOf(UnsupportedEncodingException.class));
    }

}

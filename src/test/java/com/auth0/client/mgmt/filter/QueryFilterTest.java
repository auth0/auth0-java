package com.auth0.client.mgmt.filter;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.isA;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;

import java.io.UnsupportedEncodingException;

import org.junit.Rule;
import org.junit.rules.ExpectedException;

public class QueryFilterTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private QueryFilter filter;

    @Before
    public void setUp() throws Exception {
        filter = new QueryFilter();
    }

    @Test
    public void shouldIncludeTotals() throws Exception {
        QueryFilter instance = filter.withTotals(true);

        assertThat(filter, is(instance));
        assertThat(filter.getAsMap(), is(notNullValue()));
        assertThat(filter.getAsMap(), Matchers.hasEntry("include_totals", (Object) true));
    }

    @Test
    public void shouldFilterByQuery() throws Exception {
        QueryFilter instance = filter.withQuery("id=log123");

        assertThat(filter, is(instance));
        assertThat(filter.getAsMap(), is(notNullValue()));
        assertThat(filter.getAsMap(), Matchers.hasEntry("q", (Object) "id%3Dlog123"));
    }

    @Test
    public void shouldSort() throws Exception {
        QueryFilter instance = filter.withSort("date:-1");

        assertThat(filter, is(instance));
        assertThat(filter.getAsMap(), is(notNullValue()));
        assertThat(filter.getAsMap(), Matchers.hasEntry("sort", (Object) "date:-1"));
    }

    @Test
    public void shouldFilterByPage() throws Exception {
        QueryFilter instance = filter.withPage(15, 50);

        assertThat(filter, is(instance));
        assertThat(filter.getAsMap(), is(notNullValue()));
        assertThat(filter.getAsMap(), Matchers.hasEntry("page", (Object) 15));
        assertThat(filter.getAsMap(), Matchers.hasEntry("per_page", (Object) 50));
    }

    @Test
    public void shouldFilterByWithFields() throws Exception {
        QueryFilter instance = filter.withFields("a,b,c", true);

        assertThat(filter, is(instance));
        assertThat(filter.getAsMap(), is(notNullValue()));
        assertThat(filter.getAsMap(), Matchers.hasEntry("fields", (Object) "a,b,c"));
        assertThat(filter.getAsMap(), Matchers.hasEntry("include_fields", (Object) true));
    }

    @Test
    public void shouldFilterWithoutFields() throws Exception {
        QueryFilter instance = filter.withFields("a,b,c", false);

        assertThat(filter, is(instance));
        assertThat(filter.getAsMap(), is(notNullValue()));
        assertThat(filter.getAsMap(), Matchers.hasEntry("fields", (Object) "a,b,c"));
        assertThat(filter.getAsMap(), Matchers.hasEntry("include_fields", (Object) false));
    }

    @Test
    public void shouldThrowIllegalStateExceptionWhenUtf8IsNotSupported() throws Exception {
        exception.expect(IllegalStateException.class);
        exception.expectMessage("UTF-8 encoding not supported by current Java platform implementation.");
        exception.expectCause(isA(UnsupportedEncodingException.class));
        String value = "my test value";
        QueryFilter filter = spy(new QueryFilter());
        doThrow(UnsupportedEncodingException.class).when(filter).urlEncode(value);
        filter.withQuery(value);
    }

}

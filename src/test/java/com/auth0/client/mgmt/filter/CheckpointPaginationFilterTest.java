package com.auth0.client.mgmt.filter;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class CheckpointPaginationFilterTest {

    private CheckpointPaginationFilter filter;

    @BeforeEach
    public void setUp() {
        filter = new CheckpointPaginationFilter();
    }

    @Test
    public void shouldIncludeTotals() {
        CheckpointPaginationFilter instance = filter.withTotals(true);

        assertThat(filter, is(instance));
        assertThat(filter.getAsMap(), is(notNullValue()));
        assertThat(filter.getAsMap(), Matchers.hasEntry("include_totals", true));
    }

    @Test
    public void shouldIncludeCheckpointParams() {
        CheckpointPaginationFilter instance = filter.withFrom("abc123").withTake(2);

        assertThat(filter.getAsMap(), is(notNullValue()));
        assertThat(filter.getAsMap(), Matchers.hasEntry("from", "abc123"));
        assertThat(filter.getAsMap(), Matchers.hasEntry("take", 2));
    }
}

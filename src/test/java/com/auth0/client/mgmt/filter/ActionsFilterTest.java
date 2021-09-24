package com.auth0.client.mgmt.filter;

import org.junit.Test;

import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.aMapWithSize;
import static org.hamcrest.Matchers.hasEntry;

public class ActionsFilterTest {

    @Test
    public void shouldApplyFilters() {
        ActionsFilter filter = new ActionsFilter();
        assertThat(filter.getAsMap(), is(aMapWithSize(0)));

        filter
            .withActionName("name")
            .withDeployed(true)
            .withInstalled(false)
            .withPage(1, 2)
            .withTriggerId("trigger");

        Map<String, Object> params = filter.getAsMap();
        assertThat(params, is(aMapWithSize(6)));
        assertThat(params, hasEntry("actionName", "name"));
        assertThat(params, hasEntry("deployed", true));
        assertThat(params, hasEntry("installed", false));
        assertThat(params, hasEntry("per_page", 2));
        assertThat(params, hasEntry("page", 1));
        assertThat(params, hasEntry("triggerId", "trigger"));
    }
}

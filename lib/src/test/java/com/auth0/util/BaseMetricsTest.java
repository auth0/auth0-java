package com.auth0.util;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class BaseMetricsTest {

    private BaseMetrics metrics;

    @Before
    public void setUp() throws Exception {
        metrics = new BaseMetrics();
    }

    @Test
    public void shouldReturnBase64() throws Exception {
        metrics.usingLibrary("auth0-java", "1.0.0");
        assertThat(metrics.getValue(), is(notNullValue()));
    }

    @Test
    public void shouldReturnNullWhenNoInfoIsProvided() throws Exception {
        assertThat(metrics.getValue(), is(nullValue()));
    }
}
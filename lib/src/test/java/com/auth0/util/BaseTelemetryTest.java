package com.auth0.util;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class BaseTelemetryTest {

    private BaseTelemetry telemetry;

    @Before
    public void setUp() throws Exception {
        telemetry = new BaseTelemetry();
    }

    @Test
    public void shouldReturnBase64() throws Exception {
        telemetry.usingLibrary("auth0-java", "1.0.0");
        assertThat(telemetry.getValue(), is(notNullValue()));
    }

    @Test
    public void shouldReturnNullWhenNoInfoIsProvided() throws Exception {
        assertThat(telemetry.getValue(), is(nullValue()));
    }
}
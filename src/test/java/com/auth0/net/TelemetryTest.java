package com.auth0.net;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class TelemetryTest {

    private Telemetry telemetry;

    @Before
    public void setUp() throws Exception {
        telemetry = new Telemetry("auth0-java", "1.0.0");
    }

    @Test
    public void shouldReturnBase64() throws Exception {
        assertThat(telemetry.getValue(), is(notNullValue()));
        assertThat(telemetry.getValue(), is("eyJuYW1lIjoiYXV0aDAtamF2YSIsInZlcnNpb24iOiIxLjAuMCJ9"));
    }

    @Test
    public void shouldReturnNullWhenNoInfoIsProvided() throws Exception {
        telemetry = new Telemetry(null, null);
        assertThat(telemetry.getValue(), is(nullValue()));
    }

    @Test
    public void shouldGetName() throws Exception {
        assertThat(telemetry.getName(), is("auth0-java"));
    }

    @Test
    public void shouldGetVersion() throws Exception {
        assertThat(telemetry.getVersion(), is("1.0.0"));
    }
}
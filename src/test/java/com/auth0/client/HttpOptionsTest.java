package com.auth0.client;

import org.junit.Test;
import static org.junit.Assert.assertThrows;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class HttpOptionsTest {

    @Test
    public void setsOptions() {
        HttpOptions opts = new HttpOptions();
        opts.setLoggingOptions(new LoggingOptions(LoggingOptions.LogLevel.NONE));
        opts.setConnectTimeout(20);
        opts.setReadTimeout(30);
        opts.setMaxRequests(5);
        opts.setMaxRequestsPerHost(3);
        opts.setManagementAPIMaxRetries(5);

        assertThat(opts.getLoggingOptions().getLogLevel(), is(LoggingOptions.LogLevel.NONE));
        assertThat(opts.getConnectTimeout(), is(20));
        assertThat(opts.getReadTimeout(), is(30));
        assertThat(opts.getMaxRequests(), is(5));
        assertThat(opts.getManagementAPIMaxRetries(), is(5));
        assertThat(opts.getMaxRequestsPerHost(), is(3));
    }

    @Test
    public void connectTimeoutMustNotBeNegative() {
        HttpOptions opts = new HttpOptions();
        opts.setConnectTimeout(-1);
        assertThat(opts.getConnectTimeout(), is(0));
    }

    @Test
    public void readTimeoutMustNotBeNegative() {
        HttpOptions opts = new HttpOptions();
        opts.setReadTimeout(-1);
        assertThat(opts.getReadTimeout(), is(0));
    }

    @Test
    public void apiMaxRetriesNotLessThan0() {
        HttpOptions opts = new HttpOptions();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> opts.setManagementAPIMaxRetries(-1));
        assertThat(exception.getMessage(), is("Retries must be between zero and ten."));
    }

    @Test
    public void apiMaxRetriesNotGreaterThan10() {
        HttpOptions opts = new HttpOptions();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> opts.setManagementAPIMaxRetries(11));
        assertThat(exception.getMessage(), is("Retries must be between zero and ten."));
    }

    @Test
    public void maxRequestsMustBePositive() {
        HttpOptions opts = new HttpOptions();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> opts.setMaxRequests(0));
        assertThat(exception.getMessage(), is("maxRequests must be one or greater."));
    }

    @Test
    public void maxRequestsPerHostMustBePositive() {
        HttpOptions opts = new HttpOptions();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> opts.setMaxRequestsPerHost(0));
        assertThat(exception.getMessage(), is("maxRequestsPerHost must be one or greater."));
    }

    @Test
    public void usesDefaults() {
        HttpOptions opts = new HttpOptions();
        assertThat(opts.getReadTimeout(), is(10));
        assertThat(opts.getConnectTimeout(), is(10));
        assertThat(opts.getMaxRequests(), is(64));
        assertThat(opts.getMaxRequestsPerHost(), is(5));
        assertThat(opts.getManagementAPIMaxRetries(), is(3));
    }
}

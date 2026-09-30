package com.auth0.net.client;

import static com.auth0.AssertsUtil.verifyThrows;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.Test;

public class DefaultHttpClientTest {

    @Test
    public void shouldReuseBaseClientTransportConfigAndIgnoreBuilderTransportSettings() {
        OkHttpClient baseClient =
                new OkHttpClient.Builder().readTimeout(42, TimeUnit.SECONDS).build();
        baseClient.dispatcher().setMaxRequests(7);

        DefaultHttpClient httpClient = DefaultHttpClient.newBuilder()
                .withClient(baseClient)
                // Transport settings below must be ignored when a base client is supplied.
                .withReadTimeout(99)
                .withMaxRequests(99)
                .build();

        OkHttpClient built = httpClient.getOkClient();
        assertThat(built.readTimeoutMillis(), is(42_000));
        assertThat(built.dispatcher().getMaxRequests(), is(7));
    }

    @Test
    public void shouldApplyBuilderTransportSettingsWhenNoBaseClient() {
        DefaultHttpClient httpClient = DefaultHttpClient.newBuilder()
                .withReadTimeout(42)
                .withMaxRequests(7)
                .build();

        OkHttpClient built = httpClient.getOkClient();
        assertThat(built.readTimeoutMillis(), is(42_000));
        assertThat(built.dispatcher().getMaxRequests(), is(7));
    }

    @Test
    public void shouldLayerSdkInterceptorsOnTopOfBaseClientInterceptors() {
        OkHttpClient baseClient = new OkHttpClient.Builder()
                .addInterceptor(chain -> chain.proceed(chain.request()))
                .build();
        int baseInterceptorCount = baseClient.interceptors().size();

        DefaultHttpClient httpClient =
                DefaultHttpClient.newBuilder().withClient(baseClient).build();

        // The base client's interceptor plus the three SDK interceptors (logging, telemetry, rate-limit).
        assertThat(httpClient.getOkClient().interceptors().size(), is(baseInterceptorCount + 3));
        // The original base client is left untouched.
        assertThat(
                baseClient.interceptors().size(),
                is(not(httpClient.getOkClient().interceptors().size())));
    }

    @Test
    public void shouldThrowWhenBaseClientIsNull() {
        verifyThrows(
                IllegalArgumentException.class,
                () -> DefaultHttpClient.newBuilder().withClient(null),
                "'base client' cannot be null!");
    }
}

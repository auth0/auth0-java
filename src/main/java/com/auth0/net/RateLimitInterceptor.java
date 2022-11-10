package com.auth0.net;

import com.auth0.client.HttpOptions;
import net.jodah.failsafe.Failsafe;
import net.jodah.failsafe.RetryPolicy;
import net.jodah.failsafe.event.ExecutionAttemptedEvent;
import net.jodah.failsafe.function.CheckedConsumer;
import okhttp3.Interceptor;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.time.temporal.ChronoUnit;

/**
 * An OkHttp {@linkplain Interceptor} responsible for retrying rate-limit errors (429) using a configurable maximum
 * number of retries, and an exponential backoff on retry attempts.
 * <p>
 * See {@link com.auth0.client.HttpOptions#setManagementAPIMaxRetries(int)} and {@link com.auth0.client.mgmt.ManagementAPI#ManagementAPI(String, String, HttpOptions)}
 * </p>
 * <p>
 * <strong>Note: This class is not intended for general use or extension, and may change at any time.</strong>
 * </p>
 */
public class RateLimitInterceptor implements Interceptor {

    private final int maxRetries;
    private final CheckedConsumer<? extends ExecutionAttemptedEvent<Response>> retryListener;

    static final Long INITIAL_INTERVAL = 100L;
    static final Long MAX_INTERVAL = 1000L;
    static final Double JITTER = 0.2D;

    /**
     * Constructs a new instance with the maximum number of allowed retries.
     * @param maxRetries the maximum number of consecutive retries to attempt.
     */
    public RateLimitInterceptor(int maxRetries) {
        this(maxRetries, null);
    }

    /**
     * Visible for testing purposes only.
     * @param maxRetries the maximum number of consecutive retries to attempt.
     * @param retryListener a listener to call prior to a retry attempt.
     */
    RateLimitInterceptor(int maxRetries, CheckedConsumer<? extends ExecutionAttemptedEvent<Response>> retryListener) {
        this.maxRetries = maxRetries;
        this.retryListener = retryListener;
    }

    /**
     * @return the configured number of maximum retries.
     */
    public int getMaxRetries() {
        return maxRetries;
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        if (maxRetries == 0) {
            return chain.proceed(chain.request());
        }

        RetryPolicy<Response> retryPolicy = new RetryPolicy<Response>()
            .withMaxRetries(maxRetries)
            .withBackoff(INITIAL_INTERVAL, MAX_INTERVAL, ChronoUnit.MILLIS)
            .withJitter(JITTER)
            .handleResultIf(response -> {
                if (response.code() == 429) {
                    response.close();
                    return true;
                }
                return false;
            });

        // For testing purposes only, allow test to hook into retry listener to enable verification of retry backoff
        if (retryListener != null) {
            retryPolicy.onRetry(retryListener);
        }

        return Failsafe.with(retryPolicy).get(() -> chain.proceed(chain.request()));
    }
}

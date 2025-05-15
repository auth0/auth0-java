package com.auth0.exception;

import com.auth0.net.TokenQuotaBucket;

import java.util.Map;

/**
 * Represents a server error when a rate limit has been exceeded.
 * <p>
 * Getters for {@code limit, remaining} and {@code reset} corresponds to {@code X-RateLimit-Limit, X-RateLimit-Remaining} and {@code X-RateLimit-Reset} HTTP headers.
 * If the value of any headers is missing, then a default value -1 will assigned.
 * <p>
 * To learn more about rate limits, visit <a href="https://auth0.com/docs/policies/rate-limits">https://auth0.com/docs/policies/rate-limits</a>
 */
public class RateLimitException extends APIException {

    private final long limit;
    private final long remaining;
    private final long reset;

    private TokenQuotaBucket clientQuotaLimit;
    private TokenQuotaBucket organizationQuotaLimit;
    private long retryAfter;

    private static final int STATUS_CODE_TOO_MANY_REQUEST = 429;

    public RateLimitException(long limit, long remaining, long reset, Map<String, Object> values) {
        super(values, STATUS_CODE_TOO_MANY_REQUEST);
        this.limit = limit;
        this.remaining = remaining;
        this.reset = reset;
    }

    public RateLimitException(long limit, long remaining, long reset) {
        super("Rate limit reached", STATUS_CODE_TOO_MANY_REQUEST, null);
        this.limit = limit;
        this.remaining = remaining;
        this.reset = reset;
    }

    /**
     * Getter for the maximum number of requests available in the current time frame.
     * @return The maximum number of requests or -1 if missing.
     */
    public long getLimit() {
        return limit;
    }

    /**
     * Getter for the number of remaining requests in the current time frame.
     * @return Number of remaining requests or -1 if missing.
     */
    public long getRemaining() {
        return remaining;
    }

    /**
     * Getter for the UNIX timestamp of the expected time when the rate limit will reset.
     * @return The UNIX timestamp or -1 if missing.
     */
    public long getReset() {
        return reset;
    }

    /**
     * Getter for the client quota limit.
     * @return The client quota limit or null if missing.
     */
    public TokenQuotaBucket getClientQuotaLimit() {
        return clientQuotaLimit;
    }

    /**
     * Getter for the organization quota limit.
     * @return The organization quota limit or null if missing.
     */
    public TokenQuotaBucket getOrganizationQuotaLimit() {
        return organizationQuotaLimit;
    }

    /**
     * Getter for the retry after time in seconds.
     * @return The retry after time in seconds or -1 if missing.
     */
    public long getRetryAfter() {
        return retryAfter;
    }

    /**
     * Builder class for creating instances of RateLimitException.
     */
    public static class Builder {
        private long limit;
        private long remaining;
        private long reset;
        private TokenQuotaBucket clientQuotaLimit;
        private TokenQuotaBucket organizationQuotaLimit;
        private long retryAfter;
        private Map<String, Object> values;

        /**
         * Constructor for the Builder.
         * @param limit The maximum number of requests available in the current time frame.
         * @param remaining The number of remaining requests in the current time frame.
         * @param reset The UNIX timestamp of the expected time when the rate limit will reset.
         */
        public Builder(long limit, long remaining, long reset) {
            this.limit = limit;
            this.remaining = remaining;
            this.reset = reset;
        }

        /**
         * Constructor for the Builder.
         * @param limit The maximum number of requests available in the current time frame.
         * @param remaining The number of remaining requests in the current time frame.
         * @param reset The UNIX timestamp of the expected time when the rate limit will reset.
         * @param values The values map.
         */
        public Builder(long limit, long remaining, long reset, Map<String, Object> values) {
            this.limit = limit;
            this.remaining = remaining;
            this.reset = reset;
            this.values = values;
        }

        /**
         * Sets the client quota limit.
         * @param clientQuotaLimit The client quota limit.
         * @return The Builder instance.
         */
        public Builder clientQuotaLimit(TokenQuotaBucket clientQuotaLimit) {
            this.clientQuotaLimit = clientQuotaLimit;
            return this;
        }

        /**
         * Sets the organization quota limit.
         * @param organizationQuotaLimit The organization quota limit.
         * @return The Builder instance.
         */
        public Builder organizationQuotaLimit(TokenQuotaBucket organizationQuotaLimit) {
            this.organizationQuotaLimit = organizationQuotaLimit;
            return this;
        }

        /**
         * Sets the retry after time in seconds.
         * @param retryAfter The retry after time in seconds.
         * @return The Builder instance.
         */
        public Builder retryAfter(long retryAfter) {
            this.retryAfter = retryAfter;
            return this;
        }

        /**
         * Sets the values map.
         * @param values The values map.
         * @return The Builder instance.
         */
        public Builder values(Map<String, Object> values) {
            this.values = values;
            return this;
        }

        public RateLimitException build() {
            RateLimitException exception = (this.values != null)
                ? new RateLimitException(this.limit, this.remaining, this.reset, this.values)
                : new RateLimitException(this.limit, this.remaining, this.reset);

            exception.clientQuotaLimit = this.clientQuotaLimit;
            exception.organizationQuotaLimit = this.organizationQuotaLimit;
            exception.retryAfter = this.retryAfter;

            return exception;
        }
    }

}

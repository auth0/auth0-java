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
    private final TokenQuotaBucket clientQuotaLimit;
    private final TokenQuotaBucket organizationQuotaLimit;

    private static final int STATUS_CODE_TOO_MANY_REQUEST = 429;

    public RateLimitException(long limit, long remaining, long reset, TokenQuotaBucket clientQuotaLimit, TokenQuotaBucket organizationQuotaLimit, Map<String, Object> values) {
        super(values, STATUS_CODE_TOO_MANY_REQUEST);
        this.limit = limit;
        this.remaining = remaining;
        this.reset = reset;
        this.clientQuotaLimit = clientQuotaLimit;
        this.organizationQuotaLimit = organizationQuotaLimit;
    }

    public RateLimitException(long limit, long remaining, long reset, TokenQuotaBucket clientQuotaLimit, TokenQuotaBucket organizationQuotaLimit) {
        super("Rate limit reached", STATUS_CODE_TOO_MANY_REQUEST, null);
        this.limit = limit;
        this.remaining = remaining;
        this.reset = reset;
        this.clientQuotaLimit = clientQuotaLimit;
        this.organizationQuotaLimit = organizationQuotaLimit;
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

}

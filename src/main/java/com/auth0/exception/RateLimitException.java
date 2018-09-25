package com.auth0.exception;

/**
 * Represents a server error when a rate limit has been exceeded.
 * <p>
 * Getters for {@code limit, remaining} and {@code reset} corresponds to {@code X-RateLimit-Limit, X-RateLimit-Remaining} and {@code X-RateLimit-Reset} HTTP headers.
 * If the value of any headers is missing, then a default value -1 will assigned.
 * <p>
 * "To learn more about rate limits, visit <a href="https://auth0.com/docs/policies/rate-limits">https://auth0.com/docs/policies/rate-limits</a>
 */
public class RateLimitException extends APIException {

    private final long limit;
    private final long remaining;
    private final long reset;

    private static final int STATUS_CODE_TOO_MANY_REQUEST = 429;

    public RateLimitException(long limit, long remaining, long reset) {
        super("Rate limit reached", STATUS_CODE_TOO_MANY_REQUEST, null);
        this.limit = limit;
        this.remaining = remaining;
        this.reset = reset;
    }

    /**
     * Getter for the maximum number of requests available in the current time frame.
     * @return The maximun number of requests.
     */
    public long getLimit() {
        return limit;
    }

    /**
     * Getter for the number of remaining requests in the current time frame.
     * @return Number of remaining requests.
     */
    public long getRemaining() {
        return remaining;
    }

    /**
     * Getter for the UNIX timestamp of the expected time when the rate limit will reset.
     * @return The UNIX timestamp.
     */
    public long getReset() {
        return reset;
    }

}

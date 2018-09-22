package com.auth0.exception;

/**
 * Represents a server error when a rate limit has been exceeded.
 */
public class RateLimitException extends APIException {

    private final long limit;
    private final long remaining;
    private final long reset;

    public RateLimitException(long limit, long remaining, long reset, String message) {
        super(message, 429, null);
        this.limit = limit;
        this.remaining = remaining;
        this.reset = reset;
    }

    public long getLimit() {
        return limit;
    }

    public long getRemaining() {
        return remaining;
    }

    public long getReset() {
        return reset;
    }

}

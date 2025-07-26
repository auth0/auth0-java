package com.auth0.net;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TokenQuotaLimit {
    private int quota;
    private int remaining;
    private int resetAfter;

    public TokenQuotaLimit(int quota, int remaining, int resetAfter) {
        this.quota = quota;
        this.remaining = remaining;
        this.resetAfter = resetAfter;
    }

    public int getQuota() {
        return quota;
    }

    public int getRemaining() {
        return remaining;
    }

    public int getResetAfter() {
        return resetAfter;
    }
}

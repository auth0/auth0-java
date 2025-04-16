package com.auth0.net;

public class TokenQuota {
    private int quota;
    private int remaining;
    private int time;

    public TokenQuota(int quota, int remaining, int time) {
        this.quota = quota;
        this.remaining = remaining;
        this.time = time;
    }

    public int getQuota() {
        return quota;
    }

    public int getRemaining() {
        return remaining;
    }

    public int getTime() {
        return time;
    }
}

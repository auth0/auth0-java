package com.auth0.net;

public class TokenQuotaBucket {
    private TokenQuota perHour;
    private TokenQuota perDay;

    public TokenQuotaBucket(TokenQuota perHour, TokenQuota perDay) {
        this.perHour = perHour;
        this.perDay = perDay;
    }

    public TokenQuota getPerHour() {
        return perHour;
    }

    public TokenQuota getPerDay() {
        return perDay;
    }
}

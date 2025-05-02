package com.auth0.net;

public class TokenQuotaBucket {
    private TokenQuotaLimit perHour;
    private TokenQuotaLimit perDay;

    public TokenQuotaBucket(TokenQuotaLimit perHour, TokenQuotaLimit perDay) {
        this.perHour = perHour;
        this.perDay = perDay;
    }

    public TokenQuotaLimit getPerHour() {
        return perHour;
    }

    public TokenQuotaLimit getPerDay() {
        return perDay;
    }
}

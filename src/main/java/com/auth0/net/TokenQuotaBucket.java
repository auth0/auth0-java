package com.auth0.net;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TokenQuotaBucket {
    private TokenQuotaLimit perHour;
    private TokenQuotaLimit perDay;

    /**
     *  Constructor for TokenQuotaBucket.
     */
    public TokenQuotaBucket(TokenQuotaLimit perHour, TokenQuotaLimit perDay) {
        this.perHour = perHour;
        this.perDay = perDay;
    }

    /**
     * @return the number of client credentials allowed per hour
     */
    public TokenQuotaLimit getPerHour() {
        return perHour;
    }

    /**
     * @return the number of client credentials allowed per hour
     */
    public TokenQuotaLimit getPerDay() {
        return perDay;
    }


}

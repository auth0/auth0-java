package com.auth0.json.mgmt.tokenquota;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ClientCredentials {
    @JsonProperty("per_day")
    private int perDay;
    @JsonProperty("per_hour")
    private int perHour;
    @JsonProperty("enforce")
    private boolean enforce;

    /**
     * @return the number of client credentials allowed per day
     */
    public int getPerDay() {
        return perDay;
    }

    /**
     * Sets the number of client credentials allowed per day.
     *
     * @param perDay the number of client credentials allowed per day
     */
    public void setPerDay(int perDay) {
        this.perDay = perDay;
    }

    /**
     * @return the number of client credentials allowed per hour
     */
    public int getPerHour() {
        return perHour;
    }

    /**
     * Sets the number of client credentials allowed per hour.
     *
     * @param perHour the number of client credentials allowed per hour
     */
    public void setPerHour(int perHour) {
        this.perHour = perHour;
    }

    /**
     * @return true if the quota is enforced, false otherwise
     */
    public boolean isEnforce() {
        return enforce;
    }

    /**
     * Sets whether the quota is enforced.
     *
     * @param enforce true to enforce the quota, false otherwise
     */
    public void setEnforce(boolean enforce) {
        this.enforce = enforce;
    }
}

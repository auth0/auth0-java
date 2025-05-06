package com.auth0.json.mgmt.tokenquota;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientCredentials {
    @JsonProperty("per_day")
    private Integer perDay;
    @JsonProperty("per_hour")
    private Integer perHour;
    @JsonProperty("enforce")
    private boolean enforce;
    /**
     * Default constructor for ClientCredentials.
     */
    public ClientCredentials() {}

    /**
     * Constructor for ClientCredentials.
     *
     * @param perDay the number of client credentials allowed per day
     * @param perHour the number of client credentials allowed per hour
     * @param enforce true if the quota is enforced, false otherwise
     */
    public ClientCredentials(Integer perDay, Integer perHour, boolean enforce) {
        this.perDay = perDay;
        this.perHour = perHour;
        this.enforce = enforce;
    }

    /**
     * @return the number of client credentials allowed per day
     */
    public Integer getPerDay() {
        return perDay;
    }

    /**
     * Sets the number of client credentials allowed per day.
     *
     * @param perDay the number of client credentials allowed per day
     */
    public void setPerDay(Integer perDay) {
        this.perDay = perDay;
    }

    /**
     * @return the number of client credentials allowed per hour
     */
    public Integer getPerHour() {
        return perHour;
    }

    /**
     * Sets the number of client credentials allowed per hour.
     *
     * @param perHour the number of client credentials allowed per hour
     */
    public void setPerHour(Integer perHour) {
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

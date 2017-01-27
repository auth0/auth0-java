package com.auth0.json.mgmt;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class that represents an Auth0 Daily Stats object. Related to the {@link com.auth0.client.mgmt.StatsEntity()} entity.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DailyStats {

    @JsonProperty("logins")
    private Integer logins;
    @JsonProperty("date")
    private String date;

    /**
     * Getter for the amount of logins on the date
     *
     * @return the amount of logins
     */
    @JsonProperty("logins")
    public Integer getLogins() {
        return logins;
    }

    /**
     * Getter for the date to which the stats belong
     *
     * @return the date to which the stats belong
     */
    @JsonProperty("date")
    public String getDate() {
        return date;
    }
}
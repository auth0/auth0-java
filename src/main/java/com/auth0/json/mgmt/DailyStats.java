package com.auth0.json.mgmt;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("unused")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DailyStats {

    @JsonProperty("logins")
    private Integer logins;
    @JsonProperty("date")
    private String date;

    public Integer getLogins() {
        return logins;
    }

    public String getDate() {
        return date;
    }
}
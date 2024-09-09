package com.auth0.json.mgmt.users.sessions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * This does not extend com.auth0.json.mgmt.Page<RefreshToken> because the URL only supports "next" and "take" pagination.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SessionsPage {
    @JsonProperty("total")
    private Integer total;

    @JsonProperty("next")
    private String next;

    @JsonProperty("sessions")
    private List<Session> sessions;

    /**
     * @return the total number of refresh tokens. This is only present when `include_totals` is passed as a query parameter.
     */
    public Integer getTotal() {
        return total;
    }

    /**
     * @return the token ID from which to start selection for a new page
     */
    public String getNext() {
        return next;
    }

    /**
     * @return the list of Sessions
     */
    public List<Session> getSessions() {
        return sessions;
    }
}

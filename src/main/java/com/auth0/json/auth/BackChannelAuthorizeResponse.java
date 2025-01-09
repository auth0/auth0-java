package com.auth0.json.auth;

import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BackChannelAuthorizeResponse {
    @JsonProperty("auth_req_id")
    private String authReqId;
    @JsonProperty("expires_in")
    private Long expiresIn;
    @JsonProperty("interval")
    private Integer interval;

    @JsonCreator
    public BackChannelAuthorizeResponse(@JsonProperty("auth_req_id") String authReqId, @JsonProperty("expires_in") Long expiresIn, @JsonProperty("interval") Integer interval) {
        this.authReqId = authReqId;
        this.expiresIn = expiresIn;
        this.interval = interval;
    }

    /**
     * Getter for the Auth Request ID.
     * @return the Auth Request ID.
     */
    public String getAuthReqId() {
        return authReqId;
    }

    /**
     * Getter for the Expires In value.
     * @return the Expires In value.
     */
    public Long getExpiresIn() {
        return expiresIn;
    }

    /**
     * Getter for the Interval value.
     * @return the Interval value.
     */
    public Integer getInterval() {
        return interval;
    }
}

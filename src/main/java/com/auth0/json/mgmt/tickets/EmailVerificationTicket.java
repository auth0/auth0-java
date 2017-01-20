
package com.auth0.json.mgmt.tickets;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("unused")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmailVerificationTicket {

    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("result_url")
    private String resultUrl;
    @JsonProperty("ttl_sec")
    private Integer ttlSec;
    @JsonProperty("ticket")
    private String ticket;

    @JsonCreator
    public EmailVerificationTicket(@JsonProperty("user_id") String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getResultUrl() {
        return resultUrl;
    }

    public void setResultUrl(String resultUrl) {
        this.resultUrl = resultUrl;
    }

    public Integer getTtlSec() {
        return ttlSec;
    }

    public void setTtlSec(Integer ttlSec) {
        this.ttlSec = ttlSec;
    }

    public String getTicket() {
        return ticket;
    }
}

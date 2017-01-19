
package com.auth0.json.mgmt;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("unused")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PasswordChangeTicket {

    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("result_url")
    private String resultUrl;
    @JsonProperty("ttl_sec")
    private Integer ttlSec;
    @JsonProperty("new_password")
    private String newPassword;
    @JsonProperty("connection_id")
    private String connectionId;
    @JsonProperty("email")
    private String email;
    @JsonProperty("ticket")
    private String ticket;

    @JsonCreator
    public PasswordChangeTicket(@JsonProperty("user_id") String userId) {
        this.userId = userId;
    }

    public PasswordChangeTicket(String email, String connectionId) {
        this.email = email;
        this.connectionId = connectionId;
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

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(String connectionId) {
        this.connectionId = connectionId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTicket() {
        return ticket;
    }
}

package com.auth0.json.mgmt.sessions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthenticationMethod {
    @JsonProperty("name")
    private String name;
    @JsonProperty("timestamp")
    private Date timestamp;
    @JsonProperty("type")
    private String type;

    /**
     * @return One of: "federated", "passkey", "pwd", "sms", "email", "mfa", "mock" or a custom method denoted by a URL
     */
    public String getName() {
        return name;
    }

    /**
     * @return Timestamp of when the signal was received
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * @return A specific MFA factor. Only present when "name" is set to "mfa"
     */
    public String getType() {
        return type;
    }
}

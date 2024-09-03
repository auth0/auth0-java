package com.auth0.json.mgmt.users.refreshtokens;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Device {
    @JsonProperty("initial_ip")
    private String initialIp;
    @JsonProperty("initial_asn")
    private String initialAsn;
    @JsonProperty("initial_user_agent")
    private String initialUserAgent;
    @JsonProperty("last_ip")
    private String lastIp;
    @JsonProperty("last_asn")
    private String lastAsn;
    @JsonProperty("last_user_agent")
    private String lastUserAgent;

    /**
     * @return First IP address associated with this session
     */
    public String getInitialIp() {
        return initialIp;
    }

    /**
     * @return First autonomous system number associated with this session
     */
    public String getInitialAsn() {
        return initialAsn;
    }

    /**
     * @return First user agent associated with this session
     */
    public String getInitialUserAgent() {
        return initialUserAgent;
    }

    /**
     * @return Last IP address from which this user logged in
     */
    public String getLastIp() {
        return lastIp;
    }

    /**
     * @return Last autonomous system number from which this user logged in
     */
    public String getLastAsn() {
        return lastAsn;
    }

    /**
     * @return Last user agent of the device from which this user logged in
     */
    public String getLastUserAgent() {
        return lastUserAgent;
    }
}

package com.auth0.json.mgmt.users.sessions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Device {
    @JsonProperty("initial_ip")
    private String initialIP;
    @JsonProperty("initial_asn")
    private String initialASN;
    @JsonProperty("last_user_agent")
    private String lastUserAgent;
    @JsonProperty("last_ip")
    private String lastIP;
    @JsonProperty("last_asn")
    private String lastASN;

    /**
     * @return First IP address associated with this session
     */
    public String getInitialIP() {
        return initialIP;
    }

    /**
     * @return First autonomous system number associated with this session
     */
    public String getInitialASN() {
        return initialASN;
    }

    /**
     * @return Last user agent of the device from which this user logged in
     */
    public String getLastUserAgent() {
        return lastUserAgent;
    }

    /**
     * @return Last IP address from which this user logged in
     */
    public String getLastIP() {
        return lastIP;
    }

    /**
     * @return Last autonomous system number from which this user logged in
     */
    public String getLastASN() {
        return lastASN;
    }
}

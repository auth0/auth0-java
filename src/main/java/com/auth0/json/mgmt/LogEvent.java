package com.auth0.json.mgmt;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

@SuppressWarnings("unused")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LogEvent {

    @JsonProperty("date")
    private String date;
    @JsonProperty("type")
    private String type;
    @JsonProperty("client_id")
    private String clientId;
    @JsonProperty("client_name")
    private String clientName;
    @JsonProperty("ip")
    private String ip;
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("location_info")
    private Map<String, Object> locationInfo;
    @JsonProperty("details")
    private Map<String, Object> details;

    public String getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public String getIp() {
        return ip;
    }

    public String getUserId() {
        return userId;
    }

    public Map<String, Object> getLocationInfo() {
        return locationInfo;
    }

    public Map<String, Object> getDetails() {
        return details;
    }
}
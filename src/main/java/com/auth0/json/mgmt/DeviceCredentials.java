package com.auth0.json.mgmt;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("unused")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeviceCredentials {

    @JsonProperty("id")
    private String id;
    @JsonProperty("device_name")
    private String deviceName;
    @JsonProperty("type")
    private String type;
    @JsonProperty("value")
    private String value;
    @JsonProperty("device_id")
    private String deviceId;
    @JsonProperty("client_id")
    private String clientId;
    @JsonProperty("user_id")
    private String userId;

    @JsonCreator
    public DeviceCredentials(@JsonProperty("device_name") String deviceName, @JsonProperty("type") String type, @JsonProperty("value") String value, @JsonProperty("device_id") String deviceId, @JsonProperty("client_id") String clientId) {
        this.deviceName = deviceName;
        this.type = type;
        this.value = value;
        this.deviceId = deviceId;
        this.clientId = clientId;
    }

    public String getId() {
        return id;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
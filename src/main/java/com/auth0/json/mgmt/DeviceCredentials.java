package com.auth0.json.mgmt;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class that represents an Auth0 Device Credentials object. Related to the {@link com.auth0.client.mgmt.DeviceCredentialsEntity} entity.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
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

    /**
     * Getter for the unique identifier of the device credentials.
     *
     * @return the id.
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * Getter for the device name.
     *
     * @return the device name.
     */
    @JsonProperty("device_name")
    public String getDeviceName() {
        return deviceName;
    }

    /**
     * Setter for the device name.
     *
     * @param deviceName the device name to set.
     */
    @JsonProperty("device_name")
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    /**
     * Getter for the type of credential.
     *
     * @return the type of credential.
     */
    @JsonProperty("type")
    public String getType() {
        return type;
    }

    /**
     * Setter for the type of credential. Either 'public_key' or 'refresh_token'.
     *
     * @param type the type of credential to set.
     */
    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Setter for the base64 encoded string with the value of the credential.
     *
     * @param value the value of the credential to set.
     */
    @JsonProperty("value")
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Getter for the unique device identifier.
     *
     * @return the unique device identifier.
     */
    @JsonProperty("device_id")
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * Setter for the unique device identifier.
     *
     * @param deviceId the unique device identifier to set.
     */
    @JsonProperty("device_id")
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    /**
     * Setter for the client id of the application for which the credential will be created.
     *
     * @param clientId the application's client id to set.
     */
    @JsonProperty("client_id")
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    /**
     * Getter for the user id of the device
     *
     * @return the user if
     */
    @JsonProperty("user_id")
    public String getUserId() {
        return userId;
    }

    /**
     * Setter for the user id of the devices to retrieve
     *
     * @param userId the user id to set.
     */
    @JsonProperty("user_id")
    public void setUserId(String userId) {
        this.userId = userId;
    }
}
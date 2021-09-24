package com.auth0.json.mgmt.logevents;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.Map;

/**
 * Class that represents an Auth0 Events object. Related to the {@link com.auth0.client.mgmt.LogEventsEntity} entity.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LogEvent {

    @JsonProperty("_id")
    private String id;
    @JsonProperty("log_id")
    private String logId;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("date")
    private Date date;
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
    @JsonProperty("user_name")
    private String userName;
    @JsonProperty("location_info")
    private Map<String, Object> locationInfo;
    @JsonProperty("details")
    private Map<String, Object> details;

    /**
     * Getter for the id of this event.
     *
     * @return the id.
     */
    @JsonProperty("_id")
    public String getId() {
        return id;
    }

    /**
     * Getter for the log_id of this event.
     *
     * @return the log_id of this event.
     */
    @JsonProperty("log_id")
    public String getLogId() {
        return logId;
    }

    /**
     * Getter for the date of this event.
     *
     * @return the date.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("date")
    public Date getDate() {
        return date;
    }

    /**
     * Getter for the type of this event.
     *
     * @return the type.
     */
    @JsonProperty("type")
    public String getType() {
        return type;
    }

    /**
     * Getter for the application's client id related to this event.
     *
     * @return the application's client id.
     */
    @JsonProperty("client_id")
    public String getClientId() {
        return clientId;
    }

    /**
     * Getter for the application's client name related to this event.
     *
     * @return the application's client name.
     */
    @JsonProperty("client_name")
    public String getClientName() {
        return clientName;
    }

    /**
     * Getter for the IP address related to this event.
     *
     * @return the IP address.
     */
    @JsonProperty("ip")
    public String getIP() {
        return ip;
    }

    /**
     * Getter for the user id related to this event.
     *
     * @return the user id.
     */
    @JsonProperty("user_id")
    public String getUserId() {
        return userId;
    }

    /**
     * Getter for the user name related to this event.
     *
     * @return the user id.
     */
    @JsonProperty("user_name")
    public String getUserName() {
        return userName;
    }

    /**
     * Getter for the location info object.
     *
     * @return the location info object.
     */
    @JsonProperty("location_info")
    public Map<String, Object> getLocationInfo() {
        return locationInfo;
    }

    /**
     * Getter for the details object.
     *
     * @return the details object.
     */
    @JsonProperty("details")
    public Map<String, Object> getDetails() {
        return details;
    }
}

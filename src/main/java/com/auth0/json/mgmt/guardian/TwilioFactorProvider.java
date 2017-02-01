package com.auth0.json.mgmt.guardian;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class that represents a Guardian's SMS Factor Provider for Twilio.
 * Related to the {@link com.auth0.client.mgmt.GuardianEntity} entity.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TwilioFactorProvider {

    @JsonProperty("from")
    private String from;
    @JsonProperty("messaging_service_sid")
    private String messagingServiceSID;
    @JsonProperty("auth_token")
    private String authToken;
    @JsonProperty("sid")
    private String sid;


    /**
     * Getter for the Twilio From number.
     *
     * @return the from number.
     */
    @JsonProperty("from")
    public String getFrom() {
        return from;
    }

    /**
     * Setter for the Twilio From number.
     *
     * @param from the from number to set.
     */
    @JsonProperty("from")
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * Getter for the Twilio Messaging Service SID.
     *
     * @return the messaging service SID.
     */
    @JsonProperty("messaging_service_sid")
    public String getMessagingServiceSID() {
        return messagingServiceSID;
    }

    /**
     * Setter for the Twilio Messaging Service SID.
     *
     * @param messagingServiceSID the messaging service SID.
     */
    @JsonProperty("messaging_service_sid")
    public void setMessagingServiceSID(String messagingServiceSID) {
        this.messagingServiceSID = messagingServiceSID;
    }

    /**
     * Getter for the Twilio auth token.
     *
     * @return the Twilio auth token.
     */
    @JsonProperty("auth_token")
    public String getAuthToken() {
        return authToken;
    }

    /**
     * Setter for the Twilio auth token.
     *
     * @param authToken the Twilio auth token to set.
     */
    @JsonProperty("auth_token")
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    /**
     * Getter for the Twilio SID
     *
     * @return the Twilio SID.
     */
    @JsonProperty("sid")
    public String getSID() {
        return sid;
    }

    /**
     * Setter for the Twilio SID
     *
     * @param SID the Twilio SID to set.
     */
    @JsonProperty("sid")
    public void setSID(String SID) {
        this.sid = SID;
    }
}

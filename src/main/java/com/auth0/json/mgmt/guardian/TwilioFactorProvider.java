package com.auth0.json.mgmt.guardian;

import com.fasterxml.jackson.annotation.JsonCreator;
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
     * Creates a Twilio settings object
     *
     * @param from the Twilio From number.
     * @param messagingServiceSID the Twilio Messaging Service SID.
     * @param authToken the Twilio auth token.
     * @param sid the Twilio SID.
     */
    @JsonCreator
    public TwilioFactorProvider(@JsonProperty("from") String from, @JsonProperty("messaging_service_sid") String messagingServiceSID, @JsonProperty("auth_token") String authToken, @JsonProperty("sid") String sid) {
        this.from = from;
        this.messagingServiceSID = messagingServiceSID;
        this.authToken = authToken;
        this.sid = sid;
    }

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
     * Getter for the Twilio Messaging Service SID.
     *
     * @return the messaging service SID.
     */
    @JsonProperty("messaging_service_sid")
    public String getMessagingServiceSID() {
        return messagingServiceSID;
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
     * Getter for the Twilio SID.
     *
     * @return the Twilio SID.
     */
    @JsonProperty("sid")
    public String getSID() {
        return sid;
    }
}

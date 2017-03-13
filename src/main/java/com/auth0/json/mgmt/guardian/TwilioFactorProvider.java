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
     * Creates an empty Twilio settings object
     *
     * @deprecated use the full constructor instead
     */
    @Deprecated
    public TwilioFactorProvider() {
    }

    /**
     * Creates a Twilio settings object
     *
     * You must only specify either a non-null `from` or `messagingServiceSID`, but not both.
     *
     * @param from the Twilio From number.
     * @param messagingServiceSID the Twilio Messaging Service SID.
     * @param authToken the Twilio auth token.
     * @param sid the Twilio SID.
     * @throws IllegalArgumentException when both `from` and `messagingServiceSID` are set
     */
    @JsonCreator
    public TwilioFactorProvider(@JsonProperty("from") String from, @JsonProperty("messaging_service_sid") String messagingServiceSID, @JsonProperty("auth_token") String authToken, @JsonProperty("sid") String sid)
            throws IllegalArgumentException {
        if (from != null && messagingServiceSID != null) {
            throw new IllegalArgumentException("You must specify either `from` or `messagingServiceSID`, but not both");
        }
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
     * Setter for the Twilio From number.
     *
     * @param from the from number to set.
     * @throws IllegalArgumentException when both `from` and `messagingServiceSID` are set
     * @deprecated use the constructor instead
     */
    @Deprecated
    @JsonProperty("from")
    public void setFrom(String from) throws IllegalArgumentException {
        if (messagingServiceSID != null) {
            throw new IllegalArgumentException("You must specify either `from` or `messagingServiceSID`, but not both");
        }
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
     * @throws IllegalArgumentException when both `from` and `messagingServiceSID` are set
     * @deprecated use the constructor instead
     */
    @Deprecated
    @JsonProperty("messaging_service_sid")
    public void setMessagingServiceSID(String messagingServiceSID) throws IllegalArgumentException {
        if (from != null) {
            throw new IllegalArgumentException("You must specify either `from` or `messagingServiceSID`, but not both");
        }
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
     * @deprecated use the constructor instead
     */
    @Deprecated
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
     * @deprecated use the constructor instead
     */
    @Deprecated
    @JsonProperty("sid")
    public void setSID(String SID) {
        this.sid = SID;
    }
}

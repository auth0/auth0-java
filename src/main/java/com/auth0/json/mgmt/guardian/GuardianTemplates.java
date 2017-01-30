package com.auth0.json.mgmt.guardian;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class that represents an Auth0 Guardian Templates object. Related to the {@link com.auth0.client.mgmt.GuardianEntity} entity.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GuardianTemplates {

    @JsonProperty("enrollment_message")
    private String enrollmentMessage;
    @JsonProperty("verification_message")
    private String verificationMessage;

    @JsonCreator
    public GuardianTemplates(@JsonProperty("enrollment_message") String enrollmentMessage, @JsonProperty("verification_message") String verificationMessage) {
        this.enrollmentMessage = enrollmentMessage;
        this.verificationMessage = verificationMessage;
    }

    /**
     * Getter for the message to send when the user is invited to enroll with a phone number.
     *
     * @return the message to send upon enrollment.
     */
    @JsonProperty("enrollment_message")
    public String getEnrollmentMessage() {
        return enrollmentMessage;
    }

    /**
     * Setter for the message to send when the user is invited to enroll with a phone number.
     *
     * @param enrollmentMessage the message to send upon enrollment.
     */
    @JsonProperty("enrollment_message")
    public void setEnrollmentMessage(String enrollmentMessage) {
        this.enrollmentMessage = enrollmentMessage;
    }

    /**
     * Getter for the message to send when the user is prompted to verify their account.
     *
     * @return the message to send upon verification.
     */
    @JsonProperty("verification_message")
    public String getVerificationMessage() {
        return verificationMessage;
    }

    /**
     * Setter for the message to send when the user is prompted to verify their account.
     *
     * @param verificationMessage the message to send upon verification.
     */
    @JsonProperty("verification_message")
    public void setVerificationMessage(String verificationMessage) {
        this.verificationMessage = verificationMessage;
    }
}

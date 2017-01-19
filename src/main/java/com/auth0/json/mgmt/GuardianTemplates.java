package com.auth0.json.mgmt;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("unused")
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

    public String getEnrollmentMessage() {
        return enrollmentMessage;
    }

    public void setEnrollmentMessage(String enrollmentMessage) {
        this.enrollmentMessage = enrollmentMessage;
    }

    public String getVerificationMessage() {
        return verificationMessage;
    }

    public void setVerificationMessage(String verificationMessage) {
        this.verificationMessage = verificationMessage;
    }
}

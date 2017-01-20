package com.auth0.json.mgmt.guardian;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("unused")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GuardianEnrollmentTicket {

    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("send_email")
    private Boolean sendEmail;
    @JsonProperty("email")
    private String email;
    @JsonProperty("ticket_id")
    private String ticketId;
    @JsonProperty("ticket_url")
    private String ticketUrl;

    public GuardianEnrollmentTicket(String userId) {
        this.userId = userId;
    }

    @JsonCreator
    GuardianEnrollmentTicket(@JsonProperty("ticket_id") String ticketId, @JsonProperty("ticket_url") String ticketUrl) {
        this.ticketId = ticketId;
        this.ticketUrl = ticketUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Boolean getSendEmail() {
        return sendEmail;
    }

    public void setSendEmail(Boolean sendEmail) {
        this.sendEmail = sendEmail;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTicketId() {
        return ticketId;
    }

    public String getTicketUrl() {
        return ticketUrl;
    }
}

package com.auth0.json.mgmt.guardian;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class that represents an Auth0 Guardian Enrollment Ticket object. Related to the {@link com.auth0.client.mgmt.GuardianEntity} entity.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EnrollmentTicket {

    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("send_mail")
    private Boolean sendEmail;
    @JsonProperty("email")
    private String email;
    @JsonProperty("ticket_id")
    private String ticketId;
    @JsonProperty("ticket_url")
    private String ticketUrl;

    /**
     * Creates a new instance of the Enrollment Ticket for a given User. You can then specify the email and sendMail
     *
     * @param userId the user id
     */
    public EnrollmentTicket(String userId) {
        this.userId = userId;
    }

    @JsonCreator
    EnrollmentTicket(@JsonProperty("ticket_id") String ticketId, @JsonProperty("ticket_url") String ticketUrl) {
        this.ticketId = ticketId;
        this.ticketUrl = ticketUrl;
    }

    /**
     * Getter for the id of the User this ticket was made for.
     *
     * @return the user id.
     */
    @JsonProperty("user_id")
    public String getUserId() {
        return userId;
    }

    /**
     * Setter for the id of the user this ticket is meant to.
     *
     * @param userId the user id to set.
     */
    @JsonProperty("user_id")
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Whether to send and email for enrollment or not.
     *
     * @return true is this ticket will send an email upon enrollment, false otherwise.
     */
    @JsonProperty("send_mail")
    public Boolean willSendEmail() {
        return sendEmail;
    }

    /**
     * Sets whether to send and email for enrollment or not.
     *
     * @param sendEmail whether this ticket will send an email upon enrollment or not.
     */
    @JsonProperty("send_mail")
    public void setSendEmail(Boolean sendEmail) {
        this.sendEmail = sendEmail;
    }

    /**
     * Getter for the email to which the ticket will be sent.
     *
     * @return the email.
     */
    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    /**
     * Setter for the email to which the ticket will be sent.
     *
     * @param email the email to sent the ticket to.
     */
    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter for the ticket id.
     *
     * @return the ticket id.
     */
    @JsonProperty("ticket_id")
    public String getTicketId() {
        return ticketId;
    }

    /**
     * Getter for the ticket url.
     *
     * @return the ticket url.
     */
    @JsonProperty("ticket_url")
    public String getTicketUrl() {
        return ticketUrl;
    }
}

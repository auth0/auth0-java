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
     * Creates a new instance of the Enrollment Ticket for a given User.
     *
     * @param userId the user id
     */
    public EnrollmentTicket(String userId) {
        this(userId, null, null);
    }

    /**
     * Creates a new instance of the Enrollment Ticket for a given User, specifying if you want the ticket to also be
     * sent to the user by email.
     *
     * @param userId the user id
     * @param sendEmail whether the ticket should also be sent to the user by email
     */
    public EnrollmentTicket(String userId, Boolean sendEmail) {
        this(userId, sendEmail, null);
    }

    /**
     * Creates a new instance of the Enrollment Ticket for a given User, specifying if you want the ticket to also be
     * sent to the specified email address.
     *
     * @param userId the user id
     * @param sendEmail whether the ticket should also be sent to the user by email
     * @param email the email where the ticket will be sent.
     */
    public EnrollmentTicket(String userId, Boolean sendEmail, String email) {
        this.userId = userId;
        this.sendEmail = sendEmail;
        this.email = email;
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
     * Whether to send and email for enrollment or not.
     *
     * @return true is this ticket will send an email upon enrollment, false otherwise.
     */
    @JsonProperty("send_mail")
    public Boolean willSendEmail() {
        return sendEmail;
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

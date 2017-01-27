
package com.auth0.json.mgmt.tickets;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class that represents an Auth0 Password Change Ticket object. Related to the {@link com.auth0.client.mgmt.TicketsEntity()} entity.
 */
@SuppressWarnings({"unused", "WeakerAccess", "FieldCanBeLocal"})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PasswordChangeTicket {

    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("result_url")
    private String resultUrl;
    @JsonProperty("ttl_sec")
    private Integer ttlSec;
    @JsonProperty("new_password")
    private String newPassword;
    @JsonProperty("connection_id")
    private String connectionId;
    @JsonProperty("email")
    private String email;
    @JsonProperty("ticket")
    private String ticket;

    @JsonCreator
    public PasswordChangeTicket(@JsonProperty("user_id") String userId) {
        this.userId = userId;
    }

    public PasswordChangeTicket(String email, String connectionId) {
        this.email = email;
        this.connectionId = connectionId;
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
     * Setter for the url the user will be redirected to after using the ticket.
     *
     * @param resultUrl the result url.
     */
    @JsonProperty("result_url")
    public void setResultUrl(String resultUrl) {
        this.resultUrl = resultUrl;
    }

    /**
     * The ticket's lifetime in seconds starting from the moment of creation. After expiration the ticket can not be used to verify the users's email. If not specified or if you send 0 the Auth0 default lifetime will be applied.
     *
     * @param seconds the lifetime in seconds to set.
     */
    @JsonProperty("ttl_sec")
    public void setTTLSeconds(Integer seconds) {
        this.ttlSec = seconds;
    }

    /**
     * Setter for the new password to set after the ticket is used.
     *
     * @param newPassword the new password to set.
     */
    @JsonProperty("new_password")
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    /**
     * Setter for the id of the connection the user's email should be search in.
     *
     * @param connectionId the connection id
     */
    @JsonProperty("connection_id")
    public void setConnectionId(String connectionId) {
        this.connectionId = connectionId;
    }

    /**
     * Setter for the user's email.
     *
     * @param email the user email to set.
     */
    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter for the ticket url.
     *
     * @return the ticket url.
     */
    @JsonProperty("ticket")
    public String getTicket() {
        return ticket;
    }
}

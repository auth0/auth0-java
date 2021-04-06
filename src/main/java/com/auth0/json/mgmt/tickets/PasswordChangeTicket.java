
package com.auth0.json.mgmt.tickets;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class that represents an Auth0 Password Change Ticket object. Related to the {@link com.auth0.client.mgmt.TicketsEntity} entity.
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
    private char[] newPassword;
    @JsonProperty("connection_id")
    private String connectionId;
    @JsonProperty("email")
    private String email;
    @JsonProperty("ticket")
    private String ticket;
    @JsonProperty("mark_email_as_verified")
    private Boolean markEmailAsVerified;
    @JsonProperty("organization_id")
    private String orgId;

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
     *
     * @deprecated Use {@linkplain #setNewPassword(char[])} instead.
     */
    @JsonProperty("new_password")
    @Deprecated
    public void setNewPassword(String newPassword) {
        setNewPassword(newPassword != null ? newPassword.toCharArray() : null);
    }

    @JsonProperty("new_password")
    public void setNewPassword(char[] newPassword) {
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
     * Setter for the mark_email_as_verified to define if user.email_verified should be set to true after ticket is consumed.
     *
     * @param  markEmailAsVerified true if email_verified attribute must be set to true once password is changed, false if email_verified attribute should not be updated.
     */
    @JsonProperty("mark_email_as_verified")
    public void setMarkEmailAsVerified(Boolean markEmailAsVerified){
        this.markEmailAsVerified = markEmailAsVerified;
    }

    /**
     * Sets the organization ID. This allows the organization_id and organization_name to be included in the redirect URL query.
     *
     * @param orgId the ID of the organization
     */
    @JsonProperty("organization_id")
    public void setOrganizationId(String orgId) {
        this.orgId = orgId;
    }

    /**
     * Gets the organization ID
     *
     * @return the organization ID
     */
    @JsonProperty("organization_id")
    public String getOrganizationId() {
        return orgId;
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

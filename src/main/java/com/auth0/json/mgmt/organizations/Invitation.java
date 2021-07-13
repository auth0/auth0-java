package com.auth0.json.mgmt.organizations;

import com.fasterxml.jackson.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Represents the Invitation object for an organization.
 * @see com.auth0.client.mgmt.OrganizationsEntity
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Invitation {

    @JsonProperty("id")
    private String id;
    @JsonProperty("inviter")
    private Inviter inviter;
    @JsonProperty("invitee")
    private Invitee invitee;
    @JsonProperty("ttl_sec")
    private Integer ttlInSeconds;
    @JsonProperty("client_id")
    private String clientId;
    @JsonProperty("connection_id")
    private String connectionId;
    @JsonProperty("app_metadata")
    private Map<String, Object> appMetadata;
    @JsonProperty("user_metadata")
    private Map<String, Object> userMetadata;
    @JsonProperty("send_invitation_email")
    private Boolean sendInvitationEmail;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("created_at")
    private Date createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("expires_at")
    private Date expiresAt;
    @JsonProperty("ticket_id")
    private String ticketId;
    @JsonProperty("invitation_url")
    private String invitationUrl;
    @JsonProperty("organization_id")
    private String organizationId;
    @JsonProperty("roles")
    private List<String> roles;

    /**
     * Create a new instance.
     *
     * @param inviter the {@linkplain Inviter} of this invitation.
     * @param invitee the {@linkplain Invitee} of this invitation.
     * @param clientId The id of the connection the invitee will authenticate with.\
     */
    @JsonCreator
    public Invitation(@JsonProperty("inviter") Inviter inviter, @JsonProperty("invitee") Invitee invitee, @JsonProperty("client_id") String clientId) {
        this.inviter = inviter;
        this.invitee = invitee;
        this.clientId = clientId;
    }

    /**
     * @return the ID of this invitation.
     */
    public String getId() {
        return id;
    }

    /**
     * @return the {@linkplain Inviter} of this invitation.
     */
    public Inviter getInviter() {
        return inviter;
    }

    /**
     * @return the {@linkplain Invitee} of this invitation.
     */
    public Invitee getInvitee() {
        return invitee;
    }

    /**
     * @return the number of seconds before this invitation expires.
     */
    public Integer getTtlInSeconds() {
        return ttlInSeconds;
    }

    /**
     * Sets the number of seconds before this invitation expires.
     * If unspecified or set to 0, this value defaults to 604800 seconds (7 days). Max value: 2592000 seconds (30 days).
     *
     * @param ttlInSeconds the number of seconds before this invitation expires.
     */
    public void setTtlInSeconds(Integer ttlInSeconds) {
        this.ttlInSeconds = ttlInSeconds;
    }

    /**
     * @return the client ID of this invitation.
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * @return the connection ID of this invitation.
     */
    public String getConnectionId() {
        return connectionId;
    }

    /**
     * Sets the ID of the connection for the inviter to authenticate with.
     *
     * @param connectionId the ID of the connection.
     */
    public void setConnectionId(String connectionId) {
        this.connectionId = connectionId;
    }

    /**
     * @return the app metadata of this invitation.
     */
    public Map<String, Object> getAppMetadata() {
        return appMetadata;
    }

    /**
     * Sets the app metadata associated with this invitation.
     *
     * @param appMetadata the app metadata.
     */
    public void setAppMetadata(Map<String, Object> appMetadata) {
        this.appMetadata = appMetadata;
    }

    /**
     * @return the user metadata of this invitation.
     */
    public Map<String, Object> getUserMetadata() {
        return userMetadata;
    }

    /**
     * Sets the user metadata associated with this invitation.
     *
     * @param userMetadata the user metadata.
     */
    public void setUserMetadata(Map<String, Object> userMetadata) {
        this.userMetadata = userMetadata;
    }

    /**
     * @return whether the user will receive an invitation email (true) or no email (false).
     */
    public Boolean isSendInvitationEmail() {
        return sendInvitationEmail;
    }

    /**
     * Whether the user will receive an invitation email (true) or no email (false). If not specified, default value is true.
     *
     * @param sendInvitationEmail whether to send the user an invitation email or not.
     */
    public void setSendInvitationEmail(Boolean sendInvitationEmail) {
        this.sendInvitationEmail = sendInvitationEmail;
    }

    /**
     * @return the date this invitation was created at.
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * @return the date this invitation expires.
     */
    public Date getExpiresAt() {
        return expiresAt;
    }

    /**
     * @return the ID of the invitation ticket.
     */
    public String getTicketId() {
        return ticketId;
    }

    /**
     * @return the roles associated with the user invited.
     */
    public List<String> getRoles() {
        return roles;
    }

    /**
     * Sets the roles to be associated with the user invited.
     *
     * @param roles the {@linkplain Roles} to associated with the user invited.
     */
    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    /**
     * @return the invitation url to be send to the invitee.
     */
    public String getInvitationUrl() {
        return invitationUrl;
    }

    /**
     * @return the ID of the organization for this invitation.
     */
    public String getOrganizationId() {
        return organizationId;
    }
}

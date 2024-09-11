package com.auth0.json.mgmt.sessions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Session {
    @JsonProperty("id")
    private String id;
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("created_at")
    private Date createdAt;
    @JsonProperty("updated_at")
    private Date updatedAt;
    @JsonProperty("authenticated_at")
    private Date authenticatedAt;
    @JsonProperty("idle_expires_at")
    private Date idleExpiresAt;
    @JsonProperty("expires_at")
    private Date expiresAt;
    @JsonProperty("last_interacted_at")
    private Date lastInteractedAt;
    @JsonProperty("device")
    private Device device;
    @JsonProperty("clients")
    private List<Client> clients;
    @JsonProperty("authentication")
    private Authentication authentication;

    /**
     * @return The ID of the session
     */
    public String getId() {
        return id;
    }

    /**
     * @return ID of the user which can be used when interacting with other APIs.
     */
    public String getUserId() {
        return userId;
    }

    /**
     *
     * @return The date and time when the session was created
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * @return The date and time when the session was last updated
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * @return The date and time when the session was last authenticated
     */
    public Date getAuthenticatedAt() {
        return authenticatedAt;
    }

    /**
     * @return The date and time when the session will expire if idle
     */
    public Date getIdleExpiresAt() {
        return idleExpiresAt;
    }

    /**
     * @return The date and time when the session will expire
     */
    public Date getExpiresAt() {
        return expiresAt;
    }

    /**
     * @return Metadata related to the device used in the session
     */
    public Device getDevice() {
        return device;
    }

    /**
     * @return List of client details for the session
     */
    public List<Client> getClients() {
        return clients;
    }

    /**
     * @return Details about authentication signals obtained during the login flow
     */
    public Authentication getAuthentication() {
        return authentication;
    }
}

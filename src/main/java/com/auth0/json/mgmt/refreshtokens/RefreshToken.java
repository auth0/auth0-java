package com.auth0.json.mgmt.refreshtokens;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RefreshToken {
    @JsonProperty("id")
    private String id;
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("created_at")
    private Date createdAt;
    @JsonProperty("idle_expires_at")
    private Date idleExpiresAt;
    @JsonProperty("expires_at")
    private Date expiresAt;
    @JsonProperty("device")
    private Device device;
    @JsonProperty("client_id")
    private String clientId;
    @JsonProperty("session_id")
    private String sessionId;
    @JsonProperty("rotating")
    private Boolean rotating;
    @JsonProperty("resource_servers")
    private List<ResourceServer> resourceServers;
    @JsonProperty("last_exchanged_at")
    private Date lastExchangedAt;

    /**
     * @return The ID of the refresh token
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
     * @return The date and time when the refresh token was created
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     *
     * @return The date and time when the refresh token will expire if idle
     */
    public Date getIdleExpiresAt() {
        return idleExpiresAt;
    }

    /**
     *
     * @return The date and time when the refresh token will expire
     */
    public Date getExpiresAt() {
        return expiresAt;
    }

    /**
     * @return Device information
     */
    public Device getDevice() {
        return device;
    }

    /**
     * @return ID of the client application granted with this refresh token
     */
    public String getClientId() {
        return clientId;
    }

    /**
     *
     * @return ID of the authenticated session used to obtain this refresh-token
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * @return True if the token is a rotating refresh token
     */
    public Boolean isRotating() {
        return rotating;
    }

    /**
     * @return A list of the resource server IDs associated to this refresh-token and their granted scopes
     */
    public List<ResourceServer> getResourceServers() {
        return resourceServers;
    }

    /**
     * @return The date and time when the refresh token was last exchanged
     */
    public Date getLastExchangedAt() {
        return lastExchangedAt;
    }
}

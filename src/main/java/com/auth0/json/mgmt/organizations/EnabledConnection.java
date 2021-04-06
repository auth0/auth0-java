package com.auth0.json.mgmt.organizations;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the enabled connection object for an organization.
 * @see com.auth0.client.mgmt.OrganizationsEntity
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EnabledConnection {

    @JsonProperty("connection")
    private Connection connection;
    @JsonProperty("assign_membership_on_login")
    private boolean assignMembershipOnLogin;
    @JsonProperty("connection_id")
    private String connectionId;

    public EnabledConnection() {}

    /**
     * Create a new instance.
     *
     * @param connectionId the ID of the connection
     */
    public EnabledConnection(@JsonProperty("connection_id") String connectionId) {
        this.connectionId = connectionId;
    }

    /**
     * @return the value of the {@linkplain Connection}.
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Sets the value of the {@linkplain Connection}.
     *
     * @param connection the {@linkplain Connection} to set.
     */
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    /**
     * @return whether users will automatically granted membership in the organization when logging in.
     */
    public boolean isAssignMembershipOnLogin() {
        return assignMembershipOnLogin;
    }

    /**
     * Sets whether users will be automatically granted membership in the organization when logging in.
     *
     * @param assignMembershipOnLogin {@code true} to grant membership in the organization automatically when logging in, {@code false} to require membership to be granted prior to logging with this connection.
     */
    public void setAssignMembershipOnLogin(boolean assignMembershipOnLogin) {
        this.assignMembershipOnLogin = assignMembershipOnLogin;
    }

    /**
     * @return the value of the connection ID.
     */
    public String getConnectionId() {
        return connectionId;
    }

    /**
     * Sets the connection ID.
     *
     * @param connectionId the connection ID.
     */
    public void setConnectionId(String connectionId) {
        this.connectionId = connectionId;
    }
}

package com.auth0.json.mgmt.connections;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScimConfigurationResponse {
    @JsonProperty("connection_id")
    private String connectionId;
    @JsonProperty("connection_name")
    private String connectionName;
    @JsonProperty("strategy")
    private String strategy;
    @JsonProperty("tenant_name")
    private String tenantName;
    @JsonProperty("user_id_attribute")
    private String userIdAttribute;
    @JsonProperty("mapping")
    private List<Mapping> mapping;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("updated_on")
    private String updatedOn;

    /**
     * Getter for the connection id.
     * @return the connection id.
     */
    public String getConnectionId() {
        return connectionId;
    }

    /**
     * Setter for the connection id.
     * @param connectionId the connection id to set.
     */
    public void setConnectionId(String connectionId) {
        this.connectionId = connectionId;
    }

    /**
     * Getter for the connection name.
     * @return the connection name.
     */
    public String getConnectionName() {
        return connectionName;
    }

    /**
     * Setter for the connection name.
     * @param connectionName the connection name to set.
     */
    public void setConnectionName(String connectionName) {
        this.connectionName = connectionName;
    }

    /**
     * Getter for the strategy.
     * @return the strategy.
     */
    public String getStrategy() {
        return strategy;
    }

    /**
     * Setter for the strategy.
     * @param strategy the strategy to set.
     */
    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    /**
     * Getter for the tenant name.
     * @return the tenant name.
     */
    public String getTenantName() {
        return tenantName;
    }

    /**
     * Setter for the tenant name.
     * @param tenantName the tenant name to set.
     */
    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    /**
     * Getter for the user id attribute.
     * @return the user id attribute.
     */
    public String getUserIdAttribute() {
        return userIdAttribute;
    }

    /**
     * Setter for the user id attribute.
     * @param userIdAttribute the user id attribute to set.
     */
    public void setUserIdAttribute(String userIdAttribute) {
        this.userIdAttribute = userIdAttribute;
    }

    /**
     * Getter for the mapping.
     * @return the mapping.
     */
    public List<Mapping> getMapping() {
        return mapping;
    }

    /**
     * Setter for the mapping.
     * @param mapping the mapping to set.
     */
    public void setMapping(List<Mapping> mapping) {
        this.mapping = mapping;
    }

    /**
     * Getter for the created at.
     * @return the created at.
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * Setter for the created at.
     * @param createdAt the created at to set.
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Getter for the updated on.
     * @return the updated on.
     */
    public String getUpdatedOn() {
        return updatedOn;
    }

    /**
     * Setter for the updated on.
     * @param updatedOn the updated on to set.
     */
    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }
}

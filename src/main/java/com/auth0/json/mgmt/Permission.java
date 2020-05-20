package com.auth0.json.mgmt;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Class that represents an Auth0 Permission object. Related to the {@link com.auth0.client.mgmt.RolesEntity} entity.
 */
@SuppressWarnings("unused")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Permission {

    @JsonProperty("permission_name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("resource_server_identifier")
    private String resourceServerId;

    @JsonProperty("resource_server_name")
    private String resourceServerName;

    @JsonProperty("sources")
    private List<PermissionSource> sources;

    /**
     * Getter for the role's name.
     *
     * @return the name of the role.
     */
    @JsonProperty("permission_name")
    public String getName() {
        return name;
    }

    /**
     * Setter for the role's name.
     *
     * @param name the name of the role to set.
     */
    @JsonProperty("permission_name")
    public void setName(String name) {
        this.name = name;
    }

    /**
     * The getter for the role's description.
     *
     * @return the description of the role.
     */
    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    /**
     * Setter for the role's description.
     *
     * @param description the description of the role to set.
     */
    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter for the permissions's resource server identifier.
     *
     * @return the resource server identifier of the permission.
     */
    @JsonProperty("resource_server_identifier")
    public String getResourceServerId() {
        return resourceServerId;
    }

    /**
     * Setter for the permissions's resource server identifier.
     *
     * @param resourceServerId the resource server identifier of the permission to set.
     */
    @JsonProperty("resource_server_identifier")
    public void setResourceServerId(String resourceServerId) {
        this.resourceServerId = resourceServerId;
    }

    /**
     * Getter for the permissions's resource server name.
     *
     * @return the resource server name of the permission.
     */
    @JsonProperty("resource_server_name")
    public String getResourceServerName() {
        return resourceServerName;
    }

    /**
     * Setter for the permissions's resource server name.
     *
     * @param resourceServerName the resource server name of the permission to set.
     */
    @JsonProperty("resource_server_name")
    public void setResourceServerName(String resourceServerName) {
        this.resourceServerName = resourceServerName;
    }

    /**
     * Getter for the permission's sources.
     *
     * @return the permission sources.
     */
    @JsonProperty("sources")
    public List<PermissionSource> getSources() {
        return sources;
    }

}

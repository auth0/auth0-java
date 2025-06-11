package com.auth0.json.mgmt.networkacls;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NetworkAcls {
    @JsonProperty("description")
    private String description;
    @JsonProperty("active")
    private boolean active;
    @JsonProperty("priority")
    private int priority;
    @JsonProperty("id")
    private String id;
    @JsonProperty("rule")
    private Rule rule;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("updated_at")
    private String updatedAt;

    /**
     * Getter for the ID of the network ACL.
     * @return the ID of the network ACL.
     */
    public String getId() {
        return id;
    }

    /**
     * Getter for the creation timestamp of the network ACL.
     * @return the creation timestamp of the network ACL.
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * Getter for the last updated timestamp of the network ACL.
     * @return the last updated timestamp of the network ACL.
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Getter for the description of the network ACL.
     * @return the description of the network ACL.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter for the description of the network ACL.
     * @param description the description to set for the network ACL.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Checks if the network ACL is active.
     * @return true if the network ACL is active, false otherwise.
     */
    @JsonProperty("active")
    public boolean isActive() {
        return active;
    }

    /**
     * Setter for the active status of the network ACL.
     * @param active true to set the network ACL as active, false to set it as inactive.
     */
    @JsonProperty("active")
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Getter for the priority of the network ACL.
     * @return the priority of the network ACL.
     */
    public int getPriority() {
        return priority;
    }

    /**
     * Setter for the priority of the network ACL.
     * @param priority the priority to set for the network ACL.
     */
    public void setPriority(int priority) {
        this.priority = priority;
    }

    /**
     * Getter for the rule associated with the network ACL.
     * @return the rule of the network ACL.
     */
    public Rule getRule() {
        return rule;
    }

    /**
     * Setter for the rule associated with the network ACL.
     * @param rule the rule to set for the network ACL.
     */
    public void setRule(Rule rule) {
        this.rule = rule;
    }
}

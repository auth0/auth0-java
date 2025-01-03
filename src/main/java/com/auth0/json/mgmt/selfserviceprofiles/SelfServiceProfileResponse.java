package com.auth0.json.mgmt.selfserviceprofiles;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SelfServiceProfileResponse extends SelfServiceProfile {
    @JsonProperty("id")
    private String id;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("updated_at")
    private String updatedAt;

    /**
     * Getter for the id of the self-service profile.
     * @return the id of the self-service profile.
     */
    public String getId() {
        return id;
    }

    /**
     * Setter for the id of the self-service profile.
     * @param id the id of the self-service profile to set.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Getter for the created at of the self-service profile.
     * @return the created at of the self-service profile.
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * Setter for the created at of the self-service profile.
     * @param createdAt the created at of the self-service profile to set.
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Getter for the updated at of the self-service profile.
     * @return the updated at of the self-service profile.
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Setter for the updated at of the self-service profile.
     * @param updatedAt the updated at of the self-service profile to set.
     */
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}

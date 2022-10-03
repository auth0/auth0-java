package com.auth0.json.mgmt.attackprotection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Represents the Breached Password entity.
 *
 * @see com.auth0.client.mgmt.AttackProtectionEntity
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BreachedPassword {

    @JsonProperty("enabled")
    private Boolean enabled;
    @JsonProperty("method")
    private String method;
    @JsonProperty("shields")
    private List<String> shields;
    @JsonProperty("admin_notification_frequency")
    private List<String> adminNotificationFrequency;
    @JsonProperty("stage")
    private BreachedPasswordStage stage;

    /**
     * @return whether or not breached password detection is active.
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * Sets whether or not breached password detection is active.
     * @param enabled whether or not breached password detection is active.
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * @return the subscription level for breached password detection methods.
     */
    public String getMethod() {
        return method;
    }

    /**
     * Sets the subscription level for breached password detection methods.
     * @param method the subscription level for breached password detection methods.
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * @return the action to take when a breached password is detected.
     */
    public List<String> getShields() {
        return shields;
    }

    /**
     * Sets the action to take when a breached password is detected.
     * @param shields the action to take when a breached password is detected.
     */
    public void setShields(List<String> shields) {
        this.shields = shields;
    }

    /**
     * @return the frequency email notifications are sent.
     */
    public List<String> getAdminNotificationFrequency() {
        return adminNotificationFrequency;
    }

    /**
     * Sets the frequency email notifications are sent.
     * @param adminNotificationFrequency the frequency email notifications are sent.
     */
    public void setAdminNotificationFrequency(List<String> adminNotificationFrequency) {
        this.adminNotificationFrequency = adminNotificationFrequency;
    }

    /**
     * @return the per-stage configuration options
     */
    public BreachedPasswordStage getStage() {
        return stage;
    }

   /**
     * Sets the per-stage configuration options.
     * @param stage the per-stage configuration options.
     */
    public void setStage(BreachedPasswordStage stage) {
        this.stage = stage;
    }

}

package com.auth0.json.mgmt.attackprotection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Represents the Suspicious IP Throttling Configuration
 *
 * @see com.auth0.client.mgmt.AttackProtectionEntity
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SuspiciousIPThrottlingConfiguration {

    @JsonProperty("enabled")
    Boolean enabled;
    @JsonProperty("shields")
    private List<String> shields;
    @JsonProperty("allowlist")
    List<String> allowList;
    @JsonProperty("stage")
    Stage stage;

    /**
     * @return whether or not suspicious  IP throttling attack protections are active.
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * Sets whether or not suspicious  IP throttling attack protections are active.
     * @param enabled whether or not suspicious  IP throttling attack protections are active.
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * @return the action to take when a suspicious IP throttling threshold is violated.
     */
    public List<String> getShields() {
        return shields;
    }

    /**
     * Sets the action to take when a suspicious IP throttling threshold is violated.
     * @param shields the action to take when a suspicious IP throttling threshold is violated.
     */
    public void setShields(List<String> shields) {
        this.shields = shields;
    }

    /**
     * @return the per-stage configuration options.
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Sets the per-stage configuration options.
     * @param stage the per-stage configuration options.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * @return the list of trusted IP addresses that will not have attack protection enforced against them.
     */
    public List<String> getAllowList() {
        return allowList;
    }

    /**
     * Sets the list of trusted IP addresses that will not have attack protection enforced against them.
     * @param allowList the list of trusted IP addresses that will not have attack protection enforced against them.
     */
    public void setAllowList(List<String> allowList) {
        this.allowList = allowList;
    }
}

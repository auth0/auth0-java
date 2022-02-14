package com.auth0.json.mgmt.attackprotection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Represents the Brute Force Configuration
 *
 * @see com.auth0.client.mgmt.AttackProtectionEntity
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BruteForceConfiguration {

    @JsonProperty("enabled")
    Boolean enabled;
    @JsonProperty("shields")
    List<String> shields;
    @JsonProperty("allowlist")
    List<String> allowList;
    @JsonProperty("mode")
    String mode;
    @JsonProperty("max_attempts")
    Integer maxAttempts;

    /**
     * @return whether or not brute force protections are active.
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * Sets whether or not brute force protections are active.
     * @param enabled whether or not brute force protections are active.
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * @return the action to take when a brute force configuration threshold is violated.
     */
    public List<String> getShields() {
        return shields;
    }

    /**
     * Sets the action to take when a brute force configuration threshold is violated.
     * @param shields the action to take when a brute force configuration threshold is violated.
     */
    public void setShields(List<String> shields) {
        this.shields = shields;
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

    /**
     * @return gets the account lockout mode; determines whether or not IP address is used when counting failed attempts.
     */
    public String getMode() {
        return mode;
    }

    /**
     * Sets the account lockout mode; determines whether or not IP address is used when counting failed attempts.
     * @param mode the account lockout mode; determines whether or not IP address is used when counting failed attempts.
     */
    public void setMode(String mode) {
        this.mode = mode;
    }

    /**
     * @return the maximum number of unsuccessful attempts.
     */
    public Integer getMaxAttempts() {
        return maxAttempts;
    }

    /**
     * Sets the maximum number of unsuccessful attempts.
     * @param maxAttempts the maximum number of unsuccessful attempts.
     */
    public void setMaxAttempts(Integer maxAttempts) {
        this.maxAttempts = maxAttempts;
    }
}

package com.auth0.json.mgmt.attackprotection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the per-stage configuration options
 *
 * @see Stage
 * @see com.auth0.client.mgmt.AttackProtectionEntity
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StageEntry {
    @JsonProperty("max_attempts")
    Integer maxAttempts;
    @JsonProperty("rate")
    Integer rate;

    /**
     * @return the max attempts for this Stage entry
     */
    public Integer getMaxAttempts() {
        return maxAttempts;
    }

    /**
     * Set the max attempts for this Stage entry
     * @param maxAttempts the max attempts for this Stage entry
     */
    public void setMaxAttempts(Integer maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    /**
     * Get the rate for this Stage entry
     * @return the rate for this Stage entry
     */
    public Integer getRate() {
        return rate;
    }

    /**
     * Sets the rate for this Stage entry
     * @param rate the rate for this Stage entry
     */
    public void setRate(Integer rate) {
        this.rate = rate;
    }
}

package com.auth0.json.mgmt.attackprotection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Represents the per-stage configuration options for BreachedPasswordStage
 *
 * @see Stage
 * @see com.auth0.client.mgmt.AttackProtectionEntity
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BreachedPasswordStageEntry {
    @JsonProperty("shields")
    private List<String> shields;

    /**
     * Get the shields for this Stage entry
     * @return the shields for this Stage entry
     */
    public List<String> getShields() {
        return shields;
    }

    /**
     * Sets the shields for this Stage entry
     * @param shields the shields for this Stage entry
     */
    public void setShields(List<String> shields) {
        this.shields = shields;
    }
}

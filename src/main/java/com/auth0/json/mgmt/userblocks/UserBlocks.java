package com.auth0.json.mgmt.userblocks;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Class that represents an Auth0 User Block object. Related to the {@link com.auth0.client.mgmt.UserBlocksEntity} entity.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserBlocks {

    @JsonProperty("blocked_for")
    private List<BlockDetails> blockedFor;

    /**
     * Getter for the list of user block details.
     *
     * @return the list of block details.
     */
    @JsonProperty("blocked_for")
    public List<BlockDetails> getBlockedFor() {
        return blockedFor;
    }
}

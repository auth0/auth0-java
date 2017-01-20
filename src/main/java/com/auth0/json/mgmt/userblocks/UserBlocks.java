package com.auth0.json.mgmt.userblocks;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@SuppressWarnings("unused")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserBlocks {

    @JsonProperty("blocked_for")
    private List<BlockDetails> blockedFor;

    public List<BlockDetails> getBlockedFor() {
        return blockedFor;
    }
}

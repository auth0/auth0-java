package com.auth0.json.mgmt;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("unused")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BlockDetails {

    @JsonProperty("identifier")
    private String identifier;
    @JsonProperty("ip")
    private String ip;

    public String getIdentifier() {
        return identifier;
    }

    public String getIp() {
        return ip;
    }
}

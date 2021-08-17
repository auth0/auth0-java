package com.auth0.json.mgmt.actions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Error {

    // read-only
    @JsonProperty("id")
    private String id;

    // read-only
    @JsonProperty("msg")
    private String message;

    // read-only
    @JsonProperty("url")
    private String url;
}

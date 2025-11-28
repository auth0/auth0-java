package com.auth0.json.auth;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the response from a Pushed Authorization Request (PAR).
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PushedAuthorizationResponse {

    @JsonProperty("request_uri")
    private String requestURI;

    @JsonProperty("expires_in")
    private Integer expiresIn;

    @JsonCreator
    public PushedAuthorizationResponse(
            @JsonProperty("request_uri") String requestURI, @JsonProperty("expires_in") Integer expiresIn) {
        this.requestURI = requestURI;
        this.expiresIn = expiresIn;
    }

    public String getRequestURI() {
        return requestURI;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }
}

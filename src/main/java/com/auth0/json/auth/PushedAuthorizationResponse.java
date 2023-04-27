package com.auth0.json.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PushedAuthorizationResponse {

    @JsonProperty("request_uri")
    private String requestURI;
    @JsonProperty("expires_in")
    private Integer expiresIn;

    public PushedAuthorizationResponse(String requestURI, Integer expiresIn) {
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

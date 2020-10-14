package com.auth0.json.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PasswordlessEmailResponse {

    @JsonProperty("_id")
    private String id;
    @JsonProperty("email")
    private String email;
    @JsonProperty("email_verified")
    private Boolean emailVerified;

    @JsonProperty("_id")
    public String getId() {
        return id;
    }

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("email_verified")
    public Boolean isEmailVerified() {
        return emailVerified;
    }
}

package com.auth0.json.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the successful response when initiating the passwordless flow via email.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PasswordlessEmailResponse {

    @JsonProperty("_id")
    private String id;
    @JsonProperty("email")
    private String email;
    @JsonProperty("email_verified")
    private Boolean emailVerified;

    /**
     * The Identifier of this request.
     *
     * @return the identifier of this request.
     */
    @JsonProperty("_id")
    public String getId() {
        return id;
    }

    /**
     * Gets the email to which the code or link was sent to.
     *
     * @return the email to which the code or link was sent to.
     */
    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    /**
     * Whether the email address has been verified (true) or has not been verified (false).
     *
     * @return whether the email address has been verified (true) or has not been verified (false).
     */
    @JsonProperty("email_verified")
    public Boolean isEmailVerified() {
        return emailVerified;
    }
}

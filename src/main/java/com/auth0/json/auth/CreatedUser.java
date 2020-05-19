package com.auth0.json.auth;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class that holds the data of a newly created User. Obtained after a call to {@link com.auth0.client.auth.AuthAPI#signUp(String, char[], String)}
 * or {@link com.auth0.client.auth.AuthAPI#signUp(String, String, char[], String)}.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreatedUser {

    @JsonProperty("_id")
    @JsonAlias({"_id", "id", "user_id"})
    private String userId;
    @JsonProperty("email")
    private String email;
    @JsonProperty("username")
    private String username;
    @JsonProperty("email_verified")
    private Boolean emailVerified;

    @JsonProperty("_id")
    @JsonAlias({"_id", "id", "user_id"})
    public String getUserId() {
        return userId;
    }

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("username")
    public String getUsername() {
        return username;
    }

    @JsonProperty("email_verified")
    public Boolean isEmailVerified() {
        return emailVerified;
    }

}

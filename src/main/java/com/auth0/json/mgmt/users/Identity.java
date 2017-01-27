package com.auth0.json.mgmt.users;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings({"unused", "WeakerAccess"})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Identity {

    @JsonProperty("connection")
    private String connection;
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("provider")
    private String provider;
    @JsonProperty("isSocial")
    private Boolean isSocial;
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("profileData")
    private ProfileData profileData;

    @JsonProperty("connection")
    public String getConnection() {
        return connection;
    }

    @JsonProperty("user_id")
    public String getUserId() {
        return userId;
    }

    @JsonProperty("provider")
    public String getProvider() {
        return provider;
    }

    @JsonProperty("isSocial")
    public Boolean isSocial() {
        return isSocial;
    }

    @JsonProperty("access_token")
    public String getAccessToken() {
        return accessToken;
    }

    @JsonProperty("profileData")
    public ProfileData getProfileData() {
        return profileData;
    }
}
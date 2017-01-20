package com.auth0.json.mgmt.users;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("unused")
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

    public String getConnection() {
        return connection;
    }

    public String getUserId() {
        return userId;
    }

    public String getProvider() {
        return provider;
    }

    public Boolean getSocial() {
        return isSocial;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public ProfileData getProfileData() {
        return profileData;
    }
}
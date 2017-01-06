package com.auth0.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TokenHolder {

    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("id_token")
    private String idToken;
    @JsonProperty("refresh_token")
    private String refreshToken;
    @JsonProperty("token_type")
    private String tokenType;
    @JsonProperty("expires_in")
    private long expiresIn;

    public String getAccessToken() {
        return accessToken;
    }

    public String getIdToken() {
        return idToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public long getExpiresIn() {
        return expiresIn;
    }
}

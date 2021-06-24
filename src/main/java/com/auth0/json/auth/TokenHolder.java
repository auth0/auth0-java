package com.auth0.json.auth;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Class that contains the Tokens obtained after a call to the {@link com.auth0.client.auth.AuthAPI} methods.
 */
@SuppressWarnings("unused")
@JsonDeserialize(using = TokenHolderDeserializer.class)
public class TokenHolder {

    private String accessToken;
    private String idToken;
    private String refreshToken;
    private String tokenType;
    private long expiresIn;
    private String scope;

    public TokenHolder() {}

    public TokenHolder(String accessToken, String idToken, String refreshToken, String tokenType, long expiresIn, String scope) {
        this.accessToken = accessToken;
        this.idToken = idToken;
        this.refreshToken = refreshToken;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
        this.scope = scope;
    }

    /**
     * Getter for the Auth0's access token.
     *
     * @return the access token or null if missing.
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * Getter for the Auth0's id token.
     *
     * @return the id token or null if missing.
     */
    public String getIdToken() {
        return idToken;
    }

    /**
     * Getter for the Auth0's refresh token.
     *
     * @return the refresh token or null if missing.
     */
    public String getRefreshToken() {
        return refreshToken;
    }

    /**
     * Getter for the token type.
     *
     * @return the token type or null if missing.
     */
    public String getTokenType() {
        return tokenType;
    }

    /**
     * Getter for the duration of this token in seconds since it was issued.
     *
     * @return the number of seconds in which this token will expire, since the time it was issued.
     */
    public long getExpiresIn() {
        return expiresIn;
    }

    /**
     * Gets the granted scope value for this token.
     *
     * @return a space-delimited string of the granted scopes of this token.
     */
    public String getScope() {
        return scope;
    }
}

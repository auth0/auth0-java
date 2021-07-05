package com.auth0.json.auth;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Date;

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
    private Date expiresAt;

    // We need to maintain a default constructor for backwards-compatibility
    @SuppressWarnings("unused")
    public TokenHolder() {}

    public TokenHolder(String accessToken, String idToken, String refreshToken, String tokenType, long expiresIn, String scope, Date expiresAt) {
        this.accessToken = accessToken;
        this.idToken = idToken;
        this.refreshToken = refreshToken;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
        this.scope = scope;
        this.expiresAt = expiresAt;
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
     * Getter for the duration of the Access Token token in seconds from the time it was issued. If no Access Token was
     * present the value will be zero.
     *
     * @return the number of seconds in which the Access Token token will expire, from the time it was issued.
     */
    public long getExpiresIn() {
        return expiresIn;
    }

    /**
     * Get the expiration date of the Access Token. This value is <strong>not</strong> part of the actual token response from the
     * API, but rather is calculated and provided for convenience.
     *
     * @return the date of the Access Token's expiration. If no Access Token was present on the response, returns {@code null}
     */
    public Date getExpiresAt() {
        return expiresAt;
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

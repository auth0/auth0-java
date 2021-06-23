package com.auth0.client.mgmt.tokens;

import java.io.Serializable;
import java.time.Instant;

public class TokenStorageItem implements Serializable {

    // TODO version UUID if making Serializable - does it need to be?

    // TODO make immutable? Issue is how for custom implementations to serialize to JSON, for example, without default
    //  constructor and setters

//    private final String tokenResponse;
//    private final Instant expiresAt;

    private String tokenResponse;
    private Instant expiresAt;

//    public TokenStorageItem(String tokenResponse, Instant expiresAt) {
//        this.tokenResponse = tokenResponse;
//        this.expiresAt = expiresAt;
//    }

    public void setTokenResponse(String tokenResponse) {
        this.tokenResponse = tokenResponse;
    }

    public String getTokenResponse() {
        return this.tokenResponse;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    public Instant getExpiresAt() {
        return this.expiresAt;
    }
}

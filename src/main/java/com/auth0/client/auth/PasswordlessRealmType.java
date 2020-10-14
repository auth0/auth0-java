package com.auth0.client.auth;

/**
 * Represents the type of realm used for Passwordless authentication.
 */
public enum PasswordlessRealmType {

    /**
     * The email realm.
     */
    EMAIL("email"),

    /**
     * The sms realm.
     */
    SMS("sms");

    private final String realm;

    PasswordlessRealmType(String realm) {
        this.realm = realm;
    }

    /**
     * Gets the realm of Passwordless login request.
     *
     * @return the type of Passwordless login request.
     */
    public String getRealm() {
        return realm;
    }
}

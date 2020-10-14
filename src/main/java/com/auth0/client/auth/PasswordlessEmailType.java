package com.auth0.client.auth;

/**
 * Represents the type of the Passwordless email request.
 */
public enum PasswordlessEmailType {

    /**
     * Send a link.
     */
    LINK("link"),

    /**
     * Send a code.
     */
    CODE("code");

    private final String type;

    PasswordlessEmailType(String type) {
        this.type = type;
    }

    /**
     * Gets the type of Passwordless email request.
     *
     * @return the type of Passwordless email request.
     */
    public String getType() {
        return type;
    }
}

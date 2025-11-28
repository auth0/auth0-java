package com.auth0.client.auth;

/**
 * Responsible for creating a signed client assertion used to authenticate to the Authentication API
 *
 * @see <a href="https://openid.net/specs/openid-connect-core-1_0.html#ClientAuthentication">OpenID Connect Core Specification</a>
 */
public interface ClientAssertionSigner {

    /**
     * Creates a signed JWT representing a client assertion used to authenticate to the Authentication API.
     *
     * @param issuer the Issuer. This MUST contain the client_id of the OAuth Client.
     * @param audience the audience that identifies the Authorization Server as an intended audience.
     * @param subject the Subject. This MUST contain the client_id of the OAuth Client.
     *
     * @return a signed JWT representing the client assertion.
     */
    String createSignedClientAssertion(String issuer, String audience, String subject);
}

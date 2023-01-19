package com.auth0.client.auth;

public interface ClientAssertion {

    String createSignedClientAssertion(String issuer, String audience, String subject);
}

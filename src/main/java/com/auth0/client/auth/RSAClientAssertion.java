package com.auth0.client.auth;

import com.auth0.exception.ClientAssertionSigningException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.utils.Asserts;

import java.security.interfaces.RSAPrivateKey;
import java.time.Instant;
import java.util.UUID;

public class RSAClientAssertion implements ClientAssertion {

    private final RSAPrivateKey assertionSigningKey;
    private final RSASigningAlgorithm assertionSigningAlgorithm;


    public RSAClientAssertion(RSAPrivateKey assertionSigningKey, RSASigningAlgorithm assertionSigningAlgorithm) {
        Asserts.assertNotNull(assertionSigningKey, "assertion signing key");
        Asserts.assertNotNull(assertionSigningKey, "assertion signing algorithm");

        this.assertionSigningKey = assertionSigningKey;
        this.assertionSigningAlgorithm = assertionSigningAlgorithm;
    }

    public RSAClientAssertion(RSAPrivateKey assertionSigningKey) {
        this(assertionSigningKey, RSASigningAlgorithm.RSA256);
    }

    @Override
    public String createSignedClientAssertion(String issuer, String audience, String subject) {
        Instant now = Instant.now();
        JWTCreator.Builder builder = JWT.create()
            .withIssuer(issuer)
            .withAudience(audience)
            .withSubject(subject)
            .withIssuedAt(now)
            .withExpiresAt(now.plusSeconds(180))
            .withClaim("jti", UUID.randomUUID().toString());

        switch (assertionSigningAlgorithm) {
            case RSA256:
                try {
                    return builder.sign(Algorithm.RSA256(null, assertionSigningKey));
                } catch (JWTCreationException exception) {
                    throw new ClientAssertionSigningException("Error creating the JWT used for client assertion using the RSA256 signing algorithm", exception);
                }
            case RSA384:
                try {
                    return builder.sign(Algorithm.RSA384(null, assertionSigningKey));
                } catch (JWTCreationException exception) {
                    throw new ClientAssertionSigningException("Error creating the JWT used for client assertion using the RSA384 signing algorithm", exception);
                }
            default:
                throw new ClientAssertionSigningException("Error creating the JWT used for client assertion. Unknown algorithm.");
        }
    }

    public enum RSASigningAlgorithm {
        RSA256,
        RSA384
    }
}

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
import org.jetbrains.annotations.TestOnly;

/**
 * An implementation of {@linkplain ClientAssertionSigner} for RSA-signed client assertions.
 */
public class RSAClientAssertionSigner implements ClientAssertionSigner {

    private final RSAPrivateKey assertionSigningKey;
    private final RSASigningAlgorithm assertionSigningAlgorithm;

    /**
     * Creates a new instance.
     *
     * @param assertionSigningKey the private key used to sign the assertion. Must not be null.
     * @param assertionSigningAlgorithm The RSA algorithm used to sign the assertion. Must not be null.
     *
     * @see #RSAClientAssertionSigner(RSAPrivateKey)
     */
    public RSAClientAssertionSigner(RSAPrivateKey assertionSigningKey, RSASigningAlgorithm assertionSigningAlgorithm) {
        Asserts.assertNotNull(assertionSigningKey, "assertion signing key");
        Asserts.assertNotNull(assertionSigningAlgorithm, "assertion signing algorithm");

        this.assertionSigningKey = assertionSigningKey;
        this.assertionSigningAlgorithm = assertionSigningAlgorithm;
    }

    /**
     * Creates a new instance using the RSA256 signing algorithm.
     *
     * @param assertionSigningKey the private key used to sign the assertion. Must not be null.
     *
     * @see #RSAClientAssertionSigner(RSAPrivateKey, RSASigningAlgorithm)
     */
    public RSAClientAssertionSigner(RSAPrivateKey assertionSigningKey) {
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
                    throw new ClientAssertionSigningException(
                            "Error creating the JWT used for client assertion using the RSA256 signing algorithm",
                            exception);
                }
            case RSA384:
                try {
                    return builder.sign(Algorithm.RSA384(null, assertionSigningKey));
                } catch (JWTCreationException exception) {
                    throw new ClientAssertionSigningException(
                            "Error creating the JWT used for client assertion using the RSA384 signing algorithm",
                            exception);
                }
            default:
                throw new ClientAssertionSigningException(
                        "Error creating the JWT used for client assertion. Unknown algorithm.");
        }
    }

    /**
     * @return the assertion signing algorithm configured.
     */
    @TestOnly
    RSASigningAlgorithm getAssertionSigningAlgorithm() {
        return this.assertionSigningAlgorithm;
    }

    /**
     * Represents the RSA algorithms available to sign the client assertion.
     */
    public enum RSASigningAlgorithm {
        /**
         * The RSA 256 algorithm
         */
        RSA256,

        /**
         * The RSA 384 algorithm
         */
        RSA384
    }
}

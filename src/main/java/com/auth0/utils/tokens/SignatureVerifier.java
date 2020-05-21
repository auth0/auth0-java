package com.auth0.utils.tokens;

import com.auth0.exception.IdTokenValidationException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.auth0.utils.Asserts;

/**
 * Represents the verification for an ID Token's signature used when validating an ID token.
 */
public abstract class SignatureVerifier {

    private final Algorithm algorithm;

    /**
     * Get a {@code SignatureVerifier} for use when validating an ID token signed using the HS256 signing algorithm.
     *
     * @param secret the client's secret to use when validating the token's signature.
     * @return a {@code SignatureVerifier} for use with tokens signed using the HS256 signing algorithm.
     */
    public static SignatureVerifier forHS256(String secret) {
        return new HS256SignatureVerifier(secret);
    }

    /**
     * Get a {@code SignatureVerifier} for use when validating an ID token signed using the RS256 signing algorithm.
     * Callers should provide an implementation of the {@linkplain PublicKeyProvider} to provide the public key used
     * to verify the ID token's signature.
     *
     * @see PublicKeyProvider
     * @param publicKeyProvider an implementation of {@linkplain PublicKeyProvider} to get the public key.
     * @return a {@code SignatureVerifier} for use with tokens signed using the RS256 signing algorithm.
     */
    public static SignatureVerifier forRS256(PublicKeyProvider publicKeyProvider) {
        return new RS256SignatureVerifier(publicKeyProvider);
    }

    /**
     * Creates a new JWT Signature Verifier. Used by internal implementations to create concrete verifiers.
     *
     * @param algorithm the algorithm used to verify the signature. Must not be {@code null}.
     */
    SignatureVerifier(Algorithm algorithm) {
        Asserts.assertNotNull(algorithm, "algorithm");
        this.algorithm = algorithm;
    }

    /**
     * Verifies a token's signature.
     *
     * @param token the token for which to verify its signature.
     * @return a {@linkplain DecodedJWT} that represents the token.
     * @throws IdTokenValidationException if the signature verification failed.
     */
    DecodedJWT verifySignature(String token) throws IdTokenValidationException {
        DecodedJWT decoded = decodeToken(token);

        JWTVerifier verifier = JWT.require(algorithm)
                .ignoreIssuedAt()
                .build();

        try {
            verifier.verify(decoded);
        } catch (AlgorithmMismatchException algorithmMismatchException) {
            String message = String.format("Signature algorithm of \"%s\" is not supported. Expected the ID token to be signed with \"%s\"",
                    decoded.getAlgorithm(), this.algorithm.getName());
            throw new IdTokenValidationException(message, algorithmMismatchException);
        } catch (SignatureVerificationException signatureVerificationException) {
            throw new IdTokenValidationException("Invalid ID token signature", signatureVerificationException);
        } catch (JWTVerificationException ignored) {
            // no-op. Would only occur for expired tokens, which will be handle during claims validation
        }

        return decoded;
    }

    private DecodedJWT decodeToken(String token) throws IdTokenValidationException {
        try {
            return JWT.decode(token);
        } catch (JWTDecodeException e) {
            throw new IdTokenValidationException("ID token could not be decoded", e);
        }
    }
}

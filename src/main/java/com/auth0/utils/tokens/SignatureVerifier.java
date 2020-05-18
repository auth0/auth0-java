package com.auth0.utils.tokens;

import com.auth0.exception.IdTokenValidationException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.auth0.utils.Asserts;

import java.util.Arrays;
import java.util.List;

/**
 * Represents the verification for an ID Token's signature used when validating an ID token.
 */
public abstract class SignatureVerifier {

    private final JWTVerifier verifier;
    private final List<String> acceptedAlgorithms;

    /**
     * Get a {@code SignatureVerifier} for use when validating an ID token signed using the HS256 signing algorithm.
     *
     * @param secret - the client's secret to use when validating the token's signature.
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
     * Creates a new JWT Signature Verifier.
     * This instance will validate the token was signed using an expected algorithm
     * and then proceed to verify its signature
     *
     * @param verifier  the instance that knows how to verify the signature.
     * @param algorithm the accepted algorithms. Must not be null.
     */
    SignatureVerifier(JWTVerifier verifier, String... algorithm) {
        Asserts.assertNotEmpty(algorithm, "algorithm");
        this.verifier = verifier;
        this.acceptedAlgorithms = Arrays.asList(algorithm);
    }

    private DecodedJWT decodeToken(String token) throws IdTokenValidationException {
        try {
            return JWT.decode(token);
        } catch (JWTDecodeException e) {
            throw new IdTokenValidationException("ID token could not be decoded", e);
        }
    }

    DecodedJWT verifySignature(String token) throws IdTokenValidationException {
        DecodedJWT decoded = decodeToken(token);
        if (!this.acceptedAlgorithms.contains(decoded.getAlgorithm())) {
            throw new IdTokenValidationException(String.format("Signature algorithm of \"%s\" is not supported. Expected the ID token to be signed with \"%s\".", decoded.getAlgorithm(), this.acceptedAlgorithms));
        }
        if (verifier != null) {
            try {
                verifier.verify(decoded);
            } catch (SignatureVerificationException e) {
                throw new IdTokenValidationException("Invalid token signature", e);
            } catch (JWTVerificationException ignored) {
                // no-op. Would only occur for expired tokens, which will be handle during claims validation
            }
        }

        return decoded;
    }
}

package com.auth0.utils.tokens;

import com.auth0.exception.IdTokenValidationException;
import com.auth0.exception.PublicKeyProviderException;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.RSAKeyProvider;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * An implementation of {@code SignatureVerifier} for tokens signed with the RS256 asymmetric signing algorithm.
 */
class RS256SignatureVerifier extends SignatureVerifier {

    RS256SignatureVerifier(PublicKeyProvider publicKeyProvider) {
        super(getAlgorithm(publicKeyProvider));
    }

    private static Algorithm getAlgorithm(final PublicKeyProvider publicKeyProvider) {
        return Algorithm.RSA256(new RSAKeyProvider() {
            @Override
            public RSAPublicKey getPublicKeyById(String keyId) {
                try {
                    return publicKeyProvider.getPublicKeyById(keyId);
                } catch (PublicKeyProviderException pke) {
                    throw new IdTokenValidationException(String.format("Could not find a public key for Key ID (kid) \"%s\"", keyId), pke);
                }
            }

            @Override
            public RSAPrivateKey getPrivateKey() {
                // no-op
                return null;
            }

            @Override
            public String getPrivateKeyId() {
                // no-op
                return null;
            }
        });
    }
}

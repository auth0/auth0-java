package com.auth0.utils.tokens;

import com.auth0.jwt.algorithms.Algorithm;

/**
 * An implementation of {@code SignatureVerifier} for tokens signed with the HS256 symmetric signing algorithm.
 */
class HS256SignatureVerifier extends SignatureVerifier {

    HS256SignatureVerifier(String secret) {
        super(Algorithm.HMAC256(secret));
    }

}

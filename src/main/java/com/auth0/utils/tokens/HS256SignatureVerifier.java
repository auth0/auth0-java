package com.auth0.utils.tokens;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.JWTVerifier;

/**
 * An implementation of {@code SignatureVerifier} for tokens signed with the HS256 symmetric signing algorithm.
 */
class HS256SignatureVerifier extends SignatureVerifier {

    HS256SignatureVerifier(String secret) {
        super(createJWTVerifier(secret));
    }

    private static JWTVerifier createJWTVerifier(String secret) {
        Algorithm alg = Algorithm.HMAC256(secret);
        return JWT.require(alg)
                .ignoreIssuedAt()
                .build();
    }
}

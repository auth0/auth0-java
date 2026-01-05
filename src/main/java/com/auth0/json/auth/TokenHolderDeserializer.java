package com.auth0.json.auth;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;
import java.time.Instant;
import java.util.Date;

/**
 * A custom Jackson Deserializer for the {@linkplain TokenHolder} object. It deserializes the JSON response and calculates
 * an {@code expiresAt} date derived from the current time and the {@code expires_at} returned on the response.
 */
public class TokenHolderDeserializer extends StdDeserializer<TokenHolder> {

    @SuppressWarnings("unused")
    public TokenHolderDeserializer() {
        this(null);
    }

    public TokenHolderDeserializer(Class<?> clazz) {
        super(clazz);
    }

    @Override
    public TokenHolder deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        JsonNode node = p.getCodec().readTree(p);

        JsonNode accessTokenNode = node.get("access_token");
        JsonNode idTokenNode = node.get("id_token");
        JsonNode refreshTokenNode = node.get("refresh_token");
        JsonNode tokenTypeNode = node.get("token_type");
        JsonNode scopeNode = node.get("scope");
        JsonNode expiresInNode = node.get("expires_in");

        String accessToken = accessTokenNode != null ? accessTokenNode.asText() : null;
        String idToken = idTokenNode != null ? idTokenNode.asText() : null;
        String refreshToken = refreshTokenNode != null ? refreshTokenNode.asText() : null;
        String tokenType = tokenTypeNode != null ? tokenTypeNode.asText() : null;
        String scope = scopeNode != null ? scopeNode.asText() : null;

        // As a primitive, if expires_in is not sent on the response, value will be 0 instead of null
        long expiresIn = expiresInNode != null ? expiresInNode.asLong() : 0L;
        Date expiresAt = expiresInNode != null ? Date.from(Instant.now().plusSeconds(expiresIn)) : null;

        return new TokenHolder(accessToken, idToken, refreshToken, tokenType, expiresIn, scope, expiresAt);
    }
}

package com.auth0.utils.tokens;

import com.auth0.exception.IdTokenValidationException;
import com.auth0.exception.PublicKeyException;
import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkException;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.bouncycastle.util.io.pem.PemReader;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Scanner;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SignatureVerifierTest {

    private static final String EXPIRED_HS_JWT = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJub25jZSI6IjEyMzQiLCJpc3MiOiJodHRwczovL21lLmF1dGgwLmNvbS8iLCJhdWQiOiJkYU9nbkdzUlloa3d1NjIxdmYiLCJzdWIiOiJhdXRoMHx1c2VyMTIzIiwiZXhwIjo5NzE3ODkzMTd9.5_VOXBmOVMSi8OGgonyfyiJSq3A03PwOEuZlPD-Gxik";
    private static final String NONE_JWT = "eyJhbGciOiJub25lIiwidHlwIjoiSldUIn0.eyJub25jZSI6IjEyMzQiLCJpc3MiOiJodHRwczovL21lLmF1dGgwLmNvbS8iLCJhdWQiOiJkYU9nbkdzUlloa3d1NjIxdmYiLCJzdWIiOiJhdXRoMHx1c2VyMTIzIn0.";
    private static final String HS_JWT = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJub25jZSI6IjEyMzQiLCJpc3MiOiJodHRwczovL21lLmF1dGgwLmNvbS8iLCJhdWQiOiJkYU9nbkdzUlloa3d1NjIxdmYiLCJzdWIiOiJhdXRoMHx1c2VyMTIzIn0.a7ayNmFTxS2D-EIoUikoJ6dck7I8veWyxnje_mYD3qY";
    private static final String RS_JWT = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6ImFiYzEyMyJ9.eyJub25jZSI6IjEyMzQiLCJpc3MiOiJodHRwczovL21lLmF1dGgwLmNvbS8iLCJhdWQiOiJkYU9nbkdzUlloa3d1NjIxdmYiLCJzdWIiOiJhdXRoMHx1c2VyMTIzIn0.PkPWdoZNfXz8EB0SBPH83lNSOhyhdhdqYIgIwgY2nHozUnFOaUjVewlAXxP_3LBGibQ_ng4s5fEEOCJjaKBy04McryvOuL6nqb1dPQseeyxuv2zQitfrs-7kEtfeS3umywM-tV6guw9_W3nmIgaXOiYiF4WJM23ItbdCmvwdXLaf9-xHkQbRY_zEwEFbprFttKUXFbkPt6XjZ3zZwZbNZn64bx2PBiSJ2KMZAE3Lghmci-RXdhi7hXpmN30Tzze1ZsjvVeRRKNzShByKK9ZGZPmQ5yggJOXFy32ehjGkYwFMCqgMQomcGbcYhsd97huKHMHl3HOE5GDYjIq9o9oKRA";
    private static final String RS_PUBLIC_KEY = "src/test/resources/keys/public.pem";
    private static final String RS_PUBLIC_KEY_BAD = "src/test/resources/keys/bad-public.pem";

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void failsWhenAlgorithmIsNotExpected() {
        exception.expect(IdTokenValidationException.class);
        exception.expectMessage("Signature algorithm of \"none\" is not supported. Expected the ID token to be signed with \"[HS256]\".");

        SignatureVerifier verifier = SignatureVerifier.forHS256("secret");
        verifier.verifySignature(NONE_JWT);
    }

    @Test
    public void failsWhenTokenCannotBeDecoded() {
        exception.expect(IdTokenValidationException.class);
        exception.expectMessage("ID token could not be decoded");

        SignatureVerifier verifier = SignatureVerifier.forHS256("secret");
        verifier.verifySignature("boom");
    }

    @Test
    public void failsWhenAlgorithmRS256IsNotExpected() {
        exception.expect(IdTokenValidationException.class);
        exception.expectMessage("Signature algorithm of \"RS256\" is not supported. Expected the ID token to be signed with \"[HS256]\".");

        SignatureVerifier verifier = SignatureVerifier.forHS256("secret");
        verifier.verifySignature(RS_JWT);
    }

    @Test
    public void failsWhenAlgorithmHS256IsNotExpected() throws Exception {
        exception.expect(IdTokenValidationException.class);
        exception.expectMessage("Signature algorithm of \"HS256\" is not supported. Expected the ID token to be signed with \"[RS256]\".");

        SignatureVerifier verifier = SignatureVerifier.forRS256(getRSProvider(RS_PUBLIC_KEY));
        verifier.verifySignature(HS_JWT);
    }

    @Test
    public void succeedsWithValidSignatureHS256Token() {
        SignatureVerifier verifier = SignatureVerifier.forHS256("secret");
        DecodedJWT decodedJWT = verifier.verifySignature(HS_JWT);

        assertThat(decodedJWT, notNullValue());
    }

    @Test
    public void succeedsAndIgnoresExpiredTokenException() {
        SignatureVerifier verifier = SignatureVerifier.forHS256("secret");
        DecodedJWT decodedJWT = verifier.verifySignature(EXPIRED_HS_JWT);

        assertThat(decodedJWT, notNullValue());
    }

    @Test
    public void failsWithInvalidSignatureHS256Token() {
        exception.expect(IdTokenValidationException.class);
        exception.expectMessage("Invalid token signature");

        SignatureVerifier verifier = SignatureVerifier.forHS256("badsecret");
        verifier.verifySignature(HS_JWT);
    }

    @Test
    public void succeedsWithValidSignatureRS256Token() throws Exception {
        SignatureVerifier verifier = SignatureVerifier.forRS256(getRSProvider(RS_PUBLIC_KEY));
        DecodedJWT decodedJWT = verifier.verifySignature(RS_JWT);

        assertThat(decodedJWT, notNullValue());
    }

    @Test
    public void failsWithInvalidSignatureRS256Token() throws Exception {
        exception.expect(IdTokenValidationException.class);
        exception.expectMessage("Invalid token signature");
        SignatureVerifier verifier = SignatureVerifier.forRS256(getRSProvider(RS_PUBLIC_KEY_BAD));
        DecodedJWT decodedJWT = verifier.verifySignature(RS_JWT);

        assertThat(decodedJWT, notNullValue());
    }

    @Test
    public void failsWhenErrorGettingPublicKey() throws Exception {
        JwkProvider  jwkProvider = mock(JwkProvider.class);
        when(jwkProvider.get("abc123")).thenThrow(JwkException.class);

        exception.expect(IdTokenValidationException.class);
        exception.expectMessage("Error retrieving public key");
        SignatureVerifier verifier = SignatureVerifier.forRS256(new PublicKeyProvider() {
            @Override
            public RSAPublicKey getPublicKeyById(String keyId) throws PublicKeyException {
                try {
                    return (RSAPublicKey) jwkProvider.get(keyId);
                } catch (JwkException jwke) {
                    throw new PublicKeyException(jwke);
                }
            }
        });
        verifier.verifySignature(RS_JWT);
    }

    @Test
    public void skipsVerificationWhenVerifierIsNull() {
        SignatureVerifier signatureVerifier = new NullVerifier();
        DecodedJWT decoded1 = signatureVerifier.verifySignature(HS_JWT);
        DecodedJWT decoded2 = signatureVerifier.verifySignature(RS_JWT);

        assertThat(decoded1, notNullValue());
        assertThat(decoded2, notNullValue());
    }

    private PublicKeyProvider getRSProvider(String rsaPath) throws Exception {
        JwkProvider jwkProvider = mock(JwkProvider.class);
        Jwk jwk = mock(Jwk.class);
        when(jwkProvider.get("abc123")).thenReturn(jwk);
        RSAPublicKey key = readPublicKeyFromFile(rsaPath);
        when(jwk.getPublicKey()).thenReturn(key);
        return new PublicKeyProvider() {
            @Override
            public RSAPublicKey getPublicKeyById(String keyId) throws PublicKeyException {
                try {
                    return (RSAPublicKey) jwk.getPublicKey();
                } catch (JwkException jwke) {
                    throw new PublicKeyException(jwke);
                }
            };
        };
    }

    private static RSAPublicKey readPublicKeyFromFile(final String path) throws IOException {
        Scanner scanner = null;
        PemReader pemReader = null;
        try {
            scanner = new Scanner(Paths.get(path));
            if (scanner.hasNextLine() && scanner.nextLine().startsWith("-----BEGIN CERTIFICATE-----")) {
                FileInputStream fs = new FileInputStream(path);
                CertificateFactory fact = CertificateFactory.getInstance("X.509");
                X509Certificate cer = (X509Certificate) fact.generateCertificate(fs);
                PublicKey key = cer.getPublicKey();
                fs.close();
                return (RSAPublicKey) key;
            } else {
                pemReader = new PemReader(new FileReader(path));
                byte[] keyBytes = pemReader.readPemObject().getContent();
                KeyFactory kf = KeyFactory.getInstance("RSA");
                EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
                return (RSAPublicKey) kf.generatePublic(keySpec);
            }
        } catch (Exception e) {
            throw new IOException("Couldn't parse the RSA Public Key / Certificate file.", e);
        } finally {
            if (scanner != null) {
                scanner.close();
            }
            if (pemReader != null) {
                pemReader.close();
            }
        }
    }

    private static class NullVerifier extends SignatureVerifier {
        NullVerifier() {
            super(null, "HS256", "RS256");
        }
    }

}
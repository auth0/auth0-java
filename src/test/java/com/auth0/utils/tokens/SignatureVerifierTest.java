package com.auth0.utils.tokens;

import com.auth0.exception.IdTokenValidationException;
import com.auth0.exception.PublicKeyProviderException;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.bouncycastle.util.io.pem.PemReader;
import org.junit.jupiter.api.Test;

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

import static com.auth0.AssertsUtil.verifyThrows;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class SignatureVerifierTest {

    private static final String EXPIRED_HS_JWT = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJub25jZSI6IjEyMzQiLCJpc3MiOiJodHRwczovL21lLmF1dGgwLmNvbS8iLCJhdWQiOiJkYU9nbkdzUlloa3d1NjIxdmYiLCJzdWIiOiJhdXRoMHx1c2VyMTIzIiwiZXhwIjo5NzE3ODkzMTd9.5_VOXBmOVMSi8OGgonyfyiJSq3A03PwOEuZlPD-Gxik";
    private static final String NONE_JWT = "eyJhbGciOiJub25lIiwidHlwIjoiSldUIn0.eyJub25jZSI6IjEyMzQiLCJpc3MiOiJodHRwczovL21lLmF1dGgwLmNvbS8iLCJhdWQiOiJkYU9nbkdzUlloa3d1NjIxdmYiLCJzdWIiOiJhdXRoMHx1c2VyMTIzIn0.";
    private static final String HS_JWT = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJub25jZSI6IjEyMzQiLCJpc3MiOiJodHRwczovL21lLmF1dGgwLmNvbS8iLCJhdWQiOiJkYU9nbkdzUlloa3d1NjIxdmYiLCJzdWIiOiJhdXRoMHx1c2VyMTIzIn0.a7ayNmFTxS2D-EIoUikoJ6dck7I8veWyxnje_mYD3qY";
    private static final String RS_JWT = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6ImFiYzEyMyJ9.eyJub25jZSI6IjEyMzQiLCJpc3MiOiJodHRwczovL21lLmF1dGgwLmNvbS8iLCJhdWQiOiJkYU9nbkdzUlloa3d1NjIxdmYiLCJzdWIiOiJhdXRoMHx1c2VyMTIzIn0.PkPWdoZNfXz8EB0SBPH83lNSOhyhdhdqYIgIwgY2nHozUnFOaUjVewlAXxP_3LBGibQ_ng4s5fEEOCJjaKBy04McryvOuL6nqb1dPQseeyxuv2zQitfrs-7kEtfeS3umywM-tV6guw9_W3nmIgaXOiYiF4WJM23ItbdCmvwdXLaf9-xHkQbRY_zEwEFbprFttKUXFbkPt6XjZ3zZwZbNZn64bx2PBiSJ2KMZAE3Lghmci-RXdhi7hXpmN30Tzze1ZsjvVeRRKNzShByKK9ZGZPmQ5yggJOXFy32ehjGkYwFMCqgMQomcGbcYhsd97huKHMHl3HOE5GDYjIq9o9oKRA";
    private static final String RS_PUBLIC_KEY = "src/test/resources/keys/public-rsa.pem";
    private static final String RS_PUBLIC_KEY_BAD = "src/test/resources/keys/bad-public-rsa.pem";

    @Test
    public void failsWhenAlgorithmIsNotExpected() {
        verifyThrows(IdTokenValidationException.class,
            () -> SignatureVerifier.forHS256("secret")
                .verifySignature(NONE_JWT),
            "Signature algorithm of \"none\" is not supported. Expected the ID token to be signed with \"HS256\"");
    }

    @Test
    public void failsWhenTokenCannotBeDecoded() {
        verifyThrows(IdTokenValidationException.class,
            () -> SignatureVerifier.forHS256("secret")
                .verifySignature("boom"),
            "ID token could not be decoded");
    }

    @Test
    public void failsWhenAlgorithmRS256IsNotExpected() {
        verifyThrows(IdTokenValidationException.class,
            () -> SignatureVerifier.forHS256("secret")
                .verifySignature(RS_JWT),
            "Signature algorithm of \"RS256\" is not supported. Expected the ID token to be signed with \"HS256\"");
    }

    @Test
    public void failsWhenAlgorithmHS256IsNotExpected() {
        IdTokenValidationException e = verifyThrows(IdTokenValidationException.class,
            () -> SignatureVerifier.forHS256("secret")
                .verifySignature(RS_JWT),
            "Signature algorithm of \"RS256\" is not supported. Expected the ID token to be signed with \"HS256\"");
        assertThat(e.getCause(), instanceOf(AlgorithmMismatchException.class));
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
        verifyThrows(IdTokenValidationException.class,
            () -> SignatureVerifier.forHS256("badsecret")
                .verifySignature(HS_JWT),
            "Invalid ID token signature");
    }

    @Test
    public void succeedsWithValidSignatureRS256Token() {
        SignatureVerifier verifier = SignatureVerifier.forRS256(getRSProvider(RS_PUBLIC_KEY));
        DecodedJWT decodedJWT = verifier.verifySignature(RS_JWT);

        assertThat(decodedJWT, notNullValue());
    }

    @Test
    public void failsWithInvalidSignatureRS256Token() {
        verifyThrows(IdTokenValidationException.class,
            () -> SignatureVerifier
                    .forRS256(getRSProvider(RS_PUBLIC_KEY_BAD))
                    .verifySignature(RS_JWT),
            "Invalid ID token signature");
    }

    @Test
    public void failsWhenErrorGettingPublicKey() {
        verifyThrows(IdTokenValidationException.class,
            () -> {
                SignatureVerifier.forRS256(new PublicKeyProvider() {
                    @Override
                    public RSAPublicKey getPublicKeyById(String keyId) throws PublicKeyProviderException {
                        throw new PublicKeyProviderException("error");
                    }
                }).verifySignature(RS_JWT);
            },
            "Could not find a public key for Key ID (kid) \"abc123\"");
    }

    @Test
    public void failsWithNullVerifier() {
        verifyThrows(IllegalArgumentException.class,
            NullVerifier::new,
            "'algorithm' cannot be null!");
    }

    private PublicKeyProvider getRSProvider(String rsaPath) {
        return new PublicKeyProvider() {
            @Override
            public RSAPublicKey getPublicKeyById(String keyId) throws PublicKeyProviderException {
                try {
                    return readPublicKeyFromFile(rsaPath);
                } catch (IOException ioe) {
                    throw new PublicKeyProviderException("Error reading public key", ioe);
                }
            }
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
            super(null);
        }
    }
}

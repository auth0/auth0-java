package com.auth0.client.auth;

import com.auth0.AssertsUtil;
import com.auth0.exception.ClientAssertionSigningException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import static com.auth0.AssertsUtil.verifyThrows;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static com.auth0.AssertsUtil.verifyThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RSAClientAssertionSignerTest {

    private static final String PRIVATE_KEY_FILE_RSA = "src/test/resources/auth/rsa_private_key.pem";
    private static final String PUBLIC_KEY_FILE_RSA = "src/test/resources/auth/rsa_public_key.pem";

    @Test
    public void defaultsToRS256() {
        RSAPrivateKey privateKey = mock(RSAPrivateKey.class);
        RSAClientAssertionSigner rsa = new RSAClientAssertionSigner(privateKey);
        assertThat(rsa.getAssertionSigningAlgorithm(), is(RSAClientAssertionSigner.RSASigningAlgorithm.RSA256));
    }

    @Test
    public void throwsOnNullSigningKey() {
        AssertsUtil.verifyThrows(IllegalArgumentException.class,
            () -> new RSAClientAssertionSigner(null),
            "'assertion signing key' cannot be null!");
    }

    @Test
    public void throwsOnNullSigningAlgorithm() {
        RSAPrivateKey privateKey = mock(RSAPrivateKey.class);
        verifyThrows(IllegalArgumentException.class,
            () -> new RSAClientAssertionSigner(privateKey, null),
            "'assertion signing algorithm' cannot be null!");
    }

    @Test
    public void throwsWhenErrorSigning256() {
        JWTCreator.Builder mockBuilder = mock(JWTCreator.Builder.class);
        RSAPrivateKey mockPrivateKey = mock(RSAPrivateKey.class);

        when(mockBuilder.sign(Algorithm.RSA256(null,mockPrivateKey))).thenThrow(JWTCreationException.class);

        ClientAssertionSigningException e = verifyThrows(ClientAssertionSigningException.class,
            () -> new RSAClientAssertionSigner(mockPrivateKey).createSignedClientAssertion("iss", "aud", "sub"),
            "Error creating the JWT used for client assertion using the RSA256 signing algorithm");

        assertThat(e.getCause(), is(instanceOf(JWTCreationException.class)));
    }

    @Test
    public void throwsWhenErrorSigning384() {
        JWTCreator.Builder mockBuilder = mock(JWTCreator.Builder.class);
        RSAPrivateKey mockPrivateKey = mock(RSAPrivateKey.class);

        when(mockBuilder.sign(Algorithm.RSA384(null,mockPrivateKey))).thenThrow(JWTCreationException.class);

        ClientAssertionSigningException e = verifyThrows(ClientAssertionSigningException.class,
            () -> new RSAClientAssertionSigner(mockPrivateKey, RSAClientAssertionSigner.RSASigningAlgorithm.RSA384).createSignedClientAssertion("iss", "aud", "sub"),
            "Error creating the JWT used for client assertion using the RSA384 signing algorithm");

        assertThat(e.getCause(), is(instanceOf(JWTCreationException.class)));
    }

    @Test
    public void createsVerifiedRSA256SigningAssertion() throws Exception {
        KeyPair keyPair = getKeyPair();

        RSAClientAssertionSigner clientAssertion = new RSAClientAssertionSigner((RSAPrivateKey) keyPair.getPrivate());
        String jwt = clientAssertion.createSignedClientAssertion("issuer", "audience", "subject");

        DecodedJWT decodedJWT = JWT.require(Algorithm.RSA256((RSAPublicKey) keyPair.getPublic(), null))
            .build()
            .verify(jwt);

        assertThat(decodedJWT.getSubject(), is("subject"));
        assertThat(decodedJWT.getAudience(), hasItem("audience"));
        assertThat(decodedJWT.getIssuer(), is("issuer"));
        assertThat(decodedJWT.getExpiresAtAsInstant(), is(decodedJWT.getIssuedAtAsInstant().plusSeconds(180)));
        assertThat(decodedJWT.getClaim("jti").asString(), is(notNullValue()));


        System.out.println(decodedJWT);
    }

    @Test
    public void createsVerifiedRSA384SigningAssertion() throws Exception {
        KeyPair keyPair = getKeyPair();

        RSAClientAssertionSigner clientAssertion = new RSAClientAssertionSigner((RSAPrivateKey) keyPair.getPrivate(), RSAClientAssertionSigner.RSASigningAlgorithm.RSA384);
        String jwt = clientAssertion.createSignedClientAssertion("issuer", "audience", "subject");

        DecodedJWT decodedJWT = JWT.require(Algorithm.RSA384((RSAPublicKey) keyPair.getPublic(), null))
            .build()
            .verify(jwt);

        assertThat(decodedJWT.getSubject(), is("subject"));
        assertThat(decodedJWT.getAudience(), hasItem("audience"));
        assertThat(decodedJWT.getIssuer(), is("issuer"));
        assertThat(decodedJWT.getExpiresAtAsInstant(), is(decodedJWT.getIssuedAtAsInstant().plusSeconds(180)));
        assertThat(decodedJWT.getClaim("jti").asString(), is(notNullValue()));


        System.out.println(decodedJWT);
    }

    private KeyPair getKeyPair() throws Exception {
        URI fileUri = new File(PRIVATE_KEY_FILE_RSA).toURI();
        String privateKeyContent = new String(Files.readAllBytes(Paths.get(fileUri)));
        privateKeyContent = privateKeyContent.replaceAll("\\n", "").replace("-----BEGIN PRIVATE KEY-----", "").replace("-----END PRIVATE KEY-----", "");

        KeyFactory kf = KeyFactory.getInstance("RSA");

        PKCS8EncodedKeySpec keySpecPKCS8 = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyContent));
        PrivateKey privateKey = kf.generatePrivate(keySpecPKCS8);

        URI publicKeyFileUri = new File(PUBLIC_KEY_FILE_RSA).toURI();
        String publicKeyContent = new String(Files.readAllBytes(Paths.get(publicKeyFileUri)));
        publicKeyContent = publicKeyContent.replaceAll("\\n", "").replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "");

        byte[] encodedPublicKey = Base64.getDecoder().decode(publicKeyContent);

        EncodedKeySpec keySpec = new X509EncodedKeySpec(encodedPublicKey);
        PublicKey publicKey = kf.generatePublic(keySpec);

        return new KeyPair(publicKey, privateKey);
    }
}

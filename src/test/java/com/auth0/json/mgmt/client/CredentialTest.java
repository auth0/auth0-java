package com.auth0.json.mgmt.client;

import com.auth0.json.JsonMatcher;
import com.auth0.json.JsonTest;
import org.junit.Test;

import java.time.Instant;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class CredentialTest extends JsonTest<Credential> {

    private final static String JSON = "{\n" +
        "  \"id\": \"cred_asqwXXXIIadqq2T3zAXPwv\",\n" +
        "  \"name\": \"new cred\",\n" +
        "  \"credential_type\": \"public_key\",\n" +
        "  \"kid\": \"eKtBStP7BN2NiaoVrNGzb0QaRl4HlzyH1Lp00JB6Xj0\",\n" +
        "  \"alg\": \"RS256\",\n" +
        "  \"thumbprint\": \"thumb\",\n" +
        "  \"created_at\": \"2023-04-13T16:18:01.481Z\",\n" +
        "  \"updated_at\": \"2023-04-13T16:18:01.481Z\",\n" +
        "  \"expires_at\": \"2023-04-13T16:19:00.349Z\"\n" +
        "}\n";

    @Test
    public void shouldDeserialize() throws Exception {
        Credential credential = fromJSON(JSON, Credential.class);

        assertThat(credential, is(notNullValue()));
        assertThat(credential.getAlg(), is("RS256"));
        assertThat(credential.getCredentialType(), is("public_key"));
        assertThat(credential.getExpiresAt(), is(Date.from(Instant.parse("2023-04-13T16:19:00.349Z"))));
        assertThat(credential.getUpdatedAt(), is(Date.from(Instant.parse("2023-04-13T16:18:01.481Z"))));
        assertThat(credential.getCreatedAt(), is(Date.from(Instant.parse("2023-04-13T16:18:01.481Z"))));
        assertThat(credential.getKid(), is("eKtBStP7BN2NiaoVrNGzb0QaRl4HlzyH1Lp00JB6Xj0"));
        assertThat(credential.getId(), is("cred_asqwXXXIIadqq2T3zAXPwv"));
        assertThat(credential.getThumbprint(), is("thumb"));
    }

    @Test
    public void shouldSerialize() throws Exception {
        Credential credential = new Credential("public_key", "pem");
        credential.setAlg("alg");
        credential.setName("name");
        credential.setParseExpiryFromCert(true);
        credential.setExpiresAt(new Date());

        String json = toJSON(credential);
        assertThat(json, is(notNullValue()));

        assertThat(json, JsonMatcher.hasEntry("name", "name"));
        assertThat(json, JsonMatcher.hasEntry("alg", "alg"));
        assertThat(json, JsonMatcher.hasEntry("parse_expiry_from_cert", true));
        assertThat(json, JsonMatcher.hasEntry("pem", "pem"));
        assertThat(json, JsonMatcher.hasEntry("credential_type", "public_key"));
        assertThat(json, JsonMatcher.hasEntry("expires_at", is(notNullValue())));
    }
}

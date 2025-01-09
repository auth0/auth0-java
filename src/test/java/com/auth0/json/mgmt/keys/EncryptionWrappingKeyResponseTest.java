package com.auth0.json.mgmt.keys;

import com.auth0.json.JsonMatcher;
import com.auth0.json.JsonTest;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class EncryptionWrappingKeyResponseTest extends JsonTest<EncryptionWrappingKeyResponse> {
    private static final String json = "{\n" +
        "  \"public_key\": \"pkey\",\n" +
        "  \"algorithm\": \"alg\"\n" +
        "}";

    private static final String readOnlyJson = "{\n" +
        "  \"public_key\": \"pkey\"\n" +
        "}";

    @Test
    public void shouldDeserialize() throws Exception {
        EncryptionWrappingKeyResponse response = fromJSON(json, EncryptionWrappingKeyResponse.class);

        assertThat(response, is(notNullValue()));
        assertThat(response.getPublicKey(), is("pkey"));
        assertThat(response.getAlgorithm(), is("alg"));
    }

    @Test
    public void shouldSerialize() throws Exception {
        EncryptionWrappingKeyResponse response = new EncryptionWrappingKeyResponse();
        response.setPublicKey("pkey");
        response.setAlgorithm("alg");

        String serialized = toJSON(response);
        assertThat(serialized, is(notNullValue()));
        assertThat(serialized, JsonMatcher.hasEntry("public_key", "pkey"));
        assertThat(serialized, JsonMatcher.hasEntry("algorithm", "alg"));
    }

    @Test
    public void shouldIncludeReadOnlyValuesOnDeserialize() throws Exception {
        EncryptionWrappingKeyResponse response = fromJSON(readOnlyJson, EncryptionWrappingKeyResponse.class);

        assertThat(response, is(notNullValue()));
        assertThat(response.getPublicKey(), is("pkey"));
    }
}

package com.auth0.json.mgmt.keys;

import com.auth0.json.JsonMatcher;
import com.auth0.json.JsonTest;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class EncryptionKeyTest extends JsonTest<EncryptionKey> {

    private static final String json = "{\n" +
        "  \"kid\": \"kid\",\n" +
        "  \"type\": \"tenant-master-key\",\n" +
        "  \"state\": \"active\",\n" +
        "  \"created_at\": \"2024-10-16T10:50:44.107Z\",\n" +
        "  \"updated_at\": \"2024-10-24T06:47:24.899Z\",\n" +
        "  \"parent_kid\": \"pkid\",\n" +
        "  \"public_key\": \"pkey\"\n" +
        "}";

    private static final String readOnlyJson = "{\n" +
        "  \"kid\": \"kid\"\n" +
        "}";

    @Test
    public void shouldDeserialize() throws Exception {
        EncryptionKey key = fromJSON(json, EncryptionKey.class);

        assertThat(key, is(notNullValue()));
        assertThat(key.getKid(), is("kid"));
        assertThat(key.getType(), is("tenant-master-key"));
        assertThat(key.getState(), is("active"));
        assertThat(key.getCreatedAt(), is("2024-10-16T10:50:44.107Z"));
        assertThat(key.getUpdatedAt(), is("2024-10-24T06:47:24.899Z"));
        assertThat(key.getParentKid(), is("pkid"));
        assertThat(key.getPublicKey(), is("pkey"));
    }

    @Test
    public void shouldSerialize() throws Exception {
        EncryptionKey key = new EncryptionKey();
        key.setKid("kid");
        key.setType("tenant-master-key");
        key.setState("active");
        key.setCreatedAt("2024-10-16T10:50:44.107Z");
        key.setUpdatedAt("2024-10-24T06:47:24.899Z");
        key.setParentKid("pkid");
        key.setPublicKey("pkey");

        String serialized = toJSON(key);
        assertThat(serialized, is(notNullValue()));
        assertThat(serialized, JsonMatcher.hasEntry("kid", "kid"));
        assertThat(serialized, JsonMatcher.hasEntry("type", "tenant-master-key"));
        assertThat(serialized, JsonMatcher.hasEntry("state", "active"));
        assertThat(serialized, JsonMatcher.hasEntry("created_at", "2024-10-16T10:50:44.107Z"));
        assertThat(serialized, JsonMatcher.hasEntry("updated_at", "2024-10-24T06:47:24.899Z"));
        assertThat(serialized, JsonMatcher.hasEntry("parent_kid", "pkid"));
        assertThat(serialized, JsonMatcher.hasEntry("public_key", "pkey"));
    }

    @Test
    public void shouldIncludeReadOnlyValuesOnDeserialize() throws Exception {
        EncryptionKey key = fromJSON(readOnlyJson, EncryptionKey.class);

        assertThat(key, is(notNullValue()));
        assertThat(key.getKid(), is("kid"));
    }
}

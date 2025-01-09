package com.auth0.json.mgmt.keys;

import com.auth0.json.JsonTest;
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class EncryptionKeysPageTest extends JsonTest<EncryptionKeysPage> {

    private static final String jsonWithTotal = "{\n" +
        "  \"start\": 0,\n" +
        "  \"total\": 20,\n" +
        "  \"limit\": 50,\n" +
        "  \"keys\": [\n" +
        "    {\n" +
        "      \"kid\": \"kid-1\",\n" +
        "      \"type\": \"customer-provided-root-key\",\n" +
        "      \"state\": \"pre-activation\",\n" +
        "      \"created_at\": \"2024-10-24T08:02:18.029Z\",\n" +
        "      \"updated_at\": \"2024-10-24T08:02:18.029Z\",\n" +
        "      \"parent_kid\": null,\n" +
        "      \"public_key\": null\n" +
        "    },\n" +
        "    {\n" +
        "      \"kid\": \"kid-2\",\n" +
        "      \"type\": \"tenant-master-key\",\n" +
        "      \"state\": \"active\",\n" +
        "      \"created_at\": \"2024-10-24T06:47:23.749Z\",\n" +
        "      \"updated_at\": \"2024-10-24T06:47:23.749Z\",\n" +
        "      \"parent_kid\": \"pkid-1\",\n" +
        "      \"public_key\": null\n" +
        "    }\n" +
        "  ]\n" +
        "}";

    private static final String jsonWithoutTotal = "{\n" +
        "  \"start\": 0,\n" +
        "  \"limit\": 50,\n" +
        "  \"keys\": [\n" +
        "    {\n" +
        "      \"kid\": \"kid-1\",\n" +
        "      \"type\": \"customer-provided-root-key\",\n" +
        "      \"state\": \"pre-activation\",\n" +
        "      \"created_at\": \"2024-10-24T08:02:18.029Z\",\n" +
        "      \"updated_at\": \"2024-10-24T08:02:18.029Z\",\n" +
        "      \"parent_kid\": null,\n" +
        "      \"public_key\": null\n" +
        "    },\n" +
        "    {\n" +
        "      \"kid\": \"kid-2\",\n" +
        "      \"type\": \"tenant-master-key\",\n" +
        "      \"state\": \"active\",\n" +
        "      \"created_at\": \"2024-10-24T06:47:23.749Z\",\n" +
        "      \"updated_at\": \"2024-10-24T06:47:23.749Z\",\n" +
        "      \"parent_kid\": \"pkid-1\",\n" +
        "      \"public_key\": null\n" +
        "    }\n" +
        "  ]\n" +
        "}";

    @Test
    public void shouldDeserializeWithTotal() throws Exception {
        EncryptionKeysPage page = fromJSON(jsonWithTotal, EncryptionKeysPage.class);

        assertThat(page, is(notNullValue()));
        assertThat(page.getStart(), is(0));
        assertThat(page.getTotal(), is(20));
        assertThat(page.getLimit(), is(50));
        assertThat(page.getItems(), is(notNullValue()));
        assertThat(page.getItems().size(), is(2));
        assertThat(page.getItems().get(0).getKid(), is("kid-1"));
        assertThat(page.getItems().get(0).getType(), is("customer-provided-root-key"));
        assertThat(page.getItems().get(0).getState(), is("pre-activation"));
        assertThat(page.getItems().get(0).getCreatedAt(), is("2024-10-24T08:02:18.029Z"));
        assertThat(page.getItems().get(0).getUpdatedAt(), is("2024-10-24T08:02:18.029Z"));
    }

    @Test
    public void shouldDeserializeWithoutTotal() throws Exception {
        EncryptionKeysPage page = fromJSON(jsonWithoutTotal, EncryptionKeysPage.class);

        assertThat(page, is(notNullValue()));
        assertThat(page.getStart(), is(0));
        assertThat(page.getLimit(), is(50));
        assertThat(page.getItems(), is(notNullValue()));
        assertThat(page.getItems().size(), is(2));
        assertThat(page.getItems().get(0).getKid(), is("kid-1"));
        assertThat(page.getItems().get(0).getType(), is("customer-provided-root-key"));
        assertThat(page.getItems().get(0).getState(), is("pre-activation"));
        assertThat(page.getItems().get(0).getCreatedAt(), is("2024-10-24T08:02:18.029Z"));
        assertThat(page.getItems().get(0).getUpdatedAt(), is("2024-10-24T08:02:18.029Z"));
    }
}

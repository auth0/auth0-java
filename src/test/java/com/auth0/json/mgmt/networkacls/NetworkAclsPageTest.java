package com.auth0.json.mgmt.networkacls;

import com.auth0.json.JsonTest;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class NetworkAclsPageTest extends JsonTest<NetworkAclsPage> {

    private static final String jsonWithTotal = "{\n" +
        "  \"total\": 2,\n" +
        "  \"start\": 0,\n" +
        "  \"limit\": 2,\n" +
        "  \"network_acls\": [\n" +
        "    {\n" +
        "      \"description\": \"Log America and Canada all the time.\",\n" +
        "      \"active\": true,\n" +
        "      \"priority\": 1,\n" +
        "      \"rule\": {\n" +
        "        \"match\": {\n" +
        "          \"geo_country_codes\": [\n" +
        "            \"US\",\n" +
        "            \"CA\"\n" +
        "          ]\n" +
        "        },\n" +
        "        \"scope\": \"authentication\",\n" +
        "        \"action\": {\n" +
        "          \"log\": true\n" +
        "        }\n" +
        "      },\n" +
        "      \"created_at\": \"2025-03-17T12:31:57.782Z\",\n" +
        "      \"updated_at\": \"2025-03-17T12:31:57.782Z\",\n" +
        "      \"id\": \"acl_1\"\n" +
        "    },\n" +
        "    {\n" +
        "      \"description\": \"Test Desc - 1\",\n" +
        "      \"active\": true,\n" +
        "      \"priority\": 10,\n" +
        "      \"rule\": {\n" +
        "        \"match\": {\n" +
        "          \"ipv4_cidrs\": [\n" +
        "            \"198.0.0.0\",\n" +
        "            \"10.0.0.0/24\"\n" +
        "          ]\n" +
        "        },\n" +
        "        \"scope\": \"management\",\n" +
        "        \"action\": {\n" +
        "          \"block\": true\n" +
        "        }\n" +
        "      },\n" +
        "      \"created_at\": \"2025-06-10T06:18:10.277Z\",\n" +
        "      \"updated_at\": \"2025-06-10T06:18:10.277Z\",\n" +
        "      \"id\": \"acl_2\"\n" +
        "    }\n" +
        "  ]\n" +
        "}";

    private static final String jsonWithoutTotal = "[\n" +
        "  {\n" +
        "    \"description\": \"Test Desc - 1\",\n" +
        "    \"active\": true,\n" +
        "    \"priority\": 10,\n" +
        "    \"rule\": {\n" +
        "      \"match\": {\n" +
        "        \"ipv4_cidrs\": [\n" +
        "          \"198.0.0.0\",\n" +
        "          \"10.0.0.0/24\"\n" +
        "        ]\n" +
        "      },\n" +
        "      \"scope\": \"management\",\n" +
        "      \"action\": {\n" +
        "        \"block\": true\n" +
        "      }\n" +
        "    },\n" +
        "    \"created_at\": \"2025-06-10T06:18:10.277Z\",\n" +
        "    \"updated_at\": \"2025-06-10T06:18:10.277Z\",\n" +
        "    \"id\": \"acl_1\"\n" +
        "  }\n" +
        "]";

    @Test
    public void shouldDeserializeWithTotal() throws Exception {
        NetworkAclsPage page = fromJSON(jsonWithTotal, NetworkAclsPage.class);

        assertThat(page, is(notNullValue()));
        assertThat(page.getStart(), is(0));
        assertThat(page.getTotal(), is(2));
        assertThat(page.getLimit(), is(2));
        assertThat(page.getItems(), is(notNullValue()));
        assertThat(page.getItems().size(), is(2));
        assertThat(page.getItems().get(0).getDescription(), is("Log America and Canada all the time."));
        assertThat(page.getItems().get(0).isActive(), is(true));
        assertThat(page.getItems().get(0).getPriority(), is(1));
        assertThat(page.getItems().get(0).getId(), is("acl_1"));
        assertThat(page.getItems().get(0).getCreatedAt(), is("2025-03-17T12:31:57.782Z"));
        assertThat(page.getItems().get(0).getUpdatedAt(), is("2025-03-17T12:31:57.782Z"));
    }

    @Test
    public void shouldDeserializeWithoutTotal() throws Exception {
        NetworkAclsPage page = fromJSON(jsonWithoutTotal, NetworkAclsPage.class);

        assertThat(page, is(notNullValue()));
        assertThat(page.getItems(), is(notNullValue()));
        assertThat(page.getItems().size(), is(1));
        assertThat(page.getItems().get(0).getDescription(), is("Test Desc - 1"));
        assertThat(page.getItems().get(0).isActive(), is(true));
        assertThat(page.getItems().get(0).getPriority(), is(10));
        assertThat(page.getItems().get(0).getId(), is("acl_1"));
        assertThat(page.getItems().get(0).getCreatedAt(), is("2025-06-10T06:18:10.277Z"));
        assertThat(page.getItems().get(0).getUpdatedAt(), is("2025-06-10T06:18:10.277Z"));
    }
}

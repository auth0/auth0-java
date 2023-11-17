package com.auth0.json.mgmt.organizations;

import com.auth0.json.JsonTest;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class OrganizationClientGrantsPageTest extends JsonTest<OrganizationClientGrantsPage> {

    private static final String JSON_WITHOUT_TOTALS = "[\n" +
        "  {\n" +
        "    \"id\": \"cgr_4pI9a42haOLLWnwq\",\n" +
        "    \"client_id\": \"client-id\",\n" +
        "    \"audience\": \"https://api-identifier\",\n" +
        "    \"scope\": [\n" +
        "      \"update:items\",\n" +
        "      \"read:messages\"\n" +
        "    ]\n" +
        "  },\n" +
        "  {\n" +
        "    \"id\": \"cgr_D018f9kmBmwbZod\",\n" +
        "    \"client_id\": \"client-id\",\n" +
        "    \"audience\": \"https://api-identifier\",\n" +
        "    \"scope\": []\n" +
        "  }\n" +
        "]";

    private static final String JSON_WITH_TOTALS = "{\n" +
        "  \"total\": 13,\n" +
        "  \"start\": 0,\n" +
        "  \"limit\": 1,\n" +
        "  \"client_grants\": [\n" +
        "    {\n" +
        "      \"id\": \"cgr_3aidkk3skLVOM3Ay7\",\n" +
        "      \"client_id\": \"client-id\",\n" +
        "      \"audience\": \"https://api-identifier/\",\n" +
        "      \"scope\": [\n" +
        "        \"read:messages\"\n" +
        "      ]\n" +
        "    }\n" +
        "  ]\n" +
        "}";

    @Test
    public void shouldDeserializeWithoutTotals() throws Exception {
        OrganizationClientGrantsPage page = fromJSON(JSON_WITHOUT_TOTALS, OrganizationClientGrantsPage.class);

        assertThat(page, is(notNullValue()));
        assertThat(page.getStart(), is(nullValue()));
        assertThat(page.getLength(), is(nullValue()));
        assertThat(page.getTotal(), is(nullValue()));
        assertThat(page.getLimit(), is(nullValue()));
        assertThat(page.getNext(), is(nullValue()));
        assertThat(page.getItems(), is(notNullValue()));
        assertThat(page.getItems().size(), is(2));
    }

    @Test
    public void shouldDeserializeWithTotals() throws Exception {
        OrganizationClientGrantsPage page = fromJSON(JSON_WITH_TOTALS, OrganizationClientGrantsPage.class);

        assertThat(page, is(notNullValue()));
        assertThat(page.getStart(), is(0));
        assertThat(page.getTotal(), is(13));
        assertThat(page.getLimit(), is(1));
        assertThat(page.getNext(), is(nullValue()));
        assertThat(page.getItems(), is(notNullValue()));
        assertThat(page.getItems().size(), is(1));
    }
}

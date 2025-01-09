package com.auth0.json.mgmt.selfserviceprofiles;

import com.auth0.json.JsonTest;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SelfServiceProfileResponsePageTest extends JsonTest<SelfServiceProfileResponsePage> {

    private static final String jsonWithoutTotals =
        "[\n" +
        "  {\n" +
        "    \"id\": \"id1\",\n" +
        "    \"name\": \"test1\",\n" +
        "    \"description\": \"This is for testing\",\n" +
        "    \"user_attributes\": [\n" +
        "      {\n" +
        "        \"name\": \"Phone\",\n" +
        "        \"description\": \"This is Phone Number\",\n" +
        "        \"is_optional\": true\n" +
        "      }\n" +
        "    ],\n" +
        "    \"allowed_strategies\": [\n" +
        "      \"google-apps\"\n" +
        "    ],\n" +
        "    \"created_at\": \"2024-12-16T15:26:39.015Z\",\n" +
        "    \"updated_at\": \"2024-12-16T15:28:04.933Z\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"id\": \"id2\",\n" +
        "    \"name\": \"Test2\",\n" +
        "    \"description\": \"This is for Test2\",\n" +
        "    \"user_attributes\": [\n" +
        "      {\n" +
        "        \"name\": \"Phone\",\n" +
        "        \"description\": \"This is Phone Number\",\n" +
        "        \"is_optional\": true\n" +
        "      },\n" +
        "      {\n" +
        "        \"name\": \"UserName\",\n" +
        "        \"description\": \"This is User Name\",\n" +
        "        \"is_optional\": true\n" +
        "      }\n" +
        "    ],\n" +
        "    \"allowed_strategies\": [\n" +
        "      \"oidc\"\n" +
        "    ],\n" +
        "    \"created_at\": \"2024-12-16T15:29:06.119Z\",\n" +
        "    \"updated_at\": \"2024-12-16T15:29:06.119Z\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"id\": \"id3\",\n" +
        "    \"name\": \"Test3\",\n" +
        "    \"description\": \"This is a Test3\",\n" +
        "    \"user_attributes\": [\n" +
        "      {\n" +
        "        \"name\": \"Name\",\n" +
        "        \"description\": \"Name Field\",\n" +
        "        \"is_optional\": true\n" +
        "      }\n" +
        "    ],\n" +
        "    \"allowed_strategies\": [\n" +
        "      \"oidc\"\n" +
        "    ],\n" +
        "    \"created_at\": \"2024-12-20T09:32:13.885Z\",\n" +
        "    \"updated_at\": \"2024-12-20T09:32:13.885Z\",\n" +
        "    \"branding\": {\n" +
        "      \"logo_url\": \"https://www.google.com\",\n" +
        "      \"colors\": {\n" +
        "        \"primary\": \"#ffffff\"\n" +
        "      }\n" +
        "    }\n" +
        "  }\n" +
        "]\n";

    private static final String jsonWithTotals =
        "{\n" +
        "  \"self_service_profiles\": [\n" +
        "    {\n" +
        "      \"id\": \"id1\",\n" +
        "      \"name\": \"test1\",\n" +
        "      \"description\": \"This is for testing\",\n" +
        "      \"user_attributes\": [\n" +
        "        {\n" +
        "          \"name\": \"Phone\",\n" +
        "          \"description\": \"This is Phone Number\",\n" +
        "          \"is_optional\": true\n" +
        "        }\n" +
        "      ],\n" +
        "      \"allowed_strategies\": [\n" +
        "        \"google-apps\"\n" +
        "      ],\n" +
        "      \"created_at\": \"2024-12-16T15:26:39.015Z\",\n" +
        "      \"updated_at\": \"2024-12-16T15:28:04.933Z\"\n" +
        "    },\n" +
        "    {\n" +
        "      \"id\": \"id2\",\n" +
        "      \"name\": \"Test2\",\n" +
        "      \"description\": \"This is for Test2\",\n" +
        "      \"user_attributes\": [\n" +
        "        {\n" +
        "          \"name\": \"Phone\",\n" +
        "          \"description\": \"This is Phone Number\",\n" +
        "          \"is_optional\": true\n" +
        "        },\n" +
        "        {\n" +
        "          \"name\": \"UserName\",\n" +
        "          \"description\": \"This is User Name\",\n" +
        "          \"is_optional\": true\n" +
        "        }\n" +
        "      ],\n" +
        "      \"allowed_strategies\": [\n" +
        "        \"oidc\"\n" +
        "      ],\n" +
        "      \"created_at\": \"2024-12-16T15:29:06.119Z\",\n" +
        "      \"updated_at\": \"2024-12-16T15:29:06.119Z\"\n" +
        "    },\n" +
        "    {\n" +
        "      \"id\": \"id3\",\n" +
        "      \"name\": \"Test3\",\n" +
        "      \"description\": \"This is a Test3\",\n" +
        "      \"user_attributes\": [\n" +
        "        {\n" +
        "          \"name\": \"Name\",\n" +
        "          \"description\": \"Name Field\",\n" +
        "          \"is_optional\": true\n" +
        "        }\n" +
        "      ],\n" +
        "      \"allowed_strategies\": [\n" +
        "        \"oidc\"\n" +
        "      ],\n" +
        "      \"created_at\": \"2024-12-20T09:32:13.885Z\",\n" +
        "      \"updated_at\": \"2024-12-20T09:32:13.885Z\",\n" +
        "      \"branding\": {\n" +
        "        \"logo_url\": \"https://www.google.com\",\n" +
        "        \"colors\": {\n" +
        "          \"primary\": \"#ffffff\"\n" +
        "        }\n" +
        "      }\n" +
        "    }\n" +
        "  ],\n" +
        "  \"start\": 0,\n" +
        "  \"limit\": 10,\n" +
        "  \"total\": 3\n" +
        "}";

    @Test
    public void shouldDeserializeWithoutTotals() throws Exception {
        SelfServiceProfileResponsePage page = fromJSON(jsonWithoutTotals, SelfServiceProfileResponsePage.class);

        assertThat(page, is(notNullValue()));
        assertThat(page.getStart(), is(nullValue()));
        assertThat(page.getTotal(), is(nullValue()));
        assertThat(page.getLimit(), is(nullValue()));
        assertThat(page.getItems(), is(notNullValue()));
        assertThat(page.getItems().size(), is(3));
        assertThat(page.getNext(), is(nullValue()));
    }

    @Test
    public void shouldDeserializeWithTotals() throws Exception {
        SelfServiceProfileResponsePage page = fromJSON(jsonWithTotals, SelfServiceProfileResponsePage.class);

        assertThat(page, is(notNullValue()));
        assertThat(page.getStart(), is(0));
        assertThat(page.getTotal(), is(3));
        assertThat(page.getLimit(), is(10));
        assertThat(page.getItems(), is(notNullValue()));
        assertThat(page.getItems().size(), is(3));
        assertThat(page.getNext(), is(nullValue()));
    }
}

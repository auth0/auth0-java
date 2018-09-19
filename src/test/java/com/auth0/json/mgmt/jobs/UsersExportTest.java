package com.auth0.json.mgmt.jobs;

import com.auth0.json.JsonTest;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class UsersExportTest extends JsonTest<UsersExport> {

    private static final String json = "{\n" +
            "  \"status\": \"pending\",\n" +
            "  \"type\": \"users_export\",\n" +
            "  \"created_at\": \"\",\n" +
            "  \"id\": \"job_0000000000000001\",\n" +
            "  \"connection_id\": \"con_0000000000000001\",\n" +
            "  \"format\": \"csv\",\n" +
            "  \"limit\": 5,\n" +
            "  \"fields\": [\n" +
            "    [\n" +
            "      {\n" +
            "        \"name\": \"user_id\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"name\": \"name\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"name\": \"email\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"name\": \"identities[0].connection\",\n" +
            "        \"export_as\": \"provider\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"name\": \"user_metadata.some_field\"\n" +
            "      }\n" +
            "    ]\n" +
            "  ]\n" +
            "}";

    @Test
    public void shouldDeserialize() throws Exception {
        final UsersExport usersExport = fromJSON(json, UsersExport.class);

        assertThat(usersExport, is(notNullValue()));
        assertThat(usersExport.getConnectionId(), is("con_0000000000000001"));
        assertThat(usersExport.getLimit(), is(5));
        assertThat(usersExport.getFormat(), is("csv"));
    }

    @Test
    public void shouldSetValues() throws Exception {
        final List<List<Map<String, String>>> fields = Collections.singletonList(
                Collections.singletonList(
                        new HashMap<String, String>() {{
                           put("name", "name");
                        }}
                )
        );

        final UsersExport usersExport = new UsersExport();
        usersExport.setFormat("json");
        usersExport.setConnectionId("con_0000000000000001");
        usersExport.setLimit(5);
        usersExport.setFields(fields);

        assertThat(usersExport, is(notNullValue()));
        assertThat(usersExport.getConnectionId(), is("con_0000000000000001"));
        assertThat(usersExport.getLimit(), is(5));
        assertThat(usersExport.getFormat(), is("json"));
        assertThat(usersExport.getFields(), is(notNullValue()));
    }
}

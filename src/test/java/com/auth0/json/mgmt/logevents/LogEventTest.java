package com.auth0.json.mgmt.logevents;

import com.auth0.json.JsonTest;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class LogEventTest extends JsonTest<LogEvent> {

    private static final String json = "{\n" +
        "  \"_id\": \"123\",\n" +
        "  \"log_id\": \"123\",\n" +
        "  \"date\": \"2016-02-23T19:57:29.532Z\",\n" +
        "  \"type\": \"thetype\",\n" +
        "  \"location_info\": {},\n" +
        "  \"details\": {},\n" +
        "  \"client_id\": \"clientId\",\n" +
        "  \"client_name\": \"clientName\",\n" +
        "  \"ip\": \"233.233.233.11\",\n" +
        "  \"user_id\": \"userId\",\n" +
        "  \"user_name\": \"userName\",\n" +
        "  \"connection\": \"connection\",\n" +
        "  \"connection_id\": \"connectionId\",\n" +
        "  \"description\": \"description\",\n" +
        "  \"hostname\": \"hostname\",\n" +
        "  \"audience\": \"audience\",\n" +
        "  \"organization_id\": \"org_1\",\n" +
        "  \"organization_name\": \"org2\",\n" +
        "  \"$event_schema\": {\n" +
        "    \"version\": \"1.0.0\"\n" +
        "  },\n" +
        "  \"tenant_name\": \"paid-testing\",\n" +
        "  \"audience\": \"audience\",\n" +
        "  \"isMobile\": \"false\",\n" +
        "  \"user_agent\": \"okhttp 4.11.0 / Other 0.0.0\",\n" +
    "      \"scope\": [\"read:client_grants\", \"create:client_grants\"]\n" +
        "}";

    @Test
    public void shouldDeserialize() throws Exception {
        LogEvent logEvent = fromJSON(json, LogEvent.class);

        assertThat(logEvent, is(notNullValue()));
        assertThat(logEvent.getId(), is("123"));
        assertThat(logEvent.getLogId(), is("123"));
        assertThat(logEvent.getDate(), is(parseJSONDate("2016-02-23T19:57:29.532Z")));
        assertThat(logEvent.getType(), is("thetype"));
        assertThat(logEvent.getClientId(), is("clientId"));
        assertThat(logEvent.getClientName(), is("clientName"));
        assertThat(logEvent.getIP(), is("233.233.233.11"));
        assertThat(logEvent.getUserId(), is("userId"));
        assertThat(logEvent.getUserName(), is("userName"));
        assertThat(logEvent.getLocationInfo(), is(notNullValue()));
        assertThat(logEvent.getDetails(), is(notNullValue()));
        assertThat(logEvent.getAudience(), is("audience"));
        assertThat(logEvent.getConnection(), is("connection"));
        assertThat(logEvent.getConnectionId(), is("connectionId"));
        assertThat(logEvent.getHostname(), is("hostname"));
        assertThat(logEvent.getDescription(), is("description"));
        assertThat(logEvent.isMobile(), is(false));
        assertThat(logEvent.getUserAgent(), is("okhttp 4.11.0 / Other 0.0.0"));
        assertThat(logEvent.getOrganizationId(), is("org_1"));
        assertThat(logEvent.getOrganizationName(), is("org2"));
        assertThat(logEvent.getTenantName(), is("paid-testing"));
        assertThat(logEvent.getEventSchema(), is(notNullValue()));
        assertThat(logEvent.getScope(), is(Arrays.asList("read:client_grants", "create:client_grants")));
    }
}

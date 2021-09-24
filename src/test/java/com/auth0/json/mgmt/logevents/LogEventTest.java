package com.auth0.json.mgmt.logevents;

import com.auth0.json.JsonTest;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class LogEventTest extends JsonTest<LogEvent> {

    private static final String json = "{\"_id\":\"123\", \"log_id\":\"123\", \"date\":\"2016-02-23T19:57:29.532Z\",\"type\":\"thetype\",\"location_info\":{},\"details\":{},\"client_id\":\"clientId\",\"client_name\":\"clientName\",\"ip\":\"233.233.233.11\",\"user_id\":\"userId\",\"user_name\":\"userName\"}";

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
    }
}

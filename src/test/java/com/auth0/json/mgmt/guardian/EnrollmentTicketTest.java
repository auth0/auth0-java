package com.auth0.json.mgmt.guardian;

import com.auth0.json.JsonMatcher;
import com.auth0.json.JsonTest;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class EnrollmentTicketTest extends JsonTest<EnrollmentTicket> {

    private static final String readOnlyJson = "{\"ticket_id\":\"ticket123\",\"ticket_url\":\"https://auth0.com/guardian/tickets/123\"}";

    @Test
    public void shouldSerializeDataToBeSent() throws Exception {
        EnrollmentTicket ticket = new EnrollmentTicket("1", true, "me@auth0.com");

        String serialized = toJSON(ticket);
        assertThat(serialized, is(notNullValue()));
        assertThat(serialized, JsonMatcher.hasEntry("user_id", "1"));
        assertThat(serialized, JsonMatcher.hasEntry("send_mail", true));
        assertThat(serialized, JsonMatcher.hasEntry("email", "me@auth0.com"));
    }

    @Test
    public void shouldIncludeReadOnlyValuesOnDeserialize() throws Exception {
        EnrollmentTicket ticket = fromJSON(readOnlyJson, EnrollmentTicket.class);
        assertThat(ticket, is(notNullValue()));

        assertThat(ticket.getTicketId(), is("ticket123"));
        assertThat(ticket.getTicketUrl(), is("https://auth0.com/guardian/tickets/123"));
    }
}
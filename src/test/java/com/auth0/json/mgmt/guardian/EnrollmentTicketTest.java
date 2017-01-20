package com.auth0.json.mgmt.guardian;

import com.auth0.json.JsonMatcher;
import com.auth0.json.JsonTest;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class EnrollmentTicketTest extends JsonTest<EnrollmentTicket> {

    private static final String json = "{\"user_id\":\"1\",\"send_email\":true,\"email\":\"me@auth0.com\"}";
    private static final String readOnlyJson = "{\"ticket_id\":\"ticket123\",\"ticket_url\":\"https://auth0.com/guardian/tickets/123\"}";

    @Test
    public void shouldSerialize() throws Exception {
        EnrollmentTicket ticket = new EnrollmentTicket("1");
        ticket.setEmail("me@auth0.com");
        ticket.setSendEmail(true);

        String serialized = toJSON(ticket);
        assertThat(serialized, is(notNullValue()));
        assertThat(serialized, JsonMatcher.hasEntry("user_id", "1"));
        assertThat(serialized, JsonMatcher.hasEntry("send_email", true));
        assertThat(serialized, JsonMatcher.hasEntry("email", "me@auth0.com"));
    }

    @Test
    public void shouldDeserialize() throws Exception {
        EnrollmentTicket ticket = fromJSON(json, EnrollmentTicket.class);

        assertThat(ticket, is(notNullValue()));
        assertThat(ticket.getEmail(), is("me@auth0.com"));
        assertThat(ticket.getSendEmail(), is(true));
        assertThat(ticket.getUserId(), is("1"));
    }

    @Test
    public void shouldIncludeReadOnlyValuesOnDeserialize() throws Exception {
        EnrollmentTicket ticket = fromJSON(readOnlyJson, EnrollmentTicket.class);
        assertThat(ticket, is(notNullValue()));

        assertThat(ticket.getTicketId(), is("ticket123"));
        assertThat(ticket.getTicketUrl(), is("https://auth0.com/guardian/tickets/123"));
    }

}
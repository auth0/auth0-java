package com.auth0.json.mgmt.tickets;

import com.auth0.json.JsonMatcher;
import com.auth0.json.JsonTest;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class PasswordChangeTicketTest extends JsonTest<PasswordChangeTicket> {

    private static final String json = "{\"user_id\":\"usr123\",\"result_url\":\"https://page.auth0.com/result\",\"ttl_sec\":36000,\"new_password\":\"pass123\",\"connection_id\":\"12\",\"email\":\"me@auth0.com\"}";
    private static final String readOnlyJson = "{\"ticket\":\"https://page.auth0.com/tickets/123\"}";

    @Test
    public void shouldSerialize() throws Exception {
        PasswordChangeTicket ticket = new PasswordChangeTicket("usr123");
        ticket.setResultUrl("https://page.auth0.com/result");
        ticket.setTtlSec(36000);
        ticket.setConnectionId("12");
        ticket.setEmail("me@auth0.com");
        ticket.setNewPassword("pass123");

        String serialized = toJSON(ticket);
        assertThat(serialized, is(notNullValue()));
        assertThat(serialized, JsonMatcher.hasEntry("user_id", "usr123"));
        assertThat(serialized, JsonMatcher.hasEntry("result_url", "https://page.auth0.com/result"));
        assertThat(serialized, JsonMatcher.hasEntry("ttl_sec", 36000));
        assertThat(serialized, JsonMatcher.hasEntry("new_password", "pass123"));
        assertThat(serialized, JsonMatcher.hasEntry("connection_id", "12"));
        assertThat(serialized, JsonMatcher.hasEntry("email", "me@auth0.com"));
    }

    @Test
    public void shouldDeserialize() throws Exception {
        PasswordChangeTicket ticket = fromJSON(json, PasswordChangeTicket.class);

        assertThat(ticket, is(notNullValue()));
        assertThat(ticket.getUserId(), is("usr123"));
        assertThat(ticket.getResultUrl(), is("https://page.auth0.com/result"));
        assertThat(ticket.getTtlSec(), is(36000));
        assertThat(ticket.getConnectionId(), is("12"));
        assertThat(ticket.getEmail(), is("me@auth0.com"));
        assertThat(ticket.getNewPassword(), is("pass123"));
    }

    @Test
    public void shouldIncludeReadOnlyValuesOnDeserialize() throws Exception {
        PasswordChangeTicket ticket = fromJSON(readOnlyJson, PasswordChangeTicket.class);
        assertThat(ticket, is(notNullValue()));

        assertThat(ticket.getTicket(), is("https://page.auth0.com/tickets/123"));
    }

}
package com.auth0.json.mgmt.tickets;

import com.auth0.json.JsonMatcher;
import com.auth0.json.JsonTest;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class PasswordChangeTicketTest extends JsonTest<PasswordChangeTicket> {

    private static final String readOnlyJson = "{\"ticket\":\"https://page.auth0.com/tickets/123\"}";

    @Test
    public void shouldSerialize() throws Exception {
        PasswordChangeTicket ticket = new PasswordChangeTicket("usr123");
        ticket.setResultUrl("https://page.auth0.com/result");
        ticket.setTTLSeconds(36000);
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
    public void shouldSerializeWithCustomizedConnection() throws Exception {
        PasswordChangeTicket ticket = new PasswordChangeTicket("user@emailprovider.com", "connid123");
        ticket.setResultUrl("https://page.auth0.com/result");
        ticket.setTTLSeconds(36000);
        ticket.setEmail("me@auth0.com");
        ticket.setNewPassword("pass123");
        ticket.setUserId("usr123");

        String serialized = toJSON(ticket);
        assertThat(serialized, is(notNullValue()));
        assertThat(serialized, JsonMatcher.hasEntry("user_id", "usr123"));
        assertThat(serialized, JsonMatcher.hasEntry("result_url", "https://page.auth0.com/result"));
        assertThat(serialized, JsonMatcher.hasEntry("ttl_sec", 36000));
        assertThat(serialized, JsonMatcher.hasEntry("new_password", "pass123"));
        assertThat(serialized, JsonMatcher.hasEntry("connection_id", "connid123"));
        assertThat(serialized, JsonMatcher.hasEntry("email", "me@auth0.com"));
    }
    
    @Test
    public void shouldIncludeReadOnlyValuesOnDeserialize() throws Exception {
        PasswordChangeTicket ticket = fromJSON(readOnlyJson, PasswordChangeTicket.class);
        assertThat(ticket, is(notNullValue()));

        assertThat(ticket.getTicket(), is("https://page.auth0.com/tickets/123"));
    }

}

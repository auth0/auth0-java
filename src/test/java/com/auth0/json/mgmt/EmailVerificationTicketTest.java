package com.auth0.json.mgmt;

import com.auth0.JsonMatcher;
import com.auth0.json.JsonTest;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class EmailVerificationTicketTest extends JsonTest<EmailVerificationTicket>{

    private static final String json = "{\"user_id\":\"usr123\",\"result_url\":\"https://page.auth0.com/result\",\"ttl_sec\":36000}";
    private static final String readOnlyJson = "{\"ticket\":\"https://page.auth0.com/tickets/123\"}";

    @Test
    public void shouldSerialize() throws Exception {
        EmailVerificationTicket ticket = new EmailVerificationTicket("usr123");
        ticket.setResultUrl("https://page.auth0.com/result");
        ticket.setTtlSec(36000);

        String serialized = toJSON(ticket);
        assertThat(serialized, is(notNullValue()));
        assertThat(serialized, JsonMatcher.hasEntry("user_id", "usr123"));
        assertThat(serialized, JsonMatcher.hasEntry("result_url", "https://page.auth0.com/result"));
        assertThat(serialized, JsonMatcher.hasEntry("ttl_sec", 36000));
    }

    @Test
    public void shouldDeserialize() throws Exception {
        EmailVerificationTicket ticket = fromJSON(json, EmailVerificationTicket.class);

        assertThat(ticket, is(notNullValue()));
        assertThat(ticket.getUserId(), is("usr123"));
        assertThat(ticket.getResultUrl(), is("https://page.auth0.com/result"));
        assertThat(ticket.getTtlSec(), is(36000));
    }

    @Test
    public void shouldIncludeReadOnlyValuesOnDeserialize() throws Exception {
        EmailVerificationTicket ticket = fromJSON(readOnlyJson, EmailVerificationTicket.class);
        assertThat(ticket, is(notNullValue()));

        assertThat(ticket.getTicket(), is("https://page.auth0.com/tickets/123"));
    }

}
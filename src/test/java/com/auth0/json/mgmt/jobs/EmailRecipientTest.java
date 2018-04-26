package com.auth0.json.mgmt.jobs;

import com.auth0.json.JsonTest;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class EmailRecipientTest extends JsonTest<EmailRecipient> {

    private static final String json = "{\"client_id\":\"123Asd\",\"user_id\":\"googleAuth|035\"}";

    @Test
    public void shouldDeserialize() throws Exception {
        EmailRecipient recipient = fromJSON(json, EmailRecipient.class);

        assertThat(recipient, is(notNullValue()));
        assertThat(recipient.getClientId(), is("123Asd"));
        assertThat(recipient.getUserId(), is("googleAuth|035"));
    }
}

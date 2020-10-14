package com.auth0.json.auth;

import com.auth0.json.JsonTest;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class PasswordlessEmailResponseTest extends JsonTest<PasswordlessEmailResponse> {

    private final static String EMAIL_RESPONSE_JSON = "{\"_id\":\"abc123\",\"email\":\"user@domain.com\",\"email_verified\":true}";

    @Test
    public void shouldDeserializePasswordlessEmailResponse() throws Exception {
        PasswordlessEmailResponse response = fromJSON(EMAIL_RESPONSE_JSON, PasswordlessEmailResponse.class);

        assertThat(response, is(notNullValue()));

        assertThat(response.getEmail(), is("user@domain.com"));
        assertThat(response.getId(), is("abc123"));
        assertThat(response.isEmailVerified(), is(true));
    }
}

package com.auth0.json.auth;

import com.auth0.json.JsonTest;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class PasswordlessSmsResponseTest extends JsonTest<PasswordlessSmsResponse> {

    private final static String SMS_RESPONSE_JSON = "{\"_id\":\"abc123\",\"phone_number\":\"+11234567891\",\"phone_verified\":false,\"request_language\":null}";

    @Test
    public void shouldDeserializePasswordlessEmailResponse() throws Exception {
        PasswordlessSmsResponse response = fromJSON(SMS_RESPONSE_JSON, PasswordlessSmsResponse.class);

        assertThat(response, is(notNullValue()));

        assertThat(response.getId(), is("abc123"));
        assertThat(response.isPhoneVerified(), is(false));
        assertThat(response.getPhoneNumber(), is("+11234567891"));
        assertThat(response.getRequestLanguage(), is(nullValue()));
    }
}

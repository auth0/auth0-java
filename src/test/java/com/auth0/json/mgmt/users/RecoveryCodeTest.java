package com.auth0.json.mgmt.users;

import com.auth0.json.JsonTest;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class RecoveryCodeTest extends JsonTest<RecoveryCode> {

    private static final String json = "{\"recovery_code\":\"supersecretCODE\"}";

    @Test
    public void shouldDeserialize() throws Exception {
        RecoveryCode code = fromJSON(json, RecoveryCode.class);

        assertThat(code, is(notNullValue()));
        assertThat(code.getCode(), is("supersecretCODE"));
    }
}
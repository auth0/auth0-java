package com.auth0.json.auth;

import com.auth0.json.JsonTest;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class TokenHolderTest extends JsonTest<TokenHolder> {

    private static final String json = "{\"id_token\":\"eyJ0eXAiOiJKV1Qi...\",\"access_token\":\"A9CvPwFojaBI...\",\"refresh_token\":\"GEbRxBN...edjnXbL\",\"token_type\": \"bearer\",\"expires_in\":86000}";

    @Test
    public void shouldDeserialize() throws Exception {
        TokenHolder holder = fromJSON(json, TokenHolder.class);

        assertThat(holder, is(notNullValue()));

        assertThat(holder.getAccessToken(), is(notNullValue()));
        assertThat(holder.getIdToken(), is(notNullValue()));
        assertThat(holder.getRefreshToken(), is(notNullValue()));
        assertThat(holder.getTokenType(), is(notNullValue()));
        assertThat(holder.getExpiresIn(), is(notNullValue()));
    }
}
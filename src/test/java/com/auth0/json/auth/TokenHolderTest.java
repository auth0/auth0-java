package com.auth0.json.auth;

import com.auth0.json.JsonTest;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TokenHolderTest extends JsonTest<TokenHolder> {

    @Test
    public void shouldDeserialize() throws Exception {
        String json = "{\"id_token\":\"eyJ0eXAiOiJKV1Qi...\",\"access_token\":\"A9CvPwFojaBI...\",\"refresh_token\":\"GEbRxBN...edjnXbL\",\"token_type\": \"bearer\",\"expires_in\":86000, \"scope\": \"openid profile email\"}";

        TokenHolder holder = fromJSON(json, TokenHolder.class);

        assertThat(holder, is(notNullValue()));
        assertThat(holder.getAccessToken(), is(notNullValue()));
        assertThat(holder.getIdToken(), is(notNullValue()));
        assertThat(holder.getRefreshToken(), is(notNullValue()));
        assertThat(holder.getTokenType(), is(notNullValue()));
        assertThat(holder.getExpiresIn(), is(notNullValue()));
        assertThat(holder.getScope(), is(notNullValue()));
    }

    @Test
    public void shouldDeserializeWithUnexpectedValues() throws Exception {
        String json = "{\"id_token\":\"eyJ0eXAiOiJKV1Qi...\",\"access_token\":\"A9CvPwFojaBI...\",\"refresh_token\":\"GEbRxBN...edjnXbL\",\"token_type\": \"bearer\",\"expires_in\":86000, \"scope\": \"openid profile email\", \"some-key\": \"some-value\"}";

        TokenHolder holder = fromJSON(json, TokenHolder.class);

        assertThat(holder, is(notNullValue()));
        assertThat(holder.getAccessToken(), is(notNullValue()));
        assertThat(holder.getIdToken(), is(notNullValue()));
        assertThat(holder.getRefreshToken(), is(notNullValue()));
        assertThat(holder.getTokenType(), is(notNullValue()));
        assertThat(holder.getExpiresIn(), is(notNullValue()));
        assertThat(holder.getScope(), is(notNullValue()));
    }

    @Test
    public void shouldDeserializeWithMissingFields() throws Exception {
        String json = "{\"access_token\":\"A9CvPwFojaBI...\",\"token_type\": \"bearer\", \"scope\": \"openid profile email\"}";

        TokenHolder holder = fromJSON(json, TokenHolder.class);

        assertThat(holder, is(notNullValue()));
        assertThat(holder.getAccessToken(), is(notNullValue()));
        assertThat(holder.getIdToken(), is(nullValue()));
        assertThat(holder.getRefreshToken(), is(nullValue()));
        assertThat(holder.getTokenType(), is(notNullValue()));

        // as a primitive, a missing value in the JSON will result in a value of zero on the value object.
        assertThat(holder.getExpiresIn(), is(0L));
        assertThat(holder.getScope(), is(notNullValue()));
    }
}

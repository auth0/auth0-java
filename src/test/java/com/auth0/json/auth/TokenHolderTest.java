package com.auth0.json.auth;

import com.auth0.json.JsonTest;
import org.junit.Test;

import java.time.Instant;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TokenHolderTest extends JsonTest<TokenHolder> {

    @Test
    public void shouldDeserialize() throws Exception {
        String json = "{\"id_token\":\"eyJ0eXAiOiJKV1Qi...\",\"access_token\":\"A9CvPwFojaBI...\",\"refresh_token\":\"GEbRxBN...edjnXbL\",\"token_type\": \"bearer\",\"expires_in\":86000, \"scope\": \"openid profile email\"}";

        TokenHolder holder = fromJSON(json, TokenHolder.class);

        assertThat(holder, is(notNullValue()));
        assertThat(holder.getAccessToken(), is("A9CvPwFojaBI..."));
        assertThat(holder.getIdToken(), is("eyJ0eXAiOiJKV1Qi..."));
        assertThat(holder.getRefreshToken(), is("GEbRxBN...edjnXbL"));
        assertThat(holder.getTokenType(), is("bearer"));
        assertThat(holder.getExpiresIn(), is(86000L));
        assertThat(holder.getScope(), is("openid profile email"));

        // allow for a small tolerance since now may be calculated at slightly different times in the test
        assertThat(getExpectedExpiredAtDiff(86000, holder.getExpiresAt()), is(lessThanOrEqualTo(2L)));

    }

    @Test
    public void shouldDeserializeWithUnexpectedValues() throws Exception {
        String json = "{\"id_token\":\"eyJ0eXAiOiJKV1Qi...\",\"access_token\":\"A9CvPwFojaBI...\",\"refresh_token\":\"GEbRxBN...edjnXbL\",\"token_type\": \"bearer\",\"expires_in\":86000, \"scope\": \"openid profile email\", \"some-key\": \"some-value\"}";

        TokenHolder holder = fromJSON(json, TokenHolder.class);

        assertThat(holder, is(notNullValue()));
        assertThat(holder.getAccessToken(), is("A9CvPwFojaBI..."));
        assertThat(holder.getIdToken(), is("eyJ0eXAiOiJKV1Qi..."));
        assertThat(holder.getRefreshToken(), is("GEbRxBN...edjnXbL"));
        assertThat(holder.getTokenType(), is("bearer"));
        assertThat(holder.getExpiresIn(), is(86000L));
        assertThat(holder.getScope(), is("openid profile email"));

        // allow for a small tolerance since now may be calculated at slightly different times in the test
        assertThat(getExpectedExpiredAtDiff(86000, holder.getExpiresAt()), is(lessThanOrEqualTo(2L)));
    }

    @Test
    public void shouldDeserializeWithMissingFields() throws Exception {
        String json = "{}";

        TokenHolder holder = fromJSON(json, TokenHolder.class);

        assertThat(holder, is(notNullValue()));
        assertThat(holder.getAccessToken(), is(nullValue()));
        assertThat(holder.getIdToken(), is(nullValue()));
        assertThat(holder.getRefreshToken(), is(nullValue()));
        assertThat(holder.getTokenType(), is(nullValue()));
        assertThat(holder.getScope(), is(nullValue()));

        // as a primitive, a missing value in the JSON will result in a value of zero on the value object.
        assertThat(holder.getExpiresIn(), is(0L));
        // allow for a small tolerance since now may be calculated at slightly different times in the test
        assertThat(getExpectedExpiredAtDiff(0, holder.getExpiresAt()), is(lessThanOrEqualTo(2L)));

    }

    @Test
    public void shouldHaveDefaultConstructor() {
        // To ensure default constructor exists for backwards-compatibility
        TokenHolder tokenHolder = new TokenHolder();
        assertThat(tokenHolder, is(notNullValue()));
    }

    private long getExpectedExpiredAtDiff(long expiresIn, Date expiresAt) {
        Instant expectedExpiresAt  = Instant.now().plusSeconds(expiresIn);
        long expectedEpochSeconds = expectedExpiresAt.getEpochSecond();
        long actualEpochSeconds = expiresAt.toInstant().getEpochSecond();
        return Math.abs(expectedEpochSeconds - actualEpochSeconds);
    }
}

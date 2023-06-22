package com.auth0.json.mgmt.client;

import com.auth0.json.JsonMatcher;
import com.auth0.json.JsonTest;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class RefreshTokenTest extends JsonTest<RefreshToken> {

    private static final String json = "{\"rotation_type\":\"non-rotating\",\"expiration_type\":\"non-expiring\",\"leeway\":0,\"token_lifetime\":0,\"infinite_token_lifetime\":false,\"idle_token_lifetime\":0,\"infinite_idle_token_lifetime\":false}";

    @Test
    public void shouldSerialize() throws Exception {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setRotationType("non-rotating");
        refreshToken.setExpirationType("non-expiring");
        refreshToken.setLeeway(0);
        refreshToken.setTokenLifetime(0);
        refreshToken.setInfiniteTokenLifetime(false);
        refreshToken.setIdleTokenLifetime(0);
        refreshToken.setInfiniteIdleTokenLifetime(false);

        String serialized = toJSON(refreshToken);
        assertThat(serialized, is(notNullValue()));
        assertThat(serialized, JsonMatcher.hasEntry("rotation_type", "non-rotating"));
        assertThat(serialized, JsonMatcher.hasEntry("expiration_type", "non-expiring"));
        assertThat(serialized, JsonMatcher.hasEntry("leeway", 0));
        assertThat(serialized, JsonMatcher.hasEntry("token_lifetime", 0));
        assertThat(serialized, JsonMatcher.hasEntry("infinite_token_lifetime", false));
        assertThat(serialized, JsonMatcher.hasEntry("idle_token_lifetime", 0));
        assertThat(serialized, JsonMatcher.hasEntry("infinite_idle_token_lifetime", false));
    }

    @Test
    public void shouldDeserialize() throws Exception {
        RefreshToken refreshToken = fromJSON(json, RefreshToken.class);

        assertThat(refreshToken, is(notNullValue()));
        assertThat(refreshToken.getRotationType(), is("non-rotating"));
        assertThat(refreshToken.getExpirationType(), is("non-expiring"));
        assertThat(refreshToken.getLeeway(), is(0));
        assertThat(refreshToken.getTokenLifetime(), is(0));
        assertThat(refreshToken.getInfiniteTokenLifetime(), is(false));
        assertThat(refreshToken.getIdleTokenLifetime(), is(0));
        assertThat(refreshToken.getInfiniteIdleTokenLifetime(), is(false));
    }
}

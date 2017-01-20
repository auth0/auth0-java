package com.auth0.json.mgmt.guardian;

import com.auth0.json.JsonMatcher;
import com.auth0.json.JsonTest;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class TwilioFactorProviderTest extends JsonTest<TwilioFactorProvider> {

    private static final String json = "{\"from\":\"+12356789\",\"messaging_service_sid\":\"id321\",\"auth_token\":\"atokEn\",\"sid\":\"id123\"}";

    @Test
    public void shouldSerialize() throws Exception {
        TwilioFactorProvider provider = new TwilioFactorProvider();
        provider.setAuthToken("atokEn");
        provider.setFrom("+12356789");
        provider.setMessagingServiceSid("id321");
        provider.setSid("id123");

        String serialized = toJSON(provider);
        assertThat(serialized, is(notNullValue()));
        assertThat(serialized, JsonMatcher.hasEntry("from", "+12356789"));
        assertThat(serialized, JsonMatcher.hasEntry("messaging_service_sid", "id321"));
        assertThat(serialized, JsonMatcher.hasEntry("auth_token", "atokEn"));
        assertThat(serialized, JsonMatcher.hasEntry("sid", "id123"));
    }

    @Test
    public void shouldDeserialize() throws Exception {
        TwilioFactorProvider provider = fromJSON(json, TwilioFactorProvider.class);

        assertThat(provider, is(notNullValue()));
        assertThat(provider.getAuthToken(), is("atokEn"));
        assertThat(provider.getFrom(), is("+12356789"));
        assertThat(provider.getMessagingServiceSid(), is("id321"));
        assertThat(provider.getSid(), is("id123"));
    }

}
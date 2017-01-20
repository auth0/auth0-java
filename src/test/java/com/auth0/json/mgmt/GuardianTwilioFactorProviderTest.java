package com.auth0.json.mgmt;

import com.auth0.JsonMatcher;
import com.auth0.json.JsonTest;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GuardianTwilioFactorProviderTest extends JsonTest<GuardianTwilioFactorProvider> {

    private static final String json = "{\"from\":\"+12356789\",\"messaging_service_sid\":\"id321\",\"auth_token\":\"atokEn\",\"sid\":\"id123\"}";

    @Test
    public void shouldSerialize() throws Exception {
        GuardianTwilioFactorProvider provider = new GuardianTwilioFactorProvider();
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
        GuardianTwilioFactorProvider provider = fromJSON(json, GuardianTwilioFactorProvider.class);

        assertThat(provider, is(notNullValue()));
        assertThat(provider.getAuthToken(), is("atokEn"));
        assertThat(provider.getFrom(), is("+12356789"));
        assertThat(provider.getMessagingServiceSid(), is("id321"));
        assertThat(provider.getSid(), is("id123"));
    }

}
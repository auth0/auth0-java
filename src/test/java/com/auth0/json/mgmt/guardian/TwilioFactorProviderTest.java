package com.auth0.json.mgmt.guardian;

import com.auth0.json.JsonMatcher;
import com.auth0.json.JsonTest;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TwilioFactorProviderTest extends JsonTest<TwilioFactorProvider> {

    @SuppressWarnings("deprecation")
    @Rule
    public ExpectedException exception = ExpectedException.none();

    private static final String JSON_WITH_FROM = "{\"from\":\"+12356789\",\"auth_token\":\"atokEn\",\"sid\":\"id123\"}";
    private static final String JSON_WITH_MESSAGING_SERVICE_SID = "{\"messaging_service_sid\":\"id321\",\"auth_token\":\"atokEn\",\"sid\":\"id123\"}";

    @Test
    public void shouldFailConstructionWithBothFromAndMessagingServiceSID() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("You must specify either `from` or `messagingServiceSID`, but not both");

        new TwilioFactorProvider("+12356789", "messaging_service_sid", "atokEn", "id123");
    }

    @SuppressWarnings("deprecation")
    @Test
    public void shouldSerializeWithDeprecatedSettersWithFrom() throws Exception {
        TwilioFactorProvider provider = new TwilioFactorProvider("+12356789", null, "atokEn", "id123");

        String serialized = toJSON(provider);
        assertThat(serialized, is(notNullValue()));
        assertThat(serialized, JsonMatcher.hasEntry("from", "+12356789"));
        assertThat(serialized, JsonMatcher.hasEntry("auth_token", "atokEn"));
        assertThat(serialized, JsonMatcher.hasEntry("sid", "id123"));
        assertThat(serialized, not(containsString("\"messaging_service_sid\"")));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void shouldSerializeWithDeprecatedSettersWithMessagingServiceSID() throws Exception {
        TwilioFactorProvider provider = new TwilioFactorProvider(null, "id321", "atokEn", "id123");

        String serialized = toJSON(provider);
        assertThat(serialized, is(notNullValue()));

        assertThat(serialized, JsonMatcher.hasEntry("messaging_service_sid", "id321"));
        assertThat(serialized, JsonMatcher.hasEntry("auth_token", "atokEn"));
        assertThat(serialized, JsonMatcher.hasEntry("sid", "id123"));
        assertThat(serialized, not(containsString("\"from\"")));
    }

    @Test
    public void shouldSerializeWithFrom() throws Exception {
        TwilioFactorProvider provider = new TwilioFactorProvider("+12356789", null, "atokEn", "id123");

        String serialized = toJSON(provider);
        assertThat(serialized, is(notNullValue()));
        assertThat(serialized, JsonMatcher.hasEntry("from", "+12356789"));
        assertThat(serialized, JsonMatcher.hasEntry("auth_token", "atokEn"));
        assertThat(serialized, JsonMatcher.hasEntry("sid", "id123"));
        assertThat(serialized, not(containsString("\"messaging_service_sid\"")));
    }

    @Test
    public void shouldSerializeWithMessaginServiceSID() throws Exception {
        TwilioFactorProvider provider = new TwilioFactorProvider(null, "id321", "atokEn", "id123");

        String serialized = toJSON(provider);
        assertThat(serialized, is(notNullValue()));
        assertThat(serialized, JsonMatcher.hasEntry("messaging_service_sid", "id321"));
        assertThat(serialized, JsonMatcher.hasEntry("auth_token", "atokEn"));
        assertThat(serialized, JsonMatcher.hasEntry("sid", "id123"));
        assertThat(serialized, not(containsString("\"from\"")));
    }

    @Test
    public void shouldDeserializeWithFrom() throws Exception {
        TwilioFactorProvider provider = fromJSON(JSON_WITH_FROM, TwilioFactorProvider.class);

        assertThat(provider, is(notNullValue()));
        assertThat(provider.getAuthToken(), is("atokEn"));
        assertThat(provider.getFrom(), is("+12356789"));
        assertThat(provider.getMessagingServiceSID(), is(nullValue()));
        assertThat(provider.getSID(), is("id123"));
    }

    @Test
    public void shouldDeserializeWithMessagingServiceSID() throws Exception {
        TwilioFactorProvider provider = fromJSON(JSON_WITH_MESSAGING_SERVICE_SID, TwilioFactorProvider.class);

        assertThat(provider, is(notNullValue()));
        assertThat(provider.getAuthToken(), is("atokEn"));
        assertThat(provider.getFrom(), is(nullValue()));
        assertThat(provider.getMessagingServiceSID(), is("id321"));
        assertThat(provider.getSID(), is("id123"));
    }
}

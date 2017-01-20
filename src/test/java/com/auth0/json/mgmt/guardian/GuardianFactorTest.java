package com.auth0.json.mgmt.guardian;

import com.auth0.json.JsonMatcher;
import com.auth0.json.JsonTest;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class GuardianFactorTest extends JsonTest<GuardianFactor> {

    private static final String json = "{\"enabled\":true}";
    private static final String readOnlyJson = "{\"name\":\"sms\",\"trial_expired\":true}";

    @Test
    public void shouldSerialize() throws Exception {
        GuardianFactor factor = new GuardianFactor(true);

        String serialized = toJSON(factor);
        assertThat(serialized, is(notNullValue()));
        assertThat(serialized, JsonMatcher.hasEntry("enabled", true));
    }

    @Test
    public void shouldDeserialize() throws Exception {
        GuardianFactor factor = fromJSON(json, GuardianFactor.class);

        assertThat(factor, is(notNullValue()));
        assertThat(factor.getEnabled(), is(true));
    }

    @Test
    public void shouldIncludeReadOnlyValuesOnDeserialize() throws Exception {
        GuardianFactor ticket = fromJSON(readOnlyJson, GuardianFactor.class);
        assertThat(ticket, is(notNullValue()));

        assertThat(ticket.getName(), is("sms"));
        assertThat(ticket.getTrialExpired(), is(true));
    }

}
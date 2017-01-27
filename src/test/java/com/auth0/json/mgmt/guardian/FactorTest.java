package com.auth0.json.mgmt.guardian;

import com.auth0.json.JsonMatcher;
import com.auth0.json.JsonTest;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class FactorTest extends JsonTest<Factor> {

    private static final String json = "{\"enabled\":true}";
    private static final String readOnlyJson = "{\"name\":\"sms\",\"trial_expired\":true}";

    @Test
    public void shouldSerialize() throws Exception {
        Factor factor = new Factor(true);

        String serialized = toJSON(factor);
        assertThat(serialized, is(notNullValue()));
        assertThat(serialized, JsonMatcher.hasEntry("enabled", true));
    }

    @Test
    public void shouldDeserialize() throws Exception {
        Factor factor = fromJSON(json, Factor.class);

        assertThat(factor, is(notNullValue()));
        assertThat(factor.isEnabled(), is(true));
    }

    @Test
    public void shouldIncludeReadOnlyValuesOnDeserialize() throws Exception {
        Factor ticket = fromJSON(readOnlyJson, Factor.class);
        assertThat(ticket, is(notNullValue()));

        assertThat(ticket.getName(), is("sms"));
        assertThat(ticket.isTrialExpired(), is(true));
    }

}
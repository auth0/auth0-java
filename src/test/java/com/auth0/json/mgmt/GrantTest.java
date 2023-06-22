package com.auth0.json.mgmt;

import com.auth0.json.JsonMatcher;
import com.auth0.json.JsonTest;
import com.auth0.json.mgmt.grants.Grant;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GrantTest extends JsonTest<Grant> {

    private static final String json = "{\"clientID\":\"clientId\",\"user_id\":\"userId\",\"audience\":\"aud\",\"scope\":[\"one\",\"two\"]}";
    private static final String readOnlyJson = "{\"id\":\"grantId\"}";

    @Test
    public void shouldSerialize() throws Exception {
        Grant grant = new Grant();
        grant.setUserId("userId");
        grant.setAudience("aud");
        grant.setClientId("clientId");
        grant.setScope(Arrays.asList("one", "two"));

        String serialized = toJSON(grant);
        assertThat(serialized, is(notNullValue()));
        assertThat(serialized, JsonMatcher.hasEntry("user_id", "userId"));
        assertThat(serialized, JsonMatcher.hasEntry("clientID", "clientId"));
        assertThat(serialized, JsonMatcher.hasEntry("audience", "aud"));
        assertThat(serialized, JsonMatcher.hasEntry("scope", Arrays.asList("one", "two")));
    }

    @Test
    public void shouldDeserialize() throws Exception {
        Grant grant = fromJSON(json, Grant.class);

        assertThat(grant, is(notNullValue()));
        assertThat(grant.getUserId(), is("userId"));
        assertThat(grant.getAudience(), is("aud"));
        assertThat(grant.getClientId(), is("clientId"));
        assertThat(grant.getScope(), contains("one", "two"));
    }

    @Test
    public void shouldIncludeReadOnlyValuesOnDeserialize() throws Exception {
        Grant grant = fromJSON(readOnlyJson, Grant.class);
        assertThat(grant, is(notNullValue()));

        assertThat(grant.getId(), is("grantId"));
    }
}

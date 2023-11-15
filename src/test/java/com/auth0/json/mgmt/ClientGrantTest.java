package com.auth0.json.mgmt;

import com.auth0.json.JsonMatcher;
import com.auth0.json.JsonTest;
import com.auth0.json.mgmt.clientgrants.ClientGrant;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ClientGrantTest extends JsonTest<ClientGrant> {

    private static final String json = "{\"client_id\":\"clientId\",\"audience\":\"aud\",\"scope\":[\"one\",\"two\"],\"organization_usage\": \"allow\",\"allow_any_organization\":true}";
    private static final String readOnlyJson = "{\"id\":\"grantId\"}";

    @Test
    public void shouldSerialize() throws Exception {
        ClientGrant grant = new ClientGrant();
        grant.setAudience("aud");
        grant.setClientId("clientId");
        grant.setScope(Arrays.asList("one", "two"));
        grant.setOrganizationUsage("require");
        grant.setAllowAnyOrganization(true);

        String serialized = toJSON(grant);
        assertThat(serialized, is(notNullValue()));
        assertThat(serialized, JsonMatcher.hasEntry("client_id", "clientId"));
        assertThat(serialized, JsonMatcher.hasEntry("audience", "aud"));
        assertThat(serialized, JsonMatcher.hasEntry("scope", Arrays.asList("one", "two")));
        assertThat(serialized, JsonMatcher.hasEntry("organization_usage", "require"));
        assertThat(serialized, JsonMatcher.hasEntry("allow_any_organization", true));
    }

    @Test
    public void shouldDeserialize() throws Exception {
        ClientGrant grant = fromJSON(json, ClientGrant.class);

        assertThat(grant, is(notNullValue()));

        assertThat(grant.getAudience(), is("aud"));
        assertThat(grant.getClientId(), is("clientId"));
        assertThat(grant.getScope(), contains("one", "two"));
        assertThat(grant.getOrganizationUsage(), is("allow"));
        assertThat(grant.getAllowAnyOrganization(), is(true));
    }

    @Test
    public void shouldIncludeReadOnlyValuesOnDeserialize() throws Exception {
        ClientGrant grant = fromJSON(readOnlyJson, ClientGrant.class);
        assertThat(grant, is(notNullValue()));

        assertThat(grant.getId(), is("grantId"));
    }
}

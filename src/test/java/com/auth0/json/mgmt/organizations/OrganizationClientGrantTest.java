package com.auth0.json.mgmt.organizations;

import com.auth0.json.JsonMatcher;
import com.auth0.json.JsonTest;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class OrganizationClientGrantTest extends JsonTest<OrganizationClientGrant> {

    @Test
    public void shouldSerialize() throws Exception {
        OrganizationClientGrant clientGrant = new OrganizationClientGrant();
        clientGrant.setClientId("client-id");
        clientGrant.setAudience("https://api-identifier/");
        clientGrant.setScope(Arrays.asList("read:messages", "write:messages"));

        String serialized = toJSON(clientGrant);
        assertThat(serialized, is(notNullValue()));
        assertThat(serialized, JsonMatcher.hasEntry("client_id", "client-id"));
        assertThat(serialized, JsonMatcher.hasEntry("audience", "https://api-identifier/"));
        assertThat(serialized, JsonMatcher.hasEntry("scope", Arrays.asList("read:messages", "write:messages")));
    }

    @Test
    public void shouldDeserialize() throws Exception {
        String json = "{\n" +
            "  \"id\": \"1\",\n" +
            "  \"client_id\": \"clientId1\",\n" +
            "  \"audience\": \"audience1\",\n" +
            "  \"scope\": [\n" +
            "    \"openid\",\n" +
            "    \"profile\"\n" +
            "  ]\n" +
            "}";

        OrganizationClientGrant clientGrant = fromJSON(json, OrganizationClientGrant.class);

        assertThat(clientGrant, is(notNullValue()));

        assertThat(clientGrant.getAudience(), is("audience1"));
        assertThat(clientGrant.getClientId(), is("clientId1"));
        assertThat(clientGrant.getScope(), contains("openid", "profile"));
    }
}

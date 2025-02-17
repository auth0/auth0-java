package com.auth0.json.mgmt.connections;

import com.auth0.json.JsonTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.auth0.json.JsonMatcher.hasEntry;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ScimConfigurationResponseTest extends JsonTest<ScimConfigurationResponse> {
    private final static String SCIM_CONFIGURATION_RESPONSE_JSON = "src/test/resources/mgmt/connection_scim_configuration.json";

    @Test
    public void deserialize() throws Exception {
        ScimConfigurationResponse deserialized = fromJSON(readTextFile(SCIM_CONFIGURATION_RESPONSE_JSON), ScimConfigurationResponse.class);

        assertThat(deserialized.getTenantName(), is("dev-1"));
        assertThat(deserialized.getConnectionId(), is("con_0000000000000001"));
        assertThat(deserialized.getConnectionName(), is("New Connection"));
        assertThat(deserialized.getStrategy(), is("okta"));
        assertThat(deserialized.getMapping().get(0).getAuth0(), is("preferred_username"));
        assertThat(deserialized.getMapping().get(0).getScim(), is("userName"));
        assertThat(deserialized.getMapping().get(1).getAuth0(), is("email"));
        assertThat(deserialized.getMapping().get(1).getScim(), is("emails[primary eq true].value"));
        assertThat(deserialized.getUpdatedOn(), is("2025-01-22T11:56:24.461Z"));
        assertThat(deserialized.getCreatedAt(), is("2025-01-22T11:56:24.461Z"));
        assertThat(deserialized.getUserIdAttribute(), is("externalId"));
    }

    @Test
    public void serialize() throws Exception {
        ScimConfigurationResponse scimConfigurationResponse = new ScimConfigurationResponse();
        scimConfigurationResponse.setTenantName("dev-1");
        scimConfigurationResponse.setConnectionId("con_0000000000000001");
        scimConfigurationResponse.setConnectionName("New Connection");
        scimConfigurationResponse.setStrategy("okta");

        List<Mapping> mappingList = new ArrayList<>();
        Mapping mapping1 = new Mapping("preferred_username", "userName");
        mappingList.add(mapping1);
        Mapping mapping2 = new Mapping("email", "emails[primary eq true].value");
        mappingList.add(mapping2);
        scimConfigurationResponse.setMapping(mappingList);

        scimConfigurationResponse.setUpdatedOn("2025-01-22T11:56:24.461Z");
        scimConfigurationResponse.setCreatedAt("2025-01-22T11:56:24.461Z");
        scimConfigurationResponse.setUserIdAttribute("externalId");

        String serialized = toJSON(scimConfigurationResponse);
        assertThat(serialized, is(notNullValue()));

        assertThat(serialized, hasEntry("tenant_name", "dev-1"));
        assertThat(serialized, hasEntry("connection_id", "con_0000000000000001"));
        assertThat(serialized, hasEntry("connection_name", "New Connection"));
        assertThat(serialized, hasEntry("strategy", "okta"));
        assertThat(serialized, hasEntry("mapping", notNullValue()));
        assertThat(serialized, containsString("\"mapping\":[{\"auth0\":\"preferred_username\",\"scim\":\"userName\"},{\"auth0\":\"email\",\"scim\":\"emails[primary eq true].value\"}]"));
        assertThat(serialized, hasEntry("updated_on", "2025-01-22T11:56:24.461Z"));
        assertThat(serialized, hasEntry("created_at", "2025-01-22T11:56:24.461Z"));
        assertThat(serialized, hasEntry("user_id_attribute", "externalId"));
    }
}


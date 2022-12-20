package com.auth0.json.mgmt;

import java.util.ArrayList;
import java.util.List;

import com.auth0.json.JsonTest;
import com.auth0.json.mgmt.resourceserver.ResourceServer;
import com.auth0.json.mgmt.resourceserver.Scope;
import org.junit.Test;

import static com.auth0.json.JsonMatcher.hasEntry;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ResourceServerTest extends JsonTest<ResourceServer> {
    private final static String RESOURCE_SERVER_JSON = "src/test/resources/mgmt/resource_server.json";

    @Test
    public void deserialize() throws Exception {
        ResourceServer deserialized = fromJSON(readTextFile(RESOURCE_SERVER_JSON), ResourceServer.class);

        assertThat(deserialized.getId(), is("23445566abab"));
        assertThat(deserialized.getName(), is("Some API"));
        assertThat(deserialized.getIdentifier(), is("https://api.my-company.com/api/v2/"));
        assertThat(deserialized.getScopes(), hasSize(2));
        assertThat(deserialized.getSigningAlgorithm(), is("RS256"));
        assertThat(deserialized.getSigningSecret(), is("secret"));
        assertThat(deserialized.isSystem(), is(true));
        assertThat(deserialized.getAllowOfflineAccess(), is(false));
        assertThat(deserialized.getSkipConsentForVerifiableFirstPartyClients(), is(false));
        assertThat(deserialized.getTokenDialect(), is("access_token"));
        assertThat(deserialized.getTokenLifetime(), is(86400));
        assertThat(deserialized.getVerificationLocation(), is("verification_location"));
    }

    @Test
    public void serialize() throws Exception {
        ResourceServer entity = new ResourceServer("https://api.my-company.com/api/v2/");
        Scope scope1 = new Scope("read:client_grants");
        scope1.setDescription("Read Client Grants");
        Scope scope2 = new Scope("create:client_grants");
        scope2.setDescription("Create Client Grants");

        List<Scope> scopes = new ArrayList<>();
        scopes.add(scope1);
        scopes.add(scope2);
        entity.setId("23445566abab");
        entity.setName("Some API");
        entity.setScopes(scopes);
        entity.setSigningAlgorithm("RS256");
        entity.setSigningSecret("secret");
        entity.setEnforcePolicies(true);
        entity.setAllowOfflineAccess(false);
        entity.setSkipConsentForVerifiableFirstPartyClients(false);
        entity.setTokenLifetime(86400);
        entity.setTokenDialect("access_token_authz");
        entity.setVerificationLocation("verification_location");

        String json = toJSON(entity);

        assertThat(json, hasEntry("id", "23445566abab"));
        assertThat(json, hasEntry("name", "Some API"));
        assertThat(json, hasEntry("identifier", "https://api.my-company.com/api/v2/"));
        assertThat(json, hasEntry("signing_alg", "RS256"));
        assertThat(json, hasEntry("signing_secret", "secret"));
        assertThat(json, hasEntry("enforce_policies", true));
        assertThat(json, hasEntry("allow_offline_access", false));
        assertThat(json, hasEntry("skip_consent_for_verifiable_first_party_clients", false));
        assertThat(json, hasEntry("token_lifetime", 86400));
        assertThat(json, hasEntry("token_dialect", "access_token_authz"));
        assertThat(json, hasEntry("verification_location", "verification_location"));
    }
}

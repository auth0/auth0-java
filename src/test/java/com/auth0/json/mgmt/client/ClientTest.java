package com.auth0.json.mgmt.client;

import com.auth0.json.JsonMatcher;
import com.auth0.json.JsonTest;
import org.hamcrest.collection.IsMapContaining;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ClientTest extends JsonTest<Client> {

    private static final String json = "{\"name\":\"name\",\"client_secret\":\"secret\",\"app_type\":\"type\",\"logo_uri\":\"uri\",\"oidc_conformant\":true,\"callbacks\":[\"value\"],\"allowed_origins\":[\"value\"],\"client_aliases\":[\"value\"],\"allowed_clients\":[\"value\"],\"allowed_logout_urls\":[\"value\"],\"jwt_configuration\":{\"lifetime_in_seconds\":100,\"scopes\":\"openid\",\"alg\":\"alg\"},\"encryption_key\":{\"pub\":\"pub\",\"cert\":\"cert\"},\"sso\":true,\"sso_disabled\":true,\"custom_login_page_on\":true,\"custom_login_page\":\"custom\",\"custom_login_page_preview\":\"preview\",\"form_template\":\"template\",\"addons\":{\"rms\":{},\"mscrm\":{},\"slack\":{},\"layer\":{}},\"token_endpoint_auth_method\":\"method\",\"resource_servers\":[{\"identifier\":\"server\"}],\"client_metadata\":{\"key\":\"value\"},\"mobile\":{\"android\":{\"app_package_name\":\"pkg\",\"sha256_cert_fingerprints\":[\"256\"]},\"ios\":{\"team_id\":\"team\",\"app_bundle_identifier\":\"id\"}}}";
    private static final String readOnlyJson = "{\"client_id\":\"clientId\",\"is_first_party\":true,\"is_heroku_app\":true,\"signing_keys\":[{\"cert\":\"ce\",\"pkcs7\":\"pk\",\"subject\":\"su\"}]}";

    @Test
    public void shouldSerialize() throws Exception {
        Client client = new Client("ignored");

        client.setName("name");
        client.setClientSecret("secret");
        client.setAppType("type");
        client.setLogoUri("uri");
        client.setOIDCConformant(true);
        List<String> stringList = Arrays.asList("value");
        client.setCallbacks(stringList);
        client.setAllowedOrigins(stringList);
        client.setClientAliases(stringList);
        client.setAllowedClients(stringList);
        client.setAllowedLogoutUrls(stringList);
        JWTConfiguration jwtConfig = new JWTConfiguration(100, "openid", "alg");
        client.setJWTConfiguration(jwtConfig);
        EncryptionKey key = new EncryptionKey("pub", "cert");
        client.setEncryptionKey(key);
        client.setUseAuth0SSO(true);
        client.setSSODisabled(true);
        client.setUseCustomLoginPage(true);
        client.setCustomLoginPage("custom");
        client.setCustomLoginPagePreview("preview");
        client.setFormTemplate("template");
        Addons addons = new Addons(new Addon(), new Addon(), new Addon(), new Addon());
        client.setAddons(addons);
        client.setTokenEndpointAuthMethod("method");
        List<ResourceServer> serversList = Arrays.asList(new ResourceServer("server"));
        client.setResourceServers(serversList);
        Map<String, Object> metadata = Collections.singletonMap("key", (Object) "value");
        client.setClientMetadata(metadata);
        Mobile mobile = new Mobile(new Android("pkg", Arrays.asList("256")), new IOS("team", "id"));
        client.setMobile(mobile);

        String serialized = toJSON(client);
        assertThat(serialized, is(notNullValue()));

        assertThat(serialized, JsonMatcher.hasEntry("name", "name"));
        assertThat(serialized, JsonMatcher.hasEntry("client_secret", "secret"));
        assertThat(serialized, JsonMatcher.hasEntry("app_type", "type"));
        assertThat(serialized, JsonMatcher.hasEntry("logo_uri", "uri"));
        assertThat(serialized, JsonMatcher.hasEntry("oidc_conformant", true));
        assertThat(serialized, JsonMatcher.hasEntry("callbacks", Arrays.asList("value")));
        assertThat(serialized, JsonMatcher.hasEntry("allowed_origins", Arrays.asList("value")));
        assertThat(serialized, JsonMatcher.hasEntry("client_aliases", Arrays.asList("value")));
        assertThat(serialized, JsonMatcher.hasEntry("allowed_clients", Arrays.asList("value")));
        assertThat(serialized, JsonMatcher.hasEntry("allowed_logout_urls", Arrays.asList("value")));
        assertThat(serialized, JsonMatcher.hasEntry("jwt_configuration", notNullValue()));
        assertThat(serialized, JsonMatcher.hasEntry("encryption_key", notNullValue()));
        assertThat(serialized, JsonMatcher.hasEntry("sso", true));
        assertThat(serialized, JsonMatcher.hasEntry("sso_disabled", true));
        assertThat(serialized, JsonMatcher.hasEntry("custom_login_page_on", true));
        assertThat(serialized, JsonMatcher.hasEntry("custom_login_page", "custom"));
        assertThat(serialized, JsonMatcher.hasEntry("custom_login_page_preview", "preview"));
        assertThat(serialized, JsonMatcher.hasEntry("form_template", "template"));
        assertThat(serialized, JsonMatcher.hasEntry("addons", notNullValue()));
        assertThat(serialized, JsonMatcher.hasEntry("token_endpoint_auth_method", "method"));
        assertThat(serialized, JsonMatcher.hasEntry("resource_servers", notNullValue()));
        assertThat(serialized, JsonMatcher.hasEntry("client_metadata", notNullValue()));
        assertThat(serialized, JsonMatcher.hasEntry("mobile", notNullValue()));
    }

    @Test
    public void shouldDeserialize() throws Exception {
        Client client = fromJSON(json, Client.class);
        assertThat(client, is(notNullValue()));

        assertThat(client.getName(), is("name"));
        assertThat(client.getClientSecret(), is("secret"));
        assertThat(client.getAppType(), is("type"));
        assertThat(client.getLogoUri(), is("uri"));

        assertThat(client.isOIDCConformant(), is(true));

        assertThat(client.getCallbacks(), contains("value"));
        assertThat(client.getAllowedOrigins(), contains("value"));
        assertThat(client.getClientAliases(), contains("value"));
        assertThat(client.getAllowedClients(), contains("value"));
        assertThat(client.getAllowedLogoutUrls(), contains("value"));

        assertThat(client.getJWTConfiguration(), notNullValue());
        assertThat(client.getEncryptionKey(), notNullValue());

        assertThat(client.useAuth0SSO(), is(true));
        assertThat(client.isSSODisabled(), is(true));
        assertThat(client.useCustomLoginPage(), is(true));

        assertThat(client.getCustomLoginPage(), is("custom"));
        assertThat(client.getCustomLoginPagePreview(), is("preview"));
        assertThat(client.getFormTemplate(), is("template"));

        assertThat(client.getAddons(), is(notNullValue()));
        assertThat(client.getTokenEndpointAuthMethod(), is("method"));
        assertThat(client.getResourceServers(), is(notNullValue()));
        assertThat(client.getClientMetadata(), IsMapContaining.hasEntry("key", (Object) "value"));
        assertThat(client.getMobile(), is(notNullValue()));
    }

    @Test
    public void shouldIncludeReadOnlyValuesOnDeserialize() throws Exception {
        Client client = fromJSON(readOnlyJson, Client.class);
        assertThat(client, is(notNullValue()));

        assertThat(client.getClientId(), is("clientId"));
        assertThat(client.isFirstParty(), is(true));
        assertThat(client.isHerokuApp(), is(true));
        assertThat(client.getSigningKeys(), is(notNullValue()));
    }
}
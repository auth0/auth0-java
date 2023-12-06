package com.auth0.json.mgmt.client;

import com.auth0.json.JsonMatcher;
import com.auth0.json.JsonTest;
import org.hamcrest.collection.IsMapContaining;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ClientTest extends JsonTest<Client> {

    private static final String readOnlyJson = "{\"client_id\":\"clientId\",\"is_heroku_app\":true,\"signing_keys\":[{\"cert\":\"ce\",\"pkcs7\":\"pk\",\"subject\":\"su\"}]}";
    private static final String json = "{\n" +
        "  \"name\": \"name\",\n" +
        "  \"description\": \"description\",\n" +
        "  \"client_secret\": \"secret\",\n" +
        "  \"app_type\": \"type\",\n" +
        "  \"logo_uri\": \"uri\",\n" +
        "  \"oidc_conformant\": true,\n" +
        "  \"is_first_party\": true,\n" +
        "  \"initiate_login_uri\": \"https://myhome.com/login\",\n" +
        "  \"callbacks\": [\n" +
        "    \"value\"\n" +
        "  ],\n" +
        "  \"allowed_origins\": [\n" +
        "    \"value\"\n" +
        "  ],\n" +
        "  \"web_origins\": [\n" +
        "    \"value\"\n" +
        "  ],\n" +
        "  \"grant_types\": [\n" +
        "    \"value\"\n" +
        "  ],\n" +
        "  \"client_aliases\": [\n" +
        "    \"value\"\n" +
        "  ],\n" +
        "  \"allowed_clients\": [\n" +
        "    \"value\"\n" +
        "  ],\n" +
        "  \"allowed_logout_urls\": [\n" +
        "    \"value\"\n" +
        "  ],\n" +
        "  \"organization_usage\": \"allow\",\n" +
        "  \"organization_require_behavior\": \"no_prompt\",\n" +
        "  \"jwt_configuration\": {\n" +
        "    \"lifetime_in_seconds\": 100,\n" +
        "    \"scopes\": \"openid\",\n" +
        "    \"alg\": \"alg\"\n" +
        "  },\n" +
        "  \"encryption_key\": {\n" +
        "    \"pub\": \"pub\",\n" +
        "    \"cert\": \"cert\"\n" +
        "  },\n" +
        "  \"sso\": true,\n" +
        "  \"sso_disabled\": true,\n" +
        "  \"custom_login_page_on\": true,\n" +
        "  \"custom_login_page\": \"custom\",\n" +
        "  \"custom_login_page_preview\": \"preview\",\n" +
        "  \"form_template\": \"template\",\n" +
        "  \"addons\": {\n" +
        "    \"rms\": {},\n" +
        "    \"mscrm\": {},\n" +
        "    \"slack\": {},\n" +
        "    \"layer\": {}\n" +
        "  },\n" +
        "  \"token_endpoint_auth_method\": \"method\",\n" +
        "  \"client_metadata\": {\n" +
        "    \"key\": \"value\"\n" +
        "  },\n" +
        "  \"mobile\": {\n" +
        "    \"android\": {\n" +
        "      \"app_package_name\": \"pkg\",\n" +
        "      \"sha256_cert_fingerprints\": [\n" +
        "        \"256\"\n" +
        "      ]\n" +
        "    },\n" +
        "    \"ios\": {\n" +
        "      \"team_id\": \"team\",\n" +
        "      \"app_bundle_identifier\": \"id\"\n" +
        "    }\n" +
        "  },\n" +
        "  \"refresh_token\": {\n" +
        "    \"rotation_type\": \"non-rotating\"\n" +
        "  },\n" +
        "  \"client_authentication_methods\": {\n" +
        "    \"private_key_jwt\": {\n" +
        "      \"credentials\": [\n" +
        "        {\n" +
        "          \"id\": \"cred_abc\"\n" +
        "        },\n" +
        "        {\n" +
        "          \"id\": \"cred_123\"\n" +
        "        }\n" +
        "      ]\n" +
        "    }\n" +
        "  },\n" +
        "  \"require_pushed_authorization_requests\": true,\n" +
        "  \"oidc_backchannel_logout\": {\n" +
        "     \"backchannel_logout_urls\": [\"http://acme.eu.auth0.com/events\"]\n" +
        "  }\n" +
        "}";

    @Test
    public void shouldSerialize() throws Exception {
        Client client = new Client("ignored");

        client.setName("name");
        client.setDescription("description");
        client.setClientSecret("secret");
        client.setAppType("type");
        client.setLogoUri("uri");
        client.setOIDCConformant(true);
        client.setIsFirstParty(true);
        List<String> stringList = Collections.singletonList("value");
        client.setCallbacks(stringList);
        client.setAllowedOrigins(stringList);
        client.setWebOrigins(stringList);
        client.setGrantTypes(stringList);
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
        client.setInitiateLoginUri("https://appzero.com/login");
        client.setCustomLoginPagePreview("preview");
        client.setFormTemplate("template");
        Addons addons = new Addons(new Addon(), new Addon(), new Addon(), new Addon());
        client.setAddons(addons);
        client.setTokenEndpointAuthMethod("method");
        Map<String, Object> metadata = Collections.singletonMap("key", "value");
        client.setClientMetadata(metadata);
        Mobile mobile = new Mobile(new Android("pkg", Collections.singletonList("256")), new IOS("team", "id"));
        client.setMobile(mobile);
        RefreshToken refreshToken = new RefreshToken();
        client.setRefreshToken(refreshToken);
        client.setOrganizationUsage("require");
        client.setOrganizationRequireBehavior("pre_login_prompt");
        Credential credential = new Credential("public_key", "PEM");
        PrivateKeyJwt privateKeyJwt = new PrivateKeyJwt(Collections.singletonList(credential));
        ClientAuthenticationMethods cam = new ClientAuthenticationMethods(privateKeyJwt);
        client.setClientAuthenticationMethods(cam);
        client.setRequiresPushedAuthorizationRequests(true);
        client.setOidcBackchannelLogout(new OIDCBackchannelLogout(Collections.singletonList("http://acme.eu.auth0.com/events")));

        String serialized = toJSON(client);
        assertThat(serialized, is(notNullValue()));

        assertThat(serialized, JsonMatcher.hasEntry("name", "name"));
        assertThat(serialized, JsonMatcher.hasEntry("description", "description"));
        assertThat(serialized, JsonMatcher.hasEntry("client_secret", "secret"));
        assertThat(serialized, JsonMatcher.hasEntry("app_type", "type"));
        assertThat(serialized, JsonMatcher.hasEntry("logo_uri", "uri"));
        assertThat(serialized, JsonMatcher.hasEntry("oidc_conformant", true));
        assertThat(serialized, JsonMatcher.hasEntry("initiate_login_uri", "https://appzero.com/login"));
        assertThat(serialized, JsonMatcher.hasEntry("is_first_party", true));
        assertThat(serialized, JsonMatcher.hasEntry("callbacks", Collections.singletonList("value")));
        assertThat(serialized, JsonMatcher.hasEntry("grant_types", Collections.singletonList("value")));
        assertThat(serialized, JsonMatcher.hasEntry("allowed_origins", Collections.singletonList("value")));
        assertThat(serialized, JsonMatcher.hasEntry("client_aliases", Collections.singletonList("value")));
        assertThat(serialized, JsonMatcher.hasEntry("allowed_clients", Collections.singletonList("value")));
        assertThat(serialized, JsonMatcher.hasEntry("allowed_logout_urls", Collections.singletonList("value")));
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
        assertThat(serialized, JsonMatcher.hasEntry("client_metadata", notNullValue()));
        assertThat(serialized, JsonMatcher.hasEntry("mobile", notNullValue()));
        assertThat(serialized, JsonMatcher.hasEntry("refresh_token", notNullValue()));
        assertThat(serialized, JsonMatcher.hasEntry("organization_usage", "require"));
        assertThat(serialized, JsonMatcher.hasEntry("organization_require_behavior", "pre_login_prompt"));
        assertThat(serialized, JsonMatcher.hasEntry("client_authentication_methods", notNullValue()));
        assertThat(serialized, JsonMatcher.hasEntry("client_authentication_methods", containsString("{\"private_key_jwt\":{\"credentials\":[{\"credential_type\":\"public_key\",\"pem\":\"PEM\"}]}}")));
        assertThat(serialized, JsonMatcher.hasEntry("require_pushed_authorization_requests", true));
        assertThat(serialized, JsonMatcher.hasEntry("oidc_backchannel_logout", containsString("{\"backchannel_logout_urls\":[\"http://acme.eu.auth0.com/events\"]}")));
    }

    @Test
    public void shouldDeserialize() throws Exception {
        Client client = fromJSON(json, Client.class);
        assertThat(client, is(notNullValue()));

        assertThat(client.getName(), is("name"));
        assertThat(client.getDescription(), is("description"));
        assertThat(client.getClientSecret(), is("secret"));
        assertThat(client.getAppType(), is("type"));
        assertThat(client.getLogoUri(), is("uri"));

        assertThat(client.isOIDCConformant(), is(true));
        assertThat(client.isFirstParty(), is(true));

        assertThat(client.getCallbacks(), contains("value"));
        assertThat(client.getWebOrigins(), contains("value"));
        assertThat(client.getGrantTypes(), contains("value"));
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
        assertThat(client.getInitiateLoginUri(), is("https://myhome.com/login"));
        assertThat(client.getFormTemplate(), is("template"));

        assertThat(client.getAddons(), is(notNullValue()));
        assertThat(client.getTokenEndpointAuthMethod(), is("method"));
        assertThat(client.getClientMetadata(), IsMapContaining.hasEntry("key", "value"));
        assertThat(client.getMobile(), is(notNullValue()));
        assertThat(client.getRefreshToken(), is(notNullValue()));
        assertThat(client.getOrganizationUsage(), is("allow"));
        assertThat(client.getOrganizationRequireBehavior(), is("no_prompt"));

        assertThat(client.getClientAuthenticationMethods(), is(notNullValue()));
        assertThat(client.getClientAuthenticationMethods().getPrivateKeyJwt(), is(notNullValue()));
        assertThat(client.getClientAuthenticationMethods().getPrivateKeyJwt().getCredentials(), is(notNullValue()));
        assertThat(client.getClientAuthenticationMethods().getPrivateKeyJwt().getCredentials().size(), is(2));
        assertThat(client.getClientAuthenticationMethods().getPrivateKeyJwt().getCredentials().get(0).getId(), is("cred_abc"));
        assertThat(client.getClientAuthenticationMethods().getPrivateKeyJwt().getCredentials().get(1).getId(), is("cred_123"));
        assertThat(client.getRequiresPushedAuthorizationRequests(), is(true));
        assertThat(client.getOidcBackchannelLogout().getBackchannelLogoutUrls().size(), is(1));
    }

    @Test
    public void shouldIncludeReadOnlyValuesOnDeserialize() throws Exception {
        Client client = fromJSON(readOnlyJson, Client.class);
        assertThat(client, is(notNullValue()));

        assertThat(client.getClientId(), is("clientId"));
        assertThat(client.isHerokuApp(), is(true));
        assertThat(client.getSigningKeys(), is(notNullValue()));
    }
}

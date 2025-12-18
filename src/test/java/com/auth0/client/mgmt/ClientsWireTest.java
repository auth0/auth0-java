package com.auth0.client.mgmt;

import com.auth0.client.mgmt.core.ObjectMappers;
import com.auth0.client.mgmt.core.OptionalNullable;
import com.auth0.client.mgmt.core.SyncPagingIterable;
import com.auth0.client.mgmt.types.Client;
import com.auth0.client.mgmt.types.CreateClientRequestContent;
import com.auth0.client.mgmt.types.CreateClientResponseContent;
import com.auth0.client.mgmt.types.GetClientRequestParameters;
import com.auth0.client.mgmt.types.GetClientResponseContent;
import com.auth0.client.mgmt.types.ListClientsRequestParameters;
import com.auth0.client.mgmt.types.RotateClientSecretResponseContent;
import com.auth0.client.mgmt.types.UpdateClientRequestContent;
import com.auth0.client.mgmt.types.UpdateClientResponseContent;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ClientsWireTest {
    private MockWebServer server;
    private ManagementApi client;
    private ObjectMapper objectMapper = ObjectMappers.JSON_MAPPER;

    @BeforeEach
    public void setup() throws Exception {
        server = new MockWebServer();
        server.start();
        client = ManagementApi.builder()
                .url(server.url("/").toString())
                .token("test-token")
                .build();
    }

    @AfterEach
    public void teardown() throws Exception {
        server.shutdown();
    }

    @Test
    public void testList() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"start\":1.1,\"limit\":1.1,\"total\":1.1,\"clients\":[{\"client_id\":\"client_id\",\"tenant\":\"tenant\",\"name\":\"name\",\"description\":\"description\",\"global\":true,\"client_secret\":\"client_secret\",\"app_type\":\"native\",\"logo_uri\":\"logo_uri\",\"is_first_party\":true,\"oidc_conformant\":true,\"callbacks\":[\"callbacks\"],\"allowed_origins\":[\"allowed_origins\"],\"web_origins\":[\"web_origins\"],\"client_aliases\":[\"client_aliases\"],\"allowed_clients\":[\"allowed_clients\"],\"allowed_logout_urls\":[\"allowed_logout_urls\"],\"grant_types\":[\"grant_types\"],\"signing_keys\":[{}],\"sso\":true,\"sso_disabled\":true,\"cross_origin_authentication\":true,\"cross_origin_loc\":\"cross_origin_loc\",\"custom_login_page_on\":true,\"custom_login_page\":\"custom_login_page\",\"custom_login_page_preview\":\"custom_login_page_preview\",\"form_template\":\"form_template\",\"token_endpoint_auth_method\":\"none\",\"is_token_endpoint_ip_header_trusted\":true,\"client_metadata\":{\"key\":\"value\"},\"initiate_login_uri\":\"initiate_login_uri\",\"refresh_token\":{\"rotation_type\":\"rotating\",\"expiration_type\":\"expiring\"},\"default_organization\":{\"organization_id\":\"organization_id\",\"flows\":[\"client_credentials\"]},\"organization_usage\":\"deny\",\"organization_require_behavior\":\"no_prompt\",\"organization_discovery_methods\":[\"email\"],\"require_pushed_authorization_requests\":true,\"require_proof_of_possession\":true,\"compliance_level\":\"none\",\"skip_non_verifiable_callback_uri_confirmation_prompt\":true,\"par_request_expiry\":1,\"token_quota\":{\"client_credentials\":{}},\"express_configuration\":{\"initiate_login_uri_template\":\"initiate_login_uri_template\",\"user_attribute_profile_id\":\"user_attribute_profile_id\",\"connection_profile_id\":\"connection_profile_id\",\"enable_client\":true,\"enable_organization\":true,\"okta_oin_client_id\":\"okta_oin_client_id\",\"admin_login_domain\":\"admin_login_domain\"},\"resource_server_identifier\":\"resource_server_identifier\",\"async_approval_notification_channels\":[\"guardian-push\"]}]}"));
        SyncPagingIterable<Client> response = client.clients()
                .list(ListClientsRequestParameters.builder()
                        .fields(OptionalNullable.of("fields"))
                        .includeFields(OptionalNullable.of(true))
                        .page(OptionalNullable.of(1))
                        .perPage(OptionalNullable.of(1))
                        .includeTotals(OptionalNullable.of(true))
                        .isGlobal(OptionalNullable.of(true))
                        .isFirstParty(OptionalNullable.of(true))
                        .appType(OptionalNullable.of("app_type"))
                        .q(OptionalNullable.of("q"))
                        .build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("GET", request.getMethod());

        // Validate response body
        Assertions.assertNotNull(response, "Response should not be null");
        // Pagination response validated via MockWebServer
        // The SDK correctly parses the response into a SyncPagingIterable
    }

    @Test
    public void testCreate() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"client_id\":\"client_id\",\"tenant\":\"tenant\",\"name\":\"name\",\"description\":\"description\",\"global\":true,\"client_secret\":\"client_secret\",\"app_type\":\"native\",\"logo_uri\":\"logo_uri\",\"is_first_party\":true,\"oidc_conformant\":true,\"callbacks\":[\"callbacks\"],\"allowed_origins\":[\"allowed_origins\"],\"web_origins\":[\"web_origins\"],\"client_aliases\":[\"client_aliases\"],\"allowed_clients\":[\"allowed_clients\"],\"allowed_logout_urls\":[\"allowed_logout_urls\"],\"session_transfer\":{\"can_create_session_transfer_token\":true,\"enforce_cascade_revocation\":true,\"allowed_authentication_methods\":[\"cookie\"],\"enforce_device_binding\":\"ip\",\"allow_refresh_token\":true,\"enforce_online_refresh_tokens\":true},\"oidc_logout\":{\"backchannel_logout_urls\":[\"backchannel_logout_urls\"],\"backchannel_logout_initiators\":{\"mode\":\"custom\",\"selected_initiators\":[\"rp-logout\"]},\"backchannel_logout_session_metadata\":{\"include\":true}},\"grant_types\":[\"grant_types\"],\"jwt_configuration\":{\"lifetime_in_seconds\":1,\"secret_encoded\":true,\"scopes\":{\"key\":\"value\"},\"alg\":\"HS256\"},\"signing_keys\":[{\"pkcs7\":\"pkcs7\",\"cert\":\"cert\",\"subject\":\"subject\"}],\"encryption_key\":{\"pub\":\"pub\",\"cert\":\"cert\",\"subject\":\"subject\"},\"sso\":true,\"sso_disabled\":true,\"cross_origin_authentication\":true,\"cross_origin_loc\":\"cross_origin_loc\",\"custom_login_page_on\":true,\"custom_login_page\":\"custom_login_page\",\"custom_login_page_preview\":\"custom_login_page_preview\",\"form_template\":\"form_template\",\"addons\":{\"aws\":{\"principal\":\"principal\",\"role\":\"role\",\"lifetime_in_seconds\":1},\"azure_blob\":{\"accountName\":\"accountName\",\"storageAccessKey\":\"storageAccessKey\",\"containerName\":\"containerName\",\"blobName\":\"blobName\",\"expiration\":1,\"signedIdentifier\":\"signedIdentifier\",\"blob_read\":true,\"blob_write\":true,\"blob_delete\":true,\"container_read\":true,\"container_write\":true,\"container_delete\":true,\"container_list\":true},\"azure_sb\":{\"namespace\":\"namespace\",\"sasKeyName\":\"sasKeyName\",\"sasKey\":\"sasKey\",\"entityPath\":\"entityPath\",\"expiration\":1},\"rms\":{\"url\":\"url\"},\"mscrm\":{\"url\":\"url\"},\"slack\":{\"team\":\"team\"},\"sentry\":{\"org_slug\":\"org_slug\",\"base_url\":\"base_url\"},\"box\":{\"key\":\"value\"},\"cloudbees\":{\"key\":\"value\"},\"concur\":{\"key\":\"value\"},\"dropbox\":{\"key\":\"value\"},\"echosign\":{\"domain\":\"domain\"},\"egnyte\":{\"domain\":\"domain\"},\"firebase\":{\"secret\":\"secret\",\"private_key_id\":\"private_key_id\",\"private_key\":\"private_key\",\"client_email\":\"client_email\",\"lifetime_in_seconds\":1},\"newrelic\":{\"account\":\"account\"},\"office365\":{\"domain\":\"domain\",\"connection\":\"connection\"},\"salesforce\":{\"entity_id\":\"entity_id\"},\"salesforce_api\":{\"clientid\":\"clientid\",\"principal\":\"principal\",\"communityName\":\"communityName\",\"community_url_section\":\"community_url_section\"},\"salesforce_sandbox_api\":{\"clientid\":\"clientid\",\"principal\":\"principal\",\"communityName\":\"communityName\",\"community_url_section\":\"community_url_section\"},\"samlp\":{\"mappings\":{\"key\":\"value\"},\"audience\":\"audience\",\"recipient\":\"recipient\",\"createUpnClaim\":true,\"mapUnknownClaimsAsIs\":true,\"passthroughClaimsWithNoMapping\":true,\"mapIdentities\":true,\"signatureAlgorithm\":\"signatureAlgorithm\",\"digestAlgorithm\":\"digestAlgorithm\",\"issuer\":\"issuer\",\"destination\":\"destination\",\"lifetimeInSeconds\":1,\"signResponse\":true,\"nameIdentifierFormat\":\"nameIdentifierFormat\",\"nameIdentifierProbes\":[\"nameIdentifierProbes\"],\"authnContextClassRef\":\"authnContextClassRef\"},\"layer\":{\"providerId\":\"providerId\",\"keyId\":\"keyId\",\"privateKey\":\"privateKey\",\"principal\":\"principal\",\"expiration\":1},\"sap_api\":{\"clientid\":\"clientid\",\"usernameAttribute\":\"usernameAttribute\",\"tokenEndpointUrl\":\"tokenEndpointUrl\",\"scope\":\"scope\",\"servicePassword\":\"servicePassword\",\"nameIdentifierFormat\":\"nameIdentifierFormat\"},\"sharepoint\":{\"url\":\"url\",\"external_url\":[\"external_url\"]},\"springcm\":{\"acsurl\":\"acsurl\"},\"wams\":{\"masterkey\":\"masterkey\"},\"wsfed\":{\"key\":\"value\"},\"zendesk\":{\"accountName\":\"accountName\"},\"zoom\":{\"account\":\"account\"},\"sso_integration\":{\"name\":\"name\",\"version\":\"version\"}},\"token_endpoint_auth_method\":\"none\",\"is_token_endpoint_ip_header_trusted\":true,\"client_metadata\":{\"key\":\"value\"},\"mobile\":{\"android\":{\"app_package_name\":\"app_package_name\",\"sha256_cert_fingerprints\":[\"sha256_cert_fingerprints\"]},\"ios\":{\"team_id\":\"team_id\",\"app_bundle_identifier\":\"app_bundle_identifier\"}},\"initiate_login_uri\":\"initiate_login_uri\",\"refresh_token\":{\"rotation_type\":\"rotating\",\"expiration_type\":\"expiring\",\"leeway\":1,\"token_lifetime\":1,\"infinite_token_lifetime\":true,\"idle_token_lifetime\":1,\"infinite_idle_token_lifetime\":true,\"policies\":[{\"audience\":\"audience\",\"scope\":[\"scope\"]}]},\"default_organization\":{\"organization_id\":\"organization_id\",\"flows\":[\"client_credentials\"]},\"organization_usage\":\"deny\",\"organization_require_behavior\":\"no_prompt\",\"organization_discovery_methods\":[\"email\"],\"client_authentication_methods\":{\"private_key_jwt\":{\"credentials\":[{\"id\":\"id\"}]},\"tls_client_auth\":{\"credentials\":[{\"id\":\"id\"}]},\"self_signed_tls_client_auth\":{\"credentials\":[{\"id\":\"id\"}]}},\"require_pushed_authorization_requests\":true,\"require_proof_of_possession\":true,\"signed_request_object\":{\"required\":true,\"credentials\":[{\"id\":\"id\"}]},\"compliance_level\":\"none\",\"skip_non_verifiable_callback_uri_confirmation_prompt\":true,\"token_exchange\":{\"allow_any_profile_of_type\":[\"custom_authentication\"]},\"par_request_expiry\":1,\"token_quota\":{\"client_credentials\":{\"enforce\":true,\"per_day\":1,\"per_hour\":1}},\"express_configuration\":{\"initiate_login_uri_template\":\"initiate_login_uri_template\",\"user_attribute_profile_id\":\"user_attribute_profile_id\",\"connection_profile_id\":\"connection_profile_id\",\"enable_client\":true,\"enable_organization\":true,\"linked_clients\":[{\"client_id\":\"client_id\"}],\"okta_oin_client_id\":\"okta_oin_client_id\",\"admin_login_domain\":\"admin_login_domain\",\"oin_submission_id\":\"oin_submission_id\"},\"resource_server_identifier\":\"resource_server_identifier\",\"async_approval_notification_channels\":[\"guardian-push\"]}"));
        CreateClientResponseContent response = client.clients()
                .create(CreateClientRequestContent.builder().name("name").build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("POST", request.getMethod());
        // Validate request body
        String actualRequestBody = request.getBody().readUtf8();
        String expectedRequestBody = "" + "{\n" + "  \"name\": \"name\"\n" + "}";
        JsonNode actualJson = objectMapper.readTree(actualRequestBody);
        JsonNode expectedJson = objectMapper.readTree(expectedRequestBody);
        Assertions.assertTrue(jsonEquals(expectedJson, actualJson), "Request body structure does not match expected");
        if (actualJson.has("type") || actualJson.has("_type") || actualJson.has("kind")) {
            String discriminator = null;
            if (actualJson.has("type")) discriminator = actualJson.get("type").asText();
            else if (actualJson.has("_type"))
                discriminator = actualJson.get("_type").asText();
            else if (actualJson.has("kind"))
                discriminator = actualJson.get("kind").asText();
            Assertions.assertNotNull(discriminator, "Union type should have a discriminator field");
            Assertions.assertFalse(discriminator.isEmpty(), "Union discriminator should not be empty");
        }

        if (!actualJson.isNull()) {
            Assertions.assertTrue(
                    actualJson.isObject() || actualJson.isArray() || actualJson.isValueNode(),
                    "request should be a valid JSON value");
        }

        if (actualJson.isArray()) {
            Assertions.assertTrue(actualJson.size() >= 0, "Array should have valid size");
        }
        if (actualJson.isObject()) {
            Assertions.assertTrue(actualJson.size() >= 0, "Object should have valid field count");
        }

        // Validate response body
        Assertions.assertNotNull(response, "Response should not be null");
        String actualResponseJson = objectMapper.writeValueAsString(response);
        String expectedResponseBody = ""
                + "{\n"
                + "  \"client_id\": \"client_id\",\n"
                + "  \"tenant\": \"tenant\",\n"
                + "  \"name\": \"name\",\n"
                + "  \"description\": \"description\",\n"
                + "  \"global\": true,\n"
                + "  \"client_secret\": \"client_secret\",\n"
                + "  \"app_type\": \"native\",\n"
                + "  \"logo_uri\": \"logo_uri\",\n"
                + "  \"is_first_party\": true,\n"
                + "  \"oidc_conformant\": true,\n"
                + "  \"callbacks\": [\n"
                + "    \"callbacks\"\n"
                + "  ],\n"
                + "  \"allowed_origins\": [\n"
                + "    \"allowed_origins\"\n"
                + "  ],\n"
                + "  \"web_origins\": [\n"
                + "    \"web_origins\"\n"
                + "  ],\n"
                + "  \"client_aliases\": [\n"
                + "    \"client_aliases\"\n"
                + "  ],\n"
                + "  \"allowed_clients\": [\n"
                + "    \"allowed_clients\"\n"
                + "  ],\n"
                + "  \"allowed_logout_urls\": [\n"
                + "    \"allowed_logout_urls\"\n"
                + "  ],\n"
                + "  \"session_transfer\": {\n"
                + "    \"can_create_session_transfer_token\": true,\n"
                + "    \"enforce_cascade_revocation\": true,\n"
                + "    \"allowed_authentication_methods\": [\n"
                + "      \"cookie\"\n"
                + "    ],\n"
                + "    \"enforce_device_binding\": \"ip\",\n"
                + "    \"allow_refresh_token\": true,\n"
                + "    \"enforce_online_refresh_tokens\": true\n"
                + "  },\n"
                + "  \"oidc_logout\": {\n"
                + "    \"backchannel_logout_urls\": [\n"
                + "      \"backchannel_logout_urls\"\n"
                + "    ],\n"
                + "    \"backchannel_logout_initiators\": {\n"
                + "      \"mode\": \"custom\",\n"
                + "      \"selected_initiators\": [\n"
                + "        \"rp-logout\"\n"
                + "      ]\n"
                + "    },\n"
                + "    \"backchannel_logout_session_metadata\": {\n"
                + "      \"include\": true\n"
                + "    }\n"
                + "  },\n"
                + "  \"grant_types\": [\n"
                + "    \"grant_types\"\n"
                + "  ],\n"
                + "  \"jwt_configuration\": {\n"
                + "    \"lifetime_in_seconds\": 1,\n"
                + "    \"secret_encoded\": true,\n"
                + "    \"scopes\": {\n"
                + "      \"key\": \"value\"\n"
                + "    },\n"
                + "    \"alg\": \"HS256\"\n"
                + "  },\n"
                + "  \"signing_keys\": [\n"
                + "    {\n"
                + "      \"pkcs7\": \"pkcs7\",\n"
                + "      \"cert\": \"cert\",\n"
                + "      \"subject\": \"subject\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"encryption_key\": {\n"
                + "    \"pub\": \"pub\",\n"
                + "    \"cert\": \"cert\",\n"
                + "    \"subject\": \"subject\"\n"
                + "  },\n"
                + "  \"sso\": true,\n"
                + "  \"sso_disabled\": true,\n"
                + "  \"cross_origin_authentication\": true,\n"
                + "  \"cross_origin_loc\": \"cross_origin_loc\",\n"
                + "  \"custom_login_page_on\": true,\n"
                + "  \"custom_login_page\": \"custom_login_page\",\n"
                + "  \"custom_login_page_preview\": \"custom_login_page_preview\",\n"
                + "  \"form_template\": \"form_template\",\n"
                + "  \"addons\": {\n"
                + "    \"aws\": {\n"
                + "      \"principal\": \"principal\",\n"
                + "      \"role\": \"role\",\n"
                + "      \"lifetime_in_seconds\": 1\n"
                + "    },\n"
                + "    \"azure_blob\": {\n"
                + "      \"accountName\": \"accountName\",\n"
                + "      \"storageAccessKey\": \"storageAccessKey\",\n"
                + "      \"containerName\": \"containerName\",\n"
                + "      \"blobName\": \"blobName\",\n"
                + "      \"expiration\": 1,\n"
                + "      \"signedIdentifier\": \"signedIdentifier\",\n"
                + "      \"blob_read\": true,\n"
                + "      \"blob_write\": true,\n"
                + "      \"blob_delete\": true,\n"
                + "      \"container_read\": true,\n"
                + "      \"container_write\": true,\n"
                + "      \"container_delete\": true,\n"
                + "      \"container_list\": true\n"
                + "    },\n"
                + "    \"azure_sb\": {\n"
                + "      \"namespace\": \"namespace\",\n"
                + "      \"sasKeyName\": \"sasKeyName\",\n"
                + "      \"sasKey\": \"sasKey\",\n"
                + "      \"entityPath\": \"entityPath\",\n"
                + "      \"expiration\": 1\n"
                + "    },\n"
                + "    \"rms\": {\n"
                + "      \"url\": \"url\"\n"
                + "    },\n"
                + "    \"mscrm\": {\n"
                + "      \"url\": \"url\"\n"
                + "    },\n"
                + "    \"slack\": {\n"
                + "      \"team\": \"team\"\n"
                + "    },\n"
                + "    \"sentry\": {\n"
                + "      \"org_slug\": \"org_slug\",\n"
                + "      \"base_url\": \"base_url\"\n"
                + "    },\n"
                + "    \"box\": {\n"
                + "      \"key\": \"value\"\n"
                + "    },\n"
                + "    \"cloudbees\": {\n"
                + "      \"key\": \"value\"\n"
                + "    },\n"
                + "    \"concur\": {\n"
                + "      \"key\": \"value\"\n"
                + "    },\n"
                + "    \"dropbox\": {\n"
                + "      \"key\": \"value\"\n"
                + "    },\n"
                + "    \"echosign\": {\n"
                + "      \"domain\": \"domain\"\n"
                + "    },\n"
                + "    \"egnyte\": {\n"
                + "      \"domain\": \"domain\"\n"
                + "    },\n"
                + "    \"firebase\": {\n"
                + "      \"secret\": \"secret\",\n"
                + "      \"private_key_id\": \"private_key_id\",\n"
                + "      \"private_key\": \"private_key\",\n"
                + "      \"client_email\": \"client_email\",\n"
                + "      \"lifetime_in_seconds\": 1\n"
                + "    },\n"
                + "    \"newrelic\": {\n"
                + "      \"account\": \"account\"\n"
                + "    },\n"
                + "    \"office365\": {\n"
                + "      \"domain\": \"domain\",\n"
                + "      \"connection\": \"connection\"\n"
                + "    },\n"
                + "    \"salesforce\": {\n"
                + "      \"entity_id\": \"entity_id\"\n"
                + "    },\n"
                + "    \"salesforce_api\": {\n"
                + "      \"clientid\": \"clientid\",\n"
                + "      \"principal\": \"principal\",\n"
                + "      \"communityName\": \"communityName\",\n"
                + "      \"community_url_section\": \"community_url_section\"\n"
                + "    },\n"
                + "    \"salesforce_sandbox_api\": {\n"
                + "      \"clientid\": \"clientid\",\n"
                + "      \"principal\": \"principal\",\n"
                + "      \"communityName\": \"communityName\",\n"
                + "      \"community_url_section\": \"community_url_section\"\n"
                + "    },\n"
                + "    \"samlp\": {\n"
                + "      \"mappings\": {\n"
                + "        \"key\": \"value\"\n"
                + "      },\n"
                + "      \"audience\": \"audience\",\n"
                + "      \"recipient\": \"recipient\",\n"
                + "      \"createUpnClaim\": true,\n"
                + "      \"mapUnknownClaimsAsIs\": true,\n"
                + "      \"passthroughClaimsWithNoMapping\": true,\n"
                + "      \"mapIdentities\": true,\n"
                + "      \"signatureAlgorithm\": \"signatureAlgorithm\",\n"
                + "      \"digestAlgorithm\": \"digestAlgorithm\",\n"
                + "      \"issuer\": \"issuer\",\n"
                + "      \"destination\": \"destination\",\n"
                + "      \"lifetimeInSeconds\": 1,\n"
                + "      \"signResponse\": true,\n"
                + "      \"nameIdentifierFormat\": \"nameIdentifierFormat\",\n"
                + "      \"nameIdentifierProbes\": [\n"
                + "        \"nameIdentifierProbes\"\n"
                + "      ],\n"
                + "      \"authnContextClassRef\": \"authnContextClassRef\"\n"
                + "    },\n"
                + "    \"layer\": {\n"
                + "      \"providerId\": \"providerId\",\n"
                + "      \"keyId\": \"keyId\",\n"
                + "      \"privateKey\": \"privateKey\",\n"
                + "      \"principal\": \"principal\",\n"
                + "      \"expiration\": 1\n"
                + "    },\n"
                + "    \"sap_api\": {\n"
                + "      \"clientid\": \"clientid\",\n"
                + "      \"usernameAttribute\": \"usernameAttribute\",\n"
                + "      \"tokenEndpointUrl\": \"tokenEndpointUrl\",\n"
                + "      \"scope\": \"scope\",\n"
                + "      \"servicePassword\": \"servicePassword\",\n"
                + "      \"nameIdentifierFormat\": \"nameIdentifierFormat\"\n"
                + "    },\n"
                + "    \"sharepoint\": {\n"
                + "      \"url\": \"url\",\n"
                + "      \"external_url\": [\n"
                + "        \"external_url\"\n"
                + "      ]\n"
                + "    },\n"
                + "    \"springcm\": {\n"
                + "      \"acsurl\": \"acsurl\"\n"
                + "    },\n"
                + "    \"wams\": {\n"
                + "      \"masterkey\": \"masterkey\"\n"
                + "    },\n"
                + "    \"wsfed\": {\n"
                + "      \"key\": \"value\"\n"
                + "    },\n"
                + "    \"zendesk\": {\n"
                + "      \"accountName\": \"accountName\"\n"
                + "    },\n"
                + "    \"zoom\": {\n"
                + "      \"account\": \"account\"\n"
                + "    },\n"
                + "    \"sso_integration\": {\n"
                + "      \"name\": \"name\",\n"
                + "      \"version\": \"version\"\n"
                + "    }\n"
                + "  },\n"
                + "  \"token_endpoint_auth_method\": \"none\",\n"
                + "  \"is_token_endpoint_ip_header_trusted\": true,\n"
                + "  \"client_metadata\": {\n"
                + "    \"key\": \"value\"\n"
                + "  },\n"
                + "  \"mobile\": {\n"
                + "    \"android\": {\n"
                + "      \"app_package_name\": \"app_package_name\",\n"
                + "      \"sha256_cert_fingerprints\": [\n"
                + "        \"sha256_cert_fingerprints\"\n"
                + "      ]\n"
                + "    },\n"
                + "    \"ios\": {\n"
                + "      \"team_id\": \"team_id\",\n"
                + "      \"app_bundle_identifier\": \"app_bundle_identifier\"\n"
                + "    }\n"
                + "  },\n"
                + "  \"initiate_login_uri\": \"initiate_login_uri\",\n"
                + "  \"refresh_token\": {\n"
                + "    \"rotation_type\": \"rotating\",\n"
                + "    \"expiration_type\": \"expiring\",\n"
                + "    \"leeway\": 1,\n"
                + "    \"token_lifetime\": 1,\n"
                + "    \"infinite_token_lifetime\": true,\n"
                + "    \"idle_token_lifetime\": 1,\n"
                + "    \"infinite_idle_token_lifetime\": true,\n"
                + "    \"policies\": [\n"
                + "      {\n"
                + "        \"audience\": \"audience\",\n"
                + "        \"scope\": [\n"
                + "          \"scope\"\n"
                + "        ]\n"
                + "      }\n"
                + "    ]\n"
                + "  },\n"
                + "  \"default_organization\": {\n"
                + "    \"organization_id\": \"organization_id\",\n"
                + "    \"flows\": [\n"
                + "      \"client_credentials\"\n"
                + "    ]\n"
                + "  },\n"
                + "  \"organization_usage\": \"deny\",\n"
                + "  \"organization_require_behavior\": \"no_prompt\",\n"
                + "  \"organization_discovery_methods\": [\n"
                + "    \"email\"\n"
                + "  ],\n"
                + "  \"client_authentication_methods\": {\n"
                + "    \"private_key_jwt\": {\n"
                + "      \"credentials\": [\n"
                + "        {\n"
                + "          \"id\": \"id\"\n"
                + "        }\n"
                + "      ]\n"
                + "    },\n"
                + "    \"tls_client_auth\": {\n"
                + "      \"credentials\": [\n"
                + "        {\n"
                + "          \"id\": \"id\"\n"
                + "        }\n"
                + "      ]\n"
                + "    },\n"
                + "    \"self_signed_tls_client_auth\": {\n"
                + "      \"credentials\": [\n"
                + "        {\n"
                + "          \"id\": \"id\"\n"
                + "        }\n"
                + "      ]\n"
                + "    }\n"
                + "  },\n"
                + "  \"require_pushed_authorization_requests\": true,\n"
                + "  \"require_proof_of_possession\": true,\n"
                + "  \"signed_request_object\": {\n"
                + "    \"required\": true,\n"
                + "    \"credentials\": [\n"
                + "      {\n"
                + "        \"id\": \"id\"\n"
                + "      }\n"
                + "    ]\n"
                + "  },\n"
                + "  \"compliance_level\": \"none\",\n"
                + "  \"skip_non_verifiable_callback_uri_confirmation_prompt\": true,\n"
                + "  \"token_exchange\": {\n"
                + "    \"allow_any_profile_of_type\": [\n"
                + "      \"custom_authentication\"\n"
                + "    ]\n"
                + "  },\n"
                + "  \"par_request_expiry\": 1,\n"
                + "  \"token_quota\": {\n"
                + "    \"client_credentials\": {\n"
                + "      \"enforce\": true,\n"
                + "      \"per_day\": 1,\n"
                + "      \"per_hour\": 1\n"
                + "    }\n"
                + "  },\n"
                + "  \"express_configuration\": {\n"
                + "    \"initiate_login_uri_template\": \"initiate_login_uri_template\",\n"
                + "    \"user_attribute_profile_id\": \"user_attribute_profile_id\",\n"
                + "    \"connection_profile_id\": \"connection_profile_id\",\n"
                + "    \"enable_client\": true,\n"
                + "    \"enable_organization\": true,\n"
                + "    \"linked_clients\": [\n"
                + "      {\n"
                + "        \"client_id\": \"client_id\"\n"
                + "      }\n"
                + "    ],\n"
                + "    \"okta_oin_client_id\": \"okta_oin_client_id\",\n"
                + "    \"admin_login_domain\": \"admin_login_domain\",\n"
                + "    \"oin_submission_id\": \"oin_submission_id\"\n"
                + "  },\n"
                + "  \"resource_server_identifier\": \"resource_server_identifier\",\n"
                + "  \"async_approval_notification_channels\": [\n"
                + "    \"guardian-push\"\n"
                + "  ]\n"
                + "}";
        JsonNode actualResponseNode = objectMapper.readTree(actualResponseJson);
        JsonNode expectedResponseNode = objectMapper.readTree(expectedResponseBody);
        Assertions.assertTrue(
                jsonEquals(expectedResponseNode, actualResponseNode),
                "Response body structure does not match expected");
        if (actualResponseNode.has("type") || actualResponseNode.has("_type") || actualResponseNode.has("kind")) {
            String discriminator = null;
            if (actualResponseNode.has("type"))
                discriminator = actualResponseNode.get("type").asText();
            else if (actualResponseNode.has("_type"))
                discriminator = actualResponseNode.get("_type").asText();
            else if (actualResponseNode.has("kind"))
                discriminator = actualResponseNode.get("kind").asText();
            Assertions.assertNotNull(discriminator, "Union type should have a discriminator field");
            Assertions.assertFalse(discriminator.isEmpty(), "Union discriminator should not be empty");
        }

        if (!actualResponseNode.isNull()) {
            Assertions.assertTrue(
                    actualResponseNode.isObject() || actualResponseNode.isArray() || actualResponseNode.isValueNode(),
                    "response should be a valid JSON value");
        }

        if (actualResponseNode.isArray()) {
            Assertions.assertTrue(actualResponseNode.size() >= 0, "Array should have valid size");
        }
        if (actualResponseNode.isObject()) {
            Assertions.assertTrue(actualResponseNode.size() >= 0, "Object should have valid field count");
        }
    }

    @Test
    public void testGet() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"client_id\":\"client_id\",\"tenant\":\"tenant\",\"name\":\"name\",\"description\":\"description\",\"global\":true,\"client_secret\":\"client_secret\",\"app_type\":\"native\",\"logo_uri\":\"logo_uri\",\"is_first_party\":true,\"oidc_conformant\":true,\"callbacks\":[\"callbacks\"],\"allowed_origins\":[\"allowed_origins\"],\"web_origins\":[\"web_origins\"],\"client_aliases\":[\"client_aliases\"],\"allowed_clients\":[\"allowed_clients\"],\"allowed_logout_urls\":[\"allowed_logout_urls\"],\"session_transfer\":{\"can_create_session_transfer_token\":true,\"enforce_cascade_revocation\":true,\"allowed_authentication_methods\":[\"cookie\"],\"enforce_device_binding\":\"ip\",\"allow_refresh_token\":true,\"enforce_online_refresh_tokens\":true},\"oidc_logout\":{\"backchannel_logout_urls\":[\"backchannel_logout_urls\"],\"backchannel_logout_initiators\":{\"mode\":\"custom\",\"selected_initiators\":[\"rp-logout\"]},\"backchannel_logout_session_metadata\":{\"include\":true}},\"grant_types\":[\"grant_types\"],\"jwt_configuration\":{\"lifetime_in_seconds\":1,\"secret_encoded\":true,\"scopes\":{\"key\":\"value\"},\"alg\":\"HS256\"},\"signing_keys\":[{\"pkcs7\":\"pkcs7\",\"cert\":\"cert\",\"subject\":\"subject\"}],\"encryption_key\":{\"pub\":\"pub\",\"cert\":\"cert\",\"subject\":\"subject\"},\"sso\":true,\"sso_disabled\":true,\"cross_origin_authentication\":true,\"cross_origin_loc\":\"cross_origin_loc\",\"custom_login_page_on\":true,\"custom_login_page\":\"custom_login_page\",\"custom_login_page_preview\":\"custom_login_page_preview\",\"form_template\":\"form_template\",\"addons\":{\"aws\":{\"principal\":\"principal\",\"role\":\"role\",\"lifetime_in_seconds\":1},\"azure_blob\":{\"accountName\":\"accountName\",\"storageAccessKey\":\"storageAccessKey\",\"containerName\":\"containerName\",\"blobName\":\"blobName\",\"expiration\":1,\"signedIdentifier\":\"signedIdentifier\",\"blob_read\":true,\"blob_write\":true,\"blob_delete\":true,\"container_read\":true,\"container_write\":true,\"container_delete\":true,\"container_list\":true},\"azure_sb\":{\"namespace\":\"namespace\",\"sasKeyName\":\"sasKeyName\",\"sasKey\":\"sasKey\",\"entityPath\":\"entityPath\",\"expiration\":1},\"rms\":{\"url\":\"url\"},\"mscrm\":{\"url\":\"url\"},\"slack\":{\"team\":\"team\"},\"sentry\":{\"org_slug\":\"org_slug\",\"base_url\":\"base_url\"},\"box\":{\"key\":\"value\"},\"cloudbees\":{\"key\":\"value\"},\"concur\":{\"key\":\"value\"},\"dropbox\":{\"key\":\"value\"},\"echosign\":{\"domain\":\"domain\"},\"egnyte\":{\"domain\":\"domain\"},\"firebase\":{\"secret\":\"secret\",\"private_key_id\":\"private_key_id\",\"private_key\":\"private_key\",\"client_email\":\"client_email\",\"lifetime_in_seconds\":1},\"newrelic\":{\"account\":\"account\"},\"office365\":{\"domain\":\"domain\",\"connection\":\"connection\"},\"salesforce\":{\"entity_id\":\"entity_id\"},\"salesforce_api\":{\"clientid\":\"clientid\",\"principal\":\"principal\",\"communityName\":\"communityName\",\"community_url_section\":\"community_url_section\"},\"salesforce_sandbox_api\":{\"clientid\":\"clientid\",\"principal\":\"principal\",\"communityName\":\"communityName\",\"community_url_section\":\"community_url_section\"},\"samlp\":{\"mappings\":{\"key\":\"value\"},\"audience\":\"audience\",\"recipient\":\"recipient\",\"createUpnClaim\":true,\"mapUnknownClaimsAsIs\":true,\"passthroughClaimsWithNoMapping\":true,\"mapIdentities\":true,\"signatureAlgorithm\":\"signatureAlgorithm\",\"digestAlgorithm\":\"digestAlgorithm\",\"issuer\":\"issuer\",\"destination\":\"destination\",\"lifetimeInSeconds\":1,\"signResponse\":true,\"nameIdentifierFormat\":\"nameIdentifierFormat\",\"nameIdentifierProbes\":[\"nameIdentifierProbes\"],\"authnContextClassRef\":\"authnContextClassRef\"},\"layer\":{\"providerId\":\"providerId\",\"keyId\":\"keyId\",\"privateKey\":\"privateKey\",\"principal\":\"principal\",\"expiration\":1},\"sap_api\":{\"clientid\":\"clientid\",\"usernameAttribute\":\"usernameAttribute\",\"tokenEndpointUrl\":\"tokenEndpointUrl\",\"scope\":\"scope\",\"servicePassword\":\"servicePassword\",\"nameIdentifierFormat\":\"nameIdentifierFormat\"},\"sharepoint\":{\"url\":\"url\",\"external_url\":[\"external_url\"]},\"springcm\":{\"acsurl\":\"acsurl\"},\"wams\":{\"masterkey\":\"masterkey\"},\"wsfed\":{\"key\":\"value\"},\"zendesk\":{\"accountName\":\"accountName\"},\"zoom\":{\"account\":\"account\"},\"sso_integration\":{\"name\":\"name\",\"version\":\"version\"}},\"token_endpoint_auth_method\":\"none\",\"is_token_endpoint_ip_header_trusted\":true,\"client_metadata\":{\"key\":\"value\"},\"mobile\":{\"android\":{\"app_package_name\":\"app_package_name\",\"sha256_cert_fingerprints\":[\"sha256_cert_fingerprints\"]},\"ios\":{\"team_id\":\"team_id\",\"app_bundle_identifier\":\"app_bundle_identifier\"}},\"initiate_login_uri\":\"initiate_login_uri\",\"refresh_token\":{\"rotation_type\":\"rotating\",\"expiration_type\":\"expiring\",\"leeway\":1,\"token_lifetime\":1,\"infinite_token_lifetime\":true,\"idle_token_lifetime\":1,\"infinite_idle_token_lifetime\":true,\"policies\":[{\"audience\":\"audience\",\"scope\":[\"scope\"]}]},\"default_organization\":{\"organization_id\":\"organization_id\",\"flows\":[\"client_credentials\"]},\"organization_usage\":\"deny\",\"organization_require_behavior\":\"no_prompt\",\"organization_discovery_methods\":[\"email\"],\"client_authentication_methods\":{\"private_key_jwt\":{\"credentials\":[{\"id\":\"id\"}]},\"tls_client_auth\":{\"credentials\":[{\"id\":\"id\"}]},\"self_signed_tls_client_auth\":{\"credentials\":[{\"id\":\"id\"}]}},\"require_pushed_authorization_requests\":true,\"require_proof_of_possession\":true,\"signed_request_object\":{\"required\":true,\"credentials\":[{\"id\":\"id\"}]},\"compliance_level\":\"none\",\"skip_non_verifiable_callback_uri_confirmation_prompt\":true,\"token_exchange\":{\"allow_any_profile_of_type\":[\"custom_authentication\"]},\"par_request_expiry\":1,\"token_quota\":{\"client_credentials\":{\"enforce\":true,\"per_day\":1,\"per_hour\":1}},\"express_configuration\":{\"initiate_login_uri_template\":\"initiate_login_uri_template\",\"user_attribute_profile_id\":\"user_attribute_profile_id\",\"connection_profile_id\":\"connection_profile_id\",\"enable_client\":true,\"enable_organization\":true,\"linked_clients\":[{\"client_id\":\"client_id\"}],\"okta_oin_client_id\":\"okta_oin_client_id\",\"admin_login_domain\":\"admin_login_domain\",\"oin_submission_id\":\"oin_submission_id\"},\"resource_server_identifier\":\"resource_server_identifier\",\"async_approval_notification_channels\":[\"guardian-push\"]}"));
        GetClientResponseContent response = client.clients()
                .get(
                        "id",
                        GetClientRequestParameters.builder()
                                .fields(OptionalNullable.of("fields"))
                                .includeFields(OptionalNullable.of(true))
                                .build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("GET", request.getMethod());

        // Validate response body
        Assertions.assertNotNull(response, "Response should not be null");
        String actualResponseJson = objectMapper.writeValueAsString(response);
        String expectedResponseBody = ""
                + "{\n"
                + "  \"client_id\": \"client_id\",\n"
                + "  \"tenant\": \"tenant\",\n"
                + "  \"name\": \"name\",\n"
                + "  \"description\": \"description\",\n"
                + "  \"global\": true,\n"
                + "  \"client_secret\": \"client_secret\",\n"
                + "  \"app_type\": \"native\",\n"
                + "  \"logo_uri\": \"logo_uri\",\n"
                + "  \"is_first_party\": true,\n"
                + "  \"oidc_conformant\": true,\n"
                + "  \"callbacks\": [\n"
                + "    \"callbacks\"\n"
                + "  ],\n"
                + "  \"allowed_origins\": [\n"
                + "    \"allowed_origins\"\n"
                + "  ],\n"
                + "  \"web_origins\": [\n"
                + "    \"web_origins\"\n"
                + "  ],\n"
                + "  \"client_aliases\": [\n"
                + "    \"client_aliases\"\n"
                + "  ],\n"
                + "  \"allowed_clients\": [\n"
                + "    \"allowed_clients\"\n"
                + "  ],\n"
                + "  \"allowed_logout_urls\": [\n"
                + "    \"allowed_logout_urls\"\n"
                + "  ],\n"
                + "  \"session_transfer\": {\n"
                + "    \"can_create_session_transfer_token\": true,\n"
                + "    \"enforce_cascade_revocation\": true,\n"
                + "    \"allowed_authentication_methods\": [\n"
                + "      \"cookie\"\n"
                + "    ],\n"
                + "    \"enforce_device_binding\": \"ip\",\n"
                + "    \"allow_refresh_token\": true,\n"
                + "    \"enforce_online_refresh_tokens\": true\n"
                + "  },\n"
                + "  \"oidc_logout\": {\n"
                + "    \"backchannel_logout_urls\": [\n"
                + "      \"backchannel_logout_urls\"\n"
                + "    ],\n"
                + "    \"backchannel_logout_initiators\": {\n"
                + "      \"mode\": \"custom\",\n"
                + "      \"selected_initiators\": [\n"
                + "        \"rp-logout\"\n"
                + "      ]\n"
                + "    },\n"
                + "    \"backchannel_logout_session_metadata\": {\n"
                + "      \"include\": true\n"
                + "    }\n"
                + "  },\n"
                + "  \"grant_types\": [\n"
                + "    \"grant_types\"\n"
                + "  ],\n"
                + "  \"jwt_configuration\": {\n"
                + "    \"lifetime_in_seconds\": 1,\n"
                + "    \"secret_encoded\": true,\n"
                + "    \"scopes\": {\n"
                + "      \"key\": \"value\"\n"
                + "    },\n"
                + "    \"alg\": \"HS256\"\n"
                + "  },\n"
                + "  \"signing_keys\": [\n"
                + "    {\n"
                + "      \"pkcs7\": \"pkcs7\",\n"
                + "      \"cert\": \"cert\",\n"
                + "      \"subject\": \"subject\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"encryption_key\": {\n"
                + "    \"pub\": \"pub\",\n"
                + "    \"cert\": \"cert\",\n"
                + "    \"subject\": \"subject\"\n"
                + "  },\n"
                + "  \"sso\": true,\n"
                + "  \"sso_disabled\": true,\n"
                + "  \"cross_origin_authentication\": true,\n"
                + "  \"cross_origin_loc\": \"cross_origin_loc\",\n"
                + "  \"custom_login_page_on\": true,\n"
                + "  \"custom_login_page\": \"custom_login_page\",\n"
                + "  \"custom_login_page_preview\": \"custom_login_page_preview\",\n"
                + "  \"form_template\": \"form_template\",\n"
                + "  \"addons\": {\n"
                + "    \"aws\": {\n"
                + "      \"principal\": \"principal\",\n"
                + "      \"role\": \"role\",\n"
                + "      \"lifetime_in_seconds\": 1\n"
                + "    },\n"
                + "    \"azure_blob\": {\n"
                + "      \"accountName\": \"accountName\",\n"
                + "      \"storageAccessKey\": \"storageAccessKey\",\n"
                + "      \"containerName\": \"containerName\",\n"
                + "      \"blobName\": \"blobName\",\n"
                + "      \"expiration\": 1,\n"
                + "      \"signedIdentifier\": \"signedIdentifier\",\n"
                + "      \"blob_read\": true,\n"
                + "      \"blob_write\": true,\n"
                + "      \"blob_delete\": true,\n"
                + "      \"container_read\": true,\n"
                + "      \"container_write\": true,\n"
                + "      \"container_delete\": true,\n"
                + "      \"container_list\": true\n"
                + "    },\n"
                + "    \"azure_sb\": {\n"
                + "      \"namespace\": \"namespace\",\n"
                + "      \"sasKeyName\": \"sasKeyName\",\n"
                + "      \"sasKey\": \"sasKey\",\n"
                + "      \"entityPath\": \"entityPath\",\n"
                + "      \"expiration\": 1\n"
                + "    },\n"
                + "    \"rms\": {\n"
                + "      \"url\": \"url\"\n"
                + "    },\n"
                + "    \"mscrm\": {\n"
                + "      \"url\": \"url\"\n"
                + "    },\n"
                + "    \"slack\": {\n"
                + "      \"team\": \"team\"\n"
                + "    },\n"
                + "    \"sentry\": {\n"
                + "      \"org_slug\": \"org_slug\",\n"
                + "      \"base_url\": \"base_url\"\n"
                + "    },\n"
                + "    \"box\": {\n"
                + "      \"key\": \"value\"\n"
                + "    },\n"
                + "    \"cloudbees\": {\n"
                + "      \"key\": \"value\"\n"
                + "    },\n"
                + "    \"concur\": {\n"
                + "      \"key\": \"value\"\n"
                + "    },\n"
                + "    \"dropbox\": {\n"
                + "      \"key\": \"value\"\n"
                + "    },\n"
                + "    \"echosign\": {\n"
                + "      \"domain\": \"domain\"\n"
                + "    },\n"
                + "    \"egnyte\": {\n"
                + "      \"domain\": \"domain\"\n"
                + "    },\n"
                + "    \"firebase\": {\n"
                + "      \"secret\": \"secret\",\n"
                + "      \"private_key_id\": \"private_key_id\",\n"
                + "      \"private_key\": \"private_key\",\n"
                + "      \"client_email\": \"client_email\",\n"
                + "      \"lifetime_in_seconds\": 1\n"
                + "    },\n"
                + "    \"newrelic\": {\n"
                + "      \"account\": \"account\"\n"
                + "    },\n"
                + "    \"office365\": {\n"
                + "      \"domain\": \"domain\",\n"
                + "      \"connection\": \"connection\"\n"
                + "    },\n"
                + "    \"salesforce\": {\n"
                + "      \"entity_id\": \"entity_id\"\n"
                + "    },\n"
                + "    \"salesforce_api\": {\n"
                + "      \"clientid\": \"clientid\",\n"
                + "      \"principal\": \"principal\",\n"
                + "      \"communityName\": \"communityName\",\n"
                + "      \"community_url_section\": \"community_url_section\"\n"
                + "    },\n"
                + "    \"salesforce_sandbox_api\": {\n"
                + "      \"clientid\": \"clientid\",\n"
                + "      \"principal\": \"principal\",\n"
                + "      \"communityName\": \"communityName\",\n"
                + "      \"community_url_section\": \"community_url_section\"\n"
                + "    },\n"
                + "    \"samlp\": {\n"
                + "      \"mappings\": {\n"
                + "        \"key\": \"value\"\n"
                + "      },\n"
                + "      \"audience\": \"audience\",\n"
                + "      \"recipient\": \"recipient\",\n"
                + "      \"createUpnClaim\": true,\n"
                + "      \"mapUnknownClaimsAsIs\": true,\n"
                + "      \"passthroughClaimsWithNoMapping\": true,\n"
                + "      \"mapIdentities\": true,\n"
                + "      \"signatureAlgorithm\": \"signatureAlgorithm\",\n"
                + "      \"digestAlgorithm\": \"digestAlgorithm\",\n"
                + "      \"issuer\": \"issuer\",\n"
                + "      \"destination\": \"destination\",\n"
                + "      \"lifetimeInSeconds\": 1,\n"
                + "      \"signResponse\": true,\n"
                + "      \"nameIdentifierFormat\": \"nameIdentifierFormat\",\n"
                + "      \"nameIdentifierProbes\": [\n"
                + "        \"nameIdentifierProbes\"\n"
                + "      ],\n"
                + "      \"authnContextClassRef\": \"authnContextClassRef\"\n"
                + "    },\n"
                + "    \"layer\": {\n"
                + "      \"providerId\": \"providerId\",\n"
                + "      \"keyId\": \"keyId\",\n"
                + "      \"privateKey\": \"privateKey\",\n"
                + "      \"principal\": \"principal\",\n"
                + "      \"expiration\": 1\n"
                + "    },\n"
                + "    \"sap_api\": {\n"
                + "      \"clientid\": \"clientid\",\n"
                + "      \"usernameAttribute\": \"usernameAttribute\",\n"
                + "      \"tokenEndpointUrl\": \"tokenEndpointUrl\",\n"
                + "      \"scope\": \"scope\",\n"
                + "      \"servicePassword\": \"servicePassword\",\n"
                + "      \"nameIdentifierFormat\": \"nameIdentifierFormat\"\n"
                + "    },\n"
                + "    \"sharepoint\": {\n"
                + "      \"url\": \"url\",\n"
                + "      \"external_url\": [\n"
                + "        \"external_url\"\n"
                + "      ]\n"
                + "    },\n"
                + "    \"springcm\": {\n"
                + "      \"acsurl\": \"acsurl\"\n"
                + "    },\n"
                + "    \"wams\": {\n"
                + "      \"masterkey\": \"masterkey\"\n"
                + "    },\n"
                + "    \"wsfed\": {\n"
                + "      \"key\": \"value\"\n"
                + "    },\n"
                + "    \"zendesk\": {\n"
                + "      \"accountName\": \"accountName\"\n"
                + "    },\n"
                + "    \"zoom\": {\n"
                + "      \"account\": \"account\"\n"
                + "    },\n"
                + "    \"sso_integration\": {\n"
                + "      \"name\": \"name\",\n"
                + "      \"version\": \"version\"\n"
                + "    }\n"
                + "  },\n"
                + "  \"token_endpoint_auth_method\": \"none\",\n"
                + "  \"is_token_endpoint_ip_header_trusted\": true,\n"
                + "  \"client_metadata\": {\n"
                + "    \"key\": \"value\"\n"
                + "  },\n"
                + "  \"mobile\": {\n"
                + "    \"android\": {\n"
                + "      \"app_package_name\": \"app_package_name\",\n"
                + "      \"sha256_cert_fingerprints\": [\n"
                + "        \"sha256_cert_fingerprints\"\n"
                + "      ]\n"
                + "    },\n"
                + "    \"ios\": {\n"
                + "      \"team_id\": \"team_id\",\n"
                + "      \"app_bundle_identifier\": \"app_bundle_identifier\"\n"
                + "    }\n"
                + "  },\n"
                + "  \"initiate_login_uri\": \"initiate_login_uri\",\n"
                + "  \"refresh_token\": {\n"
                + "    \"rotation_type\": \"rotating\",\n"
                + "    \"expiration_type\": \"expiring\",\n"
                + "    \"leeway\": 1,\n"
                + "    \"token_lifetime\": 1,\n"
                + "    \"infinite_token_lifetime\": true,\n"
                + "    \"idle_token_lifetime\": 1,\n"
                + "    \"infinite_idle_token_lifetime\": true,\n"
                + "    \"policies\": [\n"
                + "      {\n"
                + "        \"audience\": \"audience\",\n"
                + "        \"scope\": [\n"
                + "          \"scope\"\n"
                + "        ]\n"
                + "      }\n"
                + "    ]\n"
                + "  },\n"
                + "  \"default_organization\": {\n"
                + "    \"organization_id\": \"organization_id\",\n"
                + "    \"flows\": [\n"
                + "      \"client_credentials\"\n"
                + "    ]\n"
                + "  },\n"
                + "  \"organization_usage\": \"deny\",\n"
                + "  \"organization_require_behavior\": \"no_prompt\",\n"
                + "  \"organization_discovery_methods\": [\n"
                + "    \"email\"\n"
                + "  ],\n"
                + "  \"client_authentication_methods\": {\n"
                + "    \"private_key_jwt\": {\n"
                + "      \"credentials\": [\n"
                + "        {\n"
                + "          \"id\": \"id\"\n"
                + "        }\n"
                + "      ]\n"
                + "    },\n"
                + "    \"tls_client_auth\": {\n"
                + "      \"credentials\": [\n"
                + "        {\n"
                + "          \"id\": \"id\"\n"
                + "        }\n"
                + "      ]\n"
                + "    },\n"
                + "    \"self_signed_tls_client_auth\": {\n"
                + "      \"credentials\": [\n"
                + "        {\n"
                + "          \"id\": \"id\"\n"
                + "        }\n"
                + "      ]\n"
                + "    }\n"
                + "  },\n"
                + "  \"require_pushed_authorization_requests\": true,\n"
                + "  \"require_proof_of_possession\": true,\n"
                + "  \"signed_request_object\": {\n"
                + "    \"required\": true,\n"
                + "    \"credentials\": [\n"
                + "      {\n"
                + "        \"id\": \"id\"\n"
                + "      }\n"
                + "    ]\n"
                + "  },\n"
                + "  \"compliance_level\": \"none\",\n"
                + "  \"skip_non_verifiable_callback_uri_confirmation_prompt\": true,\n"
                + "  \"token_exchange\": {\n"
                + "    \"allow_any_profile_of_type\": [\n"
                + "      \"custom_authentication\"\n"
                + "    ]\n"
                + "  },\n"
                + "  \"par_request_expiry\": 1,\n"
                + "  \"token_quota\": {\n"
                + "    \"client_credentials\": {\n"
                + "      \"enforce\": true,\n"
                + "      \"per_day\": 1,\n"
                + "      \"per_hour\": 1\n"
                + "    }\n"
                + "  },\n"
                + "  \"express_configuration\": {\n"
                + "    \"initiate_login_uri_template\": \"initiate_login_uri_template\",\n"
                + "    \"user_attribute_profile_id\": \"user_attribute_profile_id\",\n"
                + "    \"connection_profile_id\": \"connection_profile_id\",\n"
                + "    \"enable_client\": true,\n"
                + "    \"enable_organization\": true,\n"
                + "    \"linked_clients\": [\n"
                + "      {\n"
                + "        \"client_id\": \"client_id\"\n"
                + "      }\n"
                + "    ],\n"
                + "    \"okta_oin_client_id\": \"okta_oin_client_id\",\n"
                + "    \"admin_login_domain\": \"admin_login_domain\",\n"
                + "    \"oin_submission_id\": \"oin_submission_id\"\n"
                + "  },\n"
                + "  \"resource_server_identifier\": \"resource_server_identifier\",\n"
                + "  \"async_approval_notification_channels\": [\n"
                + "    \"guardian-push\"\n"
                + "  ]\n"
                + "}";
        JsonNode actualResponseNode = objectMapper.readTree(actualResponseJson);
        JsonNode expectedResponseNode = objectMapper.readTree(expectedResponseBody);
        Assertions.assertTrue(
                jsonEquals(expectedResponseNode, actualResponseNode),
                "Response body structure does not match expected");
        if (actualResponseNode.has("type") || actualResponseNode.has("_type") || actualResponseNode.has("kind")) {
            String discriminator = null;
            if (actualResponseNode.has("type"))
                discriminator = actualResponseNode.get("type").asText();
            else if (actualResponseNode.has("_type"))
                discriminator = actualResponseNode.get("_type").asText();
            else if (actualResponseNode.has("kind"))
                discriminator = actualResponseNode.get("kind").asText();
            Assertions.assertNotNull(discriminator, "Union type should have a discriminator field");
            Assertions.assertFalse(discriminator.isEmpty(), "Union discriminator should not be empty");
        }

        if (!actualResponseNode.isNull()) {
            Assertions.assertTrue(
                    actualResponseNode.isObject() || actualResponseNode.isArray() || actualResponseNode.isValueNode(),
                    "response should be a valid JSON value");
        }

        if (actualResponseNode.isArray()) {
            Assertions.assertTrue(actualResponseNode.size() >= 0, "Array should have valid size");
        }
        if (actualResponseNode.isObject()) {
            Assertions.assertTrue(actualResponseNode.size() >= 0, "Object should have valid field count");
        }
    }

    @Test
    public void testDelete() throws Exception {
        server.enqueue(new MockResponse().setResponseCode(200).setBody("{}"));
        client.clients().delete("id");
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("DELETE", request.getMethod());
    }

    @Test
    public void testUpdate() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"client_id\":\"client_id\",\"tenant\":\"tenant\",\"name\":\"name\",\"description\":\"description\",\"global\":true,\"client_secret\":\"client_secret\",\"app_type\":\"native\",\"logo_uri\":\"logo_uri\",\"is_first_party\":true,\"oidc_conformant\":true,\"callbacks\":[\"callbacks\"],\"allowed_origins\":[\"allowed_origins\"],\"web_origins\":[\"web_origins\"],\"client_aliases\":[\"client_aliases\"],\"allowed_clients\":[\"allowed_clients\"],\"allowed_logout_urls\":[\"allowed_logout_urls\"],\"session_transfer\":{\"can_create_session_transfer_token\":true,\"enforce_cascade_revocation\":true,\"allowed_authentication_methods\":[\"cookie\"],\"enforce_device_binding\":\"ip\",\"allow_refresh_token\":true,\"enforce_online_refresh_tokens\":true},\"oidc_logout\":{\"backchannel_logout_urls\":[\"backchannel_logout_urls\"],\"backchannel_logout_initiators\":{\"mode\":\"custom\",\"selected_initiators\":[\"rp-logout\"]},\"backchannel_logout_session_metadata\":{\"include\":true}},\"grant_types\":[\"grant_types\"],\"jwt_configuration\":{\"lifetime_in_seconds\":1,\"secret_encoded\":true,\"scopes\":{\"key\":\"value\"},\"alg\":\"HS256\"},\"signing_keys\":[{\"pkcs7\":\"pkcs7\",\"cert\":\"cert\",\"subject\":\"subject\"}],\"encryption_key\":{\"pub\":\"pub\",\"cert\":\"cert\",\"subject\":\"subject\"},\"sso\":true,\"sso_disabled\":true,\"cross_origin_authentication\":true,\"cross_origin_loc\":\"cross_origin_loc\",\"custom_login_page_on\":true,\"custom_login_page\":\"custom_login_page\",\"custom_login_page_preview\":\"custom_login_page_preview\",\"form_template\":\"form_template\",\"addons\":{\"aws\":{\"principal\":\"principal\",\"role\":\"role\",\"lifetime_in_seconds\":1},\"azure_blob\":{\"accountName\":\"accountName\",\"storageAccessKey\":\"storageAccessKey\",\"containerName\":\"containerName\",\"blobName\":\"blobName\",\"expiration\":1,\"signedIdentifier\":\"signedIdentifier\",\"blob_read\":true,\"blob_write\":true,\"blob_delete\":true,\"container_read\":true,\"container_write\":true,\"container_delete\":true,\"container_list\":true},\"azure_sb\":{\"namespace\":\"namespace\",\"sasKeyName\":\"sasKeyName\",\"sasKey\":\"sasKey\",\"entityPath\":\"entityPath\",\"expiration\":1},\"rms\":{\"url\":\"url\"},\"mscrm\":{\"url\":\"url\"},\"slack\":{\"team\":\"team\"},\"sentry\":{\"org_slug\":\"org_slug\",\"base_url\":\"base_url\"},\"box\":{\"key\":\"value\"},\"cloudbees\":{\"key\":\"value\"},\"concur\":{\"key\":\"value\"},\"dropbox\":{\"key\":\"value\"},\"echosign\":{\"domain\":\"domain\"},\"egnyte\":{\"domain\":\"domain\"},\"firebase\":{\"secret\":\"secret\",\"private_key_id\":\"private_key_id\",\"private_key\":\"private_key\",\"client_email\":\"client_email\",\"lifetime_in_seconds\":1},\"newrelic\":{\"account\":\"account\"},\"office365\":{\"domain\":\"domain\",\"connection\":\"connection\"},\"salesforce\":{\"entity_id\":\"entity_id\"},\"salesforce_api\":{\"clientid\":\"clientid\",\"principal\":\"principal\",\"communityName\":\"communityName\",\"community_url_section\":\"community_url_section\"},\"salesforce_sandbox_api\":{\"clientid\":\"clientid\",\"principal\":\"principal\",\"communityName\":\"communityName\",\"community_url_section\":\"community_url_section\"},\"samlp\":{\"mappings\":{\"key\":\"value\"},\"audience\":\"audience\",\"recipient\":\"recipient\",\"createUpnClaim\":true,\"mapUnknownClaimsAsIs\":true,\"passthroughClaimsWithNoMapping\":true,\"mapIdentities\":true,\"signatureAlgorithm\":\"signatureAlgorithm\",\"digestAlgorithm\":\"digestAlgorithm\",\"issuer\":\"issuer\",\"destination\":\"destination\",\"lifetimeInSeconds\":1,\"signResponse\":true,\"nameIdentifierFormat\":\"nameIdentifierFormat\",\"nameIdentifierProbes\":[\"nameIdentifierProbes\"],\"authnContextClassRef\":\"authnContextClassRef\"},\"layer\":{\"providerId\":\"providerId\",\"keyId\":\"keyId\",\"privateKey\":\"privateKey\",\"principal\":\"principal\",\"expiration\":1},\"sap_api\":{\"clientid\":\"clientid\",\"usernameAttribute\":\"usernameAttribute\",\"tokenEndpointUrl\":\"tokenEndpointUrl\",\"scope\":\"scope\",\"servicePassword\":\"servicePassword\",\"nameIdentifierFormat\":\"nameIdentifierFormat\"},\"sharepoint\":{\"url\":\"url\",\"external_url\":[\"external_url\"]},\"springcm\":{\"acsurl\":\"acsurl\"},\"wams\":{\"masterkey\":\"masterkey\"},\"wsfed\":{\"key\":\"value\"},\"zendesk\":{\"accountName\":\"accountName\"},\"zoom\":{\"account\":\"account\"},\"sso_integration\":{\"name\":\"name\",\"version\":\"version\"}},\"token_endpoint_auth_method\":\"none\",\"is_token_endpoint_ip_header_trusted\":true,\"client_metadata\":{\"key\":\"value\"},\"mobile\":{\"android\":{\"app_package_name\":\"app_package_name\",\"sha256_cert_fingerprints\":[\"sha256_cert_fingerprints\"]},\"ios\":{\"team_id\":\"team_id\",\"app_bundle_identifier\":\"app_bundle_identifier\"}},\"initiate_login_uri\":\"initiate_login_uri\",\"refresh_token\":{\"rotation_type\":\"rotating\",\"expiration_type\":\"expiring\",\"leeway\":1,\"token_lifetime\":1,\"infinite_token_lifetime\":true,\"idle_token_lifetime\":1,\"infinite_idle_token_lifetime\":true,\"policies\":[{\"audience\":\"audience\",\"scope\":[\"scope\"]}]},\"default_organization\":{\"organization_id\":\"organization_id\",\"flows\":[\"client_credentials\"]},\"organization_usage\":\"deny\",\"organization_require_behavior\":\"no_prompt\",\"organization_discovery_methods\":[\"email\"],\"client_authentication_methods\":{\"private_key_jwt\":{\"credentials\":[{\"id\":\"id\"}]},\"tls_client_auth\":{\"credentials\":[{\"id\":\"id\"}]},\"self_signed_tls_client_auth\":{\"credentials\":[{\"id\":\"id\"}]}},\"require_pushed_authorization_requests\":true,\"require_proof_of_possession\":true,\"signed_request_object\":{\"required\":true,\"credentials\":[{\"id\":\"id\"}]},\"compliance_level\":\"none\",\"skip_non_verifiable_callback_uri_confirmation_prompt\":true,\"token_exchange\":{\"allow_any_profile_of_type\":[\"custom_authentication\"]},\"par_request_expiry\":1,\"token_quota\":{\"client_credentials\":{\"enforce\":true,\"per_day\":1,\"per_hour\":1}},\"express_configuration\":{\"initiate_login_uri_template\":\"initiate_login_uri_template\",\"user_attribute_profile_id\":\"user_attribute_profile_id\",\"connection_profile_id\":\"connection_profile_id\",\"enable_client\":true,\"enable_organization\":true,\"linked_clients\":[{\"client_id\":\"client_id\"}],\"okta_oin_client_id\":\"okta_oin_client_id\",\"admin_login_domain\":\"admin_login_domain\",\"oin_submission_id\":\"oin_submission_id\"},\"resource_server_identifier\":\"resource_server_identifier\",\"async_approval_notification_channels\":[\"guardian-push\"]}"));
        UpdateClientResponseContent response = client.clients()
                .update("id", UpdateClientRequestContent.builder().build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("PATCH", request.getMethod());
        // Validate request body
        String actualRequestBody = request.getBody().readUtf8();
        String expectedRequestBody = "" + "{}";
        JsonNode actualJson = objectMapper.readTree(actualRequestBody);
        JsonNode expectedJson = objectMapper.readTree(expectedRequestBody);
        Assertions.assertTrue(jsonEquals(expectedJson, actualJson), "Request body structure does not match expected");
        if (actualJson.has("type") || actualJson.has("_type") || actualJson.has("kind")) {
            String discriminator = null;
            if (actualJson.has("type")) discriminator = actualJson.get("type").asText();
            else if (actualJson.has("_type"))
                discriminator = actualJson.get("_type").asText();
            else if (actualJson.has("kind"))
                discriminator = actualJson.get("kind").asText();
            Assertions.assertNotNull(discriminator, "Union type should have a discriminator field");
            Assertions.assertFalse(discriminator.isEmpty(), "Union discriminator should not be empty");
        }

        if (!actualJson.isNull()) {
            Assertions.assertTrue(
                    actualJson.isObject() || actualJson.isArray() || actualJson.isValueNode(),
                    "request should be a valid JSON value");
        }

        if (actualJson.isArray()) {
            Assertions.assertTrue(actualJson.size() >= 0, "Array should have valid size");
        }
        if (actualJson.isObject()) {
            Assertions.assertTrue(actualJson.size() >= 0, "Object should have valid field count");
        }

        // Validate response body
        Assertions.assertNotNull(response, "Response should not be null");
        String actualResponseJson = objectMapper.writeValueAsString(response);
        String expectedResponseBody = ""
                + "{\n"
                + "  \"client_id\": \"client_id\",\n"
                + "  \"tenant\": \"tenant\",\n"
                + "  \"name\": \"name\",\n"
                + "  \"description\": \"description\",\n"
                + "  \"global\": true,\n"
                + "  \"client_secret\": \"client_secret\",\n"
                + "  \"app_type\": \"native\",\n"
                + "  \"logo_uri\": \"logo_uri\",\n"
                + "  \"is_first_party\": true,\n"
                + "  \"oidc_conformant\": true,\n"
                + "  \"callbacks\": [\n"
                + "    \"callbacks\"\n"
                + "  ],\n"
                + "  \"allowed_origins\": [\n"
                + "    \"allowed_origins\"\n"
                + "  ],\n"
                + "  \"web_origins\": [\n"
                + "    \"web_origins\"\n"
                + "  ],\n"
                + "  \"client_aliases\": [\n"
                + "    \"client_aliases\"\n"
                + "  ],\n"
                + "  \"allowed_clients\": [\n"
                + "    \"allowed_clients\"\n"
                + "  ],\n"
                + "  \"allowed_logout_urls\": [\n"
                + "    \"allowed_logout_urls\"\n"
                + "  ],\n"
                + "  \"session_transfer\": {\n"
                + "    \"can_create_session_transfer_token\": true,\n"
                + "    \"enforce_cascade_revocation\": true,\n"
                + "    \"allowed_authentication_methods\": [\n"
                + "      \"cookie\"\n"
                + "    ],\n"
                + "    \"enforce_device_binding\": \"ip\",\n"
                + "    \"allow_refresh_token\": true,\n"
                + "    \"enforce_online_refresh_tokens\": true\n"
                + "  },\n"
                + "  \"oidc_logout\": {\n"
                + "    \"backchannel_logout_urls\": [\n"
                + "      \"backchannel_logout_urls\"\n"
                + "    ],\n"
                + "    \"backchannel_logout_initiators\": {\n"
                + "      \"mode\": \"custom\",\n"
                + "      \"selected_initiators\": [\n"
                + "        \"rp-logout\"\n"
                + "      ]\n"
                + "    },\n"
                + "    \"backchannel_logout_session_metadata\": {\n"
                + "      \"include\": true\n"
                + "    }\n"
                + "  },\n"
                + "  \"grant_types\": [\n"
                + "    \"grant_types\"\n"
                + "  ],\n"
                + "  \"jwt_configuration\": {\n"
                + "    \"lifetime_in_seconds\": 1,\n"
                + "    \"secret_encoded\": true,\n"
                + "    \"scopes\": {\n"
                + "      \"key\": \"value\"\n"
                + "    },\n"
                + "    \"alg\": \"HS256\"\n"
                + "  },\n"
                + "  \"signing_keys\": [\n"
                + "    {\n"
                + "      \"pkcs7\": \"pkcs7\",\n"
                + "      \"cert\": \"cert\",\n"
                + "      \"subject\": \"subject\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"encryption_key\": {\n"
                + "    \"pub\": \"pub\",\n"
                + "    \"cert\": \"cert\",\n"
                + "    \"subject\": \"subject\"\n"
                + "  },\n"
                + "  \"sso\": true,\n"
                + "  \"sso_disabled\": true,\n"
                + "  \"cross_origin_authentication\": true,\n"
                + "  \"cross_origin_loc\": \"cross_origin_loc\",\n"
                + "  \"custom_login_page_on\": true,\n"
                + "  \"custom_login_page\": \"custom_login_page\",\n"
                + "  \"custom_login_page_preview\": \"custom_login_page_preview\",\n"
                + "  \"form_template\": \"form_template\",\n"
                + "  \"addons\": {\n"
                + "    \"aws\": {\n"
                + "      \"principal\": \"principal\",\n"
                + "      \"role\": \"role\",\n"
                + "      \"lifetime_in_seconds\": 1\n"
                + "    },\n"
                + "    \"azure_blob\": {\n"
                + "      \"accountName\": \"accountName\",\n"
                + "      \"storageAccessKey\": \"storageAccessKey\",\n"
                + "      \"containerName\": \"containerName\",\n"
                + "      \"blobName\": \"blobName\",\n"
                + "      \"expiration\": 1,\n"
                + "      \"signedIdentifier\": \"signedIdentifier\",\n"
                + "      \"blob_read\": true,\n"
                + "      \"blob_write\": true,\n"
                + "      \"blob_delete\": true,\n"
                + "      \"container_read\": true,\n"
                + "      \"container_write\": true,\n"
                + "      \"container_delete\": true,\n"
                + "      \"container_list\": true\n"
                + "    },\n"
                + "    \"azure_sb\": {\n"
                + "      \"namespace\": \"namespace\",\n"
                + "      \"sasKeyName\": \"sasKeyName\",\n"
                + "      \"sasKey\": \"sasKey\",\n"
                + "      \"entityPath\": \"entityPath\",\n"
                + "      \"expiration\": 1\n"
                + "    },\n"
                + "    \"rms\": {\n"
                + "      \"url\": \"url\"\n"
                + "    },\n"
                + "    \"mscrm\": {\n"
                + "      \"url\": \"url\"\n"
                + "    },\n"
                + "    \"slack\": {\n"
                + "      \"team\": \"team\"\n"
                + "    },\n"
                + "    \"sentry\": {\n"
                + "      \"org_slug\": \"org_slug\",\n"
                + "      \"base_url\": \"base_url\"\n"
                + "    },\n"
                + "    \"box\": {\n"
                + "      \"key\": \"value\"\n"
                + "    },\n"
                + "    \"cloudbees\": {\n"
                + "      \"key\": \"value\"\n"
                + "    },\n"
                + "    \"concur\": {\n"
                + "      \"key\": \"value\"\n"
                + "    },\n"
                + "    \"dropbox\": {\n"
                + "      \"key\": \"value\"\n"
                + "    },\n"
                + "    \"echosign\": {\n"
                + "      \"domain\": \"domain\"\n"
                + "    },\n"
                + "    \"egnyte\": {\n"
                + "      \"domain\": \"domain\"\n"
                + "    },\n"
                + "    \"firebase\": {\n"
                + "      \"secret\": \"secret\",\n"
                + "      \"private_key_id\": \"private_key_id\",\n"
                + "      \"private_key\": \"private_key\",\n"
                + "      \"client_email\": \"client_email\",\n"
                + "      \"lifetime_in_seconds\": 1\n"
                + "    },\n"
                + "    \"newrelic\": {\n"
                + "      \"account\": \"account\"\n"
                + "    },\n"
                + "    \"office365\": {\n"
                + "      \"domain\": \"domain\",\n"
                + "      \"connection\": \"connection\"\n"
                + "    },\n"
                + "    \"salesforce\": {\n"
                + "      \"entity_id\": \"entity_id\"\n"
                + "    },\n"
                + "    \"salesforce_api\": {\n"
                + "      \"clientid\": \"clientid\",\n"
                + "      \"principal\": \"principal\",\n"
                + "      \"communityName\": \"communityName\",\n"
                + "      \"community_url_section\": \"community_url_section\"\n"
                + "    },\n"
                + "    \"salesforce_sandbox_api\": {\n"
                + "      \"clientid\": \"clientid\",\n"
                + "      \"principal\": \"principal\",\n"
                + "      \"communityName\": \"communityName\",\n"
                + "      \"community_url_section\": \"community_url_section\"\n"
                + "    },\n"
                + "    \"samlp\": {\n"
                + "      \"mappings\": {\n"
                + "        \"key\": \"value\"\n"
                + "      },\n"
                + "      \"audience\": \"audience\",\n"
                + "      \"recipient\": \"recipient\",\n"
                + "      \"createUpnClaim\": true,\n"
                + "      \"mapUnknownClaimsAsIs\": true,\n"
                + "      \"passthroughClaimsWithNoMapping\": true,\n"
                + "      \"mapIdentities\": true,\n"
                + "      \"signatureAlgorithm\": \"signatureAlgorithm\",\n"
                + "      \"digestAlgorithm\": \"digestAlgorithm\",\n"
                + "      \"issuer\": \"issuer\",\n"
                + "      \"destination\": \"destination\",\n"
                + "      \"lifetimeInSeconds\": 1,\n"
                + "      \"signResponse\": true,\n"
                + "      \"nameIdentifierFormat\": \"nameIdentifierFormat\",\n"
                + "      \"nameIdentifierProbes\": [\n"
                + "        \"nameIdentifierProbes\"\n"
                + "      ],\n"
                + "      \"authnContextClassRef\": \"authnContextClassRef\"\n"
                + "    },\n"
                + "    \"layer\": {\n"
                + "      \"providerId\": \"providerId\",\n"
                + "      \"keyId\": \"keyId\",\n"
                + "      \"privateKey\": \"privateKey\",\n"
                + "      \"principal\": \"principal\",\n"
                + "      \"expiration\": 1\n"
                + "    },\n"
                + "    \"sap_api\": {\n"
                + "      \"clientid\": \"clientid\",\n"
                + "      \"usernameAttribute\": \"usernameAttribute\",\n"
                + "      \"tokenEndpointUrl\": \"tokenEndpointUrl\",\n"
                + "      \"scope\": \"scope\",\n"
                + "      \"servicePassword\": \"servicePassword\",\n"
                + "      \"nameIdentifierFormat\": \"nameIdentifierFormat\"\n"
                + "    },\n"
                + "    \"sharepoint\": {\n"
                + "      \"url\": \"url\",\n"
                + "      \"external_url\": [\n"
                + "        \"external_url\"\n"
                + "      ]\n"
                + "    },\n"
                + "    \"springcm\": {\n"
                + "      \"acsurl\": \"acsurl\"\n"
                + "    },\n"
                + "    \"wams\": {\n"
                + "      \"masterkey\": \"masterkey\"\n"
                + "    },\n"
                + "    \"wsfed\": {\n"
                + "      \"key\": \"value\"\n"
                + "    },\n"
                + "    \"zendesk\": {\n"
                + "      \"accountName\": \"accountName\"\n"
                + "    },\n"
                + "    \"zoom\": {\n"
                + "      \"account\": \"account\"\n"
                + "    },\n"
                + "    \"sso_integration\": {\n"
                + "      \"name\": \"name\",\n"
                + "      \"version\": \"version\"\n"
                + "    }\n"
                + "  },\n"
                + "  \"token_endpoint_auth_method\": \"none\",\n"
                + "  \"is_token_endpoint_ip_header_trusted\": true,\n"
                + "  \"client_metadata\": {\n"
                + "    \"key\": \"value\"\n"
                + "  },\n"
                + "  \"mobile\": {\n"
                + "    \"android\": {\n"
                + "      \"app_package_name\": \"app_package_name\",\n"
                + "      \"sha256_cert_fingerprints\": [\n"
                + "        \"sha256_cert_fingerprints\"\n"
                + "      ]\n"
                + "    },\n"
                + "    \"ios\": {\n"
                + "      \"team_id\": \"team_id\",\n"
                + "      \"app_bundle_identifier\": \"app_bundle_identifier\"\n"
                + "    }\n"
                + "  },\n"
                + "  \"initiate_login_uri\": \"initiate_login_uri\",\n"
                + "  \"refresh_token\": {\n"
                + "    \"rotation_type\": \"rotating\",\n"
                + "    \"expiration_type\": \"expiring\",\n"
                + "    \"leeway\": 1,\n"
                + "    \"token_lifetime\": 1,\n"
                + "    \"infinite_token_lifetime\": true,\n"
                + "    \"idle_token_lifetime\": 1,\n"
                + "    \"infinite_idle_token_lifetime\": true,\n"
                + "    \"policies\": [\n"
                + "      {\n"
                + "        \"audience\": \"audience\",\n"
                + "        \"scope\": [\n"
                + "          \"scope\"\n"
                + "        ]\n"
                + "      }\n"
                + "    ]\n"
                + "  },\n"
                + "  \"default_organization\": {\n"
                + "    \"organization_id\": \"organization_id\",\n"
                + "    \"flows\": [\n"
                + "      \"client_credentials\"\n"
                + "    ]\n"
                + "  },\n"
                + "  \"organization_usage\": \"deny\",\n"
                + "  \"organization_require_behavior\": \"no_prompt\",\n"
                + "  \"organization_discovery_methods\": [\n"
                + "    \"email\"\n"
                + "  ],\n"
                + "  \"client_authentication_methods\": {\n"
                + "    \"private_key_jwt\": {\n"
                + "      \"credentials\": [\n"
                + "        {\n"
                + "          \"id\": \"id\"\n"
                + "        }\n"
                + "      ]\n"
                + "    },\n"
                + "    \"tls_client_auth\": {\n"
                + "      \"credentials\": [\n"
                + "        {\n"
                + "          \"id\": \"id\"\n"
                + "        }\n"
                + "      ]\n"
                + "    },\n"
                + "    \"self_signed_tls_client_auth\": {\n"
                + "      \"credentials\": [\n"
                + "        {\n"
                + "          \"id\": \"id\"\n"
                + "        }\n"
                + "      ]\n"
                + "    }\n"
                + "  },\n"
                + "  \"require_pushed_authorization_requests\": true,\n"
                + "  \"require_proof_of_possession\": true,\n"
                + "  \"signed_request_object\": {\n"
                + "    \"required\": true,\n"
                + "    \"credentials\": [\n"
                + "      {\n"
                + "        \"id\": \"id\"\n"
                + "      }\n"
                + "    ]\n"
                + "  },\n"
                + "  \"compliance_level\": \"none\",\n"
                + "  \"skip_non_verifiable_callback_uri_confirmation_prompt\": true,\n"
                + "  \"token_exchange\": {\n"
                + "    \"allow_any_profile_of_type\": [\n"
                + "      \"custom_authentication\"\n"
                + "    ]\n"
                + "  },\n"
                + "  \"par_request_expiry\": 1,\n"
                + "  \"token_quota\": {\n"
                + "    \"client_credentials\": {\n"
                + "      \"enforce\": true,\n"
                + "      \"per_day\": 1,\n"
                + "      \"per_hour\": 1\n"
                + "    }\n"
                + "  },\n"
                + "  \"express_configuration\": {\n"
                + "    \"initiate_login_uri_template\": \"initiate_login_uri_template\",\n"
                + "    \"user_attribute_profile_id\": \"user_attribute_profile_id\",\n"
                + "    \"connection_profile_id\": \"connection_profile_id\",\n"
                + "    \"enable_client\": true,\n"
                + "    \"enable_organization\": true,\n"
                + "    \"linked_clients\": [\n"
                + "      {\n"
                + "        \"client_id\": \"client_id\"\n"
                + "      }\n"
                + "    ],\n"
                + "    \"okta_oin_client_id\": \"okta_oin_client_id\",\n"
                + "    \"admin_login_domain\": \"admin_login_domain\",\n"
                + "    \"oin_submission_id\": \"oin_submission_id\"\n"
                + "  },\n"
                + "  \"resource_server_identifier\": \"resource_server_identifier\",\n"
                + "  \"async_approval_notification_channels\": [\n"
                + "    \"guardian-push\"\n"
                + "  ]\n"
                + "}";
        JsonNode actualResponseNode = objectMapper.readTree(actualResponseJson);
        JsonNode expectedResponseNode = objectMapper.readTree(expectedResponseBody);
        Assertions.assertTrue(
                jsonEquals(expectedResponseNode, actualResponseNode),
                "Response body structure does not match expected");
        if (actualResponseNode.has("type") || actualResponseNode.has("_type") || actualResponseNode.has("kind")) {
            String discriminator = null;
            if (actualResponseNode.has("type"))
                discriminator = actualResponseNode.get("type").asText();
            else if (actualResponseNode.has("_type"))
                discriminator = actualResponseNode.get("_type").asText();
            else if (actualResponseNode.has("kind"))
                discriminator = actualResponseNode.get("kind").asText();
            Assertions.assertNotNull(discriminator, "Union type should have a discriminator field");
            Assertions.assertFalse(discriminator.isEmpty(), "Union discriminator should not be empty");
        }

        if (!actualResponseNode.isNull()) {
            Assertions.assertTrue(
                    actualResponseNode.isObject() || actualResponseNode.isArray() || actualResponseNode.isValueNode(),
                    "response should be a valid JSON value");
        }

        if (actualResponseNode.isArray()) {
            Assertions.assertTrue(actualResponseNode.size() >= 0, "Array should have valid size");
        }
        if (actualResponseNode.isObject()) {
            Assertions.assertTrue(actualResponseNode.size() >= 0, "Object should have valid field count");
        }
    }

    @Test
    public void testRotateSecret() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"client_id\":\"client_id\",\"tenant\":\"tenant\",\"name\":\"name\",\"description\":\"description\",\"global\":true,\"client_secret\":\"client_secret\",\"app_type\":\"native\",\"logo_uri\":\"logo_uri\",\"is_first_party\":true,\"oidc_conformant\":true,\"callbacks\":[\"callbacks\"],\"allowed_origins\":[\"allowed_origins\"],\"web_origins\":[\"web_origins\"],\"client_aliases\":[\"client_aliases\"],\"allowed_clients\":[\"allowed_clients\"],\"allowed_logout_urls\":[\"allowed_logout_urls\"],\"session_transfer\":{\"can_create_session_transfer_token\":true,\"enforce_cascade_revocation\":true,\"allowed_authentication_methods\":[\"cookie\"],\"enforce_device_binding\":\"ip\",\"allow_refresh_token\":true,\"enforce_online_refresh_tokens\":true},\"oidc_logout\":{\"backchannel_logout_urls\":[\"backchannel_logout_urls\"],\"backchannel_logout_initiators\":{\"mode\":\"custom\",\"selected_initiators\":[\"rp-logout\"]},\"backchannel_logout_session_metadata\":{\"include\":true}},\"grant_types\":[\"grant_types\"],\"jwt_configuration\":{\"lifetime_in_seconds\":1,\"secret_encoded\":true,\"scopes\":{\"key\":\"value\"},\"alg\":\"HS256\"},\"signing_keys\":[{\"pkcs7\":\"pkcs7\",\"cert\":\"cert\",\"subject\":\"subject\"}],\"encryption_key\":{\"pub\":\"pub\",\"cert\":\"cert\",\"subject\":\"subject\"},\"sso\":true,\"sso_disabled\":true,\"cross_origin_authentication\":true,\"cross_origin_loc\":\"cross_origin_loc\",\"custom_login_page_on\":true,\"custom_login_page\":\"custom_login_page\",\"custom_login_page_preview\":\"custom_login_page_preview\",\"form_template\":\"form_template\",\"addons\":{\"aws\":{\"principal\":\"principal\",\"role\":\"role\",\"lifetime_in_seconds\":1},\"azure_blob\":{\"accountName\":\"accountName\",\"storageAccessKey\":\"storageAccessKey\",\"containerName\":\"containerName\",\"blobName\":\"blobName\",\"expiration\":1,\"signedIdentifier\":\"signedIdentifier\",\"blob_read\":true,\"blob_write\":true,\"blob_delete\":true,\"container_read\":true,\"container_write\":true,\"container_delete\":true,\"container_list\":true},\"azure_sb\":{\"namespace\":\"namespace\",\"sasKeyName\":\"sasKeyName\",\"sasKey\":\"sasKey\",\"entityPath\":\"entityPath\",\"expiration\":1},\"rms\":{\"url\":\"url\"},\"mscrm\":{\"url\":\"url\"},\"slack\":{\"team\":\"team\"},\"sentry\":{\"org_slug\":\"org_slug\",\"base_url\":\"base_url\"},\"box\":{\"key\":\"value\"},\"cloudbees\":{\"key\":\"value\"},\"concur\":{\"key\":\"value\"},\"dropbox\":{\"key\":\"value\"},\"echosign\":{\"domain\":\"domain\"},\"egnyte\":{\"domain\":\"domain\"},\"firebase\":{\"secret\":\"secret\",\"private_key_id\":\"private_key_id\",\"private_key\":\"private_key\",\"client_email\":\"client_email\",\"lifetime_in_seconds\":1},\"newrelic\":{\"account\":\"account\"},\"office365\":{\"domain\":\"domain\",\"connection\":\"connection\"},\"salesforce\":{\"entity_id\":\"entity_id\"},\"salesforce_api\":{\"clientid\":\"clientid\",\"principal\":\"principal\",\"communityName\":\"communityName\",\"community_url_section\":\"community_url_section\"},\"salesforce_sandbox_api\":{\"clientid\":\"clientid\",\"principal\":\"principal\",\"communityName\":\"communityName\",\"community_url_section\":\"community_url_section\"},\"samlp\":{\"mappings\":{\"key\":\"value\"},\"audience\":\"audience\",\"recipient\":\"recipient\",\"createUpnClaim\":true,\"mapUnknownClaimsAsIs\":true,\"passthroughClaimsWithNoMapping\":true,\"mapIdentities\":true,\"signatureAlgorithm\":\"signatureAlgorithm\",\"digestAlgorithm\":\"digestAlgorithm\",\"issuer\":\"issuer\",\"destination\":\"destination\",\"lifetimeInSeconds\":1,\"signResponse\":true,\"nameIdentifierFormat\":\"nameIdentifierFormat\",\"nameIdentifierProbes\":[\"nameIdentifierProbes\"],\"authnContextClassRef\":\"authnContextClassRef\"},\"layer\":{\"providerId\":\"providerId\",\"keyId\":\"keyId\",\"privateKey\":\"privateKey\",\"principal\":\"principal\",\"expiration\":1},\"sap_api\":{\"clientid\":\"clientid\",\"usernameAttribute\":\"usernameAttribute\",\"tokenEndpointUrl\":\"tokenEndpointUrl\",\"scope\":\"scope\",\"servicePassword\":\"servicePassword\",\"nameIdentifierFormat\":\"nameIdentifierFormat\"},\"sharepoint\":{\"url\":\"url\",\"external_url\":[\"external_url\"]},\"springcm\":{\"acsurl\":\"acsurl\"},\"wams\":{\"masterkey\":\"masterkey\"},\"wsfed\":{\"key\":\"value\"},\"zendesk\":{\"accountName\":\"accountName\"},\"zoom\":{\"account\":\"account\"},\"sso_integration\":{\"name\":\"name\",\"version\":\"version\"}},\"token_endpoint_auth_method\":\"none\",\"is_token_endpoint_ip_header_trusted\":true,\"client_metadata\":{\"key\":\"value\"},\"mobile\":{\"android\":{\"app_package_name\":\"app_package_name\",\"sha256_cert_fingerprints\":[\"sha256_cert_fingerprints\"]},\"ios\":{\"team_id\":\"team_id\",\"app_bundle_identifier\":\"app_bundle_identifier\"}},\"initiate_login_uri\":\"initiate_login_uri\",\"refresh_token\":{\"rotation_type\":\"rotating\",\"expiration_type\":\"expiring\",\"leeway\":1,\"token_lifetime\":1,\"infinite_token_lifetime\":true,\"idle_token_lifetime\":1,\"infinite_idle_token_lifetime\":true,\"policies\":[{\"audience\":\"audience\",\"scope\":[\"scope\"]}]},\"default_organization\":{\"organization_id\":\"organization_id\",\"flows\":[\"client_credentials\"]},\"organization_usage\":\"deny\",\"organization_require_behavior\":\"no_prompt\",\"organization_discovery_methods\":[\"email\"],\"client_authentication_methods\":{\"private_key_jwt\":{\"credentials\":[{\"id\":\"id\"}]},\"tls_client_auth\":{\"credentials\":[{\"id\":\"id\"}]},\"self_signed_tls_client_auth\":{\"credentials\":[{\"id\":\"id\"}]}},\"require_pushed_authorization_requests\":true,\"require_proof_of_possession\":true,\"signed_request_object\":{\"required\":true,\"credentials\":[{\"id\":\"id\"}]},\"compliance_level\":\"none\",\"skip_non_verifiable_callback_uri_confirmation_prompt\":true,\"token_exchange\":{\"allow_any_profile_of_type\":[\"custom_authentication\"]},\"par_request_expiry\":1,\"token_quota\":{\"client_credentials\":{\"enforce\":true,\"per_day\":1,\"per_hour\":1}},\"express_configuration\":{\"initiate_login_uri_template\":\"initiate_login_uri_template\",\"user_attribute_profile_id\":\"user_attribute_profile_id\",\"connection_profile_id\":\"connection_profile_id\",\"enable_client\":true,\"enable_organization\":true,\"linked_clients\":[{\"client_id\":\"client_id\"}],\"okta_oin_client_id\":\"okta_oin_client_id\",\"admin_login_domain\":\"admin_login_domain\",\"oin_submission_id\":\"oin_submission_id\"},\"resource_server_identifier\":\"resource_server_identifier\",\"async_approval_notification_channels\":[\"guardian-push\"]}"));
        RotateClientSecretResponseContent response = client.clients().rotateSecret("id");
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("POST", request.getMethod());

        // Validate response body
        Assertions.assertNotNull(response, "Response should not be null");
        String actualResponseJson = objectMapper.writeValueAsString(response);
        String expectedResponseBody = ""
                + "{\n"
                + "  \"client_id\": \"client_id\",\n"
                + "  \"tenant\": \"tenant\",\n"
                + "  \"name\": \"name\",\n"
                + "  \"description\": \"description\",\n"
                + "  \"global\": true,\n"
                + "  \"client_secret\": \"client_secret\",\n"
                + "  \"app_type\": \"native\",\n"
                + "  \"logo_uri\": \"logo_uri\",\n"
                + "  \"is_first_party\": true,\n"
                + "  \"oidc_conformant\": true,\n"
                + "  \"callbacks\": [\n"
                + "    \"callbacks\"\n"
                + "  ],\n"
                + "  \"allowed_origins\": [\n"
                + "    \"allowed_origins\"\n"
                + "  ],\n"
                + "  \"web_origins\": [\n"
                + "    \"web_origins\"\n"
                + "  ],\n"
                + "  \"client_aliases\": [\n"
                + "    \"client_aliases\"\n"
                + "  ],\n"
                + "  \"allowed_clients\": [\n"
                + "    \"allowed_clients\"\n"
                + "  ],\n"
                + "  \"allowed_logout_urls\": [\n"
                + "    \"allowed_logout_urls\"\n"
                + "  ],\n"
                + "  \"session_transfer\": {\n"
                + "    \"can_create_session_transfer_token\": true,\n"
                + "    \"enforce_cascade_revocation\": true,\n"
                + "    \"allowed_authentication_methods\": [\n"
                + "      \"cookie\"\n"
                + "    ],\n"
                + "    \"enforce_device_binding\": \"ip\",\n"
                + "    \"allow_refresh_token\": true,\n"
                + "    \"enforce_online_refresh_tokens\": true\n"
                + "  },\n"
                + "  \"oidc_logout\": {\n"
                + "    \"backchannel_logout_urls\": [\n"
                + "      \"backchannel_logout_urls\"\n"
                + "    ],\n"
                + "    \"backchannel_logout_initiators\": {\n"
                + "      \"mode\": \"custom\",\n"
                + "      \"selected_initiators\": [\n"
                + "        \"rp-logout\"\n"
                + "      ]\n"
                + "    },\n"
                + "    \"backchannel_logout_session_metadata\": {\n"
                + "      \"include\": true\n"
                + "    }\n"
                + "  },\n"
                + "  \"grant_types\": [\n"
                + "    \"grant_types\"\n"
                + "  ],\n"
                + "  \"jwt_configuration\": {\n"
                + "    \"lifetime_in_seconds\": 1,\n"
                + "    \"secret_encoded\": true,\n"
                + "    \"scopes\": {\n"
                + "      \"key\": \"value\"\n"
                + "    },\n"
                + "    \"alg\": \"HS256\"\n"
                + "  },\n"
                + "  \"signing_keys\": [\n"
                + "    {\n"
                + "      \"pkcs7\": \"pkcs7\",\n"
                + "      \"cert\": \"cert\",\n"
                + "      \"subject\": \"subject\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"encryption_key\": {\n"
                + "    \"pub\": \"pub\",\n"
                + "    \"cert\": \"cert\",\n"
                + "    \"subject\": \"subject\"\n"
                + "  },\n"
                + "  \"sso\": true,\n"
                + "  \"sso_disabled\": true,\n"
                + "  \"cross_origin_authentication\": true,\n"
                + "  \"cross_origin_loc\": \"cross_origin_loc\",\n"
                + "  \"custom_login_page_on\": true,\n"
                + "  \"custom_login_page\": \"custom_login_page\",\n"
                + "  \"custom_login_page_preview\": \"custom_login_page_preview\",\n"
                + "  \"form_template\": \"form_template\",\n"
                + "  \"addons\": {\n"
                + "    \"aws\": {\n"
                + "      \"principal\": \"principal\",\n"
                + "      \"role\": \"role\",\n"
                + "      \"lifetime_in_seconds\": 1\n"
                + "    },\n"
                + "    \"azure_blob\": {\n"
                + "      \"accountName\": \"accountName\",\n"
                + "      \"storageAccessKey\": \"storageAccessKey\",\n"
                + "      \"containerName\": \"containerName\",\n"
                + "      \"blobName\": \"blobName\",\n"
                + "      \"expiration\": 1,\n"
                + "      \"signedIdentifier\": \"signedIdentifier\",\n"
                + "      \"blob_read\": true,\n"
                + "      \"blob_write\": true,\n"
                + "      \"blob_delete\": true,\n"
                + "      \"container_read\": true,\n"
                + "      \"container_write\": true,\n"
                + "      \"container_delete\": true,\n"
                + "      \"container_list\": true\n"
                + "    },\n"
                + "    \"azure_sb\": {\n"
                + "      \"namespace\": \"namespace\",\n"
                + "      \"sasKeyName\": \"sasKeyName\",\n"
                + "      \"sasKey\": \"sasKey\",\n"
                + "      \"entityPath\": \"entityPath\",\n"
                + "      \"expiration\": 1\n"
                + "    },\n"
                + "    \"rms\": {\n"
                + "      \"url\": \"url\"\n"
                + "    },\n"
                + "    \"mscrm\": {\n"
                + "      \"url\": \"url\"\n"
                + "    },\n"
                + "    \"slack\": {\n"
                + "      \"team\": \"team\"\n"
                + "    },\n"
                + "    \"sentry\": {\n"
                + "      \"org_slug\": \"org_slug\",\n"
                + "      \"base_url\": \"base_url\"\n"
                + "    },\n"
                + "    \"box\": {\n"
                + "      \"key\": \"value\"\n"
                + "    },\n"
                + "    \"cloudbees\": {\n"
                + "      \"key\": \"value\"\n"
                + "    },\n"
                + "    \"concur\": {\n"
                + "      \"key\": \"value\"\n"
                + "    },\n"
                + "    \"dropbox\": {\n"
                + "      \"key\": \"value\"\n"
                + "    },\n"
                + "    \"echosign\": {\n"
                + "      \"domain\": \"domain\"\n"
                + "    },\n"
                + "    \"egnyte\": {\n"
                + "      \"domain\": \"domain\"\n"
                + "    },\n"
                + "    \"firebase\": {\n"
                + "      \"secret\": \"secret\",\n"
                + "      \"private_key_id\": \"private_key_id\",\n"
                + "      \"private_key\": \"private_key\",\n"
                + "      \"client_email\": \"client_email\",\n"
                + "      \"lifetime_in_seconds\": 1\n"
                + "    },\n"
                + "    \"newrelic\": {\n"
                + "      \"account\": \"account\"\n"
                + "    },\n"
                + "    \"office365\": {\n"
                + "      \"domain\": \"domain\",\n"
                + "      \"connection\": \"connection\"\n"
                + "    },\n"
                + "    \"salesforce\": {\n"
                + "      \"entity_id\": \"entity_id\"\n"
                + "    },\n"
                + "    \"salesforce_api\": {\n"
                + "      \"clientid\": \"clientid\",\n"
                + "      \"principal\": \"principal\",\n"
                + "      \"communityName\": \"communityName\",\n"
                + "      \"community_url_section\": \"community_url_section\"\n"
                + "    },\n"
                + "    \"salesforce_sandbox_api\": {\n"
                + "      \"clientid\": \"clientid\",\n"
                + "      \"principal\": \"principal\",\n"
                + "      \"communityName\": \"communityName\",\n"
                + "      \"community_url_section\": \"community_url_section\"\n"
                + "    },\n"
                + "    \"samlp\": {\n"
                + "      \"mappings\": {\n"
                + "        \"key\": \"value\"\n"
                + "      },\n"
                + "      \"audience\": \"audience\",\n"
                + "      \"recipient\": \"recipient\",\n"
                + "      \"createUpnClaim\": true,\n"
                + "      \"mapUnknownClaimsAsIs\": true,\n"
                + "      \"passthroughClaimsWithNoMapping\": true,\n"
                + "      \"mapIdentities\": true,\n"
                + "      \"signatureAlgorithm\": \"signatureAlgorithm\",\n"
                + "      \"digestAlgorithm\": \"digestAlgorithm\",\n"
                + "      \"issuer\": \"issuer\",\n"
                + "      \"destination\": \"destination\",\n"
                + "      \"lifetimeInSeconds\": 1,\n"
                + "      \"signResponse\": true,\n"
                + "      \"nameIdentifierFormat\": \"nameIdentifierFormat\",\n"
                + "      \"nameIdentifierProbes\": [\n"
                + "        \"nameIdentifierProbes\"\n"
                + "      ],\n"
                + "      \"authnContextClassRef\": \"authnContextClassRef\"\n"
                + "    },\n"
                + "    \"layer\": {\n"
                + "      \"providerId\": \"providerId\",\n"
                + "      \"keyId\": \"keyId\",\n"
                + "      \"privateKey\": \"privateKey\",\n"
                + "      \"principal\": \"principal\",\n"
                + "      \"expiration\": 1\n"
                + "    },\n"
                + "    \"sap_api\": {\n"
                + "      \"clientid\": \"clientid\",\n"
                + "      \"usernameAttribute\": \"usernameAttribute\",\n"
                + "      \"tokenEndpointUrl\": \"tokenEndpointUrl\",\n"
                + "      \"scope\": \"scope\",\n"
                + "      \"servicePassword\": \"servicePassword\",\n"
                + "      \"nameIdentifierFormat\": \"nameIdentifierFormat\"\n"
                + "    },\n"
                + "    \"sharepoint\": {\n"
                + "      \"url\": \"url\",\n"
                + "      \"external_url\": [\n"
                + "        \"external_url\"\n"
                + "      ]\n"
                + "    },\n"
                + "    \"springcm\": {\n"
                + "      \"acsurl\": \"acsurl\"\n"
                + "    },\n"
                + "    \"wams\": {\n"
                + "      \"masterkey\": \"masterkey\"\n"
                + "    },\n"
                + "    \"wsfed\": {\n"
                + "      \"key\": \"value\"\n"
                + "    },\n"
                + "    \"zendesk\": {\n"
                + "      \"accountName\": \"accountName\"\n"
                + "    },\n"
                + "    \"zoom\": {\n"
                + "      \"account\": \"account\"\n"
                + "    },\n"
                + "    \"sso_integration\": {\n"
                + "      \"name\": \"name\",\n"
                + "      \"version\": \"version\"\n"
                + "    }\n"
                + "  },\n"
                + "  \"token_endpoint_auth_method\": \"none\",\n"
                + "  \"is_token_endpoint_ip_header_trusted\": true,\n"
                + "  \"client_metadata\": {\n"
                + "    \"key\": \"value\"\n"
                + "  },\n"
                + "  \"mobile\": {\n"
                + "    \"android\": {\n"
                + "      \"app_package_name\": \"app_package_name\",\n"
                + "      \"sha256_cert_fingerprints\": [\n"
                + "        \"sha256_cert_fingerprints\"\n"
                + "      ]\n"
                + "    },\n"
                + "    \"ios\": {\n"
                + "      \"team_id\": \"team_id\",\n"
                + "      \"app_bundle_identifier\": \"app_bundle_identifier\"\n"
                + "    }\n"
                + "  },\n"
                + "  \"initiate_login_uri\": \"initiate_login_uri\",\n"
                + "  \"refresh_token\": {\n"
                + "    \"rotation_type\": \"rotating\",\n"
                + "    \"expiration_type\": \"expiring\",\n"
                + "    \"leeway\": 1,\n"
                + "    \"token_lifetime\": 1,\n"
                + "    \"infinite_token_lifetime\": true,\n"
                + "    \"idle_token_lifetime\": 1,\n"
                + "    \"infinite_idle_token_lifetime\": true,\n"
                + "    \"policies\": [\n"
                + "      {\n"
                + "        \"audience\": \"audience\",\n"
                + "        \"scope\": [\n"
                + "          \"scope\"\n"
                + "        ]\n"
                + "      }\n"
                + "    ]\n"
                + "  },\n"
                + "  \"default_organization\": {\n"
                + "    \"organization_id\": \"organization_id\",\n"
                + "    \"flows\": [\n"
                + "      \"client_credentials\"\n"
                + "    ]\n"
                + "  },\n"
                + "  \"organization_usage\": \"deny\",\n"
                + "  \"organization_require_behavior\": \"no_prompt\",\n"
                + "  \"organization_discovery_methods\": [\n"
                + "    \"email\"\n"
                + "  ],\n"
                + "  \"client_authentication_methods\": {\n"
                + "    \"private_key_jwt\": {\n"
                + "      \"credentials\": [\n"
                + "        {\n"
                + "          \"id\": \"id\"\n"
                + "        }\n"
                + "      ]\n"
                + "    },\n"
                + "    \"tls_client_auth\": {\n"
                + "      \"credentials\": [\n"
                + "        {\n"
                + "          \"id\": \"id\"\n"
                + "        }\n"
                + "      ]\n"
                + "    },\n"
                + "    \"self_signed_tls_client_auth\": {\n"
                + "      \"credentials\": [\n"
                + "        {\n"
                + "          \"id\": \"id\"\n"
                + "        }\n"
                + "      ]\n"
                + "    }\n"
                + "  },\n"
                + "  \"require_pushed_authorization_requests\": true,\n"
                + "  \"require_proof_of_possession\": true,\n"
                + "  \"signed_request_object\": {\n"
                + "    \"required\": true,\n"
                + "    \"credentials\": [\n"
                + "      {\n"
                + "        \"id\": \"id\"\n"
                + "      }\n"
                + "    ]\n"
                + "  },\n"
                + "  \"compliance_level\": \"none\",\n"
                + "  \"skip_non_verifiable_callback_uri_confirmation_prompt\": true,\n"
                + "  \"token_exchange\": {\n"
                + "    \"allow_any_profile_of_type\": [\n"
                + "      \"custom_authentication\"\n"
                + "    ]\n"
                + "  },\n"
                + "  \"par_request_expiry\": 1,\n"
                + "  \"token_quota\": {\n"
                + "    \"client_credentials\": {\n"
                + "      \"enforce\": true,\n"
                + "      \"per_day\": 1,\n"
                + "      \"per_hour\": 1\n"
                + "    }\n"
                + "  },\n"
                + "  \"express_configuration\": {\n"
                + "    \"initiate_login_uri_template\": \"initiate_login_uri_template\",\n"
                + "    \"user_attribute_profile_id\": \"user_attribute_profile_id\",\n"
                + "    \"connection_profile_id\": \"connection_profile_id\",\n"
                + "    \"enable_client\": true,\n"
                + "    \"enable_organization\": true,\n"
                + "    \"linked_clients\": [\n"
                + "      {\n"
                + "        \"client_id\": \"client_id\"\n"
                + "      }\n"
                + "    ],\n"
                + "    \"okta_oin_client_id\": \"okta_oin_client_id\",\n"
                + "    \"admin_login_domain\": \"admin_login_domain\",\n"
                + "    \"oin_submission_id\": \"oin_submission_id\"\n"
                + "  },\n"
                + "  \"resource_server_identifier\": \"resource_server_identifier\",\n"
                + "  \"async_approval_notification_channels\": [\n"
                + "    \"guardian-push\"\n"
                + "  ]\n"
                + "}";
        JsonNode actualResponseNode = objectMapper.readTree(actualResponseJson);
        JsonNode expectedResponseNode = objectMapper.readTree(expectedResponseBody);
        Assertions.assertTrue(
                jsonEquals(expectedResponseNode, actualResponseNode),
                "Response body structure does not match expected");
        if (actualResponseNode.has("type") || actualResponseNode.has("_type") || actualResponseNode.has("kind")) {
            String discriminator = null;
            if (actualResponseNode.has("type"))
                discriminator = actualResponseNode.get("type").asText();
            else if (actualResponseNode.has("_type"))
                discriminator = actualResponseNode.get("_type").asText();
            else if (actualResponseNode.has("kind"))
                discriminator = actualResponseNode.get("kind").asText();
            Assertions.assertNotNull(discriminator, "Union type should have a discriminator field");
            Assertions.assertFalse(discriminator.isEmpty(), "Union discriminator should not be empty");
        }

        if (!actualResponseNode.isNull()) {
            Assertions.assertTrue(
                    actualResponseNode.isObject() || actualResponseNode.isArray() || actualResponseNode.isValueNode(),
                    "response should be a valid JSON value");
        }

        if (actualResponseNode.isArray()) {
            Assertions.assertTrue(actualResponseNode.size() >= 0, "Array should have valid size");
        }
        if (actualResponseNode.isObject()) {
            Assertions.assertTrue(actualResponseNode.size() >= 0, "Object should have valid field count");
        }
    }

    /**
     * Compares two JsonNodes with numeric equivalence and null safety.
     * For objects, checks that all fields in 'expected' exist in 'actual' with matching values.
     * Allows 'actual' to have extra fields (e.g., default values added during serialization).
     */
    private boolean jsonEquals(JsonNode expected, JsonNode actual) {
        if (expected == null && actual == null) return true;
        if (expected == null || actual == null) return false;
        if (expected.equals(actual)) return true;
        if (expected.isNumber() && actual.isNumber())
            return Math.abs(expected.doubleValue() - actual.doubleValue()) < 1e-10;
        if (expected.isObject() && actual.isObject()) {
            java.util.Iterator<java.util.Map.Entry<String, JsonNode>> iter = expected.fields();
            while (iter.hasNext()) {
                java.util.Map.Entry<String, JsonNode> entry = iter.next();
                JsonNode actualValue = actual.get(entry.getKey());
                if (actualValue == null || !jsonEquals(entry.getValue(), actualValue)) return false;
            }
            return true;
        }
        if (expected.isArray() && actual.isArray()) {
            if (expected.size() != actual.size()) return false;
            for (int i = 0; i < expected.size(); i++) {
                if (!jsonEquals(expected.get(i), actual.get(i))) return false;
            }
            return true;
        }
        return false;
    }
}

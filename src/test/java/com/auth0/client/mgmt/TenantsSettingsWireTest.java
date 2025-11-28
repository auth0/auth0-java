package com.auth0.client.mgmt;

import com.auth0.client.mgmt.core.ObjectMappers;
import com.auth0.client.mgmt.tenants.types.GetTenantSettingsRequestParameters;
import com.auth0.client.mgmt.tenants.types.UpdateTenantSettingsRequestContent;
import com.auth0.client.mgmt.types.GetTenantSettingsResponseContent;
import com.auth0.client.mgmt.types.UpdateTenantSettingsResponseContent;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TenantsSettingsWireTest {
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
    public void testGet() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"change_password\":{\"enabled\":true,\"html\":\"html\"},\"guardian_mfa_page\":{\"enabled\":true,\"html\":\"html\"},\"default_audience\":\"default_audience\",\"default_directory\":\"default_directory\",\"error_page\":{\"html\":\"html\",\"show_log_link\":true,\"url\":\"url\"},\"device_flow\":{\"charset\":\"base20\",\"mask\":\"mask\"},\"default_token_quota\":{\"clients\":{\"client_credentials\":{}},\"organizations\":{\"client_credentials\":{}}},\"flags\":{\"change_pwd_flow_v1\":true,\"enable_apis_section\":true,\"disable_impersonation\":true,\"enable_client_connections\":true,\"enable_pipeline2\":true,\"allow_legacy_delegation_grant_types\":true,\"allow_legacy_ro_grant_types\":true,\"allow_legacy_tokeninfo_endpoint\":true,\"enable_legacy_profile\":true,\"enable_idtoken_api2\":true,\"enable_public_signup_user_exists_error\":true,\"enable_sso\":true,\"allow_changing_enable_sso\":true,\"disable_clickjack_protection_headers\":true,\"no_disclose_enterprise_connections\":true,\"enforce_client_authentication_on_passwordless_start\":true,\"enable_adfs_waad_email_verification\":true,\"revoke_refresh_token_grant\":true,\"dashboard_log_streams_next\":true,\"dashboard_insights_view\":true,\"disable_fields_map_fix\":true,\"mfa_show_factor_list_on_enrollment\":true,\"remove_alg_from_jwks\":true,\"improved_signup_bot_detection_in_classic\":true,\"genai_trial\":true,\"enable_dynamic_client_registration\":true,\"disable_management_api_sms_obfuscation\":true,\"trust_azure_adfs_email_verified_connection_property\":true,\"custom_domains_provisioning\":true},\"friendly_name\":\"friendly_name\",\"picture_url\":\"picture_url\",\"support_email\":\"support_email\",\"support_url\":\"support_url\",\"allowed_logout_urls\":[\"allowed_logout_urls\"],\"session_lifetime\":1.1,\"idle_session_lifetime\":1.1,\"ephemeral_session_lifetime\":1.1,\"idle_ephemeral_session_lifetime\":1.1,\"sandbox_version\":\"sandbox_version\",\"legacy_sandbox_version\":\"legacy_sandbox_version\",\"sandbox_versions_available\":[\"sandbox_versions_available\"],\"default_redirection_uri\":\"default_redirection_uri\",\"enabled_locales\":[\"am\"],\"session_cookie\":{\"mode\":\"persistent\"},\"sessions\":{\"oidc_logout_prompt_enabled\":true},\"oidc_logout\":{\"rp_logout_end_session_endpoint_discovery\":true},\"allow_organization_name_in_authentication_api\":true,\"customize_mfa_in_postlogin_action\":true,\"acr_values_supported\":[\"acr_values_supported\"],\"mtls\":{\"enable_endpoint_aliases\":true},\"pushed_authorization_requests_supported\":true,\"authorization_response_iss_parameter_supported\":true}"));
        GetTenantSettingsResponseContent response = client.tenants()
                .settings()
                .get(GetTenantSettingsRequestParameters.builder()
                        .fields("fields")
                        .includeFields(true)
                        .build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("GET", request.getMethod());

        // Validate response body
        Assertions.assertNotNull(response, "Response should not be null");
        String actualResponseJson = objectMapper.writeValueAsString(response);
        String expectedResponseBody = ""
                + "{\n"
                + "  \"change_password\": {\n"
                + "    \"enabled\": true,\n"
                + "    \"html\": \"html\"\n"
                + "  },\n"
                + "  \"guardian_mfa_page\": {\n"
                + "    \"enabled\": true,\n"
                + "    \"html\": \"html\"\n"
                + "  },\n"
                + "  \"default_audience\": \"default_audience\",\n"
                + "  \"default_directory\": \"default_directory\",\n"
                + "  \"error_page\": {\n"
                + "    \"html\": \"html\",\n"
                + "    \"show_log_link\": true,\n"
                + "    \"url\": \"url\"\n"
                + "  },\n"
                + "  \"device_flow\": {\n"
                + "    \"charset\": \"base20\",\n"
                + "    \"mask\": \"mask\"\n"
                + "  },\n"
                + "  \"default_token_quota\": {\n"
                + "    \"clients\": {\n"
                + "      \"client_credentials\": {}\n"
                + "    },\n"
                + "    \"organizations\": {\n"
                + "      \"client_credentials\": {}\n"
                + "    }\n"
                + "  },\n"
                + "  \"flags\": {\n"
                + "    \"change_pwd_flow_v1\": true,\n"
                + "    \"enable_apis_section\": true,\n"
                + "    \"disable_impersonation\": true,\n"
                + "    \"enable_client_connections\": true,\n"
                + "    \"enable_pipeline2\": true,\n"
                + "    \"allow_legacy_delegation_grant_types\": true,\n"
                + "    \"allow_legacy_ro_grant_types\": true,\n"
                + "    \"allow_legacy_tokeninfo_endpoint\": true,\n"
                + "    \"enable_legacy_profile\": true,\n"
                + "    \"enable_idtoken_api2\": true,\n"
                + "    \"enable_public_signup_user_exists_error\": true,\n"
                + "    \"enable_sso\": true,\n"
                + "    \"allow_changing_enable_sso\": true,\n"
                + "    \"disable_clickjack_protection_headers\": true,\n"
                + "    \"no_disclose_enterprise_connections\": true,\n"
                + "    \"enforce_client_authentication_on_passwordless_start\": true,\n"
                + "    \"enable_adfs_waad_email_verification\": true,\n"
                + "    \"revoke_refresh_token_grant\": true,\n"
                + "    \"dashboard_log_streams_next\": true,\n"
                + "    \"dashboard_insights_view\": true,\n"
                + "    \"disable_fields_map_fix\": true,\n"
                + "    \"mfa_show_factor_list_on_enrollment\": true,\n"
                + "    \"remove_alg_from_jwks\": true,\n"
                + "    \"improved_signup_bot_detection_in_classic\": true,\n"
                + "    \"genai_trial\": true,\n"
                + "    \"enable_dynamic_client_registration\": true,\n"
                + "    \"disable_management_api_sms_obfuscation\": true,\n"
                + "    \"trust_azure_adfs_email_verified_connection_property\": true,\n"
                + "    \"custom_domains_provisioning\": true\n"
                + "  },\n"
                + "  \"friendly_name\": \"friendly_name\",\n"
                + "  \"picture_url\": \"picture_url\",\n"
                + "  \"support_email\": \"support_email\",\n"
                + "  \"support_url\": \"support_url\",\n"
                + "  \"allowed_logout_urls\": [\n"
                + "    \"allowed_logout_urls\"\n"
                + "  ],\n"
                + "  \"session_lifetime\": 1.1,\n"
                + "  \"idle_session_lifetime\": 1.1,\n"
                + "  \"ephemeral_session_lifetime\": 1.1,\n"
                + "  \"idle_ephemeral_session_lifetime\": 1.1,\n"
                + "  \"sandbox_version\": \"sandbox_version\",\n"
                + "  \"legacy_sandbox_version\": \"legacy_sandbox_version\",\n"
                + "  \"sandbox_versions_available\": [\n"
                + "    \"sandbox_versions_available\"\n"
                + "  ],\n"
                + "  \"default_redirection_uri\": \"default_redirection_uri\",\n"
                + "  \"enabled_locales\": [\n"
                + "    \"am\"\n"
                + "  ],\n"
                + "  \"session_cookie\": {\n"
                + "    \"mode\": \"persistent\"\n"
                + "  },\n"
                + "  \"sessions\": {\n"
                + "    \"oidc_logout_prompt_enabled\": true\n"
                + "  },\n"
                + "  \"oidc_logout\": {\n"
                + "    \"rp_logout_end_session_endpoint_discovery\": true\n"
                + "  },\n"
                + "  \"allow_organization_name_in_authentication_api\": true,\n"
                + "  \"customize_mfa_in_postlogin_action\": true,\n"
                + "  \"acr_values_supported\": [\n"
                + "    \"acr_values_supported\"\n"
                + "  ],\n"
                + "  \"mtls\": {\n"
                + "    \"enable_endpoint_aliases\": true\n"
                + "  },\n"
                + "  \"pushed_authorization_requests_supported\": true,\n"
                + "  \"authorization_response_iss_parameter_supported\": true\n"
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
    public void testUpdate() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"change_password\":{\"enabled\":true,\"html\":\"html\"},\"guardian_mfa_page\":{\"enabled\":true,\"html\":\"html\"},\"default_audience\":\"default_audience\",\"default_directory\":\"default_directory\",\"error_page\":{\"html\":\"html\",\"show_log_link\":true,\"url\":\"url\"},\"device_flow\":{\"charset\":\"base20\",\"mask\":\"mask\"},\"default_token_quota\":{\"clients\":{\"client_credentials\":{}},\"organizations\":{\"client_credentials\":{}}},\"flags\":{\"change_pwd_flow_v1\":true,\"enable_apis_section\":true,\"disable_impersonation\":true,\"enable_client_connections\":true,\"enable_pipeline2\":true,\"allow_legacy_delegation_grant_types\":true,\"allow_legacy_ro_grant_types\":true,\"allow_legacy_tokeninfo_endpoint\":true,\"enable_legacy_profile\":true,\"enable_idtoken_api2\":true,\"enable_public_signup_user_exists_error\":true,\"enable_sso\":true,\"allow_changing_enable_sso\":true,\"disable_clickjack_protection_headers\":true,\"no_disclose_enterprise_connections\":true,\"enforce_client_authentication_on_passwordless_start\":true,\"enable_adfs_waad_email_verification\":true,\"revoke_refresh_token_grant\":true,\"dashboard_log_streams_next\":true,\"dashboard_insights_view\":true,\"disable_fields_map_fix\":true,\"mfa_show_factor_list_on_enrollment\":true,\"remove_alg_from_jwks\":true,\"improved_signup_bot_detection_in_classic\":true,\"genai_trial\":true,\"enable_dynamic_client_registration\":true,\"disable_management_api_sms_obfuscation\":true,\"trust_azure_adfs_email_verified_connection_property\":true,\"custom_domains_provisioning\":true},\"friendly_name\":\"friendly_name\",\"picture_url\":\"picture_url\",\"support_email\":\"support_email\",\"support_url\":\"support_url\",\"allowed_logout_urls\":[\"allowed_logout_urls\"],\"session_lifetime\":1.1,\"idle_session_lifetime\":1.1,\"ephemeral_session_lifetime\":1.1,\"idle_ephemeral_session_lifetime\":1.1,\"sandbox_version\":\"sandbox_version\",\"legacy_sandbox_version\":\"legacy_sandbox_version\",\"sandbox_versions_available\":[\"sandbox_versions_available\"],\"default_redirection_uri\":\"default_redirection_uri\",\"enabled_locales\":[\"am\"],\"session_cookie\":{\"mode\":\"persistent\"},\"sessions\":{\"oidc_logout_prompt_enabled\":true},\"oidc_logout\":{\"rp_logout_end_session_endpoint_discovery\":true},\"allow_organization_name_in_authentication_api\":true,\"customize_mfa_in_postlogin_action\":true,\"acr_values_supported\":[\"acr_values_supported\"],\"mtls\":{\"enable_endpoint_aliases\":true},\"pushed_authorization_requests_supported\":true,\"authorization_response_iss_parameter_supported\":true}"));
        UpdateTenantSettingsResponseContent response = client.tenants()
                .settings()
                .update(UpdateTenantSettingsRequestContent.builder().build());
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
                + "  \"change_password\": {\n"
                + "    \"enabled\": true,\n"
                + "    \"html\": \"html\"\n"
                + "  },\n"
                + "  \"guardian_mfa_page\": {\n"
                + "    \"enabled\": true,\n"
                + "    \"html\": \"html\"\n"
                + "  },\n"
                + "  \"default_audience\": \"default_audience\",\n"
                + "  \"default_directory\": \"default_directory\",\n"
                + "  \"error_page\": {\n"
                + "    \"html\": \"html\",\n"
                + "    \"show_log_link\": true,\n"
                + "    \"url\": \"url\"\n"
                + "  },\n"
                + "  \"device_flow\": {\n"
                + "    \"charset\": \"base20\",\n"
                + "    \"mask\": \"mask\"\n"
                + "  },\n"
                + "  \"default_token_quota\": {\n"
                + "    \"clients\": {\n"
                + "      \"client_credentials\": {}\n"
                + "    },\n"
                + "    \"organizations\": {\n"
                + "      \"client_credentials\": {}\n"
                + "    }\n"
                + "  },\n"
                + "  \"flags\": {\n"
                + "    \"change_pwd_flow_v1\": true,\n"
                + "    \"enable_apis_section\": true,\n"
                + "    \"disable_impersonation\": true,\n"
                + "    \"enable_client_connections\": true,\n"
                + "    \"enable_pipeline2\": true,\n"
                + "    \"allow_legacy_delegation_grant_types\": true,\n"
                + "    \"allow_legacy_ro_grant_types\": true,\n"
                + "    \"allow_legacy_tokeninfo_endpoint\": true,\n"
                + "    \"enable_legacy_profile\": true,\n"
                + "    \"enable_idtoken_api2\": true,\n"
                + "    \"enable_public_signup_user_exists_error\": true,\n"
                + "    \"enable_sso\": true,\n"
                + "    \"allow_changing_enable_sso\": true,\n"
                + "    \"disable_clickjack_protection_headers\": true,\n"
                + "    \"no_disclose_enterprise_connections\": true,\n"
                + "    \"enforce_client_authentication_on_passwordless_start\": true,\n"
                + "    \"enable_adfs_waad_email_verification\": true,\n"
                + "    \"revoke_refresh_token_grant\": true,\n"
                + "    \"dashboard_log_streams_next\": true,\n"
                + "    \"dashboard_insights_view\": true,\n"
                + "    \"disable_fields_map_fix\": true,\n"
                + "    \"mfa_show_factor_list_on_enrollment\": true,\n"
                + "    \"remove_alg_from_jwks\": true,\n"
                + "    \"improved_signup_bot_detection_in_classic\": true,\n"
                + "    \"genai_trial\": true,\n"
                + "    \"enable_dynamic_client_registration\": true,\n"
                + "    \"disable_management_api_sms_obfuscation\": true,\n"
                + "    \"trust_azure_adfs_email_verified_connection_property\": true,\n"
                + "    \"custom_domains_provisioning\": true\n"
                + "  },\n"
                + "  \"friendly_name\": \"friendly_name\",\n"
                + "  \"picture_url\": \"picture_url\",\n"
                + "  \"support_email\": \"support_email\",\n"
                + "  \"support_url\": \"support_url\",\n"
                + "  \"allowed_logout_urls\": [\n"
                + "    \"allowed_logout_urls\"\n"
                + "  ],\n"
                + "  \"session_lifetime\": 1.1,\n"
                + "  \"idle_session_lifetime\": 1.1,\n"
                + "  \"ephemeral_session_lifetime\": 1.1,\n"
                + "  \"idle_ephemeral_session_lifetime\": 1.1,\n"
                + "  \"sandbox_version\": \"sandbox_version\",\n"
                + "  \"legacy_sandbox_version\": \"legacy_sandbox_version\",\n"
                + "  \"sandbox_versions_available\": [\n"
                + "    \"sandbox_versions_available\"\n"
                + "  ],\n"
                + "  \"default_redirection_uri\": \"default_redirection_uri\",\n"
                + "  \"enabled_locales\": [\n"
                + "    \"am\"\n"
                + "  ],\n"
                + "  \"session_cookie\": {\n"
                + "    \"mode\": \"persistent\"\n"
                + "  },\n"
                + "  \"sessions\": {\n"
                + "    \"oidc_logout_prompt_enabled\": true\n"
                + "  },\n"
                + "  \"oidc_logout\": {\n"
                + "    \"rp_logout_end_session_endpoint_discovery\": true\n"
                + "  },\n"
                + "  \"allow_organization_name_in_authentication_api\": true,\n"
                + "  \"customize_mfa_in_postlogin_action\": true,\n"
                + "  \"acr_values_supported\": [\n"
                + "    \"acr_values_supported\"\n"
                + "  ],\n"
                + "  \"mtls\": {\n"
                + "    \"enable_endpoint_aliases\": true\n"
                + "  },\n"
                + "  \"pushed_authorization_requests_supported\": true,\n"
                + "  \"authorization_response_iss_parameter_supported\": true\n"
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
     * Compares two JsonNodes with numeric equivalence.
     */
    private boolean jsonEquals(JsonNode a, JsonNode b) {
        if (a.equals(b)) return true;
        if (a.isNumber() && b.isNumber()) return Math.abs(a.doubleValue() - b.doubleValue()) < 1e-10;
        if (a.isObject() && b.isObject()) {
            if (a.size() != b.size()) return false;
            java.util.Iterator<java.util.Map.Entry<String, JsonNode>> iter = a.fields();
            while (iter.hasNext()) {
                java.util.Map.Entry<String, JsonNode> entry = iter.next();
                if (!jsonEquals(entry.getValue(), b.get(entry.getKey()))) return false;
            }
            return true;
        }
        if (a.isArray() && b.isArray()) {
            if (a.size() != b.size()) return false;
            for (int i = 0; i < a.size(); i++) {
                if (!jsonEquals(a.get(i), b.get(i))) return false;
            }
            return true;
        }
        return false;
    }
}

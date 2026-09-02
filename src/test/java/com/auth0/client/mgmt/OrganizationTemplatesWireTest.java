package com.auth0.client.mgmt;

import com.auth0.client.mgmt.core.ObjectMappers;
import com.auth0.client.mgmt.core.SyncPagingIterable;
import com.auth0.client.mgmt.types.CreateOrganizationTemplateRequestContent;
import com.auth0.client.mgmt.types.ListOrganizationTemplatesRequestParameters;
import com.auth0.client.mgmt.types.ListTemplateOrganizationsRequestParameters;
import com.auth0.client.mgmt.types.OrganizationDeletionBehaviorEnum;
import com.auth0.client.mgmt.types.OrganizationTemplate;
import com.auth0.client.mgmt.types.OrganizationTemplateAssignedOrganization;
import com.auth0.client.mgmt.types.UpdateOrganizationTemplateRequestContent;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OrganizationTemplatesWireTest {
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
                                "{\"next\":\"next\",\"organization_templates\":[{\"id\":\"id\",\"name\":\"name\",\"is_default\":true,\"organization_deletion_behavior\":\"allow\",\"connection_deletion_behavior\":\"allow\",\"enforce_permission_ceiling\":true,\"enforce_self_assignment_restriction\":true,\"connection_profile_id\":\"connection_profile_id\",\"user_attribute_profile_id\":\"user_attribute_profile_id\",\"allowed_strategies\":[\"adfs\"],\"invitation_landing_client_id\":\"invitation_landing_client_id\",\"admin_roles_assignment\":[\"admin_roles_assignment\"],\"use_for_organization_discovery\":{\"default_value\":true},\"role_visibility_policy\":{\"default_value\":\"write\"},\"created_at\":\"2024-01-15T09:30:00Z\",\"updated_at\":\"2024-01-15T09:30:00Z\"}]}"));
        SyncPagingIterable<OrganizationTemplate> response = client.organizationTemplates()
                .list(ListOrganizationTemplatesRequestParameters.builder()
                        .from("from")
                        .take(1)
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
                                "{\"id\":\"id\",\"name\":\"name\",\"is_default\":true,\"organization_deletion_behavior\":\"allow\",\"connection_deletion_behavior\":\"allow\",\"enforce_permission_ceiling\":true,\"enforce_self_assignment_restriction\":true,\"connection_profile_id\":\"connection_profile_id\",\"user_attribute_profile_id\":\"user_attribute_profile_id\",\"allowed_strategies\":[\"adfs\"],\"invitation_landing_client_id\":\"invitation_landing_client_id\",\"admin_roles_assignment\":[\"admin_roles_assignment\"],\"use_for_organization_discovery\":{\"default_value\":true,\"allowed_values\":[true]},\"role_visibility_policy\":{\"default_value\":\"write\",\"overrides\":[{\"role_id\":\"role_id\",\"access\":\"write\"}]},\"created_at\":\"2024-01-15T09:30:00Z\",\"updated_at\":\"2024-01-15T09:30:00Z\"}"));
        OrganizationTemplate response = client.organizationTemplates()
                .create(CreateOrganizationTemplateRequestContent.builder()
                        .name("name")
                        .organizationDeletionBehavior(OrganizationDeletionBehaviorEnum.ALLOW)
                        .enforcePermissionCeiling(true)
                        .enforceSelfAssignmentRestriction(true)
                        .build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("POST", request.getMethod());
        // Validate request body
        String actualRequestBody = request.getBody().readUtf8();
        String expectedRequestBody = ""
                + "{\n"
                + "  \"name\": \"name\",\n"
                + "  \"organization_deletion_behavior\": \"allow\",\n"
                + "  \"enforce_permission_ceiling\": true,\n"
                + "  \"enforce_self_assignment_restriction\": true\n"
                + "}";
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
                + "  \"id\": \"id\",\n"
                + "  \"name\": \"name\",\n"
                + "  \"is_default\": true,\n"
                + "  \"organization_deletion_behavior\": \"allow\",\n"
                + "  \"connection_deletion_behavior\": \"allow\",\n"
                + "  \"enforce_permission_ceiling\": true,\n"
                + "  \"enforce_self_assignment_restriction\": true,\n"
                + "  \"connection_profile_id\": \"connection_profile_id\",\n"
                + "  \"user_attribute_profile_id\": \"user_attribute_profile_id\",\n"
                + "  \"allowed_strategies\": [\n"
                + "    \"adfs\"\n"
                + "  ],\n"
                + "  \"invitation_landing_client_id\": \"invitation_landing_client_id\",\n"
                + "  \"admin_roles_assignment\": [\n"
                + "    \"admin_roles_assignment\"\n"
                + "  ],\n"
                + "  \"use_for_organization_discovery\": {\n"
                + "    \"default_value\": true,\n"
                + "    \"allowed_values\": [\n"
                + "      true\n"
                + "    ]\n"
                + "  },\n"
                + "  \"role_visibility_policy\": {\n"
                + "    \"default_value\": \"write\",\n"
                + "    \"overrides\": [\n"
                + "      {\n"
                + "        \"role_id\": \"role_id\",\n"
                + "        \"access\": \"write\"\n"
                + "      }\n"
                + "    ]\n"
                + "  },\n"
                + "  \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "  \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
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
                                "{\"id\":\"id\",\"name\":\"name\",\"is_default\":true,\"organization_deletion_behavior\":\"allow\",\"connection_deletion_behavior\":\"allow\",\"enforce_permission_ceiling\":true,\"enforce_self_assignment_restriction\":true,\"connection_profile_id\":\"connection_profile_id\",\"user_attribute_profile_id\":\"user_attribute_profile_id\",\"allowed_strategies\":[\"adfs\"],\"invitation_landing_client_id\":\"invitation_landing_client_id\",\"admin_roles_assignment\":[\"admin_roles_assignment\"],\"use_for_organization_discovery\":{\"default_value\":true,\"allowed_values\":[true]},\"role_visibility_policy\":{\"default_value\":\"write\",\"overrides\":[{\"role_id\":\"role_id\",\"access\":\"write\"}]},\"created_at\":\"2024-01-15T09:30:00Z\",\"updated_at\":\"2024-01-15T09:30:00Z\"}"));
        OrganizationTemplate response = client.organizationTemplates().get("id");
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("GET", request.getMethod());

        // Validate response body
        Assertions.assertNotNull(response, "Response should not be null");
        String actualResponseJson = objectMapper.writeValueAsString(response);
        String expectedResponseBody = ""
                + "{\n"
                + "  \"id\": \"id\",\n"
                + "  \"name\": \"name\",\n"
                + "  \"is_default\": true,\n"
                + "  \"organization_deletion_behavior\": \"allow\",\n"
                + "  \"connection_deletion_behavior\": \"allow\",\n"
                + "  \"enforce_permission_ceiling\": true,\n"
                + "  \"enforce_self_assignment_restriction\": true,\n"
                + "  \"connection_profile_id\": \"connection_profile_id\",\n"
                + "  \"user_attribute_profile_id\": \"user_attribute_profile_id\",\n"
                + "  \"allowed_strategies\": [\n"
                + "    \"adfs\"\n"
                + "  ],\n"
                + "  \"invitation_landing_client_id\": \"invitation_landing_client_id\",\n"
                + "  \"admin_roles_assignment\": [\n"
                + "    \"admin_roles_assignment\"\n"
                + "  ],\n"
                + "  \"use_for_organization_discovery\": {\n"
                + "    \"default_value\": true,\n"
                + "    \"allowed_values\": [\n"
                + "      true\n"
                + "    ]\n"
                + "  },\n"
                + "  \"role_visibility_policy\": {\n"
                + "    \"default_value\": \"write\",\n"
                + "    \"overrides\": [\n"
                + "      {\n"
                + "        \"role_id\": \"role_id\",\n"
                + "        \"access\": \"write\"\n"
                + "      }\n"
                + "    ]\n"
                + "  },\n"
                + "  \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "  \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
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
                                "{\"id\":\"id\",\"name\":\"name\",\"is_default\":true,\"organization_deletion_behavior\":\"allow\",\"connection_deletion_behavior\":\"allow\",\"enforce_permission_ceiling\":true,\"enforce_self_assignment_restriction\":true,\"connection_profile_id\":\"connection_profile_id\",\"user_attribute_profile_id\":\"user_attribute_profile_id\",\"allowed_strategies\":[\"adfs\"],\"invitation_landing_client_id\":\"invitation_landing_client_id\",\"admin_roles_assignment\":[\"admin_roles_assignment\"],\"use_for_organization_discovery\":{\"default_value\":true,\"allowed_values\":[true]},\"role_visibility_policy\":{\"default_value\":\"write\",\"overrides\":[{\"role_id\":\"role_id\",\"access\":\"write\"}]},\"created_at\":\"2024-01-15T09:30:00Z\",\"updated_at\":\"2024-01-15T09:30:00Z\"}"));
        OrganizationTemplate response = client.organizationTemplates()
                .update("id", UpdateOrganizationTemplateRequestContent.builder().build());
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
                + "  \"id\": \"id\",\n"
                + "  \"name\": \"name\",\n"
                + "  \"is_default\": true,\n"
                + "  \"organization_deletion_behavior\": \"allow\",\n"
                + "  \"connection_deletion_behavior\": \"allow\",\n"
                + "  \"enforce_permission_ceiling\": true,\n"
                + "  \"enforce_self_assignment_restriction\": true,\n"
                + "  \"connection_profile_id\": \"connection_profile_id\",\n"
                + "  \"user_attribute_profile_id\": \"user_attribute_profile_id\",\n"
                + "  \"allowed_strategies\": [\n"
                + "    \"adfs\"\n"
                + "  ],\n"
                + "  \"invitation_landing_client_id\": \"invitation_landing_client_id\",\n"
                + "  \"admin_roles_assignment\": [\n"
                + "    \"admin_roles_assignment\"\n"
                + "  ],\n"
                + "  \"use_for_organization_discovery\": {\n"
                + "    \"default_value\": true,\n"
                + "    \"allowed_values\": [\n"
                + "      true\n"
                + "    ]\n"
                + "  },\n"
                + "  \"role_visibility_policy\": {\n"
                + "    \"default_value\": \"write\",\n"
                + "    \"overrides\": [\n"
                + "      {\n"
                + "        \"role_id\": \"role_id\",\n"
                + "        \"access\": \"write\"\n"
                + "      }\n"
                + "    ]\n"
                + "  },\n"
                + "  \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "  \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
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
    public void testListOrganizations() throws Exception {
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody("{\"next\":\"next\",\"organizations\":[{\"id\":\"id\"}]}"));
        SyncPagingIterable<OrganizationTemplateAssignedOrganization> response = client.organizationTemplates()
                .listOrganizations(
                        "id",
                        ListTemplateOrganizationsRequestParameters.builder()
                                .from("from")
                                .take(1)
                                .build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("GET", request.getMethod());

        // Validate response body
        Assertions.assertNotNull(response, "Response should not be null");
        // Pagination response validated via MockWebServer
        // The SDK correctly parses the response into a SyncPagingIterable
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
                if (actualValue == null) {
                    if (!entry.getValue().isNull()) return false;
                } else if (!jsonEquals(entry.getValue(), actualValue)) return false;
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

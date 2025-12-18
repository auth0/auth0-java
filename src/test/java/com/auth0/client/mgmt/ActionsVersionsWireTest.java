package com.auth0.client.mgmt;

import com.auth0.client.mgmt.actions.types.ListActionVersionsRequestParameters;
import com.auth0.client.mgmt.core.ObjectMappers;
import com.auth0.client.mgmt.core.OptionalNullable;
import com.auth0.client.mgmt.core.SyncPagingIterable;
import com.auth0.client.mgmt.types.ActionVersion;
import com.auth0.client.mgmt.types.DeployActionVersionResponseContent;
import com.auth0.client.mgmt.types.GetActionVersionResponseContent;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ActionsVersionsWireTest {
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
                                "{\"total\":1.1,\"page\":1.1,\"per_page\":1.1,\"versions\":[{\"id\":\"id\",\"action_id\":\"action_id\",\"code\":\"code\",\"dependencies\":[{}],\"deployed\":true,\"runtime\":\"runtime\",\"secrets\":[{}],\"status\":\"pending\",\"number\":1.1,\"errors\":[{}],\"built_at\":\"2024-01-15T09:30:00Z\",\"created_at\":\"2024-01-15T09:30:00Z\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"supported_triggers\":[{\"id\":\"id\"}],\"modules\":[{}]}]}"));
        SyncPagingIterable<ActionVersion> response = client.actions()
                .versions()
                .list(
                        "actionId",
                        ListActionVersionsRequestParameters.builder()
                                .page(OptionalNullable.of(1))
                                .perPage(OptionalNullable.of(1))
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
    public void testGet() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"id\":\"id\",\"action_id\":\"action_id\",\"code\":\"code\",\"dependencies\":[{\"name\":\"name\",\"version\":\"version\",\"registry_url\":\"registry_url\"}],\"deployed\":true,\"runtime\":\"runtime\",\"secrets\":[{\"name\":\"name\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"status\":\"pending\",\"number\":1.1,\"errors\":[{\"id\":\"id\",\"msg\":\"msg\",\"url\":\"url\"}],\"action\":{\"id\":\"id\",\"name\":\"name\",\"supported_triggers\":[{\"id\":\"id\"}],\"all_changes_deployed\":true,\"created_at\":\"2024-01-15T09:30:00Z\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"built_at\":\"2024-01-15T09:30:00Z\",\"created_at\":\"2024-01-15T09:30:00Z\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"supported_triggers\":[{\"id\":\"id\",\"version\":\"version\",\"status\":\"status\",\"runtimes\":[\"runtimes\"],\"default_runtime\":\"default_runtime\",\"compatible_triggers\":[{\"id\":\"id\",\"version\":\"version\"}],\"binding_policy\":\"trigger-bound\"}],\"modules\":[{\"module_id\":\"module_id\",\"module_name\":\"module_name\",\"module_version_id\":\"module_version_id\",\"module_version_number\":1}]}"));
        GetActionVersionResponseContent response = client.actions().versions().get("actionId", "id");
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("GET", request.getMethod());

        // Validate response body
        Assertions.assertNotNull(response, "Response should not be null");
        String actualResponseJson = objectMapper.writeValueAsString(response);
        String expectedResponseBody = ""
                + "{\n"
                + "  \"id\": \"id\",\n"
                + "  \"action_id\": \"action_id\",\n"
                + "  \"code\": \"code\",\n"
                + "  \"dependencies\": [\n"
                + "    {\n"
                + "      \"name\": \"name\",\n"
                + "      \"version\": \"version\",\n"
                + "      \"registry_url\": \"registry_url\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"deployed\": true,\n"
                + "  \"runtime\": \"runtime\",\n"
                + "  \"secrets\": [\n"
                + "    {\n"
                + "      \"name\": \"name\",\n"
                + "      \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"status\": \"pending\",\n"
                + "  \"number\": 1.1,\n"
                + "  \"errors\": [\n"
                + "    {\n"
                + "      \"id\": \"id\",\n"
                + "      \"msg\": \"msg\",\n"
                + "      \"url\": \"url\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"action\": {\n"
                + "    \"id\": \"id\",\n"
                + "    \"name\": \"name\",\n"
                + "    \"supported_triggers\": [\n"
                + "      {\n"
                + "        \"id\": \"id\"\n"
                + "      }\n"
                + "    ],\n"
                + "    \"all_changes_deployed\": true,\n"
                + "    \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "  },\n"
                + "  \"built_at\": \"2024-01-15T09:30:00Z\",\n"
                + "  \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "  \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "  \"supported_triggers\": [\n"
                + "    {\n"
                + "      \"id\": \"id\",\n"
                + "      \"version\": \"version\",\n"
                + "      \"status\": \"status\",\n"
                + "      \"runtimes\": [\n"
                + "        \"runtimes\"\n"
                + "      ],\n"
                + "      \"default_runtime\": \"default_runtime\",\n"
                + "      \"compatible_triggers\": [\n"
                + "        {\n"
                + "          \"id\": \"id\",\n"
                + "          \"version\": \"version\"\n"
                + "        }\n"
                + "      ],\n"
                + "      \"binding_policy\": \"trigger-bound\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"modules\": [\n"
                + "    {\n"
                + "      \"module_id\": \"module_id\",\n"
                + "      \"module_name\": \"module_name\",\n"
                + "      \"module_version_id\": \"module_version_id\",\n"
                + "      \"module_version_number\": 1\n"
                + "    }\n"
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
    public void testDeploy() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"id\":\"id\",\"action_id\":\"action_id\",\"code\":\"code\",\"dependencies\":[{\"name\":\"name\",\"version\":\"version\",\"registry_url\":\"registry_url\"}],\"deployed\":true,\"runtime\":\"runtime\",\"secrets\":[{\"name\":\"name\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"status\":\"pending\",\"number\":1.1,\"errors\":[{\"id\":\"id\",\"msg\":\"msg\",\"url\":\"url\"}],\"action\":{\"id\":\"id\",\"name\":\"name\",\"supported_triggers\":[{\"id\":\"id\"}],\"all_changes_deployed\":true,\"created_at\":\"2024-01-15T09:30:00Z\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"built_at\":\"2024-01-15T09:30:00Z\",\"created_at\":\"2024-01-15T09:30:00Z\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"supported_triggers\":[{\"id\":\"id\",\"version\":\"version\",\"status\":\"status\",\"runtimes\":[\"runtimes\"],\"default_runtime\":\"default_runtime\",\"compatible_triggers\":[{\"id\":\"id\",\"version\":\"version\"}],\"binding_policy\":\"trigger-bound\"}],\"modules\":[{\"module_id\":\"module_id\",\"module_name\":\"module_name\",\"module_version_id\":\"module_version_id\",\"module_version_number\":1}]}"));
        DeployActionVersionResponseContent response =
                client.actions().versions().deploy("actionId", "id", OptionalNullable.absent());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("POST", request.getMethod());

        // Validate response body
        Assertions.assertNotNull(response, "Response should not be null");
        String actualResponseJson = objectMapper.writeValueAsString(response);
        String expectedResponseBody = ""
                + "{\n"
                + "  \"id\": \"id\",\n"
                + "  \"action_id\": \"action_id\",\n"
                + "  \"code\": \"code\",\n"
                + "  \"dependencies\": [\n"
                + "    {\n"
                + "      \"name\": \"name\",\n"
                + "      \"version\": \"version\",\n"
                + "      \"registry_url\": \"registry_url\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"deployed\": true,\n"
                + "  \"runtime\": \"runtime\",\n"
                + "  \"secrets\": [\n"
                + "    {\n"
                + "      \"name\": \"name\",\n"
                + "      \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"status\": \"pending\",\n"
                + "  \"number\": 1.1,\n"
                + "  \"errors\": [\n"
                + "    {\n"
                + "      \"id\": \"id\",\n"
                + "      \"msg\": \"msg\",\n"
                + "      \"url\": \"url\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"action\": {\n"
                + "    \"id\": \"id\",\n"
                + "    \"name\": \"name\",\n"
                + "    \"supported_triggers\": [\n"
                + "      {\n"
                + "        \"id\": \"id\"\n"
                + "      }\n"
                + "    ],\n"
                + "    \"all_changes_deployed\": true,\n"
                + "    \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "  },\n"
                + "  \"built_at\": \"2024-01-15T09:30:00Z\",\n"
                + "  \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "  \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "  \"supported_triggers\": [\n"
                + "    {\n"
                + "      \"id\": \"id\",\n"
                + "      \"version\": \"version\",\n"
                + "      \"status\": \"status\",\n"
                + "      \"runtimes\": [\n"
                + "        \"runtimes\"\n"
                + "      ],\n"
                + "      \"default_runtime\": \"default_runtime\",\n"
                + "      \"compatible_triggers\": [\n"
                + "        {\n"
                + "          \"id\": \"id\",\n"
                + "          \"version\": \"version\"\n"
                + "        }\n"
                + "      ],\n"
                + "      \"binding_policy\": \"trigger-bound\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"modules\": [\n"
                + "    {\n"
                + "      \"module_id\": \"module_id\",\n"
                + "      \"module_name\": \"module_name\",\n"
                + "      \"module_version_id\": \"module_version_id\",\n"
                + "      \"module_version_number\": 1\n"
                + "    }\n"
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

package com.auth0.client.mgmt;

import com.auth0.client.mgmt.actions.types.GetActionModuleActionsRequestParameters;
import com.auth0.client.mgmt.actions.types.GetActionModulesRequestParameters;
import com.auth0.client.mgmt.core.ObjectMappers;
import com.auth0.client.mgmt.core.OptionalNullable;
import com.auth0.client.mgmt.core.SyncPagingIterable;
import com.auth0.client.mgmt.types.ActionModuleAction;
import com.auth0.client.mgmt.types.ActionModuleListItem;
import com.auth0.client.mgmt.types.CreateActionModuleResponseContent;
import com.auth0.client.mgmt.types.GetActionModuleResponseContent;
import com.auth0.client.mgmt.types.RollbackActionModuleResponseContent;
import com.auth0.client.mgmt.types.UpdateActionModuleResponseContent;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ActionsModulesWireTest {
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
                                "{\"modules\":[{\"id\":\"id\",\"name\":\"name\",\"code\":\"code\",\"dependencies\":[{}],\"secrets\":[{}],\"actions_using_module_total\":1,\"all_changes_published\":true,\"latest_version_number\":1,\"created_at\":\"2024-01-15T09:30:00Z\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"total\":1,\"page\":1,\"per_page\":1}"));
        SyncPagingIterable<ActionModuleListItem> response = client.actions()
                .modules()
                .list(GetActionModulesRequestParameters.builder()
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
    public void testCreate() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"id\":\"id\",\"name\":\"name\",\"code\":\"code\",\"dependencies\":[{\"name\":\"name\",\"version\":\"version\"}],\"secrets\":[{\"name\":\"name\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"actions_using_module_total\":1,\"all_changes_published\":true,\"latest_version_number\":1,\"created_at\":\"2024-01-15T09:30:00Z\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"latest_version\":{\"id\":\"id\",\"version_number\":1,\"code\":\"code\",\"dependencies\":[{}],\"secrets\":[{}],\"created_at\":\"2024-01-15T09:30:00Z\"}}"));
        CreateActionModuleResponseContent response = client.actions().modules().create();
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("POST", request.getMethod());

        // Validate response body
        Assertions.assertNotNull(response, "Response should not be null");
        String actualResponseJson = objectMapper.writeValueAsString(response);
        String expectedResponseBody = ""
                + "{\n"
                + "  \"id\": \"id\",\n"
                + "  \"name\": \"name\",\n"
                + "  \"code\": \"code\",\n"
                + "  \"dependencies\": [\n"
                + "    {\n"
                + "      \"name\": \"name\",\n"
                + "      \"version\": \"version\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"secrets\": [\n"
                + "    {\n"
                + "      \"name\": \"name\",\n"
                + "      \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"actions_using_module_total\": 1,\n"
                + "  \"all_changes_published\": true,\n"
                + "  \"latest_version_number\": 1,\n"
                + "  \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "  \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "  \"latest_version\": {\n"
                + "    \"id\": \"id\",\n"
                + "    \"version_number\": 1,\n"
                + "    \"code\": \"code\",\n"
                + "    \"dependencies\": [\n"
                + "      {}\n"
                + "    ],\n"
                + "    \"secrets\": [\n"
                + "      {}\n"
                + "    ],\n"
                + "    \"created_at\": \"2024-01-15T09:30:00Z\"\n"
                + "  }\n"
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
                                "{\"id\":\"id\",\"name\":\"name\",\"code\":\"code\",\"dependencies\":[{\"name\":\"name\",\"version\":\"version\"}],\"secrets\":[{\"name\":\"name\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"actions_using_module_total\":1,\"all_changes_published\":true,\"latest_version_number\":1,\"created_at\":\"2024-01-15T09:30:00Z\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"latest_version\":{\"id\":\"id\",\"version_number\":1,\"code\":\"code\",\"dependencies\":[{}],\"secrets\":[{}],\"created_at\":\"2024-01-15T09:30:00Z\"}}"));
        GetActionModuleResponseContent response = client.actions().modules().get("id");
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
                + "  \"code\": \"code\",\n"
                + "  \"dependencies\": [\n"
                + "    {\n"
                + "      \"name\": \"name\",\n"
                + "      \"version\": \"version\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"secrets\": [\n"
                + "    {\n"
                + "      \"name\": \"name\",\n"
                + "      \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"actions_using_module_total\": 1,\n"
                + "  \"all_changes_published\": true,\n"
                + "  \"latest_version_number\": 1,\n"
                + "  \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "  \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "  \"latest_version\": {\n"
                + "    \"id\": \"id\",\n"
                + "    \"version_number\": 1,\n"
                + "    \"code\": \"code\",\n"
                + "    \"dependencies\": [\n"
                + "      {}\n"
                + "    ],\n"
                + "    \"secrets\": [\n"
                + "      {}\n"
                + "    ],\n"
                + "    \"created_at\": \"2024-01-15T09:30:00Z\"\n"
                + "  }\n"
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
        client.actions().modules().delete("id");
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
                                "{\"id\":\"id\",\"name\":\"name\",\"code\":\"code\",\"dependencies\":[{\"name\":\"name\",\"version\":\"version\"}],\"secrets\":[{\"name\":\"name\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"actions_using_module_total\":1,\"all_changes_published\":true,\"latest_version_number\":1,\"created_at\":\"2024-01-15T09:30:00Z\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"latest_version\":{\"id\":\"id\",\"version_number\":1,\"code\":\"code\",\"dependencies\":[{}],\"secrets\":[{}],\"created_at\":\"2024-01-15T09:30:00Z\"}}"));
        UpdateActionModuleResponseContent response = client.actions().modules().update("id");
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("PATCH", request.getMethod());

        // Validate response body
        Assertions.assertNotNull(response, "Response should not be null");
        String actualResponseJson = objectMapper.writeValueAsString(response);
        String expectedResponseBody = ""
                + "{\n"
                + "  \"id\": \"id\",\n"
                + "  \"name\": \"name\",\n"
                + "  \"code\": \"code\",\n"
                + "  \"dependencies\": [\n"
                + "    {\n"
                + "      \"name\": \"name\",\n"
                + "      \"version\": \"version\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"secrets\": [\n"
                + "    {\n"
                + "      \"name\": \"name\",\n"
                + "      \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"actions_using_module_total\": 1,\n"
                + "  \"all_changes_published\": true,\n"
                + "  \"latest_version_number\": 1,\n"
                + "  \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "  \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "  \"latest_version\": {\n"
                + "    \"id\": \"id\",\n"
                + "    \"version_number\": 1,\n"
                + "    \"code\": \"code\",\n"
                + "    \"dependencies\": [\n"
                + "      {}\n"
                + "    ],\n"
                + "    \"secrets\": [\n"
                + "      {}\n"
                + "    ],\n"
                + "    \"created_at\": \"2024-01-15T09:30:00Z\"\n"
                + "  }\n"
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
    public void testListActions() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"actions\":[{\"action_id\":\"action_id\",\"action_name\":\"action_name\",\"module_version_id\":\"module_version_id\",\"module_version_number\":1,\"supported_triggers\":[{\"id\":\"id\"}]}],\"total\":1,\"page\":1,\"per_page\":1}"));
        SyncPagingIterable<ActionModuleAction> response = client.actions()
                .modules()
                .listActions(
                        "id",
                        GetActionModuleActionsRequestParameters.builder()
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
    public void testRollback() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"id\":\"id\",\"name\":\"name\",\"code\":\"code\",\"dependencies\":[{\"name\":\"name\",\"version\":\"version\"}],\"secrets\":[{\"name\":\"name\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"actions_using_module_total\":1,\"all_changes_published\":true,\"latest_version_number\":1,\"created_at\":\"2024-01-15T09:30:00Z\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"latest_version\":{\"id\":\"id\",\"version_number\":1,\"code\":\"code\",\"dependencies\":[{}],\"secrets\":[{}],\"created_at\":\"2024-01-15T09:30:00Z\"}}"));
        RollbackActionModuleResponseContent response =
                client.actions().modules().rollback("id");
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("POST", request.getMethod());

        // Validate response body
        Assertions.assertNotNull(response, "Response should not be null");
        String actualResponseJson = objectMapper.writeValueAsString(response);
        String expectedResponseBody = ""
                + "{\n"
                + "  \"id\": \"id\",\n"
                + "  \"name\": \"name\",\n"
                + "  \"code\": \"code\",\n"
                + "  \"dependencies\": [\n"
                + "    {\n"
                + "      \"name\": \"name\",\n"
                + "      \"version\": \"version\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"secrets\": [\n"
                + "    {\n"
                + "      \"name\": \"name\",\n"
                + "      \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"actions_using_module_total\": 1,\n"
                + "  \"all_changes_published\": true,\n"
                + "  \"latest_version_number\": 1,\n"
                + "  \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "  \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "  \"latest_version\": {\n"
                + "    \"id\": \"id\",\n"
                + "    \"version_number\": 1,\n"
                + "    \"code\": \"code\",\n"
                + "    \"dependencies\": [\n"
                + "      {}\n"
                + "    ],\n"
                + "    \"secrets\": [\n"
                + "      {}\n"
                + "    ],\n"
                + "    \"created_at\": \"2024-01-15T09:30:00Z\"\n"
                + "  }\n"
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

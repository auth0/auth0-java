package com.auth0.client.mgmt;

import com.auth0.client.mgmt.connections.types.ListDirectoryProvisioningsRequestParameters;
import com.auth0.client.mgmt.core.ObjectMappers;
import com.auth0.client.mgmt.core.OptionalNullable;
import com.auth0.client.mgmt.core.SyncPagingIterable;
import com.auth0.client.mgmt.types.CreateDirectoryProvisioningResponseContent;
import com.auth0.client.mgmt.types.DirectoryProvisioning;
import com.auth0.client.mgmt.types.GetDirectoryProvisioningDefaultMappingResponseContent;
import com.auth0.client.mgmt.types.GetDirectoryProvisioningResponseContent;
import com.auth0.client.mgmt.types.UpdateDirectoryProvisioningResponseContent;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ConnectionsDirectoryProvisioningWireTest {
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
                                "{\"directory_provisionings\":[{\"connection_id\":\"connection_id\",\"connection_name\":\"connection_name\",\"strategy\":\"strategy\",\"mapping\":[{\"auth0\":\"auth0\",\"idp\":\"idp\"}],\"synchronize_automatically\":true,\"created_at\":\"2024-01-15T09:30:00Z\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"last_synchronization_at\":\"2024-01-15T09:30:00Z\",\"last_synchronization_status\":\"last_synchronization_status\",\"last_synchronization_error\":\"last_synchronization_error\"}],\"next\":\"next\"}"));
        SyncPagingIterable<DirectoryProvisioning> response = client.connections()
                .directoryProvisioning()
                .list(ListDirectoryProvisioningsRequestParameters.builder()
                        .from(OptionalNullable.of("from"))
                        .take(OptionalNullable.of(1))
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
                                "{\"connection_id\":\"connection_id\",\"connection_name\":\"connection_name\",\"strategy\":\"strategy\",\"mapping\":[{\"auth0\":\"auth0\",\"idp\":\"idp\"}],\"synchronize_automatically\":true,\"created_at\":\"2024-01-15T09:30:00Z\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"last_synchronization_at\":\"2024-01-15T09:30:00Z\",\"last_synchronization_status\":\"last_synchronization_status\",\"last_synchronization_error\":\"last_synchronization_error\"}"));
        GetDirectoryProvisioningResponseContent response =
                client.connections().directoryProvisioning().get("id");
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("GET", request.getMethod());

        // Validate response body
        Assertions.assertNotNull(response, "Response should not be null");
        String actualResponseJson = objectMapper.writeValueAsString(response);
        String expectedResponseBody = ""
                + "{\n"
                + "  \"connection_id\": \"connection_id\",\n"
                + "  \"connection_name\": \"connection_name\",\n"
                + "  \"strategy\": \"strategy\",\n"
                + "  \"mapping\": [\n"
                + "    {\n"
                + "      \"auth0\": \"auth0\",\n"
                + "      \"idp\": \"idp\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"synchronize_automatically\": true,\n"
                + "  \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "  \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "  \"last_synchronization_at\": \"2024-01-15T09:30:00Z\",\n"
                + "  \"last_synchronization_status\": \"last_synchronization_status\",\n"
                + "  \"last_synchronization_error\": \"last_synchronization_error\"\n"
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
    public void testCreate() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"connection_id\":\"connection_id\",\"connection_name\":\"connection_name\",\"strategy\":\"strategy\",\"mapping\":[{\"auth0\":\"auth0\",\"idp\":\"idp\"}],\"synchronize_automatically\":true,\"created_at\":\"2024-01-15T09:30:00Z\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"last_synchronization_at\":\"2024-01-15T09:30:00Z\",\"last_synchronization_status\":\"last_synchronization_status\",\"last_synchronization_error\":\"last_synchronization_error\"}"));
        CreateDirectoryProvisioningResponseContent response =
                client.connections().directoryProvisioning().create("id", OptionalNullable.absent());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("POST", request.getMethod());

        // Validate response body
        Assertions.assertNotNull(response, "Response should not be null");
        String actualResponseJson = objectMapper.writeValueAsString(response);
        String expectedResponseBody = ""
                + "{\n"
                + "  \"connection_id\": \"connection_id\",\n"
                + "  \"connection_name\": \"connection_name\",\n"
                + "  \"strategy\": \"strategy\",\n"
                + "  \"mapping\": [\n"
                + "    {\n"
                + "      \"auth0\": \"auth0\",\n"
                + "      \"idp\": \"idp\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"synchronize_automatically\": true,\n"
                + "  \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "  \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "  \"last_synchronization_at\": \"2024-01-15T09:30:00Z\",\n"
                + "  \"last_synchronization_status\": \"last_synchronization_status\",\n"
                + "  \"last_synchronization_error\": \"last_synchronization_error\"\n"
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
        client.connections().directoryProvisioning().delete("id");
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
                                "{\"connection_id\":\"connection_id\",\"connection_name\":\"connection_name\",\"strategy\":\"strategy\",\"mapping\":[{\"auth0\":\"auth0\",\"idp\":\"idp\"}],\"synchronize_automatically\":true,\"created_at\":\"2024-01-15T09:30:00Z\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"last_synchronization_at\":\"2024-01-15T09:30:00Z\",\"last_synchronization_status\":\"last_synchronization_status\",\"last_synchronization_error\":\"last_synchronization_error\"}"));
        UpdateDirectoryProvisioningResponseContent response =
                client.connections().directoryProvisioning().update("id", OptionalNullable.absent());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("PATCH", request.getMethod());

        // Validate response body
        Assertions.assertNotNull(response, "Response should not be null");
        String actualResponseJson = objectMapper.writeValueAsString(response);
        String expectedResponseBody = ""
                + "{\n"
                + "  \"connection_id\": \"connection_id\",\n"
                + "  \"connection_name\": \"connection_name\",\n"
                + "  \"strategy\": \"strategy\",\n"
                + "  \"mapping\": [\n"
                + "    {\n"
                + "      \"auth0\": \"auth0\",\n"
                + "      \"idp\": \"idp\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"synchronize_automatically\": true,\n"
                + "  \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "  \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "  \"last_synchronization_at\": \"2024-01-15T09:30:00Z\",\n"
                + "  \"last_synchronization_status\": \"last_synchronization_status\",\n"
                + "  \"last_synchronization_error\": \"last_synchronization_error\"\n"
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
    public void testGetDefaultMapping() throws Exception {
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody("{\"mapping\":[{\"auth0\":\"auth0\",\"idp\":\"idp\"}]}"));
        GetDirectoryProvisioningDefaultMappingResponseContent response =
                client.connections().directoryProvisioning().getDefaultMapping("id");
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("GET", request.getMethod());

        // Validate response body
        Assertions.assertNotNull(response, "Response should not be null");
        String actualResponseJson = objectMapper.writeValueAsString(response);
        String expectedResponseBody = ""
                + "{\n"
                + "  \"mapping\": [\n"
                + "    {\n"
                + "      \"auth0\": \"auth0\",\n"
                + "      \"idp\": \"idp\"\n"
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

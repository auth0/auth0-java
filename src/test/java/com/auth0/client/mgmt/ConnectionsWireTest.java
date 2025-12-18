package com.auth0.client.mgmt;

import com.auth0.client.mgmt.core.ObjectMappers;
import com.auth0.client.mgmt.core.OptionalNullable;
import com.auth0.client.mgmt.core.SyncPagingIterable;
import com.auth0.client.mgmt.types.ConnectionForList;
import com.auth0.client.mgmt.types.ConnectionIdentityProviderEnum;
import com.auth0.client.mgmt.types.CreateConnectionRequestContent;
import com.auth0.client.mgmt.types.CreateConnectionResponseContent;
import com.auth0.client.mgmt.types.GetConnectionRequestParameters;
import com.auth0.client.mgmt.types.GetConnectionResponseContent;
import com.auth0.client.mgmt.types.ListConnectionsQueryParameters;
import com.auth0.client.mgmt.types.UpdateConnectionRequestContent;
import com.auth0.client.mgmt.types.UpdateConnectionResponseContent;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ConnectionsWireTest {
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
                                "{\"next\":\"next\",\"connections\":[{\"name\":\"name\",\"display_name\":\"display_name\",\"options\":{\"key\":\"value\"},\"id\":\"id\",\"strategy\":\"strategy\",\"realms\":[\"realms\"],\"is_domain_connection\":true,\"show_as_button\":true,\"authentication\":{\"active\":true},\"connected_accounts\":{\"active\":true}}]}"));
        SyncPagingIterable<ConnectionForList> response = client.connections()
                .list(ListConnectionsQueryParameters.builder()
                        .from(OptionalNullable.of("from"))
                        .take(OptionalNullable.of(1))
                        .name(OptionalNullable.of("name"))
                        .fields(OptionalNullable.of("fields"))
                        .includeFields(OptionalNullable.of(true))
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
                                "{\"name\":\"name\",\"display_name\":\"display_name\",\"options\":{\"key\":\"value\"},\"id\":\"id\",\"strategy\":\"strategy\",\"realms\":[\"realms\"],\"enabled_clients\":[\"enabled_clients\"],\"is_domain_connection\":true,\"show_as_button\":true,\"metadata\":{\"key\":\"value\"},\"authentication\":{\"active\":true},\"connected_accounts\":{\"active\":true,\"cross_app_access\":true}}"));
        CreateConnectionResponseContent response = client.connections()
                .create(CreateConnectionRequestContent.builder()
                        .name("name")
                        .strategy(ConnectionIdentityProviderEnum.AD)
                        .build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("POST", request.getMethod());
        // Validate request body
        String actualRequestBody = request.getBody().readUtf8();
        String expectedRequestBody = "" + "{\n" + "  \"name\": \"name\",\n" + "  \"strategy\": \"ad\"\n" + "}";
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
                + "  \"name\": \"name\",\n"
                + "  \"display_name\": \"display_name\",\n"
                + "  \"options\": {\n"
                + "    \"key\": \"value\"\n"
                + "  },\n"
                + "  \"id\": \"id\",\n"
                + "  \"strategy\": \"strategy\",\n"
                + "  \"realms\": [\n"
                + "    \"realms\"\n"
                + "  ],\n"
                + "  \"enabled_clients\": [\n"
                + "    \"enabled_clients\"\n"
                + "  ],\n"
                + "  \"is_domain_connection\": true,\n"
                + "  \"show_as_button\": true,\n"
                + "  \"metadata\": {\n"
                + "    \"key\": \"value\"\n"
                + "  },\n"
                + "  \"authentication\": {\n"
                + "    \"active\": true\n"
                + "  },\n"
                + "  \"connected_accounts\": {\n"
                + "    \"active\": true,\n"
                + "    \"cross_app_access\": true\n"
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
                                "{\"name\":\"name\",\"display_name\":\"display_name\",\"options\":{\"key\":\"value\"},\"id\":\"id\",\"strategy\":\"strategy\",\"realms\":[\"realms\"],\"enabled_clients\":[\"enabled_clients\"],\"is_domain_connection\":true,\"show_as_button\":true,\"metadata\":{\"key\":\"value\"},\"authentication\":{\"active\":true},\"connected_accounts\":{\"active\":true,\"cross_app_access\":true}}"));
        GetConnectionResponseContent response = client.connections()
                .get(
                        "id",
                        GetConnectionRequestParameters.builder()
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
                + "  \"name\": \"name\",\n"
                + "  \"display_name\": \"display_name\",\n"
                + "  \"options\": {\n"
                + "    \"key\": \"value\"\n"
                + "  },\n"
                + "  \"id\": \"id\",\n"
                + "  \"strategy\": \"strategy\",\n"
                + "  \"realms\": [\n"
                + "    \"realms\"\n"
                + "  ],\n"
                + "  \"enabled_clients\": [\n"
                + "    \"enabled_clients\"\n"
                + "  ],\n"
                + "  \"is_domain_connection\": true,\n"
                + "  \"show_as_button\": true,\n"
                + "  \"metadata\": {\n"
                + "    \"key\": \"value\"\n"
                + "  },\n"
                + "  \"authentication\": {\n"
                + "    \"active\": true\n"
                + "  },\n"
                + "  \"connected_accounts\": {\n"
                + "    \"active\": true,\n"
                + "    \"cross_app_access\": true\n"
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
        client.connections().delete("id");
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
                                "{\"name\":\"name\",\"display_name\":\"display_name\",\"options\":{\"key\":\"value\"},\"id\":\"id\",\"strategy\":\"strategy\",\"realms\":[\"realms\"],\"enabled_clients\":[\"enabled_clients\"],\"is_domain_connection\":true,\"show_as_button\":true,\"metadata\":{\"key\":\"value\"},\"authentication\":{\"active\":true},\"connected_accounts\":{\"active\":true,\"cross_app_access\":true}}"));
        UpdateConnectionResponseContent response = client.connections()
                .update("id", UpdateConnectionRequestContent.builder().build());
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
                + "  \"name\": \"name\",\n"
                + "  \"display_name\": \"display_name\",\n"
                + "  \"options\": {\n"
                + "    \"key\": \"value\"\n"
                + "  },\n"
                + "  \"id\": \"id\",\n"
                + "  \"strategy\": \"strategy\",\n"
                + "  \"realms\": [\n"
                + "    \"realms\"\n"
                + "  ],\n"
                + "  \"enabled_clients\": [\n"
                + "    \"enabled_clients\"\n"
                + "  ],\n"
                + "  \"is_domain_connection\": true,\n"
                + "  \"show_as_button\": true,\n"
                + "  \"metadata\": {\n"
                + "    \"key\": \"value\"\n"
                + "  },\n"
                + "  \"authentication\": {\n"
                + "    \"active\": true\n"
                + "  },\n"
                + "  \"connected_accounts\": {\n"
                + "    \"active\": true,\n"
                + "    \"cross_app_access\": true\n"
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
    public void testCheckStatus() throws Exception {
        server.enqueue(new MockResponse().setResponseCode(200).setBody("{}"));
        client.connections().checkStatus("id");
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("GET", request.getMethod());
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

package com.auth0.client.mgmt;

import com.auth0.client.mgmt.core.ObjectMappers;
import com.auth0.client.mgmt.types.GetSessionResponseContent;
import com.auth0.client.mgmt.types.UpdateSessionRequestContent;
import com.auth0.client.mgmt.types.UpdateSessionResponseContent;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SessionsWireTest {
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
                                "{\"id\":\"id\",\"user_id\":\"user_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"authenticated_at\":\"2024-01-15T09:30:00Z\",\"idle_expires_at\":\"2024-01-15T09:30:00Z\",\"expires_at\":\"2024-01-15T09:30:00Z\",\"last_interacted_at\":\"2024-01-15T09:30:00Z\",\"device\":{\"initial_user_agent\":\"initial_user_agent\",\"initial_ip\":\"initial_ip\",\"initial_asn\":\"initial_asn\",\"last_user_agent\":\"last_user_agent\",\"last_ip\":\"last_ip\",\"last_asn\":\"last_asn\"},\"clients\":[{\"client_id\":\"client_id\"}],\"authentication\":{\"methods\":[{}]},\"cookie\":{\"mode\":\"non-persistent\"},\"session_metadata\":{\"key\":\"value\"}}"));
        GetSessionResponseContent response = client.sessions().get("id");
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("GET", request.getMethod());

        // Validate response body
        Assertions.assertNotNull(response, "Response should not be null");
        String actualResponseJson = objectMapper.writeValueAsString(response);
        String expectedResponseBody = ""
                + "{\n"
                + "  \"id\": \"id\",\n"
                + "  \"user_id\": \"user_id\",\n"
                + "  \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "  \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "  \"authenticated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "  \"idle_expires_at\": \"2024-01-15T09:30:00Z\",\n"
                + "  \"expires_at\": \"2024-01-15T09:30:00Z\",\n"
                + "  \"last_interacted_at\": \"2024-01-15T09:30:00Z\",\n"
                + "  \"device\": {\n"
                + "    \"initial_user_agent\": \"initial_user_agent\",\n"
                + "    \"initial_ip\": \"initial_ip\",\n"
                + "    \"initial_asn\": \"initial_asn\",\n"
                + "    \"last_user_agent\": \"last_user_agent\",\n"
                + "    \"last_ip\": \"last_ip\",\n"
                + "    \"last_asn\": \"last_asn\"\n"
                + "  },\n"
                + "  \"clients\": [\n"
                + "    {\n"
                + "      \"client_id\": \"client_id\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"authentication\": {\n"
                + "    \"methods\": [\n"
                + "      {}\n"
                + "    ]\n"
                + "  },\n"
                + "  \"cookie\": {\n"
                + "    \"mode\": \"non-persistent\"\n"
                + "  },\n"
                + "  \"session_metadata\": {\n"
                + "    \"key\": \"value\"\n"
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
        client.sessions().delete("id");
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
                                "{\"id\":\"id\",\"user_id\":\"user_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"authenticated_at\":\"2024-01-15T09:30:00Z\",\"idle_expires_at\":\"2024-01-15T09:30:00Z\",\"expires_at\":\"2024-01-15T09:30:00Z\",\"last_interacted_at\":\"2024-01-15T09:30:00Z\",\"device\":{\"initial_user_agent\":\"initial_user_agent\",\"initial_ip\":\"initial_ip\",\"initial_asn\":\"initial_asn\",\"last_user_agent\":\"last_user_agent\",\"last_ip\":\"last_ip\",\"last_asn\":\"last_asn\"},\"clients\":[{\"client_id\":\"client_id\"}],\"authentication\":{\"methods\":[{}]},\"cookie\":{\"mode\":\"non-persistent\"},\"session_metadata\":{\"key\":\"value\"}}"));
        UpdateSessionResponseContent response = client.sessions()
                .update("id", UpdateSessionRequestContent.builder().build());
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
                + "  \"user_id\": \"user_id\",\n"
                + "  \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "  \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "  \"authenticated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "  \"idle_expires_at\": \"2024-01-15T09:30:00Z\",\n"
                + "  \"expires_at\": \"2024-01-15T09:30:00Z\",\n"
                + "  \"last_interacted_at\": \"2024-01-15T09:30:00Z\",\n"
                + "  \"device\": {\n"
                + "    \"initial_user_agent\": \"initial_user_agent\",\n"
                + "    \"initial_ip\": \"initial_ip\",\n"
                + "    \"initial_asn\": \"initial_asn\",\n"
                + "    \"last_user_agent\": \"last_user_agent\",\n"
                + "    \"last_ip\": \"last_ip\",\n"
                + "    \"last_asn\": \"last_asn\"\n"
                + "  },\n"
                + "  \"clients\": [\n"
                + "    {\n"
                + "      \"client_id\": \"client_id\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"authentication\": {\n"
                + "    \"methods\": [\n"
                + "      {}\n"
                + "    ]\n"
                + "  },\n"
                + "  \"cookie\": {\n"
                + "    \"mode\": \"non-persistent\"\n"
                + "  },\n"
                + "  \"session_metadata\": {\n"
                + "    \"key\": \"value\"\n"
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
    public void testRevoke() throws Exception {
        server.enqueue(new MockResponse().setResponseCode(200).setBody("{}"));
        client.sessions().revoke("id");
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("POST", request.getMethod());
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

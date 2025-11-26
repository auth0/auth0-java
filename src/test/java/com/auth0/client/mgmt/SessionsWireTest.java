package com.auth0.client.mgmt;

import com.auth0.client.mgmt.core.ObjectMappers;
import com.auth0.client.mgmt.types.GetSessionResponseContent;
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
                                "{\"id\":\"id\",\"user_id\":\"user_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"authenticated_at\":\"2024-01-15T09:30:00Z\",\"idle_expires_at\":\"2024-01-15T09:30:00Z\",\"expires_at\":\"2024-01-15T09:30:00Z\",\"last_interacted_at\":\"2024-01-15T09:30:00Z\",\"device\":{\"initial_user_agent\":\"initial_user_agent\",\"initial_ip\":\"initial_ip\",\"initial_asn\":\"initial_asn\",\"last_user_agent\":\"last_user_agent\",\"last_ip\":\"last_ip\",\"last_asn\":\"last_asn\"},\"clients\":[{\"client_id\":\"client_id\"}],\"authentication\":{\"methods\":[{}]},\"cookie\":{\"mode\":\"non-persistent\"}}"));
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
    public void testRevoke() throws Exception {
        server.enqueue(new MockResponse().setResponseCode(200).setBody("{}"));
        client.sessions().revoke("id");
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("POST", request.getMethod());
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

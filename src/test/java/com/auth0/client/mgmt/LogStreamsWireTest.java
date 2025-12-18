package com.auth0.client.mgmt;

import com.auth0.client.mgmt.core.ObjectMappers;
import com.auth0.client.mgmt.types.CreateLogStreamHttpRequestBody;
import com.auth0.client.mgmt.types.CreateLogStreamRequestContent;
import com.auth0.client.mgmt.types.CreateLogStreamResponseContent;
import com.auth0.client.mgmt.types.GetLogStreamResponseContent;
import com.auth0.client.mgmt.types.LogStreamHttpSink;
import com.auth0.client.mgmt.types.LogStreamResponseSchema;
import com.auth0.client.mgmt.types.UpdateLogStreamRequestContent;
import com.auth0.client.mgmt.types.UpdateLogStreamResponseContent;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LogStreamsWireTest {
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
                                "[{\"id\":\"id\",\"name\":\"name\",\"status\":\"active\",\"type\":\"http\",\"isPriority\":true,\"filters\":[{}],\"pii_config\":{\"log_fields\":[\"first_name\"],\"method\":\"mask\",\"algorithm\":\"xxhash\"},\"sink\":{\"httpAuthorization\":\"httpAuthorization\",\"httpContentFormat\":\"JSONARRAY\",\"httpContentType\":\"httpContentType\",\"httpEndpoint\":\"httpEndpoint\",\"httpCustomHeaders\":[{}]}}]"));
        List<LogStreamResponseSchema> response = client.logStreams().list();
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("GET", request.getMethod());

        // Validate response body
        Assertions.assertNotNull(response, "Response should not be null");
        String actualResponseJson = objectMapper.writeValueAsString(response);
        String expectedResponseBody = ""
                + "[\n"
                + "  {\n"
                + "    \"id\": \"id\",\n"
                + "    \"name\": \"name\",\n"
                + "    \"status\": \"active\",\n"
                + "    \"type\": \"http\",\n"
                + "    \"isPriority\": true,\n"
                + "    \"filters\": [\n"
                + "      {}\n"
                + "    ],\n"
                + "    \"pii_config\": {\n"
                + "      \"log_fields\": [\n"
                + "        \"first_name\"\n"
                + "      ],\n"
                + "      \"method\": \"mask\",\n"
                + "      \"algorithm\": \"xxhash\"\n"
                + "    },\n"
                + "    \"sink\": {\n"
                + "      \"httpAuthorization\": \"httpAuthorization\",\n"
                + "      \"httpContentFormat\": \"JSONARRAY\",\n"
                + "      \"httpContentType\": \"httpContentType\",\n"
                + "      \"httpEndpoint\": \"httpEndpoint\",\n"
                + "      \"httpCustomHeaders\": [\n"
                + "        {}\n"
                + "      ]\n"
                + "    }\n"
                + "  }\n"
                + "]";
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
                                "{\"id\":\"id\",\"name\":\"name\",\"status\":\"active\",\"type\":\"http\",\"isPriority\":true,\"filters\":[{\"type\":\"category\",\"name\":\"auth.login.fail\"}],\"pii_config\":{\"log_fields\":[\"first_name\"],\"method\":\"mask\",\"algorithm\":\"xxhash\"},\"sink\":{\"httpAuthorization\":\"httpAuthorization\",\"httpContentFormat\":\"JSONARRAY\",\"httpContentType\":\"httpContentType\",\"httpEndpoint\":\"httpEndpoint\",\"httpCustomHeaders\":[{}]}}"));
        CreateLogStreamResponseContent response = client.logStreams()
                .create(CreateLogStreamRequestContent.of(CreateLogStreamHttpRequestBody.builder()
                        .type("http")
                        .sink(LogStreamHttpSink.builder()
                                .httpEndpoint("httpEndpoint")
                                .build())
                        .build()));
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("POST", request.getMethod());
        // Validate request body
        String actualRequestBody = request.getBody().readUtf8();
        String expectedRequestBody = ""
                + "{\n"
                + "  \"type\": \"http\",\n"
                + "  \"sink\": {\n"
                + "    \"httpEndpoint\": \"httpEndpoint\"\n"
                + "  }\n"
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
                + "  \"status\": \"active\",\n"
                + "  \"type\": \"http\",\n"
                + "  \"isPriority\": true,\n"
                + "  \"filters\": [\n"
                + "    {\n"
                + "      \"type\": \"category\",\n"
                + "      \"name\": \"auth.login.fail\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"pii_config\": {\n"
                + "    \"log_fields\": [\n"
                + "      \"first_name\"\n"
                + "    ],\n"
                + "    \"method\": \"mask\",\n"
                + "    \"algorithm\": \"xxhash\"\n"
                + "  },\n"
                + "  \"sink\": {\n"
                + "    \"httpAuthorization\": \"httpAuthorization\",\n"
                + "    \"httpContentFormat\": \"JSONARRAY\",\n"
                + "    \"httpContentType\": \"httpContentType\",\n"
                + "    \"httpEndpoint\": \"httpEndpoint\",\n"
                + "    \"httpCustomHeaders\": [\n"
                + "      {}\n"
                + "    ]\n"
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
                                "{\"id\":\"id\",\"name\":\"name\",\"status\":\"active\",\"type\":\"http\",\"isPriority\":true,\"filters\":[{\"type\":\"category\",\"name\":\"auth.login.fail\"}],\"pii_config\":{\"log_fields\":[\"first_name\"],\"method\":\"mask\",\"algorithm\":\"xxhash\"},\"sink\":{\"httpAuthorization\":\"httpAuthorization\",\"httpContentFormat\":\"JSONARRAY\",\"httpContentType\":\"httpContentType\",\"httpEndpoint\":\"httpEndpoint\",\"httpCustomHeaders\":[{}]}}"));
        GetLogStreamResponseContent response = client.logStreams().get("id");
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
                + "  \"status\": \"active\",\n"
                + "  \"type\": \"http\",\n"
                + "  \"isPriority\": true,\n"
                + "  \"filters\": [\n"
                + "    {\n"
                + "      \"type\": \"category\",\n"
                + "      \"name\": \"auth.login.fail\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"pii_config\": {\n"
                + "    \"log_fields\": [\n"
                + "      \"first_name\"\n"
                + "    ],\n"
                + "    \"method\": \"mask\",\n"
                + "    \"algorithm\": \"xxhash\"\n"
                + "  },\n"
                + "  \"sink\": {\n"
                + "    \"httpAuthorization\": \"httpAuthorization\",\n"
                + "    \"httpContentFormat\": \"JSONARRAY\",\n"
                + "    \"httpContentType\": \"httpContentType\",\n"
                + "    \"httpEndpoint\": \"httpEndpoint\",\n"
                + "    \"httpCustomHeaders\": [\n"
                + "      {}\n"
                + "    ]\n"
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
        client.logStreams().delete("id");
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
                                "{\"id\":\"id\",\"name\":\"name\",\"status\":\"active\",\"type\":\"http\",\"isPriority\":true,\"filters\":[{\"type\":\"category\",\"name\":\"auth.login.fail\"}],\"pii_config\":{\"log_fields\":[\"first_name\"],\"method\":\"mask\",\"algorithm\":\"xxhash\"},\"sink\":{\"httpAuthorization\":\"httpAuthorization\",\"httpContentFormat\":\"JSONARRAY\",\"httpContentType\":\"httpContentType\",\"httpEndpoint\":\"httpEndpoint\",\"httpCustomHeaders\":[{}]}}"));
        UpdateLogStreamResponseContent response = client.logStreams()
                .update("id", UpdateLogStreamRequestContent.builder().build());
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
                + "  \"status\": \"active\",\n"
                + "  \"type\": \"http\",\n"
                + "  \"isPriority\": true,\n"
                + "  \"filters\": [\n"
                + "    {\n"
                + "      \"type\": \"category\",\n"
                + "      \"name\": \"auth.login.fail\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"pii_config\": {\n"
                + "    \"log_fields\": [\n"
                + "      \"first_name\"\n"
                + "    ],\n"
                + "    \"method\": \"mask\",\n"
                + "    \"algorithm\": \"xxhash\"\n"
                + "  },\n"
                + "  \"sink\": {\n"
                + "    \"httpAuthorization\": \"httpAuthorization\",\n"
                + "    \"httpContentFormat\": \"JSONARRAY\",\n"
                + "    \"httpContentType\": \"httpContentType\",\n"
                + "    \"httpEndpoint\": \"httpEndpoint\",\n"
                + "    \"httpCustomHeaders\": [\n"
                + "      {}\n"
                + "    ]\n"
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
